// Alcides Gomes Beato Neto		19060987
// Henrique Sartori Siqueira	19240472
// Rafael Silva Barbon			19243633

public class professor extends Info{
    public professor(String nome, String CPF, String email, String telefone, String codigo, Data nascimento){
        this.nome = nome;
        this.CPF = CPF;
        this.email = email;
        this.telefone = telefone;
        this.codigo = codigo;
        this.nascimento = nascimento;
    }

    @Override
	public void set_nome(String nome){
		this.nome = nome;
	}

    @Override
	public void set_CPF(String CPF){
		this.CPF = CPF;
	}

    @Override
	public void set_email(String email){
		this.email = email;
	}


    @Override
    public void set_telefone(String telefone){
		this.telefone = telefone;
	}

    @Override
	public void set_nascimento(int Dia, int Mes, int Ano){
		this.nascimento = new Data(Dia, Mes, Ano);
	}

	// Método que exibe as informações do professor
	@Override
	public void exibe(){
		System.out.printf("Nome: %s\n", get_nome());
		System.out.printf("CPF: %s\n", get_CPF());
		System.out.printf("Data de nascimento: " + get_nascimento()+"\n");
		System.out.printf("Email: %s\n", get_email());
		System.out.printf("Telefone: %s\n", get_telefone());
		System.out.printf("Número codigo: %.08s\n", get_codigo());
	}

	// Método que retorna nome e código de um cliente para escolha de consulta
	public String toString(){
		return String.format("%s - %s\t", get_nome(), get_codigo());
	}
}
