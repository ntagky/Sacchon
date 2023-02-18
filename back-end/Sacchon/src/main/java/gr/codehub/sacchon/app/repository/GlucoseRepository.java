package gr.codehub.sacchon.app.repository;

import gr.codehub.sacchon.app.SacchonApplication;
import gr.codehub.sacchon.app.model.Glucose;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GlucoseRepository extends JpaRepository<Glucose, Integer> {

    @Query(value = "SELECT * FROM " + SacchonApplication.SCHEMA + ".GLUCOSE WHERE patient_id = :patientId", nativeQuery = true)
    List<Glucose> findGlucoseByPatientId(@Param("patientId") int patientId);

}
