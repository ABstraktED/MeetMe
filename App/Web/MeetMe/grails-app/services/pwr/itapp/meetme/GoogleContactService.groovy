package pwr.itapp.meetme

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.CountDownLatch;

import com.google.gdata.client.contacts.ContactsService;
import com.google.gdata.data.Link;
import com.google.gdata.data.PlainTextConstruct;
import com.google.gdata.data.contacts.ContactEntry;
import com.google.gdata.data.contacts.ContactFeed;
import com.google.gdata.data.contacts.GroupMembershipInfo;
import com.google.gdata.data.contacts.Nickname;
import com.google.gdata.data.contacts.ShortName;
import com.google.gdata.data.extensions.Email;
import com.google.gdata.data.extensions.ExtendedProperty;
import com.google.gdata.data.extensions.FamilyName;
import com.google.gdata.data.extensions.FullName;
import com.google.gdata.data.extensions.GivenName;
import com.google.gdata.data.extensions.Im;
import com.google.gdata.data.extensions.Name;
import com.google.gdata.data.extensions.PhoneNumber;
import com.google.gdata.util.ServiceException;

class GoogleContactService {

	List<GoogleContact> getContactsFromAccount(userid, password) {
		def results = []

		try{
			ContactsService contactService = new ContactsService("RupalMindfire-AddressApp");
			contactService.setUserCredentials(userid, password);

			URL feedUrl = new URL("https://www.google.com/m8/feeds/contacts/default/full");
			ContactFeed resultFeed = contactService.getFeed(feedUrl, ContactFeed.class);
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

}
