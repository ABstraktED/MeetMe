package pwr.itapps.meetmee.model.mapper;

import pwr.itapps.meetmee.model.entity.Event;
import pwr.itapps.meetmee.model.entity.Location;
import pwr.itapps.meetmee.model.entity.User;
import pwr.itapps.meetmee.model.in.dto.EventInDto;

public class EventMapper extends Mapper<Event, EventInDto> {

	UserMapper usermapper;
	LocationMapper locationMapper;

	public EventMapper() {
		super();
		usermapper = new UserMapper();
		locationMapper = new LocationMapper();
	}

	@Override
	public Event mapDtoToEntity(EventInDto dto) {
		User u = null;
		if (dto.getUser() != null)
			u = usermapper.mapDtoToEntity(dto.getUser());
		Location loc = null;
		if (dto.getLocationInDto() != null)
			loc = locationMapper.mapDtoToEntity(dto.getLocationInDto());
		Event e = new Event(dto.getId(),
				dto.getAll_can_invite().equals("true"), dto.getAll_can_join()
						.equals("true"), dto.getDate(), dto.getDescription(),
				dto.getDate(), loc, u);
		return e;
	}

	@Override
	public EventInDto mapEntityToDto(Event entity) {

		return null;
	}

}
