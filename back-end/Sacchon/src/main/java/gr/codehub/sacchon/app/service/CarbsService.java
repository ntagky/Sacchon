package gr.codehub.sacchon.app.service;

import gr.codehub.sacchon.app.dto.CarbsDto;

import java.util.List;

public interface CarbsService {

    CarbsDto createCarbs(CarbsDto carbsDto);
    List<CarbsDto> readCarbs();
    CarbsDto readCarbsById(int id);
    boolean updateCarbs(CarbsDto carbsDto, int id);
    boolean deleteCarbsById(int id);

}
