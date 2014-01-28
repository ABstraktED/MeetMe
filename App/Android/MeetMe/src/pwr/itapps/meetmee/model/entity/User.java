package pwr.itapps.meetmee.model.entity;

public class User extends Entity {
	private String name;
	private String username;
	private String email;
	private String phone;
	private String status;
	private String password;
	private String imageAddress; // image address on the server
	private boolean isOwner;

	public User(Long id, String name, String username, String email,
			String phone, String status, String imageAddress, boolean isOwner) {
		super(id);
		this.name = name;
		this.username = username;
		this.email = email;
		this.phone = phone;
		this.status = status;
		this.imageAddress = imageAddress;
		this.isOwner = isOwner;
	}

	public User() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String surname) {
		this.username = surname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getImageAddress() {
		return imageAddress;
	}

	public void setImageAddress(String imageAddress) {
		this.imageAddress = imageAddress;
	}

	public boolean isOwner() {
		return isOwner;
	}

	public void setOwner(boolean isOwner) {
		this.isOwner = isOwner;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
