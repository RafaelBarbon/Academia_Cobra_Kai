// Alcides Gomes Beato Neto		19060987
// Henrique Sartori Siqueira	19240472
// Rafael Silva Barbon			19243633

import java.util.*;
import java.io.*;

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
		/////salvar em algum lugar para enviar por email
		salvar_registro();
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