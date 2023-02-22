package gr.codehub.sacchon.app.dto;

import gr.codehub.sacchon.app.model.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class PatientDto extends PersonDto {
    private Long id;
    private String medicalRecordNumber;
    private String address;
    private String gender;
    private LocalDate dateOfBirth;
    private BloodType bloodType;
    private DiabetesType diabetesType;
    private int height;
    private double weight;
    private List<String> allergies;
    private List<String> medications;
    private List<String> conditions;

    public PatientDto(Patient patient){
        if (patient!= null){
            id = patient.getId();
            super.setFirstName(patient.getFirstName());
            super.setLastName(patient.getLastName());
            super.setEmail(patient.getEmail());
            super.setPassword(patient.getPassword());
            medicalRecordNumber = patient.getMedicalRecordNumber();
            address = patient.getAddress();
            gender = patient.getGender();
            dateOfBirth = patient.getDateOfBirth();
            bloodType = patient.getBloodType();
            diabetesType = patient.getDiabetesType();
            height = patient.getHeight();
            weight = patient.getWeight();
            allergies = patient.getAllergies();
            medications = patient.getMedications();
            conditions = patient.getConditions();
        }
    }


    public Patient asPatient() {

        Patient patient = new Patient();
        patient.setId(7);
        patient.setFirstName("chris");
        patient.setLastName("tzis");
        patient.setEmail("dfdfdf");
        patient.setPassword("asdads");
        patient.setMedicalRecordNumber(medicalRecordNumber);
        patient.setAddress(address);
        patient.setGender(gender);
        patient.setDateOfBirth(dateOfBirth);
        patient.setBloodType(BloodType.AB_NEGATIVE);
        patient.setDiabetesType(DiabetesType.TYPE_1);
        patient.setHeight(height);
        patient.setWeight(weight);
        patient.setAllergies(allergies);
        patient.setMedications(medications);
        patient.setConditions(conditions);
        return patient;
    }
}