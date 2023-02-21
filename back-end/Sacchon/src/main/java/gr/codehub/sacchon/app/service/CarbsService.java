package gr.codehub.sacchon.app.service;

import gr.codehub.sacchon.app.dto.CarbsDto;
import gr.codehub.sacchon.app.dto.CarbsFromPersonDto;
import gr.codehub.sacchon.app.exception.CarbsException;

import java.time.LocalDate;
import java.util.List;

public interface CarbsService {

    CarbsDto createCarbsIntake(CarbsDto carbsDto);
    List<CarbsDto> readCarbs();
    CarbsDto readCarbsById(long id) throws CarbsException;
    List<CarbsFromPersonDto> readCarbsByPatientId(long id);
    Integer readAverageCarbsIntakeByPatientIdOnSpecificDates(long id, LocalDate startingDate, LocalDate endingDate);
    boolean updateCarbs(CarbsDto carbsDto, long id) throws CarbsException;
    boolean deleteCarbsById(long id) throws CarbsException;

}
