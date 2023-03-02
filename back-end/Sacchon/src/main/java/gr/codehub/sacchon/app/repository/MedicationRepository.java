package gr.codehub.sacchon.app.repository;


import gr.codehub.sacchon.app.SacchonApplication;
import gr.codehub.sacchon.app.model.Medication;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface MedicationRepository extends JpaRepository<Medication, Long> {

    // query: get medication
    @Query(value = "SELECT * FROM " + SacchonApplication.SCHEMA + ".MEDICATION WHERE consultation_id = :consultationId", nativeQuery = true)
    List<Medication> findMedicationsByConsId(@Param("consultationId") long consultationId);


    // query: creates medications
    @Transactional
    @Modifying
    @Query(value = "INSERT INTO " + SacchonApplication.SCHEMA + ".MEDICATION(med_Name, dosage) VALUES (:medName, :dosage)", nativeQuery = true)
    void addMedicationsByConsultationId(@Param("medName") String medName,
                                        @Param("dosage") String dosage);

    // query: deletes medications
    @Transactional
    @Modifying
    @Query(value = "DELETE FROM " + SacchonApplication.SCHEMA + ".MEDICATION WHERE id = :medicationId", nativeQuery = true)
    void deleteMedicationsByConsultationId(@Param("medicationId") long medicationId);


    // query: updates medications
    @Transactional
    @Modifying
    @Query(value = "UPDATE " + SacchonApplication.SCHEMA + ".MEDICATION SET med_Name = :medName, " +
            "dosage = :dosage WHERE id = :medicationId", nativeQuery = true)
    void updateMedicationByConsultationId(@Param("medicationId") long medicationId,
                                          @Param("medName") String medName,
                                          @Param("dosage") String dosage);

    // query: updates seenConsultation after medications have been modified
    @Query(value = "SELECT consultation_id FROM " + SacchonApplication.SCHEMA + ".MEDICATION" +
            " WHERE id = :medicationId", nativeQuery = true)
    long getConsIdByMedId(@Param("medicationId") long medicationId);

}
