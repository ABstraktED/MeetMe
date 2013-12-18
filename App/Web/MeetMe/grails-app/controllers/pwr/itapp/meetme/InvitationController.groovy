package pwr.itapp.meetme

import pwr.itapp.meetme.auth.User;
import grails.plugin.springsecurity.annotation.Secured

@Secured(['isFullyAuthenticated()'])
class InvitationController {
	static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
	
	def googleContactService
	def listService
	def currentUser = getAuthenticatedUser()
	
    def index() { }
	
	def list() {
		def query = Event.executeQuery("select e from Event e, Invitation i where i.user = :user and i.event = e",
			[user: getAuthenticatedUser(), max: 10, offset: 5])
		def q = Invitation.findAllByUser(User.get(6))
		def qsize = (q==null) ? 0 : q.size()
		
		[invitationInstanceList: q, invitationInstanceTotal: qsize]
	}
	
	def invitationCount(){
		[list().invitationInstanceTotal]
	}
	
	def acceptInvitation(){
		def inv = Invitation.findByUserAndEvent(currentUser, Event.get(params.eventId))
		inv.setConfirmation(true)
		redirect(controller: "event", action: "show", id: params.eventId)
	}

	
	def userListJSON(){
		
	}
	
	def inviteByPortal(){
		def userInstance = User.findByEmail(params.email)
		params.put("user", userInstance)
		params.put("event", Event.get(params.eventId))
		def invitationInstance = Invitation.find(new Invitation(params))
		if(invitationInstance == null){
			invitationInstance = new Invitation(params)
		}
		if(!invitationInstance.save(flush:true)){
			error: "Could not save"
			return
		}
		
		def eventInstance = Event.get(params.eventId)
		def dateString = eventInstance.date.format("dd/MM/yyyy hh:mm");
		sendMail {
			to "folque@gmail.com" //params.email
			subject "[MeetMe Client] You have new invitation"
			html g.render(template:"invitationByMailTemplate",
			model:[event: eventInstance.title,
				invitedBy :eventInstance.user.name,
				description: eventInstance.description,
				eventDate : dateString,
				link: 'http://www.wp.pl',
				eventId:params.eventId,
				recipientEmail: params.email])
		}
		redirect(controller: "event", action: "show", id: params.eventId)
	}
	
	def inviteFromGoogleContacts() {
		def eventId = params.eventId
		String view = 'inviteFromGoogleContacts'
		String postUrl = createLink(action: "inviteFromGoogleContactsPost", controller: "invitation")
		render view: view, model: [postUrl: postUrl, eventId: eventId]
	}

	def inviteFromGoogleContactsPost() {
		def username = params.username
		def password = params.password
		def eventId = params.eventId
		if(username.contains("@")) {
			flash.message = "Type your google account login without '@gmail.com'";
			redirect(action: "inviteFromGoogleContacts",)
		}

		def user = session["user"]
		session["user"] = username
		session["pass"] = password
		session["eventId"] = eventId

		redirect(action: "displayGoogleContact");
		return
	}

	def displayGoogleContact() {
		def user = session["user"]
		def pass = session["pass"]
		def eventId = session["eventId"]
		def result = googleContactService.getContactsFromAccount(user,pass)
		result.sort{it.name}
		println "contactsNumber: " + result.size()
		println "eventId: " + eventId
		println "Result " + result 
		def subList = listService.getSubList(result, params)
		[contacts: subList, eventId: eventId, contactsNumber: result.size()]
	}

	def inviteByEmail() {
		def user = session["user"]
		def pass = session["pass"]
		def eventId = session["eventId"]

		if(user == null || pass == null || eventId== null) {
			flash.message = "Your session expired. Please sign in to Google Account once again."
			redirect(action: "list");
		}

		def eventInstance = Event.get(eventId)
		def dateString = eventInstance.date.format("dd/MM/yyyy hh:mm");//new SimpleDateFormat("dd/MM/yyyy hh:mm").parse(eventInstance.date)
		sendMail {
			to "folque@gmail.com" //params.email
			subject "[MeetMe Client] You have new invitation"
			html g.render(template:"invitationByMailTemplate",
			model:[event: eventInstance.title,
				invitedBy :eventInstance.user.name,
				description: eventInstance.description,
				eventDate : dateString,
				link: 'http://www.wp.pl',
				eventId:eventId,
				recipientEmail: params.email])
		}
		flash.message = "Email successfully sent"
		redirect(action: "displayGoogleContact");
	}

	def inviteByPhone() {
		render "This is result of action: inviteByPhone from controller: event for phone: " + params.phone
	}

	def inviteFromGoogleContactsDone()
	{
		println session.getAttribute("user")
		println session.getAttribute("pass")
		session.removeAttribute("user")
		session.removeAttribute("pass")
		session.removeAttribute("eventId")
		redirect(action: "list");
	}
}
