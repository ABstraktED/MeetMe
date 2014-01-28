package pwr.itapps.meetmee.model.out.dto;

public class EventsOutDto {

	private String userId;
	private String name;
	private String date;
	private String time;
	private String address;
	private String decritption;

	public EventsOutDto(String userId, String name, String date, String time,
			String address, String decritption) {
		super();
		this.userId = userId;
		this.name = name;
		this.date = date;
		this.time = time;
		this.address = address;
		this.decritption = decritption;
	}




	public String getUserId() {
		return userId;
	}

	public String getName() {
		return name;
	}




	public void setName(String name) {
		this.name = name;
	}




	public String getDate() {
		return date;
	}




	public void setDate(String date) {
		this.date = date;
	}




	public String getTime() {
		return time;
	}




	public void setTime(String time) {
		this.time = time;
	}




	public String getAddress() {
		return address;
	}




	public void setAddress(String address) {
		this.address = address;
	}




	public String getDecritption() {
		return decritption;
	}




	public void setDecritption(String decritption) {
		this.decritption = decritption;
	}




	public void setUserId(String userId) {
		this.userId = userId;
	}

}
