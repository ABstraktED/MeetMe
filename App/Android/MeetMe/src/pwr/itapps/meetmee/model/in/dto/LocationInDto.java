package pwr.itapps.meetmee.model.in.dto;

public class LocationInDto {

	private Long id;
	private double longitude;
	private double latitude;

	public LocationInDto(Long id, double longitude, double latitude) {
		super();
		this.id = id;
		this.longitude = longitude;
		this.latitude = latitude;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

}
