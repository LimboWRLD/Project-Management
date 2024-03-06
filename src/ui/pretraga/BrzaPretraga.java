package ui.pretraga;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import radNadFajlovima.Pisac;
import ui.tabele.ObjektiTable;
import interfejsi.sortirajuci;
import korisnici.Zaposleni;
import sredstva.Roba;

public class BrzaPretraga extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2842294456961969341L;
	//Komponente
	private JButton pretrazi;
	private JTextField poljeZaKucanje;
	private ObjektiTable tabelaObjekata;
	private JTable objektiTable;
	private ArrayList<sortirajuci>s = new ArrayList<>();
	//Inicijalizacija
	public void init() {
		pretrazi = new JButton("Pretrazi");
		poljeZaKucanje = new JTextField();
		tabelaObjekata = new ObjektiTable(s);
		objektiTable =  new JTable(tabelaObjekata);
		objektiTable.setVisible(false);
		JPanel panela = new JPanel(new GridLayout(3 ,2));
		panela.add(poljeZaKucanje);
		panela.add(pretrazi);
		//panela.add(objektiTable);
		JScrollPane scroll = new JScrollPane(objektiTable);
		add(panela,BorderLayout.NORTH);
		add(scroll, BorderLayout.SOUTH);
		pretrazi.addActionListener(e -> pretrazi());
	}
	public void pretrazi() {
		String pojam = poljeZaKucanje.getText();
		s.clear();
		if(!pojam.isEmpty()) {
		//Pretraga kroz najnoviju verziju liste
		for(sortirajuci izabrani : new Pisac().izCsva(new Pisac().ucitajSveLinije("src/data/data.csv"))) {
			if(izabrani instanceof Zaposleni) {
				//Ako je zaposleni prodji kroz sve atribute i poredi ih
				for(String element :  ((Zaposleni) izabrani).toCsv().split("\\|")) {
					if(element.contains(pojam) || element.equalsIgnoreCase(pojam)) {
						
						if(!s.contains(izabrani)) {
							s.add(izabrani);
							
						}
						continue;
					}
				}
			}else {
				//Ako je roba uradi isto ali za atribute robe
			for(String element : ((Roba) izabrani).toCsv().split("\\|")) {
				
			if(element.contains(pojam)|| element.equalsIgnoreCase(pojam)) {
				if(!s.contains(izabrani)) {
				s.add(izabrani);
				
			}continue;
				}
		}
		}}if(s.size()==0) {
			 JOptionPane.showMessageDialog(this, "Ne postoji pojam koji trazite.", "Greška", JOptionPane.ERROR_MESSAGE);
		}else {
			tabelaObjekata.updateData(s);
			objektiTable.setVisible(true);
		}}
		}
	
}
