package ui.tabele;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;
import sredstva.TehnickaRoba;

public class TehnickaRobaTable extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7595651791374323722L;
	//Imena kolona
	private String[] imenaKolona = {"Naziv proizvoda", "Cena u dinarima", "Jedinica mere", "Zemlja porekla", "Model ","Dimenzije", "Nominalna Snaga", "Radni napon"};
	
	private ArrayList<TehnickaRoba> listaRobe;
	//Konstruktor
	public TehnickaRobaTable(ArrayList<TehnickaRoba> listaRobe) {
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
	//Azuriranje podataka
    public void updateData(ArrayList<TehnickaRoba> newData) {
        this.listaRobe = newData;
        fireTableDataChanged();
    }
    //Dobavljanje vrednosti polja
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		TehnickaRoba roba = listaRobe.get(rowIndex);
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
            	return roba.getDimenzije().toCsv();
            case 6:
            	return roba.getNominalnaSnaga();
            case 7:
            	return roba.getRadniNapon();
            default:
                return null;
        }
	}
    //Metoda za uklanjanje reda
    public void ukloniRed(int row) {
    	this.fireTableRowsDeleted(row, row);
    	
    }
}
