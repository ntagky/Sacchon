package gr.codehub.sacchon.app.service;

import gr.codehub.sacchon.app.dto.InsightsData;
import gr.codehub.sacchon.app.dto.PastCarbReadingsDto;
import gr.codehub.sacchon.app.dto.PatientDto;
import gr.codehub.sacchon.app.exception.DateValidationException;
import gr.codehub.sacchon.app.exception.PatientException;
import gr.codehub.sacchon.app.exception.RegisterValidationException;
import gr.codehub.sacchon.app.exception.RegistrationException;

import java.time.LocalDate;
import java.util.List;

public interface PatientService {

    List<PastCarbReadingsDto> getPreviousCarbReadingsByPatientIdBetweenDates(long id, LocalDate startingDate, LocalDate endingDate)
        throws PatientException, DateValidationException;

    List<PatientDto> readPatientById(long id) throws PatientException ;

    Long findDoctorIdByPatientId(long id) throws PatientException;

    void updateEmailByPatientId(long id, String email) throws PatientException;

     void deletePatientById(long patientId) throws PatientException;

    long registerPatient(PatientDto patientDto) throws RegistrationException, RegisterValidationException;

    void updateDoctorIdFromPatient(long patientId, long doctorId);

    LocalDate findDateAssignedFromPatientId(long id) throws PatientException;

    void deleteCarbsFromPatientInSpecificDate(long id, LocalDate dateGiven) throws PatientException, DateValidationException;

     void updateFirstNameByPatientId(long id, String firstName) throws PatientException;

     void updateLastNameByPatientId(long id, String lastName) throws PatientException;

     void updateGenderByPatientId(long id, String gender) throws PatientException;

     void updateHeightByPatientId(long id, int height) throws PatientException;
     void updateWeightByPatientId(long id,double weight) throws PatientException;

     void updateMedicalRecordNumberByPatientId(long id, String medicalRecordNumber)
             throws PatientException;

     void updatePhoneNumberByPatientId(long id, String phoneNumber) throws PatientException;

     void updateBirthDateByPatientId(long id, LocalDate birthDate) throws PatientException;

     void updateAddressByPatientId(long id, String address) throws PatientException;

    InsightsData getInsightsData(long id, LocalDate staringDate, LocalDate endingDate);
}
