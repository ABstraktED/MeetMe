package pwr.itapp.meetme.auth

import org.springframework.dao.DataIntegrityViolationException
import grails.plugin.springsecurity.annotation.Secured

class UserController {

    static allowedMethods = [save: "POST", saveAdmin: "POST", update: "POST", updateAdmin: "POST", delete: "POST", changePasswordPost : "POST"]

    def index() {
        redirect(action: "create", params: params)
    }
	
	@Secured(['ROLE_ADMIN'])
    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [userInstanceList: User.list(params), userInstanceTotal: User.count()]
    }

	def create ()
	{
		[userInstance: new User(params), password2 : ""]
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
//	@Secured(['ROLE_ADMIN'])
//	def saveAdmin() {
//		def userInstance = new User(params)
//		if(userInstance.password == params.password2)
//		{
//		if (!userInstance.save(flush: true)) {
//			render(view: "create", model: [userInstance: userInstance])
//			return
//		}
//
//		flash.message = message(code: 'default.created.message', args: [message(code: 'user.label', default: 'User'), userInstance.id])
//		redirect(action: "showAdmin", id: userInstance.id)
//		}
//		else
//		{
//			flash.message = 'Password not matching'
//			render(view: "create", model: [userInstance: userInstance])
//			return
//		}
//		
//	}

    def show(Long id) {
        def userInstance = User.get(id)
        if (!userInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'user.label', default: 'User'), id])
            redirect(action: "list")
            return
        }

        [userInstance: userInstance]
    }
//	@Secured(['ROLE_ADMIN'])
//	def showAdmin(Long id) {
//		def userInstance = User.get(id)
//		if (!userInstance) {
//			flash.message = message(code: 'default.not.found.message', args: [message(code: 'user.label', default: 'User'), id])
//			redirect(action: "list")
//			return
//		}
//
//		[userInstance: userInstance]
//	}

    def edit(Long id) {
        def userInstance = User.get(id)
        if (!userInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'user.label', default: 'User'), id])
            redirect(action: "list")
            return
        }

        [userInstance: userInstance]
    }
//	@Secured(['ROLE_ADMIN'])
//	def editAdmin(Long id) {
//		def userInstance = User.get(id)
//		if (!userInstance) {
//			flash.message = message(code: 'default.not.found.message', args: [message(code: 'user.label', default: 'User'), id])
//			redirect(action: "list")
//			return
//		}
//
//		[userInstance: userInstance]
//	}

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
//	@Secured(['ROLE_ADMIN'])
//	def updateAdmin(Long id, Long version) {
//		def userInstance = User.get(id)
//		if (!userInstance) {
//			flash.message = message(code: 'default.not.found.message', args: [message(code: 'user.label', default: 'User'), id])
//			redirect(action: "list")
//			return
//		}
//
//		if (version != null) {
//			if (userInstance.version > version) {
//				userInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
//						  [message(code: 'user.label', default: 'User')] as Object[],
//						  "Another user has updated this User while you were editing")
//				render(view: "edit", model: [userInstance: userInstance])
//				return
//			}
//		}
//
//		userInstance.properties = params
//
//		if (!userInstance.save(flush: true)) {
//			render(view: "edit", model: [userInstance: userInstance])
//			return
//		}
//
//		flash.message = message(code: 'default.updated.message', args: [message(code: 'user.label', default: 'User'), userInstance.id])
//		redirect(action: "show", id: userInstance.id)
//	}

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
}
