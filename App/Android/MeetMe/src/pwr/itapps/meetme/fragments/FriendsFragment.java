package pwr.itapps.meetme.fragments;

import java.nio.channels.AsynchronousCloseException;
import java.util.ArrayList;
import java.util.List;

import pwr.itapps.meetme.R;
import pwr.itapps.meetme.adapter.FriendsAdapter;
import pwr.itapps.meetme.helper.CommunicationHelper;
import pwr.itapps.meetme.helper.SharedPreferencesHelper;
import pwr.itapps.meetmee.model.UserModel;
import pwr.itapps.meetmee.model.entity.User;
import pwr.itapps.meetmee.model.in.dto.UserInDto;
import pwr.itapps.meetmee.model.mapper.UserMapper;
import pwr.itapps.meetmee.model.out.dto.FriendsOutDto;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

public class FriendsFragment extends ListFragment {

	private GetUsersTask mAuthTask = null;
	private List<User> users = null;

	private View mLoginFormView;
	private View mLoginStatusView;
	private CheckBox mRememberCb;
	private TextView mLoginStatusMessageView;
	private UserModel userModel;
	private FriendsAdapter adapter;
	private SharedPreferencesHelper prefs;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_friends_list, container,
				false);
		// setupIds(view);
		mLoginFormView = view.findViewById(R.id.login_form);
		mLoginStatusView = view.findViewById(R.id.login_status);
		mLoginStatusMessageView = (TextView) view
				.findViewById(R.id.login_status_message);
		prefs = SharedPreferencesHelper.getInstance();
		userModel = new UserModel(getActivity());
		users = userModel.fetchAllUsers();
		adapter = new FriendsAdapter(getActivity(), users);
		setListAdapter(adapter);
		return view;
	}

	@Override
	public void onResume() {
		boolean synch = prefs.getBoolean(
				SharedPreferencesHelper.SYNCHRONIZE_USERS,
				SharedPreferencesHelper.SYNCHRONIZE_USERS_DEF, getActivity());
		if (synch) {
			runDialog();
		}
		super.onResume();
	}

	private void runDialog() {
		Builder builder = new AlertDialog.Builder(getActivity());
		builder.setMessage("Synchronize phone contacts?");
		builder.setCancelable(true);
		builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				new AsyncTask<Void, Void, Void>() {
					@Override
					protected Void doInBackground(Void... params) {
						List<User> u = fetchContacts();
						userModel.saveAction(u);
						u = userModel.fetchAllUsers();
						users.addAll(u);
						prefs.putBoolean(
								SharedPreferencesHelper.SYNCHRONIZE_USERS,
								false, getActivity());
						return null;
					}

					protected void onPostExecute(Void result) {
						adapter.notifyDataSetChanged();
					};

				}.execute((Void[]) null);

			}
		});
		builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				prefs.putBoolean(SharedPreferencesHelper.SYNCHRONIZE_USERS,
						false, getActivity());
			}
		});
		AlertDialog dialog = builder.create();
		dialog.show();
	}

	public void downloadContacs() {
		mLoginStatusMessageView.setText(R.string.login_progress_down_contacts);
		showProgress(true);
		mAuthTask = new GetUsersTask(getActivity());
		mAuthTask.execute((Void[]) null);
	}

	public class GetUsersTask extends AsyncTask<Void, Void, Boolean> {

		UserModel userModel;
		CommunicationHelper comm;

		public GetUsersTask(Context context) {
			super();
			comm = new CommunicationHelper(context);
			userModel = new UserModel(context);
		}

		@Override
		protected Boolean doInBackground(Void... params) {
			Boolean result = null;
			FriendsOutDto dto = new FriendsOutDto(userModel.fetchOwner()
					.getId());
			try {
				List<UserInDto> users = comm.getFriends(dto);
				if (users != null) {
					UserMapper mapper = new UserMapper();
					userModel = new UserModel(getActivity());
					userModel.saveAction(mapper
							.mapDtoToEntity((ArrayList) users));
					return true;
				} else {
					return false;
				}
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}

		@Override
		protected void onPostExecute(final Boolean success) {
			mAuthTask = null;
			showProgress(false);

			if (success == null) {
				Toast.makeText(getActivity(), "Problems with communication",
						Toast.LENGTH_SHORT).show();
			} else if (success) {
				List<User> users = userModel.fetchAllUsers();
				FriendsFragment.this.users.clear();
				FriendsFragment.this.users.addAll(users);
				adapter = new FriendsAdapter(getActivity(), FriendsFragment.this.users);
				setListAdapter(adapter);
			} else {
				Toast.makeText(getActivity(), "U may have no friends... ?",
						Toast.LENGTH_SHORT).show();
			}
		}

		@Override
		protected void onCancelled() {
			mAuthTask = null;
			showProgress(false);
		}
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
	private void showProgress(final boolean show) {
		// for very easy animations. If available, use these APIs to fade-in
		// the progress spinner.
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
			int shortAnimTime = getResources().getInteger(
					android.R.integer.config_shortAnimTime);

			mLoginStatusView.setVisibility(View.VISIBLE);
			mLoginStatusView.animate().setDuration(shortAnimTime)
					.alpha(show ? 1 : 0)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							mLoginStatusView.setVisibility(show ? View.VISIBLE
									: View.GONE);
						}
					});

			mLoginFormView.setVisibility(View.VISIBLE);
			mLoginFormView.animate().setDuration(shortAnimTime)
					.alpha(show ? 0 : 1)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							mLoginFormView.setVisibility(show ? View.GONE
									: View.VISIBLE);
						}
					});
		} else {
			// The ViewPropertyAnimator APIs are not available, so simply show
			// and hide the relevant UI components.
			mLoginStatusView.setVisibility(show ? View.VISIBLE : View.GONE);
			mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
		}
	}

	public List<User> fetchContacts() {
		List<User> users = new ArrayList<User>();
		String phoneNumber = null;
		String email = null;
		Uri CONTENT_URI = ContactsContract.Contacts.CONTENT_URI;
		String _ID = ContactsContract.Contacts._ID;
		String DISPLAY_NAME = ContactsContract.Contacts.DISPLAY_NAME;
		String HAS_PHONE_NUMBER = ContactsContract.Contacts.HAS_PHONE_NUMBER;
		Uri PhoneCONTENT_URI = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
		String Phone_CONTACT_ID = ContactsContract.CommonDataKinds.Phone.CONTACT_ID;
		String NUMBER = ContactsContract.CommonDataKinds.Phone.NUMBER;
		Uri EmailCONTENT_URI = ContactsContract.CommonDataKinds.Email.CONTENT_URI;
		String EmailCONTACT_ID = ContactsContract.CommonDataKinds.Email.CONTACT_ID;
		String DATA = ContactsContract.CommonDataKinds.Email.DATA;
		StringBuffer output = new StringBuffer();
		ContentResolver contentResolver = getActivity().getContentResolver();
		Cursor cursor = contentResolver.query(CONTENT_URI, null, null, null,
				null);
		if (cursor.getCount() > 0) {
			while (cursor.moveToNext()) {
				User user = new User();
				String contact_id = cursor
						.getString(cursor.getColumnIndex(_ID));
				String name = cursor.getString(cursor
						.getColumnIndex(DISPLAY_NAME));
				int hasPhoneNumber = Integer.parseInt(cursor.getString(cursor
						.getColumnIndex(HAS_PHONE_NUMBER)));
				if (hasPhoneNumber > 0) {
					user.setName(name);
					user.setUsername(name);
					output.append("\n First Name:" + name);
					Cursor phoneCursor = contentResolver.query(
							PhoneCONTENT_URI, null, Phone_CONTACT_ID + " = ?",
							new String[] { contact_id }, null);
					while (phoneCursor.moveToNext()) {
						phoneNumber = phoneCursor.getString(phoneCursor
								.getColumnIndex(NUMBER));
						output.append("\n Phone number:" + phoneNumber);
						user.setPhone(phoneNumber);
					}
					phoneCursor.close();
					// Query and loop for every email of the contact
					Cursor emailCursor = contentResolver.query(
							EmailCONTENT_URI, null, EmailCONTACT_ID + " = ?",
							new String[] { contact_id }, null);
					while (emailCursor.moveToNext()) {
						email = emailCursor.getString(emailCursor
								.getColumnIndex(DATA));
						output.append("\nEmail:" + email);
						user.setEmail(email);
					}
					emailCursor.close();
				}
				if (user.getName() != null && user.getName().length() > 1) {
					if ((user.getPhone() != null && user.getPhone().length() > 1)
							|| (user.getEmail() != null && user.getEmail()
									.length() > 1)) {
						users.add(user);
					}
				}
				output.append("\n");
			}
			// outputText.setText(output);
		}
		return users;
	}
}
