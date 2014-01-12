package pwr.itapp.meetme.auth

import org.springframework.dao.DataIntegrityViolationException
import grails.plugin.springsecurity.annotation.Secured

class UserController {

	def passwordEncoder;
	
	static allowedMethods = [save: "POST", saveAdmin: "POST", update: "POST", updateAdmin: "POST", delete: "POST", changePasswordPost : "POST"]

	def activate(Long id) {
		// VALIDATION
		if(id == null)
		{
			flash.error = message(code: 'val.msg.user.emptyIdValue')
			redirect(action: "list")
			return
		}

		def userInstance = User.get(id)
		if (!userInstance) {
			flash.error = message(code: 'val.msg.user.userNotFoundById')
			redirect(action: "list")
			return
		}

		if(userInstance.status) {
			flash.error = message(code:'msg.user.activate.alreadyActivated')
			redirect(action: "list")
			return
		}
		else {
			userInstance.status = true;
			if(userInstance.save(flush: true)) {
				flash.message = message(code:'msg.user.activate.Activated')
			}
			else {
				flash.error = message(code:'val.msg.user.userNotSaved');
			}

			redirect(action: "list")
			return
		}
	}

	def changePassword(Long id) {
		// VALIDATION
		if(id == null)
		{
			flash.error = message(code: 'val.msg.user.emptyIdValue')
			redirect(url: "/")
			return
		}
		def userInstance = User.get(id)
		if (!userInstance) {
			flash.error = message(code: 'val.msg.user.userNotFoundById')
			redirect(url: "/")
			return
		}

		[userInstance: userInstance]
	}

	def changePasswordPost() {

		// Validation
		if(params.id == null || params.id.isEmpty() || !params.id.isNumber())
		{
			flash.error = message(code: 'val.msg.user.emptyIdValue')
			redirect(url: "/")
			return
		}
		if(params.oldPassword == null || params.oldPassword.isEmpty())
		{
			flash.error = message(code: 'val.msg.user.emptyOldPasswordValue')
			redirect(action: "show", id: params.id)
			return
		}
		if(params.newPassword == null || params.newPassword.isEmpty())
		{
			flash.error = message(code: 'val.msg.user.emptyNewPasswordValue')
			redirect(action: "show", id: params.id)
			return
		}
		if(params.newPassword2 == null || params.newPassword2.isEmpty())
		{
			flash.error = message(code: 'val.msg.user.emptyNewPassword2Value')
			redirect(action: "show", id: params.id)
			return
		}

		
		def userInstance = User.get(params.id)
		if (!userInstance) {
			flash.error = message(code: 'val.msg.user.userNotFoundById')
			redirect(action: "show", id: params.id)
			return
		}

		if (!passwordEncoder.isPasswordValid(userInstance.password, params.oldPassword, null /*salt*/)) {
			flash.error = message(code:'val.msg.user.oldPasswordIsIncorrect');
			render view: 'changePassword', model: [userInstance: userInstance]
			return
		}

		if (passwordEncoder.isPasswordValid(userInstance.password, params.newPassword, null /*salt*/)) {
			flash.error = message(code: 'val.msg.user.oldAndNewPasswordAreSame')
			render view: 'changePassword', model: [userInstance: userInstance]
			return
		}

		if(params.newPassword != params.newPassword2)
		{
			flash.error = message(code: 'val.msg.user.passwordMismatch')
			render view: 'changePassword', model: [userInstance: userInstance]
			return
		}
		userInstance.password = params.newPassword
		userInstance.passwordExpired = false
		if (!userInstance.save(flush: true)) {
			flash.message = message(code: 'val.msg.user.passwordNotChanged')
		}
		else
		{
			flash.message = message(code: 'val.msg.user.passwordChanged')
		}
		
		redirect(action: "show", id: params.id)
	}

	def create () {
		[userInstance: new User(params), password2 : ""]
	}

	def createFromInvitation() {
		// VALIDATION
		if(params.guid == null || params.guid.isEmpty())
		{
			flash.error = message(code: 'val.msg.user.incorrectLinkToInvitationAccept')
			redirect(url:"/");
			return
		}
		User user = User.findByGuid(params.guid);
		if(user != null) {
			[userInstance: user, password2 : ""]
		}
		else {
			//throw new Exception("Cannot update user from invitation because he is not existing")
			flash.error = message(code:"val.msg.user.userCannotBeFound");
			redirect(url:"/");
			return
		}
	}
	def deactivate(Long id) {
		if(id == null)
		{
			flash.error = message(code: 'val.msg.user.emptyIdValue')
			redirect(action: "list")
			return
		}
		def userInstance = User.get(id)
		if (!userInstance) {
			flash.error = message(code: 'val.msg.user.userNotFoundById')
			redirect(action: "list")
			return
		}

		if(!userInstance.status) {
			flash.error = message(code: 'msg.user.deactivate.alreadyDeactivated')
			redirect(action: "list")
		}
		else {
			userInstance.status = false;
			if(userInstance.save(flush: true)) {
				flash.message =  message(code: 'msg.user.deactivate.Deactivated')
			}
			else {
				flash.error = message(code: 'val.msg.user.userNotSaved')
			}

			redirect(action: "list")
		}
	}

