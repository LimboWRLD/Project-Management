package ui.tabele;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import korisnici.Prodavac;
import korisnici.Zaposleni;

public class ProdavacTable extends AbstractTableModel{

	

	/**
	 * 
	 */
	private static final long serialVersionUID = -1675667285729689044L;
	//Imena kolona
	private String[] imenaKolona = {"Sifra Zaposlenog", "Ime", "Prezime", "Visina Plate", "Radno mesto"};	
	
	private ArrayList<Prodavac> listaProdavaca;
	//Konstruktor
	public ProdavacTable(ArrayList<Prodavac>listaProdavaca) {
		this.listaProdavaca=listaProdavaca;
	}
	@Override
	public int getRowCount() {
		return listaProdavaca.size();
	}

	@Override
	public int getColumnCount() {
		return imenaKolona.length;
	}
	public String getColumnName(int columnIndex) {
        return imenaKolona[columnIndex];
    }
	//Azuriranje tabele
    public void updateData(ArrayList<Prodavac> newData) {
        this.listaProdavaca = newData;
        fireTableDataChanged();
    }
    //Dobavljanje vrednosti u polju
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Zaposleni menadzer = listaProdavaca.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return menadzer.getSifraZaposlenog();
            case 1:
                return menadzer.getIme();
            case 2:
                return menadzer.getPrezime();
            case 3:
                return menadzer.getVisinaPlate();
            case 4:
            	return ((Prodavac) menadzer).getRadnoMesto();
            default:
                return null;
        }
    }
    //Metoda za uklanjanje reda
    public void ukloniRed(int row) {
    	this.fireTableRowsDeleted(row, row);
    	
    }
}
