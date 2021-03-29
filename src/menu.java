// Alcides Gomes Beato Neto		19060987
// Henrique Sartori Siqueira	19240472
// Rafael Silva Barbon			19243633

import java.util.*;

public class menu{
	public static void main(String[] args){
		Scanner input = new Scanner(System.in);
		int menu_option = 0, codigo_carteirinha = 0;
		boolean error = false, encontrou = false, invalido = false;
		String procurar, nome, CPF, email, telefone;
		aluno aluno_auxiliar;
		// Listas de alunos
        LinkedList <aluno> alunos = new LinkedList<aluno>();
		do{
			do{
				try{
					error = false;
					System.out.println("---MENU---\n");
					System.out.println("0 - Sair.");
					System.out.println("1 - Cadastro de Alunos.");
					System.out.println("2 - Remoção de Alunos.");
					System.out.println("3 - Consulta de Alunos.");
					//System.out.println("4 - Sincronizar contas com o banco.");
					System.out.print("-> ");
					menu_option = input.nextInt();
				}
				catch(InputMismatchException InputMismatchException){
					error = true;
					System.out.println("Insira um número inteiro dentro do intervalo do menu para prosseguir. Pressione qualquer tecla para continuar.");
					input.nextLine();
				}
			}while(error || menu_option > 3 || menu_option < 0);
			clear();
			switch(menu_option){
				case 1: // Cadastro de aluno
					/*
					coleta nome, CPF, email, telefone, RG, conta bancária
					verifica se existe conflito de CPF
					gera um número de carteirinha
					*/
					System.out.printf("Nome do Aluno ->");
					nome = input.nextLine();
					nome = input.nextLine();
					nome = nome.toUpperCase();
					invalido = true;
					do{//Verifica conflito CPF
						System.out.printf("CPF->"); // 000 000 000 00
						CPF = input.nextLine();
						if(CPF.matches("[0-9]+") && CPF.length() == 11){
							invalido = false;
						}
						for(aluno al : alunos){
							if(CPF.equals(al.get_CPF())){
								invalido = true;
								break;
							}
						}
						if(invalido){
							System.out.println("CPF inválido, tente novamente digitando apenas números.");
						}
					}while(invalido);
					//1CPF = formataCPF(CPF);
					invalido = true;
					do{
						System.out.printf("Email ->"); // * @ *.com* // FUNÇÃO QUE VERIFICA O EMAIL
						email = input.nextLine();
						email = email.toLowerCase();
						if(confirmaEmail(email)){
							invalido = false;
						}
						else{
							System.out.println("Email inválido, tente novamente.");
						}
					}while(invalido);
					invalido = true;
					do{
						System.out.printf("Telefone ->"); // 00 &0000 0000 // DDD + (1) + 8 NºS
						telefone = input.nextLine();
						if((telefone.length() == 10 || telefone.length() == 11) && telefone.matches("[0-9]+")){
							invalido = false;
						}
					}while(invalido);
					aluno_auxiliar = new aluno(nome,CPF,email,telefone);
					codigo_carteirinha++;
					aluno_auxiliar.set_carteirinha(Integer.toString(codigo_carteirinha));
					alunos.add(aluno_auxiliar);
					System.out.println("Aluno cadastrado com sucesso. Pressione ENTER para continuar.");
					input.nextLine();
					clear();
					break;
				case 2: // Remoção de aluno
					/*
					coleta nº da carteirinha
					confere se existe
					remove
					*/
					exibir(alunos);
					System.out.print("\n->");
					procurar = input.nextLine();
					procurar = input.nextLine();
					encontrou = false;
					for(aluno al : alunos){
						if(procurar.equals(al.get_carteirinha())){
							alunos.remove(al);
							System.out.println("Aluno removido com sucesso. Pressione ENTER para continuar");
							encontrou = true;
							break;
						}
					}
					if(!encontrou){
						System.out.println("Aluno inexistente no sistema. Pressione ENTER para continuar");
					}
					input.nextLine();
					clear();
					break;
				case 3: // Consulta de aluno
					/*
					coleta nº da carteirinha
					confere se existe
					coleta as infos & exibe
					*/
					exibir(alunos);
					System.out.print("\n->");
					procurar = input.nextLine();
					procurar = input.nextLine();
					encontrou = false;
					for(aluno al : alunos){
						if(procurar.equals(al.get_carteirinha())){
							al.exibe();
							encontrou = true;
							break;
						}
					}
					if(!encontrou){
						System.out.println("Aluno inexistente no sistema.");
					}
					System.out.println("Pressione ENTER para continuar.");
					input.nextLine();
					clear();
					break;
				default: // Sair do sistema
					System.out.println("Obrigado por utilizar o sistema desenvolvido por RAH - Desenvolvimento de Sistemas.");
			}
		}while(menu_option != 0);
	}

	// Método que limpa a tela do terminal
	public final static void clear(){
		try{
			final String os = System.getProperty("os.name");
			if(os.contains("Windows")){ // Caso o sistema operacional da máquina seja Windows
				Runtime.getRuntime().exec("cls");
			}
			else{ // Caso o sistema operacional da máquina seja Linux ou MAC
				System.out.print("\33\143"); // 33 - limpa a tela ; 143 volta o cursor de texto para o início
			}
		}
		catch(final Exception e){
			System.out.println("\n\tNão foi possível limpar a tela.");
		}
	}

	// Formata o CPF do aluno (00000000000 -> 000.000.000-00)
	public static String formataCPF(String CPF){
		char [] CPFC = CPF.toCharArray(), CPFF = {'0','0','0','.','0','0','0','.','0','0','0','-','0','0','\0'};
		for(int i = 0,j = 0; i < 12; i++){
			if(i == 3 || i == 6 || i == 9){
				j++;
			}
				CPFF[j++] = CPFC[i];
		}
		return CPFF.toString();
	}

	public static boolean confirmaEmail(String email){
		char [] confere = email.toCharArray();

		if(confere[0] < 97 && confere[0] > 122)
			return false;
		for(int i = 1; i < email.length(); i++){
			if(confere[i] == '@'){
				for(; i+3 < email.length(); i++){
					if(confere[i] == '.' && confere[i+1] == 'c' && confere[i+2] == 'o' && confere[i+3] == 'm'){
						return true;
					}
				}
			}
		}
		return false;
	}

	// Método que lista os nomes e carteirinhas para a consulta
	public static <T> void exibir(LinkedList <T> lista){
		for(T aux : lista){
			System.out.printf("\t%s", aux);
		}
	}
}