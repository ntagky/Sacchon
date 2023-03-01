package gr.codehub.sacchon.app.service;

import gr.codehub.sacchon.app.dto.InsightsData;
import gr.codehub.sacchon.app.dto.PastCarbReadingsDto;
import gr.codehub.sacchon.app.dto.PatientDto;

import java.time.LocalDate;
import java.util.List;

public interface PatientService {

//    PatientDto createPatient(PatientDto patient);
    List<PastCarbReadingsDto> getPreviousCarbReadingsByPatientIdBetweenDates(long id, LocalDate startingDate, LocalDate endingDate);

    List<PatientDto> readPatientById(long id);
    Long findDoctorIdByPatientId(long id);
    boolean updatePatient(PatientDto patient, long id);

    void updateEmailByPatientId(long id, String email);

    void deletePatientById(long patientId);

    long registerPatient(PatientDto patientDto);

    void updateDoctorIdFromPatient(long patientId, long doctorId);

    LocalDate findDateAssignedFromPatientId(long id);

    void deleteCarbsFromPatientInSpecificDate(long id, LocalDate dateGiven);

     void updateFirstNameByPatientId(long id, String firstName);

     void updateLastNameByPatientId(long id, String lastName);

     void updateGenderByPatientId(long id, String gender);

     void updateHeightByPatientId(long id, int height);
     void updateWeightByPatientId(long id,double weight);

     void updateMedicalRecordNumberByPatientId(long id, String medicalRecordNumber);

     void updatePhoneNumberByPatientId(long id, String phoneNumber);

     void updateBirthDateByPatientId(long id, LocalDate birthDate);

     void updateAddressByPatientId(long id, String address);

    InsightsData getInsightsData(long id, LocalDate staringDate, LocalDate endingDate);
}
