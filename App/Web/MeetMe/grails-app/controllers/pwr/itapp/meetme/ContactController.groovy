package pwr.itapp.meetme

import grails.plugin.springsecurity.annotation.Secured

import org.springframework.dao.DataIntegrityViolationException

import pwr.itapp.meetme.auth.User

@Secured(['isFullyAuthenticated()'])
class ContactController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
	def currentUser = getAuthenticatedUser()
	def googleContactService
	def listService

    def index() {
        redirect(action: "list", params: params)
    }

    def list() {
		def query = Contact.findAllByUser(currentUser)
		
		def qsize = (query==null) ? 0 : query.size()
		
		[contactInstanceList: query, contactInstanceTotal: qsize]
	}
	
	
	def addContact() {
		// Validation
		String view = 'addContact'
		String postUrl = createLink(action: "contactFromGoogleContactsPost", controller: "contact")
		render view: view, model: [postUrl: postUrl]
	}
	
	def insertContact() {
		def email = params.email
		params.friend = User.findByEmail(email)
		params.user = currentUser
		println "Email " + email
		println "Friend " + params.friend
		println "User " + params.user
		def contactInstance = new Contact(params)
		if(!contactInstance.save(flush:true)){
			flash.error = message(code: "val.msg.event.couldNotSave")
			return
		}
		redirect(action:"list", params:params)
	}

	def contactFromGoogleContactsPost() {

		def username = params.username
		def password = params.password

		// Validation
		if(username.trim().isEmpty() || username == null) {
			flash.error = message(code: "val.msg.loginCannotBeEmpty");
			redirect(action: "contactFromGoogleContacts")
			return
		}
		if(username.contains("@")) {
			flash.error = message(code: "val.msg.emailContainsAt");
			redirect(action: "contactFromGoogleContacts")
			return
		}
		if(password.trim().isEmpty() || password == null) {
			flash.error = message(code: "val.msg.passwordCannotBeEmpty");
			redirect(action: "contactFromGoogleContacts")
			return
		}
		
		def user = session["user"]
		session["user"] = username
		session["pass"] = password
		
		redirect(action: "displayGoogleContact");
		return
	}

	def displayGoogleContact() {
		def user = session["user"]
		def pass = session["pass"]
		
		if(user == null || user.contains("@") || user.trim().isEmpty()) {
			flash.error = message(code: "val.msg.invitation.incorrectEmail");
			redirect(action: "list")
			return
		}
		if(pass.trim().isEmpty() || pass == null) {
			flash.error = message(code: "passwordCannotBeEmpty");
			redirect(action: "list")
			return
		}
		def allUsers = User.list(params)
		def result = googleContactService.getContactsFromAccountOnSystem(user,pass,allUsers)
		result.sort{it.name}
		def subList = listService.getSubList(result, params)
		[contacts: subList, contactsNumber: result.size()]
	}

    def create() {
        [contactInstance: new Contact(params)]
    }

    def save() {
        def contactInstance = new Contact(params)
        if (!contactInstance.save(flush: true)) {
            render(view: "create", model: [contactInstance: contactInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'contact.label', default: 'Contact'), contactInstance.id])
        redirect(action: "show", id: contactInstance.id)
    }

    def show(Long id) {
        def contactInstance = Contact.get(id)
        if (!contactInstance) {
            flash.error = message(code: 'default.not.found.message', args: [message(code: 'contact.label', default: 'Contact'), id])
            redirect(action: "list")
            return
        }

        [contactInstance: contactInstance]
    }

    def edit(Long id) {
        def contactInstance = Contact.get(id)
        if (!contactInstance) {
            flash.error = message(code: 'default.not.found.message', args: [message(code: 'contact.label', default: 'Contact'), id])
            redirect(action: "list")
            return
        }

        [contactInstance: contactInstance]
    }

    def update(Long id, Long version) {
        def contactInstance = Contact.get(id)
        if (!contactInstance) {
            flash.error = message(code: 'default.not.found.message', args: [message(code: 'contact.label', default: 'Contact'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (contactInstance.version > version) {
                contactInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'contact.label', default: 'Contact')] as Object[],
                          "Another user has updated this Contact while you were editing")
                render(view: "edit", model: [contactInstance: contactInstance])
                return
            }
        }

        contactInstance.properties = params

        if (!contactInstance.save(flush: true)) {
            render(view: "edit", model: [contactInstance: contactInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'contact.label', default: 'Contact'), contactInstance.id])
        redirect(action: "show", id: contactInstance.id)
    }

    def delete(Long id) {
        def contactInstance = Contact.get(id)
        if (!contactInstance) {
            flash.error = message(code: 'default.not.found.message', args: [message(code: 'contact.label', default: 'Contact'), id])
            redirect(action: "list")
            return
        }

        try {
            contactInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'contact.label', default: 'Contact'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.error = message(code: 'default.not.deleted.message', args: [message(code: 'contact.label', default: 'Contact'), id])
            redirect(action: "show", id: id)
        }
    }
}
