package radNadFajlovima;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import interfejsi.sortirajuci;
import korisnici.Menadzer;
import korisnici.Zaposleni;
import ljudskResursi.Departman;
import korisnici.Prodavac;
import sredstva.TehnickaRoba;
import sredstva.Dimenzije;
import sredstva.RobaKratkogTrajanja;

public class Mapper {

    public Mapper() {
        // Konstruktor bez parametara | prazumevani
    }

    // Metoda za kreiranje menadžera iz stringa
    public Menadzer kreirajMenadzera(String podaci) {
        String[] podaciMenadzera = podaci.split("\\|");
        Departman departman  = new Departman();
        if (podaci.length() >= 4) {
        	departman = kreirajDepartman(podaciMenadzera[4]);
        }
        return new Menadzer(podaciMenadzera[0], podaciMenadzera[1], podaciMenadzera[2], Double.parseDouble(podaciMenadzera[3]), departman);
    }



    // Metoda za dobijanje podataka o zaposlenima iz stringa
    public Zaposleni dobaviZaposlene(String podaci) {
        List<String> sviPodaci = new Pisac().ucitajSveLinije("src/data/data.csv");

        for (String zaposleni : sviPodaci) {
            if (zaposleni.startsWith(podaci)) {
                return zaposleni.startsWith("11") ? kreirajMenadzera(zaposleni) : kreirajProdavca(zaposleni);
            }
        }
        return null;
    }
    // Metoda za kreiranje prodavca iz stringa
    public Prodavac kreirajProdavca(String linija) {
        String[] podaci = linija.contains(":") ? linija.split(":") : linija.split("\\|");
        ArrayList<LocalDate> datumi = new ArrayList<>();

        String[] datumiString = podaci[5].split("\\@");
        if (!datumiString[0].equalsIgnoreCase("nema odsustva")) {
            for (String datum : datumiString) {
                datumi.add(LocalDate.parse(datum));
            }
        }
        return new Prodavac(podaci[0].trim(), podaci[1].trim(), podaci[2].trim(), Double.parseDouble(podaci[3]), podaci[4].trim(), datumi);
    }

    // Metoda za kreiranje tehničke robe iz stringa
    public TehnickaRoba kreirajTehnickuRobu(String linija) {
        String[] podaci = linija.split("\\|");
        String[] dimenzijeString = podaci[5].split("x");
        
        return new TehnickaRoba(podaci[0], Double.parseDouble(podaci[1]), podaci[2], podaci[3], podaci[4],
                new Dimenzije(Double.parseDouble(dimenzijeString[0]), Double.parseDouble(dimenzijeString[1]), Double.parseDouble(dimenzijeString[0])),
                Double.parseDouble(podaci[6]), Double.parseDouble(podaci[7]));
    }

    // Metoda za kreiranje robe kratkog trajanja iz stringa
    public sortirajuci kreirajRobuKratkogTrajanja(String linija) {
        String[] podaci = linija.split("\\|");

        return new RobaKratkogTrajanja(podaci[0], Double.parseDouble(podaci[1]), podaci[2], podaci[3], podaci[4], LocalDate.parse(podaci[5]), podaci[6]);
    }

    // Pomoćna metoda za kreiranje departmana iz podataka menadžera
    private Departman kreirajDepartman(String podaci) {
        Departman departman = new Departman();
            if (!podaci.equalsIgnoreCase("nema")) {
                String[] podaciDepartmana = podaci.split(":");
                for (String deoDepartmana : podaciDepartmana) {
                    departman.dodajZaposlenog(dobaviZaposlene(deoDepartmana));
                }
            }
        
        return departman;
    }
}