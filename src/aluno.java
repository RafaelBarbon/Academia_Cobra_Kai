// Alcides Gomes Beato Neto		19060987
// Henrique Sartori Siqueira	19240472
// Rafael Silva Barbon			19243633

public class aluno{
	private String nome, CPF, email, telefone, carteirinha;
    private float mensalidade;
	private boolean mes_pago;
    private int atraso_conta;
    //Construtores
	public aluno(String nome, String CPF, String email, String telefone){
		this.nome = nome;
        this.CPF = CPF;
        this.email = email;
        this.telefone = telefone;
		this.atraso_conta = 0;
	}
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

	public int get_atraso_conta(){
		return this.atraso_conta;
	}

	public boolean get_mes_pago(){
		return this.mes_pago;
	}

	public void atraso(int meses){
		this.atraso_conta += meses;
		this.mes_pago = false;
	}

	public void pago(){
		this.mes_pago = true;
		this.atraso_conta = 0;
	}

	public void exibe(){
		System.out.println("Nome: %s", get_nome());
		System.out.println("CPF: %s", get_CPF());
		System.out.println("Email: %s", get_email());
		System.out.println("Telefone: %s", get_telefone());
		System.out.println("Número carteirinha: %s", get_carteirinha());
		System.out.println("Valor da mensalidade: %.2f", get_mensalidade());
		if(get_mes_pago()){
			System.out.println("Em dia");
		}
		else{
			System.out.println("Atrasado %d meses", get_atraso_conta());
		}
	}

	public String toString(){
		return String.format("%s - %s", get_nome(), get_carteirinha());
	}
}

