package sredstva;

import interfejsi.Csv;
import interfejsi.sortirajuci;

public abstract class Roba  implements Csv, sortirajuci{
	
	private String naziv;
	private Double cena;
	private String jedinicaMere;
	private String zemljaPorekla;
	private String model;
	//Konstruktor bez parametara
	public Roba() {
		super();
		
	}
	//Kontruktor sa parametrima
	public Roba(String naziv, Double cena, String jedinicaMere, String zemljaPorekla, String model){
		super();
		this.naziv = naziv;
		this.cena = cena;
		this.jedinicaMere = jedinicaMere;
		this.zemljaPorekla = zemljaPorekla;
		this.model = model;
	}
	//Geteri i seteri
	public String getNaziv() {
		return naziv;
	}
	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}
	public Double getCena() {
		return cena;
	}
	public void setCena(Double cena) {
		this.cena = cena;
	}
	public String getJedinicaMere() {
		return jedinicaMere;
	}
	public void setJedinicaMere(String jedinicaMere) {
		this.jedinicaMere = jedinicaMere;
	}
	public String getZemljaPorekla() {
		return zemljaPorekla;
	}
	public void setZemljaPorekla(String zemljaPorekla) {
		this.zemljaPorekla = zemljaPorekla;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	//Konverzija u string
	@Override
	public String toString() {
		return "Roba [naziv=" + naziv + ", cena=" + cena + ", jedinicaMere=" + jedinicaMere + ", zemljaPorekla="
				+ zemljaPorekla + ", model=" + model + "]";
	}
	//Priprema za upis u fajl
	public String toCsv() {
		return this.getNaziv()+"|"+this.getCena()+"|"+this.getJedinicaMere()+"|"+this.getZemljaPorekla()+"|"+this.getModel();
		
	}
}
