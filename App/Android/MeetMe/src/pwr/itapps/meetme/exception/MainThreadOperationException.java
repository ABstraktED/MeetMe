package pwr.itapps.meetme.exception;

import pwr.itapps.meetme.R;
import android.content.Context;

public class MainThreadOperationException extends BaseException {

	public static final String MESSAGE = "Attempting to run async operations on main thread.";

	private static final long serialVersionUID = 1L;

	public MainThreadOperationException() {
		super(MESSAGE);
	}

	public MainThreadOperationException(String message) {
		super(message);
	}

	@Override
	public String getMessage() {
		return MESSAGE;

	}

	@Override
	public String getMessage(Context context) {
		return context.getString(R.string.MainThreadOperationException);
	}

}
