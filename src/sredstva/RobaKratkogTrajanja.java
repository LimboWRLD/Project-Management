package sredstva;

import java.time.LocalDate;

public class RobaKratkogTrajanja extends Roba {
	
	private LocalDate rokTrajanja;
	private String upustvoZaSkladistenje;
	//Konstruktor bez parametara
	public RobaKratkogTrajanja() {
		super();
	
	}
	//Kostruktor sa parametrima
	public RobaKratkogTrajanja(String naziv, Double cena, String jedinicaMere, String zemljaPorekla, String model, LocalDate rokTrajanja, String uputstvoZaSkladistenje) {
		super(naziv, cena, jedinicaMere, zemljaPorekla, model);
		this.rokTrajanja=rokTrajanja;
		this.upustvoZaSkladistenje=uputstvoZaSkladistenje;
	}
	//Geteri i seteri
	public LocalDate getRokTrajanja() {
		return rokTrajanja;
	}
	public void setRokTrajanja(LocalDate rokTrajanja) {
		this.rokTrajanja = rokTrajanja;
	}
	public String getUpustvoZaSkladistenje() {
		return upustvoZaSkladistenje;
	}
	public void setUpustvoZaSkladistenje(String upustvoZaSkladistenje) {
		this.upustvoZaSkladistenje = upustvoZaSkladistenje;
	}
	//Konverzija u string
	@Override
	public String toString() {
		return "RobaKratkogTrajanja [rokTrajanja=" + rokTrajanja + ", upustvoZaSkladistenje=" + upustvoZaSkladistenje
				+ "]" +super.toString();
	}
	//Priprema za upis u fajl
	@Override
	public String toCsv() {
		// TODO Auto-generated method stub
		return super.toCsv()+"|"+this.getRokTrajanja().toString()+"|"+this.getUpustvoZaSkladistenje();
	}
	//Red u listi
	@Override
	public int redSortiranja() {
		
		return 4;
	}
	
}
