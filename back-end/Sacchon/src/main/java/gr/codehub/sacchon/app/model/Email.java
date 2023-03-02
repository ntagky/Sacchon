package gr.codehub.sacchon.app.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 The Email class represents an email structure.
 It is used to send notifications to the Patient whenever they have an unseen consultation, or they have their birthday.

 It contains the following properties:

 recipient: Recipient of the email
 msgBody: Main body of the email
 subject: Subject of the email

 @author Georgia Giannokosta
 @version 1.0
 @since 2023-03-02
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Email {
    private String recipient;
    private String msgBody;
    private String subject;
}