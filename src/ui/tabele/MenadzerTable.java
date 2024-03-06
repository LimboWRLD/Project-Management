package ui.tabele;

import javax.swing.table.AbstractTableModel;

import korisnici.Menadzer;
import korisnici.Zaposleni;
import korisnici.Prodavac;
import java.util.ArrayList;


public class MenadzerTable extends AbstractTableModel{
	private static final long serialVersionUID = 1L;
    // Definisanje imena kolona
    private String[] columnNames = {"Klasa","Sifra Zaposlenog", "Ime", "Prezime", "Visina Plate","Departman/Odsustva"};

    // Lista koja se pise u tabeli
    private ArrayList<Zaposleni> menadzerList;

    // Konstruktor
    public MenadzerTable(ArrayList<Zaposleni> menadzerList) {
        this.menadzerList = menadzerList;
    }

   
    @Override
    public int getRowCount() {
        return menadzerList.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public String getColumnName(int columnIndex) {
        return columnNames[columnIndex];
    }
    //Azuriranje podataka
    public void updateData(ArrayList<Zaposleni> newData) {
        this.menadzerList = newData;
        fireTableDataChanged();
    }
    //Dobavljanje vrednosti na polju
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Zaposleni menadzer = menadzerList.get(rowIndex);
        switch (columnIndex) {
        	case 0:
        		return menadzer.getClass().getSimpleName();
            case 1:
                return menadzer.getSifraZaposlenog();
            case 2:
                return menadzer.getIme();
            case 3:
                return menadzer.getPrezime();
            case 4:
                return menadzer.getVisinaPlate();
            case 5:
            	if(menadzer instanceof Menadzer) {
            		return ((Menadzer) menadzer).getDepartman().toCsv();
            	}else {
            		if(((Prodavac) menadzer).getOdsustva().size()==0){
            			return "nema odsustva";
            		}
            		return ((Prodavac) menadzer).getOdsustva().toString();
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
