package pwr.itapps.meetmee.model.out.dto;

public class FriendsOutDto {

	private long userId;
	private int start;
	private int count;

	public FriendsOutDto(long userId) {
		super();
		this.userId = userId;
		start = 0;
		count = 0;
	}

	public FriendsOutDto(long userId, int start, int count) {
		super();
		this.userId = userId;
		this.start = start;
		this.count = count;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

}
