package pwr.itapps.meetmee.model.out.dto;

public class InvitationOutDto {

	Boolean eventId;

	public InvitationOutDto(Boolean eventId) {
		super();
		this.eventId = eventId;
	}

	public Boolean getEventId() {
		return eventId;
	}

	public void setEventId(Boolean eventId) {
		this.eventId = eventId;
	}

}
