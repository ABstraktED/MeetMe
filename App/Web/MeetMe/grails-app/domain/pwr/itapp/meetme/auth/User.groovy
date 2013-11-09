package pwr.itapp.meetme.auth

import pwr.itapp.meetme.Location

class User {

	transient springSecurityService

	String username
	String password
	boolean enabled = true
	boolean accountExpired
	boolean accountLocked
	boolean passwordExpired
	
	String email
	String name
	String phone
	boolean status
	String image
	Location location

	static transients = ['springSecurityService']

	static constraints = {
		username blank: false, unique: true, nullable: false
		password blank: false, nullable: false
	}

	static mapping = {
		password column: '`password`'
		version false
	}

	Set<Role> getAuthorities() {
		UserRole.findAllByUser(this).collect { it.role } as Set
	}

	def beforeInsert() {
		encodePassword()
	}

	def beforeUpdate() {
		if (isDirty('password')) {
			encodePassword()
		}
	}

	protected void encodePassword() {
		password = springSecurityService.encodePassword(password)
	}
}
