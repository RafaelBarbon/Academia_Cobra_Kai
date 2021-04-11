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

    public int get_faixa(){
        return this.faixa;
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
		System.out.printf("Faixa: ");
		switch(get_faixa()){
			case 0:
			System.out.println("Amarela");
				break;
			case 1:
			System.out.println("Dourada");
				break;
			case 2:
			System.out.println("Laranja");
				break;
			case 3:
			System.out.println("Jade");
				break;
			case 4:
			System.out.println("Verde");
				break;
			case 5:
			System.out.println("Roxa");
				break;
			case 6:
			System.out.println("Azul");
				break;
			case 7:
			System.out.println("Vermelha");
				break;
			case 8:
			System.out.println("Marrom Clara");
				break;
			case 9:
			System.out.println("Marrom");
				break;
			case 10:
			System.out.println("Preta");
				break;
		}
		System.out.printf("Período: %s\n", get_periodo() == 1 ? "matutino" : get_periodo() == 2 ? "vespertino" : "noturno");
		System.out.printf("Armas: %s\n", get_arma() ? "sim" : "não");
		System.out.println();
	}

	public String toString(){
		return String.format("%s - %s ", get_codigo(), get_periodo() == 1 ? "matutino" : get_periodo() == 2 ? "vespertino" : "noturno");
	}
}