	def delete(Long id) {
		if(id == null)
		{
			flash.error = message(code: 'val.msg.user.emptyIdValue')
			redirect(action: "list")
			return
		}
		def userInstance = User.get(id)
		if (!userInstance) {
			flash.error = message(code: 'val.msg.user.userNotFoundById')
			redirect(action: "list")
			return
		}

		try {
			userInstance.delete(flush: true)
			flash.error = message(code: 'val.msg.user.userNotFoundById')
			redirect(action: "list")
			return
		}
		catch (DataIntegrityViolationException e) {
			flash.error = message(code: 'val.msg.user.userNotDeleted')
			redirect(action: "show", id: id)
		}
	}

	def disable(Long id){
		if(id == null)
		{
			flash.error = message(code: 'val.msg.user.emptyIdValue')
			redirect(action: "list")
			return
		}
		def userInstance = User.get(id)
		if (!userInstance) {
			flash.error = message(code: 'val.msg.user.userNotFoundById')
			redirect(action: "list")
			return
		}

		if(!userInstance.enabled) {
			flash.error = message(code: 'msg.user.disable.alreadyDisabled')
			redirect(action: "list")
		}
		else {
			userInstance.enabled = false;
			if(userInstance.save(flush: true)) {
				flash.message =  message(code: 'msg.user.disable.Disabled')
			}
			else {
				flash.error = message(code: 'val.msg.user.userNotSaved')
			}

			redirect(action: "list")
		}
	}

	def edit(Long id) {
		if(id == null)
		{
			flash.error = message(code: 'val.msg.user.emptyIdValue')
			redirect(action: "list")
			return
		}
		def userInstance = User.get(id)
		if (!userInstance) {
			flash.error = message(code: 'val.msg.user.userNotFoundById')
			redirect(action: "list")
			return
		}

		[userInstance: userInstance]
	}

	def enable(Long id){
		if(id == null)
		{
			flash.error = message(code: 'val.msg.user.emptyIdValue')
			redirect(action: "list")
			return
		}
		def userInstance = User.get(id)
		if (!userInstance) {
			flash.error = message(code: 'val.msg.user.userNotFoundById')
			redirect(action: "list")
			return
		}

		if(userInstance.enabled) {
			flash.error = message(code: 'msg.user.enable.alreadyEnabled')
			redirect(action: "list")
			return
		}
		else {
			userInstance.enabled = true;
			if(userInstance.save(flush: true)) {
				flash.message =  message(code: 'msg.user.enable.Enabled')
			}
			else {
				flash.error = message(code: 'val.msg.user.userNotSaved')
			}

			redirect(action: "list")
		}
	}

	def expire(Long id){
		if(id == null)
		{
			flash.error = message(code: 'val.msg.user.emptyIdValue')
			redirect(action: "list")
			return
		}
		def userInstance = User.get(id)
		if (!userInstance) {
			flash.error = message(code: 'val.msg.user.userNotFoundById')
			redirect(action: "list")
			return
		}

		if(userInstance.accountExpired) {
			flash.error = message(code: 'msg.user.expire.alreadyExpired')
			redirect(action: "list")
		}
		else {
			userInstance.accountExpired = true;
			if(userInstance.save(flush: true)) {
				flash.message =  message(code: 'msg.user.expire.Expired')
			}
			else {
				flash.error = message(code: 'val.msg.user.userNotSaved')
			}

			redirect(action: "list")
		}
	}

	def extend(Long id){
		if(id == null)
		{
			flash.error = message(code: 'val.msg.user.emptyIdValue')
			redirect(action: "list")
			return
		}
		def userInstance = User.get(id)
		if (!userInstance) {
			flash.error = message(code: 'val.msg.user.userNotFoundById')
			redirect(action: "list")
			return
		}

		if(!userInstance.accountExpired) {
			flash.error = message(code: 'msg.user.extend.notExpired')
			redirect(action: "list")
		}
		else {
			userInstance.accountExpired = false;
			if(userInstance.save(flush: true)) {
				flash.message =  message(code: 'msg.user.extend.Extended')
			}
			else {
				flash.error = message(code: 'val.msg.user.userNotSaved')
			}

			redirect(action: "list")
		}
	}

