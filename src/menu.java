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
					do{//Verifica conflito CPF
						invalido = false;
						System.out.printf("CPF->"); // 000 000 000 00
						CPF = input.nextLine();
						for(aluno al : alunos){
							if(CPF.equals(al.get_CPF()) || CPF.matches("[0-9]+")){
								invalido = true;
								break;
							}
						}
					}while(invalido);
					CPF = formataCPF(CPF);
					System.out.printf("Email ->"); // * @ *.com* // FUNÇÃO QUE VERIFICA O EMAIL
					email = input.nextLine();
					email = email.toLowerCase();
					System.out.printf("Telefone ->"); // 00 &0000 0000 // DDD + (1) + 8 NºS
					telefone = input.nextLine();
					aluno_auxiliar = new aluno(nome,CPF,email,telefone);
					codigo_carteirinha++;
					aluno_auxiliar.set_carteirinha(Integer.toString(codigo_carteirinha));
					alunos.add(aluno_auxiliar);
					break;
				case 2: // Remoção de aluno
					/*
					coleta nº da carteirinha
					confere se existe
					remove
					*/
					exibir(alunos);
					procurar = input.nextLine();
					procurar = input.nextLine();
					encontrou = false;
					for(aluno al : alunos){
						if(procurar.equals(al.get_carteirinha())){
							alunos.remove(al);
							System.out.println("Aluno removido com sucesso.");
							encontrou = true;
							break;
						}
					}
					if(!encontrou){
						System.out.println("Aluno inexistente no sistema.");
					}
					break;
				case 3: // Consulta de aluno
					/*
					coleta nº da carteirinha
					confere se existe
					coleta as infos & exibe
					*/
					exibir(alunos);
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
		char [] CPFC = CPF.toCharArray(), CPFF = {'0','0','0','.','0','0','0','.','0','0','0','-','0','0'};
		for(int i = 0,j = 0; i < 12; i++){
			if(i == 3 || i == 6 || i == 9){
				j++;
			}
				CPFF[j++] = CPFC[i];
		}
		return CPFF.toString();
	}

	// Método que lista os nomes e carteirinhas para a consulta
	public static <T> void exibir(LinkedList <T> lista){
		for(T aux : lista){
			System.out.printf("\t%s", aux);
		}
	}
}