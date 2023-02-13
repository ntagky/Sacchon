package gr.codehub.sacchon.app.repository;

import gr.codehub.sacchon.app.model.Glucose;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GlucoseRepository extends JpaRepository<Glucose, Integer> {

}
