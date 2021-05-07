// Alcides Gomes Beato Neto		19060987
// Henrique Sartori Siqueira	19240472
// Rafael Silva Barbon			19243633

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import javax.swing.*;
import javax.swing.text.MaskFormatter;

public class menu extends JFrame implements ActionListener{
	private JButton Botao_MP, ok_cadastro_aluno;
	private JRadioButton MP0, MP1, MP2, MP3, MP4, MP5, MP6, MP7, MP8;
	private JLabel titulo;
	private ButtonGroup Menu_op;

	private static int menu_option = -1, submenu_option = 0, sub_inter_menuoption = 0;

	static Scanner input = new Scanner(System.in);
	static ImageIcon logo_empresa = new ImageIcon("logo.jpg"), logo_cliente = new ImageIcon("Cobra_Kai.jpg");
	static final String empresa = "Academia Cobra Kai";
	public static void main(String[] args){
		int contador_codigo_aluno = 0, contador_codigo_professor = 0, contador_codigo_aula = 0, faixa = 1, posicao = 0;
		boolean error = false, encontrou = false, invalido = false, armas = true, valido = true;
		String procurar = "", nome = "", CPF = "", email = "", telefone = "",codigo = "", senha = "",senhaMestre = "admin", vincular = "", confirmaSenha = "", horario = "", auxiliar = "", mostrar = "";
		aluno aluno_auxiliar = null;
		professor professor_auxiliar = null;
		aula aula_auxiliar = null;
		Data data = null;
		// Listas de objetos
        LinkedList <aluno> alunos = new LinkedList<aluno>();
		LinkedList <professor> professores = new LinkedList<professor>();
		LinkedList <aula> aulas = new LinkedList<aula>();
		clear();

		/*
		menu principal = new menu(1);
		principal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		principal.setTitle("Menu principal");
		principal.setForeground(Color.WHITE);
		principal.setBackground(Color.DARK_GRAY);
		principal.setResizable(false);
		principal.setSize(500,500);
		principal.setLocationRelativeTo(null);
		principal.setIconImage(logo_cliente.getImage());
		*/

		do{
			menu_option = -1;
			do{
				try{
					error = false;
					auxiliar = (String)JOptionPane.showInputDialog(null, "0 - Sair.\n1 - Cadastro.\n2 - Remoção.\n3 - Vínculos\n4 - Consulta.\n5 - Atualizar Dados.\n6 - Entrada.\n7 - Sincronizar contas com o banco.\n8 - Enviar comprovante para os professores", empresa, JOptionPane.QUESTION_MESSAGE, logo_cliente, null, "");
					if(auxiliar == null){
						auxiliar = "0";
					}
					menu_option = Integer.parseInt(auxiliar);
				}
				catch(NumberFormatException e){
					error = true;
					JOptionPane.showMessageDialog(null, "Insira um número inteiro dentro do intervalo do menu para prosseguir.",empresa,JOptionPane.ERROR_MESSAGE);
				}
				//principal.setVisible(true);
			}while(error || menu_option > 8 || menu_option < 0);
			switch(menu_option){
				case 0:
					encerra();
					break;
				case 1: // Cadastro
					do{
						try{
							error = false;
							submenu_option = Integer.parseInt((String)JOptionPane.showInputDialog(null, "0 - Menu principal.\n1 - Cadastro Aluno.\n2 - Cadastrar professsores.\n3 - Cadastro Aula", empresa, JOptionPane.QUESTION_MESSAGE, logo_cliente, null, ""));
						}
						catch(NumberFormatException e){
							error = true;
							JOptionPane.showMessageDialog(null, "Insira um número inteiro dentro do intervalo do menu para prosseguir.", empresa, JOptionPane.ERROR_MESSAGE);
						}
					}while(error || submenu_option > 3 || submenu_option < 0);
					// Caso professor ou aluno, coleta nome, email, data de nascimento, telefone
					if(submenu_option == 1 || submenu_option == 2){
						nome = insereNome();
						email = insereEmail();
						telefone = insereTelefone();
						data = insereData();
						do{
							senha = insereSenha(false);
							confirmaSenha = insereSenha(true);
							if(!senha.equals(confirmaSenha)){
								JOptionPane.showMessageDialog(null, "Senhas distintas! Tente novamente.", empresa, JOptionPane.ERROR_MESSAGE);
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
										JOptionPane.showMessageDialog(null, "CPF já existente no sistema, tente novamente.", empresa, JOptionPane.ERROR_MESSAGE);
										break;
									}
								}
							}while(invalido);
							contador_codigo_aluno++;
							aluno_auxiliar = new aluno(nome,CPF,email,telefone,"A"+Integer.toString(contador_codigo_aluno),data,senha);
							alunos.add(aluno_auxiliar);
							JOptionPane.showMessageDialog(null, "Aluno cadastrado com sucesso! Código: " + aluno_auxiliar.get_codigo(), empresa, JOptionPane.INFORMATION_MESSAGE);
							break;
// ****************************************************************************************************
						case 2: // Professor
							do{
								invalido = false;
								CPF = insereCPF();
								for(professor pr : professores){
									if(CPF.equals(pr.get_CPF())){
										invalido = true;
										JOptionPane.showMessageDialog(null, "CPF já existente no sistema, tente novamente.", empresa, JOptionPane.ERROR_MESSAGE);
										break;
									}
								}
							}while(invalido);
							contador_codigo_professor++;
							professor_auxiliar = new professor(nome,CPF,email,telefone,"P"+Integer.toString(contador_codigo_professor),data,senha);
							professores.add(professor_auxiliar);
							JOptionPane.showMessageDialog(null, "Professor cadastrado com sucesso! Código: " + professor_auxiliar.get_codigo(),empresa, JOptionPane.INFORMATION_MESSAGE);
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
							JOptionPane.showMessageDialog(null, "Aula cadastrada com sucesso! Código: " + aula_auxiliar.get_codigo(), empresa, JOptionPane.INFORMATION_MESSAGE);
							break;
					}
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
							submenu_option = Integer.parseInt((String)JOptionPane.showInputDialog(null,"0 - Menu principal.\n1 - Remover aluno.\n2 - Remover professor.\n3 - Remover aula",empresa,JOptionPane.QUESTION_MESSAGE,logo_cliente,null,""));
						}
						catch(NumberFormatException e){
							error = true;
							JOptionPane.showMessageDialog(null, "Insira um número inteiro dentro do intervalo do menu para prosseguir.", empresa, JOptionPane.ERROR_MESSAGE);
						}
					}while(error || submenu_option > 3 || submenu_option < 0);
					switch(submenu_option){
						case 1:
							if(alunos.size() == 0){
								JOptionPane.showMessageDialog(null, "Não há alunos cadastrados no sistema.", empresa, JOptionPane.ERROR_MESSAGE);
								break;
							}
							aluno_auxiliar = (aluno)JOptionPane.showInputDialog(null, "Selecione o aluno a ser removido:", empresa, JOptionPane.QUESTION_MESSAGE, logo_cliente, alunos.toArray(new aluno[alunos.size()]), "");
							senha = insereSenha(false);
							if(senha.equals(aluno_auxiliar.get_senha()) || senha.equals(senhaMestre)){
								alunos.remove(aluno_auxiliar);
								JOptionPane.showMessageDialog(null, "Aluno removido com sucesso.", empresa, JOptionPane.INFORMATION_MESSAGE);
							}
							else{
								JOptionPane.showMessageDialog(null, "Senha incorreta.", empresa, JOptionPane.ERROR_MESSAGE);
							}
							break;
// ****************************************************************************************************
						case 2:
							if(professores.size() == 0){
								JOptionPane.showMessageDialog(null, "Não há professores cadastrados no sistema.", empresa, JOptionPane.ERROR_MESSAGE);
								break;
							}
							professor_auxiliar = (professor)JOptionPane.showInputDialog(null, "Selecione o professor a ser removido:", empresa, JOptionPane.QUESTION_MESSAGE, logo_cliente, professores.toArray(new professor[professores.size()]), "");
							senha = insereSenha(false);
							if(senha.equals(professor_auxiliar.get_senha()) || senha.equals(senhaMestre)){
								professores.remove(professor_auxiliar);
								JOptionPane.showMessageDialog(null, "Professor removido com sucesso.", empresa, JOptionPane.INFORMATION_MESSAGE);
							}
							else{
								JOptionPane.showMessageDialog(null, "Senha incorreta.", empresa, JOptionPane.ERROR_MESSAGE);
							}
							break;
// ****************************************************************************************************
						case 3:
							if(aulas.size() == 0){
								//System.out.println("Não há aulas cadastrados no sistema.");
								JOptionPane.showMessageDialog(null, "Não há aulas cadastradas no sistema.", empresa, JOptionPane.ERROR_MESSAGE);
								break;
							}
							aula_auxiliar = (aula)JOptionPane.showInputDialog(null, "Selecione a aula a ser removida:", empresa, JOptionPane.QUESTION_MESSAGE, logo_cliente, aulas.toArray(new aula[aulas.size()]), "");
							for(aluno a : alunos){ // Remove o vínculo dos alunos que possuiam a aula a ser removida
								a.del_aula(aula_auxiliar);
								a.set_valor(valor_aulas(a.get_aulas()));
							}
							aulas.remove(aula_auxiliar);
							JOptionPane.showMessageDialog(null, "Aula removida com sucesso.", empresa, JOptionPane.INFORMATION_MESSAGE);
							break;
					}
					break;
// ****************************************************************************************************
				case 3:
					if(alunos.size() == 0){
						JOptionPane.showMessageDialog(null, "Não há alunos cadastrados no sistema.", empresa, JOptionPane.ERROR_MESSAGE);
					}
					else if(aulas.size() == 0){
						JOptionPane.showMessageDialog(null, "Não há aulas cadastradas no sistema.", empresa, JOptionPane.ERROR_MESSAGE);
					}
					else{
						do{
							try{
								error = false;
								submenu_option = Integer.parseInt((String)JOptionPane.showInputDialog(null,"0 - Menu principal.\n1 - Vincular aula a um aluno.\n2 - Desvincular aula de um aluno",empresa,JOptionPane.QUESTION_MESSAGE,logo_cliente,null,""));
							}
							catch(NumberFormatException e){
								error = true;
								JOptionPane.showMessageDialog(null, "Insira um número inteiro dentro do intervalo do menu para prosseguir.", empresa, JOptionPane.ERROR_MESSAGE);
							}
						}while(error || submenu_option > 2 || submenu_option < 0);
						switch(submenu_option){
							case 1:
								aula_auxiliar = (aula)JOptionPane.showInputDialog(null, "Selecione a aula a ser vinculada:", empresa, JOptionPane.QUESTION_MESSAGE, logo_cliente, aulas.toArray(new aula[aulas.size()]), "");
								do{
									aluno_auxiliar = (aluno)JOptionPane.showInputDialog(null, "Selecione o aluno a ser vinculado:", empresa, JOptionPane.QUESTION_MESSAGE, logo_cliente, alunos.toArray(new aluno[alunos.size()]), "");
									posicao = alunos.indexOf(aluno_auxiliar);
									valido = aluno_auxiliar.add_aula(aula_auxiliar);
									aluno_auxiliar.set_valor(valor_aulas(aluno_auxiliar.get_aulas()));
									alunos.set(posicao,aluno_auxiliar);
									if(valido){
										JOptionPane.showMessageDialog(null, "Aula vinculada ao aluno.", empresa, JOptionPane.INFORMATION_MESSAGE);
									}
									else{
										JOptionPane.showMessageDialog(null, "Aluno possui faixa discrepante com o nível da aula ou já matriculado na mesma.", empresa, JOptionPane.ERROR_MESSAGE);
									}
									sub_inter_menuoption = JOptionPane.showConfirmDialog(null, "Deseja vincular outro aluno a esta mesma aula?", empresa, JOptionPane.YES_NO_OPTION);
								}while(sub_inter_menuoption == 0);
								break;
							case 2:
								aula_auxiliar = (aula)JOptionPane.showInputDialog(null, "Selecione a aula a ser desvinculada:", empresa, JOptionPane.QUESTION_MESSAGE, logo_cliente, aulas.toArray(new aula[aulas.size()]), "");
								do{
									aluno_auxiliar = (aluno)JOptionPane.showInputDialog(null, "Selecione o aluno a ser desvinculado:", empresa, JOptionPane.QUESTION_MESSAGE, logo_cliente, alunos.toArray(new aluno[alunos.size()]), "");
									posicao = alunos.indexOf(aluno_auxiliar);
									aluno_auxiliar.del_aula(aula_auxiliar);
									aluno_auxiliar.set_valor(valor_aulas(aluno_auxiliar.get_aulas()));
									alunos.set(posicao,aluno_auxiliar);
									JOptionPane.showMessageDialog(null, "Aula desvinculada do aluno.", empresa, JOptionPane.INFORMATION_MESSAGE);
									sub_inter_menuoption = JOptionPane.showConfirmDialog(null, "Deseja desvincular outro aluno a esta mesma aula?", empresa, JOptionPane.YES_NO_OPTION);
								}while(sub_inter_menuoption == 0);
								break;
						}
					}
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
							submenu_option = Integer.parseInt((String)JOptionPane.showInputDialog(null,"0 - Menu principal.\n1 - Consulta Aluno.\n2 - Consulta Professor\n3 - Consulta Aula",empresa,JOptionPane.QUESTION_MESSAGE,logo_cliente,null,""));
						}
						catch(NumberFormatException e){
							error = true;
							JOptionPane.showMessageDialog(null, "Insira um número inteiro dentro do intervalo do menu para prosseguir.", empresa, JOptionPane.ERROR_MESSAGE);
						}
					}while(error || submenu_option > 3 || submenu_option < 0);
					switch(submenu_option){
						case 1:
							if(alunos.size() == 0){
								JOptionPane.showMessageDialog(null, "Não há aulas cadastradas no sistema.", empresa, JOptionPane.ERROR_MESSAGE);
								invalido = true;
								break;
							}
							aluno_auxiliar = null;
							aluno_auxiliar = (aluno)JOptionPane.showInputDialog(null, "Selecione o aluno a ser consultado (cancelar = exibir todos):", empresa, JOptionPane.QUESTION_MESSAGE, logo_cliente, alunos.toArray(new aluno[alunos.size()]), "");
							if(aluno_auxiliar == null){
								senha = insereSenha(false);
								if(senha.equals(senhaMestre)){
									for(aluno al : alunos){
										JOptionPane.showMessageDialog(null, al.exibe(),empresa,JOptionPane.INFORMATION_MESSAGE,logo_cliente);
									}
								}
								else{
									JOptionPane.showMessageDialog(null, "Senha incorreta!",empresa,JOptionPane.ERROR_MESSAGE);

								}
							}
							else{
								senha = insereSenha(false);
								if(senha.equals(aluno_auxiliar.get_senha()) || senha.equals(senhaMestre)){
									JOptionPane.showMessageDialog(null, aluno_auxiliar.exibe(),empresa,JOptionPane.INFORMATION_MESSAGE,logo_cliente);
								}
								else{
									JOptionPane.showMessageDialog(null, "Senha incorreta.", empresa, JOptionPane.ERROR_MESSAGE);
								}
							}
							break;
// ****************************************************************************************************
						case 2:
							if(professores.size() == 0){
								JOptionPane.showMessageDialog(null, "Não há professores cadastrados no sistema.", empresa, JOptionPane.ERROR_MESSAGE);
								invalido = true;
								break;
							}
							professor_auxiliar = null;
							professor_auxiliar = (professor)JOptionPane.showInputDialog(null, "Selecione o professor a ser consultado (cancelar = exibe todos):", empresa, JOptionPane.QUESTION_MESSAGE, logo_cliente, professores.toArray(new professor[professores.size()]), "");
							posicao = professores.indexOf(professor_auxiliar);
							if(professor_auxiliar == null){
								senha = insereSenha(false);
								if(senha.equals(senhaMestre)){
									for(professor pr : professores){
										JOptionPane.showMessageDialog(null, pr.exibe(),empresa,JOptionPane.INFORMATION_MESSAGE,logo_cliente);
									}
								}
								else{
									JOptionPane.showMessageDialog(null, "Senha incorreta.", empresa, JOptionPane.ERROR_MESSAGE);
								}
							}
							else{
								senha = insereSenha(false);
								if(senha.equals(professor_auxiliar.get_senha()) || senha.equals(senhaMestre)){
									JOptionPane.showMessageDialog(null, professor_auxiliar.exibe(), empresa, JOptionPane.INFORMATION_MESSAGE,logo_cliente);
								}
								else{
									JOptionPane.showMessageDialog(null, "Senha incorreta.", empresa, JOptionPane.ERROR_MESSAGE);
								}
							}
							break;
// ****************************************************************************************************
						case 3:
							if(aulas.size() == 0){
								JOptionPane.showMessageDialog(null, "Não há aulas cadastradas no sistema.", empresa, JOptionPane.ERROR_MESSAGE);
								invalido = true;
								break;
							}
							aula_auxiliar = null;
							aula_auxiliar = (aula)JOptionPane.showInputDialog(null, "Selecione o aula a ser consultada (cancelar = exibe todos):", empresa, JOptionPane.QUESTION_MESSAGE, logo_cliente, aulas.toArray(new aula[aulas.size()]), "");
							if(aula_auxiliar == null){
								for(aula au : aulas){
									JOptionPane.showMessageDialog(null, au.exibe(),empresa,JOptionPane.INFORMATION_MESSAGE,logo_cliente);
								}
							}
							else{
								JOptionPane.showMessageDialog(null, aula_auxiliar.exibe(), empresa, JOptionPane.INFORMATION_MESSAGE,logo_cliente);
							}
							break;
					}
					break;
