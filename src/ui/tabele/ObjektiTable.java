package ui.tabele;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import interfejsi.sortirajuci;
import korisnici.Menadzer;
import korisnici.Zaposleni;
import korisnici.Prodavac;
import sredstva.Roba;

public class ObjektiTable extends AbstractTableModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3569708908210957336L;
	//Imena kolona
	private String[]imenaKolona= {"Klasa", "Ime/Naziv", "Prezime/Model.","Plata/Cena", "RadnoMesto/ZemljaPorekla"};
	private ArrayList<sortirajuci>s = new ArrayList<>();
	//Konstruktor
	public ObjektiTable(ArrayList<sortirajuci>s) {
		this.s=s;
	}
	//Azuriranje tabele
    public void updateData(ArrayList<sortirajuci> newData) {
        this.s = newData;
        fireTableDataChanged();
    }
	@Override
	public int getRowCount() {
		
		return s.size();
	}
	  @Override
	    public String getColumnName(int columnIndex) {
	        return imenaKolona[columnIndex];
	    }
	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return imenaKolona.length;
	}
	//Dobavljenje vrednosti u polju
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		sortirajuci izabrani = s.get(rowIndex);
        switch (columnIndex) {
        case 0:
            return izabrani.getClass().getSimpleName();
        case 1:
        	if(izabrani instanceof Zaposleni) {
        		return ((Zaposleni) izabrani).getIme();
        	}else {
        		return ((Roba)izabrani).getNaziv();
        	}
        case 2:
        	if(izabrani instanceof Zaposleni) {
        		return ((Zaposleni) izabrani).getPrezime();
        	}else {
        		return ((Roba)izabrani).getModel();
        	}
        case 3:
        	if(izabrani instanceof Zaposleni) {
        		return ((Zaposleni) izabrani).getVisinaPlate();
        	}else {
        		return ((Roba)izabrani).getCena();
        	}
        case 4:
        	if(izabrani instanceof Zaposleni) {
        		if(izabrani instanceof Menadzer) {
        			return "nema";
        		}
        		return ((Prodavac) izabrani).getRadnoMesto();
        	}else {
        		return ((Roba)izabrani).getZemljaPorekla();
        	}
        default:
            return null;
    }

	}
    //Metoda za uklanjanje reda
    public void ukloniRed(int row) {
    	this.fireTableRowsDeleted(row, row);
    	
    }
}
