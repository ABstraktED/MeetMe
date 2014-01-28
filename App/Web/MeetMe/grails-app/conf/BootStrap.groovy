import java.text.SimpleDateFormat

import pwr.itapp.meetme.Comment
import pwr.itapp.meetme.Contact
import pwr.itapp.meetme.Event
import pwr.itapp.meetme.Invitation
import pwr.itapp.meetme.Location
import pwr.itapp.meetme.auth.Role
import pwr.itapp.meetme.auth.User
import pwr.itapp.meetme.auth.UserRole

class BootStrap {

	def init = { servletContext ->
		if(Role.getAll().empty){
			def adminRole = new Role(authority: 'ROLE_ADMIN').save(flush: true)
			def userRole = new Role(authority: 'ROLE_USER').save(flush: true)

			def tAdminUser = new User(username: 'admin', enabled: true, password: 'admin')
			tAdminUser.save(flush: true)

			def tUser1 = new User(username: 'user1', enabled: true, password: 'user1', email: 'user1@wp.pl', name:'user1_name', phone:'+48100500400', status: true)
			tUser1.save(flush: true)

			def tUser2 = new User(username: 'user2', enabled: true, password: 'user2', email: 'user2@wp.pl', name:'user2_name', phone:'+48200500700',status: true)
			tUser2.save(flush: true)

			def tUser3 = new User(username: 'user3', enabled: true, password: 'user3', email: 'user3@wp.pl', name:'user3_name', phone:'+48300500700',status: false)
			tUser3.save(flush: true)

			def tUser4 = new User(username: 'user4', enabled: true, password: 'user4', email: 'user4@wp.pl', name:'user4_name', phone:'+48400500700',status: false)
			tUser4.save(flush: true)

			def anotherUser = new User(username: 'another', enabled: true, password: 'another', email: 'another@another.com')
			anotherUser.save(flush: true)

			

			UserRole.create tAdminUser, adminRole, true
			UserRole.create tUser1, userRole, true
			UserRole.create tUser2, userRole, true
			UserRole.create tUser3, userRole, true
			UserRole.create tUser4, userRole, true
			UserRole.create anotherUser, adminRole, true
			

			assert User.count() == 6
			assert Role.count() == 2
			assert UserRole.count() == 6

			def testLocation = new Location(address: 'Poznan, Poland', lat: 52.40637400000000, lng: 16.92516810000006)
			testLocation.save(flush: true)
			def testLocation2 = new Location(address: 'Wroclaw, Poland', lat: 51.10788520000000, lng: 17.03853760000004)
			testLocation2.save(flush: true)
			
			def event1 = new Event(user: tAdminUser, location: testLocation, title: 'First event', description: 'First description', date: new SimpleDateFormat("yyyy/MM/dd hh:mm").parse('2014/03/31 14:30'), allCanJoin: true, allCanInvite: true)
			event1.save(flush: true)
			def event2 = new Event(user: tAdminUser, location: testLocation2, title: 'Second event', description: 'Second description', date: new SimpleDateFormat("yyyy/MM/dd hh:mm").parse('2014/03/20 15:30'), allCanJoin: false, allCanInvite: false)
			event2.save(flush: true)
			
			def contact1 = new Contact(user: tAdminUser, friend: tUser1)
			contact1.save(flush: true)
			def contact2 = new Contact(user: tAdminUser, friend: anotherUser)
			contact2.save(flush: true)
			
			def comment1 = new Comment(user: tUser1, event: event1, content: "It's gonna be awesome!", date: new SimpleDateFormat("yyyy/MM/dd hh:mm").parse('2014/02/20 10:30'))
			comment1.save(flush: true)
			def comment2 = new Comment(user: anotherUser, event: event1, content: "You know it!", date: new SimpleDateFormat("yyyy/MM/dd hh:mm").parse('2014/02/20 11:30'))
			comment2.save(flush: true)
			
			def invitation1 = new Invitation(user: tUser1, event: event1, status: false, confirmation: false)
			invitation1.save(flush: true)
			def invitation2 = new Invitation(user: anotherUser, event: event1, status: false, confirmation: false)
			invitation2.save(flush: true)
			
		}
	}
	def destroy = {
	}
}