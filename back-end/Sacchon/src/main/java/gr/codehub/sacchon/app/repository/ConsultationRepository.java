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

    @Query(value = "SELECT id, doctor_first_name,doctor_last_name,doctor_email,date_created,details,seen_consultation FROM " + SacchonApplication.SCHEMA + ".CONSULTATION WHERE CONSULTATION.PATIENT_ID = :patientId",
            nativeQuery = true)
    List<Object[]> findAllConsultationsReceivedForPatient(@Param("patientId") long patientId);

    @Query(value = "SELECT patient_id FROM " + SacchonApplication.SCHEMA + ".CONSULTATION WHERE date_created >= :dateGiven", nativeQuery = true)
    List<Long> findPatientWithActiveConsultation(@Param("dateGiven") LocalDate dateGiven);

    // query for table Consultation (set doctor_id to null when doctor is deleted)
    @Transactional
    @Modifying
    @Query(value = "UPDATE " + SacchonApplication.SCHEMA + ".CONSULTATION SET doctor_id = null WHERE doctor_id = :doctorId" , nativeQuery = true)
    void makeDoctorIdNullOnDoctorDelete(@Param("doctorId") long doctorId);


    // query: doctor modifies a consultation to a patient
    @Transactional
    @Modifying
    @Query(value = "UPDATE " + SacchonApplication.SCHEMA + ".CONSULTATION SET details = :details, " +
            "seen_consultation = :seenConsultation WHERE id = :consultationId", nativeQuery = true)
    void updateConsultationFromDoctorByPatientId(@Param("consultationId") long consultationId,
                                                 @Param("details") String details,
                                                 @Param("seenConsultation") boolean seenConsultation);

    // query: updates seenConsultation after medications have been modified
    @Transactional
    @Modifying
    @Query(value = "UPDATE " + SacchonApplication.SCHEMA + ".CONSULTATION SET seen_consultation = :status" +
            " WHERE id = :consultationId", nativeQuery = true)
    void updateSeenConsultationById(@Param("consultationId") long consultationId, @Param("status") int status);

    @Query(value = "SELECT CONSULTATION.ID FROM " + SacchonApplication.SCHEMA + ".CONSULTATION" +
            " WHERE CONSULTATION.DATE_CREATED <= :dateGiven AND CONSULTATION.DATE_CREATED > :previousDays" +
            " AND CONSULTATION.PATIENT_ID = :patientId", nativeQuery = true)
    Long findConsultationIdInSpecificDate(
            @Param("patientId") long patientId,
            @Param("dateGiven") LocalDate dateGiven,
            @Param("previousDays") LocalDate previousDays
    );

    @Query(value = "SELECT MAX(CONSULTATION.DATE_CREATED) FROM " + SacchonApplication.SCHEMA + ".CONSULTATION " +
            "WHERE CONSULTATION.PATIENT_ID = :patientId", nativeQuery = true)
    LocalDate findLatestConsultationByPatientId(@Param("patientId") long patientId);

    @Query(value = "SELECT COUNT(*) FROM " + SacchonApplication.SCHEMA + ".CONSULTATION " +
            "WHERE CONSULTATION.PATIENT_ID = :patientId", nativeQuery = true)
    Long findConsultationCountByPatientId(@Param("patientId") long patientId);
}
