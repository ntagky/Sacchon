package gr.codehub.sacchon.app.repository;

import gr.codehub.sacchon.app.SacchonApplication;
import gr.codehub.sacchon.app.model.Carbs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CarbsRepository extends JpaRepository<Carbs, Integer> {

    @Query(value = "SELECT * FROM " + SacchonApplication.SCHEMA + ".CARBS WHERE CARBS.PATIENT_ID = :patientId",
            nativeQuery = true)
    List<Carbs> findCarbsByPatientId(@Param("patientId") int patientId);

    @Query(value = "SELECT AVG(MEASUREMENT) FROM " + SacchonApplication.SCHEMA + ".CARBS " +
            "WHERE (CARBS.PATIENT_ID = :patientId AND CARBS.DATE >= :startingDate AND CARBS.DATE <= :endingDate)",
            nativeQuery = true)
    Integer findAverageCarbsByPatientIdOnSpecificDates(
            @Param("patientId") int patientId,
            @Param("startingDate") LocalDate startingDate,
            @Param("endingDate") LocalDate endingDate
    );

}
