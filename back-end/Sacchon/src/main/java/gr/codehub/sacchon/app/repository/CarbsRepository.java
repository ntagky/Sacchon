package gr.codehub.sacchon.app.repository;

import gr.codehub.sacchon.app.model.Carbs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarbsRepository extends JpaRepository<Carbs, Integer> {

}
