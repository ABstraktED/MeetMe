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

		email email: true, blank: false, unique: true
		name size: 2..30, blank: false, unique: false
		phone blank: false, unique: false //, matches: "/[+]([0-9]{2})([ -]?)([0-9]{3})([ -]?)([0-9]{3})([ -]?)([0-9]{3})/"
		// to check regex visit http://www.regexper.com/#%2F%5B%2B%5D(%5B0-9%5D%7B2%7D)(%5B%20-%5D%3F)(%5B0-9%5D%7B3%7D)%5C2(%5B0-9%5D%7B3%7D)%5C2(%5B0-9%5D%7B3%7D)%2F
		status blank: false, unique: false
		image blank: true
		location blank: true
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
