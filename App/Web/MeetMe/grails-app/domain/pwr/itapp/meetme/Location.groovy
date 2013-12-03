package pwr.itapp.meetme

class Location {

	BigDecimal lng
	BigDecimal lat
	String address


	static constraints = {
	}

	static hasMany = [events: Event]

	static mapping = {
		version false
	}
}
