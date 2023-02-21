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

    private final int SEED = 42;
    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    private final LinkedList<String> namesMaleLinkedList = new LinkedList<>(List.of("Liam", "Noah", "Oliver", "Elijah", "James", "William", "Benjamin", "Lucas", "Henry", "Theodore"));
    private final LinkedList<String> namesFemaleLinkedList = new LinkedList<>(List.of("Olivia", "Emma", "Charlotte", "Amelia", "Ava", "Sophia", "Isabella", "Mia", "Evelyn", "Harper"));
    private final List<String> lastNamesLinkedList = new ArrayList<>(List.of("Smith", "Johnson", "William", "Brown", "Jones", "Garcia", "Miller", "Davis", "Rodriguez", "Martinez", "Hernandez", "Lopez", " Wilson", "Anderson", "Thomas", "Taylor", "Jackson", "Martin"));
    private final List<String> allergiesList = new ArrayList<>(List.of("Grass Pollen", "Dust", "Peanut", "Milk", "Egg", "Animal Fur", "Bee", "Wasp", "Fish", "Crustaceans", "Wheat", "Soy"));
    private final List<String> medicationsList = new ArrayList<>(List.of("Atorvastatin", "Levothyroxine", "Metformin", "Lisinopril", "Amlodipine", "Metoprolol", "Albuterol", "Omeprazole", "Losartan", "Gabapentin", "Hydrochlorothiazide", "Sertraline", "Simvastatin", "Montelukast", "Escitalopram", "Rosuvastatin", "Bupropion", "Furosemide", "Pantoprazole"));
    private final List<String> conditionsList = new ArrayList<>(List.of("Heart Disease", "Cancer", "Asthma", "Emphysema", "Alzheimer Disease", "Substance Abuse", "Pneumonia", "Kidney Disease", "Mental Health Conditions"));
    private final List<String> detailsList = new ArrayList<>(List.of("Take 2 pills per day", "Don't mix with alcohol!"));

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
        Random random = new Random(SEED);

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
            chiefDoctor.setEmail("ch." + firstName.charAt(0) + lastName + yearBorn + "@gmail.com");
            chiefDoctor.setPassword(RandomStringUtils.random(12, true, true));

            chiefDoctorArrayList.add(chiefDoctor);
        }

        chiefDoctorRepository.saveAll(chiefDoctorArrayList);
    }

    private List<Doctor> createDoctors(DoctorRepository doctorRepository, int population) {
        Random random = new Random(SEED);

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
            doctor.setEmail(firstName.charAt(0) + lastName + yearBorn + "@gmail.com");
            doctor.setPassword(RandomStringUtils.random(12, true, true));

            doctor.setPatients(null);
            doctor.setConsultations(null);

            doctorArrayList.add(doctor);
        }

        return doctorRepository.saveAll(doctorArrayList);
    }

    private List<Patient> createPatients(PatientRepository patientRepository, int population) {
        Random random = new Random(SEED);

        ArrayList<Patient> patientArrayList = new ArrayList<>();
        Patient patient;
        String firstName;
        String lastName;
        int yearBorn;
        List<String> allergies;
        List<String> medications;
        List<String> conditions;

        for (int i = 0; i < population; i++) {
            patient = new Patient();
            firstName = (namesMaleLinkedList.size() + namesFemaleLinkedList.size()) % 2 == 0 ? namesMaleLinkedList.pop() : namesFemaleLinkedList.pop();
            lastName = lastNamesLinkedList.get(i);
            yearBorn = 2005 - random.nextInt(50);
            patient.setId(0L);
            patient.setFirstName(firstName);
            patient.setLastName(lastName);
            patient.setEmail(firstName.charAt(0) + lastName + yearBorn + "@gmail.com");
            patient.setPassword(RandomStringUtils.random(12, true, true));
            patient.setAddress(random.nextInt(1000) + " Address St.");
            patient.setGender(i % 2 == 0 ? "Male" : "Female");
            patient.setDateOfBirth(LocalDate.of(yearBorn, random.nextInt(12) + 1, random.nextInt(27) + 1));
            patient.setBloodType(BloodType.values()[random.nextInt(BloodType.values().length - 1)]);
            patient.setDiabetesType(DiabetesType.values()[random.nextInt(DiabetesType.values().length - 1)]);
            patient.setHeight(150 + random.nextInt(50));
            patient.setWeight(50 + random.nextInt(70));

            allergies = new ArrayList<>();
            createRandomListSequence(allergiesList, allergies, 0.35, random);
            patient.setAllergies(allergies);

            medications = new ArrayList<>();
            createRandomListSequence(medicationsList, medications, 0.2, random);
            patient.setMedications(medications);

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

    private void createCarbs(CarbsRepository carbsRepository, int population, Patient assignedPerson) {
        Random random = new Random(SEED);

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
        Random random = new Random(SEED);

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

    private void createConsultation(ConsultationRepository consultationRepository, int population, Patient assignedPerson, Doctor assignedDoctor, LocalDate[] localDates) {
        Random random = new Random(SEED);

        ArrayList<Consultation> consultationArrayList = new ArrayList<>();
        Consultation consultation;

        List<String> medications = new ArrayList<>();
        createRandomListSequence(medicationsList, medications, 2, random);

        for (int i = 0; i < population; i++) {
            consultation = new Consultation();

            consultation.setId(0L);
            consultation.setDoctorFirstName(assignedDoctor.getFirstName());
            consultation.setDoctorLastName(assignedDoctor.getLastName());
            consultation.setDoctorEmail(assignedDoctor.getEmail());
            consultation.setDateCreated(localDates[i]);
            consultation.setSeenConsultation(random.nextBoolean());
            consultation.setMedications(medications);
            consultation.setDetails(detailsList.get(random.nextInt(detailsList.size())));
            consultation.setPatient(assignedPerson);
            consultation.setDoctor(assignedDoctor);

            consultationArrayList.add(consultation);
        }

        consultationRepository.saveAll(consultationArrayList);
    }

    @Bean
    CommandLineRunner PatientCommandLineRunner(
            PatientRepository patientRepository, CarbsRepository carbsRepository,
            GlucoseRepository glucoseRepository, GlucoseRecordRepository glucoseRecordRepository,
            DoctorRepository doctorRepository,
            ChiefDoctorRepository chiefDoctorRepository, ConsultationRepository consultationRepository){
        return args -> {

            if (!SacchonApplication.DEBUG_MODE)
                return;

            System.out.println("Saving dummy objects..");

            Random random = new Random(SEED);
            int patientPopulation = 13;
            int doctorPopulation = 4;
            int chiefDoctorPopulation = 1;
            int[] measurementPersonPopulation = new int[patientPopulation];
            for (int i = 0; i < patientPopulation; i++)
                measurementPersonPopulation[i] = random.nextInt(15, 125);

            Collections.shuffle(namesMaleLinkedList);
            Collections.shuffle(namesFemaleLinkedList);
            Collections.shuffle(lastNamesLinkedList);

            assert namesMaleLinkedList.size() == namesFemaleLinkedList.size() : "Provide same length of male and female names";
            assert (patientPopulation + doctorPopulation + chiefDoctorPopulation) <= namesMaleLinkedList.size() + namesFemaleLinkedList.size() ||
                    (patientPopulation + doctorPopulation + chiefDoctorPopulation) <= lastNamesLinkedList.size()
                    : "Provided less population.";

            createChiefDoctors(chiefDoctorRepository, chiefDoctorPopulation);
            List<Doctor> doctorList = createDoctors(doctorRepository, doctorPopulation);
            List<Patient> patientArrayList = createPatients(patientRepository, patientPopulation);

            int[] idx = {0};
            patientArrayList.forEach(patient -> {
                createCarbs(carbsRepository, measurementPersonPopulation[idx[0]], patient);

                List<Glucose> glucoseArrayList = createGlucose(glucoseRepository, measurementPersonPopulation[idx[0]], patient);
                glucoseArrayList.forEach(glucose -> createGlucoseRecords(glucoseRecordRepository, random.nextInt(1, 6), glucose));

                int maxConsultation = measurementPersonPopulation[idx[0]] / 30;
                if (maxConsultation > 0) {
                    boolean isWaiting = random.nextInt(0, 1) == 0;
                    if (isWaiting)
                        maxConsultation--;

                    if (maxConsultation > 0){
                        Doctor randomDoctor = doctorList.get(random.nextInt(doctorList.size()-1));
                        LocalDate[] localDates = new LocalDate[maxConsultation];
                        for (int i = 0; i < maxConsultation; i++) {
                            localDates[i] = glucoseArrayList.get((i + 1) * 30).getDate();
                        }
                        createConsultation(consultationRepository, maxConsultation, patient, randomDoctor, localDates);
                    }
                }
                idx[0]++;
            });

        };
    }
}
