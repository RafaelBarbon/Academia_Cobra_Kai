public class Data{
	private int dia,mes,ano;
	private boolean bi;

	public Data(int d,int m,int y){
		if(y > 0){ // Verifica se o ano é positivo
			if(y % 4 == 0 && y % 400 != 0){ // Verifica se é bissexto
				this.bi = true;
			}
			else{
				this.bi = false;
			}
		}
		else{ // Caso o ano seja negativo
			this.bi = false;
			throw new IllegalArgumentException("\nAno negativo!");
		}
		// Conferência dos dias do mês
		if((m == 1 || m == 3 || m == 5 || m == 7 || m == 8 || m == 10 || m == 12) && (d < 1 || d > 31)){
			throw new IllegalArgumentException("\nDia fora do intervalo (1-31)!");
		}
		else if((m == 4 || m == 6 || m == 9 || m == 11) && (d < 1 || d > 30)){
			throw new IllegalArgumentException("\nDia fora do intervalo (1-30)!");
		}
		else if(m == 2){
			if(this.bi && (d < 1 || d > 29)){
				throw new IllegalArgumentException("\nDia fora do intervalo (1-29)!");
			}
			else if(!this.bi && (d < 1 || d > 28)){
				throw new IllegalArgumentException("\nDia fora do intervalo (1-28)!");
			}
		}
		else if(m < 1 || m > 12){
			throw new IllegalArgumentException("\nMês fora do intervalo (1-12)!");
		}
		this.ano = y;
		this.mes = m;
		this.dia = d;
	}

	public String toString(){
		return String.format("%02d/%02d/%04d", dia, mes, ano);
	}

}
