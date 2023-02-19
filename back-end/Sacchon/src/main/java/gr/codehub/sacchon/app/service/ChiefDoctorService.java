package gr.codehub.sacchon.app.service;

import gr.codehub.sacchon.app.dto.ChiefDoctorDto;
import gr.codehub.sacchon.app.exception.ChiefDoctorException;

import java.util.List;

public interface ChiefDoctorService {
    ChiefDoctorDto createChiefDoctor(ChiefDoctorDto chiefDoctor);
    List<ChiefDoctorDto> readChiefDoctor();
    ChiefDoctorDto readChiefDoctor(int id) throws ChiefDoctorException;
    boolean updateChiefDoctor(ChiefDoctorDto chiefDoctor, int id);
    boolean deleteChiefDoctor(int id);
}
