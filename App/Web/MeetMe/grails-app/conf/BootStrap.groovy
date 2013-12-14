import pwr.itapp.meetme.auth.Role
import pwr.itapp.meetme.auth.User
import pwr.itapp.meetme.auth.UserRole
import pwr.itapp.meetme.Location

class BootStrap {

	def init = { servletContext ->
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

		def testLocation = new Location(address: 'place 1', lat: 1, lng: 1)
		testLocation.save(flush: true)
	}
	def destroy = {
	}
}
