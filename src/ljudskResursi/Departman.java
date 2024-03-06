package ljudskResursi;

import java.util.ArrayList;
import korisnici.Zaposleni;

public class Departman {
	
	private ArrayList<Zaposleni>sviZaposleni = new ArrayList<>();

	//Kontruktor sa parametrima
	public Departman() {
		super();
		
	}
	//Konstruktor bez parametara
	public Departman(ArrayList<Zaposleni> sviZaposleni) {
		super();
		this.sviZaposleni = sviZaposleni;
	}
	//Geteri i seteri
	public ArrayList<Zaposleni> getSviZaposleni() {
		return sviZaposleni;
	}

	public void setSviZaposleni(ArrayList<Zaposleni> sviZaposleni) {
		this.sviZaposleni = sviZaposleni;
	}
	//Priprema objekta za upis u fajl
	public String toCsv() {
		String neki = "";
		if(sviZaposleni.size()==0){
			neki="nema zaposlenih";
		}
		for(Zaposleni z:sviZaposleni) {
			if(z==null) {
				continue;
			}
			if(sviZaposleni.indexOf(z)!=sviZaposleni.size()-1) {
			neki +=  z.getSifraZaposlenog() + ",";
		}else{
			neki +=  z.getSifraZaposlenog() ;}
		}
		
		return neki;
	}
	//Pretvaranje objekta u string
	@Override
	public String toString() {
		return "Departman [sviZaposleni=" + sviZaposleni + "]";
	}
	//Metode za dodavanje i uklanjanje zaposlenih iz departmana
	public void dodajZaposlenog(Zaposleni z) {
		this.sviZaposleni.add(z);
	}

	public void ukloniZaposlenog(Zaposleni z) {
		this.sviZaposleni.remove(z);
	}
}
