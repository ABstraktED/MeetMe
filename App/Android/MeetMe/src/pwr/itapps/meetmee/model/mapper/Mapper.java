package pwr.itapps.meetmee.model.mapper;

import java.util.ArrayList;

public abstract class Mapper<Entit, Dto> {

	public abstract Entit mapDtoToEntity(Dto dto);

	public ArrayList<Entit> mapDtoToEntity(ArrayList<Dto> input) {
		ArrayList<Entit> result = new ArrayList<Entit>();
		if (input != null) {
			for (Dto dto : input) {
				result.add(mapDtoToEntity(dto));
			}
		}
		return result;
	}

	public abstract Dto mapEntityToDto(Entit entity);

	public ArrayList<Dto> mapEntityToDto(ArrayList<Entit> input) {
		ArrayList<Dto> result = new ArrayList<Dto>();
		for (Entit entity : input) {
			result.add(mapEntityToDto(entity));
		}
		return result;
	}
}
