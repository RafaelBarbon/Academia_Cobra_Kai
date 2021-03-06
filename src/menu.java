// Alcides Gomes Beato Neto		19060987
// Henrique Sartori Siqueira	19240472
// Rafael Silva Barbon			19243633

import java.sql.*;
import java.util.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import javax.swing.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;
import jssc.*;
public class menu{
	static ImageIcon logo_empresa = new ImageIcon("logo.jpg"), logo_cliente = new ImageIcon("Cobra_Kai.jpg");
	static final String empresa = "Academia Cobra Kai";
	public static void main(String[] args){
		int menu_option = 0, submenu_option = 0, sub_inter_menuoption = 0, contador_codigo_aluno = 0, contador_codigo_professor = 0, contador_codigo_aula = 0, faixa = 0, posicao = 0, i = 0, aux = 0;
		boolean error = false, encontrou = false, invalido = false, armas = true;
		String nome = "", CPF = "", email = "", telefone = "",codigo = "", senha = "", vincular = "", confirmaSenha = "", horario = "", auxiliar = "", arquivo = "", descricao = "", formato = "",portlist[] = SerialPortList.getPortNames();
		//char caract[] = new char[5];
		// Valores modificáveis nas configurações (carregados a partir do banco de dados)
		float valores[] = new float[4], valoresaux[] = new float[4];
		// Objetos auxiliares para manipulação
		aluno aluno_auxiliar = null;
		professor professor_auxiliar = null;
		aula aula_auxiliar = null;
		Data data = null;
		// Variáveis para coletar o registro dos professores
		DateTimeFormatter formatarel,formatabanco;
		LocalDateTime agora;
		// Listas de objetos
        LinkedList <aluno> alunos = new LinkedList<aluno>();
		LinkedList <professor> professores = new LinkedList<professor>();
		LinkedList <aula> aulas = new LinkedList<aula>();
		// Variáveis para composição do email
		MimeMessage mensagem; // Cria o objeto mensagem
		DataSource caminho; // Localização do arquivo no diretório
		BodyPart corpo; // Cria a mensagem do email
		Multipart anexo;
		Properties configuracao;
		Session sessao;
		// Variáveis para o banco de dados
		Connection link = null;
		Statement stat = null, statinter = null;
		ResultSet result = null,result2 = null;
		final String driver = "com.mysql.cj.jdbc.Driver";
		final String url = "jdbc:mysql://localhost:3306/academia";
		final String user = "root";
		final String pass = insereSenha(false);

		clear();

		// Inicialização das variáveis essenciais para manipulação
		try{
			Class.forName(driver); // Conecta com o mysql
			link = DriverManager.getConnection(url,user,pass); // Estabelece conexão com o banco de dados
			stat = link.createStatement(); // Variável usada para atribuir os comandos realizados ao banco de dados
			statinter = link.createStatement();
			result = stat.executeQuery("select * from info;"); // Coleta resultado da query
			result.next(); // Avança para a próxima LINHA (no caso a primeira)
			valores[0] = result.getFloat(2); // Cada número simboliza a COLUNA selecionada
			valores[1] = result.getFloat(3);
			valores[2] = result.getFloat(4);
			valores[3] = result.getFloat(5);
			senha = result.getString(6);
			confirmaSenha = result.getString(7);
			email = result.getString(8);
			// Inicializa contadores
			result = stat.executeQuery("select max(codigo) from aulas;"); // Coleta resultado da query
			if(result.next()){
				auxiliar = result.getString(1);
				if(auxiliar != null){
					contador_codigo_aula = Integer.parseInt(auxiliar.substring(1));
				}
			}
			result = stat.executeQuery("select max(codigo) from alunos;"); // Coleta resultado da query
			if(result.next()){
				auxiliar = result.getString(1);
				if(auxiliar != null){
					contador_codigo_aluno = Integer.parseInt(auxiliar.substring(1));
				}
			}
			result = stat.executeQuery("select max(codigo) from professores;"); // Coleta resultado da query
			if(result.next()){
				auxiliar = result.getString(1);
				if(auxiliar != null){
					contador_codigo_professor = Integer.parseInt(auxiliar.substring(1));
				}
			}
			// Inicializa as listas
			result = stat.executeQuery("select * from aulas;");
			while(result.next()){
				aula_auxiliar = new aula(result.getInt(3), result.getBoolean(4), result.getString(2), result.getString(5),result.getString(1));
				aulas.add(aula_auxiliar);
			}
			result = stat.executeQuery("select * from alunos;");
			while(result.next()){
				auxiliar = result.getString(9); // Para a data
				data = new Data(Integer.parseInt(auxiliar.substring(8)),Integer.parseInt(auxiliar.substring(5, 7)),Integer.parseInt(auxiliar.substring(0, 4)));
				aluno_auxiliar = new aluno(result.getString(2),result.getString(3),result.getString(4),result.getString(5),result.getString(1),data,result.getString(12));
				aluno_auxiliar.set_faixa(result.getInt(10));
				aluno_auxiliar.set_auxiliar(result.getBoolean(11));
				aluno_auxiliar.atraso(result.getInt(6));
				result2 = statinter.executeQuery("select id_aula from matriculas where id_aluno = '" + aluno_auxiliar.get_codigo() +"';"); // Vínculos com as aulas
				while(result2.next()){
					for(aula au : aulas){
						if(result2.getString(1).equals(au.get_codigo())){
							aluno_auxiliar.add_aula(au);
						}
					}
				}
				switch(aluno_auxiliar.get_aulas()){
					case 0: aluno_auxiliar.set_valor(0); break;
					case 1:	aluno_auxiliar.set_valor(valores[0]); break;
					case 2:	aluno_auxiliar.set_valor(valores[1]); break;
					case 3: aluno_auxiliar.set_valor(valores[2]); break;
					default: aluno_auxiliar.set_valor(valores[3]);
				}
				alunos.add(aluno_auxiliar);
			}
			result = stat.executeQuery("select * from professores;");
			while(result.next()){
				auxiliar = result.getString(7); // Para a data
				data = new Data(Integer.parseInt(auxiliar.substring(8)),Integer.parseInt(auxiliar.substring(5, 7)),Integer.parseInt(auxiliar.substring(0, 4)));
				professor_auxiliar = new professor(result.getString(2), result.getString(3), result.getString(4), result.getString(5), result.getString(1), data, result.getString(6));
				result2 = statinter.executeQuery("select horario from acessos where id_professor = '" + professor_auxiliar.get_codigo() +"';"); // Horários de acessos
				while(result2.next()){
					auxiliar = result2.getString(3);
					auxiliar = auxiliar.substring(8, 10) + "/" + auxiliar.substring(5, 7) + "/" + auxiliar.substring(0, 4) + auxiliar.substring(10);
					professor_auxiliar.registra_acesso(auxiliar);
				}
				professores.add(professor_auxiliar);
			}
			result.close();
			if(result2 != null){
				result2.close();
			}
			if(statinter != null){
				statinter.close();
			}
			stat.close();
			link.close();
		}
		catch(Exception e){
			//JOptionPane.showMessageDialog(null, e, empresa, JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
		// Controle total das informações
		final String senhaMestre = senha;
		// Variáveis de manipulação de email
		final String password = confirmaSenha;
		final String username = email;
		final String servidor = coletaserver(username);

		do{
			menu_option = menuopcoes("Cancelar ou 0 -> Sair.\n1 -> Cadastro.\n2 -> Remoção.\n3 -> Vínculos.\n4 -> Consulta.\n5 -> Atualizar dados.\n6 -> Entrada.\n7 -> Sincronizar contas com o banco.\n8 -> Enviar comprovante para os professores.\n9 -> Configurações.", 9);
			switch(menu_option){
				case 1: // Cadastro
					submenu_option = menuopcoes("Cancelar ou 0 -> Menu principal.\n1 -> Cadastrar aluno.\n2 -> Cadastrar professor.\n3 -> Cadastrar aula.", 3);
					// Caso professor ou aluno, coleta nome, email, data de nascimento, telefone de início
					do{
						sub_inter_menuoption = 1;
						if(submenu_option == 1 || submenu_option == 2){
							nome = insereNome();
							if(nome == null){
								break;
							}
							email = insereEmail();
							if(email == null){
								break;
							}
							telefone = insereTelefone();
							if(telefone == null){
								break;
							}
							data = insereData();
							if(data == null){
								break;
							}
							do{
								senha = insereSenha(false);
								if(senha == null){
									break;
								}
								confirmaSenha = insereSenha(true);
								if(confirmaSenha == null){
									break;
								}
								if(!senha.equals(confirmaSenha)){
									JOptionPane.showMessageDialog(null, "Senhas distintas! Tente novamente.", empresa, JOptionPane.ERROR_MESSAGE);
								}
							}while(!senha.equals(confirmaSenha));
							if(senha == null || confirmaSenha == null){
								break;
							}
						}
						switch(submenu_option){
							case 1: // Aluno
								do{
									invalido = false;
									CPF = insereCPF();
									if(CPF == null){
										break;
									}
									for(aluno al : alunos){
										if(CPF.equals(al.get_CPF())){
											invalido = true;
											JOptionPane.showMessageDialog(null, "CPF já existente no sistema, tente novamente.", empresa, JOptionPane.ERROR_MESSAGE);
											break;
										}
									}
								}while(invalido);
								if(CPF == null){
									break;
								}
								contador_codigo_aluno++;
								auxiliar = "A"+Integer.toString(contador_codigo_aluno);
								while(auxiliar.length() < 5){
									auxiliar = auxiliar.charAt(0) + "0" + auxiliar.substring(1);
								}
								aluno_auxiliar = new aluno(nome,CPF,email,telefone,auxiliar,data,senha);
								alunos.add(aluno_auxiliar);
								// Banco de dados
								try{
									Class.forName(driver); // Conecta com o mysql
									link = DriverManager.getConnection(url,user,pass); // Estabelece conexão com o banco de dados
									stat = link.createStatement(); // Variável usada para atribuir os comandos realizados ao banco de dados
									stat.execute("insert into banco values ('" + CPF + "',true);");
									formato = data.toString();
									formato = formato.substring(6) + "-" + formato.substring(3,5) + "-" + formato.substring(0,2);
									stat.execute("insert into alunos values ('" + auxiliar + "','" + nome + "','" + CPF + "','" + email + "','" + telefone + "',0,true,0,'" + formato + "',0,false, '" + senha + "');");
									stat.close();
									result.close();
									link.close();
								} // YYYY-MM-dd -> dd/MM/YYYY
								catch(Exception e){
									//JOptionPane.showMessageDialog(null, e, empresa, JOptionPane.ERROR_MESSAGE);
									e.printStackTrace();
								}
								sub_inter_menuoption = JOptionPane.showConfirmDialog(null, "Aluno cadastrado com sucesso! Código: " + aluno_auxiliar.get_codigo() + ". Deseja cadastrar outro aluno?", empresa, JOptionPane.YES_NO_OPTION);
								break;
// ****************************************************************************************************
							case 2: // Professor
								do{
									invalido = false;
									CPF = insereCPF();
									if(CPF == null){
										break;
									}
									for(professor pr : professores){
										if(CPF.equals(pr.get_CPF())){
											invalido = true;
											JOptionPane.showMessageDialog(null, "CPF já existente no sistema, tente novamente.", empresa, JOptionPane.ERROR_MESSAGE);
											break;
										}
									}
								}while(invalido);
								if(CPF == null){
									break;
								}
								contador_codigo_professor++;
								auxiliar = "P"+Integer.toString(contador_codigo_professor);
								while(auxiliar.length() < 5){
									auxiliar = auxiliar.charAt(0) + "0" + auxiliar.substring(1);
								}
								professor_auxiliar = new professor(nome,CPF,email,telefone,auxiliar,data,senha);
								professores.add(professor_auxiliar);
								// Banco de dados
								try{
									Class.forName(driver); // Conecta com o mysql
									link = DriverManager.getConnection(url,user,pass); // Estabelece conexão com o banco de dados
									stat = link.createStatement(); // Variável usada para atribuir os comandos realizados ao banco de dados
									formato = data.toString();
									formato = formato.substring(6) + "-" + formato.substring(3,5) + "-" + formato.substring(0,2);
									stat.execute("insert into professores values ('" + auxiliar + "','" + nome + "','" + CPF + "','" + email + "','" + telefone + "','" + senha + "','" + formato + "');");
									stat.close();
									result.close();
									link.close();
								} // YYYY-MM-dd -> dd/MM/YYYY
								catch(Exception e){
									//JOptionPane.showMessageDialog(null, e, empresa, JOptionPane.ERROR_MESSAGE);
									e.printStackTrace();
								}
								sub_inter_menuoption = JOptionPane.showConfirmDialog(null, "Professor cadastrado com sucesso! Código: " + professor_auxiliar.get_codigo() + ". Deseja cadastrar outro professor?",empresa, JOptionPane.YES_NO_OPTION);
								break;
// ****************************************************************************************************
							case 3: // Aulas
								horario = insereHorario();
								if(horario == null){
									break;
								}
								faixa = insereFaixa();
								if(faixa == -1){
									break;
								}
								armas = insereArmas();
								descricao = insereDescricao();
								if(descricao == null){
									break;
								}
								contador_codigo_aula++;
								auxiliar = "C"+Integer.toString(contador_codigo_aula);
								while(auxiliar.length() < 5){
									auxiliar = auxiliar.charAt(0) + "0" + auxiliar.substring(1);
								}
								aula_auxiliar = new aula(faixa, armas, horario, descricao, auxiliar);
								aulas.add(aula_auxiliar);
								// Banco de dados
								try{
									Class.forName(driver); // Conecta com o mysql
									link = DriverManager.getConnection(url,user,pass); // Estabelece conexão com o banco de dados
									stat = link.createStatement(); // Variável usada para atribuir os comandos realizados ao banco de dados
									stat.execute("insert into aulas values ('" + auxiliar + "','" + horario + "'," + faixa + "," + armas + ",'" + descricao + "');");
									stat.close();
									link.close();
								}
								catch(Exception e){
									//JOptionPane.showMessageDialog(null, e, empresa, JOptionPane.ERROR_MESSAGE);
									e.printStackTrace();
								}
								sub_inter_menuoption = JOptionPane.showConfirmDialog(null, "Aula cadastrada com sucesso! Código: " + aula_auxiliar.get_codigo() + ". Deseja cadastrar outra aula?", empresa, JOptionPane.YES_NO_OPTION);
								break;
						}
					}while(sub_inter_menuoption == 0);
					break;
// ****************************************************************************************************
				case 2: // Remoção de aluno
					submenu_option = menuopcoes("Cancelar ou 0 -> Menu principal.\n1 -> Remover aluno.\n2 -> Remover professor.\n3 -> Remover aula.", 3);
					switch(submenu_option){
						case 1:
							if(alunos.size() == 0){
								JOptionPane.showMessageDialog(null, "Não há alunos cadastrados no sistema.", empresa, JOptionPane.ERROR_MESSAGE);
								break;
							}
							aluno_auxiliar = (aluno)JOptionPane.showInputDialog(null, "Selecione o aluno a ser removido:", empresa, JOptionPane.QUESTION_MESSAGE, logo_cliente, alunos.toArray(new aluno[alunos.size()]), "");
							if(aluno_auxiliar == null){
								break;
							}
							senha = insereSenha(false);
							if(senha == null){
								break;
							}
							else if(senha.equals(aluno_auxiliar.get_senha()) || senha.equals(senhaMestre)){
								// Banco de dados
								try{
									Class.forName(driver); // Conecta com o mysql
									link = DriverManager.getConnection(url,user,pass); // Estabelece conexão com o banco de dados
									stat = link.createStatement(); // Variável usada para atribuir os comandos realizados ao banco de dados
									stat.execute("delete from matriculas where id_aluno = '" + aluno_auxiliar.get_codigo() + "';");
									stat.execute("delete from alunos where codigo = '" + aluno_auxiliar.get_codigo() + "';");
									stat.execute("delete from banco where cpf_aluno = '" + aluno_auxiliar.get_CPF() + "';");
									stat.close();
									link.close();
								}
								catch(Exception e){
									//JOptionPane.showMessageDialog(null, e, empresa, JOptionPane.ERROR_MESSAGE);
									e.printStackTrace();
								}
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
							if(professor_auxiliar == null){
								break;
							}
							senha = insereSenha(false);
							if(senha == null){
								break;
							}
							else if(senha.equals(professor_auxiliar.get_senha()) || senha.equals(senhaMestre)){
								// Banco de dados
								try{
									Class.forName(driver); // Conecta com o mysql
									link = DriverManager.getConnection(url,user,pass); // Estabelece conexão com o banco de dados
									stat = link.createStatement(); // Variável usada para atribuir os comandos realizados ao banco de dados
									stat.execute("delete from acessos where id_professor = '" + professor_auxiliar.get_codigo() + "';");
									stat.execute("delete from professores where codigo = '" + professor_auxiliar.get_codigo() + "';");
									stat.close();
									link.close();
								}
								catch(Exception e){
									//JOptionPane.showMessageDialog(null, e, empresa, JOptionPane.ERROR_MESSAGE);
									e.printStackTrace();
								}
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
								JOptionPane.showMessageDialog(null, "Não há aulas cadastradas no sistema.", empresa, JOptionPane.ERROR_MESSAGE);
								break;
							}
							aula_auxiliar = (aula)JOptionPane.showInputDialog(null, "Selecione a aula a ser removida:", empresa, JOptionPane.QUESTION_MESSAGE, logo_cliente, aulas.toArray(new aula[aulas.size()]), "");
							if(aula_auxiliar == null){
								break;
							}
							try{ // Banco de dados
								Class.forName(driver); // Conecta com o mysql
								link = DriverManager.getConnection(url,user,pass); // Estabelece conexão com o banco de dados
								stat = link.createStatement(); // Variável usada para atribuir os comandos realizados ao banco de dados
								for(aluno a : alunos){ // Remove o vínculo dos alunos que possuiam a aula a ser removida
									if(a.del_aula(aula_auxiliar)){
										switch(a.get_aulas()){
											case 0: a.set_valor(0); break;
											case 1:	a.set_valor(valores[0]); break;
											case 2:	a.set_valor(valores[1]); break;
											case 3: a.set_valor(valores[2]); break;
											default: a.set_valor(valores[3]);
										}
										stat.execute("delete from matriculas where id_aula = '" + aula_auxiliar.get_codigo() + "';");
										stat.execute("update alunos set valor = " + a.get_valor() + " where codigo = '" + a.get_codigo() + "';");
									}
								}
								stat.execute("delete from aulas where codigo = '" + aula_auxiliar.get_codigo() + "';");
								stat.close();
								link.close();
							}
							catch(Exception e){
								//JOptionPane.showMessageDialog(null, e, empresa, JOptionPane.ERROR_MESSAGE);
								e.printStackTrace();
							}
							aulas.remove(aula_auxiliar);
							// Banco de dados
							JOptionPane.showMessageDialog(null, "Aula removida com sucesso.", empresa, JOptionPane.INFORMATION_MESSAGE);
							break;
					}
					break;
// ****************************************************************************************************
				case 3: // Vínculos
					if(alunos.size() == 0){
						JOptionPane.showMessageDialog(null, "Não há alunos cadastrados no sistema.", empresa, JOptionPane.ERROR_MESSAGE);
					}
					else if(aulas.size() == 0){
						JOptionPane.showMessageDialog(null, "Não há aulas cadastradas no sistema.", empresa, JOptionPane.ERROR_MESSAGE);
					}
					else{
						submenu_option = menuopcoes("Cancelar ou 0 -> Menu principal.\n1 -> Vincular aula a um aluno.\n2 -> Desvincular aula de um aluno.", 2);
						switch(submenu_option){
							case 1:
								aula_auxiliar = (aula)JOptionPane.showInputDialog(null, "Selecione a aula a ser vinculada:", empresa, JOptionPane.QUESTION_MESSAGE, logo_cliente, aulas.toArray(new aula[aulas.size()]), "");
								if(aula_auxiliar == null){
									break;
								}
								do{
									aluno_auxiliar = (aluno)JOptionPane.showInputDialog(null, "Selecione o aluno a ser vinculado:", empresa, JOptionPane.QUESTION_MESSAGE, logo_cliente, alunos.toArray(new aluno[alunos.size()]), "");
									if(aluno_auxiliar == null){
										break;
									}
									posicao = alunos.indexOf(aluno_auxiliar);
									if(aluno_auxiliar.add_aula(aula_auxiliar)){
										switch(aluno_auxiliar.get_aulas()){
											case 0: aluno_auxiliar.set_valor(0); break;
											case 1:	aluno_auxiliar.set_valor(valores[0]); break;
											case 2:	aluno_auxiliar.set_valor(valores[1]); break;
											case 3: aluno_auxiliar.set_valor(valores[2]); break;
											default: aluno_auxiliar.set_valor(valores[3]);
										}
										try{ // Banco de dados
											Class.forName(driver); // Conecta com o mysql
											link = DriverManager.getConnection(url,user,pass); // Estabelece conexão com o banco de dados
											stat = link.createStatement(); // Variável usada para atribuir os comandos realizados ao banco de dados
											stat.execute("insert into matriculas values (default, '" + aluno_auxiliar.get_codigo() + "','" + aula_auxiliar.get_codigo() + "');");
											stat.execute("update alunos set valor = " + aluno_auxiliar.get_valor() + " where codigo = '" + aluno_auxiliar.get_codigo() + "';");
											stat.close();
											link.close();
										}
										catch(Exception e){
											//JOptionPane.showMessageDialog(null, e, empresa, JOptionPane.ERROR_MESSAGE);
											e.printStackTrace();
										}
										alunos.set(posicao,aluno_auxiliar);
										sub_inter_menuoption = JOptionPane.showConfirmDialog(null, "Aula vinculada ao aluno. Deseja vincular outro aluno a esta mesma aula?", empresa, JOptionPane.YES_NO_OPTION);
									}
									else{
										sub_inter_menuoption = JOptionPane.showConfirmDialog(null, "Aluno possui faixa discrepante com o nível da aula ou já matriculado na mesma. Deseja vincular outro aluno a esta mesma aula?", empresa, JOptionPane.YES_NO_OPTION);
									}
								}while(sub_inter_menuoption == 0);
								break;
							case 2:
								aula_auxiliar = (aula)JOptionPane.showInputDialog(null, "Selecione a aula a ser desvinculada:", empresa, JOptionPane.QUESTION_MESSAGE, logo_cliente, aulas.toArray(new aula[aulas.size()]), "");
								if(aula_auxiliar == null){
									break;
								}
								do{
									aluno_auxiliar = (aluno)JOptionPane.showInputDialog(null, "Selecione o aluno a ser desvinculado:", empresa, JOptionPane.QUESTION_MESSAGE, logo_cliente, alunos.toArray(new aluno[alunos.size()]), "");
									if(aluno_auxiliar == null){
										break;
									}
									posicao = alunos.indexOf(aluno_auxiliar);
									if(aluno_auxiliar.del_aula(aula_auxiliar)){
										switch(aluno_auxiliar.get_aulas()){
											case 0: aluno_auxiliar.set_valor(0); break;
											case 1:	aluno_auxiliar.set_valor(valores[0]); break;
											case 2:	aluno_auxiliar.set_valor(valores[1]); break;
											case 3: aluno_auxiliar.set_valor(valores[2]); break;
											default: aluno_auxiliar.set_valor(valores[3]);
										}
										try{ // Banco de dados
											Class.forName(driver); // Conecta com o mysql
											link = DriverManager.getConnection(url,user,pass); // Estabelece conexão com o banco de dados
											stat = link.createStatement(); // Variável usada para atribuir os comandos realizados ao banco de dados
											stat.execute("delete from matriculas where id_aluno = '" + aluno_auxiliar.get_codigo() + "' and id_aula = '" + aula_auxiliar.get_codigo()+ "';");
											stat.execute("update alunos set valor = " + aluno_auxiliar.get_valor() + "where codigo = '" + aluno_auxiliar.get_codigo() + "';");
											stat.close();
											link.close();
										}
										catch(Exception e){
											//JOptionPane.showMessageDialog(null, e, empresa, JOptionPane.ERROR_MESSAGE);
											e.printStackTrace();
										}
										alunos.set(posicao,aluno_auxiliar);
										sub_inter_menuoption = JOptionPane.showConfirmDialog(null, "Aula desvinculada do aluno. Deseja desvincular outro aluno desta mesma aula?", empresa, JOptionPane.YES_NO_OPTION);
									}
									else{
										sub_inter_menuoption = JOptionPane.showConfirmDialog(null, "Aluno não era matriculado nesta aula. Deseja desvincular outro aluno desta mesma aula?", empresa, JOptionPane.YES_NO_OPTION);
									}
								}while(sub_inter_menuoption == 0);
								break;
						}
					}
					break;
// ****************************************************************************************************
				case 4: // Consulta
					submenu_option = menuopcoes("Cancelar ou 0 -> Menu principal.\n1 -> Consulta Aluno.\n2 -> Consulta Professor.\n3 -> Consulta Aula.", 3);
					switch(submenu_option){
						case 1:
							if(alunos.size() == 0){
								JOptionPane.showMessageDialog(null, "Não há aulas cadastradas no sistema.", empresa, JOptionPane.ERROR_MESSAGE);
								invalido = true;
								break;
							}
							aluno_auxiliar = (aluno)JOptionPane.showInputDialog(null, "Selecione o aluno a ser consultado (cancelar = exibir todos):", empresa, JOptionPane.QUESTION_MESSAGE, logo_cliente, alunos.toArray(new aluno[alunos.size()]), "");
							senha = insereSenha(false);
							if(senha == null){
								break;
							}
							if(aluno_auxiliar == null){
								if(senha.equals(senhaMestre)){
									for(aluno al : alunos){
										if(JOptionPane.showConfirmDialog(null, al.exibe() + "\nContinuar?", empresa, JOptionPane.OK_CANCEL_OPTION,JOptionPane.INFORMATION_MESSAGE,logo_cliente) != 0){
											break;
										}
									}
								}
								else{
									JOptionPane.showMessageDialog(null, "Senha incorreta!",empresa,JOptionPane.ERROR_MESSAGE);
								}
							}
							else{
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
							professor_auxiliar = (professor)JOptionPane.showInputDialog(null, "Selecione o professor a ser consultado (cancelar = exibe todos):", empresa, JOptionPane.QUESTION_MESSAGE, logo_cliente, professores.toArray(new professor[professores.size()]), "");
							posicao = professores.indexOf(professor_auxiliar);
							senha = insereSenha(false);
							if(senha == null){
								break;
							}
							if(professor_auxiliar == null){
								if(senha.equals(senhaMestre)){
									for(professor pr : professores){
										if(JOptionPane.showConfirmDialog(null, pr.exibe() + "\n\nContinuar?", empresa, JOptionPane.OK_CANCEL_OPTION,JOptionPane.INFORMATION_MESSAGE,logo_cliente) != 0){
											break;
										}
									}
								}
								else{
									JOptionPane.showMessageDialog(null, "Senha incorreta.", empresa, JOptionPane.ERROR_MESSAGE);
								}
							}
							else{
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
							aula_auxiliar = (aula)JOptionPane.showInputDialog(null, "Selecione o aula a ser consultada (cancelar = exibe todos):", empresa, JOptionPane.QUESTION_MESSAGE, logo_cliente, aulas.toArray(new aula[aulas.size()]), "");
							if(aula_auxiliar == null){
								for(aula au : aulas){
									if(JOptionPane.showConfirmDialog(null, au.exibe() + "\nContinuar?", empresa, JOptionPane.OK_CANCEL_OPTION,JOptionPane.INFORMATION_MESSAGE,logo_cliente) != 0){
										break;
									}
								}
							}
							else{
								JOptionPane.showMessageDialog(null, aula_auxiliar.exibe(), empresa, JOptionPane.INFORMATION_MESSAGE,logo_cliente);
							}
							break;
					}
					break;
// ****************************************************************************************************
				case 5: // Atualizar dados
					submenu_option = menuopcoes("Cancelar ou 0 -> Menu principal.\n1 -> Atualizar dados de um aluno.\n2 -> Atualizar dados de um professor.\n3 -> Atualizar dados de uma aula.", 3);
					error = false;
					if(submenu_option == 0){
						break;
					}
					try{
						Class.forName(driver); // Conecta com o mysql
						link = DriverManager.getConnection(url,user,pass); // Estabelece conexão com o banco de dados
						stat = link.createStatement(); // Variável usada para atribuir os comandos realizados ao banco de dados
						switch(submenu_option){
							case 1:
								if(alunos.size() == 0){
									JOptionPane.showMessageDialog(null, "Não há alunos cadastrados no sistema.", empresa, JOptionPane.ERROR_MESSAGE);
									error = true;
									break;
								}
								aluno_auxiliar = (aluno)JOptionPane.showInputDialog(null, "Selecione o aluno a ser atualizado:", empresa, JOptionPane.QUESTION_MESSAGE, logo_cliente, alunos.toArray(new aluno[alunos.size()]), "");
								if(aluno_auxiliar == null){
									error = true;
									break;
								}
								posicao = alunos.indexOf(aluno_auxiliar);
								senha = insereSenha(false);
								if(senha == null){
									error = true;
									break;
								}
								else if(senha.equals(aluno_auxiliar.get_senha()) || senha.equals(senhaMestre)){
									sub_inter_menuoption = menuopcoes("1 -> Nome.\n2 -> CPF.\n3 -> Email.\n4 -> Telefone.\n5 -> Data de Nascimento. \n6 -> Auxiliar.\n7 -> Faixa.\n8 -> Senha.", 8);
									switch(sub_inter_menuoption){
										case 0:
											error = true;
											break;
										case 1:
											auxiliar = insereNome();
											if(auxiliar == null){
												error = true;
												break;
											}
											// Banco de dados
											aluno_auxiliar.set_nome(auxiliar);
											stat.execute("update alunos set nome = '" + aluno_auxiliar.get_nome() +"' where codigo = '" + aluno_auxiliar.get_codigo() + "';");
											break;
										case 2:
											do{
												invalido = false;
												CPF = insereCPF();
												if(CPF == null){
													break;
												}
												for(aluno a : alunos){
													if(CPF.equals(a.get_CPF())){
														invalido = true;
														JOptionPane.showMessageDialog(null, "CPF já existente no sistema.", empresa, JOptionPane.ERROR_MESSAGE);
														break;
													}
												}
											}while(invalido);
											if(CPF == null){
												error = true;
												break;
											}
											// Banco de dados
											aluno_auxiliar.set_CPF(CPF);
											stat.execute("update alunos set cpf = '" + aluno_auxiliar.get_CPF() + "' where codigo = '" + aluno_auxiliar.get_codigo() + "';");
											break;
										case 3:
											auxiliar = insereEmail();
											if(auxiliar == null){
												error = true;
												break;
											}
											// Banco de dados
											aluno_auxiliar.set_email(auxiliar);
											stat.execute("update alunos set email = '" + aluno_auxiliar.get_email() + "' where codigo = '" + aluno_auxiliar.get_codigo() + "';");
											break;
										case 4:
											auxiliar = insereTelefone();
											if(auxiliar == null){
												error = true;
												break;
											}
											// Banco de dados
											aluno_auxiliar.set_telefone(auxiliar);
											stat.execute("update alunos set telefone = '" + aluno_auxiliar.get_telefone() + "' where codigo = '" + aluno_auxiliar.get_codigo() + "';");
											break;
										case 5:
											data = insereData();
											if(data == null){
												error = true;
												break;
											}
											// Banco de dados
											aluno_auxiliar.set_nascimento(data);
											auxiliar = data.toString(); // dd/mm/yyyy -> yyyy-mm-dd
											stat.execute("update alunos set nascimento = '" + auxiliar.substring(6) + "-" + auxiliar.substring(3, 5) + "-" + auxiliar.substring(0, 2) + "' where codigo = '" + aluno_auxiliar.get_codigo() + "';");
											break;
										case 6:
											// Banco de dados
											armas = insereAuxiliar();
											aluno_auxiliar.set_auxiliar(armas);
											stat.execute("update alunos set auxiliar = " + aluno_auxiliar.get_auxiliar() + " where codigo = '" + aluno_auxiliar.get_codigo() + "';");
											break;
										case 7:
											faixa = insereFaixa();
											if(faixa == -1){
												error = true;
												break;
											}
											// Banco de dados
											aluno_auxiliar.set_faixa(faixa);
											for(aula au : aulas){
												if(aluno_auxiliar.del_aula(au)){
													if(!aluno_auxiliar.add_aula(au)){
														stat.execute("delete from matriculas where id_aula = '" + au.get_codigo() + "' and id_aluno = '" + aluno_auxiliar.get_codigo() + "';");
													}
												}
											}
											switch(aluno_auxiliar.get_aulas()){
												case 0: aluno_auxiliar.set_valor(0); break;
												case 1:	aluno_auxiliar.set_valor(valores[0]); break;
												case 2:	aluno_auxiliar.set_valor(valores[1]); break;
												case 3: aluno_auxiliar.set_valor(valores[2]); break;
												default: aluno_auxiliar.set_valor(valores[3]);
											}
											stat.execute("update alunos set faixa = " + aluno_auxiliar.get_faixaN() + ", valor = " + aluno_auxiliar.get_valor() + " where codigo = '" + aluno_auxiliar.get_codigo() + "';");
											break;
										case 8:
											do{
												vincular = insereSenha(false);
												if(vincular == null){
													break;
												}
												confirmaSenha = insereSenha(true);
												if(confirmaSenha == null){
													break;
												}
												if(!vincular.equals(confirmaSenha)){
													JOptionPane.showMessageDialog(null, "Senha distintas! Tente novamente.", empresa, JOptionPane.ERROR_MESSAGE);
												}
											}while(!vincular.equals(confirmaSenha));
											if(vincular == null || confirmaSenha == null){
												error = true;
												break;
											}
											// Banco de dados
											if(aluno_auxiliar.set_senha(senha,vincular,senhaMestre)){
												stat.execute("update alunos set senha = '" + vincular + "' where codigo = '" + aluno_auxiliar.get_codigo() + "';");
											}
											else{
												error = true;
											}
											break;
									}
								}
								else{
									JOptionPane.showMessageDialog(null, "Senha incorreta.", empresa, JOptionPane.ERROR_MESSAGE);
									error = true;
								}
								if(!error){
									alunos.set(posicao,aluno_auxiliar);
									JOptionPane.showMessageDialog(null, "Aluno atualizado com sucesso.", empresa, JOptionPane.INFORMATION_MESSAGE);
								}
								break;
	// ****************************************************************************************************
							case 2:
								if(professores.size() == 0){
									JOptionPane.showMessageDialog(null, "Não há professores cadastrados no sistema.", empresa, JOptionPane.ERROR_MESSAGE);
									error = true;
									break;
								}
								professor_auxiliar = (professor)JOptionPane.showInputDialog(null, "Selecione o professor a ser atualizado:", empresa, JOptionPane.QUESTION_MESSAGE, logo_cliente, professores.toArray(new professor[professores.size()]), "");
								if(professor_auxiliar == null){
									error = true;
									break;
								}
								posicao = professores.indexOf(professor_auxiliar);
								senha = insereSenha(false);
								if(senha == null){
									error = true;
									break;
								}
								else if(senha.equals(professor_auxiliar.get_senha()) || senha.equals(senhaMestre)){
									sub_inter_menuoption = menuopcoes("1 -> Nome.\n2 -> CPF.\n3 -> Email.\n4 -> Telefone.\n5 -> Data de Nascimento. \n6 -> Senha.", 6);
									switch(sub_inter_menuoption){
										case 0:
											error = true;
											break;
										case 1:
											auxiliar = insereNome();
											if(auxiliar == null){
												error = true;
												break;
											}
											// Banco de dados
											professor_auxiliar.set_nome(auxiliar);
											stat.execute("update professores set nome = '" + auxiliar + "' where codigo = '" + professor_auxiliar.get_codigo() + "';");
											break;
										case 2:
											do{
												invalido = false;
												CPF = insereCPF();
												if(CPF == null){
													break;
												}
												for(professor p : professores){
													if(CPF.equals(p.get_CPF())){
														invalido = true;
														JOptionPane.showMessageDialog(null, "CPF já existente no sistema, tente novamente.", empresa, JOptionPane.ERROR_MESSAGE);
														break;
													}
												}
											}while(invalido);
											if(CPF == null){
												error = true;
												break;
											}
											// Banco de dados
											professor_auxiliar.set_CPF(CPF);
											stat.execute("update professores set cpf = '" + CPF + "' where codigo = '" + professor_auxiliar.get_codigo()+ "';");
											break;
										case 3:
											auxiliar = insereEmail();
											if(auxiliar == null){
												error = true;
												break;
											}
											// Banco de dados
											professor_auxiliar.set_email(auxiliar);
											stat.execute("update professores set email = '" + auxiliar + "' where codigo = '" + professor_auxiliar.get_codigo()+ "';");
											break;
										case 4:
											auxiliar = insereTelefone();
											if(auxiliar == null){
												error = true;
												break;
											}
											// Banco de dados
											professor_auxiliar.set_telefone(auxiliar);
											stat.execute("update professores set telefone = '" + auxiliar + "' where codigo = '" + professor_auxiliar.get_codigo()+ "';");
											break;
										case 5:
											data = insereData();
											if(data == null){
												error = true;
												break;
											}
											// Banco de dados
											professor_auxiliar.set_nascimento(data);
											auxiliar = data.toString(); // dd/mm/yyyy -> yyyy-mm-dd
											stat.execute("update professores set nascimento = '" + auxiliar.substring(6) + "-" + auxiliar.substring(3, 5) + "-" + auxiliar.substring(0, 2) + "' where codigo = '" + professor_auxiliar.get_codigo() + "';");
											break;
										case 6:
											do{
												vincular = insereSenha(false);
												if(vincular == null){
													break;
												}
												confirmaSenha = insereSenha(true);
												if(confirmaSenha == null){
													break;
												}
												if(!vincular.equals(confirmaSenha)){
													JOptionPane.showMessageDialog(null, "Senhas distintas! Tente novamente.", empresa, JOptionPane.ERROR_MESSAGE);
												}
											}while(!vincular.equals(confirmaSenha));
											if(vincular == null || confirmaSenha == null){
												error = true;
												break;
											}
											// Banco de dados
											if(!professor_auxiliar.set_senha(senha,vincular,senhaMestre)){
												error = true;
											}
											else{
												stat.execute("update professores set senha = '" + vincular + "' where codigo = '" + professor_auxiliar.get_codigo() + "';");
											}
											break;
									}
								}
								else{
									JOptionPane.showMessageDialog(null, "Senha incorreta.", empresa, JOptionPane.ERROR_MESSAGE);
									error = true;
								}
								if(!error){
									professores.set(posicao,professor_auxiliar);
									JOptionPane.showMessageDialog(null, "Professor atualizado com sucesso.", empresa, JOptionPane.INFORMATION_MESSAGE);
								}
								break;
	// ****************************************************************************************************
							case 3:
								if(aulas.size() == 0){
									JOptionPane.showMessageDialog(null, "Não há aulas cadastradas no sistema.", empresa, JOptionPane.ERROR_MESSAGE);
									break;
								}

								aula_auxiliar = (aula)JOptionPane.showInputDialog(null, "Selecione a aula a ser atualizada:", empresa, JOptionPane.QUESTION_MESSAGE, logo_cliente, aulas.toArray(new aula[aulas.size()]), "");
								if(aula_auxiliar == null){
									break;
								}
								posicao = aulas.indexOf(aula_auxiliar);
								sub_inter_menuoption = menuopcoes("1 -> Armas.\n2 -> Faixa.\n3 -> Horário.\n4 -> Descrição", 4);
								switch(sub_inter_menuoption){
									case 0:
										error = true;
										break;
									case 1:
										// Banco de dados
										armas = insereArmas();
										aula_auxiliar.set_arma(armas);
										stat.execute("update aulas set arma = " + armas + " where codigo = '" + aula_auxiliar.get_codigo() + "';");
										break;
									case 2:
										faixa = insereFaixa();
										if(faixa == -1){
											error = true;
											break;
										}
										// Banco de dados
										aula_auxiliar.set_faixa(faixa);
										stat.execute("update aulas set faixa = " + aula_auxiliar.get_faixa() + " where codigo = '" + aula_auxiliar.get_codigo()+ "';");
										for(aluno a : alunos){
											if(a.del_aula(aula_auxiliar)){
												if(!a.add_aula(aula_auxiliar)){
													stat.execute("delete from matriculas where id_aula = '" + aula_auxiliar.get_codigo() + "' and id_aluno = '" + a.get_codigo() + "';");
												}
											}
											switch(a.get_aulas()){
												case 0: a.set_valor(0); break;
												case 1:	a.set_valor(valores[0]); break;
												case 2:	a.set_valor(valores[1]); break;
												case 3: a.set_valor(valores[2]); break;
												default: a.set_valor(valores[3]);
											}
											stat.execute("update alunos set valor = " + a.get_valor() + " where codigo = '" + aula_auxiliar.get_codigo()+ "';");
										}
										break;
									case 3:
										horario = insereHorario();
										if(horario == null){
											error = true;
											break;
										}
										// Banco de dados
										aula_auxiliar.set_horario(horario);
										stat.execute("update aulas set horario = '" + aula_auxiliar.get_horario()+ "' where codigo = '" + aula_auxiliar.get_codigo()+ "';");
										break;
									case 4:
										descricao = insereDescricao();
										if(descricao == null){
											error = true;
											break;
										}
										// Banco de dados
										aula_auxiliar.set_descricao(descricao);
										stat.execute("update aulas set descricao = '" + descricao + "' where codigo = '" + aula_auxiliar.get_codigo()+ "';");
										break;
								}
								if(!error){
									aulas.set(posicao,aula_auxiliar);
									JOptionPane.showMessageDialog(null, "Aula atualizada com sucesso.", empresa, JOptionPane.INFORMATION_MESSAGE);
								}
								break;
						}
						if(stat != null){
							stat.close();
						}
						if(link != null){
							link.close();
						}
					}
					catch(Exception e){
						//JOptionPane.showMessageDialog(null, e, empresa, JOptionPane.ERROR_MESSAGE);
						e.printStackTrace();
					}
					break;
// ****************************************************************************************************
				case 6: // Entrada
					if(alunos.size() == 0 && professores.size() == 0){
						JOptionPane.showMessageDialog(null, "Cadastros inexistentes.", empresa, JOptionPane.ERROR_MESSAGE);
					}
					else{
						aux = JOptionPane.showConfirmDialog(null, "Deseja realizar a entrada via cartão?", empresa, JOptionPane.YES_NO_CANCEL_OPTION);
						switch(aux){
							case 0: // Yes
								codigo = connect(portlist);
								//System.out.printf((String)codigo);
								break;
							case 1: // No
								codigo = (String)JOptionPane.showInputDialog(null, "Insira o código do aluno/professor que deseja entrar:", empresa, JOptionPane.QUESTION_MESSAGE, logo_cliente, null, "");
								if(codigo == null){
									break;
								}
								codigo = codigo.toUpperCase();
								break;
						}
						if(aux == 2){
							break;
						}
						if(codigo == null){
							break;
						}
						encontrou = false;
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
									agora = LocalDateTime.now();
									// Formata a data e hora
									formatarel = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
									auxiliar = formatarel.format(agora);
									pr.registra_acesso(auxiliar);
									formatabanco = DateTimeFormatter.ofPattern("dd-MM-yyyyHH-mm-ss");
									auxiliar = formatabanco.format(agora);
									// Banco de dados
									try{ /*yyyy-MM-dd hh-mm-ss*/
										Class.forName(driver); // Conecta com o mysql
										link = DriverManager.getConnection(url,user,pass); // Estabelece conexão com o banco de dados
										stat = link.createStatement(); // Variável usada para atribuir os comandos realizados ao banco de dados
										stat.execute("insert into acessos values (default,'" + pr.get_codigo() + "','" + auxiliar.substring(6,10) + auxiliar.substring(2, 6) + auxiliar.substring(0, 2) + " " + auxiliar.substring(10) +"');");
										stat.close();
										link.close();
									}
									catch(Exception e){
										//JOptionPane.showMessageDialog(null, e, empresa, JOptionPane.ERROR_MESSAGE);
										e.printStackTrace();
									}
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
				case 7: // Sincronização com o banco
					try{
						Class.forName(driver); // Conecta com o mysql
						link = DriverManager.getConnection(url,user,pass); // Estabelece conexão com o banco de dados
						stat = link.createStatement(); // Variável usada para atribuir os comandos realizados ao banco de dados
						for(aluno a : alunos){
							result = stat.executeQuery("select pago from banco where cpf_aluno = '" + a.get_CPF() + "';");
							result.next();
							if(result.getBoolean(1)){
								a.pago();
							}
							else{
								a.atraso(1);
							}
							stat.execute("update alunos set atraso = " + a.get_atraso_conta() + ", mes_pago = " + result.getBoolean(1) + " where codigo = '" + a.get_codigo() + "';");
						}
						result.close();
						stat.close();
						link.close();
					}
					catch(Exception e){
						//JOptionPane.showMessageDialog(null, e, empresa, JOptionPane.ERROR_MESSAGE);
						e.printStackTrace();
					}
					break;
// ****************************************************************************************************
				case 8: // Envio do histórico de acesso para os professores por email
					if(professores.size() == 0){
						JOptionPane.showMessageDialog(null, "Não há professores cadastrados.", empresa, JOptionPane.ERROR_MESSAGE);
					}
					else{
						senha = insereSenha(false);
						if(senha == null){
							break;
						}
						else if(senha.equals(senhaMestre)){
							// Configuração para configurar o servidor de email
							configuracao = new Properties();
							configuracao.put("mail.smtp.auth", "true");
							configuracao.put("mail.smtp.starttls.enable", "true");
							configuracao.put("mail.smtp.host", servidor);
							configuracao.put("mail.smtp.port", "587");
							// Inicializa a sessão
							sessao = Session.getInstance(configuracao, new javax.mail.Authenticator(){
								protected PasswordAuthentication getPasswordAuthentication(){
									return new PasswordAuthentication(username, password);
								}
							});
							try{ // Banco de dados
								Class.forName(driver); // Conecta com o mysql
								link = DriverManager.getConnection(url,user,pass); // Estabelece conexão com o banco de dados
								stat = link.createStatement(); // Variável usada para atribuir os comandos realizados ao banco de dados
								for(professor pf : professores){
									pf.salvar_registro();
									error = false;
									try{
										mensagem = new MimeMessage(sessao); // Cria o objeto mensagem
										arquivo = pf.get_codigo().concat(".txt"); // Nome do arquivo
										caminho = new FileDataSource(arquivo); // Localização do arquivo no diretório
										corpo = new MimeBodyPart(); // Cria a mensagem do email
										anexo = new MimeMultipart(); // Cria outra parte da mensagem (para o anexo e juntar com o texto)
										mensagem.setFrom(new InternetAddress(username)); // Coloca o remetente do email
										mensagem.addRecipient(Message.RecipientType.TO, new InternetAddress(pf.get_email())); // Adiciona o email do professor a ser enviado
										mensagem.setSubject("Ponto eletrônico - Relatório mensal"); // Assunto do email
										corpo.setContent("<p>Ol&aacute; "+pf.EnviarEmail()+",</p><br /><p>Obrigado por fazer parte de nossa academia, segue em anexo o relat&oacute;rio mensal de acesso &agrave; academia. Tenha um bom dia.</p><br /><p>Atenciosamente,</p><img src=\"https://github.com/RafaelBarbon/Academia_Cobra_Kai/blob/main/Cobra_Kai.jpg?raw=true\" height=\"200px\" width=\"200px\" border=\"1px\" alt=\"Academia Cobra Kai\" /><br /><br /><br /><h5>Este &eacute; um email autom&aacute;tico, por favor n&atilde;o responda. Desenvolvido por </h5><img src=\"https://github.com/RafaelBarbon/Academia_Cobra_Kai/blob/main/logo.jpg?raw=true\" height=\"100px\" width=\"130px\" border=\"1px\" alt=\"RAH - Desenvolvimento de sistemas\" />","text/html");
										anexo.addBodyPart(corpo); // Adiciona o texto
										corpo = new MimeBodyPart(); // Adiciona o campo para anexo
										corpo.setDataHandler(new DataHandler(caminho)); // Coleta o arquivo para anexar
										corpo.setFileName(arquivo); // Coloca o nome do arquivo
										anexo.addBodyPart(corpo); // Junta o arquivo com o objeto do anexo
										mensagem.setContent(anexo); // Junta o anexo com a mensagem
										Transport.send(mensagem); // Envia o email
									}
									catch(MessagingException e){
										error = true;
										JOptionPane.showMessageDialog(null, "Não foi possível enviar o email para o professor" + pf.get_codigo() + ".", "Academia Cobra Kai", JOptionPane.ERROR_MESSAGE);
										e.printStackTrace();
									}
									if(!error){
										pf.renova_mes();
										stat.execute("delete from acessos where id_professor = '" + pf.get_codigo() + "';");
									}
								}
								stat.close();
								link.close();
							}
							catch(Exception e){
								//JOptionPane.showMessageDialog(null, e, empresa, JOptionPane.ERROR_MESSAGE);
								e.printStackTrace();
							}
							JOptionPane.showMessageDialog(null, "Comprovantes enviados por email a todos os professores.", empresa, JOptionPane.INFORMATION_MESSAGE);
						}
						else{
							JOptionPane.showMessageDialog(null, "Senha incorreta.", empresa, JOptionPane.ERROR_MESSAGE);
						}
					}
					break;
				case 9: // Configurações
					submenu_option = menuopcoes("Cancelar ou 0 -> Menu principal.\n1 -> Atualizar email e senha.\n2 -> Atualizar preço das aulas.\n3 -> Atualizar senha mestre.", 3);
					switch(submenu_option){
						case 1: // Email e senha
							email = insereEmail();
							if(email == null){
								break;
							}
							senha = insereSenha(false);
							if(senha == null){
								break;
							}
							// Banco de dados
							try{
								Class.forName(driver); // Conecta com o mysql
								link = DriverManager.getConnection(url,user,pass); // Estabelece conexão com o banco de dados
								stat = link.createStatement(); // Variável usada para atribuir os comandos realizados ao banco de dados
								stat.execute("update info set email = '" + email + "', senha_email = '" + senha + "' where id = '1';");
								stat.close();
								link.close();
							}
							catch(Exception e){
								//JOptionPane.showMessageDialog(null, e, empresa, JOptionPane.ERROR_MESSAGE);
								e.printStackTrace();
							}
							JOptionPane.showMessageDialog(null, "Dados de email coletados, reinicie o programa para atualizar.", empresa, JOptionPane.INFORMATION_MESSAGE);
							break;
// ****************************************************************************************************
						case 2: // Preços
							JOptionPane.showMessageDialog(null, "Preço das aulas atualizados.", empresa, JOptionPane.INFORMATION_MESSAGE);
							for(i = 0; i < 4; i++){
								do{
									error = false;
									try{
										auxiliar = (String)JOptionPane.showInputDialog(null, "Insira o novo valor para " + i+1 +"aula(s) na semana.", empresa, JOptionPane.QUESTION_MESSAGE, logo_cliente, null, "");;
										if(auxiliar == null){
											break;
										}
										valoresaux[i] = Float.parseFloat(auxiliar);
									}
									catch(NumberFormatException e){
										JOptionPane.showMessageDialog(null, "Formato errado, tente novamente.", empresa, JOptionPane.ERROR_MESSAGE);
										error = true;
									}
								}while(error);
								if(auxiliar == null){
									break;
								}
							}
							if(auxiliar == null){
								break;
							}
							else{
								for(i = 0;i < 4; i++){
									valores[i] = valoresaux[i];
								}
								try{
									Class.forName(driver); // Conecta com o mysql
									link = DriverManager.getConnection(url,user,pass); // Estabelece conexão com o banco de dados
									stat = link.createStatement(); // Variável usada para atribuir os comandos realizados ao banco de dados
									for(aluno a : alunos){ // Atualiza os alunos
										switch(a.get_aulas()){
											case 0: a.set_valor(0); break;
											case 1:	a.set_valor(valores[0]); break;
											case 2:	a.set_valor(valores[1]); break;
											case 3: a.set_valor(valores[2]); break;
											default: a.set_valor(valores[3]);
										}
										// Banco de dados
										stat.execute("update alunos set valor = " + a.get_valor() + " where codigo = '" + a.get_codigo() + "';");
									}
									stat.close();
									link.close();
								}
								catch(Exception e){
									//JOptionPane.showMessageDialog(null, e, empresa, JOptionPane.ERROR_MESSAGE);
									e.printStackTrace();
								}
								// Banco de dados
								try{
									Class.forName(driver); // Conecta com o mysql
									link = DriverManager.getConnection(url,user,pass); // Estabelece conexão com o banco de dados
									stat = link.createStatement(); // Variável usada para atribuir os comandos realizados ao banco de dados
									stat.execute("update info set v1aula = " + valores[0] + ", v2aula = " + valores[1] + ", v3aula = " + valores[2] + ", v4aula = " + valores[3] + " where id = '1';");
									stat.close();
									link.close();
								}
								catch(Exception e){
									//JOptionPane.showMessageDialog(null, e, empresa, JOptionPane.ERROR_MESSAGE);
									e.printStackTrace();
								}
								JOptionPane.showMessageDialog(null, "Preços atualizados com sucesso.", empresa, JOptionPane.INFORMATION_MESSAGE);
							}
							break;
// ****************************************************************************************************
						case 3: // SenhaMestre
							auxiliar = insereSenha(false);
							if(auxiliar == null){
								break;
							}
							if(auxiliar.equals(senhaMestre)){
								senha = insereSenha(false);
								if(senha == null){
									break;
								}
								confirmaSenha = insereSenha(true);
								if(confirmaSenha == null){
									break;
								}
								if(senha.equals(confirmaSenha)){
									// Banco de dados
									try{
										Class.forName(driver); // Conecta com o mysql
										link = DriverManager.getConnection(url,user,pass); // Estabelece conexão com o banco de dados
										stat = link.createStatement(); // Variável usada para atribuir os comandos realizados ao banco de dados
										stat.execute("update info set senha_mestre = '" + senha + "' where id = '1';");
										stat.close();
										link.close();
									}
									catch(Exception e){
										//JOptionPane.showMessageDialog(null, e, empresa, JOptionPane.ERROR_MESSAGE);
										e.printStackTrace();
									}
									JOptionPane.showMessageDialog(null, "Senha mestre coletada, reinicie o programa para atualizar.", empresa, JOptionPane.INFORMATION_MESSAGE);
								}
							}
							else{
								JOptionPane.showMessageDialog(null, "Senha incorreta.", empresa, JOptionPane.ERROR_MESSAGE);
							}
							break;
					}
					break;
				default:
					encerra();
			}
		}while(menu_option != 0);
	}

	// Método que imprime o menu
	public static int menuopcoes(String texto, int fim){
		int op = 0;
		boolean error = false;
		String auxiliar;
		do{
			try{
				error = false;
				auxiliar = (String)JOptionPane.showInputDialog(null,texto,empresa,JOptionPane.QUESTION_MESSAGE,logo_cliente,null,"");
				if(auxiliar == null){
					auxiliar = "0";
				}
				op = Integer.parseInt(auxiliar);
			}
			catch(NumberFormatException e){
				error = true;
				JOptionPane.showMessageDialog(null, "Insira um número inteiro dentro do intervalo do menu para prosseguir.", empresa, JOptionPane.ERROR_MESSAGE);
			}
		}while(error || op > fim || op < 0);
		return op;
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
			JOptionPane.showMessageDialog(null, "Não foi possivel limpar a tela do terminal.", empresa, JOptionPane.ERROR_MESSAGE);
		}
	}

	// Método que coleta o nome
	public static String insereNome(){
		String nome;
		do{
			nome = (String)JOptionPane.showInputDialog(null, "Nome(Máx 50 Caracteres):", empresa, JOptionPane.QUESTION_MESSAGE);
			if(nome == null){
				return null;
			}
		}while(nome.equals("") || nome.length() > 50);
		nome = nome.toUpperCase();
		return nome;
	}

	// Método que coleta a descrição de aula
	public static String insereDescricao(){
		String desc;
		do{
			desc = (String)JOptionPane.showInputDialog(null, "Descrição (Máx. 150 Caracteres):", empresa, JOptionPane.QUESTION_MESSAGE);
			if(desc == null){
				return null;
			}
		}while(desc.length() > 150);
		return desc;
	}

	// Método que coleta senha (ou confirmação de senha)
	public static String insereSenha(boolean confirma){
		String senha;
		do{
			if(confirma){
				senha = (String)JOptionPane.showInputDialog(null, "Confirme senha (Máx. 20 Caracteres):", empresa, JOptionPane.QUESTION_MESSAGE);
			}
			else{
				senha = (String)JOptionPane.showInputDialog(null, "Senha (Máx. 20 Caracteres):", empresa, JOptionPane.QUESTION_MESSAGE);
			}
			if(senha == null){
				return null;
			}
		}while(senha.length() > 20);
		return senha;
	}

	// Insere CPF
	public static String insereCPF(){
		boolean invalido = true;
		String CPF = "";
		do{ // Verifica conflito CPF
			CPF = (String)JOptionPane.showInputDialog(null, "CPF:", empresa, JOptionPane.QUESTION_MESSAGE);
			if(CPF == null){
				return null;
			}
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
			email = (String)JOptionPane.showInputDialog(null, "Email(Máx 50 Caracteres):", empresa, JOptionPane.QUESTION_MESSAGE);
			if(email == null){
				return null;
			}
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
		}while(invalido || email.length() > 50);
		return email;
	}

	// Formata o telefone (0000000000 -> 00 0000-0000 ou 0000000000 -> 00 00000-0000)
	public static String insereTelefone(){
		boolean invalido = true;
		String telefone;
		do{

			telefone = (String)JOptionPane.showInputDialog(null, "Telefone:", empresa, JOptionPane.QUESTION_MESSAGE);
			if(telefone == null){
				return null;
			}
			if((telefone.length() == 10 || telefone.length() == 11) && telefone.matches("[0-9]+")){ // Verifica se existe somente números e verifica o tamanho (caso de celular / telefone fixo)
				invalido = false;
			}
			else{
				JOptionPane.showMessageDialog(null, "Telefone inválido, tente novamente.", empresa, JOptionPane.ERROR_MESSAGE);
			}
		}while(invalido);
		if(telefone.length() == 10){
			return '(' + telefone.substring(0,2) + ") " + telefone.substring(2,6) + '-' + telefone.substring(6);
		}
		return '(' + telefone.substring(0,2) + ") " + telefone.substring(2,7) + '-' + telefone.substring(7);
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
				error = false;
				aux = (String)JOptionPane.showInputDialog(null, "Nascimento (dd/mm/yyyy):", empresa, JOptionPane.QUESTION_MESSAGE);
				if(aux == null){
					return null;
				}
				if(aux.matches("([0-9]{2})/([0-9]{2})/([0-9]{4})")){
					dia = Integer.parseInt(aux.substring(0,2));
					mes = Integer.parseInt(aux.substring(3,5));
					ano = Integer.parseInt(aux.substring(6));
				}
				else{
					error = true;
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

	// Método que seleciona se o aluno é auxiliar
	public static boolean insereAuxiliar(){
		int aux = 1;
		aux = JOptionPane.showConfirmDialog(null, "Aluno auxiliar?", empresa, JOptionPane.YES_NO_OPTION);
		if(aux == 0){
			return true;
		}
		return false;
	}

	// Método que coleta a cor da faixa
	public static int insereFaixa(){
		String[] faixas = {"0 -> Branca", "1 -> Amarela", "2 -> Laranja", "3 -> Verde", "4 -> Azul", "5 -> Roxa", "6 -> Vermelha", "7 -> Marrom", "8 -> Marrom ponta preta", "9 -> Preta"};
		String aux = "";
		aux = (String)JOptionPane.showInputDialog(null, "Selecione a faixa da aula:", empresa, JOptionPane.QUESTION_MESSAGE, logo_cliente, faixas, "");
		if(aux == null){
			return -1;
		}
		switch(aux.charAt(0)){
			case '0': return 0;
			case '1': return 1;
			case '2': return 2;
			case '3': return 3;
			case '4': return 4;
			case '5': return 5;
			case '6': return 6;
			case '7': return 7;
			case '8': return 8;
			case '9': return 9;
			default: return -1;
		}
	}

	// Método que seleciona se a aula possui armas
	public static boolean insereArmas(){
		int armas = 1;
		armas = JOptionPane.showConfirmDialog(null, "Esta aula utiliza armas?", empresa, JOptionPane.YES_NO_OPTION);
		if(armas == 0){
			return true;
		}
		return false;
	}

	// Método que coleta o dia e horário da semana da aula
	public static String insereHorario(){
		int hora = 1;
		String[] dias = {"1 -> Segunda-feira","2 -> Terça-feira","3 -> Quarta-feira","4 -> Quinta-feira","5 -> Sexta-feira","6 -> Sábado"}, horariosab = {"1 -> 7:00", "2 -> 8:00", "3 -> 9:00", "4 -> 10:00", "5 -> 11:00", "6 -> 12:00", "7 -> 13:00", "8 -> 14:00", "9 -> 15:00", "10 -> 16:00", "11 -> 17:00"}, horariosemana = {"1 -> 7:00", "2 -> 8:00", "3 -> 9:00", "4 -> 10:00", "5 -> 11:00", "6 -> 12:00", "7 -> 13:00", "8 -> 14:00", "9 -> 15:00", "10 -> 16:00", "11 -> 17:00", "12 -> 18:00", "13 -> 19:00", "14 -> 20:00", "15 -> 21:00"};
		String aux = "", aux2 = "";
		aux = (String)JOptionPane.showInputDialog(null, "Selecione o dia da aula:", empresa, JOptionPane.QUESTION_MESSAGE, logo_cliente, dias, "");
		if(aux == null){
			return null;
		}
		if(aux.charAt(7) == 'b'){
			aux2 = (String)JOptionPane.showInputDialog(null, "Selecione o horário da aula:", empresa, JOptionPane.QUESTION_MESSAGE, logo_cliente, horariosab, "");
		}
		else{
			aux2 = (String)JOptionPane.showInputDialog(null, "Selecione o horário da aula:", empresa, JOptionPane.QUESTION_MESSAGE, logo_cliente, horariosemana, "");
		}
		if(aux2 == null){
			return null;
		}
		switch(aux2.charAt(0)){
			case '1':
				switch(aux2.charAt(1)){
					case ' ': hora = 1;	break;
					case '0': hora = 10; break;
					case '1': hora = 11; break;
					case '2': hora = 12; break;
					case '3': hora = 13; break;
					case '4': hora = 14; break;
					case '5': hora = 15; break;
					case '6': hora = 16; break;
					case '7': hora = 17; break;
					case '8': hora = 18; break;
					case '9': hora = 19; break;
				}
				break;
			case '2':
				switch(aux2.charAt(1)){
					case ' ': hora = 2;	break;
					case '0': hora = 20; break;
					case '1': hora = 21; break;
				}
				break;
			case '3': hora = 3;	break;
			case '4': hora = 4; break;
			case '5': hora = 5; break;
			case '6': hora = 6; break;
			case '7': hora = 7; break;
			case '8': hora = 8; break;
			case '9': hora = 9; break;
		}
		switch(aux.charAt(7)){
			case 'g':	return "Segunda-feira " + Integer.toString(hora) + ":00";
			case 'r':	return "Terça-feira " + Integer.toString(hora) + ":00";
			case 'a':	return "Quarta-feira " + Integer.toString(hora) + ":00";
			case 'i':	return "Quinta-feira " + Integer.toString(hora) + ":00";
			case 'x':	return "Sexta-feira " + Integer.toString(hora) + ":00";
			case 'b':	return "Sábado " + Integer.toString(hora) + ":00";
			default: return "";
		}
	}

	// Atribui o endereço do servidor smtp para a sessão
	public static String coletaserver(String email){
		String dominio = email.substring(email.indexOf('@') + 1, email.lastIndexOf('.'));
		if(dominio.equals("gmail")){
			return "smtp.gmail.com";
		}
		else if(dominio.equals("outlook") || dominio.equals("hotmail") || dominio.equals("hotmail.com") || dominio.equals("outlook.com")){
			return "smtp.live.com";
		}
		else if(dominio.equals("yahoo") || dominio.equals("ymail") || dominio.equals("yahoo.com") || dominio.equals("ymail.com")){
			return "smtp.mail.yahoo.com";
		}
		return null;
	}

	// Caixa que demonstra o fim do programa
	public static void encerra(){
		JOptionPane.showMessageDialog(null, "Obrigado por utilizar o sistema desenvolvido por:\n\tRAH - Desenvolvimento de Sistemas.",empresa,JOptionPane.INFORMATION_MESSAGE,logo_empresa);
	}


	public static String connect(String portname[]){
	    String s = null;
		SerialPort port = new SerialPort(portname[0]);
		try{
			port.openPort();
			port.setParams(
				SerialPort.BAUDRATE_9600,
				SerialPort.DATABITS_8,
				SerialPort.STOPBITS_1,
				SerialPort.PARITY_NONE
			);
			while(true) {
				s = port.readString(7);
				if(s != null){
					// Read the first 7 byte
					//String msg  = new String(s);
					//System.out.println("\nRead: " + s);
					//System.out.println("\n");
					port.closePort();
					return s;
				}
			}
			/*port.addEventListener((SerialPortEvent event)->{
				if(event.isRXCHAR()){
					try{
						s = port.readString(5);
						System.out.print(s);

					}
					catch(SerialPortException e){
						e.printStackTrace();
					}
				}
			});*/
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
}