// ****************************************************************************************************
				case 5:
					do{
						try{
							error = false;
							submenu_option = Integer.parseInt((String)JOptionPane.showInputDialog(null,"0 - Menu principal.\n1 - Atualizar dados aluno.\n2 -Atualizar dados professores\n 3- Atualizar dados da Aula",empresa,JOptionPane.QUESTION_MESSAGE,logo_cliente,null,""));
						}
						catch(NumberFormatException e){
							error = true;
							JOptionPane.showMessageDialog(null, "Insira um número inteiro dentro do intervalo do menu para prosseguir.", empresa, JOptionPane.ERROR_MESSAGE);
						}
					}while(error || submenu_option > 3 || submenu_option < 0);
					invalido = false;
					switch(submenu_option){
						case 1:
							if(alunos.size() == 0){
								JOptionPane.showMessageDialog(null, "Não há alunos cadastrados no sistema", empresa, JOptionPane.ERROR_MESSAGE);
								invalido = true;
								break;
							}
							aluno_auxiliar = (aluno)JOptionPane.showInputDialog(null, "Selecione o aluno a ser atualizado:", empresa, JOptionPane.QUESTION_MESSAGE, logo_cliente, alunos.toArray(new aluno[alunos.size()]), "");
							posicao = alunos.indexOf(aluno_auxiliar);
							senha = insereSenha(false);
							if(senha.equals(/*al*/aluno_auxiliar.get_senha()) || senha.equals(senhaMestre)){
								do{
									try{
										error = false;
										sub_inter_menuoption = Integer.parseInt((String)JOptionPane.showInputDialog(null,"1 - Nome.\n2 - CPF.\n3 - Email.\n 4 - Telefone.\n5 - Data de Nascimento. \n6 - Auxiliar.\n7 - Faixa.\n8 - Senha.",empresa,JOptionPane.QUESTION_MESSAGE,logo_cliente,null,""));
									}
									catch(NumberFormatException e){
										error = true;
										JOptionPane.showMessageDialog(null, "Insira um número inteiro dentro do intervalo do menu para prosseguir.", empresa, JOptionPane.ERROR_MESSAGE);
									}
								}while(error || menu_option > 8 || menu_option < 1);
								switch(sub_inter_menuoption){
									case 1:
										aluno_auxiliar.set_nome(insereNome());
										break;
									case 2:
										do{
											invalido = false;
											CPF = insereCPF();
											for(aluno a : alunos){
												if(CPF.equals(a.get_CPF())){
													invalido = true;
													JOptionPane.showMessageDialog(null, "CPF já existente no sistema.", empresa, JOptionPane.ERROR_MESSAGE);
													break;
												}
											}
										}while(invalido);
										aluno_auxiliar.set_CPF(CPF);
										break;
									case 3:
										aluno_auxiliar.set_email(insereEmail());
										break;
									case 4:
										aluno_auxiliar.set_telefone(insereTelefone());
										break;
									case 5:
										aluno_auxiliar.set_nascimento(insereData());
										break;
									case 6:
										aluno_auxiliar.set_auxiliar(insereAuxiliar());
										break;
									case 7:
										aluno_auxiliar.set_faixa(insereFaixa());
										break;
									case 8:
										do{
											vincular = insereSenha(false);
											confirmaSenha = insereSenha(true);
											if(!vincular.equals(confirmaSenha)){
												JOptionPane.showMessageDialog(null, "Senha distintas! Tente novamente.", empresa, JOptionPane.ERROR_MESSAGE);
											}
										}while(!vincular.equals(confirmaSenha));
										aluno_auxiliar.set_senha(senha,vincular,senhaMestre);
										break;
								}
										alunos.set(posicao,aluno_auxiliar);
							}
							else{
								JOptionPane.showMessageDialog(null, "Senha incorreta.", empresa, JOptionPane.ERROR_MESSAGE);
								invalido = true;
							}
							if(!invalido){
								JOptionPane.showMessageDialog(null, "Aluno atualizado com sucesso.", empresa, JOptionPane.INFORMATION_MESSAGE);
							}
							break;
	// ****************************************************************************************************
						case 2:
							if(professores.size() == 0){
								JOptionPane.showMessageDialog(null, "Não há professores cadastrados no sistema.", empresa, JOptionPane.ERROR_MESSAGE);
								invalido = true;
								break;
							}
							professor_auxiliar = (professor)JOptionPane.showInputDialog(null, "Selecione o professor a ser atualizado:", empresa, JOptionPane.QUESTION_MESSAGE, logo_cliente, professores.toArray(new professor[professores.size()]), "");
							posicao = professores.indexOf(professor_auxiliar);
							senha = insereSenha(false);
							if(senha.equals(professor_auxiliar.get_senha()) || senha.equals(senhaMestre)){
								do{
									try{
										error = false;
										sub_inter_menuoption = Integer.parseInt((String)JOptionPane.showInputDialog(null,"1 - Nome.\n2 - CPF.\n3 - Email.\n 4 - Telefone.\n5 - Data de Nascimento. \n6 - Senha.\n->",empresa,JOptionPane.QUESTION_MESSAGE,logo_cliente,null,""));
									}
									catch(NumberFormatException e){
										error = true;
										JOptionPane.showMessageDialog(null, "Insira um número inteiro dentro do intervalo do menu para prosseguir.", empresa, JOptionPane.ERROR_MESSAGE);
									}
								}while(error || menu_option > 6 || menu_option < 1);
								switch(sub_inter_menuoption){
									case 1:
										professor_auxiliar.set_nome(insereNome());
										break;
									case 2:
										do{
											invalido = false;
											CPF = insereCPF();
											for(professor p : professores){
												if(CPF.equals(p.get_CPF())){
													invalido = true;
													JOptionPane.showMessageDialog(null, "CPF já existente no sistema, tente novamente.", empresa, JOptionPane.ERROR_MESSAGE);
													break;
												}
											}
										}while(invalido);
										professor_auxiliar.set_CPF(CPF);
										break;
									case 3:
										professor_auxiliar.set_email(insereEmail());
										break;
									case 4:
										professor_auxiliar.set_telefone(insereTelefone());
										break;
									case 5:
										professor_auxiliar.set_nascimento(insereData());
										break;
									case 6:
										do{
											vincular = insereSenha(false);
											confirmaSenha = insereSenha(true);
											if(!vincular.equals(confirmaSenha)){
												JOptionPane.showMessageDialog(null, "Senhas distintas! Tente novamente.", empresa, JOptionPane.ERROR_MESSAGE);
											}
										}while(!vincular.equals(confirmaSenha));
										professor_auxiliar.set_senha(senha,vincular,senhaMestre);
										break;
								}
								professores.set(posicao,professor_auxiliar);
							}
							else{
								JOptionPane.showMessageDialog(null, "Senha incorreta.", empresa, JOptionPane.ERROR_MESSAGE);
										invalido = true;
							}
							if(!invalido){
								JOptionPane.showMessageDialog(null, "Professor atualizado com sucesso.", empresa, JOptionPane.INFORMATION_MESSAGE);
							}
							break;
	// ****************************************************************************************************
						case 3:
							if(aulas.size() == 0){
								JOptionPane.showMessageDialog(null, "Não há aulas cadastradas no sistema.", empresa, JOptionPane.ERROR_MESSAGE);
								invalido = true;
								break;
							}

							aula_auxiliar = (aula)JOptionPane.showInputDialog(null, "Selecione a aula a ser atualizada:", empresa, JOptionPane.QUESTION_MESSAGE, logo_cliente, aulas.toArray(new aula[aulas.size()]), "");
							posicao = aulas.indexOf(aula_auxiliar);
							do{
								try{
									error = false;
										sub_inter_menuoption = Integer.parseInt((String)JOptionPane.showInputDialog(null,"1 - Armas.\n2 - Faixa.\n3 - Horário.",empresa,JOptionPane.QUESTION_MESSAGE,logo_cliente,null,""));
								}
								catch(NumberFormatException e){
									error = true;
									JOptionPane.showMessageDialog(null, "Insira um número inteiro dentro do intervalo do menu para prosseguir.", empresa, JOptionPane.ERROR_MESSAGE);
								}
							}while(error || menu_option > 5 || menu_option < 1);
							switch(sub_inter_menuoption){
								case 1:
									aula_auxiliar.set_arma(insereArmas());
									break;
								case 2:
									aula_auxiliar.set_faixa(insereFaixa());
									break;
								case 3:
									aula_auxiliar.set_horario(insereHorario());
									break;
							}
							aulas.set(posicao,aula_auxiliar);
							JOptionPane.showMessageDialog(null, "Aula atualizada com sucesso.", empresa, JOptionPane.INFORMATION_MESSAGE);
							break;
					}
					break;
