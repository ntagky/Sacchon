package gr.codehub.sacchon.app.service;

import gr.codehub.sacchon.app.dto.*;
import gr.codehub.sacchon.app.exception.GlucoseRecordException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface GlucoseRecordService {

    long createGlucoseRecordForPatientOnSpecificDate(long patientId, LocalDate localDate, GlucoseRecordUpdaterDto glucoseRecordUpdaterDto);
    List<GlucoseRecordDto> readGlucoseRecord();
    GlucoseRecordDto readGlucoseRecordById(long id) throws GlucoseRecordException;
    BigDecimal readAverageDailyGlucoseByGlucoseId(long id);
    boolean updateGlucoseRecord(GlucoseRecordDto glucoseRecordDto, long id) throws GlucoseRecordException;
    boolean deleteGlucoseRecordById(long id) throws GlucoseRecordException;
    boolean updateRecordById(long id, GlucoseRecordUpdaterDto glucoseRecordUpdaterDto);
    public List<PastGlucoseMeasurementDto> getGlucoseReadingsBetweenDatesByPatientId(long patientId, LocalDate startingDate, LocalDate endingDate);
    Integer readGlucoseRecordCountByGlucoseId(Long glucoseId);
    List<GlucoseRecordFromDayDto> readGlucoseRecordByGlucoseId(long glucoseId);
    InsightsGlucoseRecordsData readGlucoseRecordByGlucoseIdForInsights(long glucoseId);
}
