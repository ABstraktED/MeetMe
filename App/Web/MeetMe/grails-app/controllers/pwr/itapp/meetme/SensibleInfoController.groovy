package pwr.itapp.meetme

import grails.plugin.springsecurity.annotation.Secured

class SensibleInfoController {

	@Secured(['ROLE_ADMIN'])
    def index() {
		render "A very sensible content"
	}
}
