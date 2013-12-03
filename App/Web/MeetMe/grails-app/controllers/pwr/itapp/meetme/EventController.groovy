package pwr.itapp.meetme

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import grails.plugin.springsecurity.SpringSecurityService;
import grails.plugin.springsecurity.annotation.Secured

import org.hibernate.hql.ast.util.LiteralProcessor.DecimalFormatter;
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.format.annotation.DateTimeFormat;


@Secured(['ROLE_ADMIN'])
class EventController {

	static allowedMethods = [save: "POST", update: "POST", delete: "POST"]


	def index() {
		redirect(action: "list", params: params)
	}

	def newEvent(){
		render(view:'new')
	}

	def processEvent(){
		println params.date
		params.date = new SimpleDateFormat("dd/MM/yyyy hh:mm").parse(params.date)
		println params.date
		println params.lat + " " + params.lng
		def locationInstance = Location.find(new Location(params))
		if(locationInstance == null){
			locationInstance = new Location(params)
		}
		params.put("location", locationInstance)
		println locationInstance.lat + " " + locationInstance.lng
		params.put("user", getAuthenticatedUser())
		def eventInstance = new Event(params)
		if(!eventInstance.save(flush:true)){
			error: "Could not save"
			return
		}
		return [
			test: "Event ${params.title} successfully created"
		]
	}
	
	def search(){
		
		def eventInstance = new Event(params)
		
	}
	
	

	def list() {
		params.max = 10
		[eventInstanceList: Event.list(params), eventInstanceTotal: Event.count()]
	}

	def create() {
		[eventInstance: new Event(params)]
	}

	def save() {
		def eventInstance = new Event(params)
		if (!eventInstance.save(flush: true)) {
			render(view: "create", model: [eventInstance: eventInstance])
			return
		}

		flash.message = message(code: 'default.created.message', args: [message(code: 'event.label', default: 'Event'), eventInstance.id])
		redirect(action: "show", id: eventInstance.id)
	}

	def show(Long id) {
		def eventInstance = Event.get(id)
		println eventInstance.getDate()
		def discussion = Comment.findAll("from Comment c where c.event = " + id)
		if (!eventInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'event.label', default: 'Event'), id])
			redirect(action: "list")
			return
		}
		[eventInstance: eventInstance, discussion: discussion]
	}
	
	def newComment(){
		params.put("date", new Date())
		params.put("user", getAuthenticatedUser())
		params.put("event", Event.get(params.eventId))
		def commentInstance = new Comment(params)
		if(!commentInstance.save(flush:true)){
			error: "Could not save"
			return
		}
		redirect(action: "show", id: params.eventId)
	}

	def edit(Long id) {
		def eventInstance = Event.get(id)
		if (!eventInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'event.label', default: 'Event'), id])
			redirect(action: "list")
			return
		}

		[eventInstance: eventInstance]
	}

	def update(Long id, Long version) {
		def eventInstance = Event.get(id)
		if (!eventInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'event.label', default: 'Event'), id])
			redirect(action: "list")
			return
		}

		if (version != null) {
			if (eventInstance.version > version) {
				eventInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
						[message(code: 'event.label', default: 'Event')] as Object[],
						"Another user has updated this Event while you were editing")
				render(view: "edit", model: [eventInstance: eventInstance])
				return
			}
		}

		eventInstance.properties = params

		if (!eventInstance.save(flush: true)) {
			render(view: "edit", model: [eventInstance: eventInstance])
			return
		}

		flash.message = message(code: 'default.updated.message', args: [message(code: 'event.label', default: 'Event'), eventInstance.id])
		redirect(action: "show", id: eventInstance.id)
	}

	def delete(Long id) {
		def eventInstance = Event.get(id)
		if (!eventInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'event.label', default: 'Event'), id])
			redirect(action: "list")
			return
		}

		try {
			eventInstance.delete(flush: true)
			flash.message = message(code: 'default.deleted.message', args: [message(code: 'event.label', default: 'Event'), id])
			redirect(action: "list")
		}
		catch (DataIntegrityViolationException e) {
			flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'event.label', default: 'Event'), id])
			redirect(action: "show", id: id)
		}
	}
}