	def index() {
		redirect(action: "create", params: params)
	}

	@Secured(['ROLE_ADMIN'])
	def list(Integer max) {
		params.max = Math.min(max ?: 10, 100)
		[userInstanceList: User.list(params), userInstanceTotal: User.count()]
	}

	def lock(Long id){
		if(id == null)
		{
			flash.error = message(code: 'val.msg.user.emptyIdValue')
			redirect(action: "list")
			return
		}
		def userInstance = User.get(id)
		if (!userInstance) {
			flash.error = message(code: 'val.msg.user.userNotFoundById')
			redirect(action: "list")
			return
		}

		if(userInstance.accountLocked) {
			flash.error = message(code: 'msg.user.lock.alreadyLocked')
			redirect(action: "list")
		}
		else {
			userInstance.accountLocked = true;
			if(userInstance.save(flush: true)) {
				flash.message =  message(code: 'msg.user.lock.Locked')
			}
			else {
				flash.error = message(code: 'val.msg.user.userNotSaved')
			}

			redirect(action: "list")
		}
	}

	def save() {
		def userInstance = new User(params)

		if(params.username == null || params.username.isEmpty())
		{
			flash.error = message(code: 'val.msg.user.usernameNullOrEmpty')
			render(view: "create", model: [userInstance: userInstance])
			return
		}
		if(params.password == null || params.password.isEmpty())
		{
			flash.error = message(code: 'val.msg.user.passwordNullOrEmpty')
			render(view: "create", model: [userInstance: userInstance])
			return
		}
		if(params.name == null || params.name.isEmpty())
		{
			flash.error = message(code: 'val.msg.user.nameNullOrEmpty')
			render(view: "create", model: [userInstance: userInstance])
			return
		}
		if(params.phone == null || params.phone.isEmpty())
		{
			flash.error = message(code: 'val.msg.user.phoneNullOrEmpty')
			render(view: "create", model: [userInstance: userInstance])
			return
		}


		if (userInstance.validate()) {
			if(userInstance.password == params.password2)
			{
				userInstance.passwordExpired = false
				userInstance.accountExpired = false
				userInstance.accountLocked = false
				userInstance.enabled = true
				if (!userInstance.save(flush: true)) {
					flash.error = message(code: 'val.msg.user.userNotSaved')
					render(view: "create", model: [userInstance: userInstance])
					return
				}
				else
				{
					flash.message = message(code: 'val.msg.user.userSaved')
					redirect(action: "list")
					return
				}
			}
			else
			{
				flash.error = message(code: 'val.msg.user.passwordMismatch')
				render(view: "create", model: [userInstance: userInstance])
				return
			}
		}
		else {
			flash.error = message(code: 'val.msg.user.userNotCreated')
			redirect(action: "list")
			return
		}

		flash.message = message(code: 'msg.user.create.created')
		redirect(action: "show", id: userInstance.id)
	}

