package gr.codehub.sacchon.app.repository;

import gr.codehub.sacchon.app.SacchonApplication;
import gr.codehub.sacchon.app.model.GlucoseRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface GlucoseRecordRepository extends JpaRepository<GlucoseRecord, Long> {
    @Query(value = "SELECT AVG(GLUCOSE_RECORD.MEASUREMENT) FROM " + SacchonApplication.SCHEMA + ".GLUCOSE_RECORD " +
            "WHERE GLUCOSE_RECORD.GLUCOSE_ID = :glucoseId",
            nativeQuery = true)
    BigDecimal findGlucoseRecordsByGlucoseId(@Param("glucoseId") long glucoseId);
}