// ****************************************************************************************************
				case 6:
					if(alunos.size() == 0 && professores.size() == 0){
						JOptionPane.showMessageDialog(null, "Cadastros inexistentes.", empresa, JOptionPane.ERROR_MESSAGE);
					}
					else{
						codigo = (String)JOptionPane.showInputDialog(null, "Insira o código do aluno/professor que deseja entrar.", empresa, JOptionPane.QUESTION_MESSAGE, logo_cliente, null, "");
						encontrou = false;
						codigo = codigo.toUpperCase();
						if(codigo.charAt(0) == 'A'){
							for(aluno al : alunos){
								if(al.get_codigo().equals(codigo)){
									encontrou = true;
									if(al.get_mes_pago()){
										JOptionPane.showMessageDialog(null, "Seja bem vindo, " + al.get_nome() + ".", empresa, JOptionPane.INFORMATION_MESSAGE);
										break;
									}
									else{
										JOptionPane.showMessageDialog(null, "Pague sua mensalidade para entrar, " + al.get_nome() + ".", empresa, JOptionPane.INFORMATION_MESSAGE);
										break;
									}
								}
							}
							if(!encontrou){
								JOptionPane.showMessageDialog(null, "Aluno inexistente no sistema.", empresa, JOptionPane.ERROR_MESSAGE);
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
									JOptionPane.showMessageDialog(null, "Acesso registrado, " + pr.get_nome() + ".", empresa, JOptionPane.INFORMATION_MESSAGE);
								}
							}
							if(!encontrou){
								JOptionPane.showMessageDialog(null, "Professor inexistente no sistema.", empresa, JOptionPane.ERROR_MESSAGE);
							}
						}
						else{
							JOptionPane.showMessageDialog(null, "Pessoa inexistente no sistema.", empresa, JOptionPane.ERROR_MESSAGE);
						}
					}
					break;
