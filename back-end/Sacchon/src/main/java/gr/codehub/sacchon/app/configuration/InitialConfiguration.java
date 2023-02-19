package gr.codehub.sacchon.app.configuration;

import gr.codehub.sacchon.app.model.*;
import gr.codehub.sacchon.app.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class InitialConfiguration {

    @Bean
    CommandLineRunner PatientCommandLineRunner(
            PatientRepository patientRepository, CarbsRepository carbsRepository,
            GlucoseRepository glucoseRepository, GlucoseRecordRepository glucoseRecordRepository,
            DoctorRepository doctorRepository,
            ChiefDoctorRepository chiefDoctorRepository){
        return args -> {
            System.out.println("System dummy saved object");

            Doctor doctor1 = new Doctor(0, new ArrayList<>(), new ArrayList<>());
            doctor1.setId(1);
            doctor1.setFirstName("Brand");
            doctor1.setFirstName("Red");
            doctor1.setEmail("dc.brand@hotmail.com");
            doctorRepository.saveAll(List.of(doctor1));

            Patient patient1 = new Patient();
            patient1.setFirstName("James");
            patient1.setLastName("Harden");
            patient1.setEmail("jh1982@gmail.com");
            patient1.setId(1);
            patient1.setAddress("124 Main St.");
            patient1.setGender("Male");
            patient1.setDateOfBirth(LocalDate.of(1990, 1, 1));
            patient1.setBloodType(BloodType.AB_NEGATIVE);
            patient1.setDiabetesType("Type 2");
            patient1.setHeight(72);
            patient1.setWeight(200.0);

            List<String> allergies = new ArrayList<>();
            allergies.add("Peanuts");
            allergies.add("Eggs");
            patient1.setAllergies(allergies);

            List<String> medications = new ArrayList<>();
            medications.add("Ibuprofen");
            medications.add("Aspirin");
            patient1.setMedications(medications);

            List<String> conditions = new ArrayList<>();
            conditions.add("Asthma");
            conditions.add("High Blood Pressure");
            patient1.setConditions(conditions);

            patient1.setCarbs(null);
            patient1.setGlucose(null);
            patient1.setDoctor(doctor1);

            Patient patient2 = new Patient();
            patient2.setId(2);
            patient2.setAddress("902 Queens");
            patient2.setGender("Female");
            patient2.setDateOfBirth(LocalDate.of(1992, 2, 1));
            patient2.setBloodType(BloodType.O_NEGATIVE);
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
            patient2.setDoctor(doctor1);
            patient2.setMedications(medications2);

            List<String> conditions2 = new ArrayList<>();
            conditions2.add("Heart disease");
            patient2.setConditions(conditions2);

            patientRepository.saveAll(List.of(patient1, patient2));

            Carbs carb1 = new Carbs(1,LocalDate.of(2023, 11, 16),1900, patient1);
            Carbs carb2 = new Carbs(2,LocalDate.of(2022, 11, 17),2300, patient1);
            Carbs carb3 = new Carbs(3,LocalDate.of(2022, 11, 18),1980, patient1);
            Carbs carb4 = new Carbs(4,LocalDate.of(2022, 11, 12),2210, patient2);
            Carbs carb5 = new Carbs(5,LocalDate.of(2022, 11, 12),2830, patient2);
            carbsRepository.saveAll(List.of(carb1, carb2, carb3, carb4, carb5));

            Glucose glucose1 = new Glucose(
                    1,
                    LocalDate.of(2023, 2, 16),
                    new ArrayList<> (),
                    patient1
            );
            Glucose glucose2 = new Glucose(
                    2,
                    LocalDate.of(2023, 11, 17),
                    new ArrayList<> (),
                    patient1
            );
            glucoseRepository.saveAll(List.of(glucose1, glucose2));

            GlucoseRecord glucoseRecord1 = new GlucoseRecord(
                    1,
                    LocalTime.of(18, 30),
                    new BigDecimal("76.98"),
                    glucose1
            );
            GlucoseRecord glucoseRecord2 = new GlucoseRecord(
                    2,
                    LocalTime.of(21, 10),
                    new BigDecimal("88.10"),
                    glucose1
            );
            GlucoseRecord glucoseRecord3 = new GlucoseRecord(
                    3,
                    LocalTime.of(23, 9),
                    new BigDecimal("120.99"),
                    glucose1
            );
            GlucoseRecord glucoseRecord4 = new GlucoseRecord(
                    4,
                    LocalTime.of(14, 10),
                    new BigDecimal("92.44"),
                    glucose2
            );
            glucoseRecordRepository.saveAll(List.of(
                    glucoseRecord1,
                    glucoseRecord2,
                    glucoseRecord3,
                    glucoseRecord4)
            );

//
//            ChiefDoctor chiefDoctor1 = new ChiefDoctor();
//            ChiefDoctor chiefDoctor2 = new ChiefDoctor();
//            chiefDoctorRepository.saveAll(List.of(chiefDoctor1, chiefDoctor2));
//

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
