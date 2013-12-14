package pwr.itapp.meetme

import java.sql.Timestamp

import pwr.itapp.meetme.auth.User

class Event {
	
	User user
	Location location
	String title
	String description
	Date date

    static constraints = {
    }
	
	static hasMany = [invitations: Invitation]
	
	static mapping = {
		version false
		invitations cascade: 'all-delete-orphan'
		location cascade: 'all'
		title email: true
	}
}