// ****************************************************************************************************
				case 7: /// PARTE DO banco
					break;
// ****************************************************************************************************
				case 8: // Envio do histórico de acesso para os professores por email
					if(professores.size() == 0){
						JOptionPane.showMessageDialog(null, "Não há professores cadastrados.", empresa, JOptionPane.ERROR_MESSAGE);
					}
					else{
						senha = insereSenha(false);
						if(senha.equals(senhaMestre)){
							for(professor pf : professores){
								pf.renova_mes();
							}
						JOptionPane.showMessageDialog(null, "Comprovantes enviados por email a todos os professores.", empresa, JOptionPane.INFORMATION_MESSAGE);
						}
						else{
							JOptionPane.showMessageDialog(null, "Senha incorreta.", empresa, JOptionPane.ERROR_MESSAGE);
						}
					}
					break;
			}
		}while(menu_option != 0);
	}

	// Janela
	menu(int i){
		setLayout(null);
		switch(i){
			case 1:
				titulo = new JLabel("Menu principal");
				titulo.setForeground(Color.WHITE);
				titulo.setBackground(Color.DARK_GRAY);
				titulo.setBounds(120, 5, 100, 20);
				add(titulo);
				MP0 = new JRadioButton("Sair.");
				MP0.setBounds(10, 35, 100, 20);
				MP0.setSelected(true);
				MP1 = new JRadioButton("Cadastro.");
				MP1.setBounds(10, 65, 100, 20);
				MP2 = new JRadioButton("Remoção.");
				MP2.setBounds(10, 95, 100, 20);
				MP3 = new JRadioButton("Vínculos.");
				MP3.setBounds(10, 125, 100, 20);
				MP4 = new JRadioButton("Consulta.");
				MP4.setBounds(10, 155, 100, 20);
				MP5 = new JRadioButton("Atualizar.");
				MP5.setBounds(10, 185, 100, 20);
				MP6 = new JRadioButton("Entrada.");
				MP6.setBounds(10, 215, 100, 20);
				MP7 = new JRadioButton("Sincronizar contas com o banco.");
				MP7.setBounds(10, 245, 100, 20);
				MP8 = new JRadioButton("Enviar comprovantes para os professores.");
				MP8.setBounds(10, 275, 100, 20);

				Menu_op = new ButtonGroup();
				Menu_op.add(MP0);
				Menu_op.add(MP1);
				Menu_op.add(MP2);
				Menu_op.add(MP3);
				Menu_op.add(MP4);
				Menu_op.add(MP5);
				Menu_op.add(MP6);
				Menu_op.add(MP7);
				Menu_op.add(MP8);
				add(MP0);
				add(MP1);
				add(MP2);
				add(MP3);
				add(MP4);
				add(MP5);
				add(MP6);
				add(MP7);
				add(MP8);


				Botao_MP = new JButton("Ok");
				Botao_MP.setBounds(120,5,120,40);
				Botao_MP.setMnemonic('O');
				Botao_MP.setToolTipText("Clique para prosseguir");
				Botao_MP.setForeground(Color.RED);
				Botao_MP.addActionListener(this);
				add(Botao_MP);
				break;
			case 2:
				ok_cadastro_aluno = new JButton("TEste");
				ok_cadastro_aluno.setBounds(110,385,120,40);
				ok_cadastro_aluno.setMnemonic('T');
				ok_cadastro_aluno.setToolTipText("TESTE...");
				ok_cadastro_aluno.setForeground(Color.RED);
				ok_cadastro_aluno.addActionListener(this);
				add(ok_cadastro_aluno);
				break;
		}
	}

	// Método que coleta o clique
	public void actionPerformed(ActionEvent acesso){
		if(acesso.getSource() == Botao_MP){
			if(MP0.isSelected())
				menu_option = 0;
			if(MP1.isSelected())
				menu_option = 1;
			if(MP2.isSelected())
				menu_option = 2;
			if(MP3.isSelected())
				menu_option = 3;
			if(MP4.isSelected())
				menu_option = 4;
			if(MP5.isSelected())
				menu_option = 5;
			if(MP6.isSelected())
				menu_option = 6;
			if(MP7.isSelected())
				menu_option = 7;
			if(MP8.isSelected())
				menu_option = 8;
		}
		else if(acesso.getSource() == ok_cadastro_aluno){

		}
	}



	// Método que limpa a tela do terminal
	public final static void clear(){
		try{
			final String os = System.getProperty("os.name");
			if(os.contains("Windows")){ // Caso o sistema operacional da máquina seja Windows
				new ProcessBuilder("cmd","/c","cls").inheritIO().start().waitFor();
			}
			else{ // Caso o sistema operacional da máquina seja Linux ou MAC
				System.out.print("\33\143"); // 33 - limpa a tela ; 143 volta o cursor de texto para o início
			}
		}
		catch(final Exception e){
			JOptionPane.showMessageDialog(null, "Não foi possivel limpar a tela.", empresa, JOptionPane.ERROR_MESSAGE);
		}
	}

	// Método que coleta o nome
	public static String insereNome(){
		String nome = (String)JOptionPane.showInputDialog(null, "Nome:", empresa, JOptionPane.QUESTION_MESSAGE);
		nome = nome.toUpperCase();
		return nome;
	}

	// Método que coleta senha (ou confirmação de senha)
	public static String insereSenha(boolean confirma){
		String senha;
		if(confirma){
			senha = (String)JOptionPane.showInputDialog(null, "Confirme senha:", empresa, JOptionPane.QUESTION_MESSAGE);
		}
		else{
			senha = (String)JOptionPane.showInputDialog(null, "Senha:", empresa, JOptionPane.QUESTION_MESSAGE);
		}

		return senha;
	}

	//Insere CPF
	public static String insereCPF(){
		boolean invalido = true;
		String CPF = "";
		do{//Verifica conflito CPF
			CPF = (String)JOptionPane.showInputDialog(null, "CPF:", empresa, JOptionPane.QUESTION_MESSAGE);
			if(CPF.matches("[0-9]+") && CPF.length() == 11){
				invalido = false;
			}
			if(invalido){
				JOptionPane.showMessageDialog(null, "CPF inválido, tente novamente digitando apenas números.", empresa, JOptionPane.ERROR_MESSAGE);
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
			email = (String)JOptionPane.showInputDialog(null, "Email:", empresa, JOptionPane.QUESTION_MESSAGE);
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
				JOptionPane.showMessageDialog(null, "Email inválido, tente novamente.", empresa, JOptionPane.ERROR_MESSAGE);
			}
		}while(invalido);
		return email;
	}

	// Formata o telefone (0000000000 -> 00 0000-0000 ou 0000000000 -> 00 00000-0000)
	public static String insereTelefone(){
		boolean invalido = true;
		String telefone;
		do{

			telefone = (String)JOptionPane.showInputDialog(null, "Telefone:", empresa, JOptionPane.QUESTION_MESSAGE);
			if((telefone.length() == 10 || telefone.length() == 11) && telefone.matches("[0-9]+")){ // Verifica se existe somente números e verifica o tamanho (caso de celular / telefone fixo)
				invalido = false;
			}
			else{
				JOptionPane.showMessageDialog(null, "Telefone inválido, tente novamente.", empresa, JOptionPane.ERROR_MESSAGE);
			}
		}while(invalido);
		if(telefone.length() == 10){
			return telefone.substring(0,2) + ' ' + telefone.substring(2,6) + '-' + telefone.substring(6);
		}
		return telefone.substring(0,2) + ' ' + telefone.substring(2,7) + '-' + telefone.substring(7);
	}

	// Método que insere data
	public static Data insereData(){
		boolean invalido = true, error;
		String aux = "";
		int dia = 0, mes = 0, ano = 0;
		Data data = null;
		do{
			error = true;
			do{
				try{
					error = false;
					aux = (String)JOptionPane.showInputDialog(null, "Dia:", empresa, JOptionPane.QUESTION_MESSAGE);
					dia = Integer.parseInt(aux);
					aux = (String)JOptionPane.showInputDialog(null, "Mes:", empresa, JOptionPane.QUESTION_MESSAGE);
					mes = Integer.parseInt(aux);
					aux = (String)JOptionPane.showInputDialog(null, "Ano:", empresa, JOptionPane.QUESTION_MESSAGE);
					ano = Integer.parseInt(aux);
				}
				catch(NumberFormatException e){
					error = true;
					JOptionPane.showMessageDialog(null, "Insira um número inteiro para prosseguir.", empresa, JOptionPane.ERROR_MESSAGE);
				}
			}while(error);
			try{
				invalido = false;
				data = new Data(dia, mes, ano);
			}
			catch(IllegalArgumentException VariableDeclaratorId){
				invalido = true;
				JOptionPane.showMessageDialog(null, VariableDeclaratorId, empresa, JOptionPane.ERROR_MESSAGE);
			}
		}while(invalido);
		return data;
	}

	public static boolean insereAuxiliar(){
		int aux = 1;
		aux = JOptionPane.showConfirmDialog(null, "Aluno auxiliar?", empresa, JOptionPane.YES_NO_OPTION);
		if(aux == 0){
			return true;
		}
		return false;
	}

	public static int insereFaixa(){
		int faixa = 0;
		boolean error = true;
		do{ // Inserção do nível
			if(faixa > 10 || faixa < 0){
				JOptionPane.showMessageDialog(null, "Insira um número dentro do intervalo para prosseguir.", empresa, JOptionPane.ERROR_MESSAGE);
			}
			try{
				error = false;
				faixa = Integer.parseInt((String)JOptionPane.showInputDialog(null,"0 - Branca.\n1 - Amarela.\n2 - Laranja\n3 - Verde.\n4 - Azul.\n5 - Roxa.\n6 - Vermelha.\n7 - Marrom.\n8 - Marrom ponta preta.\n9 - Preta",empresa,JOptionPane.QUESTION_MESSAGE,logo_cliente,null,""));
			}
			catch(InputMismatchException InputMismatchException){
				error = true;
				JOptionPane.showMessageDialog(null, "Insira um número inteiro para prosseguir.", empresa, JOptionPane.ERROR_MESSAGE);
			}
		}while(error || faixa > 10 || faixa < 0);
		return faixa;
	}

	public static boolean insereArmas(){
		int armas = 1;
		armas = JOptionPane.showConfirmDialog(null, "Esta aula utiliza armas?", empresa, JOptionPane.YES_NO_OPTION);
		if(armas == 0){
			return true;
		}
		return false;
	}

	public static String insereHorario(){
		int dia = 1, hora = 1;
		boolean error;
		do{ // Inserção do dia da semana
			if(dia > 6 || dia < 1){
				JOptionPane.showMessageDialog(null, "Insira um número inteiro dentro do intervalo para prosseguir.", empresa, JOptionPane.ERROR_MESSAGE);
			}
			try{
				error = false;

				dia = Integer.parseInt((String)JOptionPane.showInputDialog(null,"1 - Segunda-feira.\n2 - Terça-feira.\n3 - Quarta-feira.\n4 - Quinta-feira.\n5 - Sexta-feira. \n6 - Sábado.",empresa,JOptionPane.QUESTION_MESSAGE,logo_cliente,null,""));
			}
			catch(InputMismatchException InputMismatchException){
				error = true;
				JOptionPane.showMessageDialog(null, "Insira um número inteiro para prosseguir.", empresa, JOptionPane.ERROR_MESSAGE);
			}
		}while(error || dia > 6 || dia < 1);
		do{ // Inserção do horário
			if(hora < 1 || (hora > 15 && dia != 6) || (hora > 11 && dia == 6)){
				JOptionPane.showMessageDialog(null, "Insira um número inteiro dentro do intervalo para prosseguir.", empresa, JOptionPane.ERROR_MESSAGE);
			}
			try{
				error = false;
				hora = Integer.parseInt((String)JOptionPane.showInputDialog(null," 1 - 7:00.\n 2 - 8:00.\n 3 - 9:00.\n 4 - 10:00.\n 5 - 11:00. \n 6 - 12:00.\n 7 - 13:00.\n 8 - 14:00.\n 9 - 15:00.\n10 - 16:00.\n11 - 17:00."+(dia != 6 ? "\n12 - 18:00.\n14 - 19:00.\n15 - 21:00." : null),empresa,JOptionPane.QUESTION_MESSAGE,logo_cliente,null,""));
			}
			catch(InputMismatchException InputMismatchException){
				error = true;
				JOptionPane.showMessageDialog(null, "Insira um número inteiro para prosseguir.", empresa, JOptionPane.ERROR_MESSAGE);
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

	// Caixa que demonstra o fim do programa
	public static void encerra(){
		JOptionPane.showMessageDialog(null, "Obrigado por utilizar o sistema desenvolvido por RAH - Desenvolvimento de Sistemas.",empresa,JOptionPane.ERROR_MESSAGE,logo_empresa);
	}
}