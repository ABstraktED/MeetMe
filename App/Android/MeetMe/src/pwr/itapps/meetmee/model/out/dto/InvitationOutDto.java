package pwr.itapps.meetmee.model.out.dto;

public class InvitationOutDto {

	Long eventId;

	public InvitationOutDto(Long eventId) {
		super();
		this.eventId = eventId;
	}

	public Long getEventId() {
		return eventId;
	}

	public void setEventId(Long eventId) {
		this.eventId = eventId;
	}

}
