import java.util.*;
import javax.mail.*;

public class JavaMailApp{
	public static void main(String[] args) {
		Properties props = new Properties();
		// Parâmetros de conexão com servidor Gmail
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");

		Session session = Session.getDefaultInstance(props,new javax.mail.Authenticator(){
			protected PasswordAuthentication getPasswordAuthentication(){
				return new PasswordAuthentication("dev.hours88@gmail.com","Hd@4149!");
			}
		});

		// Ativa Debug para sessão
		session.setDebug(true);

		try{

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("dev.hours88@gmail.com"));
			// Remetente

			Address[] toUser = InternetAddress.parse("rsb1@outlook.com.br"); // Destinatário(s)

			message.setRecipients(Message.RecipientType.TO, toUser);
			message.setSubject("Enviando email com JavaMail"); //Assunto
			message.setText("Enviei este email utilizando JavaMail com minha conta GMail!");
			// Método para enviar a mensagem criada
			Transport.send(message);

			System.out.println("Feito!!!");

		}
		catch(MessagingException e){
			throw new RuntimeException(e);
		}
	}
}