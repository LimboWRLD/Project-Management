package ui.forme;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Locale;
import java.time.LocalDate;
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
import sredstva.Roba;
import sredstva.RobaKratkogTrajanja;
import ui.tabele.RobaKratkogTrajanjaTable;

public class RobaKratkogTrajanjaForma extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7099872780309983291L;
	//Komponente
	private JTextField nazivField;
    private JTextField canaField;
    private JTextField modelField;
    private JTextField jedinicaMereField;
    private JTextField uputstvoField;
    private JComboBox<String> zemljaPoreklaComboBox;
    private JComboBox<Object>datumi;
    private JButton kreirajButton;
    private JButton izmeniButton;
    private JButton obrisi;
    private RobaKratkogTrajanja t;
    private RobaKratkogTrajanjaTable tabelaRobeKratkogTrajanja;
    private JTable robaTable;
    private ArrayList<sortirajuci> robaList = new Pisac().izCsva(new Pisac().ucitajSveLinije("src/data/data.csv"));  // Example data storage
    //Konstruktor
    public RobaKratkogTrajanjaForma() {
		super();
		
	}
    //Geter i seteri
    public RobaKratkogTrajanjaTable getTabela() {
    	return tabelaRobeKratkogTrajanja;
    }
    public void setRobaList(ArrayList<sortirajuci> a) {
    	this.robaList=a;
    }

    private String[] generisiDatume() {
        // Generisanje datuma za sledecih 30 dana
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String[] opcijeDatuma = new String[30];
        Calendar calendar = Calendar.getInstance();

        for (int i = 0; i < 14; i++) {
            opcijeDatuma[i] = dateFormat.format(calendar.getTime());
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }

        return opcijeDatuma;
    }
    //Inicijalizacija komponenata
    public void init() {
    	nazivField = new JTextField();
        canaField = new JTextField();
        uputstvoField = new JTextField();
        modelField = new JTextField();
        jedinicaMereField = new JTextField();
        obrisi = new JButton("Obrisi");
        izmeniButton =  new JButton("Izmeni");
        kreirajButton = new JButton("Kreiraj");
        String[] opcijeDatuma = generisiDatume();
        datumi = new JComboBox<>(opcijeDatuma);
        String[] zemlje = Locale.getISOCountries();
        Arrays.sort(zemlje);
        tabelaRobeKratkogTrajanja = new RobaKratkogTrajanjaTable(new Pisac().svaRobaKT());
        robaTable = new JTable(tabelaRobeKratkogTrajanja);
        String[] imenaZemalja = new String[zemlje.length];
        for (int i = 0; i < zemlje.length; i++) {
            Locale locale = new Locale("", zemlje[i]);
            imenaZemalja[i] = locale.getDisplayCountry();
        }
        
        
        zemljaPoreklaComboBox = new JComboBox<>(imenaZemalja);
        
        JPanel inputPanel = new JPanel(new GridLayout(8, 2));
        inputPanel.add(new JLabel("Naziv:"));
        inputPanel.add(nazivField);
        inputPanel.add(new JLabel("Cena:"));
        inputPanel.add(canaField);
        inputPanel.add(new JLabel("Jedinica mere:"));
        inputPanel.add(jedinicaMereField);
        inputPanel.add(new JLabel("Model :"));
        inputPanel.add(modelField);
        inputPanel.add(new JLabel("Uputstvo za skladištenje:"));
        inputPanel.add(uputstvoField);
        //inputPanel.add(inputPanel);
        //zemljaPoreklaComboBox = new JComboBox<>(countries.toArray(new String[0]));
        inputPanel.add(zemljaPoreklaComboBox); 
        inputPanel.add(datumi);
        
        inputPanel.add(kreirajButton);
        inputPanel.add(obrisi);
        izmeniButton.setVisible(false);
        inputPanel.add(izmeniButton);
        robaTable.addMouseListener( new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) { 
                	// Check for double-click	
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
    	//Ucitavanje najnovije verzije liste
        robaList = new Pisac().izCsva(new Pisac().ucitajSveLinije("src/data/data.csv"));
        
        for (sortirajuci a : robaList) {
            if (a instanceof RobaKratkogTrajanja) {
                if (((Roba) a).getNaziv().equals(t.getNaziv()) && ((RobaKratkogTrajanja) a).getJedinicaMere().equals(t.getJedinicaMere())) {
                    izabrani = robaList.indexOf(a);
                    //Pronalazenje indeksa izabranog
                }
            }
        }

        if (!nazivField.getText().trim().isEmpty()) {
            try {
                robaList = new Pisac().izCsva(new Pisac().ucitajSveLinije("src/data/data.csv"));
                String ime = nazivField.getText();
                String cenaaa = canaField.getText();
                double cena = Double.parseDouble(cenaaa);
                String uputstvo = uputstvoField.getText();
                String jedinicaMere = jedinicaMereField.getText();
                String model = modelField.getText();

                if (ime.isEmpty() || jedinicaMere.isEmpty() || model.isEmpty() || uputstvo.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Molimo popunite sva polja.", "Greška", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                robaList.set(izabrani, new RobaKratkogTrajanja(ime, cena, jedinicaMere,
                        (String) zemljaPoreklaComboBox.getSelectedItem(), model,
                        LocalDate.parse((CharSequence) datumi.getSelectedItem()), uputstvo));

                new Pisac().pisi(robaList, "src/data/data.csv");
                tabelaRobeKratkogTrajanja.updateData(new Pisac().svaRobaKT());
                izmeniButton.setVisible(false);
                nazivField.setText("");
                canaField.setText("");
                jedinicaMereField.setText("");
                uputstvoField.setText("");
                modelField.setText("");
                kreirajButton.setVisible(true);
                obrisi.setVisible(true);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Neispravan format za cenu. Unesite validan broj.", "Greška", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void obrisi() {
        int selectedRow = robaTable.getSelectedRow();
        //Ucitavanje najnovije verzije liste
        robaList = new Pisac().izCsva(new Pisac().ucitajSveLinije("src/data/data.csv"));
        if (selectedRow != -1) {
        	for(sortirajuci a:robaList) {
        		if(a instanceof RobaKratkogTrajanja) {
        		if(((Roba) a).getNaziv().equalsIgnoreCase((String) robaTable.getValueAt(selectedRow, 0)) && ((Roba) a).getJedinicaMere().equals(robaTable.getValueAt(selectedRow, 2))) {
        				robaList.remove(a);
        				new Pisac().pisi(robaList, "src/data/data.csv");
        			
        				tabelaRobeKratkogTrajanja.updateData(new Pisac().svaRobaKT());
        				 nazivField.setText("");
        		            canaField.setText("");
        		            jedinicaMereField.setText("");
        		            uputstvoField.setText("");
        		            modelField.setText("");
        				break;// Clear the input fields after removal
        }}}} else {
            JOptionPane.showMessageDialog(this, "Molimo odaberite red za brisanje.", "Greška", JOptionPane.ERROR_MESSAGE);
        }
    }
    private void izmeni() {
    	int izbraniRed =  robaTable.getSelectedRow();
    	//Pronalazenje izabranog
    	for(sortirajuci s: robaList) {
    		if(s instanceof RobaKratkogTrajanja) {
    			if(((Roba) s).getNaziv().equals(robaTable.getValueAt(izbraniRed, 0)) || ((Roba) s).getJedinicaMere().equals(robaTable.getValueAt(izbraniRed, 2))) {
    			nazivField.setText(((Roba) s).getNaziv());
    			canaField.setText(((Roba) s).getCena().toString());
    			jedinicaMereField.setText(((Roba) s).getJedinicaMere());
    			t=(RobaKratkogTrajanja) s;
    			modelField.setText(((Roba) s).getModel());
    			uputstvoField.setText(((RobaKratkogTrajanja) s).getUpustvoZaSkladistenje());
    			izabrani= robaList.indexOf(s);
    			obrisi.setVisible(false);
    		}}
    	}
    }
    private void kreiraj() {
        try {
        	//Ucitavanje najnovije verzije liste
        	 robaList = new Pisac().izCsva(new Pisac().ucitajSveLinije("src/data/data.csv"));
            String ime = nazivField.getText();
            String cenaaa = canaField.getText();
            double price = Double.parseDouble(cenaaa);
           
            String uputstvo = uputstvoField.getText();
            String jedinicaMere = jedinicaMereField.getText();
            String model =  modelField.getText();
            if (ime.isEmpty() || jedinicaMere.isEmpty() || model.isEmpty() || uputstvo.isEmpty()) {
                 JOptionPane.showMessageDialog(this, "Molimo popunite sva polja.", "Greška", JOptionPane.ERROR_MESSAGE);
                 return;
             }
            LocalDate izabraniRok =LocalDate.parse((CharSequence) datumi.getSelectedItem()) ;
            String selectedCountry = (String) zemljaPoreklaComboBox.getSelectedItem();
            RobaKratkogTrajanja roba = new RobaKratkogTrajanja(ime, price, jedinicaMere, selectedCountry, model, izabraniRok, uputstvo);
           
            robaList.add(roba);
            new Pisac().pisi(robaList, "src/data/data.csv");
            
            nazivField.setText("");
            canaField.setText("");
            jedinicaMereField.setText("");
            uputstvoField.setText("");
            modelField.setText("");
            tabelaRobeKratkogTrajanja.updateData(new Pisac().svaRobaKT());
            
          
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Unesite validan iznos cene.");
        }
    }

}
