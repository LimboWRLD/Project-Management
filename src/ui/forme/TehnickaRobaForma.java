package ui.forme;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import interfejsi.sortirajuci;

import radNadFajlovima.Pisac;
import sredstva.Dimenzije;
import sredstva.Roba;
import sredstva.TehnickaRoba;
import ui.tabele.TehnickaRobaTable;

public class TehnickaRobaForma extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7099872780309983291L;
	//Komponente
	private JTextField nazivField;
    private JTextField cenaField;
    private JTextField modelField;
    private JTextField jedinicaMereField;
    private JTextField sirinaField;
    private JTextField visinaField;
    private JTextField duzinaField;
    private JTextField snagaField;
    private JTextField naponField;
    private JComboBox<String> zemljaPoreklaComboBox;
    private JButton kreirajButton;
    private JButton izmeniButton;
    private JButton obrisi;
    private TehnickaRobaTable tabelaTehnickeRobe;
    private JTable robaTable;
    private TehnickaRoba t;
    private ArrayList<sortirajuci> robaList =new Pisac().izCsva(new Pisac().ucitajSveLinije("src/data/data.csv")); // Example data storage

    //Geter i seter tabele
    public TehnickaRobaTable getTabela() {
    	return tabelaTehnickeRobe;
    }
    public void setRobaList(ArrayList<sortirajuci> a) {
    	this.robaList=a;
    }
   
   //Konstruktor
    public TehnickaRobaForma() {
		super();
	}


    //Inicijalizacija komponenata
    public void init() {
    	nazivField = new JTextField();
        cenaField = new JTextField();
        visinaField = new JTextField();
        duzinaField = new JTextField();
        sirinaField = new JTextField();
        modelField = new JTextField();
        snagaField = new JTextField();
        naponField = new JTextField();
        jedinicaMereField = new JTextField();
        obrisi = new JButton("Obrisi");
        izmeniButton =  new JButton("Izmeni");
        kreirajButton = new JButton("Kreiraj");
        String[] zemlje = Locale.getISOCountries();
        Arrays.sort(zemlje);
        tabelaTehnickeRobe = new TehnickaRobaTable(new Pisac().svaRobaT());
        robaTable = new JTable(tabelaTehnickeRobe);
        String[] imenaZemalja = new String[zemlje.length];
        for (int i = 0; i < zemlje.length; i++) {
            Locale locale = new Locale("", zemlje[i]);
            imenaZemalja[i] = locale.getDisplayCountry();
        }
        
        
        zemljaPoreklaComboBox = new JComboBox<>(imenaZemalja);
        
        JPanel inputPanel = new JPanel(new GridLayout(24, 2));
        inputPanel.add(new JLabel("Naziv:"));
        inputPanel.add(nazivField);
        inputPanel.add(new JLabel("Cena:"));
        inputPanel.add(cenaField);
        inputPanel.add(new JLabel("Jedinica mere:"));
        inputPanel.add(jedinicaMereField);
        inputPanel.add(new JLabel("Model :"));
        inputPanel.add(modelField);
        inputPanel.add(zemljaPoreklaComboBox); 
        inputPanel.add(new JLabel("Sirina:"));
        inputPanel.add(sirinaField);
        inputPanel.add(new JLabel("Visina:"));
        inputPanel.add(visinaField);
        inputPanel.add(new JLabel("Duzina:"));
        inputPanel.add(duzinaField);
        inputPanel.add(new JLabel("Nominalna snaga:"));
        inputPanel.add(snagaField);
        inputPanel.add(new JLabel("Radni napon:"));
        inputPanel.add(naponField);
        inputPanel.add(kreirajButton);
        inputPanel.add(obrisi);
        izmeniButton.setVisible(false);
        inputPanel.add(izmeniButton);
        robaTable.addMouseListener( new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) { 
                	
                	izmeniButton.setVisible(true);
                	kreirajButton.setVisible(false);
                    izmeni();
                }
            }
        });
        kreirajButton.addActionListener(e -> kreiraj());
        obrisi.addActionListener(e -> obrisi());
        izmeniButton.addActionListener(e -> azuriraj(izabrani));
        //add(buttonPanel, BorderLayout.SOUTH);
        add(inputPanel, BorderLayout.NORTH);
        JScrollPane scrollPane = new JScrollPane(robaTable);
        add(scrollPane,  BorderLayout.CENTER);
        
        
    }
    private int izabrani = 0;
    //Azuriranje izabranog
    public void azuriraj(int izabrani) {
    	robaList = new Pisac().izCsva(new Pisac().ucitajSveLinije("src/data/data.csv"));
    	//Provera da li je izabrani promenio poziciju u listi
    	for(sortirajuci a:robaList) {
    		if(a instanceof TehnickaRoba) {
    			if(((Roba) a).getNaziv().equals(t.getNaziv()) && ((TehnickaRoba) a).getJedinicaMere().equals(t.getJedinicaMere())) {
    				izabrani = robaList.indexOf(a);
    			}}}
    	if(!(nazivField.getText().trim()=="")) {
    		try {
                String ime = nazivField.getText();
                String cenaaa = cenaField.getText();
                String visinaa = visinaField.getText();
                String sirinaa = sirinaField.getText();
                String duzinaa = duzinaField.getText();
                String snagaa = snagaField.getText();
                String naponn = naponField.getText();
                String jedinicaMere = jedinicaMereField.getText();
                String model = modelField.getText();

                if (ime.isEmpty() || jedinicaMere.isEmpty() || model.isEmpty() || visinaa.isEmpty() || sirinaa.isEmpty()
                        || duzinaa.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Molimo popunite sva polja.", "Greška", JOptionPane.ERROR_MESSAGE);
                    return;
                }
          
                double cena = proveraDubleVrednosti(cenaaa, "Pogresan cena format. Molimo ukucajte validan broj.");
                double visina = proveraDubleVrednosti(visinaa, "Pogresan visina format. Molimo ukucajte validan broj.");
                double sirina = proveraDubleVrednosti(sirinaa, "Pogresan sirina format. Molimo ukucajte validan broj.");
                double duzina = proveraDubleVrednosti(duzinaa, "Pogresan duzina format. Molimo ukucajte validan broj.");
                double snaga = proveraDubleVrednosti(snagaa, "Pogresan snaga format. Molimo ukucajte validan broj.");
                double napon = proveraDubleVrednosti(naponn, "Pogresan napon format. Molimo ukucajte validan broj.");
            	robaList.set(izabrani, new TehnickaRoba(nazivField.getText(), cena, jedinicaMereField.getText(), zemljaPoreklaComboBox.getSelectedItem().toString(), modelField.getText(), new Dimenzije(sirina, visina, duzina), snaga, napon));
            	new Pisac().pisi(robaList, "src/data/data.csv");
            	tabelaTehnickeRobe.updateData(new Pisac().svaRobaT());
            	izmeniButton.setVisible(false);
            	kreirajButton.setVisible(true);
                nazivField.setText("");
                cenaField.setText("");
                jedinicaMereField.setText("");
                visinaField.setText("");
                modelField.setText("");
                duzinaField.setText("");
                sirinaField.setText("");
                snagaField.setText("");
                naponField.setText("");
                obrisi.setVisible(true);
            
            } catch (NumberFormatException ex) {
                
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Pogresan Input", JOptionPane.ERROR_MESSAGE);
            }

    }}
    public void obrisi() {
        int izabraniRed = robaTable.getSelectedRow();
        //Ucitavanje najnovije verzije liste
        robaList=new Pisac().izCsva(new Pisac().ucitajSveLinije("src/data/data.csv"));
        if (izabraniRed != -1) {
        	for(sortirajuci a:robaList) {
        		if(a instanceof TehnickaRoba) {
        			//Provera da li je to taj izabrani
        		if(((Roba) a).getNaziv().equals(robaTable.getValueAt(izabraniRed, 0)) && ((TehnickaRoba) a).getJedinicaMere().equals(robaTable.getValueAt(izabraniRed, 2))) {
        				robaList.remove(a);
        				new Pisac().pisi(robaList, "src/data/data.csv");
        				
        				tabelaTehnickeRobe.updateData(new Pisac().svaRobaT());
        				  nazivField.setText("");
        			        cenaField.setText("");
        			        visinaField.setText("");
        			        modelField.setText("");
        			        duzinaField.setText("");
        			        jedinicaMereField.setText("");
        			        sirinaField.setText("");
        			        snagaField.setText("");
        			        naponField.setText("");
        				break;
        }}}} else {
            JOptionPane.showMessageDialog(this, "Molimo odaberite red za brisanje.", "Greška", JOptionPane.ERROR_MESSAGE);
        }
    }
    private void izmeni() {
    	//dohvatanje izabranog
    	int izbraniRed =  robaTable.getSelectedRow();
    	for(sortirajuci s: robaList) {
    		if(s instanceof Roba) {
    			if(s instanceof TehnickaRoba) {
    			if(((Roba) s).getNaziv().equals(robaTable.getValueAt(izbraniRed, 0)) || ((Roba) s).getJedinicaMere().equals(robaTable.getValueAt(izbraniRed, 2))) {
    			nazivField.setText(((Roba) s).getNaziv());
    			t=(TehnickaRoba) s;
    			cenaField.setText(((Roba) s).getCena().toString());
    			jedinicaMereField.setText(((Roba) s).getJedinicaMere());
    			modelField.setText(((Roba) s).getModel());
    			visinaField.setText(String.valueOf(((TehnickaRoba) s).getDimenzije().getVisina()));
    			sirinaField.setText(String.valueOf(((TehnickaRoba) s).getDimenzije().getSirina()));
    			duzinaField.setText(String.valueOf(((TehnickaRoba) s).getDimenzije().getDuzina()));
    			snagaField.setText(((TehnickaRoba) s).getNominalnaSnaga().toString());
    			naponField.setText(((TehnickaRoba) s).getRadniNapon().toString());
    			obrisi.setVisible(false);
    			izabrani= robaList.indexOf(s);
    			break;
    		}}}
    	}
    }
    private void kreiraj() {
    	//Ucitavanje najnovije verzije liste
    	robaList=new Pisac().izCsva(new Pisac().ucitajSveLinije("src/data/data.csv"));
        try {
            String ime = nazivField.getText();
            String cenaaa = cenaField.getText();
            String visinaa = visinaField.getText();
            String sirinaa = sirinaField.getText();
            String duzinaa = duzinaField.getText();
            String snagaa = snagaField.getText();
            String naponn = naponField.getText();
            String jedinicaMere = jedinicaMereField.getText();
            String model = modelField.getText();

            if (ime.isEmpty() || jedinicaMere.isEmpty() || model.isEmpty() || visinaa.isEmpty() || sirinaa.isEmpty()
                    || duzinaa.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Molimo popunite sva polja.", "Greška", JOptionPane.ERROR_MESSAGE);
                return;
            }

            double cena = proveraDubleVrednosti(cenaaa, "Pogresan cena format. Molimo ukucajte validan broj.");
            double visina = proveraDubleVrednosti(visinaa, "Pogresan visina format. Molimo ukucajte validan broj.");
            double sirina = proveraDubleVrednosti(sirinaa, "Pogresan sirina format. Molimo ukucajte validan broj.");
            double duzina = proveraDubleVrednosti(duzinaa, "Pogresan duzina format. Molimo ukucajte validan broj.");
            double snaga = proveraDubleVrednosti(snagaa, "Pogresan snaga format. Molimo ukucajte validan broj.");
            double napon = proveraDubleVrednosti(naponn, "Pogresan napon format. Molimo ukucajte validan broj.");

            String selectedCountry = (String) zemljaPoreklaComboBox.getSelectedItem();
            TehnickaRoba roba = new TehnickaRoba(ime, cena, jedinicaMere, selectedCountry, model,
                    new Dimenzije(sirina, visina, duzina), snaga, napon);

            robaList.add(roba);
            new Pisac().pisi(robaList, "src/data/data.csv");
            tabelaTehnickeRobe.updateData(new Pisac().svaRobaT());
            nazivField.setText("");
            cenaField.setText("");
            jedinicaMereField.setText("");
            visinaField.setText("");
            modelField.setText("");
            duzinaField.setText("");
            sirinaField.setText("");
            snagaField.setText("");
            naponField.setText("");
            // Brisanje polja
        } catch (NumberFormatException ex) {
            
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Pogresan Input", JOptionPane.ERROR_MESSAGE);
        }
        //Dodatna provera double vrednosti
    }    private double proveraDubleVrednosti(String vrednost, String errorPoruka) {
        try {
           
            String cistaVrednost = vrednost.trim().replace(',', '.');
           
            return Double.parseDouble(cistaVrednost);
        } catch (NumberFormatException ex) {
            throw new NumberFormatException(errorPoruka);
        }
    }



}
