package gr.codehub.sacchon.app.repository;
import gr.codehub.sacchon.app.SacchonApplication;
import gr.codehub.sacchon.app.model.Consultation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConsultationRepository extends JpaRepository<Consultation, Long> {

    @Query(value = "SELECT * FROM " + SacchonApplication.SCHEMA + ".CONSULTATION WHERE CONSULTATION.PATIENT_ID = :patientId", nativeQuery = true)
    List<Consultation> findConsultationByPatientId(@Param("patientId") long patientId);

    @Query(value = "SELECT * FROM " + SacchonApplication.SCHEMA + ".CONSULTATION WHERE CONSULTATION.PATIENT_ID = :patientId", nativeQuery = true)
    List<Consultation> findConsultationInfoByPatientId(@Param("patientId") long patientId);
}
