// Alcides Gomes Beato Neto		19060987
// Henrique Sartori Siqueira	19240472
// Rafael Silva Barbon			19243633

import java.util.*;

public class aluno extends Info{
    private float valor; // Define valor do aluno ao cadastrar em uma aula
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
	public boolean set_senha(String senhaAntiga, String senhaNova, String senhaMestre){
		if(senhaAntiga.equals(get_senha()) || senhaAntiga.equals(senhaMestre)){
			super.senha = senhaNova;
			return true;
		}
		return false;
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
			case 0:	return "Branca";
			case 1:	return "Amarela";
			case 2:	return "Laranja";
			case 3:	return "Verde";
			case 4:	return "Azul";
			case 5:	return "Roxa";
			case 6:	return "Vermelha";
			case 7:	return "Marrom";
			case 8:	return "Marrom Ponta Preta";
			case 9:	return "Preta";
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
		if(!aulas_matriculadas.contains(a) && (a.get_faixaN() == get_faixaN() || a.get_faixaN()+1 == get_faixaN() || a.get_faixaN()-1 == get_faixaN())){
			aulas_matriculadas.add(a);
			return true;
		}
		return false;
	}

	public boolean del_aula(aula a){
		return aulas_matriculadas.remove(a);
	}

	public String exibe_aulas(){
		String exib = "";
		for(aula d : aulas_matriculadas){
			exib = exib.concat("\nAula:" + d.exibe());
		}
		return exib;
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
		String tudo = "Nome: " + get_nome() + "\nCPF: " + get_CPF() + "\nData de nascimento: " + get_nascimento() + "\nEmail: " + get_email() + "\nTelefone: " + get_telefone() + "\nCódigo: " + get_codigo() + "\nFaixa: " + get_faixa() + "\nAuxiliar: " + (get_auxiliar() ? "sim" : "não") + "\nQuantidade de aulas na semana: " + get_aulas() + "\nValor semanal de aulas: " + String.format("%.2f",get_valor()) + "\nSituação: " + (get_mes_pago() ? "Em dia\n" : ("Atrasado " + get_atraso_conta() + " meses\n"));
		tudo = tudo.concat(exibe_aulas());
		return tudo;
	}

	// Método que retorna nome e código de um cliente para escolha de consulta
	public String toString(){
		return String.format("%s - %s ", get_nome(), get_codigo());
	}
}

