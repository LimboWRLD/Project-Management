package radNadFajlovima;


import korisnici.Prodavac;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import interfejsi.sortirajuci;
import korisnici.Menadzer;
import korisnici.Zaposleni;
import sredstva.RobaKratkogTrajanja;
import sredstva.TehnickaRoba;

public class Pisac {
	//Podrazumevani kostruktor
	public Pisac() {
		super();
		
	}
	//Spremanje objekata za upis
	public String zaFajl(Object o) {
		StringBuilder stringBuilder = new StringBuilder();
		
		if(o instanceof Zaposleni) {
			if(o instanceof Menadzer) {
				stringBuilder.append(((Menadzer) o).toCsv());
			}else {
				stringBuilder.append(((Prodavac) o).toCsv());
			}
		}else {
			if(o instanceof RobaKratkogTrajanja) {
				stringBuilder.append(((RobaKratkogTrajanja) o).toCsv());
			}else {
				stringBuilder.append(((TehnickaRoba) o).toCsv());
			}
		}
		return stringBuilder.toString();
	}
	//Upis u fajl
    public void pisi(ArrayList<sortirajuci> a, String filePath) {
        Map<?, List<sortirajuci>> grupisaniPoKlasi = a.stream()
                .collect(Collectors.groupingBy(Object::getClass));

        List<String> csvRedovi = grupisaniPoKlasi.entrySet().stream()
                .sorted(Comparator.comparing(ulazni -> ulazni.getValue().get(0).redSortiranja()))
                .flatMap(ulazni -> {
                    Class<?> klasa = (Class<?>) ulazni.getKey();
                    List<sortirajuci> objekti = ulazni.getValue();

                    Stream<String> redovi = objekti.stream()
                            .sorted(Comparator.comparing(sortirajuci::redSortiranja))
                            .flatMap(obj -> {
                                Stream<String> objLines = Stream.of(zaFajl(obj));

                                if (objekti.indexOf(obj) == 0) {
                                    objLines = Stream.concat(Stream.of("$" + klasa.getSimpleName()), objLines);
                                }

                                return objLines;
                            });

                    return redovi;
                })
                .collect(Collectors.toList());

        try {
            Files.write(Path.of(filePath), csvRedovi);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //Ucitavanje iz fajla
	 public  List<String> ucitajSveLinije(String filePath) {
	        try {
	            
	            return Files.lines(Paths.get(filePath)).collect(Collectors.toList());
	        } catch (IOException e) {
	            e.printStackTrace();
	        }

	        return List.of();  
	    }
	 //Konverzija stringova u objekte
	 public ArrayList<sortirajuci> izCsva(List<String> a){
		 String brojac = "";
		 Mapper mapper =  new Mapper();
		 ArrayList<sortirajuci> s= new ArrayList<>();
		 for(String linija: a) {
			 if(linija.startsWith("$")) {
				 brojac=linija;
				 continue;
			 }
			 if(brojac.equalsIgnoreCase("$Menadzer") || linija.startsWith("11")) {
				 
				s.add(mapper.kreirajMenadzera(linija));
			 }else if(brojac.equalsIgnoreCase("$Prodavac") && linija.startsWith("22")) {
				
				 s.add(mapper.kreirajProdavca(linija));
			 }else if(brojac.equalsIgnoreCase("$TehnickaRoba") && linija.split("\\|").length==8) {
				 
				 
				 s.add(mapper.kreirajTehnickuRobu(linija));
			 }else if(brojac.equalsIgnoreCase("$RobaKratkogTrajanja")){
				
				 s.add(mapper.kreirajRobuKratkogTrajanja(linija));
			 }
		 }
		return s;
	 }
	 //Ucitaj samo zaposlene
	 public ArrayList<Zaposleni> sviZaposleni(){
		 List<String> a = this.ucitajSveLinije("src/data/data.csv");
		 ArrayList<sortirajuci> b =this.izCsva(a);
		 ArrayList<Zaposleni> mi= new ArrayList<>();
		 for(sortirajuci s: b) {
			 if(s instanceof Zaposleni) {
				 mi.add((Zaposleni)s);
			 }
		 }
		 return mi;
	 }
	 //Ucitaj samo prodavce
	 public ArrayList<Prodavac> sviProdavci(){
		 List<String> a = this.ucitajSveLinije("src/data/data.csv");
		 ArrayList<sortirajuci> b =this.izCsva(a);
		 ArrayList<Prodavac> mi= new ArrayList<>();
		 for(sortirajuci s: b) {
			 if(s instanceof Prodavac) {
				 mi.add((Prodavac) s);
			 }
		 }
		 
		 return mi;
	}
	 //Ucitaj samo menadzere
	 public ArrayList<Menadzer> sviMenadzeri(){
		 List<String> a = this.ucitajSveLinije("src/data/data.csv");
		 ArrayList<sortirajuci> b =this.izCsva(a);
		 ArrayList<Menadzer> mi= new ArrayList<>();
		 for(sortirajuci s: b) {
			 if(s instanceof Menadzer) {
				 mi.add((Menadzer) s);
			 }
		 }
		 return mi;
	 }
	 //Ucitaj samo robu kratkog trajanja
	 public ArrayList<RobaKratkogTrajanja> svaRobaKT(){
		 List<String> a = this.ucitajSveLinije("src/data/data.csv");
		 ArrayList<sortirajuci> b =this.izCsva(a);
		 ArrayList<RobaKratkogTrajanja> mi= new ArrayList<>();
		 for(sortirajuci s: b) {
			 if(s instanceof RobaKratkogTrajanja) {
				 mi.add((RobaKratkogTrajanja) s);
			 }
		 }
		 return mi;
	 }
	 //Ucitaj samo tehnicku robu
	 public ArrayList<TehnickaRoba> svaRobaT(){
		 List<String> a = this.ucitajSveLinije("src/data/data.csv");
		 ArrayList<sortirajuci> b =this.izCsva(a);
		 ArrayList<TehnickaRoba> mi= new ArrayList<>();
		 for(sortirajuci s: b) {
			 if(s instanceof TehnickaRoba) {
				 mi.add((TehnickaRoba) s);
			 }
		 }
		 return mi;
	 }

	 
}
