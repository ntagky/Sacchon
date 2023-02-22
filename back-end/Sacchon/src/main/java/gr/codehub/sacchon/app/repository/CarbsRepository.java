package gr.codehub.sacchon.app.repository;

import gr.codehub.sacchon.app.SacchonApplication;
import gr.codehub.sacchon.app.dto.PastCarbReadingsDto;
import gr.codehub.sacchon.app.model.Carbs;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CarbsRepository extends JpaRepository<Carbs, Long> {

    @Query(value = "SELECT * FROM " + SacchonApplication.SCHEMA + ".CARBS WHERE CARBS.PATIENT_ID = :patientId",
            nativeQuery = true)
    List<Carbs> findCarbsByPatientId(@Param("patientId") long patientId);

    @Query(value = "SELECT AVG(MEASUREMENT) FROM " + SacchonApplication.SCHEMA + ".CARBS " +
            "WHERE (CARBS.PATIENT_ID = :patientId AND CARBS.DATE >= :startingDate AND CARBS.DATE <= :endingDate)",
            nativeQuery = true)
    Integer findAverageCarbsByPatientIdOnSpecificDates(
            @Param("patientId") long patientId,
            @Param("startingDate") LocalDate startingDate,
            @Param("endingDate") LocalDate endingDate
    );

//    @Query(value = "SELECT CARBS.DATE,CARBS.MEASUREMENT FROM " + SacchonApplication.SCHEMA + ".CARBS WHERE CARBS.PATIENT_ID= :patientId AND CARBS.DATE >= :startingDate AND CARBS.DATE <= :endingDate",nativeQuery = true)
//    List<PastCarbReadingsDto> getCarbsReadingsBetweenDatesByPatientId(
//            @Param("patientId") long patientId,
//            @Param("startingDate") LocalDate startingDate,
//            @Param("endingDate") LocalDate endingDate
//    );

    @Query(value = "SELECT new gr.codehub.sacchon.app.dto.PastCarbReadingsDto(c.date, c.measurement) FROM Carbs c WHERE c.patient.id = :patientId AND c.date BETWEEN :startingDate AND :endingDate")
    List<PastCarbReadingsDto> getCarbsReadingsBetweenDatesByPatientId(
            @Param("patientId") long patientId,
            @Param("startingDate") LocalDate startingDate,
            @Param("endingDate") LocalDate endingDate
    );

    @Query(value = "SELECT COUNT(*) FROM " + SacchonApplication.SCHEMA + ".CARBS " +
            "WHERE CARBS.PATIENT_ID = :patientId AND CARBS.DATE > :localDate",
            nativeQuery = true)
    Long findCountOfCarbsByPatientIdAfterDate(@Param("patientId") long patientId, LocalDate localDate);

    @Query(value = "SELECT PATIENT_ID FROM " + SacchonApplication.SCHEMA + ".CARBS " +
            "WHERE CARBS.DATE >= :startingDate AND CARBS.DATE <= :endingDate",
            nativeQuery = true)
    List<Long> findPatientIdsWithinRange(
            @Param("startingDate") LocalDate startingDate,
            @Param("endingDate") LocalDate endingDate
    );

    @Transactional
    @Modifying
    @Query(value ="UPDATE " + SacchonApplication.SCHEMA + ".CARBS SET CARBS.MEASUREMENT = :measurement" +
            " WHERE CARBS.ID = :carbsId", nativeQuery = true)
    void updateCarbsById(
            @Param("carbsId") long carbsId,
            @Param("measurement") int measurement
    );

}
