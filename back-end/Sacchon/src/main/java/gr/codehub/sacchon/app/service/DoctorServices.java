package gr.codehub.sacchon.app.service;

import gr.codehub.sacchon.app.dto.DoctorDto;
import gr.codehub.sacchon.app.dto.DoctorNameAndEmailDto;
import gr.codehub.sacchon.app.exception.DoctorException;

import java.util.List;
public interface DoctorServices {

    DoctorDto createDoctor(DoctorDto doctor);
    List<DoctorDto> readDoctor();
    List<DoctorDto> readDoctorByEmailNativeService(String match);
    DoctorDto readDoctor(int id) throws DoctorException;
    DoctorNameAndEmailDto findDoctorNameAndEmailByPatientId(int id);
    boolean updateDoctor(DoctorDto doctor, int id);
    boolean deleteDoctor(int id);
}
