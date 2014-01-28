package pwr.itapp.meetme

import pwr.itapp.meetme.auth.User

import com.google.gdata.client.Query
import com.google.gdata.client.contacts.ContactsService
import com.google.gdata.data.contacts.ContactEntry
import com.google.gdata.data.contacts.ContactFeed

class GoogleContactService {

	List<GoogleContact> getContactsFromAccount(userid, password) {
		def results = []

		try{
			ContactsService contactService = new ContactsService("MeetMe");
			contactService.setUserCredentials(userid, password);

			URL feedUrl = new URL("https://www.google.com/m8/feeds/contacts/default/full");
			Query myQuery = new Query(feedUrl);
			myQuery.setMaxResults(100);
			ContactFeed resultFeed = contactService.getFeed(myQuery, ContactFeed.class);

			for (int i = 0; i < resultFeed.getEntries().size(); i++) {
				ContactEntry entry = resultFeed.getEntries().get(i);

				def name = "---"
				def email = "---"
				def phone = "---"

				if(entry.getName() != null)
				{
					def nameObj = entry.getName();
					name = nameObj.fullName.value
				}
				if(entry.getEmailAddresses() != null && entry.getEmailAddresses().size()> 0)
				{
					def emailObj = entry.getEmailAddresses().get(0);
					email = emailObj.address
				}
				if(entry.getPhoneNumbers() != null && entry.getPhoneNumbers().size() >0)
				{
					def phoneObj = entry.getPhoneNumbers().get(0);
					phone = phoneObj.phoneNumber
				}
				if(name != "---" && (phone != "---" || email != "---"))
				{
					results.add(new GoogleContact(email: email, name: name, phone: phone))
				}
			}
		}catch(Exception e){
			System.out.println(e);
		}
		return results
	}

	List<GoogleContact> getContactsFromAccountOnSystem(userid, password, allUsers) {
		def results = []

		try{
			ContactsService contactService = new ContactsService("MeetMe");
			contactService.setUserCredentials(userid, password);

			URL feedUrl = new URL("https://www.google.com/m8/feeds/contacts/default/full");
			Query myQuery = new Query(feedUrl);
			myQuery.setMaxResults(100);
			ContactFeed resultFeed = contactService.getFeed(myQuery, ContactFeed.class);

			for (int i = 0; i < resultFeed.getEntries().size(); i++) {
				ContactEntry entry = resultFeed.getEntries().get(i);
				if(entry.getEmailAddresses() != null && entry.getEmailAddresses().size()> 0 && User.findByEmail(entry.getEmailAddresses().get(0).address) != null){

					def name = "---"
					def email = "---"
					def phone = "---"

					if(entry.getName() != null)
					{
						def nameObj = entry.getName();
						name = nameObj.fullName.value
					}
					if(entry.getEmailAddresses() != null && entry.getEmailAddresses().size()> 0)
					{
						def emailObj = entry.getEmailAddresses().get(0);
						email = emailObj.address
					}
					if(name != "---" && (phone != "---" || email != "---"))
					{
						results.add(new GoogleContact(email: email, name: name, phone: phone))
					}
				}
			}
		}catch(Exception e){
			System.out.println(e);
		}
		return results
	}

}
