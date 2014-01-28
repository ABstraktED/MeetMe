package pwr.itapps.meetmee.model.entity;

public class Entity {

	public Long _ID = null;

	public Entity() {
		super();
	}

	public Entity(Long id) {
		super();
		this._ID = id;
	}

	public Long getId() {
		return _ID;
	}

	public void setId(Long id) {
		this._ID = id;
	}

}
