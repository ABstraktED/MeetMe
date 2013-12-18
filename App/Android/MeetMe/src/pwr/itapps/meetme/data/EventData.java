package pwr.itapps.meetme.data;

import java.util.Date;
import java.util.List;

public class EventData extends BaseData {

	private String name;
	private String description;
	private Date date;
	private Date alarmDate;
	private List<PersonData> organizators; // first 10 organizators
	private int numberOfVisitors; // number of all visitors jus to display
	private List<PersonData> visitors; // first 10 visotors
	private List<String> eventGallery;

	
	
	public EventData(String name, String description, Date date,
			Date alarmDate, int numberOfVisitors) {
		super();
		this.name = name;
		this.description = description;
		this.date = date;
		this.alarmDate = alarmDate;
		this.numberOfVisitors = numberOfVisitors;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Date getAlarmDate() {
		return alarmDate;
	}

	public void setAlarmDate(Date alarmDate) {
		this.alarmDate = alarmDate;
	}

	public List<PersonData> getOrganizators() {
		return organizators;
	}

	public void setOrganizators(List<PersonData> organizators) {
		this.organizators = organizators;
	}

	public int getNumberOfVisitors() {
		return numberOfVisitors;
	}

	public void setNumberOfVisitors(int numberOfVisitors) {
		this.numberOfVisitors = numberOfVisitors;
	}

	public List<PersonData> getVisitors() {
		return visitors;
	}

	public void setVisitors(List<PersonData> visitors) {
		this.visitors = visitors;
	}

	public List<String> getEventGallery() {
		return eventGallery;
	}

	public void setEventGallery(List<String> eventGallery) {
		this.eventGallery = eventGallery;
	}

}
