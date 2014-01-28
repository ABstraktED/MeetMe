package pwr.itapps.meetmee.model.in.dto;

public class InvitationInDto {

	private Long id;
	private UserInDto user;
	private Boolean confirmation;
	private Boolean status;

	public InvitationInDto(Long id, UserInDto user, Boolean confirmation,
			Boolean status) {
		super();
		this.id = id;
		this.user = user;
		this.confirmation = confirmation;
		this.status = status;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public UserInDto getUser() {
		return user;
	}

	public void setUser(UserInDto user) {
		this.user = user;
	}

	public Boolean getConfirmation() {
		return confirmation;
	}

	public void setConfirmation(Boolean confirmation) {
		this.confirmation = confirmation;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

}
