package gr.codehub.sacchon.app.service;

import gr.codehub.sacchon.app.dto.GlucoseDto;
import gr.codehub.sacchon.app.dto.GlucoseFromPersonDto;
import gr.codehub.sacchon.app.exception.GlucoseException;

import java.util.List;

public interface GlucoseService {

    GlucoseDto createGlucose(GlucoseDto glucoseDto);
    List<GlucoseDto> readGlucose();
    List<GlucoseFromPersonDto> readGlucoseByPatientId(int id);
    GlucoseDto readGlucoseById(int id) throws GlucoseException;
    boolean updateGlucose(GlucoseDto glucoseDto, int id) throws GlucoseException;
    boolean deleteGlucoseById(int id) throws GlucoseException;

}
