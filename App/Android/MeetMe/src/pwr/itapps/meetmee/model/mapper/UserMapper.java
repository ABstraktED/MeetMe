package pwr.itapps.meetmee.model.mapper;

import pwr.itapps.meetmee.model.entity.User;
import pwr.itapps.meetmee.model.in.dto.UserInDto;

public class UserMapper extends Mapper<User, UserInDto> {

	@Override
	public User mapDtoToEntity(UserInDto dto) {
		User user = new User(dto.getId(), dto.getName(), dto.getUsername(),
				dto.getEmail(), dto.getPhone(), dto.getStatus(),
				dto.getImageAddress(), false);
		return user;
	}

	@Override
	public UserInDto mapEntityToDto(User entity) {
		
		return null;
	}

}
