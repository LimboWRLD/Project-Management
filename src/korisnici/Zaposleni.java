package korisnici;

import java.util.Random;

import interfejsi.Csv;
import interfejsi.sortirajuci;

public abstract class Zaposleni implements Csv, sortirajuci{
	private String sifraZaposlenog;
	private String ime;
	private String prezime;
	private Double visinaPlate;
	//Konstruktor bez parametara
	public Zaposleni() {
		super();
	}
	//Kontruktor sa parametrima
	public Zaposleni(String sifraZaposlenog, String ime, String prezime, Double visinaPlate) {
		this.sifraZaposlenog = sifraZaposlenog;
		this.ime = ime;
		this.prezime = prezime;
		this.visinaPlate = visinaPlate;
	}
	//Geteri i seteri
	public String getSifraZaposlenog() {
		return sifraZaposlenog;
	}
	public void setSifraZaposlenog(String sifraZaposlenog) {
		this.sifraZaposlenog = sifraZaposlenog;
	}
	public String getIme() {
		return ime;
	}
	public void setIme(String ime) {
		this.ime = ime;
	}
	public String getPrezime() {
		return prezime;
	}
	public void setPrezime(String prezime) {
		this.prezime = prezime;
	}
	public Double getVisinaPlate() {
		return visinaPlate;
	}
	public void setVisinaPlate(Double visinaPlate) {
		this.visinaPlate = visinaPlate;
	}
	//Pretvaranje instance klase u string
	@Override
	public String toString() {
		return "Zaposleni [sifraZaposlenog=" + sifraZaposlenog + ", ime=" + ime + ", prezime=" + prezime
				+ ", visinaPlate=" + visinaPlate + "]";
	}
	//Generisanje sifre zaposlenog
	public String generisiSifruZaposlenog() {
		StringBuilder id =  new StringBuilder();
		Random randomBroj = new Random();
		for(int i = 0; i<6; i++) {	
			int randomInt = randomBroj.nextInt(10);
			id.append(randomInt);
		}
		return id.toString();
	}
	//Priprema objekta za upis u csv
	public String toCsv() {
		return this.getSifraZaposlenog()+"|"+this.getIme()+"|"+this.getPrezime()+"|"+this.getVisinaPlate();
	}
}
