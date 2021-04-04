// Alcides Gomes Beato Neto		19060987
// Henrique Sartori Siqueira	19240472
// Rafael Silva Barbon			19243633

import java.util.*;

public class menu{
	public static void main(String[] args){
		Scanner input = new Scanner(System.in);

		int menu_option = 0, submenu_option = 0, contador_codigo_aluno = 0, contador_codigo_professor = 0, contador_codigo_aula = 0,forma_pag = 1, dia = 0, mes = 0, ano = 0, nivel = 1, periodo = 1, armas = 1;
		boolean error = false, encontrou = false, invalido = false;
		String procurar = "", nome = "", CPF = "", email = "", telefone = "",codigo = "";
		aluno aluno_auxiliar;
		professor professor_auxiliar;
		aula aula_auxiliar;
		Data data = null;
		// Listas de alunos
        LinkedList <aluno> alunos = new LinkedList<aluno>();
		LinkedList <professor> professores = new LinkedList<professor>();
		LinkedList <aula> aulas = new LinkedList<aula>();
		do{
			do{
				try{
					error = false;
					System.out.println("---MENU---\n");
					System.out.println("0 - Sair.");
					System.out.println("1 - Cadastro."); // Cadastrar professores e aulas (submenu de cadastro)
					System.out.println("2 - Remoção."); // Remover professores e aulas (submenu de remoção)
					System.out.println("3 - (Des)Vincular"); // (Des)Vincula Aula com aluno ou aula com professor
					System.out.println("4 - Consulta."); // Consultar professores e aulas (submenu de consulta)
					System.out.println("5 - Entrada de aluno(s)"); // "Catraca"
					System.out.println("6 - Sincronizar contas com o banco.");
					System.out.print("-> ");
					menu_option = input.nextInt();
				}
				catch(InputMismatchException InputMismatchException){
					error = true;
					System.out.println("Insira um número inteiro dentro do intervalo do menu para prosseguir. Pressione qualquer tecla para continuar.");
					input.nextLine();
				}
			}while(error || menu_option > 6 || menu_option < 0);
			clear();
			switch(menu_option){
				case 1: // Cadastro de aluno
					/*
					coleta nome, CPF, email, telefone, RG, conta bancária
					verifica se existe conflito de CPF
					gera um número de codigo
					*/
					do{
						try{
							error = false;
							System.out.println("1 - Cadastro Aluno");
							System.out.println("2 - Cadastro Professor");
							System.out.println("3 - Cadastro Aula");
							System.out.print("->");
							submenu_option = input.nextInt();
						}
						catch(InputMismatchException InputMismatchException){
							error = true;
							System.out.println("Insira um número inteiro dentro do intervalo do menu para prosseguir. Pressione qualquer tecla para continuar.");
							input.nextLine();
						}
					}while(error || submenu_option > 3 || submenu_option < 0);
					clear();
					// Caso professor ou aluno, coleta nome, CPF, email, data de nascimento, telefone
					if(submenu_option == 1 || submenu_option == 2){
						System.out.printf("Nome ->");
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
					}

					switch(submenu_option){
						case 1: // Aluno
							error = true;
							do{ //Inserção da forma de pagamento.
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
							contador_codigo_aluno++;
							aluno_auxiliar = new aluno(nome,formataCPF(CPF),email,formataTelefone(telefone),Integer.toString(contador_codigo_aluno),forma_pag,data);
							alunos.add(aluno_auxiliar);
							System.out.println("Aluno cadastrado com sucesso. Pressione ENTER para continuar.");
							break;
// ****************************************************************************************************
						case 2: // Professor
							contador_codigo_professor++;
							professor_auxiliar = new professor(nome,formataCPF(CPF),email,formataTelefone(telefone),Integer.toString(contador_codigo_professor),data);
							professores.add(professor_auxiliar);
							System.out.println("Professor cadastrado com sucesso. Pressione ENTER para continuar.");
							break;
// ****************************************************************************************************
						case 3: // Aulas
							error = true;
							do{ // Inserção do período
								if(periodo > 3 || periodo < 1){
									System.out.println("Insira um número inteiro dentro do intervalo para prosseguir. Pressione qualquer tecla para continuar.");
									input.nextLine();
									input.nextLine();
									clear();
								}
								try{
									error = false;
									System.out.printf("Período (1-Matutino; 2-Vespertino; 3-Noturno) ->");
									periodo = input.nextInt();
								}
								catch(InputMismatchException InputMismatchException){
									error = true;
									System.out.println("Insira um número inteiro para prosseguir. Pressione qualquer tecla para continuar.");
									input.nextLine();
									input.nextLine();
									clear();
								}
							}while(error || periodo > 3 || periodo < 1);
							error = true;
							do{ // Inserção do nível
								if(nivel > 3 || nivel < 1){
									System.out.println("Insira um número inteiro dentro do intervalo para prosseguir. Pressione qualquer tecla para continuar.");
									input.nextLine();
									input.nextLine();
									clear();
								}
								try{
									error = false;
									System.out.printf("Nível (1-Iniciante; 2-Intermediário; 3-Avançado) ->");
									nivel = input.nextInt();
								}
								catch(InputMismatchException InputMismatchException){
									error = true;
									System.out.println("Insira um número inteiro para prosseguir. Pressione qualquer tecla para continuar.");
									input.nextLine();
									input.nextLine();
									clear();
								}
							}while(error || nivel > 3 || nivel < 1);
							error = true;
							do{ //Inserção arma
								if(armas > 2 || armas < 1){
									System.out.println("Insira um número inteiro dentro do intervalo para prosseguir. Pressione qualquer tecla para continuar.");
									input.nextLine();
									input.nextLine();
									clear();
								}
								try{
									error = false;
									System.out.printf("Armas (1-Sim; 2-Não) ->");
									armas = input.nextInt();
								}
								catch(InputMismatchException InputMismatchException){
									error = true;
									System.out.println("Insira um número inteiro para prosseguir. Pressione qualquer tecla para continuar.");
									input.nextLine();
									input.nextLine();
									clear();
								}
							}while(error || armas > 2 || armas < 1);
							contador_codigo_aula++;
							aula_auxiliar = new aula(nivel, armas == 2 ? false : true, periodo, Integer.toString(contador_codigo_aula));
							aulas.add(aula_auxiliar);
							System.out.println("Aula cadastrada com sucesso. Pressione ENTER para continuar.");
							break;
					}
					input.nextLine();
					clear();
					break;
// ****************************************************************************************************
				case 2: // Remoção de aluno
					/*
					coleta nº da codigo
					confere se existe
					remove
					*/
					do{
						try{
							error = false;
							System.out.println("1 - Remoção Aluno");
							System.out.println("2 - Remoção Professor");
							System.out.println("3 - Remoção Aula");
							System.out.print("->");
							submenu_option = input.nextInt();
						}
						catch(InputMismatchException InputMismatchException){
							error = true;
							System.out.println("Insira um número inteiro dentro do intervalo do menu para prosseguir. Pressione qualquer tecla para continuar.");
							input.nextLine();
						}
					}while(error || submenu_option > 3 || submenu_option < 0);
					clear();
					switch(submenu_option){
						case 1:
							if(alunos.size() == 0){
								System.out.println("Não há alunos cadastrados no sistema.");
								break;
							}
							exibir(alunos);
							System.out.print("\n->");
							procurar = input.nextLine();
							procurar = input.nextLine();
							encontrou = false;
							for(aluno al : alunos){
								if(procurar.equals(al.get_codigo())){
									alunos.remove(al);
									System.out.println("Aluno removido com sucesso");
									encontrou = true;
									break;
								}
							}
							if(!encontrou){
								System.out.println("Aluno inexistente no sistema");
							}
							break;
// ****************************************************************************************************
						case 2:
							if(professores.size() == 0){
								System.out.println("Não há professores cadastrados no sistema.");
								break;
							}
							exibir(professores);
							System.out.print("\n->");
							procurar = input.nextLine();
							procurar = input.nextLine();
							encontrou = false;
							for(professor pr : professores){
								if(procurar.equals(pr.get_codigo())){
									professores.remove(pr);
									System.out.println("Professor removido com sucesso");
									encontrou = true;
									break;
								}
							}
							if(!encontrou){
								System.out.println("Professor inexistente no sistema");
							}
							break;
// ****************************************************************************************************
						case 3:
							if(aulas.size() == 0){
								System.out.println("Não há aulas cadastrados no sistema.");
								break;
							}
							exibir(aulas);
							System.out.print("\n->");
							procurar = input.nextLine();
							procurar = input.nextLine();
							encontrou = false;
							for(aula au : aulas){
								if(procurar.equals(au.get_codigo())){
									aulas.remove(au);
									System.out.println("Aula removida com sucesso");
									encontrou = true;
									break;
								}
							}
							if(!encontrou){
								System.out.println("Aula inexistente no sistema");
							}
							break;
					}
					try{
						Thread.sleep(2000);
					}
					catch(InterruptedException e){
					}
					clear();
					break;
// ****************************************************************************************************
				case 3:
					break;
// ****************************************************************************************************
				case 4: // Consulta de aluno
					/*
					coleta nº da codigo
					confere se existe
					coleta as infos & exibe
					*/
					do{
						try{
							error = false;
							System.out.println("1 - Consulta Aluno");
							System.out.println("2 - Consulta Professor");
							System.out.println("3 - Consulta Aula");
							System.out.print("->");
							submenu_option = input.nextInt();
						}
						catch(InputMismatchException InputMismatchException){
							error = true;
							System.out.println("Insira um número inteiro dentro do intervalo do menu para prosseguir. Pressione qualquer tecla para continuar.");
							input.nextLine();
						}
					}while(error || submenu_option > 3 || submenu_option < 0);
					clear();
					switch(submenu_option){
						case 1:
							if(alunos.size() == 0){
								System.out.println("Não há alunos cadastrados no sistema.");
								invalido = true;
								break;
							}
							exibir(alunos);
							System.out.println("0 - exibir todos");
							System.out.print("->");
							procurar = input.nextLine();
							procurar = input.nextLine();
							if(procurar.equals("0")){
								for(aluno al : alunos){
									al.exibe();
									System.out.println();
								}
							}
							else{
								encontrou = false;
								for(aluno al : alunos){
									if(procurar.equals(al.get_codigo())){
										al.exibe();
										encontrou = true;
										break;
									}
								}
								if(!encontrou){
									System.out.println("Aluno inexistente no sistema.");
								}
							}
							break;
// ****************************************************************************************************
						case 2:
							if(professores.size() == 0){
								System.out.println("Não há professores cadastrados no sistema.");
								invalido = true;
								break;
							}
							exibir(professores);
							System.out.println("0 - exibir todos");
							System.out.print("\n->");
							procurar = input.nextLine();
							procurar = input.nextLine();
							encontrou = false;
							if(procurar.equals("0")){
								for(professor pr : professores){
									pr.exibe();
									System.out.println();
								}
							}
							else{
								for(professor pr : professores){
									if(procurar.equals(pr.get_codigo())){
										pr.exibe();
										encontrou = true;
										break;
									}
								}
								if(!encontrou){
									System.out.println("Professor inexistente no sistema.");
								}
							}
							break;
// ****************************************************************************************************
						case 3:
							if(aulas.size() == 0){
								System.out.println("Não há aulas cadastradas no sistema.");
								invalido = true;
								break;
							}
							exibir(aulas);
							System.out.println("0 - exibir todos");
							System.out.print("\n->");
							procurar = input.nextLine();
							procurar = input.nextLine();
							if(procurar.equals("0")){
								for(aula au : aulas){
									au.exibe();
									System.out.println();
								}
							}
							else{
								encontrou = false;
								for(aula au : aulas){
									if(procurar.equals(au.get_codigo())){
										au.exibe();
										encontrou = true;
										break;
									}
								}
								if(!encontrou){
									System.out.println("Aula inexistente no sistema.");
								}
							}
							break;
					}

						System.out.println("Pressione ENTER para continuar.");
						input.nextLine();
						if(invalido){
							input.nextLine();
						}
						clear();
					break;
// ****************************************************************************************************
				case 5:
					System.out.print("Insira a código do aluno que deseja entrar\n->");
					input.nextLine();
					codigo = input.nextLine();
					encontrou = false;
					for(aluno al : alunos){
						if(al.get_codigo().equals(codigo)){
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
// ****************************************************************************************************
				case 6:
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

	// Formata o CPF (00000000000 -> 000.000.000-00)
	public static String formataCPF(String CPF){
		return CPF.substring(0,3) + '.' + CPF.substring(3,6) + '.' + CPF.substring(6,9) + '-' + CPF.substring(9);
	}

	// Formata o telefone (00000000000 -> 000.000.000-00)
	public static String formataTelefone(String telefone){
		if(telefone.length() == 10){
			return telefone.substring(0,2) + ' ' + telefone.substring(2,6) + '-' + telefone.substring(6);  
		}
		return telefone.substring(0,2) + ' ' + telefone.substring(2,7) + '-' + telefone.substring(7); 
	}

	// Método que lista os nomes e codigos para a consulta
	public static <T> void exibir(LinkedList <T> lista){
		for(T aux : lista){
			System.out.printf("\t%s", aux);
		}
	}
}