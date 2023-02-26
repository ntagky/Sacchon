package gr.codehub.sacchon.app.service;

import gr.codehub.sacchon.app.dto.*;
import gr.codehub.sacchon.app.exception.GlucoseException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface GlucoseService {


    GlucoseDto createGlucose(GlucoseDto glucoseDto);
    List<GlucoseDto> readGlucose();
    List<GlucoseFromPersonDto> readGlucoseByPatientId(long id);
    List<BigDecimal> readDailyAverageGlucoseByPatientIdOnSpecificDates(long id, LocalDate startingDate, LocalDate endingDate);
    GlucoseDto readGlucoseById(long id) throws GlucoseException;
    boolean updateGlucose(GlucoseDto glucoseDto, long id) throws GlucoseException;
    boolean deleteGlucoseById(long id) throws GlucoseException;
    boolean deleteGlucoseByPatientIdAndDate(long patientId, LocalDate date) throws GlucoseException;
    long createGlucoseByPatientId(long id, GlucoseInitiatorDto glucoseInitiatorDto);
    Long findGlucoseIdInSpecificDateByPatientId(long patientId, LocalDate date);
    Long createGlucoseByPatientIdAtDate(long patientId, LocalDate date, GlucoseRecordUpdaterDto glucoseRecordUpdaterDto);

}
