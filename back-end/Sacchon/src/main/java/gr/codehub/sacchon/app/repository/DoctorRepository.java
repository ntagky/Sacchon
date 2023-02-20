package gr.codehub.sacchon.app.repository;

import gr.codehub.sacchon.app.SacchonApplication;
import gr.codehub.sacchon.app.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Integer>{
    @Query(value = "SELECT * FROM " + SacchonApplication.SCHEMA + ".DOCTOR WHERE email LIKE CONCAT('%' + ?1 + '%')", nativeQuery = true)
    List<Doctor> findDoctorByEmailNative(String match);

    @Query(value ="SELECT DOCTOR.FIRSTNAME, DOCTOR.LASTNAME, DOCTOR.EMAIL FROM " + SacchonApplication.SCHEMA + ".DOCTOR WHERE id =: id", nativeQuery = true)
    Doctor findDoctorNameAndEmailById(@Param("id") int id);
}
