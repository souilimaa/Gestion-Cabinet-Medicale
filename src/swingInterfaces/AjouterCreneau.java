package swingInterfaces;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import DAO.dao;
import Models.Medecin;
import Models.creneau;
import daoImpl.creneauDAOImpl;
import daoImpl.medecinDAOImpl;

import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.JFormattedTextField;
import javax.swing.JSpinner;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.SystemColor;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;
import java.awt.event.ActionEvent;

public class AjouterCreneau extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AjouterCreneau frame = new AjouterCreneau();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public AjouterCreneau() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 720, 612);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(661, 405, -582, -350);
		contentPane.add(panel_1);
		
		JPanel panel = new JPanel();
		panel.setBounds(150, 26, 414, 37);
		contentPane.add(panel);
		
		JLabel lblNewLabel = new JLabel("Ajouter Un Creneau : ");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Arial Black", Font.BOLD, 19));
		lblNewLabel.setBackground(Color.RED);
		panel.add(lblNewLabel);
		
		
		JLabel lblNewLabel_1 = new JLabel("Heure de début :");
		lblNewLabel_1.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 15));
		lblNewLabel_1.setBounds(182, 118, 138, 14);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Minute de début :");
		lblNewLabel_2.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 15));
		lblNewLabel_2.setBounds(182, 176, 138, 14);
		contentPane.add(lblNewLabel_2);
		
		
		
		JLabel lblNewLabel_3 = new JLabel("Heure de fin :");
		lblNewLabel_3.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 15));
		lblNewLabel_3.setBounds(182, 240, 138, 14);
		contentPane.add(lblNewLabel_3);
		
	
		JLabel promptLabel = new JLabel("Tous les champs sont obligatoires !!");
		promptLabel.setForeground(UIManager.getColor("ToolBar.dockingForeground"));
		promptLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		promptLabel.setBounds(236, 74, 276, 20);
		promptLabel.setVisible(false);
		contentPane.add(promptLabel);
		JLabel labelPromptErr = new JLabel(" Une erreur dans l'insertion du creneau !!");
		labelPromptErr.setFont(new Font("Tahoma", Font.PLAIN, 12));
		labelPromptErr.setForeground(UIManager.getColor("ToolBar.dockingForeground"));
		labelPromptErr.setBounds(182, 479, 276, 14);
		labelPromptErr.setVisible(false);
		contentPane.add(labelPromptErr);
		
		JLabel lblNewLabel_3_1 = new JLabel("Minute de fin :");
		lblNewLabel_3_1.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 15));
		lblNewLabel_3_1.setBounds(182, 293, 138, 14);
		contentPane.add(lblNewLabel_3_1);
		
		JLabel lblNewLabel_3_1_1 = new JLabel("Medecin ID :");
		lblNewLabel_3_1_1.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 15));
		lblNewLabel_3_1_1.setBounds(182, 350, 138, 14);
		contentPane.add(lblNewLabel_3_1_1);
		SpinnerModel sm=new SpinnerNumberModel(0,0,12,1);
		JSpinner HDEBUTInput = new JSpinner(sm);
		HDEBUTInput.setBounds(338, 118, 174, 20);
		contentPane.add(HDEBUTInput);
		SpinnerModel spm=new SpinnerNumberModel(0,0,59,1);
		JSpinner MDEBUTInput = new JSpinner(spm);
		MDEBUTInput.setBounds(338, 176, 174, 20);
		contentPane.add(MDEBUTInput);
		
		SpinnerModel smm=new SpinnerNumberModel(0,0,12,1);

		JSpinner HFINInput = new JSpinner(smm);
		HFINInput.setBounds(338, 234, 174, 20);
		contentPane.add(HFINInput);
		SpinnerModel sppm=new SpinnerNumberModel(0,0,59,1);

		JSpinner MFINInput = new JSpinner(sppm);
		MFINInput.setBounds(338, 293, 174, 20);
		contentPane.add(MFINInput);
		
		JComboBox MedecinIDCombo = new JComboBox();
		MedecinIDCombo.setBounds(338, 349, 174, 22);
        MedecinIDCombo.setSelectedIndex(-1);
		contentPane.add(MedecinIDCombo);
		addMedecinsToCombo(MedecinIDCombo);
		JButton btnAjouterCreneau = new JButton("Ajouter");
		btnAjouterCreneau.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
		       
                String selectedValue = (String) MedecinIDCombo.getSelectedItem();
				if((int)HDEBUTInput.getValue() == 0||(int)HFINInput.getValue() == 0||selectedValue==null) {
					promptLabel.setVisible(true);

				}else {
					promptLabel.setVisible(false);
					int version=1;
					 int hdebut = (int) HDEBUTInput.getValue();
				        int mdebut = (int) MDEBUTInput.getValue();
				        int hfin=(int) HFINInput.getValue();
				        int mfin=(int) MFINInput.getValue();
		                int startIndex=selectedValue.lastIndexOf(":")+2;
		                int endIndex=selectedValue.lastIndexOf(")")-1;
		                String idSubstring = selectedValue.substring(startIndex, endIndex);
		                long idMedecin=Long.parseLong(idSubstring);
				       creneau creneau=new creneau(version,hdebut,mdebut,hfin,mfin,idMedecin);
						dao dao=new creneauDAOImpl();
						try {
							int rowsAffected=dao.insert(creneau);
							HDEBUTInput.setValue(0);
							MDEBUTInput.setValue(0);
							HFINInput.setValue(0);
							MFINInput.setValue(0);
		                    JOptionPane.showMessageDialog(AjouterCreneau.this, "Creneau inséré avec succès !", "Success", JOptionPane.INFORMATION_MESSAGE);
		                    
						} catch (SQLException e1) {
							labelPromptErr.setVisible(true);
							System.out.println("error had occured in insert Creneau"+e);
							e1.printStackTrace();
							
						}
				}
				
				
			}
		});
		btnAjouterCreneau.setForeground(Color.WHITE);
		btnAjouterCreneau.setFont(new Font("Segoe UI Historic", Font.BOLD, 14));
		btnAjouterCreneau.setBackground(SystemColor.textHighlight);
		btnAjouterCreneau.setBounds(182, 426, 330, 29);
		contentPane.add(btnAjouterCreneau);
		
	}
	//myFunction to add medecins to combobox
	private void addMedecinsToCombo(JComboBox jcombo ) {
		dao dao=new medecinDAOImpl();
		List<Medecin> medecins;
		try {
				medecins=dao.getAll();
				for(Medecin m:medecins) {
					jcombo.addItem(m.getNOM()+", "+m.getPRENOM()+"( id: "+m.getID()+" )");
				}
				
		}catch(SQLException e) {
			
		}
		
	}
}
