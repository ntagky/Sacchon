package gr.codehub.sacchon.app.service;

import gr.codehub.sacchon.app.dto.PastCarbReadingsDto;
import gr.codehub.sacchon.app.dto.PatientDto;

import java.time.LocalDate;
import java.util.List;

public interface PatientService {

//    PatientDto createPatient(PatientDto patient);
    List<PastCarbReadingsDto> getPreviousCarbReadingsByPatientIdBetweenDates(long id, LocalDate startingDate, LocalDate endingDate);
    List<PatientDto> readPatient();
    List<PatientDto> readPatientById(long id); // giati List ???
    long findDoctorIdByPatientId(long id);
    boolean updatePatient(PatientDto patient, long id);

    long getPatientCount();

    void deletePatientById(long patientId);

    long registerPatient(PatientDto patientDto);

    void updateDoctorIdFromPatient(long patientId, long doctorId);
}
