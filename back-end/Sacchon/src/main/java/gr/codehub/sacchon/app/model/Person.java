package gr.codehub.sacchon.app.model;

import gr.codehub.sacchon.app.SacchonApplication;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

/**
 The Person class represents a person in the system. It is a superclass that holds
 common attributes that are shared by both patients and healthcare professionals.
 The person class is inherited from the patient and doctor/chief-doctor classes

 This class contains the person's id, first name, last name, password, email, signed date,
 and phone number.

 This class is annotated with {@link MappedSuperclass} to indicate that it is a non-entity class
 that is used as a superclass for entity classes. It is also annotated with {@link SuperBuilder}
 to enable the creation of a builder pattern for creating instances of this class.

 It contains the following fields:

 ID: Unique identifier
 firstName: The first name of the patient
 lastName: The last name of the patient
 password: The patients password
 email: The patients email
 signedDate: Patients sign up date
 phoneNumber: The patient phone number

 @author Christos Tzoulias
 @version 1.0
 @since 2023-02-28
 */

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@MappedSuperclass
@SuperBuilder
@Table(schema = SacchonApplication.SCHEMA)
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String firstName;
    private String lastName;
    private String password;
    private String email;
    private LocalDate signedDate;
    private String phoneNumber;



}
