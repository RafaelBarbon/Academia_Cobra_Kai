
// Alcides Gomes Beato Neto		19060987
// Henrique Sartori Siqueira	19240472
// Rafael Silva Barbon			19243633

public class aula{
	private String codigo, horario;
	private int faixa;
	private boolean armas;

    public aula(int faixa, boolean armas, String horario, String codigo){
        this.faixa = faixa;
        this.armas = armas;
		this.horario = horario;
		this.codigo = codigo;
    }

	public int get_faixaN(){
		return this.faixa;
	}

    public String get_faixa(){
        switch(this.faixa){
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

    public boolean get_arma(){
        return this.armas;
    }

	public String get_horario(){
		return this.horario;
	}

	public String get_codigo(){
		return this.codigo;
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

	public String exibe(){
		return String.format("\nCódigo: %s\nFaixa: %s\nHorário: %s\nArmas: %s\n\n",get_codigo(), get_faixa(), get_horario(),get_arma() ? "sim" : "não");
		//System.out.println();
		//System.out.printf("Código: %s\n", get_codigo());
		//System.out.printf("Faixa: %s\n", get_faixa());
		//System.out.printf("Horário: %s\n", get_horario());
		//System.out.printf("Armas: %s\n", get_arma() ? "sim" : "não");
		//System.out.println();
	}

	public String toString(){
		return String.format("%s - %s ", get_codigo(), get_faixa());
	}
}


