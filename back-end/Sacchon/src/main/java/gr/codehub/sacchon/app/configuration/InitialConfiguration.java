package gr.codehub.sacchon.app.configuration;

import gr.codehub.sacchon.app.model.*;
import gr.codehub.sacchon.app.repository.*;
import gr.codehub.sacchon.app.controller.ChiefDoctorController;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Configuration
public class InitialConfiguration {

    @Bean
    CommandLineRunner PatientCommandLineRunner(
            PatientRepository patientRepository, CarbsRepository carbsRepository,
            GlucoseRepository glucoseRepository, DoctorRepository doctorRepository,
            ChiefDoctorRepository chiefDoctorRepository){
        System.out.println("System dummy saved object");
        return args -> {
            Patient patient = new Patient();
            patient.setId(1);
            patient.setAddress("124 Main St.");
            patient.setGender("Male");
            patient.setDateOfBirth(LocalDate.of(1990, 1, 1));
            patient.setBloodType("O-");
            patient.setDiabetesType("Type 2");
            patient.setHeight(72);
            patient.setWeight(200.0);
//            patient.setDoctor(new Doctor());

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

            Patient patient2 = new Patient();
            patient2.setId(2);
            patient2.setAddress("902 Queens");
            patient2.setGender("Female");
            patient2.setDateOfBirth(LocalDate.of(1992, 2, 1));
            patient2.setBloodType("A+");
            patient2.setDiabetesType("Type 1");
            patient2.setHeight(52);
            patient2.setWeight(140.0);

            List<String> allergies2 = new ArrayList<>();
            allergies2.add("Grapes");
            allergies2.add("Water");
            patient2.setAllergies(allergies2);

            List<String> medications2 = new ArrayList<>();
            medications2.add("Medrol");
            medications2.add("Aspirin");
//            patient2.setDoctor(new Doctor());
            patient2.setMedications(medications2);

            List<String> conditions2 = new ArrayList<>();
            conditions2.add("Heart disease");
            patient2.setConditions(conditions2);
            patientRepository.saveAll(List.of(patient,patient2));

//            Doctor doctor1 = new Doctor(0, new ArrayList<Patient>(), new ArrayList<Consultation>());
//            Doctor doctor2 = new Doctor(1, new ArrayList<Patient>(), new ArrayList<Consultation>());
//            doctorRepository.saveAll(List.of(doctor1, doctor2));

            ChiefDoctor chiefDoctor1 = new ChiefDoctor();
            ChiefDoctor chiefDoctor2 = new ChiefDoctor();
            chiefDoctorRepository.saveAll(List.of(chiefDoctor1, chiefDoctor2));


            Carbs carb1 = new Carbs(0,LocalDate.of(2023, 2, 16),12.56, patient);
            Carbs carb2 = new Carbs(0,LocalDate.of(2022, 11, 12),11.2, patient);
            carbsRepository.saveAll(List.of(carb1, carb2));

            Glucose glucose1 = new Glucose(0,LocalDate.of(2023, 2, 16),
                               new ArrayList<GlucoseRecord> (), patient);
            Glucose glucose2 = new Glucose(0,LocalDate.of(2023, 11, 16),
                               new ArrayList<GlucoseRecord> (),
                               patient2);
            glucoseRepository.saveAll(List.of(glucose1, glucose2));
        };
    }


//    @Bean
//    CommandLineRunner DoctorCommandLineRunner(
//            DoctorRepository docRepository, ChiefDoctorRepository chiefDocRepository, ConsultationRepository consultationRepository){
//        return args -> {
//            Doctor doctor1 = new Doctor(0, new ArrayList<Patient>(), new ArrayList<Consultation>());
//            Doctor doctor2 = new Doctor(1, new ArrayList<Patient>(), new ArrayList<Consultation>());
//            docRepository.saveAll(List.of(doctor1, doctor2));
//
//            ChiefDoctor chiefDoctor1 = new ChiefDoctor();
//            ChiefDoctor chiefDoctor2 = new ChiefDoctor();
//            chiefDocRepository.saveAll(List.of(chiefDoctor1, chiefDoctor2));
//        };
//    }


//    @Bean
//    CommandLineRunner ChiefDoctorCommandLineRunner(
//            ChiefDoctorRepository chiefDoctorRepository){
//        return args -> {
//            ChiefDoctor chiefDoctor1 = new ChiefDoctor();
//            ChiefDoctor chiefDoctor2 = new ChiefDoctor();
//           chiefDoctorRepository.saveAll(List.of(chiefDoctor1, chiefDoctor2));
//        };
//    }

    //Doctor name maybe should be hard coded in, instead be found from the doctor class
//    @Bean
//    CommandLineRunner ConsultationCommandLineRunner(
//            ConsultationRepository consultationRepository){
//        return args -> {
//            Consultation consultation1 = new Consultation(1,"Martinez",LocalDate.of(2023, 2, 16),
//                                         true,new ArrayList<String>(),"details",new Doctor(),new Patient());
//            Consultation consultation2 = new Consultation(1,"John",LocalDate.of(2023, 1, 11),
//                    true,new ArrayList<String>(),"extra details",new Doctor(),new Patient());
//
//            consultationRepository.saveAll(List.of(consultation1,consultation2));
//        };
//    }
}
