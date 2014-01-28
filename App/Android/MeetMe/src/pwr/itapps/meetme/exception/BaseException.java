package pwr.itapps.meetme.exception;

import android.content.Context;

public abstract class BaseException extends Exception {

	private static final long serialVersionUID = 4408675988494685126L;

	public BaseException() {
		super();
	}

	public BaseException(String detailMessage, Throwable throwable) {
		super(detailMessage, throwable);
	}

	public BaseException(String detailMessage) {
		super(detailMessage);
	}

	public BaseException(Throwable throwable) {
		super(throwable);
	}

	public abstract String getMessage(Context context);

	public void reportToServer() {
		// ACRA.getErrorReporter().handleSilentException(this);
	}

}
