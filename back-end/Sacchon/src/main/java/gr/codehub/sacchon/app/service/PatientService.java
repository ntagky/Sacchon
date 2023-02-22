package gr.codehub.sacchon.app.service;

import gr.codehub.sacchon.app.dto.DoctorDto;
import gr.codehub.sacchon.app.dto.PatientDto;
import gr.codehub.sacchon.app.exception.PatientException;
import gr.codehub.sacchon.app.model.Patient;

import java.util.List;

public interface PatientService {


    List<PatientDto> readPatient();
    List<PatientDto> readPatientById(long id);
    long findDoctorIdByPatientId(long id);
    boolean updatePatient(PatientDto patient, long id);
//    boolean deletePatientById(long id);
    long getPatientCount();

    void deletePatientById(long patientId);

    public void registerPatient(PatientDto patientDto);

    void updateDoctorIdFromPatient(long patientId, long doctorId);
}
