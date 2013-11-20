package pwr.itapp.meetme

import grails.plugin.springsecurity.annotation.Secured

class HomeController {

	@Secured(['ROLE_USER'])
    def index() {
		render "Hello kittens"
	}
	
	def myEvent(){
		render {
			"MEGA TEXT"
		}
	}
}
