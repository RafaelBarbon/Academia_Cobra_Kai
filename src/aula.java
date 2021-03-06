
// Alcides Gomes Beato Neto		19060987
// Henrique Sartori Siqueira	19240472
// Rafael Silva Barbon			19243633

public class aula{
	private String codigo, horario, descricao;
	private int faixa;
	private boolean armas;

    public aula(int faixa, boolean armas, String horario, String descricao, String codigo){
        this.faixa = faixa;
        this.armas = armas;
		this.horario = horario;
		this.descricao = descricao;
		this.codigo = codigo;
    }

	public int get_faixaN(){
		return this.faixa;
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

    public boolean get_arma(){
        return this.armas;
    }

	public String get_horario(){
		return this.horario;
	}

	public String get_codigo(){
		return this.codigo;
	}

	public String get_descricao(){
		return this.descricao;
	}

    public void set_horario(String horario){
        this.horario = horario;
    }

    public void set_faixa(int faixa){
        this.faixa = faixa;
    }

    public void set_arma(boolean arma){
        this.armas = arma;
    }

	public void set_descricao(String desc){
		this.descricao = desc;
	}

	public String exibe(){
		return String.format("Código: %s\nFaixa: %s\nHorário: %s\nArmas: %s\nDescrição: %s\n",get_codigo(), get_faixa(), get_horario(),get_arma() ? "sim" : "não", get_descricao());
	}

	public String toString(){
		return String.format("%s - %s ", get_codigo(), get_faixa());
	}
}


