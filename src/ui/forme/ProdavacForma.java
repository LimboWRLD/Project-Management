package ui.forme;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.text.DateFormat;

import interfejsi.sortirajuci;
import korisnici.Prodavac;
import korisnici.Zaposleni;

import radNadFajlovima.Pisac;
import ui.tabele.ProdavacTable;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;


public class ProdavacForma extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 742819872809875650L;
	//Komponente
	private String sifra;
	private ProdavacTable tabelaProdavaca;
	private JTable prodavacTable;
	private ArrayList<LocalDate>odsustva =  new ArrayList<>();
	private Pisac p = new Pisac();
	private ArrayList<sortirajuci> sviSvi  = p.izCsva(p.ucitajSveLinije("src/data/data.csv"));
	private JTextField imeField;
    private JTextField prezimeField;
    private JTextField visinaPlateField;
    private JTextField radnoMestoField;
    private JButton dodajUOdsustva;
    private JButton kreirajButton;
    private JButton	izmeniButton;
    private JButton obrisiButton;
    private JButton ukloniOdsustvo;
    private JComboBox<Object> datum;
    private JComboBox<Object> datumi;
    //Konstruktor
    public ProdavacForma() {
       
    }

    //Setovanje  liste
    public void setSviSvi(ArrayList<sortirajuci> a) {
    	this.sviSvi=a;
    }    

    //Inicijalizacija komponenti
	public void init() {
		imeField = new JTextField();
        prezimeField = new JTextField();
        visinaPlateField = new JTextField();
        radnoMestoField = new JTextField();
        // Buttons
        dodajUOdsustva = new JButton("Dodaj odsustvo");
        kreirajButton = new JButton("Kreiraj");
        izmeniButton = new JButton("Izmeni");
        obrisiButton = new JButton("Obrisi");
        ukloniOdsustvo = new JButton("Ukloni odsustvo");
        String[] opcijeDatuma = generisiDatume();
        datum = new JComboBox<>(opcijeDatuma);
        datumi = new JComboBox<>();
        JPanel formPanel = new JPanel(new GridLayout(10, 2));


       
        formPanel.add(new JLabel("Ime:"));
        formPanel.add(imeField);
        formPanel.add(new JLabel("Prezime:"));
        formPanel.add(prezimeField);
        formPanel.add(new JLabel("Visina Plate:"));
        formPanel.add(visinaPlateField);
        formPanel.add(new JLabel("Radno mesto:"));
        formPanel.add(radnoMestoField);
        formPanel.add(datum);

        dodajUOdsustva.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
             
                String izabraniDatum = (String) datum.getSelectedItem();
             
                if(!odsustva.contains(LocalDate.parse(izabraniDatum))) {
                odsustva.add(LocalDate.parse(izabraniDatum));
            }}
        });
        
        formPanel.add(dodajUOdsustva);
        formPanel.add(kreirajButton);
        ukloniOdsustvo.setVisible(false);
        
        izmeniButton.setVisible(false);
        obrisiButton.setVisible(true);
        formPanel.add(izmeniButton);
        
        datumi.setVisible(false);
        formPanel.add(datumi);
        formPanel.add(ukloniOdsustvo);
        add(formPanel, BorderLayout.NORTH);
        tabelaProdavaca = new ProdavacTable(p.sviProdavci());
   
        formPanel.add(obrisiButton);
        prodavacTable = new JTable(tabelaProdavaca);
        JScrollPane scrollPaneb = new JScrollPane(prodavacTable);
       
        add(scrollPaneb, BorderLayout.SOUTH);
        kreirajButton.addActionListener(e -> kreiraj());
        ukloniOdsustvo.addActionListener(e -> ukloniDatum());
        izmeniButton.addActionListener(e -> azuriraj());
        obrisiButton.addActionListener(e -> obrisi());
      
        prodavacTable.addMouseListener( new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) { 
                		
                	izmeniButton.setVisible(true);
                
                    izmeni();
                }
            }
        });
		this.setVisible(true);
	}
	//Brisanje izabranog reda
	public void obrisi() {
	    int selectedRow = prodavacTable.getSelectedRow();
	    //Ucitavanje najnovije liste
	    sviSvi = p.izCsva(p.ucitajSveLinije("src/data/data.csv"));
	    if (selectedRow != -1) {
	        
	        for (sortirajuci s: sviSvi) {
	        	
	         if(s instanceof Prodavac && ((Zaposleni) s).getSifraZaposlenog().equalsIgnoreCase((String) prodavacTable.getValueAt(selectedRow, 0))){
	        		sviSvi.remove(s);
	        		p.pisi(sviSvi, "src/data/data.csv");
	        		
	        		tabelaProdavaca.updateData(p.sviProdavci());
	        		break;
	        	}
	        }
	     } else {
	        JOptionPane.showMessageDialog(this, "Molimo odaberite red za brisanje.", "Greška", JOptionPane.ERROR_MESSAGE);
	    }
	}
	//Ciscenje polja
    private void ocistiPolja() {
        
        imeField.setText("");
        prezimeField.setText("");
        visinaPlateField.setText("");
        radnoMestoField.setText("");
    }
    //Generisanje datuma za narednih 30 dana
    private String[] generisiDatume() {
        
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String[] opcijeDatuma = new String[30];
        Calendar calendar = Calendar.getInstance();

        for (int i = 0; i < 30; i++) {
            opcijeDatuma[i] = dateFormat.format(calendar.getTime());
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }

        return opcijeDatuma;
    }
    //Uklanjanje odustva
    public void ukloniDatum() {	
    	LocalDate nekiDatum = (LocalDate) datumi.getSelectedItem();
    	if(datumi.getItemCount()>0) {
    	odsustva.remove(datumi.getSelectedItem());
    	datumi.removeItem(nekiDatum);
    	
    	
    }else{
    	JOptionPane.showMessageDialog(this, "Nema vise odsustva.", "Greška", JOptionPane.ERROR_MESSAGE);
    	datumi.setVisible(false);
    	}
    }
    //Izabrani u listi objekata
    private int izabraniUListi = 0;
    //Menjanje nekog objekata
    public void azuriraj() {
    	//Ucitavanje najnovije lise
    	sviSvi = p.izCsva(p.ucitajSveLinije("src/data/data.csv"));
    	int selectedRow = prodavacTable.getSelectedRow();
    	//Provera da li se pozicija izabranog promenila
    	for(sortirajuci a: sviSvi) {
    		
			if(a instanceof Zaposleni) 
			{
			if(((Zaposleni) a).getSifraZaposlenog().equalsIgnoreCase((String) prodavacTable.getValueAt(selectedRow, 0)) && ((Zaposleni) a).getIme().equalsIgnoreCase((String)prodavacTable.getValueAt(selectedRow, 1))) {
				
				izabraniUListi = sviSvi.indexOf(a);
			}}}
    	if(!(imeField.getText().trim()=="")) {
    		String ime = imeField.getText().trim();
            String prezime = prezimeField.getText().trim();
            String visinaPlateText = visinaPlateField.getText();

            if (ime.isEmpty() || prezime.isEmpty() || visinaPlateText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Molimo popunite sva polja.", "Greška", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                
                double visinaPlate = Double.parseDouble(visinaPlateText);
                sviSvi.set(izabraniUListi, new Prodavac(sifra, ime, prezime, visinaPlate,radnoMestoField.getText(), odsustva));
              
            	p.pisi(sviSvi, "src/data/data.csv");
            	
            	tabelaProdavaca.updateData(p.sviProdavci());
            	izmeniButton.setVisible(false);
            	ocistiPolja();
            	kreirajButton.setVisible(true);
            	obrisiButton.setVisible(true);
                odsustva.clear();
                ukloniOdsustvo.setVisible(false);
                datumi.setVisible(false);
                ocistiPolja();
                   
             	tabelaProdavaca.updateData(p.sviProdavci());

        	}	 catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Neispravan format za Visinu Plate.", "Greška", JOptionPane.ERROR_MESSAGE);}
            }
    
    }
    //Izbor za izmenu
    public void izmeni(){
    	datumi.removeAllItems();
    	//Ucitavanje nove liste
    	sviSvi = p.izCsva(p.ucitajSveLinije("src/data/data.csv"));
    	int selectedRow = prodavacTable.getSelectedRow();
    	//Pronalazenje izabranog
		for(sortirajuci a: sviSvi) {
		
			if(a instanceof Zaposleni) 
			{
			if(((Zaposleni) a).getSifraZaposlenog().equalsIgnoreCase((String) prodavacTable.getValueAt(selectedRow, 0)) && ((Zaposleni) a).getIme().equalsIgnoreCase((String)prodavacTable.getValueAt(selectedRow, 1))) {
				
				
				sifra=((((Zaposleni) a).getSifraZaposlenog()));
				imeField.setText(((Zaposleni) a).getIme());
				prezimeField.setText(((Zaposleni) a).getPrezime());
				visinaPlateField.setText(((Zaposleni) a).getVisinaPlate().toString());
				radnoMestoField.setText(((Prodavac) a).getRadnoMesto());
				izabraniUListi = sviSvi.indexOf(a);
				
				odsustva = ((Prodavac) a).getOdsustva();
				
				ukloniOdsustvo.setVisible(true);
				kreirajButton.setVisible(false);
			
				obrisiButton.setVisible(false);
				
				if(odsustva.size()==0){
					datumi.setVisible(false);
					ukloniOdsustvo.setVisible(false);
					
				}else {
					
				for(LocalDate z: odsustva) {
					if(z!=null) {
					datumi.addItem(z);
				}}
			}}
			
		}}
		if(datumi.getItemCount()==0) {
			datumi.setVisible(false);
			ukloniOdsustvo.setVisible(false);
		}else {
		kreirajButton.setVisible(false);
		
		obrisiButton.setVisible(false);
		datumi.setVisible(true);
		}
    }
    //Metoda za kreiranje
    private void kreiraj() {
    	
        String ime = imeField.getText().trim();
        String prezime = prezimeField.getText().trim();
        String visinaPlateText = visinaPlateField.getText();
        //Provera polja
        if (ime.isEmpty() || prezime.isEmpty() || visinaPlateText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Molimo popunite sva polja.", "Greška", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            
            double visinaPlate = Double.parseDouble(visinaPlateText);

            	
                sviSvi.add(new Prodavac(new Prodavac().generisiSifruZaposlenog(), ime, prezime, visinaPlate,radnoMestoField.getText(), odsustva));
                p.pisi(sviSvi, "src/data/data.csv");
                tabelaProdavaca.updateData(p.sviProdavci());
           
                sviSvi = p.izCsva(p.ucitajSveLinije("src/data/data.csv"));
                odsustva.clear();
                ocistiPolja();

               	tabelaProdavaca.updateData(p.sviProdavci());

      

    	}	 catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Neispravan format za Visinu Plate.", "Greška", JOptionPane.ERROR_MESSAGE);}
        }
}
