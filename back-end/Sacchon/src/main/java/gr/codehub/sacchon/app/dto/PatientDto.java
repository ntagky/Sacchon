package gr.codehub.sacchon.app.dto;

import gr.codehub.sacchon.app.model.Patient;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class PatientDto {
    String medicalRecordNumber;
    String address;
    String gender;
    LocalDate dateOfBirth;
    String bloodType;
    String diabetesType;
    int height;
    double weight;
    List<String> allergies;
    List<String> medications;
    List<String> conditions;



    public PatientDto(Patient patient){
        if (patient!= null){
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
        patient.setMedicalRecordNumber(medicalRecordNumber);
        patient.setAddress(address);
        patient.setGender(gender);
        patient.setDateOfBirth(dateOfBirth);
        patient.setBloodType(bloodType);
        patient.setDiabetesType(diabetesType);
        patient.setHeight(height);
        patient.setWeight(weight);
        patient.setAllergies(allergies);
        patient.setMedications(medications);
        patient.setConditions(conditions);
        return patient;
    }
}