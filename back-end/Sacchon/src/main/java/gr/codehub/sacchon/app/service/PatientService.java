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

    public void updateEmailByPatientId(long id, String email);

    long getPatientCount();

    void deletePatientById(long patientId);

    long registerPatient(PatientDto patientDto);

    void updateDoctorIdFromPatient(long patientId, long doctorId);

    LocalDate findDateAssignedFromPatientId(long id);

    void deleteCarbsFromPatientInSpecificDate(long id, LocalDate dateGiven);

    public void updateFirstNameByPatientId(long id, String firstName);

    public void updateLastNameByPatientId(long id, String lastName);

    public void updateGenderByPatientId(long id, String gender);

    public void updateHeightByPatientId(long id, int height);
    public void updateWeightByPatientId(long id,double weight);

    public void updateMedicalRecordNumberByPatientId(long id, String medicalRecordNumber);

    public void updatePhoneNumberByPatientId(long id, String phoneNumber);

    public void updateBirthDateByPatientId(long id, LocalDate birthDate);

    public void updateAddressByPatientId(long id, String address);
}
