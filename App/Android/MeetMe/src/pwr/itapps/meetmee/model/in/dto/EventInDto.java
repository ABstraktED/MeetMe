package pwr.itapps.meetmee.model.in.dto;

public class EventInDto {

	private Long id;
	private Boolean all_can_invite;
	private Boolean all_can_join;
	private String date;
	private String description;
	private String title;
	private LocationInDto LocationInDto; // tego na razie nie bedzie, bo i tak
											// nie
	// zdarze wyswietlac na mapach nic
	private UserInDto user;

	public EventInDto(Long id, Boolean all_can_invite, Boolean all_can_join,
			String date, String description, String title,
			LocationInDto locationInDto, UserInDto userInDto) {
		super();
		this.id = id;
		this.all_can_invite = all_can_invite;
		this.all_can_join = all_can_join;
		this.date = date;
		this.description = description;
		this.title = title;
		this.LocationInDto = locationInDto;
		user = userInDto;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Boolean getAll_can_invite() {
		return all_can_invite;
	}

	public void setAll_can_invite(Boolean all_can_invite) {
		this.all_can_invite = all_can_invite;
	}

	public Boolean getAll_can_join() {
		return all_can_join;
	}

	public void setAll_can_join(Boolean all_can_join) {
		this.all_can_join = all_can_join;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public LocationInDto getLocationInDto() {
		return LocationInDto;
	}

	public void setLocationInDto(LocationInDto locationInDto) {
		LocationInDto = locationInDto;
	}

	public UserInDto getUser() {
		return user;
	}

	public void setUser(UserInDto user) {
		this.user = user;
	}

}
