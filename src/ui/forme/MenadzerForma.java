package ui.forme;

import javax.swing.*;
import interfejsi.sortirajuci;
import korisnici.Menadzer;
import korisnici.Prodavac;
import korisnici.Zaposleni;
import ljudskResursi.Departman;
import radNadFajlovima.Pisac;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import ui.tabele.MenadzerTable;

public class MenadzerForma extends JPanel {
	//Komponente
    private MenadzerTable tabelaMenadzera;
    public MenadzerTable getTabela() {
    	return tabelaMenadzera;
    }
    public void setSviSvi(ArrayList<sortirajuci> a) {
    	this.sviSvi=a;
    }
    private JTable menadzerTable;
    private Menadzer m = new Menadzer();
    private ArrayList<Zaposleni> sviZaposleni = new ArrayList<>();
    private Departman d = new Departman(sviZaposleni);
    private Pisac p = new Pisac();
    private ArrayList<sortirajuci> sviSvi = p.izCsva(p.ucitajSveLinije("src/data/data.csv"));
    private static final long serialVersionUID = 1L;

    private JTextField imeField;
    private JTextField prezimeField;
    private JTextField visinaPlateField;
    private JButton dodajUDepartmanButton;
    private JButton kreirajButton;
    private JButton izmeniButton;
    private JButton obrisiButton;
    private JButton ukloniDepartman;
    private JComboBox<Object> zaposleni;
    private int izabraniUlisti = 0;
    //Kreiranje forme
    public MenadzerForma() {
        
    }
    //Inicijalizacija koponenti
    public void init() {
        imeField = new JTextField();
        prezimeField = new JTextField();
        visinaPlateField = new JTextField();
        zaposleni = new JComboBox<>();

        dodajUDepartmanButton = new JButton("Dodaj u departman");
        kreirajButton = new JButton("Kreiraj");
        izmeniButton = new JButton("Izmeni");
        obrisiButton = new JButton("Obrisi");
        ukloniDepartman = new JButton("Ukloni iz departmana");

        setLayout(new BorderLayout());
        JPanel formPanel = new JPanel(new GridLayout(8, 2));

        formPanel.add(new JLabel("Ime:"));
        formPanel.add(imeField);
        formPanel.add(new JLabel("Prezime:"));
        formPanel.add(prezimeField);
        formPanel.add(new JLabel("Visina Plate:"));
        formPanel.add(visinaPlateField);
        formPanel.add(dodajUDepartmanButton);
        ukloniDepartman.setVisible(false);
        formPanel.add(ukloniDepartman);
        formPanel.add(zaposleni).setVisible(false);
        formPanel.add(kreirajButton);
        formPanel.add(izmeniButton).setVisible(false);
        formPanel.add(obrisiButton);

        add(formPanel, BorderLayout.NORTH);

        tabelaMenadzera = new MenadzerTable(new Pisac().sviZaposleni());
        menadzerTable = new JTable(tabelaMenadzera);
        JScrollPane scrollPane = new JScrollPane(menadzerTable);
        add(scrollPane, BorderLayout.CENTER);
        //Funkcionalnost dugmica i tabele
        obrisiButton.addActionListener(e -> obrisi());
        ukloniDepartman.addActionListener(e -> ukloniIzDepartmana());
        dodajUDepartmanButton.addActionListener(e -> dodajUDepartman());
        kreirajButton.addActionListener(e -> kreiraj());
        izmeniButton.addActionListener(e -> azuriraj(izabraniUlisti));

        menadzerTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    izmeniButton.setVisible(true);
                    zaposleni.removeAllItems();
                    izmeni();
                }
            }
        });
    }
    //Metoda za kreiranje
    private void kreiraj() {
        String ime = imeField.getText().trim();
        String prezime = prezimeField.getText().trim();
        String visinaPlateText = visinaPlateField.getText();
        //Ucitavanje najnovije verzije liste iz fajla
        sviSvi =p.izCsva(p.ucitajSveLinije("src/data/data.csv"));
        //Provera da li su polja prazna
        if (ime.isEmpty() || prezime.isEmpty() || visinaPlateText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Molimo popunite sva polja.", "Greška", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
        	//Kreiranje novog menadzera ako je plata broj
            double visinaPlate = Double.parseDouble(visinaPlateText);

            sviSvi.add(new Menadzer(m.generisiSifruZaposlenog(), ime, prezime, visinaPlate, d));
            p.pisi(sviSvi, "src/data/data.csv");
            sviSvi =p.izCsva(p.ucitajSveLinije("src/data/data.csv"));
            d.getSviZaposleni().clear();
            
            ocistiPolja();
            tabelaMenadzera.updateData(p.sviZaposleni());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Neispravan format za Visinu Plate.", "Greška", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void izmeni() {
        int selectedRow = menadzerTable.getSelectedRow();
        sviSvi =p.izCsva(p.ucitajSveLinije("src/data/data.csv"));
        zaposleni.removeAllItems();
        for (sortirajuci a : sviSvi) {
            if (a instanceof Zaposleni) {
            	
                if (((Zaposleni) a).getSifraZaposlenog().equalsIgnoreCase((String) menadzerTable.getValueAt(selectedRow, 1)) && ((Zaposleni) a).getSifraZaposlenog().startsWith("11")) {
                    
                    imeField.setText(((Zaposleni) a).getIme());
                    prezimeField.setText(((Zaposleni) a).getPrezime());
                    visinaPlateField.setText(((Zaposleni) a).getVisinaPlate().toString());
                    izabraniUlisti = sviSvi.indexOf(a);
                    m=(Menadzer) a;
                    d = ((Menadzer) a).getDepartman();
                    
                    if (d == null) {

                    } else {
                        for (Zaposleni z : d.getSviZaposleni()) {
                            if (z != null) {
                                zaposleni.addItem(z.getSifraZaposlenog());
                                
                            }else {
                            	continue;
                            }
                        }
                        
                        if (!(zaposleni.getItemCount() == 0)) {
                            zaposleni.setVisible(true);
                            ukloniDepartman.setVisible(true);
                        } else {
                            zaposleni.setVisible(false);
                            ukloniDepartman.setVisible(false);
                        }
                        
                        kreirajButton.setVisible(false);
                        obrisiButton.setVisible(false);
                    }
                }
            }
        }
    }

    private void azuriraj(int izabrani) {
       
        
        	
        	 String ime = imeField.getText().trim();
             String prezime = prezimeField.getText().trim();
             String visinaPlateText = visinaPlateField.getText();

             if (ime.isEmpty() || prezime.isEmpty() || visinaPlateText.isEmpty()) {
                 JOptionPane.showMessageDialog(this, "Molimo popunite sva polja.", "Greška", JOptionPane.ERROR_MESSAGE);
                 return;
             }

             try {
                 double visinaPlate = Double.parseDouble(visinaPlateText);
                 sviSvi =p.izCsva(p.ucitajSveLinije("src/data/data.csv"));
                 for (sortirajuci a : sviSvi) {
                     if (a instanceof Zaposleni) {
                     	
                         if (((Zaposleni) a).getSifraZaposlenog().equalsIgnoreCase(m.getSifraZaposlenog()) && ((Zaposleni) a).getSifraZaposlenog().startsWith("11")) {

                             izabraniUlisti = sviSvi.indexOf(a);
                         }}}
                 sviSvi.set(izabraniUlisti, new Menadzer(m.getSifraZaposlenog(), imeField.getText().trim(), prezimeField.getText().trim(), visinaPlate, d));
                 p.pisi(sviSvi, "src/data/data.csv");
                 tabelaMenadzera.updateData(p.sviZaposleni());
                 zaposleni.setVisible(false);
                 ukloniDepartman.setVisible(false);
                 izmeniButton.setVisible(false);
                 kreirajButton.setVisible(true);
                 obrisiButton.setVisible(true);
                 d.getSviZaposleni().clear();
                 ocistiPolja();

             } catch (NumberFormatException e) {
            	 //Hvatanje greske
                 JOptionPane.showMessageDialog(this, "Neispravan format za Visinu Plate.", "Greška", JOptionPane.ERROR_MESSAGE);
             }

        }
    
    //Dodavanje u departman
    private void dodajUDepartman() {
  
        int selectedRow = menadzerTable.getSelectedRow();
        
        if(selectedRow!=-1) {
        for (Zaposleni a : p.sviZaposleni()) {
        	
        	//Pronalazenje izabranog iz tabele u listi
            if (a.getSifraZaposlenog().equalsIgnoreCase((String) menadzerTable.getValueAt(selectedRow, 1))) {
            	//Provera da li je u pitanju kreiranje novo menadzera
            	if(m.getIme()==null) {
            		//Provera da li se taj zaposleni vec nalazi u departmanu
            		if(!d.getSviZaposleni().contains(a)) {
            			d.dodajZaposlenog(a);
            		}else {
            			break;
            		}
            	}
            	//Provera da li menadzer vec sadrzi tog zaposlenog i da li je zaposleni ustvari menadzer koji dodaje sebi u departman
        	else if(!(m.getDepartman().toCsv().contains(a.getSifraZaposlenog())) && !(m.getSifraZaposlenog().equalsIgnoreCase(a.getSifraZaposlenog()))) {
        		//Provera da li taj menadzer koji se dodaje sadrzi menadzera koji vrsi dodavanje u svom departmanu
        		if(a instanceof Menadzer  &&  (!((Menadzer) a).getDepartman().toCsv().contains(m.getSifraZaposlenog()))){
            		
            			d.dodajZaposlenog(a);}
        		//Ako je prodavaca dodaj ga posto je prosao vec gore datu proveru
            	else if(a instanceof Prodavac ){
            		 	d.dodajZaposlenog(a);
            	
                }
        			}
        	
               
            }}
            
           
        	}}
        
    
    //Metoda za uklanjanje
    private void ukloniIzDepartmana() {
    	//Ako ima nekog u departamanu
    	if(zaposleni.getItemCount()>0) {
        String zaposleniZaUklanjanje = (String) zaposleni.getSelectedItem();
        //Uzmi izabrano u comboboxu
        for (Zaposleni z : d.getSviZaposleni()) {
        	//Ako je to taj zaposleni, ukloni ga
            if (z.getSifraZaposlenog().equalsIgnoreCase(zaposleniZaUklanjanje)) {
                d.ukloniZaposlenog(z);
                zaposleni.removeItem(zaposleniZaUklanjanje);
                break;
            }
        }}else {
        	JOptionPane.showMessageDialog(this, "Nema vise zaposlenih u departmanu", "Greška", JOptionPane.ERROR_MESSAGE);
        }
    }
    //Ciscenje input polja
    private void ocistiPolja() {
        imeField.setText("");
        prezimeField.setText("");
        visinaPlateField.setText("");
    }
    //Brisanje izabranog reda
    private void obrisi() {
        int izabraniRed = menadzerTable.getSelectedRow();
        //Ucitavanje najnovije liste
        sviSvi =p.izCsva(p.ucitajSveLinije("src/data/data.csv"));
        //Ako izabrani nije -1
        if (izabraniRed != -1) {
            for (sortirajuci a : sviSvi) {
                if (a instanceof Zaposleni && ((Zaposleni) a).getSifraZaposlenog().equalsIgnoreCase((String) menadzerTable.getValueAt(izabraniRed, 1)) && ((Zaposleni) a).getSifraZaposlenog().startsWith("11")) {
                    sviSvi.remove(a);
                    p.pisi(sviSvi, "src/data/data.csv");
                    p.pisi(p.izCsva(p.ucitajSveLinije("src/data/data.csv")), "src/data/data.csv");
                    sviSvi =p.izCsva(p.ucitajSveLinije("src/data/data.csv"));
                    sviZaposleni = p.sviZaposleni();
                  
                    tabelaMenadzera.updateData(sviZaposleni);
                    ocistiPolja();
                    break;
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Molimo odaberite red za brisanje.", "Greška", JOptionPane.ERROR_MESSAGE);
        }
    }
}

