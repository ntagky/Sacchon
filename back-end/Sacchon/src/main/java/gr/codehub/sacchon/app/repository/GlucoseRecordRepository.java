package gr.codehub.sacchon.app.repository;

import gr.codehub.sacchon.app.model.GlucoseRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GlucoseRecordRepository extends JpaRepository<GlucoseRecord, Integer> {
}
