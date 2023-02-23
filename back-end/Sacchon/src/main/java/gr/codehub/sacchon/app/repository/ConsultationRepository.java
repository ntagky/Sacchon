package gr.codehub.sacchon.app.repository;
import gr.codehub.sacchon.app.SacchonApplication;
import gr.codehub.sacchon.app.model.Consultation;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ConsultationRepository extends JpaRepository<Consultation, Long> {

    @Query(value = "SELECT * FROM " + SacchonApplication.SCHEMA + ".CONSULTATION WHERE CONSULTATION.PATIENT_ID = :patientId", nativeQuery = true)
    List<Consultation> findConsultationByPatientId(@Param("patientId") long patientId);

    @Query(value = "SELECT * FROM " + SacchonApplication.SCHEMA + ".CONSULTATION WHERE CONSULTATION.PATIENT_ID = :patientId", nativeQuery = true)
    List<Consultation> findConsultationInfoByPatientId(@Param("patientId") long patientId);

    @Query(value = "SELECT PATIENT_ID FROM " + SacchonApplication.SCHEMA + ".CONSULTATION " + " " +
            "WHERE CONSULTATION.DATE_CREATED < :dateBefore",
            nativeQuery = true)
    List<Long> findPatientsIdWaitingForConsultation(
            @Param("dateBefore") LocalDate startingDate
    );

    @Query(value = "SELECT PATIENT_ID FROM " + SacchonApplication.SCHEMA + ".CONSULTATION " + " " +
            "WHERE CONSULTATION.DATE_CREATED >= :startingDate AND CONSULTATION.DATE_CREATED <= :endingDate",
            nativeQuery = true)
    List<Long> findPatientsIdWithConsultationWithinRange(
            @Param("startingDate") LocalDate startingDate,
            @Param("endingDate") LocalDate endingDate
    );

    @Query(value = "SELECT DOCTOR_ID FROM " + SacchonApplication.SCHEMA + ".CONSULTATION " + " " +
            "WHERE CONSULTATION.DATE_CREATED >= :startingDate AND CONSULTATION.DATE_CREATED <= :endingDate",
            nativeQuery = true)
    List<Long> findDoctorsIdWithConsultationWithinRange(
            @Param("startingDate") LocalDate startingDate,
            @Param("endingDate") LocalDate endingDate
    );

    @Query(value = "SELECT doctor_first_name,doctor_last_name,doctor_email,date_created,details FROM " + SacchonApplication.SCHEMA + ".CONSULTATION WHERE CONSULTATION.PATIENT_ID = :patientId",
            nativeQuery = true)
    List<Object[]> findAllConsultationsReceivedForPatient(@Param("patientId") long patientId);

    @Query(value = "SELECT patient_id FROM " + SacchonApplication.SCHEMA + ".CONSULTATION WHERE date_created >= :dateGiven", nativeQuery = true)
    List<Long> findPatientWithActiveConsultation(@Param("dateGiven") LocalDate dateGiven);

    // query for table Consultation (set doctor_id to null when doctor is deleted)
    @Transactional
    @Modifying
    @Query(value = "UPDATE " + SacchonApplication.SCHEMA + ".CONSULTATION SET doctor_id = null WHERE doctor_id = :doctorId" , nativeQuery = true)
    void makeDoctorIdNullOnDoctorDelete(@Param("doctorId") long doctorId);

    // Queries for Use Case: Doctor modifies a consultation to a patient
    // query: deletes medications from consultation_medications table (to re-write at modification)
    @Transactional
    @Modifying
    @Query(value = "DELETE FROM DBO.CONSULTATION_MEDICATIONS WHERE consultation_id = :consultationId", nativeQuery = true)
    void deleteMedicationsByConsultationId(@Param("consultationId") long consultationId);

    // query: deletes medications from consultation_medications table (to re-write at modification)
    @Transactional
    @Modifying
    @Query(value = "INSERT INTO DBO.CONSULTATION_MEDICATIONS(consultation_id, medications) VALUES (:consultationId, :medications)", nativeQuery = true)
    void addMedicationsByConsultationId(@Param("consultationId") long consultationId,
                                        @Param("medications") List<String> medications);

    // query: doctor modifies a consultation to a patient
    @Transactional
    @Modifying
    @Query(value = "UPDATE " + SacchonApplication.SCHEMA + ".CONSULTATION SET details = :details, " +
            "seen_consultation = :seenConsultation WHERE id = :consultationId", nativeQuery = true)
    void updateConsultationFromDoctorByPatientId(@Param("consultationId") long consultationId,
                                                 @Param("details") String details,
                                                 @Param("seenConsultation") boolean seenConsultation);
}
