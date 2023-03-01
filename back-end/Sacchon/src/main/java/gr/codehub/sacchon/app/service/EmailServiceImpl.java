package gr.codehub.sacchon.app.service;

import gr.codehub.sacchon.app.SacchonApplication;
import gr.codehub.sacchon.app.model.Email;
import gr.codehub.sacchon.app.repository.PatientRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private PatientRepository patientRepository;

    @Value("${spring.mail.username}") private String sender;

    public String sendSimpleMail(Email details) {
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();

            mailMessage.setFrom(sender);
            mailMessage.setTo(details.getRecipient());
            mailMessage.setText(details.getMsgBody());
            mailMessage.setSubject(details.getSubject());

            javaMailSender.send(mailMessage);
            return "Mail Sent Successfully...";
        }

        catch (Exception e) {
            return "Error while Sending Mail";
        }
    }

    @Scheduled(cron = "0 0 19 * * ?")
    public void checkForBirthdays() {
        LocalDate today = LocalDate.now();
        List<String> emailList = patientRepository.findPatientsWithBirthday("%" + today.toString().split("-")[1] + "-" + today.toString().split("-")[2]);

        if (emailList == null)
            return;

        if (!SacchonApplication.DEBUG_MODE)
            emailList.forEach(
                    email -> sendSimpleMail(new Email(
                            email,
                            "Doctors and stuff from Sacchon app wishes you all the best!",
                            "Happy Birthday!"
            )));
    }

    public void newConsultation(String email, String doctorName) {
        sendSimpleMail(new Email(
                email,
                doctorName + " send you a new consultation!",
                "Consultation Received")
        );
    }
}