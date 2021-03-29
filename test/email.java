// https://www.botecodigital.dev.br/java/enviando-e-mail-em-java-com-api-commons-email-da-apache/

// Download de duas bibliotecas

/// http://commons.apache.org/proper/commons-email/download_email.cgi
/// https://javaee.github.io/javamail/

// Gerar os boletos

/// http://www.jmssolucoes.com.br/boleto-bradesco.aspx

import java.util.*;

public class menu{
	public static void main(String[] args){
		HtmlEmail email = new HtmlEmail();
		email.setSSLOnConnect(true); // True se existir conexão SSL
		email.setHostName("smtp.seudominio.com.br"); // domínio do servidor de emails
		email.setSslSmtpPort("465"); // Código SSL do servidor de emails (opcional)
		email.setAuthenticator(new DefaultAuthenticator("rodrigo@seudominio.com.br", "1234")); // email e senha do servidor de emails

		try{
			email.setFrom("rodrigo@seudominio.com.br", "Rodrigo Aramburu"); // Remetente do email
			email.setDebug(true);
			email.setSubject("Boleto Mẽs"); // Assunto do email

			EmailAttachment anexo = new EmailAttachment();
			anexo.setPath("boteco.pdf"); // Caminho até o arquivo
			anexo.setDisposition(EmailAttachment.ATTACHMENT);
			anexo.setName("boteco.pdf"); // Nome do arquivo no email
			email.attach(anexo);
			email.setHtmlMsg("Caro aluno,\n\nSegue o boleto referente a mensalidade deste mês.\n\nAtenciosamente,\nAcademia Cobra Kai."); // Texto do email
			email.addTo("rodrigo@botecodigital.info"); // Destinatário do email
			email.send();
		}
		catch(EmailException e){
			e.printStackTrace();
		}
	}
}