package pwr.itapps.meetmee.model.entity;

public class Event extends Entity {

	private Boolean all_can_invite;
	private Boolean all_can_join;
	private String date;
	private String description;
	private String title;
	private Location location;
	private User user;

	public Event() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Event(Long id, Boolean all_can_invite, Boolean all_can_join,
			String date, String description, String title, Location location,
			User user) {
		super();
		this._ID = id;
		this.all_can_invite = all_can_invite;
		this.all_can_join = all_can_join;
		this.date = date;
		this.description = description;
		this.title = title;
		this.location = location;
		this.user = user;
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

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
