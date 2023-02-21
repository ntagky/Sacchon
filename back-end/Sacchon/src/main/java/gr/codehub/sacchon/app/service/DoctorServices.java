package gr.codehub.sacchon.app.service;

import gr.codehub.sacchon.app.dto.DoctorDto;
import gr.codehub.sacchon.app.exception.DoctorException;

import java.util.List;
public interface DoctorServices {

    DoctorDto createDoctor(DoctorDto doctor);
    List<DoctorDto> readDoctor();
    List<DoctorDto> readDoctorByEmailNativeService(String match);
    DoctorDto readDoctor(long id) throws DoctorException;
    DoctorDto findDoctorById(long id);
    boolean updateDoctor(DoctorDto doctor, long id);
    boolean deleteDoctor(long id);
}
