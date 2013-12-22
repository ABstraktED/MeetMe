package pwr.itapp.meetme.auth

import org.springframework.dao.DataIntegrityViolationException
import grails.plugin.springsecurity.annotation.Secured

class UserController {

    static allowedMethods = [save: "POST", saveAdmin: "POST", update: "POST", updateAdmin: "POST", delete: "POST", changePasswordPost : "POST"]

	def activate(Long id)
	{
		def userInstance = User.get(id)
		if (!userInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'user.label', default: 'User'), id])
			redirect(action: "list")
			return
		}
		
		if(userInstance.status)
		{
			flash.message = message(code:'msg.user.activate.alreadyActivated')
			redirect(action: "list")
		}
		else
		{
			userInstance.status = true;
			if(userInstance.save(flush: true))
			{
			flash.message = message(code:'msg.user.activate.Activated')
			}
			else
			{
				flash.message = message(code:'msg.meetme.error');
			}
			
			redirect(action: "list")
		}
		
	}
	
	def changePassword(Long id) {
		def userInstance = User.get(id)
		if (!userInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'user.label', default: 'User'), id])
			redirect(action: "list")
			return
		}

		[userInstance: userInstance]
	}
	
	def changePasswordPost()
	{
		flash.message = "Password changed";
		redirect(action: "index")
		
	}
	
	def create ()
	{
		[userInstance: new User(params), password2 : ""]
	}
	
	def deactivate(Long id)
	{
		def userInstance = User.get(id)
		if (!userInstance) {
			flash.message = message(code: 'msg.meetme.error')
			redirect(action: "list")
			return
		}
		
		if(!userInstance.status)
		{
			flash.message = message(code: 'msg.user.deactivate.alreadyDeactivated')
			redirect(action: "list")
		}
		else
		{
			userInstance.status = false;
			if(userInstance.save(flush: true))
			{
			flash.message =  message(code: 'msg.user.deactivate.Deactivated')
			}
			else
			{
				flash.message = message(code: 'msg.meetme.error')
			}
			
			redirect(action: "list")
		}
		
	}
	
	def delete(Long id) {
		def userInstance = User.get(id)
		if (!userInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'user.label', default: 'User'), id])
			redirect(action: "list")
			return
		}

		try {
			userInstance.delete(flush: true)
			flash.message = message(code: 'default.deleted.message', args: [message(code: 'user.label', default: 'User'), id])
			redirect(action: "list")
		}
		catch (DataIntegrityViolationException e) {
			flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'user.label', default: 'User'), id])
			redirect(action: "show", id: id)
		}
	}
	
	def disable(Long id){
		def userInstance = User.get(id)
		if (!userInstance) {
			flash.message = message(code: 'msg.meetme.error')
			redirect(action: "list")
			return
		}
		
		if(!userInstance.enabled)
		{
			flash.message = message(code: 'msg.user.disable.alreadyDisabled')
			redirect(action: "list")
		}
		else
		{
			userInstance.enabled = false;
			if(userInstance.save(flush: true))
			{
			flash.message =  message(code: 'msg.user.disable.Disabled')
			}
			else
			{
				flash.message = message(code: 'msg.meetme.error')
			}
			
			redirect(action: "list")
		}
	}
	
	def edit(Long id) {
		def userInstance = User.get(id)
		if (!userInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'user.label', default: 'User'), id])
			redirect(action: "list")
			return
		}

		[userInstance: userInstance]
	}
	
	def enable(Long id){
		def userInstance = User.get(id)
		if (!userInstance) {
			flash.message = message(code: 'msg.meetme.error')
			redirect(action: "list")
			return
		}
		
		if(userInstance.enabled)
		{
			flash.message = message(code: 'msg.user.enable.alreadyEnabled')
			redirect(action: "list")
		}
		else
		{
			userInstance.enabled = true;
			if(userInstance.save(flush: true))
			{
			flash.message =  message(code: 'msg.user.enable.Enabled')
			}
			else
			{
				flash.message = message(code: 'msg.meetme.error')
			}
			
			redirect(action: "list")
		}
	}
	def expire(Long id){
		def userInstance = User.get(id)
		if (!userInstance) {
			flash.message = message(code: 'msg.meetme.error')
			redirect(action: "list")
			return
		}
		
		if(userInstance.accountExpired)
		{
			flash.message = message(code: 'msg.user.expire.alreadyExpired')
			redirect(action: "list")
		}
		else
		{
			userInstance.accountExpired = true;
			if(userInstance.save(flush: true))
			{
			flash.message =  message(code: 'msg.user.expire.Expired')
			}
			else
			{
				flash.message = message(code: 'msg.meetme.error')
			}
			
			redirect(action: "list")
		}
	}
	
	def extend(Long id){
		def userInstance = User.get(id)
		if (!userInstance) {
			flash.message = message(code: 'msg.meetme.error')
			redirect(action: "list")
			return
		}
		
		if(!userInstance.accountExpired)
		{
			flash.message = message(code: 'msg.user.extend.notExpired')
			redirect(action: "list")
		}
		else
		{
			userInstance.accountExpired = false;
			if(userInstance.save(flush: true))
			{
			flash.message =  message(code: 'msg.user.extend.Extended')
			}
			else
			{
				flash.message = message(code: 'msg.meetme.error')
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
		def userInstance = User.get(id)
		if (!userInstance) {
			flash.message = message(code: 'msg.meetme.error')
			redirect(action: "list")
			return
		}
		
		if(userInstance.accountLocked)
		{
			flash.message = message(code: 'msg.user.lock.alreadyLocked')
			redirect(action: "list")
		}
		else
		{
			userInstance.accountLocked = true;
			if(userInstance.save(flush: true))
			{
			flash.message =  message(code: 'msg.user.lock.Locked')
			}
			else
			{
				flash.message = message(code: 'msg.meetme.error')
			}
			
			redirect(action: "list")
		}
	}
	
    def save() {
		println 'SAVE'
        def userInstance = new User(params)
		if(userInstance.password == params.password2)
		{
			userInstance.passwordExpired = false
			userInstance.accountExpired = false
			userInstance.accountLocked = false
			userInstance.enabled = true
	        if (!userInstance.save(flush: true)) {
	            render(view: "create", model: [userInstance: userInstance])
	            return
	        }
		}
		else
		{
			flash.message = 'Password not matching'
			render(view: "create", model: [userInstance: userInstance])
			return
		}

        flash.message = message(code: 'default.created.message', args: [message(code: 'user.label', default: 'User'), userInstance.id])
        redirect(action: "show", id: userInstance.id)
    }

    def show(Long id) {
        def userInstance = User.get(id)
        if (!userInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'user.label', default: 'User'), id])
            redirect(action: "list")
            return
        }

        [userInstance: userInstance]
    }

	def unlock(Long id){
		def userInstance = User.get(id)
		if (!userInstance) {
			flash.message = message(code: 'msg.meetme.error')
			redirect(action: "list")
			return
		}
		
		if(!userInstance.accountLocked)
		{
			flash.message = message(code: 'msg.user.unlock.alreadyUnlocked')
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
				flash.message = message(code: 'msg.meetme.error')
			}
			
			redirect(action: "list")
		}
	}
	
    def update(Long id, Long version) {
		println 'UPDATE'
        def userInstance = User.get(id)
        if (!userInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'user.label', default: 'User'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (userInstance.version > version) {
                userInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'user.label', default: 'User')] as Object[],
                          "Another user has updated this User while you were editing")
                render(view: "edit", model: [userInstance: userInstance])
                return
            }
        }

        userInstance.properties = params

        if (!userInstance.save(flush: true)) {
            render(view: "edit", model: [userInstance: userInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'user.label', default: 'User'), userInstance.id])
        redirect(action: "show", id: userInstance.id)
    }



	

}
