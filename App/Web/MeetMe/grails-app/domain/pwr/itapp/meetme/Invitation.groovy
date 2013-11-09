package pwr.itapp.meetme

import pwr.itapp.meetme.auth.User

class Invitation implements Serializable{
	
	private static final long serialVersionUID = 1
	
	User user
	Event event
	boolean status
	boolean confirmation
	
    static constraints = {
    }
	
	
	static mapping = {
		id composite: ['user', 'event']
		version false
		status defaultValue: false
		confirmation defaultValue: false
	}

}
