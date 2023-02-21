package gr.codehub.sacchon.app.dto;

import gr.codehub.sacchon.app.model.BloodType;
import gr.codehub.sacchon.app.model.DiabetesType;
import gr.codehub.sacchon.app.model.Patient;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Setter
@Getter
@NoArgsConstructor
public class PatientReceivedDto extends PersonDto {
    private Long id;
    private String medicalRecordNumber;
    private String address;
    private String gender;
    private LocalDate dateOfBirth;
    private String bloodType;
    private DiabetesType diabetesType;
    private int height;
    private double weight;
    private List<String> allergies;
    private List<String> medications;
    private List<String> conditions;

    private List<ConsultationDto> consultationsDto;
    private List<CarbsDto> carbsDto;
    private List<GlucoseDto> glucoseDto;

    public PatientReceivedDto(Patient patient){
        if (patient!= null){
            id = patient.getId();
            medicalRecordNumber = patient.getMedicalRecordNumber();
            address = patient.getAddress();
            gender = patient.getGender();
            dateOfBirth = patient.getDateOfBirth();
            bloodType = patient.getBloodType().name();
            diabetesType = patient.getDiabetesType();
            height = patient.getHeight();
            weight = patient.getWeight();
            allergies = patient.getAllergies();
            medications = patient.getMedications();
            conditions = patient.getConditions();

            consultationsDto = patient.getConsultations()
                    .stream()
                    .map(ConsultationDto::new)
                    .collect(Collectors.toList());
            glucoseDto = patient.getGlucose()
                    .stream()
                    .map(GlucoseDto::new)
                    .collect(Collectors.toList());
            carbsDto = patient.getCarbs()
                    .stream()
                    .map(CarbsDto::new)
                    .collect(Collectors.toList());
        }
    }
    public Patient asPatient() {
        Patient patient = new Patient();
        patient.setId(id);
        patient.setFirstName("James");
        patient.setLastName("Pfizer");
        patient.setEmail("james999@gmail.com");
        patient.setPassword("MyPassw0rD");
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
        patient.setConsultations(
                consultationsDto.stream()
                        .map(ConsultationDto::asConsultation)
                        .collect(Collectors.toList())
        );
        patient.setCarbs(
                carbsDto.stream()
                        .map(CarbsDto::asCarbs)
                        .collect(Collectors.toList())
        );
        patient.setGlucose(
                glucoseDto.stream()
                        .map(GlucoseDto::asGlucose)
                        .collect(Collectors.toList())
        );
        return patient;
    }
}