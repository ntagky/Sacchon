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

/** This is the initial configuration file of the Sacchon2023 project
 * Its aim is to create and populate the database with entities. If the
 * user is running the application in test mode it populates the DEVELOP database
 * while if the user runs it in production mode it populates the PRODUCTION database.
 * The creation of the population is done using randomized seeds to achieve maximum randomization.
 * Developers have the opportunity to choose their own size of populations and create instances
 * depicting a real-life production environment. Glucose levels are generated through a Gaussian
 * distribution in order to implement more realistic scenarios and outliers.
 */
@Configuration
public class InitialConfiguration {

    private final Random RANDOM = new Random(42);
    private final int PATIENT_POPULATION = 3;
    private final int DOCTOR_POPULATION = 1;
    private final int CHIEF_DOCTOR_POPULATION = 1;
    private final int[] PERSONS_MEASUREMENT_POPULATION = new int[PATIENT_POPULATION];
    private final int MEASUREMENTS_AMOUNT_ORIGIN = 70;
    private final int MEASUREMENTS_AMOUNT_BOUND = 120;
    private final int GLUCOSE_AMOUNT_ORIGIN = 1;
    private final int GLUCOSE_AMOUNT_BOUND = 6;
    private final int MEDICATIONS_AMOUNT_ORIGIN = 1;
    private final int MEDICATIONS_AMOUNT_BOUND = 4;
    private final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    private final LinkedList<String> namesMaleLinkedList = new LinkedList<>(List.of("Liam", "Noah", "Oliver", "Elijah", "James", "William", "Benjamin", "Lucas", "Henry", "Theodore", "Amiri", "Kevin", "Jason", "Jeffrey", "Jacob", "Gary", "Eric", "Nicolas", "Jonathan", "Tyler", "Samuel", "Gregory", "Alexander", "Frank", "Patrick"));
    private final LinkedList<String> namesFemaleLinkedList = new LinkedList<>(List.of("Olivia", "Emma", "Charlotte", "Amelia", "Ava", "Sophia", "Isabella", "Mia", "Evelyn", "Harper", "Debra", "Maria", "Olivia", "Joyce", "Ruth", "Janet", "Samantha", "Stella", "Helen", "Evelyn", "Katherine", "Christine", "Debra", "Rachel", "Carolyn"));
    private final List<String> lastNamesLinkedList = new ArrayList<>(List.of("Smith", "Johnson", "William", "Brown", "Jones", "Garcia", "Miller", "Davis", "Rodriguez", "Martinez", "Hernandez", "Lopez", "Wilson", "Anderson", "Thomas", "Taylor", "Jackson", "Martin", "Moore", "Lee", "Perez", "Thompson", "White", "Walker", "Hill", "Torres", "Scot", "Young"));
    private final List<String> ALLERGIES_LIST = new ArrayList<>(List.of("Grass Pollen", "Dust", "Peanut", "Milk", "Egg", "Animal Fur", "Bee", "Wasp", "Fish", "Crustaceans", "Wheat", "Soy"));
    private final List<String> MEDICATION_NAMES_LIST = new ArrayList<>(List.of("Atorvastatin", "Levothyroxine", "Metformin", "Lisinopril", "Amlodipine", "Metoprolol", "Albuterol", "Omeprazole", "Losartan", "Gabapentin", "Hydrochlorothiazide", "Sertraline", "Simvastatin", "Montelukast", "Escitalopram", "Rosuvastatin", "Bupropion", "Furosemide", "Pantoprazole"));
    private final List<String> CONDITIONS_LIST = new ArrayList<>(List.of("Heart Disease", "Cancer", "Asthma", "Emphysema", "Alzheimer Disease", "Substance Abuse", "Pneumonia", "Kidney Disease", "Mental Health Conditions"));
    private final List<String> DOSOLOGE_LIST = new ArrayList<>(List.of("Take 2 pills per day", "After each meal", "Before bed only", "One before eating lunch, one after", "At the morning"));
    private final List<String> DETAILS_LIST = new ArrayList<>(List.of("Read carefully the label on the container.", "Don't mix with alcohol", "Do not mix medicine into hot drinks.", "Take medicine with a full glass of water.", "Read directions, warnings and interaction precautions printed on medicine label.", "Don’t take vitamin pills at the same time you take medicine", "Take medicine on time", "Store your medicines in a cool, dry place."));

    private String convertToTens(int number) { return number > 10 ? String.valueOf(number) : "0" + number; }

