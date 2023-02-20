package gr.codehub.sacchon.app.repository;

import gr.codehub.sacchon.app.SacchonApplication;
import gr.codehub.sacchon.app.model.BloodType;
import gr.codehub.sacchon.app.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Integer> {

    @Query("SELECT COUNT(p) FROM Patient p")
    Long countPatients();

    @Query(value = "SELECT * FROM " + SacchonApplication.SCHEMA + ".PATIENT WHERE id = :patientId", nativeQuery = true)
    Patient DisplayAccountData(@Param("patientId") int patientId);

    @Modifying
    @Query(value = "DELETE FROM " + SacchonApplication.SCHEMA + ".PATIENT WHERE id=:patientId", nativeQuery = true)
    void deletePatientById(@Param("patientId") Long patientId);

    @Query(value = "INSERT INTO " + SacchonApplication.SCHEMA + ".PATIENT (first_name, last_name, password, email, medical_record_number, address, gender, date_of_birth, blood_type, diabetes_type, height, weight) " +
            "VALUES (:firstName, :lastName, :password, :email, :medicalRecordNumber, :address, :gender, :dateOfBirth, :bloodType, :diabetesType, :height, :weight)", nativeQuery = true)
    void registerPatient(@Param("firstName") String firstName, @Param("lastName") String lastName, @Param("password") String password, @Param("email") String email,
                         @Param("medicalRecordNumber") String medicalRecordNumber, @Param("address") String address, @Param("gender") String gender, @Param("dateOfBirth") LocalDate dateOfBirth,
                         @Param("bloodType") BloodType bloodType, @Param("diabetesType") String diabetesType, @Param("height") int height, @Param("weight") double weight);


}
