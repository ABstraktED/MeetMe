package pwr.itapp.meetme.auth

import grails.converters.JSON
import grails.plugin.springsecurity.SpringSecurityUtils

import org.apache.commons.validator.EmailValidator
import javax.servlet.http.HttpServletResponse
import java.util.regex.Matcher
import java.util.regex.Pattern

import org.springframework.security.access.annotation.Secured
import org.springframework.security.authentication.AccountExpiredException
import org.springframework.security.authentication.CredentialsExpiredException
import org.springframework.security.authentication.DisabledException
import org.springframework.security.authentication.LockedException
import org.springframework.security.core.context.SecurityContextHolder as SCH
import org.springframework.security.web.WebAttributes

import pwr.itapp.meetme.auth.User

@Secured('permitAll')
class AuthenticationController {
	/*
	 * Properties
	 */
	def pattern = ~/[+]([0-9]{2})([ -]?)([0-9]{3})([ -]?)([0-9]{3})([ -]?)([0-9]{3})/
	
	static allowedMethods = [loginPost: "POST"]
	/**
	 * Dependency injection for the authenticationTrustResolver.
	 */
	def authenticationTrustResolver

	/**
	 * Dependency injection for the springSecurityService.
	 */
	def springSecurityService

	/**
	 * Default action; redirects to 'defaultTargetUrl' if logged in, Authentication/login otherwise.
	 */
	def index() {
		if (springSecurityService.isLoggedIn()) {
			redirect uri: SpringSecurityUtils.securityConfig.successHandler.defaultTargetUrl
		}
		else {
			redirect action: 'login', params: params
		}
	}

	/**
	 * Show the login page.
	 */
	def login() {

		def config = SpringSecurityUtils.securityConfig

		if (springSecurityService.isLoggedIn()) {
			redirect uri: config.successHandler.defaultTargetUrl
			return
		}
		String view = 'login'
		String postUrl = createLink(action: "loginPost", controller:"authentication")
		render view: view, model: [postUrl: postUrl,
			rememberMeParameter: config.rememberMe.parameter]
	}
	
	def loginPost() {

		def config = SpringSecurityUtils.securityConfig
		
		def userEntry = params.j_username
		def userName = userEntry
		def userPass = params.j_password
		
		String postUrl = 'loginPost'
		String view = 'login'
		if(!userEntry?.trim() || !userPass?.trim())
		{
			if (!userEntry?.trim()) {
				appendFlashMessage('Empty or null username')
			}
			if (!userPass?.trim()) {
				appendFlashMessage('Empty or null password')
			}
			render view: view, model: [postUrl: postUrl,
				rememberMeParameter: config.rememberMe.parameter]
			return
		}
		EmailValidator emailValidator = EmailValidator.getInstance() 
		if (emailValidator.isValid(userEntry))
		{
			def user = User.findWhere(email: userEntry)
			if(user)
			{
				userName = user.username
			}
			else
			{
				appendFlashMessage('User not found by given email')
				println 'User not found by given email'
				render view: view, model: [postUrl: postUrl,
					rememberMeParameter: config.rememberMe.parameter]
				return
			}
		}
		else if (pattern.matcher(userEntry).matches())
		{
			def user = User.findWhere(phone: userEntry)
			if(user)
			{
				userName = user.username
			}
			else
			{
				appendFlashMessage('User not found by given phone')
				render view: view, model: [postUrl: postUrl,
					rememberMeParameter: config.rememberMe.parameter]
				
				println 'User not found by given phone'
				return
			}
		}
		else
		{
			def user = User.findWhere(username: userEntry)
			if(user)
			{
				userName = user.username
			}
			else
			{
				appendFlashMessage('User not found by given user name')
				render view: view, model: [postUrl: postUrl,
					rememberMeParameter: config.rememberMe.parameter]
				println 'User not found by given user name'
				return
			}
		}	
		
		postUrl = "${request.contextPath}${config.apf.filterProcessesUrl}"
		render view: 'login_redirect', model: [
			postUrl: postUrl, 
			rememberMeParameter: config.rememberMe.parameter, 
			j_username: userName, 
			j_password: userPass,
			j_rememberMe: params[config.rememberMe.parameter] ]
		println 'login redirect'
	}

	/**
	 * The redirect action for Ajax requests.
	 */
	def authAjax() {
		response.setHeader 'Location', SpringSecurityUtils.securityConfig.auth.ajaxLoginFormUrl
		response.sendError HttpServletResponse.SC_UNAUTHORIZED
	}

	/**
	 * Show denied page.
	 */
	def denied() {
		if (springSecurityService.isLoggedIn() &&
		authenticationTrustResolver.isRememberMe(SCH.context?.authentication)) {
			// have cookie but the page is guarded with IS_AUTHENTICATED_FULLY
			redirect action: 'full', params: params
		}
	}

	/**
	 * Login page for users with a remember-me cookie but accessing a IS_AUTHENTICATED_FULLY page.
	 */
	def full() {
		def config = SpringSecurityUtils.securityConfig
		render view: 'login', params: params,
		model: [hasCookie: authenticationTrustResolver.isRememberMe(SCH.context?.authentication),
			postUrl: "${request.contextPath}${config.apf.filterProcessesUrl}"]
	}

	/**
	 * Callback after a failed login. Redirects to the auth page with a warning message.
	 */
	def authfail() {

		String msg = ''
		def exception = session[WebAttributes.AUTHENTICATION_EXCEPTION]
		if (exception) {
			if (exception instanceof AccountExpiredException) {
				msg = g.message(code: "springSecurity.errors.login.expired")
			}
			else if (exception instanceof CredentialsExpiredException) {
				msg = g.message(code: "springSecurity.errors.login.passwordExpired")
			}
			else if (exception instanceof DisabledException) {
				msg = g.message(code: "springSecurity.errors.login.disabled")
			}
			else if (exception instanceof LockedException) {
				msg = g.message(code: "springSecurity.errors.login.locked")
			}
			else {
				msg = g.message(code: "springSecurity.errors.login.fail")
			}
		}

		if (springSecurityService.isAjax(request)) {
			render([error: msg] as JSON)
		}
		else {
			flash.message = msg
			redirect action: 'login', params: params
		}
	}

	/**
	 * The Ajax success redirect url.
	 */
	def ajaxSuccess() {
		render([success: true, username: springSecurityService.authentication.name] as JSON)
	}

	/**
	 * The Ajax denied redirect url.
	 */
	def ajaxDenied() {
		render([error: 'access denied'] as JSON)
	}

	def logout() {

		if (!request.post && SpringSecurityUtils.getSecurityConfig().logout.postOnly) {
			response.sendError HttpServletResponse.SC_METHOD_NOT_ALLOWED // 405
			return
		}

		// TODO put any pre-logout code here
		redirect uri: SpringSecurityUtils.securityConfig.logout.filterProcessesUrl // '/j_spring_security_logout'
	}
	
	def error() {
		render view: 'error'
	}
	
	
	def appendFlashMessage(String message)
	{
		if(flash.message != null)
		{
			flash.message = flash.message + message
		}
		else
		{
			flash.message = 'User not found by given user name'
		}
	}
}
