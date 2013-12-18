package pwr.itapps.meetme.session;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import pwr.itapps.meetme.data.EventData;
import pwr.itapps.meetme.data.PersonData;
import pwr.itapps.meetme.data.PersonStatus;

public class Session {

	private static Session session;
	private List<EventData> events = ev;

	private Session() {
		super();
	}

	public static Session getInstance() {
		if (session == null)
			session = new Session();
		return session;
	}

	public List<EventData> getEvents() {
		return events;
	}

	// ****************************************************************
	// ********************* testing data ****************************
	// ****************************************************************

	private static ArrayList<EventData> ev = new ArrayList<EventData>() {
		{

			final PersonData person0 = new PersonData("AAAA0", "AaAaAaA0",
					new Date(), "ewfnaiuerfaiwubfeia", PersonStatus.AVAILABLE,
					"http://bestclipartblog.com/clipart-pics/person-clip-art-13.gif");
			final PersonData person1 = new PersonData("AAAA1", "AaAaAaA1",
					new Date(), "ewfnaiuerfaiwubfeia", PersonStatus.AVAILABLE,
					"http://bestclipartblog.com/clipart-pics/person-clip-art-13.gif");
			final PersonData person2 = new PersonData("AAAA2", "AaAaAaA2",
					new Date(), "ewfnaiuerfaiwubfeia", PersonStatus.AVAILABLE,
					"http://bestclipartblog.com/clipart-pics/person-clip-art-13.gif");
			final PersonData person3 = new PersonData("AAAA3", "AaAaAaA3",
					new Date(), "ewfnaiuerfaiwubfeia", PersonStatus.AVAILABLE,
					"http://bestclipartblog.com/clipart-pics/person-clip-art-13.gif");
			final PersonData person4 = new PersonData("AAAA4", "AaAaAaA4",
					new Date(), "ewfnaiuerfaiwubfeia", PersonStatus.AVAILABLE,
					"http://bestclipartblog.com/clipart-pics/person-clip-art-13.gif");
			final PersonData person5 = new PersonData("AAAA5", "AaAaAaA5",
					new Date(), "ewfnaiuerfaiwubfeia", PersonStatus.AVAILABLE,
					"http://bestclipartblog.com/clipart-pics/person-clip-art-13.gif");
			final PersonData person6 = new PersonData("AAAA6", "AaAaAaA6",
					new Date(), "ewfnaiuerfaiwubfeia", PersonStatus.AVAILABLE,
					"http://bestclipartblog.com/clipart-pics/person-clip-art-13.gif");
			final PersonData person7 = new PersonData("AAAA7", "AaAaAaA7",
					new Date(), "ewfnaiuerfaiwubfeia", PersonStatus.AVAILABLE,
					"http://bestclipartblog.com/clipart-pics/person-clip-art-13.gif");

			final ArrayList<PersonData> visitors = new ArrayList<PersonData>() {
				{
					add(person0);
					add(person1);
					add(person2);
					add(person3);
					add(person4);
					add(person5);
					add(person6);
					add(person7);
				}
			};

			final ArrayList<PersonData> organizators = new ArrayList<PersonData>() {
				{
					add(person0);
					add(person1);
					add(person2);
					add(person3);
					add(person4);
					add(person5);
					add(person6);
					add(person7);
				}
			};

			final ArrayList<String> eventGallery = new ArrayList<String>() {
				{
					add("http://newsinfo.iu.edu/pub/libs/images/usr/4984.jpg");
					add("http://4.bp.blogspot.com/_5BhAyLGzLmE/TBENViiN5rI/AAAAAAAAAEk/CJ0mfXP9bvg/s1600/person.jpg");
					add("http://evenesis.com/blog//wp-content/uploads/2012/06/hitech-events.jpg");
				}
			};

			final ArrayList<String> eventGallery1 = new ArrayList<String>() {
				{
					add("http://4.bp.blogspot.com/_5BhAyLGzLmE/TBENViiN5rI/AAAAAAAAAEk/CJ0mfXP9bvg/s1600/person.jpg");
					add("http://newsinfo.iu.edu/pub/libs/images/usr/4984.jpg");
					add("http://evenesis.com/blog//wp-content/uploads/2012/06/hitech-events.jpg");
				}
			};
			final ArrayList<String> eventGallery2 = new ArrayList<String>() {
				{
					add("http://evenesis.com/blog//wp-content/uploads/2012/06/hitech-events.jpg");
					add("http://newsinfo.iu.edu/pub/libs/images/usr/4984.jpg");
					add("http://4.bp.blogspot.com/_5BhAyLGzLmE/TBENViiN5rI/AAAAAAAAAEk/CJ0mfXP9bvg/s1600/person.jpg");
				}
			};
			final EventData ed1 = new EventData("This is first event to meet",
					"Simple description, simple description.", new Date(),
					new Date(), 18) {
				{
					setVisitors(visitors);
					setOrganizators(organizators);
					setEventGallery(eventGallery);
				}
			};
			final EventData ed2 = new EventData(
					"This is second event to meet",
					"Simple description, simple description, simple description, simple description, simple description, simple description, simple description, simple description, simple description, simple description, simple description, simple description, simple description, simple description, simple description, simple description, simple description, simple description.",
					new Date(), new Date(), 18) {
				{
					setVisitors(visitors);
					setOrganizators(organizators);
					setEventGallery(eventGallery1);
				}
			};
			final EventData ed3 = new EventData("This is third event to meet",
					"Simple description, simple description.", new Date(),
					new Date(), 18) {
				{
					setVisitors(visitors);
					setOrganizators(organizators);
					setEventGallery(eventGallery2);
				}
			};
			add(ed1);
			add(ed2);
			add(ed3);
			add(ed1);
			add(ed2);
			add(ed3);
			add(ed1);
			add(ed2);
			add(ed3);
			add(ed1);
			add(ed2);
		}
	};

}