	def saveFromInvitation() {
		def newUserInstance = new User(params)
		if (userInstance.validate()) {
			if(params.guid == null || params.guid.isEmpty())
			{
				flash.error = message(code: 'val.msg.user.incorrectLinkToInvitationAccept')
				render(view: "create", model: [userInstance: currentUserInstance])
				return
			}
			if(params.password2 == null || params.password2.isEmpty())
			{
				flash.error = message(code: 'val.msg.user.password2Missed')
				render(view: "create", model: [userInstance: currentUserInstance])
				return
			}
			if(newUserInstance.username == null || newUserInstance.username.isEmpty())
			{
				flash.error = message(code: 'val.msg.user.usernameNullOrEmpty')
				render(view: "create", model: [userInstance: currentUserInstance])
				return
			}
			if(newUserInstance.password == null || newUserInstance.password.isEmpty())
			{
				flash.error = message(code: 'val.msg.user.passwordNullOrEmpty')
				render(view: "create", model: [userInstance: currentUserInstance])
				return
			}
			if(newUserInstance.name == null || newUserInstance.name.isEmpty())
			{
				flash.error = message(code: 'val.msg.user.val.msg.user.nameNullOrEmpty')
				render(view: "create", model: [userInstance: currentUserInstance])
				return
			}
			if(newUserInstance.phone == null || newUserInstance.phone.isEmpty())
			{
				flash.error = message(code: 'val.msg.user.phoneNullOrEmpty')
				render(view: "create", model: [userInstance: currentUserInstance])
				return
			}



			def currentUserInstance = User.findByGuid(params.guid)
			if (!currentUserInstance) {
				flash.error = message(code: 'val.msg.user.userNotFoundById')
				redirect(action: "list")
				return
			}
			if(newUserInstance.password == params.password2)
			{
				currentUserInstance.passwordExpired = false
				currentUserInstance.accountExpired = false
				currentUserInstance.accountLocked = false
				currentUserInstance.enabled = true
				currentUserInstance.status = true

				currentUserInstance.username = newUserInstance.username
				currentUserInstance.password = newUserInstance.password
				currentUserInstance.guid = "";
				currentUserInstance.name = newUserInstance.name
				currentUserInstance.phone = newUserInstance.phone

				if (currentUserInstance.save(flush: true)) {
					flash.message = message(code: 'val.msg.user.userSaved')
				}
				else {
					flash.error = message(code: 'val.msg.user.userNotSaved')
					render(view: "create", model: [userInstance: currentUserInstance])
					return

				}

			}
			else
			{
				flash.error = message(code: 'val.msg.user.passwordMismatch')
				render(view: "create", model: [userInstance: userInstance])
				return
			}

			flash.message = message(code: 'msg.user.create.created')
			redirect(action: "show", id: currentUserInstance.id)
		}
		else {
			flash.error = message(code: 'val.msg.user.userNotCreated')
			redirect(action: "list")
			return
		}

	}

	def show(Long id) {
		if(id == null)
		{
			flash.error = message(code: 'val.msg.user.emptyIdValue')
			redirect(action: "list")
			return
		}
		def userInstance = User.get(id)
		if (!userInstance) {
			flash.error = message(code: 'val.msg.user.userNotFoundById')
			redirect(action: "list")
			return
		}

		[userInstance: userInstance]
	}

	def unlock(Long id){
		if(id == null)
		{
			flash.error = message(code: 'val.msg.user.emptyIdValue')
			redirect(action: "list")
			return
		}
		def userInstance = User.get(id)
		if (!userInstance) {
			flash.error = message(code: 'val.msg.user.userNotFoundById')
			redirect(action: "list")
			return
		}

		if(!userInstance.accountLocked)
		{
			flash.error = message(code: 'msg.user.unlock.alreadyUnlocked')
			redirect(action: "list")
		}
		else
		{
			userInstance.accountLocked = false;
			if(userInstance.save(flush: true))
			{
				flash.message =  message(code: 'msg.user.unlock.Unlocked')
			}
			else
			{
				flash.error = message(code: 'val.msg.user.userNotSaved')
			}

			redirect(action: "list")
		}
	}

	def update(Long id, Long version) {
		def userInstance = User.get(id)
		if (!userInstance) {
			flash.error = message(code: 'val.msg.user.userNotFoundById')
			redirect(action: "list")
			return
		}

		if (version != null) {
			if (userInstance.version > version) {
				flash.error = "Another user has updated this User while you were editing"
				render(view: "edit", model: [userInstance: userInstance])
				return
			}
		}

		def paramsUser = new User(params)
		// VALIDATION
		if(params.username == null ||params.username.isEmpty())
		{
			flash.error = message(code: 'val.msg.user.usernameNullOrEmpty')
			render(view: "edit", model: [userInstance: paramsUser])
			return
		}
		if(params.email == null ||params.email.isEmpty())
		{
			flash.error = message(code: 'val.msg.user.emailNullOrEmpty')
			render(view: "edit", model: [userInstance: paramsUser])
			return
		}
		if(params.name == null ||params.name.isEmpty())
		{
			flash.error = message(code: 'val.msg.user.nameNullOrEmpty')
			render(view: "edit", model: [userInstance: paramsUser])
			return
		}
		if(params.phone == null ||params.phone.isEmpty())
		{
			flash.error = message(code: 'val.msg.user.phoneNullOrEmpty')
			render(view: "edit", model: [userInstance: paramsUser])
			return
		}

		userInstance.username = params.username;
		userInstance.email = params.email
		userInstance.name = params.name
		userInstance.phone = params.phone

		if (userInstance.save(flush: true)) {
			flash.message = message(code: 'val.msg.user.userSaved')
		}
		else
		{
			flash.error = message(code: 'val.msg.user.userNotSaved')
			render(view: "edit", model: [userInstance: userInstance])
			return
		}

		redirect(action: "show", id: userInstance.id)
	}
}
