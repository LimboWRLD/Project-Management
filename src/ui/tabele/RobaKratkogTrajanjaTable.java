package ui.tabele;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;


import sredstva.RobaKratkogTrajanja;

public class RobaKratkogTrajanjaTable extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7595651791374323722L;
	//Imena kolona
	private String[] imenaKolona = {"Naziv proizvoda", "Cena u dinarima", "Jedinica mere", "Zemlja porekla", "Model ","Rok trajanja", "Uputstvo za skladistenje"};
	
	private ArrayList<RobaKratkogTrajanja> listaRobe;
	//Konstruktor
	public RobaKratkogTrajanjaTable(ArrayList<RobaKratkogTrajanja> listaRobe) {
		this.listaRobe=listaRobe;
	}
	@Override
	public int getRowCount() {
		return listaRobe.size();
	}
	public String getColumnName(int columnIndex) {
        return imenaKolona[columnIndex];
    }
	@Override
	public int getColumnCount() {
		return imenaKolona.length;
	}
	//Azuriranje Tabele
    public void updateData(ArrayList<RobaKratkogTrajanja> newData) {
        this.listaRobe = newData;
        fireTableDataChanged();
    }
    //Dobavljanje vrednosti u polju
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
        RobaKratkogTrajanja roba = listaRobe.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return roba.getNaziv();
            case 1:
                return roba.getCena();
            case 2:
                return roba.getJedinicaMere();
            case 3:
                return roba.getZemljaPorekla();
            case 4:
            	return roba.getModel();
            case 5:
            	return roba.getRokTrajanja();
            case 6:
            	return roba.getUpustvoZaSkladistenje();
            default:
                return null;
        }
	}
    //Metoda za uklanjanje reda
    public void ukloniRed(int row) {
    	this.fireTableRowsDeleted(row, row);
    	
    }
}
