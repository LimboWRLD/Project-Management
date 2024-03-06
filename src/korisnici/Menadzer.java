package korisnici;

import java.util.Random;
import ljudskResursi.Departman;
import radNadFajlovima.Pisac;

public class Menadzer extends Zaposleni {
	
    private Departman departman;

    //  Konstruktor bez parametara
    public Menadzer() {
        super();
    }

    // Konstruktor sa parametrima
    public Menadzer(String sifraZaposlenog, String ime, String prezime, Double visinaPlate, Departman departman) {
        super(sifraZaposlenog, ime, prezime, visinaPlate);
        this.departman = departman;
    }

    // Getter za departman
    public Departman getDepartman() {
        return departman;
    }

    // Setter za departman
    public void setDepartman(Departman departman) {
        this.departman = departman;
    }

    // Prikaz informacija o menadžeru (override)
    @Override
    public String toString() {
        return "Menadzer [departman=" + departman + "]" + super.toString();
    }

    // Formatiranje za CSV format (override metoda)
    @Override
    public String toCsv() {
        String zaposleniUDepartmanu = "";

        // Provera da li menadžer ima departman i zaposlene u njemu
        if (this.getDepartman() == null || this.getDepartman().getSviZaposleni().size() == 0) {
            zaposleniUDepartmanu = "nema";
        } else {
            // Prolazak kroz zaposlene u departmanu i dodavanje njihovih šifri u string
            for (Zaposleni z : this.departman.getSviZaposleni()) {
                if (z == null && this.departman.getSviZaposleni().size()==1) {
                	zaposleniUDepartmanu = "nema";
                }else if(z==null){
                	continue;
                }else {
                zaposleniUDepartmanu += z.getSifraZaposlenog() + ":";
            }}
                
            }
        

        // Formatiranje podataka u CSV format
        return super.toCsv() + "|" + zaposleniUDepartmanu;
    }

    // Generisanje šifre zaposlenog (override)
    @Override
    public String generisiSifruZaposlenog() {
        StringBuilder id = new StringBuilder("11");
        if(new Pisac().sviMenadzeri().size()>0) {
        	while(new Pisac().sviMenadzeri().toString().contains(id.toString())) {
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

    // Red sortiranja menadžera (override metoda)
    @Override
    public int redSortiranja() {
        return 1;
    }
}