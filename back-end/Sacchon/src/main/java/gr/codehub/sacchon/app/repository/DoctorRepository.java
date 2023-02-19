package gr.codehub.sacchon.app.repository;

import gr.codehub.sacchon.app.SacchonApplication;
import gr.codehub.sacchon.app.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Integer>{
    @Query(value = "SELECT * FROM" + SacchonApplication.SCHEMA + ".DOCTOR WHERE email LIKE CONCAT('%' + ?1 + '%')", nativeQuery = true)
    List<Doctor> findDoctorByEmailNative(String match);
}
