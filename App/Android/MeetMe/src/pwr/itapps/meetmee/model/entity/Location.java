package pwr.itapps.meetmee.model.entity;

public class Location extends Entity {

	private double longitude;
	private double latitude;

	public Location() {
		super();
	}

	public Location(Long id, double longitude, double latitude) {
		super();
		this._ID = id;
		this.longitude = longitude;
		this.latitude = latitude;
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
