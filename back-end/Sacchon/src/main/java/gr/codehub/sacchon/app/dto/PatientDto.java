package gr.codehub.sacchon.app.dto;

import gr.codehub.sacchon.app.model.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class PatientDto extends PersonDto {
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
        if (patient != null){
            super.setId(patient.getId());
            super.setFirstName(patient.getFirstName());
            super.setLastName(patient.getLastName());
            super.setEmail(patient.getEmail());
            super.setSignedDate(patient.getSignedDate());
            super.setPhoneNumber(patient.getPhoneNumber());
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
            conditions = patient.getConditions();
        }
    }
    public Patient asPatient() {
        Patient patient = new Patient();
        patient.setId(super.getId());
        patient.setPhoneNumber(super.getPhoneNumber());
        patient.setFirstName(super.getFirstName());
        patient.setLastName(super.getLastName());
        patient.setEmail(super.getEmail());
        patient.setSignedDate(super.getSignedDate());
        patient.setPassword(super.getPassword());
        patient.setMedicalRecordNumber(medicalRecordNumber);
        patient.setAddress(address);
        patient.setGender(gender);
        patient.setDateOfBirth(dateOfBirth);
        patient.setBloodType(bloodType);
        patient.setDiabetesType(diabetesType);
        patient.setHeight(height);
        patient.setWeight(weight);
        patient.setAllergies(allergies);
        patient.setConditions(conditions);
        return patient;
    }
}