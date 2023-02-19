package gr.codehub.sacchon.app.service;

import gr.codehub.sacchon.app.dto.GlucoseRecordDto;
import gr.codehub.sacchon.app.exception.GlucoseRecordException;

import java.math.BigDecimal;
import java.util.List;

public interface GlucoseRecordService {

    GlucoseRecordDto createGlucoseRecord(GlucoseRecordDto glucoseRecordDto);
    List<GlucoseRecordDto> readGlucoseRecord();
    GlucoseRecordDto readGlucoseRecordById(int id) throws GlucoseRecordException;
    BigDecimal readAverageDailyGlucoseByGlucoseId(int id);
    boolean updateGlucoseRecord(GlucoseRecordDto glucoseRecordDto, int id) throws GlucoseRecordException;
    boolean deleteGlucoseRecordById(int id) throws GlucoseRecordException;

}
