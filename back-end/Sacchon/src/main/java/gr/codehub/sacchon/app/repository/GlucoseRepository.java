package gr.codehub.sacchon.app.repository;

import gr.codehub.sacchon.app.SacchonApplication;
import gr.codehub.sacchon.app.model.Glucose;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface GlucoseRepository extends JpaRepository<Glucose, Long> {

    @Query(value = "SELECT * FROM " + SacchonApplication.SCHEMA + ".GLUCOSE WHERE GLUCOSE.PATIENT_ID = :patientId",
            nativeQuery = true)
    List<Glucose> findGlucoseByPatientId(@Param("patientId") long patientId);

    @Query(value = "SELECT GLUCOSE.ID FROM " + SacchonApplication.SCHEMA + ".GLUCOSE " +
            "WHERE GLUCOSE.PATIENT_ID = :patientId AND GLUCOSE.DATE >= :startingDate AND GLUCOSE.DATE <= :endingDate",
            nativeQuery = true)
    List<Long> findGlucoseDatesAndIdsByPatientIdOnSpecificDates(
            @Param("patientId") long patientId,
            @Param("startingDate") LocalDate startingDate,
            @Param("endingDate") LocalDate endingDate
    );

    @Query(value = "SELECT PATIENT_ID FROM " + SacchonApplication.SCHEMA + ".GLUCOSE " +
            "WHERE GLUCOSE.DATE >= :startingDate AND GLUCOSE.DATE <= :endingDate",
            nativeQuery = true)
    List<Long> findPatientIdsWithinRange(
            @Param("startingDate") LocalDate startingDate,
            @Param("endingDate") LocalDate endingDate
    );

    @Query(value = "SELECT GLUCOSE.ID FROM " + SacchonApplication.SCHEMA + ".GLUCOSE " +
            "WHERE GLUCOSE.PATIENT_ID = :patientId AND GLUCOSE.DATE = :givenDate",
            nativeQuery = true)
    Long findGlucoseIdInSpecificDate(long patientId, LocalDate givenDate);
}
