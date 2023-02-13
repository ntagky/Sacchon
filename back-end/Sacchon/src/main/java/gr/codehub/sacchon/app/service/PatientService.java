package gr.codehub.sacchon.app.service;

import gr.codehub.sacchon.app.dto.PatientDto;
import gr.codehub.sacchon.app.exception.PatientException;

import java.util.List;

public interface PatientService {

    PatientDto createPatient(PatientDto patient);
    List<PatientDto> readPatient();
    PatientDto readPatientById(int id) throws PatientException;
    boolean updatePatient(PatientDto patient, int id);
    boolean deletePatientById(int id);

}
