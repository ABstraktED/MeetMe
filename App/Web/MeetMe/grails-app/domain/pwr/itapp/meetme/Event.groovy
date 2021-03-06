package pwr.itapp.meetme



import pwr.itapp.meetme.auth.User


class Event {
	
	User user
	Location location
	String title
	String description
	Date date
	Boolean allCanJoin // true = everybody can join the event
	Boolean allCanInvite // true = everybody can invite

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
