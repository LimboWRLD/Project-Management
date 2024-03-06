package sredstva;

import interfejsi.Csv;

public class Dimenzije implements Csv{
	private double sirina;
	private double duzina;
	private double visina;
	//Konstruktor bez parametara
	public Dimenzije() {
		super();
		
	}
	//Konstruktor sa parametrima
	public Dimenzije(double sirina,double visina, double duzina ) {
		super();
		this.sirina = sirina;
		this.duzina = duzina;
		this.visina = visina;
	}
	//Geteri i seteri
	public double getVisina() {
		return visina;
	}
	public void setVisina(double visina) {
		this.visina = visina;
	}
	public double getSirina() {
		return sirina;
	}
	public void setSirina(double sirina) {
		this.sirina = sirina;
	}
	public double getDuzina() {
		return duzina;
	}
	public void setDuzina(double duzina) {
		this.duzina = duzina;
	}
	
	//Priprema za upis u fajl
	public String toCsv() {
		return this.sirina+"x"+this.visina+"x"+this.duzina;
	}
	//Konverzija u string
	@Override
	public String toString() {
		return "Dimenzije [sirina=" + sirina + ", visina=" + visina + ", duzina=" + duzina  + "]";
	}
}
