package pwr.itapp.meetme

import pwr.itapp.meetme.auth.User;

class UserService {

	boolean  clearInactiveUsersWithNoInvitations() {
		
		def result = true;
		try {
			def users = User.findAllByStatus(false)
			for (int i = 0; i < users.size() ; i++) {
				def user = users.get(i);
				
				def usersInvitations = Invitation.getByUser(user);
				
				if(usersInvitations.size() == 0)
				{
					println "delete user " + user.email
					user.delete();
				}
				
			}
		}
		catch(Exception ex) {
			result = false;
		}

		return result;
	}
	
	boolean clearExpiredInvitations() {
		Date currentDate = new Date();
		def daysToWaitBeforeRemovingInvitation = 5;
		Calendar cal = Calendar.getInstance();
		cal.setTime(currentDate);
		cal.add(Calendar.DAY_OF_MONTH, -1*daysToWaitBeforeRemovingInvitation);
		
		def result = true;
		try {
			def invitations = Invitation.getAll()
			for (int i = 0; i < invitations.size() ; i++) {
				def item = invitations.get(i);
				if(!item.user.status && item.event.date.before(cal.getTime()))
				{
					println "delete invitation " + item.event.date + " for " + item.user.email + " with status = " + item.user.status
					item.delete();
				} 
				
			}
		}
		catch(Exception ex) {
			result = false;
		}

		return result;
	}
}
