package gr.codehub.sacchon.app.repository;

import gr.codehub.sacchon.app.SacchonApplication;
import gr.codehub.sacchon.app.model.Carbs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarbsRepository extends JpaRepository<Carbs, Integer> {

    @Query(value = "SELECT * FROM " + SacchonApplication.SCHEMA + ".CARBS WHERE patient_id = :patientId", nativeQuery = true)
    List<Carbs> findCarbsByPatientId(@Param("patientId") int patientId);

}
