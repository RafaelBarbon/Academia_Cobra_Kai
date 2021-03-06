// Alcides Gomes Beato Neto		19060987
// Henrique Sartori Siqueira	19240472
// Rafael Silva Barbon			19243633

import java.util.*;
import java.io.*;
import javax.swing.*;

public class professor extends Info{
	LinkedList <String> horario = new LinkedList <String>(); // Data e hora de entrada e saída
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
	public boolean set_senha(String senhaAntiga, String senhaNova, String senhaMestre){
		if(senhaAntiga.equals(get_senha()) || senhaAntiga.equals(senhaMestre)){
			super.senha = senhaNova;
			return true;
		}
		return false;
	}

	public void registra_acesso(String hora){
		horario.add(hora);
	}

	public void renova_mes(){
		horario.clear();
	}

	public void salvar_registro(){
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
			JOptionPane.showMessageDialog(null, "Não foi possível salvar o registro de acesso.", "Academia Cobra Kai" ,JOptionPane.ERROR_MESSAGE);
		}
	}

	public String EnviarEmail(){
		String dest = "",destinatario = "";
		dest = get_nome();
		for(int i = 0; i < dest.length(); i++){
			switch(dest.charAt(i)){
				case 'Á':
					destinatario = destinatario.concat("&Aacute;");
					break;
				case 'Ã':
					destinatario = destinatario.concat("&Atilde;");
					break;
				case 'Â':
					destinatario = destinatario.concat("&Acirc;");
					break;
				case 'Ç':
					destinatario = destinatario.concat("&Ccedil;");
					break;
				case 'É':
					destinatario = destinatario.concat("&Eacute;");
					break;
				case 'Ê':
					destinatario = destinatario.concat("&Ecirc;");
					break;
				case 'Í':
					destinatario = destinatario.concat("&Iacute;");
					break;
				case 'Î':
					destinatario = destinatario.concat("&Icirc;");
					break;
				case 'Ó':
					destinatario = destinatario.concat("&Oacute;");
					break;
				case 'Õ':
					destinatario = destinatario.concat("&Otilde;");
					break;
				case 'Ô':
					destinatario = destinatario.concat("&Ocirc;");
					break;
				case 'Ú':
					destinatario = destinatario.concat("&Uacute;");
					break;
				case 'Û':
					destinatario = destinatario.concat("&Ucirc;");
					break;
				default:
					destinatario += dest.charAt(i);
			}
		}
		return destinatario;
	}

	// Método que exibe as informações do professor
	@Override
	public String exibe(){
		String tudo = "Nome: " + get_nome() + "\nCPF: " + get_CPF() + "\nData de nascimento: " + get_nascimento() + "\nEmail: " + get_email() + "\nTelefone: " + get_telefone() + "\nCódigo: " + get_codigo() + "\nRegistros de acesso:";
		//// Exibir o registro de entrada e saída
		for(String s : horario){
			tudo = tudo.concat("\n\t" + s);
		}
		return tudo;
	}

	// Método que retorna nome e código de um cliente para escolha de consulta
	public String toString(){
		return String.format("%s - %s ", get_nome(), get_codigo());
	}
}
											