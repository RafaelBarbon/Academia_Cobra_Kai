// Alcides Gomes Beato Neto		19060987
// Henrique Sartori Siqueira	19240472
// Rafael Silva Barbon			19243633

import java.util.*;

public class aluno extends Info{
    private float valor; // DEFINIR valor DO ALUNO AO CADASTRAR EM UMA AULA
	private boolean mes_pago, auxiliar;
    private int atraso_conta, faixa;
	private LinkedList <aula> aulas_matriculadas = new LinkedList<aula>();

    //Construtores
	public aluno(String nome, String CPF, String email, String telefone, String codigo, Data nascimento, String senha){
		super.nome = nome;
        super.CPF = CPF;
        super.email = email;
        super.telefone = telefone;
		this.atraso_conta = 0;
		super.codigo = codigo;
		this.mes_pago = true;
		this.valor = 0;
		super.nascimento = nascimento;
		this.faixa = 0;
		this.auxiliar = false;
		super.senha = senha;
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

	public float get_valor(){
		return this.valor;
	}

	public void set_valor(float valor){
		this.valor = valor;
	}

	@Override
	public void set_nascimento(Data data){
		super.nascimento = data;
	}

	@Override
	public void set_senha(String senhaAntiga, String senhaNova, String senhaMestre){
		if(senhaAntiga.equals(get_senha()) || senhaAntiga.equals(senhaMestre)){
			super.senha = senhaNova;
		}
	}

	public int get_atraso_conta(){
		return this.atraso_conta;
	}

	public boolean get_mes_pago(){
		return this.mes_pago;
	}

	public void set_faixa(int faixa){
		this.faixa = faixa;
	}

	public String get_faixa(){
        switch(get_faixaN()){
			case 0:	return "Amarela";
			case 1:	return "Dourada";
			case 2:	return "Laranja";
			case 3:	return "Jade";
			case 4:	return "Verde";
			case 5:	return "Roxa";
			case 6:	return "Azul";
			case 7:	return "Vermelha";
			case 8:	return "Marrom Clara";
			case 9:	return "Marrom";
			case 10: return "Preta";
			default: return "";
		}
    }

	public int get_faixaN(){
		return this.faixa;
	}

	public void set_auxiliar(boolean auxiliar){
		if(this.faixa >= 9 && auxiliar)
			this.auxiliar = auxiliar;
		else if(!auxiliar)
			this.auxiliar = auxiliar;
	}

	public boolean get_auxiliar(){
		return this.auxiliar;
	}

	public boolean add_aula(aula a){
		if(!aulas_matriculadas.contains(a) && (a.get_faixaN() == faixa || a.get_faixaN()+1 == faixa || a.get_faixaN()-1 == faixa)){
			aulas_matriculadas.add(a);
			return true;
		}
		return false;
	}

	public void del_aula(aula a){
		aulas_matriculadas.remove(a);
	}

	public String exibe_aulas(){
		String exib = "";
		for(aula d : aulas_matriculadas){
			exib.concat(d.exibe());
			//d.exbie();
			//System.out.println();
			exib.concat("\n");
		}
		return exib;
	}

	public void att_aula(aula a){
		for(aula d : aulas_matriculadas){
			if(d.get_codigo() == a.get_codigo()){
				aulas_matriculadas.remove(d);
				if(a.get_faixaN() == faixa || a.get_faixaN() + 1 == faixa || a.get_faixaN() - 1 == faixa){
					aulas_matriculadas.add(a);
				}
				break;
			}
		}
	}

	public int get_aulas(){
		return this.aulas_matriculadas.size();
	}

	// Método que atribui atraso da conta de um cliente
	public void atraso(int meses){
		this.atraso_conta += meses;
		if(this.atraso_conta > 1){
			this.mes_pago = false;
		}
	}

	// Método que torna a situação financeira de um cliente como pago
	public void pago(){
		this.mes_pago = true;
		this.atraso_conta = 0;
	}


	// Método que exibe as informações de um cliente
	@Override
	public String exibe(){
		String tudo = "\nNome: " + get_nome() + "\nCPF: " + get_CPF() + "\nData de Nascimento: " + get_nascimento() + "\nEmail: " + get_email() + "\nTelefone: " + get_telefone() + "\nNúmero codigo: " + get_codigo() + "\nFaixa: " + get_faixa() + "\nAuxiliar: " + (get_auxiliar() ? "sim" : "não") + "\nQuantidade de aulas na semana: " + get_aulas() + "\nValor semanal de aulas: " + String.format("%.2f",get_valor()) + "\nSituação: " + (get_mes_pago() ? "Em dia" : ("Atrasado " + get_atraso_conta() + " meses\n"));



		//System.out.println();
		//System.out.printf("Nome: %s\n", get_nome());
		//System.out.printf("CPF: %s\n", get_CPF());
		//System.out.printf("Data de nascimento: " + get_nascimento() + "\n");
		//System.out.printf("Email: %s\n", get_email());
		//System.out.printf("Telefone: %s\n", get_telefone());
		//System.out.printf("Número codigo: %s\n", get_codigo());
		//System.out.printf("Faixa: %s\n", get_faixa());
		//System.out.printf("Auxiliar: %s\n", get_auxiliar() ? "sim" : "não");
		//System.out.printf("Quantidade de aulas na semana: %d\n", get_aulas());
		//System.out.printf("Valor semanal de aulas: %.2f\n", get_valor());
		//System.out.printf("Situação: ");
		//if(get_mes_pago()){
		//	System.out.println("Em dia");
		//}
		//else{
		//	System.out.printf("Atrasado %d meses\n", get_atraso_conta());
		//}
		//System.out.println();
		tudo.concat(exibe_aulas());
		//System.out.println();
		return tudo;
	}

	// Método que retorna nome e código de um cliente para escolha de consulta
	public String toString(){
		return String.format("%s - %s ", get_nome(), get_codigo());
	}
}

