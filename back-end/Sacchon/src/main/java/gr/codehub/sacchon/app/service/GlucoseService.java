package gr.codehub.sacchon.app.service;

import gr.codehub.sacchon.app.dto.GlucoseDto;
import gr.codehub.sacchon.app.dto.PatientDto;

import java.util.List;

public interface GlucoseService {

    GlucoseDto createGlucose(GlucoseDto glucoseDto);
    List<PatientDto> readGlucose();
    GlucoseDto readGlucoseById(int id);
    boolean updateGlucose(GlucoseDto glucoseDto, int id);
    boolean deleteGlucoseById(int id);

}
