package pwr.itapp.meetme

import grails.plugin.springsecurity.annotation.Secured

class HomeController {

	@Secured(['ROLE_ADMIN'])
    def index() {
		render "Hello kittens"
	}
}
