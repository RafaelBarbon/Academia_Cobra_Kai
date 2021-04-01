// Alcides Gomes Beato Neto		19060987
// Henrique Sartori Siqueira	19240472
// Rafael Silva Barbon			19243633

import java.util.*;

public class menu{
	public static void main(String[] args){
		Scanner input = new Scanner(System.in);

		int menu_option = 0, contador_carteirinha = 0, forma_pag = 0, dia = 0, mes = 0, ano = 0;
		boolean error = false, encontrou = false, invalido = false;
		String procurar, nome, CPF, email, telefone,carteirinha;
		aluno aluno_auxiliar;
		Data data = null;
		// Listas de alunos
        LinkedList <aluno> alunos = new LinkedList<aluno>();
		do{
			do{
				try{
					error = false;
					System.out.println("---MENU---\n");
					System.out.println("0 - Sair.");
					System.out.println("1 - Cadastro de Alunos."); // Cadastrar professores e aulas (submenu de cadastro)
					System.out.println("2 - Remoção de Alunos."); // Remover professores e aulas (submenu de remoção)
					System.out.println("3 - Consulta de Alunos."); // Consultar professores e aulas (submenu de consulta)
					System.out.println("4 - Entrada de aluno(s)"); // "Catraca"
					//System.out.println("4 - Sincronizar contas com o banco.");
					//System.out.println("5 - Vincular professor com sala");
					//System.out.println("6 - Vincular aluno com sala");
					//System.out.println("7 - Desvincular professor com sala");
					//System.out.println("8 - Desvincular aluno com sala");
					//System.out.println("9 - Atualizar informações aluno");
					System.out.print("-> ");
					menu_option = input.nextInt();
				}
				catch(InputMismatchException InputMismatchException){
					error = true;
					System.out.println("Insira um número inteiro dentro do intervalo do menu para prosseguir. Pressione qualquer tecla para continuar.");
					input.nextLine();
				}
			}while(error || menu_option > 4 || menu_option < 0);
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
					invalido = true;
					do{
						System.out.printf("Email ->"); // * @ *.com* // FUNÇÃO QUE VERIFICA O EMAIL
						email = input.nextLine();
						email = email.toLowerCase();
						if(email.matches("^(?=.{1,64}@)[a-z0-9_-]+(\\.[a-z0-9_-]+)*@"/*nome do email*/+"[^-][a-z0-9-]+(\\.[a-z0-9-]+)*(\\.[a-z]{2,})$")/*domínio*/){
							/*
							^(?=.{1,64}@) -> define o espaço máximo do email (entre 1 e 64 caracteres antes do '@')
							[a-z0-9_-]+ -> indica o primeiro caractere do email, sendo que não se pode iniciar com '.', '+' representa mais de um
							(\\.[a-z0-9_-]+)*@ -> // indica que é seguido pelo intervalo de caracteres definidos, incluindo o '.', "*@" indica que deve conter caracteres antes do '@'
							[^-] -> indica que não se pode começar com '-'
							(\\.[a-z0-9-]+)*(\\.[a-z]{2,})$ -> deve conter o domínio separado por ponto e, após o ponto, no mínimo 2 caracteres
							*/
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
						if((telefone.length() == 10 || telefone.length() == 11) && telefone.matches("[0-9]+")){ // Verifica se existe somente números e verifica o tamanho (caso de celular / telefone fixo)
							invalido = false;
						}
						else{
							System.out.println("Telefone inválido, tente novamente.");
						}
					}while(invalido);
					invalido = true;
					do{
						System.out.println("Data de nascimento.");
						error = true;
						do{
							try{
								error = false;
								System.out.printf("Dia ->");
								dia = input.nextInt();
							}
							catch(InputMismatchException InputMismatchException){
								error = true;
								System.out.println("Insira um número inteiro para prosseguir. Pressione qualquer tecla para continuar.");
								input.nextLine();
								input.nextLine();
								clear();
							}
						}while(error);
						error = true;
						do{
							try{
								error = false;
								System.out.printf("Mes ->");
								mes = input.nextInt();
							}
							catch(InputMismatchException InputMismatchException){
								error = true;
								System.out.println("Insira um número inteiro para prosseguir. Pressione qualquer tecla para continuar.");
								input.nextLine();
								input.nextLine();
								clear();
							}
						}while(error);
						error = true;
						do{
							try{
								error = false;
								System.out.printf("Ano ->");
								ano = input.nextInt();
							}
							catch(InputMismatchException InputMismatchException){
								error = true;
								System.out.println("Insira um número inteiro para prosseguir. Pressione qualquer tecla para continuar.");
								input.nextLine();
								input.nextLine();
								clear();
							}
						}while(error);
						try{
							invalido = false;
							data = new Data(dia, mes, ano);
						}
						catch(IllegalArgumentException VariableDeclaratorId){
							invalido = true;
							System.out.println(VariableDeclaratorId);
							System.out.println("Pressione qualquer tecla para continuar.");
							input.nextLine();
							input.nextLine();
							clear();
						}
					}while(invalido);
					forma_pag = 1;
					do{//Inserção da forma de pagamento.
						if(forma_pag > 2 || forma_pag < 1){
							System.out.println("Insira um número inteiro dentro do intervalo para prosseguir. Pressione qualquer tecla para continuar.");
							input.nextLine();
							input.nextLine();
							clear();
						}
						try{
							error = false;
							System.out.printf("Forma de pagamento (1-Boleto; 2-Débito Automático) ->");
							forma_pag = input.nextInt();
						}
						catch(InputMismatchException InputMismatchException){
							error = true;
							System.out.println("Insira um número inteiro para prosseguir. Pressione qualquer tecla para continuar.");
							input.nextLine();
							input.nextLine();
							clear();
						}
					}while(error || forma_pag > 2 || forma_pag < 1);
					contador_carteirinha++;
					aluno_auxiliar = new aluno(nome,formataCPF(CPF),email,telefone,Integer.toString(contador_carteirinha),forma_pag,data);
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
							System.out.println("Aluno removido com sucesso");
							encontrou = true;
							break;
						}
					}
					if(!encontrou){
						System.out.println("Aluno(s) inexistente(s) no sistema");
					}
					try{
						Thread.sleep(2000);
					}
					catch(InterruptedException e){
					}
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
						System.out.println("Aluno(s) inexistente(s) no sistema.");
					}
					System.out.println("Pressione ENTER para continuar.");
					input.nextLine();
					clear();
					break;
				case 4:
					System.out.print("Insira a carteirinha do aluno que deseja entrar\n->");
					input.nextLine();
					carteirinha = input.nextLine();
					encontrou = false;
					for(aluno al : alunos){
						if(al.get_carteirinha().equals(carteirinha)){
							encontrou = true;
							if(al.get_mes_pago()){
								System.out.printf("Seja bem vindo %s.", al.get_nome());
								break;
							}
							else{
								System.out.println("Aluno inadimplente.");
								break;
							}
						}
					}
					if(!encontrou){
						System.out.println("Aluno inexistente no sistema.");
					}
					try{
						Thread.sleep(2000);
					}
					catch(InterruptedException e){
					}
					clear();
					break;
				default: // Sair do sistema
					System.out.println("Obrigado por utilizar o sistema desenvolvido por RAH - Desenvolvimento de Sistemas.");
					try{
						Thread.sleep(5000);
					}
					catch(InterruptedException e){
					}
					clear();
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
		String newCPF = CPF.substring(0,3) + '.' + CPF.substring(3,6) + '.' + CPF.substring(6,9) + '-' + CPF.substring(9);
		return newCPF;
	}

	// Método que lista os nomes e carteirinhas para a consulta
	public static <T> void exibir(LinkedList <T> lista){
		for(T aux : lista){
			System.out.printf("\t%s", aux);
		}
	}
}