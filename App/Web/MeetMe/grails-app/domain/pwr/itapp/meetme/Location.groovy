package pwr.itapp.meetme

class Location {

	double lng
	double lat
	String address


	static constraints = {
	}

	static hasMany = [events: Event]

	static mapping = {
		version false
	}
}
