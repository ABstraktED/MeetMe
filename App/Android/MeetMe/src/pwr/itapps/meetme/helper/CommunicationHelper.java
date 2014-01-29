package pwr.itapps.meetme.helper;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.concurrent.TimeoutException;

import pwr.itapps.meetme.application.MeetMe;
import pwr.itapps.meetme.exception.MainThreadOperationException;
import pwr.itapps.meetmee.model.entity.Event;
import pwr.itapps.meetmee.model.in.dto.EventInDto;
import pwr.itapps.meetmee.model.in.dto.InvitationInDto;
import pwr.itapps.meetmee.model.in.dto.UserInDto;
import pwr.itapps.meetmee.model.out.dto.EventsOutDto;
import pwr.itapps.meetmee.model.out.dto.FriendsOutDto;
import pwr.itapps.meetmee.model.out.dto.InvitationOutDto;
import pwr.itapps.meetmee.model.out.dto.LoginOutDto;
import pwr.itapps.meetmee.model.out.dto.UserOutDto;
import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class CommunicationHelper extends AbstractCommunicationHelper {

	private static final String TAG = "CommunicationHelper";
	private Gson gson;

	public CommunicationHelper(Context context) {
		super(context, MeetMe.SERVICE_SERVER_ADDRESS);
		gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
	}

	public CommunicationHelper(Context context, String serviceAddress) {
		super(context, serviceAddress);
		gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
	}

	public UserInDto logIn(LoginOutDto dto)
			throws MainThreadOperationException, TimeoutException, IOException {
		String request = gson.toJson(dto);
		String response = sendRequest(request, "user/login");
		return gson.fromJson(response, UserInDto.class);
	}

	public UserInDto register(UserOutDto dto)
			throws MainThreadOperationException, TimeoutException, IOException {
		String request = gson.toJson(dto);
		String response = sendRequest(request, "user/register");
		return gson.fromJson(response, UserInDto.class);
	}

	public List<Event> test() throws MainThreadOperationException,
			TimeoutException, IOException {
		String request = gson.toJson("");
		String response = sendRequest("", "event");
		Type collectionType = new TypeToken<List<Event>>() {
		}.getType();
		return gson.fromJson(response, collectionType);
	}

	public List<EventInDto> getEvents(EventsOutDto dto)
			throws MainThreadOperationException, TimeoutException, IOException {
		String request = gson.toJson(dto);
		String response = sendRequest(request, "user/events");
		Type collectionType = new TypeToken<List<EventInDto>>() {
		}.getType();
		return gson.fromJson(response, collectionType);
	}

	public List<InvitationInDto> getInvitations(InvitationOutDto dto)
			throws MainThreadOperationException, TimeoutException, IOException {
		String request = gson.toJson(dto);
		String response = sendRequest(request, "event/invitations");
		Type collectionType = new TypeToken<List<InvitationInDto>>() {
		}.getType();
		return gson.fromJson(response, collectionType);
	}
	
	public List<UserInDto> getFriends(FriendsOutDto dto)
			throws MainThreadOperationException, TimeoutException, IOException {
		String request = gson.toJson(dto);
		String response = sendRequest(request, "user/contacts");
		Type collectionType = new TypeToken<List<Event>>() {
		}.getType();
		return gson.fromJson(response, collectionType);
	}

}
