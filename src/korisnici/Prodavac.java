package korisnici;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Random;

import radNadFajlovima.Pisac;

public class Prodavac extends Zaposleni {
	
	private String radnoMesto;
	private ArrayList<LocalDate>odsustva = new ArrayList<>();
	//Konstruktor bez parametara
	public Prodavac() {
		super();
		
	}
	//Konstruktor sa parametrima
	public Prodavac(String sifraZaposlenog, String ime, String prezime, Double visinaPlate , String radnoMesto, ArrayList<LocalDate>odsustva) {
		super(sifraZaposlenog, ime, prezime, visinaPlate);
		this.radnoMesto=radnoMesto;
		this.odsustva=odsustva;
	}
	//Geteri i seteri
	public String getRadnoMesto() {
		return radnoMesto;
	}
	public void setRadnoMesto(String radnoMesto) {
		this.radnoMesto = radnoMesto;
	}
	public ArrayList<LocalDate> getOdsustva() {
		return odsustva;
	}
	public void setOdsustva(ArrayList<LocalDate> odsustva) {
		this.odsustva = odsustva;
	}
	//Pretvaranje instance klase u string
	@Override
	public String toString() {
		return "Prodavac [radnoMesto=" + radnoMesto + ", odsustva=" + odsustva + "]" +super.toString();
	}
	//Metoda za dodavanje odsustva
	public void dodajOdsustvo(LocalDate odsustvo) {
		this.odsustva.add(odsustvo);
		
	}
	//Metoda za brisanje odsustva
	public void obrisiOdsustvo(LocalDate odsustvo) {
		this.odsustva.remove(odsustvo);
	}

	//Priprema instance klase za upis u fajl
	@Override
	public String toCsv() {
		String datumi="";
		if(this.getOdsustva().size()==0) {
			datumi="nema odsustva";
		}
		if(this.odsustva.size()==1) {
			datumi += this.getOdsustva().get(0).toString();
			}else {
		for(LocalDate datum : this.getOdsustva()) {
			datumi+=datum.toString()+"@";
		}}
		return super.toCsv()+ "|" + this.getRadnoMesto() + "|" + datumi;
	}
	//Generisanje sifre zaposlenog
	@Override
    public String generisiSifruZaposlenog() {
        StringBuilder id = new StringBuilder("22");
        if(new Pisac().sviProdavci().size()>0) {
        	while(new Pisac().sviProdavci().toString().contains(id.toString())) {
        			Random random = new Random();
        			for (int i = 0; i < 4; i++) {
        				int randomBroj = random.nextInt(10);
        				id.append(randomBroj);
        }
        }
        	}else {
        		Random random = new Random();
    			for (int i = 0; i < 4; i++) {
    				int randomBroj = random.nextInt(10);
    				id.append(randomBroj);
        	}}
        return id.toString();
    }
	//Metoda za sortiranje objekata u listi
	@Override
	public int redSortiranja() {
		return 2;
	}
}
