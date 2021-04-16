// Alcides Gomes Beato Neto		19060987
// Henrique Sartori Siqueira	19240472
// Rafael Silva Barbon			19243633

import java.time.format.DateTimeFormatter;
import java.util.*;
import java.time.*;

//import java.time.LocalDateTime;
//import  java.time.format.DateTimeFormatter;
public class menu{
	static Scanner input = new Scanner(System.in);
	public static void main(String[] args){
		int menu_option = 0, submenu_option = 0, sub_inter_menuoption = 0, contador_codigo_aluno = 0, contador_codigo_professor = 0, contador_codigo_aula = 0, faixa = 1;
		boolean error = false, encontrou = false, invalido = false, armas = true, valido = true;
		String procurar = "", nome = "", CPF = "", email = "", telefone = "",codigo = "", senha = "",senhaMestre = "admin", vincular = "", confirmaSenha = "", horario = "";
		aluno aluno_auxiliar = null;
		professor professor_auxiliar = null;
		aula aula_auxiliar = null;
		Data data = null;
		// Listas de alunos
        LinkedList <aluno> alunos = new LinkedList<aluno>();
		LinkedList <professor> professores = new LinkedList<professor>();
		LinkedList <aula> aulas = new LinkedList<aula>();
		clear();
		do{
			do{
				try{
					error = false;
					System.out.println("---MENU---\n");
					System.out.println("0 - Sair.");
					System.out.println("1 - Cadastro."); // Cadastrar alunos, professores ou aulas
					System.out.println("2 - Remoção."); // Remover alunos, professores ou aulas
					System.out.println("3 - Vínculos."); // (Des)Vincula Aula com aluno
					System.out.println("4 - Consulta."); // Consultar alunos, professores ou aulas
					System.out.println("5 - Atualizar dados.");
					System.out.println("6 - Entrada."); // "Catraca"
					System.out.println("7 - Sincronizar contas com o banco.");
					System.out.println("8 - Enviar comprovantes para os professores.");
					System.out.print("-> ");
					menu_option = input.nextInt();
				}
				catch(InputMismatchException InputMismatchException){
					error = true;
					System.out.println("Insira um número inteiro dentro do intervalo do menu para prosseguir. Pressione qualquer tecla para continuar.");
					input.nextLine();
				}
			}while(error || menu_option > 8 || menu_option < 0);
			clear();
			switch(menu_option){
				case 1: // Cadastro
					do{
						try{
							error = false;
							System.out.println("0 - Menu principal.");
							System.out.println("1 - Cadastro Aluno.");
							/*
							coleta nome, CPF, email, telefone, data de nascimento e forma de pagamento
							verifica se existe conflito de CPF
							gera um número de codigo
							*/
							System.out.println("2 - Cadastro Professor.");
							/*
							coleta nome, CPF, email, telefone e data de nascimento
							verifica se existe conflito de CPF
							gera um número de codigo
							*/
							System.out.println("3 - Cadastro Aula.");
							/*
							coleta Horário, faixa e arma
							*/
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
					// Caso professor ou aluno, coleta nome, email, data de nascimento, telefone
					if(submenu_option == 1 || submenu_option == 2){
						nome = insereNome();
						email = insereEmail();
						telefone = insereTelefone();
						data = insereData();
						input.nextLine();
						do{
							senha = insereSenha(false);
							confirmaSenha = insereSenha(true);
							if(!senha.equals(confirmaSenha)){
								System.out.println("Senhas distintas! Tente novamente.");
							}
						}while(!senha.equals(confirmaSenha));
					}

					switch(submenu_option){
						case 1: // Aluno
							do{
								invalido = false;
								CPF = insereCPF();
								for(aluno al : alunos){
									if(CPF.equals(al.get_CPF())){
										invalido = true;
										System.out.println("CPF já existente no sistema, tente novamente.");
										break;
									}
								}
							}while(invalido);
							contador_codigo_aluno++;
							aluno_auxiliar = new aluno(nome,CPF,email,telefone,"A"+Integer.toString(contador_codigo_aluno),data,senha);
							alunos.add(aluno_auxiliar);
							System.out.printf("Aluno cadastrado com sucesso. Código: %s. Pressione ENTER para continuar.",aluno_auxiliar.get_codigo());
							break;
// ****************************************************************************************************
						case 2: // Professor
							do{
								invalido = false;
								CPF = insereCPF();
								for(professor pr : professores){
									if(CPF.equals(pr.get_CPF())){
										invalido = true;
										System.out.println("CPF já existente no sistema, tente novamente.");
										break;
									}
								}
							}while(invalido);
							contador_codigo_professor++;
							professor_auxiliar = new professor(nome,CPF,email,telefone,"P"+Integer.toString(contador_codigo_professor),data,senha);
							professores.add(professor_auxiliar);
							System.out.printf("Professor cadastrado com sucesso. Código: %s . Pressione ENTER para continuar.",professor_auxiliar.get_codigo());
							break;
// ****************************************************************************************************
						case 3: // Aulas
							horario = insereHorario();
							error = true;
							faixa = insereFaixa();
							armas = insereArmas();
							contador_codigo_aula++;
							aula_auxiliar = new aula(faixa, armas, horario, "C"+Integer.toString(contador_codigo_aula));
							aulas.add(aula_auxiliar);
							System.out.printf("Aula cadastrada com sucesso. Código: %s. Pressione ENTER para continuar.",aula_auxiliar.get_codigo());
							input.nextLine();
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
							System.out.println("0 - Menu principal.");
							System.out.println("1 - Remoção Aluno.");
							System.out.println("2 - Remoção Professor.");
							System.out.println("3 - Remoção Aula.");
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
							input.nextLine();
							procurar = input.nextLine();
							encontrou = false;
							procurar = procurar.toUpperCase();
							for(aluno al : alunos){
								if(procurar.equals(al.get_codigo())){
									senha = insereSenha(false);
									encontrou = true;
									if(senha.equals(al.get_senha()) || senha.equals(senhaMestre)){
										alunos.remove(al);
										System.out.println("Aluno removido com sucesso.");
									}
									else{
										System.out.println("Senha incorreta.");
									}
									break;
								}
							}
							if(!encontrou){
								System.out.println("Aluno inexistente no sistema.");
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
							input.nextLine();
							procurar = input.nextLine();
							encontrou = false;
							procurar = procurar.toUpperCase();
							for(professor pr : professores){
								if(procurar.equals(pr.get_codigo())){
									senha = insereSenha(false);
									encontrou = true;
									if(senha.equals(pr.get_senha()) || senha.equals(senhaMestre)){
										professores.remove(pr);
										System.out.println("Professor removido com sucesso.");
									}
									else{
										System.out.println("Senha incorreta.");
									}
									break;
								}
							}
							if(!encontrou){
								System.out.println("Professor inexistente no sistema.");
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
							input.nextLine();
							procurar = input.nextLine();
							encontrou = false;
							procurar = procurar.toUpperCase();
							for(aula au : aulas){
								if(procurar.equals(au.get_codigo())){
									for(aluno a : alunos){
										a.del_aula(au);
										a.set_valor(valor_aulas(a.get_aulas()));
									}
									aulas.remove(au);
									System.out.println("Aula removida com sucesso.");
									encontrou = true;
									break;
								}
							}
							if(!encontrou){
								System.out.println("Aula inexistente no sistema.");
							}
							break;
					}
					esperar(2000);
					clear();
					break;
// ****************************************************************************************************
				case 3:
					if(alunos.size() == 0){
						System.out.println("Não há alunos cadastrados no sistema.");
						esperar(2000);
					}
					else if(aulas.size() == 0){
						System.out.println("Não há aulas cadastradas no sistema.");
						esperar(2000);
					}
					else{
						do{
							try{
								error = false;
								System.out.println("0 - Menu principal.");
								System.out.println("1 - Vincular aula a um aluno.");
								System.out.println("2 - Desvincular aula de um aluno.");
								System.out.print("->");
								submenu_option = input.nextInt();
							}
							catch(InputMismatchException InputMismatchException){
								error = true;
								System.out.println("Insira um número inteiro dentro do intervalo do menu para prosseguir. Pressione qualquer tecla para continuar.");
								input.nextLine();
							}
						}while(error || submenu_option > 2 || submenu_option < 0);
						clear();
						switch(submenu_option){
							case 1:
								do{
									encontrou = false;
									System.out.println("Selecione a aula a ser vinculada:");
									exibir(aulas);
									System.out.print("\n->");
									input.nextLine();
									codigo = input.nextLine();
									codigo = codigo.toUpperCase();
									for(aula au : aulas){
										if(codigo.equals(au.get_codigo())){
											aula_auxiliar = au;
											encontrou = true;
											break;
										}
									}
								}while(!encontrou);
								do{
									encontrou = false;
									System.out.println("Selecione o aluno a ser vinculado:");
									exibir(alunos);
									System.out.print("\n->");
									vincular = input.nextLine();
									vincular = vincular.toUpperCase();
									for(aluno a : alunos){
										if(vincular.equals(a.get_codigo())){
											valido = a.add_aula(aula_auxiliar);
											a.set_valor(valor_aulas(a.get_aulas()));
											encontrou = true;
											break;
										}
									}
									if(encontrou){
										if(valido){
											System.out.println("\nAula vinculada do aluno.");
										}
										else{
											System.out.println("\nAluno possui faixa discrepante com o nível da aula.");
										}
									}
									else{
										System.out.println("Aluno inexistente no sistema.");
									}
									do{
										error = false;
										try{
											System.out.print("\nDeseja vincular outro aluno a esta mesma aula?(1 - sim, 2 - não)\n->");
											sub_inter_menuoption = input.nextInt();
										}
										catch(InputMismatchException InputMismatchException){
											error = true;
											System.out.println("Insira um número inteiro dentro do intervalo para prosseguir. Pressione qualquer tecla para continuar.");
											input.nextLine();
										}
									}while(error || sub_inter_menuoption > 2 || sub_inter_menuoption < 1);
								}while(sub_inter_menuoption == 1);
								break;
							case 2:
								do{
									encontrou = false;
									System.out.println("Selecione a aula a ser desvinculada:");
									exibir(aulas);
									System.out.println("->");
									input.nextLine();
									codigo = input.nextLine();
									codigo = codigo.toUpperCase();
									for(aula au : aulas){
										if(codigo.equals(au.get_codigo())){
											aula_auxiliar = au;
											encontrou = true;
											break;
										}
									}
								}while(!encontrou);
								do{
									encontrou = false;
									System.out.println("Selecione o aluno a ser desvinculado:");
									exibir(alunos);
									System.out.print("\n->");
									vincular = input.nextLine();
									vincular = vincular.toUpperCase();
									for(aluno a : alunos){
										if(vincular.equals(a.get_codigo())){
											a.del_aula(aula_auxiliar);
											a.set_valor(valor_aulas(a.get_aulas()));
											encontrou = true;
											break;
										}
									}
									if(encontrou){
										System.out.println("\nAula desvinculada do aluno.");
									}
									else{
										System.out.println("Aluno inexistente no sistema.");
									}
									do{
										error = false;
										try{
											System.out.print("\nDeseja desvincular outro aluno desta mesma aula?(1 - sim, 2 - não)\n->");
											sub_inter_menuoption = input.nextInt();
										}
										catch(InputMismatchException InputMismatchException){
											error = true;
											System.out.println("Insira um número inteiro dentro do intervalo para prosseguir. Pressione qualquer tecla para continuar.");
											input.nextLine();
										}
									}while(error || sub_inter_menuoption > 2 || sub_inter_menuoption < 1);
								}while(sub_inter_menuoption == 1);
								break;
						}
					}
					clear();
					break;
// ****************************************************************************************************
				case 4: // Consulta
					/*
					coleta nº do codigo
					confere se existe
					coleta as infos & exibe
					*/
					do{
						try{
							error = false;
							System.out.println("0 - Menu principal.");
							System.out.println("1 - Consulta Aluno.");
							System.out.println("2 - Consulta Professor.");
							System.out.println("3 - Consulta Aula.");
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
							System.out.println("Exibir todos - 0");
							System.out.print("->");
							input.nextLine();
							procurar = input.nextLine();
							procurar = procurar.toUpperCase();
							if(procurar.equals("0")){
								senha = insereSenha(false);
								if(senha.equals(senhaMestre)){
									for(aluno al : alunos){
										al.exibe();
									}
								}
								else{
									System.out.println("Senha incorreta!");
								}
							}
							else{
								encontrou = false;
								for(aluno al : alunos){
									if(procurar.equals(al.get_codigo())){
										senha = insereSenha(false);
										if(senha.equals(al.get_senha()) || senha.equals(senhaMestre)){
											al.exibe();
										}
										else{
											System.out.println("Senha incorreta!");
										}
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
							System.out.println("Exibir todos - 0");
							System.out.print("\n->");
							input.nextLine();
							procurar = input.nextLine();
							encontrou = false;
							procurar = procurar.toUpperCase();
							if(procurar.equals("0")){
								senha = insereSenha(false);
								if(senha.equals(senhaMestre)){
									for(professor pr : professores){
										pr.exibe();
									}
								}
								else{
									System.out.println("Senha incorreta!");
								}
							}
							else{
								for(professor pr : professores){
									if(procurar.equals(pr.get_codigo())){
										senha = insereSenha(false);
										if(senha.equals(pr.get_senha()) || senha.equals(senhaMestre)){
											pr.exibe();
										}
										else{
											System.out.println("Senha incorreta!");
										}
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
							System.out.println("Exibir todos - 0");
							System.out.print("\n->");
							input.nextLine();
							procurar = input.nextLine();
							procurar = procurar.toUpperCase();
							if(procurar.equals("0")){
								for(aula au : aulas){
									au.exibe();
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
					do{
						try{
							error = false;
							System.out.println("0 - Menu principal.");
							System.out.println("1 - Atualizar dados do Aluno.");
							System.out.println("2 - Atualizar dados do Professor.");
							System.out.println("3 - Atualizar dados da Aula.");
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
					invalido = false;
					switch(submenu_option){
						case 1:
							if(alunos.size() == 0){
								System.out.println("Não há alunos cadastrados no sistema.");
								invalido = true;
								break;
							}
							exibir(alunos);
							System.out.println();
							System.out.print("->");
							input.nextLine();
							procurar = input.nextLine();
							procurar = procurar.toUpperCase();
							encontrou = false;
							for(aluno al : alunos){
								if(procurar.equals(al.get_codigo())){
									senha = insereSenha(false);
									if(senha.equals(al.get_senha()) || senha.equals(senhaMestre)){
										do{
											try{
												error = false;
												System.out.println("1 - Nome.");
												System.out.println("2 - CPF.");
												System.out.println("3 - Email.");
												System.out.println("4 - Telefone.");
												System.out.println("5 - Data de Nascimento.");
												System.out.println("6 - Auxiliar.");
												System.out.println("7 - Faixa.");
												System.out.println("8 - Senha.");
												System.out.print("->");
												sub_inter_menuoption = input.nextInt();
											}
											catch(InputMismatchException InputMismatchException){
												error = true;
												System.out.println("Insira um número inteiro dentro do intervalo do menu para prosseguir. Pressione qualquer tecla para continuar.");
												input.nextLine();
											}
										}while(error || menu_option > 8 || menu_option < 1);
										switch(sub_inter_menuoption){
											case 1:
												al.set_nome(insereNome());
												break;
											case 2:
												do{
													invalido = false;
													CPF = insereCPF();
													for(aluno a : alunos){
														if(CPF.equals(a.get_CPF())){
															invalido = true;
															System.out.println("CPF já existente no sistema, tente novamente.");
															break;
														}
													}
												}while(invalido);
												al.set_CPF(CPF);
												break;
											case 3:
												al.set_email(insereEmail());
												break;
											case 4:
												al.set_telefone(insereTelefone());
												break;
											case 5:
												al.set_nascimento(insereData());
												break;
											case 6:
												al.set_auxiliar(insereAuxiliar());
												break;
											case 7:
												al.set_faixa(insereFaixa());
												break;
											case 8:
												do{
													input.nextLine();
													vincular = insereSenha(false);
													confirmaSenha = insereSenha(true);
													if(!vincular.equals(confirmaSenha)){
														System.out.println("Senhas distintas! Tente novamente.");
													}
												}while(!vincular.equals(confirmaSenha));
												al.set_senha(senha,vincular,senhaMestre);
												break;
										}
									}
									else{
										System.out.println("Senha incorreta!");
										invalido = true;
									}
									encontrou = true;
									break;
								}
							}
							if(!encontrou){
								System.out.println("Aluno inexistente no sistema.");
							}
							else if(!invalido){
								System.out.println("Aluno atualizado com sucesso.");
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
							System.out.println();
							System.out.print("\n->");
							procurar = input.nextLine();
							encontrou = false;
							procurar = procurar.toUpperCase();
							for(professor pr : professores){
								if(procurar.equals(pr.get_codigo())){
									input.nextLine();
									senha = insereSenha(false);
									if(senha.equals(pr.get_senha()) || senha.equals(senhaMestre)){
										do{
											try{
												error = false;
												System.out.println("1 - Nome.");
												System.out.println("2 - CPF.");
												System.out.println("3 - Email.");
												System.out.println("4 - Telefone.");
												System.out.println("5 - Data de Nascimento.");
												System.out.println("6 - Senha.");
												System.out.print("->");
												sub_inter_menuoption = input.nextInt();
											}
											catch(InputMismatchException InputMismatchException){
												error = true;
												System.out.println("Insira um número inteiro dentro do intervalo do menu para prosseguir. Pressione qualquer tecla para continuar.");
												input.nextLine();
											}
										}while(error || menu_option > 6 || menu_option < 1);
										switch(sub_inter_menuoption){
											case 1:
												pr.set_nome(insereNome());
												break;
											case 2:
												do{
													invalido = false;
													CPF = insereCPF();
													for(professor p : professores){
														if(CPF.equals(p.get_CPF())){
															invalido = true;
															System.out.println("CPF já existente no sistema, tente novamente.");
															break;
														}
													}
												}while(invalido);
												pr.set_CPF(CPF);
												break;
											case 3:
												pr.set_email(insereEmail());
												break;
											case 4:
												pr.set_telefone(insereTelefone());
												break;
											case 5:
												pr.set_nascimento(insereData());
												break;
											case 6:
												do{
													input.nextLine();
													vincular = insereSenha(false);
													confirmaSenha = insereSenha(true);
													if(!vincular.equals(confirmaSenha)){
														System.out.println("Senhas distintas! Tente novamente.");
													}
												}while(!vincular.equals(confirmaSenha));
												pr.set_senha(senha,vincular,senhaMestre);
												break;
										}
									}
									else{
										System.out.println("Senha incorreta!");
										invalido = true;
									}
									encontrou = true;
									break;
								}
							}
							if(!encontrou){
								System.out.println("Professor inexistente no sistema.");
							}
							else if(!invalido){
								System.out.println("Professor atualizado com sucesso.");
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
							System.out.println();
							System.out.print("\n->");
							input.nextLine();
							procurar = input.nextLine();
							procurar = procurar.toUpperCase();
							encontrou = false;
							for(aula au : aulas){
								if(procurar.equals(au.get_codigo())){
									do{
										try{
											error = false;
											System.out.println("1 - Armas.");
											System.out.println("2 - Faixa.");
											System.out.println("3 - Horário.");
											System.out.print("->");
											sub_inter_menuoption = input.nextInt();
										}
										catch(InputMismatchException InputMismatchException){
											error = true;
											System.out.println("Insira um número inteiro dentro do intervalo do menu para prosseguir. Pressione qualquer tecla para continuar.");
											input.nextLine();
										}
									}while(error || menu_option > 5 || menu_option < 1);
									switch(sub_inter_menuoption){
										case 1:
											au.set_arma(insereArmas());
											break;
										case 2:
											au.set_horario(insereHorario());
											break;
										case 3:
											au.set_faixa(insereFaixa());
											break;
									}
									encontrou = true;
									break;
								}
							}
							if(!encontrou){
								System.out.println("Aula inexistente no sistema.");
							}
							else{
								System.out.println("Aula atualizada com sucesso.");
							}
							break;
					}

					System.out.println("Pressione ENTER para continuar.");
					input.nextLine();
					//if(invalido){
						//input.nextLine();
					//}
					clear();
					break;
// ****************************************************************************************************
				case 6:
					if(alunos.size() == 0 && professores.size() == 0){
						System.out.println("Cadastros inexistentes.");
					}
					else{
						System.out.print("Insira a código do aluno/professor que deseja entrar.\n->");
						input.nextLine();
						codigo = input.nextLine();
						encontrou = false;
						codigo = codigo.toUpperCase();
						if(codigo.charAt(0) == 'A'){
							for(aluno al : alunos){
								if(al.get_codigo().equals(codigo)){
									encontrou = true;
									if(al.get_mes_pago()){
										System.out.printf("Seja bem vindo, %s.", al.get_nome());
										break;
									}
									else{
										System.out.printf("Pague sua valor para entrar, %s.", al.get_nome());
										break;
									}
								}
							}
							if(!encontrou){
								System.out.println("Aluno inexistente no sistema.");
							}
						}
						else if(codigo.charAt(0) == 'P'){
							for(professor pr : professores){
								if(pr.get_codigo().equals(codigo)){
									encontrou = true;
									LocalDateTime agora = LocalDateTime.now();

									// formatar a data e hora
									DateTimeFormatter formatterData = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
									String dataFormatada = formatterData.format(agora);
									pr.registra_acesso(dataFormatada);
									System.out.printf("Acesso registrado, %s", pr.get_nome());
								}
							}
							if(!encontrou){
								System.out.println("Professor inexistente no sistema.");
							}
						}
						else{
							System.out.println("Pessoa inexistente no sistema.");
						}
					}
					esperar(2000);
					clear();
					break;
// ****************************************************************************************************
				case 7: ///PARTE DO EMAIL
					break;
				case 8:///Envio de comprovante para professores
					if(professores.size() == 0){
						System.out.println("Não há professores cadastrados.");
					}
					else{
						System.out.print("Insira senha Master ->");
						input.nextLine();
						senha = input.nextLine();
						if(senha.equals(senhaMestre)){
							for(professor pf : professores){
								pf.renova_mes();
							}
						}
						System.out.print("Comprovantes enviados por email a todos os professores.");
					}
					esperar(2000);
					clear();
					break;
				default: // Sair do sistema
					System.out.println("Obrigado por utilizar o sistema desenvolvido por RAH - Desenvolvimento de Sistemas.");
					esperar(5000);
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

	// Método que coleta o nome
	public static String insereNome(){
		String nome;
		System.out.print("Nome ->");
		input.nextLine();
		nome = input.nextLine();
		nome = nome.toUpperCase();
		return nome;
	}

	// Método que coleta senha (ou confirmação de senha)
	public static String insereSenha(boolean confirma){
		String senha;
		if(confirma){
			System.out.print("Confirme senha ->");
		}
		else{
			System.out.print("Senha ->");
		}
		senha = input.nextLine();
		return senha;
	}

	//Insere CPF
	public static String insereCPF(){
		boolean invalido = true;
		String CPF = "";
		do{//Verifica conflito CPF
			System.out.print("CPF->"); // 000 000 000 00
			//CPF = input.nextLine();
			CPF = input.nextLine();
			if(CPF.matches("[0-9]+") && CPF.length() == 11){
				invalido = false;
			}
			if(invalido){
				System.out.println("CPF inválido, tente novamente digitando apenas números.");
			}
		}while(invalido);
		// Formata o CPF (00000000000 -> 000.000.000-00)
		return CPF.substring(0,3) + '.' + CPF.substring(3,6) + '.' + CPF.substring(6,9) + '-' + CPF.substring(9);
	}

	// Método que coleta o email
	public static String insereEmail(){
		boolean invalido = true;
		String email;
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
		return email;
	}

	// Formata o telefone (0000000000 -> 00 0000-0000 ou 0000000000 -> 00 00000-0000)
	public static String insereTelefone(){
		boolean invalido = true;
		String telefone;
		do{
			System.out.printf("Telefone ->"); // 00 &0000 0000 // DDD + (1) + 8 NºS
			telefone = input.nextLine();
			if((telefone.length() == 10 || telefone.length() == 11) && telefone.matches("[0-9]+")){ // Verifica se existe somente números e verifica o tamanho (caso de celular / telefone fixo)
				invalido = false;
			}
			else{
				System.out.println("Telefone inválido, tente novamente.");							}
			}while(invalido);
		if(telefone.length() == 10){
			return telefone.substring(0,2) + ' ' + telefone.substring(2,6) + '-' + telefone.substring(6);
		}
		return telefone.substring(0,2) + ' ' + telefone.substring(2,7) + '-' + telefone.substring(7);
	}

	// Método que insere data
	public static Data insereData(){
		boolean invalido = true, error;
		int dia = 0, mes = 0, ano = 0;
		Data data = null;
		do{
			error = true;
			do{
				System.out.printf("Data de nascimento (DD MM AAAA) ->");
				try{
					error = false;
					dia = input.nextInt();
					mes = input.nextInt();
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
		return data;
	}

	public static boolean insereAuxiliar(){
		boolean error = true;
		int aux = 1;
		do{ //Inserção da forma de pagamento.
			if(aux > 2 || aux < 1){
				System.out.println("Insira um número inteiro dentro do intervalo para prosseguir. Pressione qualquer tecla para continuar.");
				input.nextLine();
				input.nextLine();
				clear();
			}
			try{
				error = false;
				System.out.printf("Aluno auxiliar (1-Não; 2-Sim) ->");
				aux = input.nextInt();
			}
			catch(InputMismatchException InputMismatchException){
				error = true;
				System.out.println("Insira um número inteiro para prosseguir. Pressione qualquer tecla para continuar.");
				input.nextLine();
				input.nextLine();
				clear();
			}
		}while(error || aux > 2 || aux < 1);
		if(aux == 2){
			return true;
		}
		return false;
	}

	public static int insereFaixa(){
		int faixa = 0;
		boolean error = true;
		do{ // Inserção do nível
			if(faixa > 10 || faixa < 0){
				System.out.println("Insira um número inteiro dentro do intervalo para prosseguir. Pressione qualquer tecla para continuar.");
				input.nextLine();
				input.nextLine();
				clear();
			}
			try{
				error = false;
				System.out.println("Faixa:");
				System.out.println("\t0 - Amerela.");
				System.out.println("\t1 - Dourada.");
				System.out.println("\t2 - Laranja.");
				System.out.println("\t3 - Jade.");
				System.out.println("\t4 - Verde.");
				System.out.println("\t5 - Roxa.");
				System.out.println("\t6 - Azul.");
				System.out.println("\t7 - Vermelha.");
				System.out.println("\t8 - Marrom Claro.");
				System.out.println("\t9 - Marrom.");
				System.out.println("\t10 - Preta.");
				System.out.print("->");
				faixa = input.nextInt();
			}
			catch(InputMismatchException InputMismatchException){
				error = true;
				System.out.println("Insira um número inteiro para prosseguir. Pressione qualquer tecla para continuar.");
				input.nextLine();
				input.nextLine();
				clear();
			}
		}while(error || faixa > 10 || faixa < 0);
		return faixa;
	}

	public static boolean insereArmas(){
		boolean error = true;
		int armas = 1;
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
		if(armas == 1){
			return true;
		}
		return false;
	}

	public static String insereHorario(){
		int dia = 1, hora = 1;
		boolean error;
		do{ // Inserção do dia da semana
			if(dia > 6 || dia < 1){
				System.out.println("Insira um número inteiro dentro do intervalo para prosseguir. Pressione qualquer tecla para continuar.");
				input.nextLine();
				input.nextLine();
				clear();
			}
			try{
				error = false;
				System.out.println("Dia da semana:");
				System.out.println("\t1 - Segunda-feira.");
				System.out.println("\t2 - Terça-feira.");
				System.out.println("\t3 - Quarta-feira.");
				System.out.println("\t4 - Quinta-feira.");
				System.out.println("\t5 - Sexta-feira.");
				System.out.println("\t6 - Sábado.");
				System.out.print("->");
				dia = input.nextInt();
			}
			catch(InputMismatchException InputMismatchException){
				error = true;
				System.out.println("Insira um número inteiro para prosseguir. Pressione qualquer tecla para continuar.");
				input.nextLine();
				input.nextLine();
				clear();
			}
		}while(error || dia > 6 || dia < 1);
		do{ // Inserção do horário
			if(hora < 1 || (hora > 15 && dia != 6) || (hora > 11 && dia == 6)){
				System.out.println("Insira um número inteiro dentro do intervalo para prosseguir. Pressione qualquer tecla para continuar.");
				input.nextLine();
				input.nextLine();
				clear();
			}
			try{
				error = false;
				System.out.println("Horário:");
				System.out.println("\t1 - 7:00.");
				System.out.println("\t2 - 8:00.");
				System.out.println("\t3 - 9:00.");
				System.out.println("\t4 - 10:00.");
				System.out.println("\t5 - 11:00.");
				System.out.println("\t6 - 12:00.");
				System.out.println("\t7 - 13:00.");
				System.out.println("\t8 - 14:00.");
				System.out.println("\t9 - 15:00.");
				System.out.println("\t10 - 16:00.");
				System.out.println("\t11 - 17:00.");
				if(dia != 6){
					System.out.println("\t12 - 18:00.");
					System.out.println("\t13 - 19:00.");
					System.out.println("\t14 - 20:00.");
					System.out.println("\t15 - 21:00.");
				}
				System.out.print("->");
				hora = input.nextInt();
			}
			catch(InputMismatchException InputMismatchException){
				error = true;
				System.out.println("Insira um número inteiro para prosseguir. Pressione qualquer tecla para continuar.");
				input.nextLine();
				input.nextLine();
				clear();
			}
		}while(error || hora < 1 || (hora > 15 && dia != 6) || (hora > 11 && dia == 6));
		hora += 6;
		switch(dia){
			case 1:	return "Segunda-feira " + Integer.toString(hora) + ":00";
			case 2:	return "Terça-feira " + Integer.toString(hora) + ":00";
			case 3:	return "Quarta-feira " + Integer.toString(hora) + ":00";
			case 4:	return "Quinta-feira " + Integer.toString(hora) + ":00";
			case 5:	return "Sexta-feira " + Integer.toString(hora) + ":00";
			case 6:	return "Sábado " + Integer.toString(hora) + ":00";
			default: return "";
		}
	}

	// Atribui o valor a ser pago semanalmente a partir do número de aulas da semana
	public static float valor_aulas(int aulas){
		switch(aulas){
			case 0: return 0;
			case 1:	return (float)100.0;
			case 2:	return (float)170.0;
			case 3: return (float)200.0;
			default: return (float)250.0;
		}
	}

	// Método que lista os nomes e codigos para a consulta
	public static <T> void exibir(LinkedList <T> lista){
		for(T aux : lista){
			System.out.printf("\t%s", aux);
		}
	}

	public static void esperar(int tempo){
		try{
			Thread.sleep(tempo);
		}
		catch(InterruptedException e){}
	}
}