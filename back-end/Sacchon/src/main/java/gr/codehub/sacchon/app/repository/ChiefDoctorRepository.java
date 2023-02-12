package gr.codehub.sacchon.app.repository;

import gr.codehub.sacchon.app.model.ChiefDoctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChiefDoctorRepository
        extends JpaRepository<ChiefDoctor, Integer> {
}