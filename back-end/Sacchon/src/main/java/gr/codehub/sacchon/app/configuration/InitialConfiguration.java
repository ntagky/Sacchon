package gr.codehub.sacchon.app.configuration;

import gr.codehub.sacchon.app.model.Patient;
import gr.codehub.sacchon.app.repository.PatientRepository;
import gr.codehub.sacchon.app.controller.ChiefDoctorController;
import gr.codehub.sacchon.app.model.ChiefDoctor;
import gr.codehub.sacchon.app.model.Consultation;
import gr.codehub.sacchon.app.model.Doctor;
import gr.codehub.sacchon.app.repository.ChiefDoctorRepository;
import gr.codehub.sacchon.app.repository.ConsultationRepository;
import gr.codehub.sacchon.app.repository.DoctorRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.ArrayList;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Configuration
public class InitialConfiguration {

    @Bean
    CommandLineRunner commandLineRunner(
            PatientRepository patientRepository){
        System.out.println("System dummy saved object");
        return args -> {
            Patient patient = new Patient();
            patient.setAddress("123 Main St.");
            patient.setGender("Male");
            patient.setDateOfBirth(LocalDate.of(1980, 1, 1));
            patient.setBloodType("O-");
            patient.setDiabetesType("Type 2");
            patient.setHeight(72);
            patient.setWeight(200.0);

            List<String> allergies = new ArrayList<>();
            allergies.add("Peanuts");
            allergies.add("Eggs");
            patient.setAllergies(allergies);

            List<String> medications = new ArrayList<>();
            medications.add("Ibuprofen");
            medications.add("Aspirin");
            patient.setMedications(medications);

            List<String> conditions = new ArrayList<>();
            conditions.add("Asthma");
            conditions.add("High Blood Pressure");
            patient.setConditions(conditions);

            patientRepository.saveAll(List.of(patient));

        };

    }


    @Bean
    CommandLineRunner commandLineRunner(
            DoctorRepository docRepository, ChiefDoctorRepository chiefDocRepository, ConsultationRepository consultationRepository){
        return args -> {
            Doctor doctor1 = new Doctor(0);
            Doctor doctor2 = new Doctor(1);
            docRepository.saveAll(List.of(doctor1, doctor2));

//            ChiefDoctor chiefDoctor1 = new ChiefDoctor();
//            ChiefDoctor chiefDoctor2 = new ChiefDoctor();
//            chiefDocRepository.saveAll(List.of(chiefDoctor1, chiefDoctor2));
        };
    }
}
