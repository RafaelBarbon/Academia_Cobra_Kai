import java.util.*;
import javax.mail.*;


public class JavaMailApp{
	public static void main(String[] args) {
		Properties config = new Properties();
		// Parâmetros de conexão com servidor Gmail
		config.put("mail.smtp.host", "smtp.gmail.com");
		config.put("mail.smtp.socketFactory.port", "587");
		config.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
		config.put("mail.smtp.auth", "true");
		config.put("mail.smtp.starttls.enable", "true");

		Session session = Session.getDefaultInstance(config,new javax.mail.Authenticator(){
			protected PasswordAuthentication getPasswordAuthentication(){
				return new PasswordAuthentication("dev.hours88@gmail.com","Hd@4149!");
			}
		});

		// Ativa Debug para sessão
		//session.setDebug(true);

		try{

			Message messagem = new MimeMessage(session);
			messagem.setFrom(new InternetAddress("dev.hours88@gmail.com"));
			// Remetente

			Address[] toUser = InternetAddress.parse("rsb1@outlook.com.br"); // Destinatário(s)

			messagem.setRecipients(Message.RecipientType.TO, toUser);
			messagem.setSubject("Enviando email com JavaMail"); //Assunto
			messagem.setText("Enviei este email utilizando JavaMail com minha conta GMail, para te chamar de babaca! :)");
			// Método para enviar a mensagem criada
			Transport.send(messagem);

			System.out.println("Feito!!!");

		}
		catch(MessagingException e){
			throw new RuntimeException(e);
		}
	}
}