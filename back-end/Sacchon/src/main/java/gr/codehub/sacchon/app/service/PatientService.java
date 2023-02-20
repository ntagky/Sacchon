package gr.codehub.sacchon.app.service;

import gr.codehub.sacchon.app.dto.PatientDto;
import gr.codehub.sacchon.app.exception.PatientException;
import gr.codehub.sacchon.app.model.Patient;

import java.util.List;

public interface PatientService {

    PatientDto createPatient(PatientDto patient);
    List<PatientDto> readPatient();
    PatientDto readPatientById(int id) throws PatientException;
    boolean updatePatient(PatientDto patient, int id);
    boolean deletePatientById(int id) ;
    long getPatientCount();
    public Patient getPatientById(int patientId);

    void deletePatientById(Long patientId);

    public void registerPatient(PatientDto patientDto);

}
