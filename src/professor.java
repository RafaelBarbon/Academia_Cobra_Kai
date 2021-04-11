// Alcides Gomes Beato Neto		19060987
// Henrique Sartori Siqueira	19240472
// Rafael Silva Barbon			19243633

public class professor extends Info{
	private int aulasMes;
    public professor(String nome, String CPF, String email, String telefone, String codigo, Data nascimento, String senha){
        this.nome = nome;
        this.CPF = CPF;
        this.email = email;
        this.telefone = telefone;
        this.codigo = codigo;
        this.nascimento = nascimento;
		this.aulasMes = 0;
		this.senha = senha;
    }

	public int get_aulasMes(){
		return this.aulasMes;
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
	public void set_senha(String senhaAntiga, String senhaNova){
		if(senhaAntiga.equals(get_senha())){
			super.senha = senhaNova;
		}
	}

	// Método que inicia o mês, zerando o número de aulas dos professores
	public void IniciaMes(){
		this.aulasMes = 0;
	}

	// Método que incrementa o número de aulas do professor
	public void IncrementaAula(){
		this.aulasMes++;
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
		System.out.printf("Número codigo: %.08s\n", get_codigo());
		System.out.println();
	}

	// Método que retorna nome e código de um cliente para escolha de consulta
	public String toString(){
		return String.format("%s - %s\t", get_nome(), get_codigo());
	}
}
