package pwr.itapps.meetmee.model.mapper;

import pwr.itapps.meetmee.model.entity.Location;
import pwr.itapps.meetmee.model.in.dto.LocationInDto;

public class LocationMapper extends Mapper<Location, LocationInDto> {

	@Override
	public Location mapDtoToEntity(LocationInDto dto) {
		Location l = new Location(dto.getId(), dto.getLongitude(),
				dto.getLatitude());
		return l;
	}

	@Override
	public LocationInDto mapEntityToDto(Location entity) {
		// TODO Auto-generated method stub
		return null;
	}

}
