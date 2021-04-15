
// Alcides Gomes Beato Neto		19060987
// Henrique Sartori Siqueira	19240472
// Rafael Silva Barbon			19243633

public class aula{
	private String codigo;
	private int faixa, periodo;
	private boolean armas;

    public aula(int faixa, boolean armas, int periodo, String codigo){
        this.faixa = faixa;
        this.armas = armas;
		this.periodo = periodo;
		this.codigo = codigo;
    }

	public int get_faixaN(){
		return this.faixa;
	}

    public String get_faixa(){
		String ret = "";
        switch(this.faixa){
			case 0:
				ret = "Amarela";
				break;
			case 1:
				ret = "Dourada";
				break;
			case 2:
				ret = "Laranja";
				break;
			case 3:
				ret = "Jade";
				break;
			case 4:
				ret = "Verde";
				break;
			case 5:
				ret = "Roxa";
				break;
			case 6:
				ret = "Azul";
				break;
			case 7:
				ret = "Vermelha";
				break;
			case 8:
				ret = "Marrom Clara";
				break;
			case 9:
				ret = "Marrom";
				break;
			case 10:
				ret = "Preta";
				break;
		}
		return ret;
    }

    public boolean get_arma(){
        return this.armas;
    }

	public int get_periodo(){
		return this.periodo;
	}

	public String get_codigo(){
		return this.codigo;
	}

    public void set_periodo(int periodo){
        this.periodo = periodo;
    }

    public void set_faixa(int faixa){
        this.faixa = faixa;
    }

    public void set_arma(boolean arma){
        this.armas = arma;
    }

	public void exibe(){
		System.out.println();
		System.out.printf("Código: %s\n", get_codigo());
		System.out.printf("Faixa: %s", get_faixa());
		System.out.printf("Período: %s\n", get_periodo() == 1 ? "matutino" : get_periodo() == 2 ? "vespertino" : "noturno");
		System.out.printf("Armas: %s\n", get_arma() ? "sim" : "não");
		System.out.println();
	}

	public String toString(){
		return String.format("%s - %s ", get_codigo(), get_faixa());
	}
}


