package pwr.itapps.meetme.data;

public class VisitorData extends BaseData {

	private long eventDataId;
	private long personDataId;
	private VisitorStatus status;

	public long getEventDataId() {
		return eventDataId;
	}

	public void setEventDataId(long eventDataId) {
		this.eventDataId = eventDataId;
	}

	public long getPersonDataId() {
		return personDataId;
	}

	public void setPersonDataId(long personDataId) {
		this.personDataId = personDataId;
	}

	public VisitorStatus getStatus() {
		return status;
	}

	public void setStatus(VisitorStatus status) {
		this.status = status;
	}

}
