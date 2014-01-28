package pwr.itapp.meetme

import pwr.itapp.meetme.auth.User

class Contact implements Serializable{
	
	private static final long serialVersionUID = 1
	
	User user
	User friend

    static constraints = {
    }
	
	static mapping = {
		version false
		id composite: ['user', 'friend']
	}
}
