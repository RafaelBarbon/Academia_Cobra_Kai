import java.util.*;
import javax.mail.*;
import javax.mail.Part.*;
import javax.mail.internet.*;
import javax.activation.*;

public class SimpleEmail{
	public static void main(String[] args) {
 		// Add recipient
		String to = "jemis.djm@puccampinas.edu.br";

		// Add sender
 		String from = "dev.hours88@gmail.com";
 		final String username = "dev.hours88@gmail.com";//your Gmail username
 		final String password = "Hd@4149!";//your Gmail password

		String host = "smtp.gmail.com";

		Properties props = new Properties();
 		props.put("mail.smtp.auth", "true");
 		props.put("mail.smtp.starttls.enable", "true");
 		props.put("mail.smtp.host", host);
 		props.put("mail.smtp.port", "587");

		// Get the Session object
 		Session session = Session.getInstance(props, new javax.mail.Authenticator(){
 			protected PasswordAuthentication getPasswordAuthentication(){
 				return new PasswordAuthentication(username, password);
 			}
 		});
		int i = 500;
		while(i!=0){
			try{
				// Create a default MimeMessage object.
				MimeMessage message = new MimeMessage(session);

				// Set From: header field of the header.
				message.setFrom(new InternetAddress(from));

				// Set To: header field of the header.
				message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));

				// Set Subject: header field
				message.setSubject("Douchbag");

				// Create the message part
				BodyPart messageBodyPart = new MimeBodyPart();

				// Fill the message
				messageBodyPart.setText("This is an email for the douchbag, please open the attachment to get more information");

				// Create a multipar message
				Multipart multipart = new MimeMultipart();

				// Set text message part
				multipart.addBodyPart(messageBodyPart);

				// Part two is attachment
				messageBodyPart = new MimeBodyPart();
				String filename = "file.txt";
				DataSource source = new FileDataSource("test/".concat(filename));
				messageBodyPart.setDataHandler(new DataHandler(source));
				messageBodyPart.setFileName(filename);
				multipart.addBodyPart(messageBodyPart);

				// Send the complete message parts
				message.setContent(multipart);

				// Send message
				Transport.send(message);
				System.out.println("Sent message successfully....");
			}
			catch(MessagingException mex){
				mex.printStackTrace();
			}
			i--;
			try{
			Thread.sleep(500);
			}
			catch(InterruptedException e){}
		}
 	}
}
