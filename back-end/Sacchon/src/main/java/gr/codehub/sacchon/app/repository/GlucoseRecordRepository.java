package gr.codehub.sacchon.app.repository;

import gr.codehub.sacchon.app.SacchonApplication;
import gr.codehub.sacchon.app.model.GlucoseRecord;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalTime;

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
    int findGlucoseRecordsCountByGlucoseId(@Param("glucoseId") long glucoseId);
}
