// Alcides Gomes Beato Neto		19060987
// Henrique Sartori Siqueira	19240472
// Rafael Silva Barbon			19243633

import java.util.*;
import java.io.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

public class professor extends Info{
	LinkedList <String> horario = new LinkedList <String>(); // data e hora de entrada e saída
    public professor(String nome, String CPF, String email, String telefone, String codigo, Data nascimento, String senha){
        this.nome = nome;
        this.CPF = CPF;
        this.email = email;
        this.telefone = telefone;
        this.codigo = codigo;
        this.nascimento = nascimento;
		this.senha = senha;
    }

    @Override
	public void set_nome(String nome){
		super.nome = nome;
	}

    @Override
	public void set_CPF(String CPF){
		super.CPF = CPF;
	}

    @Override
	public void set_email(String email){
		super.email = email;
	}

    @Override
    public void set_telefone(String telefone){
		super.telefone = telefone;
	}

    @Override
	public void set_nascimento(Data data){
		super.nascimento = data;
	}

	@Override
	public void set_senha(String senhaAntiga, String senhaNova, String senhaMestre){
		if(senhaAntiga.equals(get_senha()) || senhaAntiga.equals(senhaMestre)){
			super.senha = senhaNova;
		}
	}

	public void registra_acesso(String hora){
		horario.add(hora);
	}

	public void renova_mes(){
		salvar_registro();
		EnviarEmail();
		horario.clear();
	}

	private void salvar_registro(){
		String nome_arquivo = get_codigo().concat(".txt");
		try{
			FileWriter comprovante = new FileWriter(nome_arquivo);

			PrintWriter escrever = new PrintWriter(comprovante);
			escrever.printf("Entradas e saídas relativas ao professor %s (%s):\n", get_nome(),get_codigo());
			for(String s : horario){
				escrever.printf("\t%s\n",s);
			}
			comprovante.close();
		}
		catch(IOException e){
			System.out.println("Erro ao criar o arquivo");
		}
	}

	private void EnviarEmail(){
		final String username = "dev.hours88@gmail.com";// Usuário (gmail)
		final String password = "Hd@4149!";// Senha
		String servidor = "smtp.gmail.com";
		// Configuração para configurar o servidor de email
		Properties configuracao = new Properties();
		configuracao.put("mail.smtp.auth", "true");
		configuracao.put("mail.smtp.starttls.enable", "true");
		configuracao.put("mail.smtp.host", servidor);
		configuracao.put("mail.smtp.port", "587");
		// Inicializa a sessão
		Session sessao = Session.getInstance(configuracao, new javax.mail.Authenticator(){
			protected PasswordAuthentication getPasswordAuthentication(){
				return new PasswordAuthentication(username, password);
			}
		});
		try{
			MimeMessage mensagem = new MimeMessage(sessao); // Cria o objeto mensagem
			mensagem.setFrom(new InternetAddress(username)); // Coloca o remetente do email
			mensagem.addRecipient(Message.RecipientType.TO, new InternetAddress(this.email)); // Adiciona o email do professor a ser enviado
			mensagem.setSubject("Ponto eletrônico - Relatório mensal"); // Assunto do email
			BodyPart corpo = new MimeBodyPart(); // Cria a mensagem do email
			corpo.setText("Olá " + this.nome + ",\n\nObrigado por fazer parte de nossa academia, segue em anexo o relatório mensal de acesso à academia. Tenha um bom dia.\n\nAtenciosamente,\nAcademia Cobra Kai. \n\n\nEste é um email automático, por favor não responda.\n\nDesenvolvido por RAH - Desenvolvimento de sistemas."); // A mensagem propriamente dita
			Multipart anexo = new MimeMultipart(); // Cria outra parte da mensagem (para o anexo e juntar com o texto)
			anexo.addBodyPart(corpo); // Adiciona o texto
			corpo = new MimeBodyPart(); // Adiciona o campo para anexo
			String arquivo = this.codigo.concat(".txt");
			DataSource caminho = new FileDataSource(arquivo);
			corpo.setDataHandler(new DataHandler(caminho)); // Coleta o arquivo para anexar
			corpo.setFileName(arquivo); // Coloca o nome do arquivo
			anexo.addBodyPart(corpo); // Junta o arquivo com o objeto do anexo
			mensagem.setContent(anexo); // Junta o anexo com a mensagem
			Transport.send(mensagem); // Envia o email
		}
		catch(MessagingException e){
			e.printStackTrace();
		}
	}

	// Método que exibe as informações do professor
	@Override
	public void exibe(){
		System.out.println();
		System.out.printf("Nome: %s\n", get_nome());
		System.out.printf("CPF: %s\n", get_CPF());
		System.out.printf("Data de nascimento: " + get_nascimento()+"\n");
		System.out.printf("Email: %s\n", get_email());
		System.out.printf("Telefone: %s\n", get_telefone());
		System.out.printf("Número codigo: %s\n", get_codigo());
		System.out.println("Registro de acesso:");
		//// Exibir o registro de entrada e saída
		for(String s : horario){
			System.out.printf("\t%s\n",s);
		}
		System.out.println();
	}

	// Método que retorna nome e código de um cliente para escolha de consulta
	public String toString(){
		return String.format("%s - %s ", get_nome(), get_codigo());
	}
}