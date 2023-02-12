package gr.codehub.sacchon.app.repository;
import gr.codehub.sacchon.app.model.Consultation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConsultationRepository
        extends JpaRepository<Consultation, Integer> {
}
