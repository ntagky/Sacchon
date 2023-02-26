package gr.codehub.sacchon.app.configuration;

import gr.codehub.sacchon.app.SacchonApplication;
import gr.codehub.sacchon.app.model.*;
import gr.codehub.sacchon.app.repository.*;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.*;

@Configuration
public class InitialConfiguration {

    private final Random random = new Random(42);
    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    private final LinkedList<String> namesMaleLinkedList = new LinkedList<>(List.of("Liam", "Noah", "Oliver", "Elijah", "James", "William", "Benjamin", "Lucas", "Henry", "Theodore", "Amiri", "Kevin", "Jason", "Jeffrey", "Jacob", "Gary", "Eric", "Nicolas", "Jonathan", "Tyler"));
    private final LinkedList<String> namesFemaleLinkedList = new LinkedList<>(List.of("Olivia", "Emma", "Charlotte", "Amelia", "Ava", "Sophia", "Isabella", "Mia", "Evelyn", "Harper", "Debra", "Maria", "Olivia", "Joyce", "Ruth", "Janet", "Samantha", "Stella", "Helen", "Evelyn"));
    private final List<String> lastNamesLinkedList = new ArrayList<>(List.of("Smith", "Johnson", "William", "Brown", "Jones", "Garcia", "Miller", "Davis", "Rodriguez", "Martinez", "Hernandez", "Lopez", " Wilson", "Anderson", "Thomas", "Taylor", "Jackson", "Martin", "Moore", "Lee", "Perez", "Thompson", "White", "Walker", "Hill", "Torres", "Scot", "Young"));
    private final List<String> allergiesList = new ArrayList<>(List.of("Grass Pollen", "Dust", "Peanut", "Milk", "Egg", "Animal Fur", "Bee", "Wasp", "Fish", "Crustaceans", "Wheat", "Soy"));
    private final List<String> medicationNamesList = new ArrayList<>(List.of("Atorvastatin", "Levothyroxine", "Metformin", "Lisinopril", "Amlodipine", "Metoprolol", "Albuterol", "Omeprazole", "Losartan", "Gabapentin", "Hydrochlorothiazide", "Sertraline", "Simvastatin", "Montelukast", "Escitalopram", "Rosuvastatin", "Bupropion", "Furosemide", "Pantoprazole"));
    private final List<String> conditionsList = new ArrayList<>(List.of("Heart Disease", "Cancer", "Asthma", "Emphysema", "Alzheimer Disease", "Substance Abuse", "Pneumonia", "Kidney Disease", "Mental Health Conditions"));
    private final List<String> detailsList = new ArrayList<>(List.of("Take 2 pills per day.", "Don't mix with alcohol!", "After each meal.", "Before bed only.", "One before eating lunch, one after.", "At the morning."));

    private String convertToTens(int number) { return number > 10 ? String.valueOf(number) : "0" + number; }

    private void createRandomListSequence(List<String> referenceList, List<String> arrayList, double probability, Random random) {
        if (Math.random() < probability) {
            int idx = random.nextInt(0, referenceList.size() - 1);
            while (idx < referenceList.size()) {
                arrayList.add(referenceList.get(idx));
                idx = random.nextInt(idx, referenceList.size() + 5);
            }
        }
    }

