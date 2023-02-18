package gr.codehub.sacchon.app.service;

import gr.codehub.sacchon.app.dto.GlucoseRecordDto;
import gr.codehub.sacchon.app.exception.GlucoseRecordException;

import java.util.List;

public interface GlucoseRecordService {

    GlucoseRecordDto createGlucoseRecord(GlucoseRecordDto glucoseRecordDto);
    List<GlucoseRecordDto> readGlucoseRecord();
    GlucoseRecordDto readGlucoseRecordById(int id) throws GlucoseRecordException;
    boolean updateGlucoseRecord(GlucoseRecordDto glucoseRecordDto, int id) throws GlucoseRecordException;
    boolean deleteGlucoseRecordById(int id) throws GlucoseRecordException;

}
