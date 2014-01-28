package pwr.itapps.meetmee.model.out.dto;

public class EventsOutDto {

	private String userId;

	public EventsOutDto(String userId) {
		super();
		this.userId = userId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}