    private LocalDate getDateBeforeToday(int minusDays) {
        Date date;
        try {
            date = sdf.parse(LocalDate.now().toString());
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.DATE, -minusDays);
            return calendar.getTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    private void createChiefDoctors(ChiefDoctorRepository chiefDoctorRepository, int population) {
        ArrayList<ChiefDoctor> chiefDoctorArrayList = new ArrayList<>();
        ChiefDoctor chiefDoctor;
        String firstName;
        String lastName;
        int yearBorn;

        for (int i = 0; i < population; i++) {
            chiefDoctor = new ChiefDoctor();
            firstName = (namesMaleLinkedList.size() + namesFemaleLinkedList.size()) % 2 == 0 ? namesMaleLinkedList.pop() : namesFemaleLinkedList.pop();
            lastName = lastNamesLinkedList.get(i);
            yearBorn = 1985 - random.nextInt(40);

            chiefDoctor.setId(0L);
            chiefDoctor.setFirstName(firstName);
            chiefDoctor.setLastName(lastName);
            chiefDoctor.setEmail("ch." + firstName.toLowerCase().charAt(0) + lastName.toLowerCase() + yearBorn + "@gmail.com");
            chiefDoctor.setPassword(RandomStringUtils.random(12, true, true));
            chiefDoctor.setSignedDate(getDateBeforeToday(random.nextInt(150)));

            chiefDoctorArrayList.add(chiefDoctor);
        }

        chiefDoctorRepository.saveAll(chiefDoctorArrayList);
    }

    private List<Doctor> createDoctors(DoctorRepository doctorRepository, int population, int assignedBefore) {
        ArrayList<Doctor> doctorArrayList = new ArrayList<>();
        Doctor doctor;
        String firstName;
        String lastName;
        int yearBorn;

        for (int i = 0; i < population; i++) {
            doctor = new Doctor();
            firstName = (namesMaleLinkedList.size() + namesFemaleLinkedList.size()) % 2 == 0 ? namesMaleLinkedList.pop() : namesFemaleLinkedList.pop();
            lastName = lastNamesLinkedList.get(i);
            yearBorn = 1995 - random.nextInt(40);

            doctor.setId(0L);
            doctor.setFirstName(firstName);
            doctor.setLastName(lastName);
            doctor.setEmail(firstName.toLowerCase().charAt(0) + lastName.toLowerCase() + yearBorn + "@gmail.com");
            doctor.setPassword(RandomStringUtils.random(12, true, true));
            doctor.setSignedDate(getDateBeforeToday(random.nextInt(assignedBefore, assignedBefore + 30)));

            doctor.setPatients(null);
            doctor.setConsultations(null);
            doctor.setPhoneNumber("69"+RandomStringUtils.random(8, false, true));

            doctorArrayList.add(doctor);
        }

        return doctorRepository.saveAll(doctorArrayList);
    }

    private List<Patient> createPatients(PatientRepository patientRepository, int population, int[] assignedBefore) {
        ArrayList<Patient> patientArrayList = new ArrayList<>();
        Patient patient;
        String firstName;
        String lastName;
        int yearBorn;
        int monthBorn;
        int dayBorn;
        List<String> allergies;
//        List<String> medications;
        List<String> conditions;

        for (int i = 0; i < population; i++) {
            patient = new Patient();
            firstName = (namesMaleLinkedList.size() + namesFemaleLinkedList.size()) % 2 == 0 ? namesMaleLinkedList.pop() : namesFemaleLinkedList.pop();
            lastName = lastNamesLinkedList.get(i);
            yearBorn = 2005 - random.nextInt(50);
            monthBorn = random.nextInt(12) + 1;
            dayBorn = random.nextInt(27) + 1;

            patient.setPhoneNumber("69"+RandomStringUtils.random(8, false, true));
            patient.setId(0L);
            patient.setFirstName(firstName);
            patient.setLastName(lastName);
            patient.setEmail(firstName.toLowerCase().charAt(0) + lastName.toLowerCase() + yearBorn + "@gmail.com");
            patient.setSignedDate(getDateBeforeToday(assignedBefore[i]));

            patient.setMedicalRecordNumber(convertToTens(dayBorn) + convertToTens(monthBorn) + yearBorn + "" + RandomStringUtils.random(5, false, true));
            patient.setPassword(RandomStringUtils.random(12, true, true));
            patient.setAddress(random.nextInt(1000) + " Address St.");
            patient.setGender(i % 2 == 0 ? "Male" : "Female");
            patient.setDateOfBirth(LocalDate.of(yearBorn, monthBorn, dayBorn));
            patient.setBloodType(BloodType.values()[random.nextInt(BloodType.values().length)]);
            patient.setDiabetesType(DiabetesType.values()[random.nextInt(DiabetesType.values().length)]);
            patient.setHeight(150 + random.nextInt(50));
            patient.setWeight(50 + random.nextInt(70));

            allergies = new ArrayList<>();
            createRandomListSequence(allergiesList, allergies, 0.35, random);
            patient.setAllergies(allergies);

//            medications = new ArrayList<>();
//            createRandomListSequence(medicationNamesList, medications, 0.2, random);
//            patient.setMedications(medications);

            conditions = new ArrayList<>();
            createRandomListSequence(conditionsList, conditions, 0.1, random);
            patient.setConditions(conditions);

            patient.setCarbs(null);
            patient.setGlucose(null);
            patient.setDoctor(null);

            patientArrayList.add(patient);
        }

        return patientRepository.saveAll(patientArrayList);
    }

    private void createMedication(MedicationRepository medicationRepository, int population, Consultation assignedConsultation) {
        ArrayList<Medication> medicationArrayList = new ArrayList<>();
        Medication medication;
        List<String> medicationNames;

        for (int i = 0; i < population; i++) {
            medication = new Medication();

            medication.setId(0L);

//            medicationNames = new ArrayList<>();
//            createRandomListSequence(medicationNamesList, medicationNames, 0.2, random);
//            medication.setName(medicationNames.get(i));
            medication.setMedName("sffs");

            medication.setDosage("2 pills per day");

            medication.setConsultation(assignedConsultation);

            medicationArrayList.add(medication);
        }

        medicationRepository.saveAll(medicationArrayList);
    }

    private void createCarbs(CarbsRepository carbsRepository, int population, Patient assignedPerson) {
        ArrayList<Carbs> carbsArrayList = new ArrayList<>();
        Carbs carbs;

        for (int i = 0; i < population; i++) {
            carbs = new Carbs();

            carbs.setId(0L);
            carbs.setDate(getDateBeforeToday(population - i));
            carbs.setMeasurement(random.nextInt(1500, 3000));
            carbs.setPatient(assignedPerson);

            carbsArrayList.add(carbs);
        }

        carbsRepository.saveAll(carbsArrayList);
    }

    private List<Glucose> createGlucose(GlucoseRepository glucoseRepository, int population, Patient assignedPerson) {
        ArrayList<Glucose> glucoseArrayList = new ArrayList<>();
        Glucose glucose;

        for (int i = 0; i < population; i++) {
            glucose = new Glucose();

            glucose.setId(0L);
            glucose.setDate(getDateBeforeToday(population - i));
            glucose.setMeasurement(null);
            glucose.setPatient(assignedPerson);

            glucoseArrayList.add(glucose);
        }

        return glucoseRepository.saveAll(glucoseArrayList);
    }

    private void createGlucoseRecords(GlucoseRecordRepository glucoseRecordRepository, int population, Glucose assignedGlucose) {
        ArrayList<GlucoseRecord> glucoseRecordArrayList = new ArrayList<>();
        GlucoseRecord glucoseRecord;

        List<Integer> hours = Arrays
                .stream(random.ints(population, 8, 23).toArray())
                .boxed()
                .distinct()
                .sorted()
                .toList();

        for (Integer hour : hours) {
            glucoseRecord = new GlucoseRecord();

            glucoseRecord.setId(0L);
            glucoseRecord.setTime(LocalTime.of(hour, random.nextInt(60), random.nextInt(59)));
            glucoseRecord.setMeasurement(BigDecimal.valueOf(Math.abs(random.nextGaussian()) * 100 + 100));
            glucoseRecord.setGlucose(assignedGlucose);

            glucoseRecordArrayList.add(glucoseRecord);
        }

        glucoseRecordRepository.saveAll(glucoseRecordArrayList);
    }

    private List<Consultation> createConsultation(ConsultationRepository consultationRepository, int population, Patient assignedPerson, Doctor assignedDoctor, LocalDate[] localDates) {
        ArrayList<Consultation> consultationArrayList = new ArrayList<>();
        Consultation consultation;

//        List<String> medications = new ArrayList<>();
//        createRandomListSequence(medicationNamesList, medications, 2, random);

        for (int i = 0; i < population; i++) {
            consultation = new Consultation();

            consultation.setId(0L);
            consultation.setDoctorFirstName(assignedDoctor.getFirstName());
            consultation.setDoctorLastName(assignedDoctor.getLastName());
            consultation.setDoctorEmail(assignedDoctor.getEmail());
            consultation.setDateCreated(localDates[i]);
            consultation.setSeenConsultation(random.nextBoolean());
//            consultation.setMedications(medications);
            consultation.setDetails(detailsList.get(random.nextInt(detailsList.size())));
            consultation.setPatient(assignedPerson);
            consultation.setDoctor(assignedDoctor);

            consultationArrayList.add(consultation);
        }

        return consultationRepository.saveAll(consultationArrayList);
    }

    @Bean
    CommandLineRunner PatientCommandLineRunner(
            PatientRepository patientRepository, CarbsRepository carbsRepository,
            GlucoseRepository glucoseRepository, GlucoseRecordRepository glucoseRecordRepository,
            DoctorRepository doctorRepository,
            ChiefDoctorRepository chiefDoctorRepository, ConsultationRepository consultationRepository, MedicationRepository medicationRepository){
        return args -> {

            if (!SacchonApplication.DEBUG_MODE)
                return;

            System.out.println("Saving dummy objects..");

            int patientPopulation = 12;
            int doctorPopulation = 4;
            int chiefDoctorPopulation = 1;
            int[] measurementPersonPopulation = new int[patientPopulation];
            int measurementBound = 125;
            for (int i = 0; i < patientPopulation; i++)
                measurementPersonPopulation[i] = random.nextInt(25, measurementBound);

            Collections.shuffle(namesMaleLinkedList);
            Collections.shuffle(namesFemaleLinkedList);
            Collections.shuffle(lastNamesLinkedList);

            assert namesMaleLinkedList.size() == namesFemaleLinkedList.size()
                    : "Provide same length of male and female names";
            assert patientPopulation + doctorPopulation + chiefDoctorPopulation <= namesMaleLinkedList.size() + namesFemaleLinkedList.size() ||
                    patientPopulation + doctorPopulation + chiefDoctorPopulation <= lastNamesLinkedList.size()
                    : "Provided less population.";

            createChiefDoctors(chiefDoctorRepository, chiefDoctorPopulation);
            List<Doctor> doctorList = createDoctors(doctorRepository, doctorPopulation, measurementBound);
            List<Patient> patientArrayList = createPatients(patientRepository, patientPopulation, measurementPersonPopulation);

            int[] idx = {0};
            patientArrayList.forEach(patient -> {
                createCarbs(carbsRepository, measurementPersonPopulation[idx[0]], patient);

                List<Glucose> glucoseArrayList = createGlucose(glucoseRepository, measurementPersonPopulation[idx[0]], patient);
                glucoseArrayList.forEach(glucose -> createGlucoseRecords(glucoseRecordRepository, random.nextInt(1, 6), glucose));

                int maxConsultation = measurementPersonPopulation[idx[0]] / 31;
                int waitingPenalty = 0;
                if (maxConsultation > 0) {
                    boolean isWaiting = random.nextInt(0, 2) == 0;
                    if (isWaiting)
                        waitingPenalty = 1;

                    Doctor randomDoctor = doctorList.get(random.nextInt(doctorList.size()-1));
                    patientRepository.updateDoctorIdFromPatient(patient.getId(), randomDoctor.getId());
                    LocalDate[] localDates = new LocalDate[maxConsultation - waitingPenalty];
                    for (int i = 0; i < maxConsultation - waitingPenalty; i++)
                        localDates[i] = glucoseArrayList.get((i + 1) * 31).getDate();

                    maxConsultation -= waitingPenalty;
                    if (maxConsultation > 0) {
                        List<Consultation> consultationsList = createConsultation(consultationRepository, maxConsultation, patient, randomDoctor, localDates);
                        Consultation randomCons = consultationsList.get(consultationsList.size() > 0 ? random.nextInt(consultationsList.size()) : 0);
                        createMedication(medicationRepository, maxConsultation, randomCons);
                    }
                }
                idx[0]++;
            });
        };
    }

}