    private void createRandomListSequence(List<String> referenceList, List<String> arrayList, double probability, Random random) {
        if (Math.random() < probability) {
            int idx = random.nextInt(0, referenceList.size());
            while (idx < referenceList.size()) {
                arrayList.add(referenceList.get(idx));
                idx = random.nextInt(idx, referenceList.size() + 5);
            }
        }
    }

    private LocalDate getDateBeforeToday(int minusDays) {
        Date date;
        try {
            date = DATE_FORMAT.parse(LocalDate.now().toString());
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
            yearBorn = 1985 - RANDOM.nextInt(40);

            chiefDoctor.setId(0L);
            chiefDoctor.setFirstName(firstName);
            chiefDoctor.setLastName(lastName);
            chiefDoctor.setEmail("ch." + firstName.toLowerCase().charAt(0) + lastName.toLowerCase() + yearBorn + "@gmail.com");
            chiefDoctor.setPassword(RandomStringUtils.random(12, true, true));
            chiefDoctor.setSignedDate(getDateBeforeToday(RANDOM.nextInt(150)));

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
            yearBorn = 1995 - RANDOM.nextInt(40);

            doctor.setId(0L);
            doctor.setFirstName(firstName);
            doctor.setLastName(lastName);
            doctor.setEmail(firstName.toLowerCase().charAt(0) + lastName.toLowerCase() + yearBorn + "@gmail.com");
            doctor.setPassword(RandomStringUtils.random(12, true, true));
            doctor.setSignedDate(getDateBeforeToday(RANDOM.nextInt(assignedBefore, assignedBefore + 30)));

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
        List<String> conditions;

        for (int i = 0; i < population; i++) {
            patient = new Patient();
            firstName = (namesMaleLinkedList.size() + namesFemaleLinkedList.size()) % 2 == 0 ? namesMaleLinkedList.pop() : namesFemaleLinkedList.pop();
            lastName = lastNamesLinkedList.get(i);
            yearBorn = 2005 - RANDOM.nextInt(50);
            monthBorn = RANDOM.nextInt(12) + 1;
            dayBorn = RANDOM.nextInt(27) + 1;

            patient.setPhoneNumber("69"+RandomStringUtils.random(8, false, true));
            patient.setId(0L);
            patient.setFirstName(firstName);
            patient.setLastName(lastName);
            patient.setEmail(firstName.toLowerCase().charAt(0) + lastName.toLowerCase() + yearBorn + "@gmail.com");
            patient.setSignedDate(getDateBeforeToday(assignedBefore[i]));

            patient.setMedicalRecordNumber(convertToTens(dayBorn) + convertToTens(monthBorn) + yearBorn + "" + RandomStringUtils.random(5, false, true));
            patient.setPassword(RandomStringUtils.random(12, true, true));
            patient.setAddress(RANDOM.nextInt(1000) + " Address St.");
            patient.setGender(i % 2 == 0 ? "Male" : "Female");
            patient.setDateOfBirth(LocalDate.of(yearBorn, monthBorn, dayBorn));
            patient.setBloodType(BloodType.values()[RANDOM.nextInt(BloodType.values().length)]);
            patient.setDiabetesType(DiabetesType.values()[RANDOM.nextInt(DiabetesType.values().length)]);
            patient.setHeight(150 + RANDOM.nextInt(50));
            patient.setWeight(50 + RANDOM.nextInt(70));

            allergies = new ArrayList<>();
            createRandomListSequence(ALLERGIES_LIST, allergies, 0.35, RANDOM);
            patient.setAllergies(allergies);

            conditions = new ArrayList<>();
            createRandomListSequence(CONDITIONS_LIST, conditions, 0.1, RANDOM);
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

        for (int i = 0; i < population; i++) {
            medication = new Medication();

            medication.setId(0L);
            medication.setMedName(MEDICATION_NAMES_LIST.get(RANDOM.nextInt(MEDICATION_NAMES_LIST.size())));
            medication.setDosage(DOSOLOGE_LIST.get(RANDOM.nextInt(DOSOLOGE_LIST.size())));
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
            carbs.setMeasurement(RANDOM.nextInt(1500, 3000));
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
                .stream(RANDOM.ints(population, 8, 23).toArray())
                .boxed()
                .distinct()
                .sorted()
                .toList();

        for (Integer hour : hours) {
            glucoseRecord = new GlucoseRecord();

            glucoseRecord.setId(0L);
            glucoseRecord.setTime(LocalTime.of(hour, RANDOM.nextInt(60), RANDOM.nextInt(59)));
            glucoseRecord.setMeasurement(BigDecimal.valueOf(Math.abs(RANDOM.nextGaussian()) * 100 + 100));
            glucoseRecord.setGlucose(assignedGlucose);

            glucoseRecordArrayList.add(glucoseRecord);
        }

        glucoseRecordRepository.saveAll(glucoseRecordArrayList);
    }

    private List<Consultation> createConsultation(ConsultationRepository consultationRepository, int population, Patient assignedPerson, Doctor assignedDoctor, LocalDate[] localDates) {
        ArrayList<Consultation> consultationArrayList = new ArrayList<>();
        Consultation consultation;

        for (int i = 0; i < population; i++) {
            consultation = new Consultation();

            consultation.setId(0L);
            consultation.setDoctorFirstName(assignedDoctor.getFirstName());
            consultation.setDoctorLastName(assignedDoctor.getLastName());
            consultation.setDoctorEmail(assignedDoctor.getEmail());
            consultation.setDateCreated(localDates[i]);
            consultation.setSeenConsultation(RANDOM.nextBoolean());
            consultation.setMedications(null);
            consultation.setDetails(DETAILS_LIST.get(RANDOM.nextInt(DETAILS_LIST.size())));
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

            for (int i = 0; i < PATIENT_POPULATION; i++)
                PERSONS_MEASUREMENT_POPULATION[i] = RANDOM.nextInt(MEASUREMENTS_AMOUNT_ORIGIN, MEASUREMENTS_AMOUNT_BOUND);

            Collections.shuffle(namesMaleLinkedList);
            Collections.shuffle(namesFemaleLinkedList);
            Collections.shuffle(lastNamesLinkedList);

            assert namesMaleLinkedList.size() == namesFemaleLinkedList.size()
                    : "Provide same length of male and female names";
            assert PATIENT_POPULATION + DOCTOR_POPULATION + CHIEF_DOCTOR_POPULATION <= namesMaleLinkedList.size() + namesFemaleLinkedList.size()
                    : "Provided less population than the sum of names.";

            createChiefDoctors(chiefDoctorRepository, CHIEF_DOCTOR_POPULATION);
            List<Doctor> doctorList = createDoctors(doctorRepository, DOCTOR_POPULATION, MEASUREMENTS_AMOUNT_BOUND);
            List<Patient> patientArrayList = createPatients(patientRepository, PATIENT_POPULATION, PERSONS_MEASUREMENT_POPULATION);

            int[] idx = {0};
            patientArrayList.forEach(patient -> {
                createCarbs(carbsRepository, PERSONS_MEASUREMENT_POPULATION[idx[0]], patient);

                List<Glucose> glucoseArrayList = createGlucose(glucoseRepository, PERSONS_MEASUREMENT_POPULATION[idx[0]], patient);
                glucoseArrayList.forEach(glucose -> createGlucoseRecords(glucoseRecordRepository, RANDOM.nextInt(GLUCOSE_AMOUNT_ORIGIN, GLUCOSE_AMOUNT_BOUND), glucose));

                int maxConsultation = PERSONS_MEASUREMENT_POPULATION[idx[0]] / 31;
                if (maxConsultation > 0) {
                    boolean isWaiting = RANDOM.nextInt(0, 2) == 0;
                    if (isWaiting)
                        maxConsultation -= 1;

                    if (maxConsultation> 0) {
                        Doctor randomDoctor = doctorList.get(RANDOM.nextInt(doctorList.size()));
                        patientRepository.updateDoctorIdFromPatient(patient.getId(), randomDoctor.getId());
                        LocalDate[] localDates = new LocalDate[maxConsultation];
                        for (int i = 0; i < maxConsultation; i++)
                            localDates[i] = glucoseArrayList.get((i + 1) * 31).getDate();

                        List<Consultation> consultationsList = createConsultation(consultationRepository, maxConsultation, patient, randomDoctor, localDates);
                        final int[] i = {0};
                        consultationsList.forEach(consultation -> createMedication(medicationRepository, RANDOM.nextInt(MEDICATIONS_AMOUNT_ORIGIN,MEDICATIONS_AMOUNT_BOUND), consultationsList.get(i[0]++)));
                    }
                }
                idx[0]++;
            });
        };
    }

}
