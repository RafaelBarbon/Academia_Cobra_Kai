// Alcides Gomes Beato Neto		19060987
// Henrique Sartori Siqueira	19240472
// Rafael Silva Barbon			19243633

abstract class Info {
	protected String nome, CPF, email, telefone, codigo;
	protected Data nascimento;

	String get_nome(){
		return this.nome;
	}

	abstract void set_nome(String nome);

	String get_CPF(){
		return this.CPF;
	}

	abstract void set_CPF(String CPF);

	String get_email(){
		return this.email;
	}

	abstract void set_email(String email);

	String get_telefone(){
		return this.telefone;
	}

    abstract void set_telefone(String telefone);

    String get_codigo(){
		return this.codigo;
	}

	Data get_nascimento(){
		return this.nascimento;
	}

	abstract void set_nascimento(Data data);

	abstract void exibe();
}
