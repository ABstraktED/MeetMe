package pwr.itapps.meetmee.model.mapper;

import pwr.itapps.meetmee.model.EventModel;
import pwr.itapps.meetmee.model.entity.Event;
import pwr.itapps.meetmee.model.entity.Invitation;
import pwr.itapps.meetmee.model.entity.User;
import pwr.itapps.meetmee.model.in.dto.InvitationInDto;
import android.content.Context;

public class InvitationMapper extends Mapper<Invitation, InvitationInDto> {

	UserMapper userMapper;
	EventModel eventModel;
	private Event e;

	public InvitationMapper(Context context, Event e) {
		super();
		this.e = e;
		eventModel = new EventModel(context);
		userMapper = new UserMapper();
	}

	@Override
	public Invitation mapDtoToEntity(InvitationInDto dto) {
		User u = null;
		if (dto.getUser() != null) {
			u = userMapper.mapDtoToEntity(dto.getUser());
		}
		Invitation i = new Invitation(u, e, dto.getConfirmation(),
				dto.getStatus());
		return i;
	}

	@Override
	public InvitationInDto mapEntityToDto(Invitation entity) {
		// TODO Auto-generated method stub
		return null;
	}

}
