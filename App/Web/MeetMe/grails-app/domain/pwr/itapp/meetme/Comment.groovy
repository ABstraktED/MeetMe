package pwr.itapp.meetme

import java.sql.Timestamp

import pwr.itapp.meetme.auth.User

class Comment implements Serializable{

	private static final long serialVersionUID = 1
	
	User user
	Event event
	String content
	Date date
	
	
    static constraints = {
    }
	
	static mapping = {
		version false
		id composite: ['user', 'event', 'date']
	}
}
