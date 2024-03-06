package ui.mainWindow;


import java.awt.HeadlessException;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import radNadFajlovima.Pisac;
import ui.forme.MenadzerForma;
import ui.forme.ProdavacForma;
import ui.forme.RobaKratkogTrajanjaForma;
import ui.forme.TehnickaRobaForma;
import ui.pretraga.BrzaPretraga;

public class ProzorAplikacije extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3592270463652961032L;
	public ProzorAplikacije() throws HeadlessException {
		super();
		
	}

	//Inicijalizacija komponenti
	public void init() {
		this.setName("Upravljanje Projektom");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(800, 800);
		RobaKratkogTrajanjaForma rt = new RobaKratkogTrajanjaForma();
		TehnickaRobaForma tr = new TehnickaRobaForma();
		rt.init();
		tr.init();
		JTabbedPane p = new JTabbedPane();
		BrzaPretraga pretraga  =  new BrzaPretraga();
		pretraga.init();
		p.addTab("Pretraga", pretraga);
		p.addTab("Roba kratkog trajanja", rt);
		p.addTab("Tehnicka roba",tr);
		ProdavacForma p4 = new ProdavacForma();
		p4.init();
		p.addTab("Prodavci", p4);
		
		MenadzerForma m4 = new MenadzerForma();
		m4.init();
		
		p.addTab("Menadzeri", m4);
		//Kada se promeni stranica azuriraj vrednosti tabela i lista za te stranice(u slucaju da se prodavaca obrise, na tab-u menadzer ce se takodje obrisati)
        p.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                JTabbedPane sourceTabbedPane = (JTabbedPane) e.getSource();
                int selectedIndex = sourceTabbedPane.getSelectedIndex();

                
                if (selectedIndex == sourceTabbedPane.indexOfTab("Menadzeri")) {
                    
                    m4.getTabela().updateData(new Pisac().sviZaposleni());
                    m4.setSviSvi(new Pisac().izCsva(new Pisac().ucitajSveLinije("src/data/data.csv")));
                }else if((selectedIndex == sourceTabbedPane.indexOfTab("Prodavci"))) {
                	p4.setSviSvi(new Pisac().izCsva(new Pisac().ucitajSveLinije("src/data/data.csv")));
                
                }
            }
        });
		
	
		
		
		
		
		
		this.add(p);
		
		this.setVisible(true);
	}
}
