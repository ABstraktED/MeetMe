package pwr.itapp.meetme

class Location {

	double longitude
	double latitude
	String address


	static constraints = {
	}

	static hasMany = [events: Event]

	static mapping = {
		version false
	}
}
