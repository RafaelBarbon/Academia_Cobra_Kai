// Alcides Gomes Beato Neto		19060987
// Henrique Sartori Siqueira	19240472
// Rafael Silva Barbon			19243633

public class aula{
	private String codigo;
	private int nivel, periodo;
	private boolean armas;

    public aula(int nivel, boolean armas, int periodo, String codigo){
        this.nivel = nivel;
        this.armas = armas;
		this.periodo = periodo;
		this.codigo = codigo;
    }

    public int get_nivel(){
        return this.nivel;
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

    public void set_nivel(int nivel){
        this.nivel = nivel;
    }

    public void set_arma(boolean arma){
        this.armas = arma;
    }

	public void exibe(){
		System.out.printf("Código: %s\n", get_codigo());
		System.out.printf("Nível: %s\n", get_nivel() == 1 ? "iniciante" : get_nivel() == 2 ? "intermediário" : "avançado");
		System.out.printf("Período: %s\n", get_periodo() == 1 ? "matutino" : get_periodo() == 2 ? "vespertino" : "noturno");
		System.out.printf("Armas: %s\n", get_arma() ? "sim" : "não");
	}

	public String toString(){
		return String.format("%s - %s ", get_codigo(), get_periodo() == 1 ? "matutino" : get_periodo() == 2 ? "vespertino" : "noturno");
	}
}


