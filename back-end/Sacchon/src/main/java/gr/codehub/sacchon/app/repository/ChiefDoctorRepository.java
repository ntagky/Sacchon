package gr.codehub.sacchon.app.repository;

import gr.codehub.sacchon.app.SacchonApplication;
import gr.codehub.sacchon.app.dto.ConsultationsGivenByDoctor;
import gr.codehub.sacchon.app.model.ChiefDoctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ChiefDoctorRepository
        extends JpaRepository<ChiefDoctor, Long> {

    @Query(value = "SELECT new gr.codehub.sacchon.app.dto.ConsultationsGivenByDoctor(c.doctor.firstName, c.doctor.lastName, c.doctor.email, c.dateCreated, c.details) FROM Consultation c WHERE c.doctor.id = :doctorId AND c.dateCreated BETWEEN :startingDate AND :endingDate")
    List<ConsultationsGivenByDoctor> findConsultationsBetweenGivenDates(
            @Param("doctorId") long doctorId,
            @Param("startingDate") LocalDate startingDate,
            @Param("endingDate") LocalDate endingDate
    );

    @Query(value = "SELECT CHIEF_DOCTOR.ID FROM " + SacchonApplication.SCHEMA + ".CHIEF_DOCTOR " +
            "WHERE CHIEF_DOCTOR.EMAIL = :email AND CHIEF_DOCTOR.PASSWORD = :password", nativeQuery = true)
    Long findChiefByEmailAndPassword(@Param("email") String email, @Param("password") String password);
}
