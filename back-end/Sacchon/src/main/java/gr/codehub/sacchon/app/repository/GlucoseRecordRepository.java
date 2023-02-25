package gr.codehub.sacchon.app.repository;

import gr.codehub.sacchon.app.SacchonApplication;
import gr.codehub.sacchon.app.dto.PastGlucoseMeasurementDto;
import gr.codehub.sacchon.app.model.GlucoseRecord;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface GlucoseRecordRepository extends JpaRepository<GlucoseRecord, Long> {
    @Query(value = "SELECT AVG(GLUCOSE_RECORD.MEASUREMENT) FROM " + SacchonApplication.SCHEMA + ".GLUCOSE_RECORD " +
            "WHERE GLUCOSE_RECORD.GLUCOSE_ID = :glucoseId",
            nativeQuery = true)
    BigDecimal findAverageGlucoseRecordsByGlucoseId(@Param("glucoseId") long glucoseId);

    @Transactional
    @Modifying
    @Query(value ="UPDATE " + SacchonApplication.SCHEMA + ".GLUCOSE_RECORD " +
            "SET GLUCOSE_RECORD.MEASUREMENT = :measurement , GLUCOSE_RECORD.TIME = :localTime " +
            "WHERE GLUCOSE_RECORD.ID = :recordId", nativeQuery = true)
    void updateRecordById(
            @Param("recordId") long recordId,
            @Param("measurement") BigDecimal measurement,
            @Param("localTime") LocalTime localTime
    );

    @Query(value = "SELECT COUNT(*) FROM " + SacchonApplication.SCHEMA + ".GLUCOSE_RECORD " +
            "WHERE GLUCOSE_RECORD.GLUCOSE_ID = :glucoseId",
            nativeQuery = true)
    Integer findGlucoseRecordsCountByGlucoseId(@Param("glucoseId") long glucoseId);

    @Query("SELECT new gr.codehub.sacchon.app.dto.PastGlucoseMeasurementDto(glr.measurement, glr.time, g.date) FROM GlucoseRecord glr JOIN glr.glucose g WHERE g.patient.id = :patientId AND g.date BETWEEN :startingDate AND :endingDate")
    List<PastGlucoseMeasurementDto> getGlucoseMeasurementsBetweenDatesByPatientId(
            @Param("patientId") long patientId,
            @Param("startingDate") LocalDate startingDate,
            @Param("endingDate") LocalDate endingDate
    );

    @Query(value = "SELECT * FROM " + SacchonApplication.SCHEMA + ".GLUCOSE_RECORD " +
            "WHERE GLUCOSE_RECORD.GLUCOSE_ID = :glucoseId", nativeQuery = true)
    List<GlucoseRecord> readGlucoseRecordByGlucoseId(
            @Param("glucoseId") long glucoseId
    );
}
