package gr.codehub.sacchon.app.service;

import gr.codehub.sacchon.app.dto.CarbsDto;
import gr.codehub.sacchon.app.dto.CarbsFromPersonDto;
import gr.codehub.sacchon.app.exception.CarbsException;

import java.util.List;

public interface CarbsService {

    CarbsDto createCarbsIntake(CarbsDto carbsDto);
    List<CarbsDto> readCarbs();
    CarbsDto readCarbsById(int id) throws CarbsException;
    List<CarbsFromPersonDto> readCarbsByPatientId(int id);
    boolean updateCarbs(CarbsDto carbsDto, int id) throws CarbsException;
    boolean deleteCarbsById(int id) throws CarbsException;

}
