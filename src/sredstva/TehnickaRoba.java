package sredstva;

public class TehnickaRoba extends Roba {
	
	private Dimenzije dimenzije;
	private Double nominalnaSnaga;
	private Double radniNapon;
	//Konstruktor bez parametara
	public TehnickaRoba() {
		super();
		
	}
	//Konstruktor sa parametrima
	public TehnickaRoba(String naziv, Double cena, String jedinicaMere, String zemljaPorekla, String model, Dimenzije dimenzije, Double nominalnaSnaga, Double radniNapon) {
		super(naziv, cena, jedinicaMere, zemljaPorekla, model);
		this.dimenzije=dimenzije;
		this.nominalnaSnaga=nominalnaSnaga;
		this.radniNapon=radniNapon;
	}
	//Geteri i seteri
	public Dimenzije getDimenzije() {
		return dimenzije;
	}
	public void setDimenzije(Dimenzije dimenzije) {
		this.dimenzije = dimenzije;
	}
	public Double getNominalnaSnaga() {
		return nominalnaSnaga;
	}
	public void setNominalnaSnaga(Double nominalnaSnaga) {
		this.nominalnaSnaga = nominalnaSnaga;
	}
	public Double getRadniNapon() {
		return radniNapon;
	}
	public void setRadniNapon(Double radniNapon) {
		this.radniNapon = radniNapon;
	}
	//Konverzija u string
	@Override
	public String toString() {
		return "TehnickaRoba [dimenzije=" + dimenzije + ", nominalnaSnaga=" + nominalnaSnaga + ", radniNapon="
				+ radniNapon + "]";
	}
	//Priprema za upis u fajl
	@Override
	public String toCsv() {
		
		return super.toCsv()+"|"+this.getDimenzije().toCsv()+"|"+this.getNominalnaSnaga()+"|"+this.getRadniNapon();
	}
	//Red u listi
	@Override
	public int redSortiranja() {
		
		return 3;
	}
	
	
}
