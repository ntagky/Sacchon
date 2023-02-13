package gr.codehub.sacchon.app.dto;

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
public class PatientDto extends PersonDto {
    private int id;
    private String medicalRecordNumber;
    private String address;
    private String gender;
    private LocalDate dateOfBirth;
    private String bloodType;
    private String diabetesType;
    private int height;
    private double weight;
    private List<String> allergies;
    private List<String> medications;
    private List<String> conditions;

    private List<ConsultationDto> consultationsDto;
    private List<CarbsDto> carbsDto;
    private List<GlucoseDto> glucoseDto;
    private DoctorDto doctorDto;

    public PatientDto(Patient patient){
        if (patient!= null){
            id = patient.getId();
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

//            doctorDto = new DoctorDto(patient.getDoctor());
//            consultationsDto = patient.getDoctor()
//                    .getConsultations()
//                    .stream()
//                    .map(ConsultationDto::new)
//                    .collect(Collectors.toList());
//            glucoseDto = patient.getGlucose()
//                    .stream()
//                    .map(GlucoseDto::new)
//                    .collect(Collectors.toList());
//            carbsDto = patient.getCarbs()
//                    .stream()
//                    .map(CarbsDto::new)
//                    .collect(Collectors.toList());
        }
    }
    public Patient asPatient() {
        Patient patient = new Patient();
        patient.setId(id);
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
//        patient.setDoctor(doctorDto.asDoctor());
//        patient.setCarbs(
//                carbsDto.stream()
//                        .map(CarbsDto::asCarbs)
//                        .collect(Collectors.toList())
//        );
//        patient.setGlucose(
//                glucoseDto.stream()
//                        .map(GlucoseDto::asGlucose)
//                        .collect(Collectors.toList())
//        );
        return patient;
    }
}