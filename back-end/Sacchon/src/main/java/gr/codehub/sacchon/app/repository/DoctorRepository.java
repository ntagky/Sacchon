package gr.codehub.sacchon.app.repository;

import gr.codehub.sacchon.app.SacchonApplication;
import gr.codehub.sacchon.app.model.Doctor;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long>{

    @Query(value ="SELECT ID FROM " + SacchonApplication.SCHEMA + ".DOCTOR WHERE DOCTOR.SIGNED_DATE < :endingDate", nativeQuery = true)
    List<Long> readDoctorsBySignedDateBefore(@Param("endingDate") LocalDate endingDate);

    @Query(value = "SELECT * FROM " + SacchonApplication.SCHEMA + ".DOCTOR WHERE email LIKE CONCAT('%', + ?1, + '%')", nativeQuery = true)
    List<Doctor> findDoctorByEmailNative(String match);

    @Query(value ="SELECT * FROM " + SacchonApplication.SCHEMA + ".DOCTOR WHERE id = :doctorId", nativeQuery = true)
    Doctor findDoctorNameAndEmailById(@Param("doctorId") long id);

    // query: display Doctor's account data + frontend - Doctor card on landpage
    @Query(value ="SELECT * FROM " + SacchonApplication.SCHEMA + ".DOCTOR WHERE id = :doctorId", nativeQuery = true)
    Doctor DisplayAccountData(@Param("doctorId") long id);

    // query: register a new doctor
    @Transactional
    @Modifying
    @Query(value = "INSERT INTO " + SacchonApplication.SCHEMA + ".DOCTOR (first_name, last_name, password, email, signed_date) " +
            "VALUES (:firstName, :lastName, :password, :email, :signedDate);", nativeQuery = true)
    void registerDoctor(@Param("firstName") String firstName,
                        @Param("lastName") String lastName,
                        @Param("password") String password,
                        @Param("email") String email,
                        @Param("signedDate") LocalDate signedDate);

//     query: delete a doctor
    @Transactional
    @Modifying
    @Query(value = "DELETE FROM " + SacchonApplication.SCHEMA + ".DOCTOR WHERE id = :doctorId", nativeQuery = true)
    void deleteDoctorById(@Param("doctorId") long doctorId);
}
