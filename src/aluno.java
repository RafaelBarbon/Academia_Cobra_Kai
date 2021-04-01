// Alcides Gomes Beato Neto		19060987
// Henrique Sartori Siqueira	19240472
// Rafael Silva Barbon			19243633

public class aluno{
	private String nome, CPF, email, telefone, carteirinha;
    private float mensalidade; // DEFINIR MENSALIDADE DO ALUNO AO CADASTRAR EM UMA AULA
	private boolean mes_pago;
    private int atraso_conta, forma_pagamento;
	private Data nascimento;

    //Construtores
	public aluno(String nome, String CPF, String email, String telefone, String carteirinha, int forma_pagamento, Data nascimento){
		this.nome = nome;
        this.CPF = CPF;
        this.email = email;
        this.telefone = telefone;
		this.atraso_conta = 0;
		this.carteirinha = carteirinha;
		this.forma_pagamento = forma_pagamento;
		this.mes_pago = true;
		this.mensalidade = 0;
		this.nascimento = nascimento;
	}
	/*
    public aluno(String nome, String CPF, String telefone){
		this.nome = nome;
        this.CPF = CPF;
        this.telefone = telefone;
		this.atraso_conta = 0;
	}
	public aluno(String nome, String CPF, String email){
		this.nome = nome;
        this.CPF = CPF;
        this.email = email;
		this.atraso_conta = 0;
	}
*/
	// Setters and getters
	public String get_nome(){
		return this.nome;
	}

	public void set_nome(String nome){
		this.nome = nome;
	}

	public String get_CPF(){
		return this.CPF;
	}

	public void set_CPF(String CPF){
		this.CPF = CPF;
	}

	public String get_email(){
		return this.email;
	}

	public void set_email(String email){
		this.email = email;
	}

	public String get_telefone(){
		return this.telefone;
	}

    public void set_telefone(String telefone){
		this.telefone = telefone;
	}

    public String get_carteirinha(){
		return this.carteirinha;
	}

	public void set_carteirinha(String carteirinha){
		this.carteirinha = carteirinha;
	}

	public float get_mensalidade(){
		return this.mensalidade;
	}

	public void set_mensalidade(float mensalidade){
		this.mensalidade = mensalidade;
	}

	public void set_nascimento(int Dia, int Mes, int Ano){
		this.nascimento = new Data(Dia, Mes, Ano);
	}

	public int get_atraso_conta(){
		return this.atraso_conta;
	}

	public void set_forma_pagamento(int forma_pagamento){
		this.forma_pagamento = forma_pagamento;
	}

	public int get_forma_pagamento(){
		return this.forma_pagamento;
	}

	public boolean get_mes_pago(){
		return this.mes_pago;
	}

	public Data get_nascimento(){
		return this.nascimento;
	}

	// Método que atribui atraso da conta de um cliente
	public void atraso(int meses){
		this.atraso_conta += meses;
		this.mes_pago = false;
	}

	// Método que torna a situação financeira de um cliente como pago
	public void pago(){
		this.mes_pago = true;
		this.atraso_conta = 0;
	}

	// Método que exibe as informações de um cliente
	public void exibe(){
		System.out.printf("Nome: %s\n", get_nome());
		System.out.printf("CPF: %s\n", get_CPF());
		System.out.printf("Data de nascimento: "+ get_nascimento()+"\n");
		System.out.printf("Email: %s\n", get_email());
		System.out.printf("Telefone: %s\n", get_telefone());
		System.out.printf("Número carteirinha: %.8s\n", get_carteirinha());
		System.out.printf("Valor da mensalidade: %.2f\n", get_mensalidade());
		System.out.printf("Forma de Pagamento:%s\n", get_forma_pagamento() == 1 ? "Boleto bancário" : "Débito automático");
		System.out.printf("Mensalidade: ");
		if(get_mes_pago()){
			System.out.println("Em dia");
		}
		else{
			System.out.printf("Atrasado %d meses\n", get_atraso_conta());
		}
	}

	// Método que retorna nome e código de um cliente para escolha de consulta
	public String toString(){
		return String.format("%s - %s\t", get_nome(), get_carteirinha());
	}
}

