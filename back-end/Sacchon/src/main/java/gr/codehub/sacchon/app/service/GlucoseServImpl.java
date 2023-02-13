package gr.codehub.sacchon.app.service;

import gr.codehub.sacchon.app.dto.GlucoseDto;
import gr.codehub.sacchon.app.dto.PatientDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GlucoseServImpl implements GlucoseService {
    @Override
    public GlucoseDto createGlucose(GlucoseDto glucoseDto) {
        return null;
    }

    @Override
    public List<PatientDto> readGlucose() {
        return null;
    }

    @Override
    public GlucoseDto readGlucoseById(int id) {
        return null;
    }

    @Override
    public boolean updateGlucose(GlucoseDto glucoseDto, int id) {
        return false;
    }

    @Override
    public boolean deleteGlucoseById(int id) {
        return false;
    }
}
