package gr.codehub.sacchon.app.repository;

import gr.codehub.sacchon.app.SacchonApplication;
import gr.codehub.sacchon.app.model.Patient;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

    @Query("SELECT COUNT(p) FROM Patient p")
    Long countPatients();

    @Query(value = "SELECT * FROM " + SacchonApplication.SCHEMA + ".PATIENT WHERE id = :patientId", nativeQuery = true)
    List<Patient> DisplayAccountData(@Param("patientId") long patientId);

    @Query(value ="SELECT doctor_id FROM " + SacchonApplication.SCHEMA + ".PATIENT WHERE id = :patientId", nativeQuery = true)
    long findDoctorIdByPatientId(@Param("patientId") long id);


//    @Query(value = "INSERT INTO " + SacchonApplication.SCHEMA + ".PATIENT (first_name, last_name, password, email, medical_record_number, address, gender, date_of_birth, blood_type, diabetes_type, height, weight) " +
//            "VALUES (:firstName, :lastName, :password, :email, :medicalRecordNumber, :address, :gender, :dateOfBirth, :bloodType, :diabetesType, :height, :weight)", nativeQuery = true)
//    void registerPatient(@Param("firstName") String firstName, @Param("lastName") String lastName, @Param("password") String password, @Param("email") String email,
//                         @Param("medicalRecordNumber") String medicalRecordNumber, @Param("address") String address, @Param("gender") String gender, @Param("dateOfBirth") LocalDate dateOfBirth,
//                         @Param("bloodType") BloodType bloodType, @Param("diabetesType") DiabetesType diabetesType, @Param("height") int height, @Param("weight") double weight);

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO " + SacchonApplication.SCHEMA + ".PATIENT (first_name, last_name, password, email, medical_record_number, address, gender, date_of_birth, blood_type, diabetes_type, height, weight, signed_date) VALUES (:firstName, :lastName, :password, :email, :medicalRecordNumber, :address, :gender, :dateOfBirth, :bloodType, :diabetesType, :height, :weight, :signedDate)", nativeQuery = true)
    void createPatient(@Param("firstName") String firstName,
                       @Param("lastName") String lastName,
                       @Param("password") String password,
                       @Param("email") String email,
                       @Param("medicalRecordNumber") String medicalRecordNumber,
                       @Param("address") String address,
                       @Param("gender") String gender,
                       @Param("dateOfBirth") LocalDate dateOfBirth,
                       @Param("bloodType") String bloodType,
                       @Param("diabetesType") String diabetesType,
                       @Param("height") int height,
                       @Param("weight") Double weight,
                       @Param("signedDate") LocalDate signedDate);

    @Transactional
    @Modifying
    @Query(value ="UPDATE " + SacchonApplication.SCHEMA + ".PATIENT SET DOCTOR_ID = :doctorId WHERE ID = :patientId", nativeQuery = true)
    void updateDoctorIdFromPatient(@Param("patientId") long patientId, @Param("doctorId") long doctorId);

    @Query(value ="SELECT ID FROM " + SacchonApplication.SCHEMA + ".PATIENT " +
            "WHERE PATIENT.SIGNED_DATE < :endingDate", nativeQuery = true)
    List<Long> readPatientsBySignedDateBefore(
            @Param("endingDate") LocalDate endingDate
    );


    //PATIENT/GLUCOSE/CARBS/CONSULTATION DELETION
    @Transactional
    @Modifying
    @Query(value = "DELETE FROM " + SacchonApplication.SCHEMA + ".PATIENT WHERE id=:patientId", nativeQuery = true)
    void deletePatientById(@Param("patientId") long patientId);


    @Transactional
    @Modifying
    @Query(value = "DELETE FROM " + SacchonApplication.SCHEMA + ".carbs WHERE patient_id=:patientId", nativeQuery = true)
    void deleteCarbsByPatientId(@Param("patientId") long patientId);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM " + SacchonApplication.SCHEMA + ".glucose WHERE patient_id=:patientId", nativeQuery = true)
    void deleteGlucoseByPatientId(@Param("patientId") long patientId);
    @Transactional
    @Modifying
    @Query(value = "DELETE FROM " + SacchonApplication.SCHEMA + ".consultation WHERE patient_id=:patientId", nativeQuery = true)
    void deleteConsultationsByPatientId(@Param("patientId") long patientId);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM " + SacchonApplication.SCHEMA + ".Glucose_Record WHERE glucose_id IN (SELECT id FROM " + SacchonApplication.SCHEMA + ".Glucose WHERE patient_id=:patientId)", nativeQuery = true)
    void deleteGlucoseRecordByPatientId(@Param("patientId") long patientId);

    @Transactional
    @Modifying
    @Query(value = "UPDATE " + SacchonApplication.SCHEMA + ".consultation SET medications = NULL WHERE patient_id = :patientId", nativeQuery = true)
    void deleteMedicationsByPatientId(@Param("patientId") long patientId);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM DBO.CONSULTATION_MEDICATIONS WHERE CONSULTATION_ID IN (SELECT ID FROM " +SacchonApplication.SCHEMA + ".CONSULTATION WHERE patient_id=:patientId)", nativeQuery = true)
    void deleteDboConsultationMedications(@Param("patientId") long patientId);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM DBO.PATIENT_ALLERGIES  WHERE patient_id=:patientId", nativeQuery = true)
    void deleteDboPatientAllergies(@Param("patientId") long patientId);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM DBO.PATIENT_CONDITIONS  WHERE patient_id=:patientId", nativeQuery = true)
    void deleteDboPatientConditions(@Param("patientId") long patientId);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM DBO.PATIENT_CONDITIONS  WHERE patient_id=:patientId", nativeQuery = true)
    void deleteDboPatientMedications(@Param("patientId") long patientId);






    // query for table Patient (set doctor_id to null when doctor is deleted)
    @Transactional
    @Modifying
    @Query(value = "UPDATE " + SacchonApplication.SCHEMA + ".PATIENT SET doctor_id = null WHERE doctor_id = :doctorId" , nativeQuery = true)
    void makeDoctorIdNullOnDoctorDelete(@Param("doctorId") long doctorId);
}
