package pwr.itapps.meetmee.model.entity;

import java.util.ArrayList;

public class Invitation extends Entity {

	User user;
	Event event;
	Boolean confirmation;
	Boolean status;

	public Invitation() {
		super();
	}

	public Invitation(User user, Event event, Boolean confirmation,
			Boolean status) {
		super();
		this.user = user;
		this.event = event;
		this.confirmation = confirmation;
		this.status = status;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
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
