package gr.codehub.sacchon.app.repository;
import gr.codehub.sacchon.app.SacchonApplication;
import gr.codehub.sacchon.app.model.Consultation;
import org.springframework.data.jpa.repository.JpaRepository;
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


    @Query(value = "SELECT doctor_first_name,doctor_last_name,doctor_email,date_created,details FROM " + SacchonApplication.SCHEMA + ".CONSULTATION WHERE CONSULTATION.PATIENT_ID = :patientId",
            nativeQuery = true)
    List<Object[]> findAllConsultationsReceivedForPatient(@Param("patientId") long patientId);

    @Query(value = "SELECT patient_id FROM " + SacchonApplication.SCHEMA + ".CONSULTATION WHERE date_created >= :dateGiven", nativeQuery = true)
    List<Long> findPatientWithActiveConsultation(@Param("dateGiven") LocalDate dateGiven);
}
