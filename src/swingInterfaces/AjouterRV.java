package swingInterfaces;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.SystemColor;
import com.toedter.calendar.JDateChooser;

import DAO.dao;
import Models.Client;
import Models.Medecin;
import Models.creneau;
import Models.rv;
import daoImpl.clientDAOImpl;
import daoImpl.creneauDAOImpl;
import daoImpl.medecinDAOImpl;
import daoImpl.rvDAOImpl;

import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.awt.event.ActionEvent;

public class AjouterRV extends JFrame {

	private JPanel contentPane;
	private JComboBox CreneauxCombo;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AjouterRV frame = new AjouterRV();
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
	public AjouterRV() {

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 704, 472);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(661, 405, -582, -350);
		contentPane.add(panel_1);
		
		JPanel panel = new JPanel();
		panel.setBounds(128, 30, 414, 37);
		contentPane.add(panel);
		
		JLabel lblNewLabel = new JLabel("Ajouter un rendez-vous");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Arial Black", Font.BOLD, 19));
		lblNewLabel.setBackground(Color.RED);
		panel.add(lblNewLabel);
		
		JLabel lblNewLabel_3 = new JLabel("Jour :");
		lblNewLabel_3.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 15));
		lblNewLabel_3.setBounds(177, 113, 68, 14);
		contentPane.add(lblNewLabel_3);
		
	
		JLabel promptLabel = new JLabel("Tous les champs sont obligatoires !!");
		promptLabel.setForeground(UIManager.getColor("ToolBar.dockingForeground"));
		promptLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		promptLabel.setBounds(177, 78, 276, 20);
		promptLabel.setVisible(false);
		contentPane.add(promptLabel);
		JLabel labelPromptErr = new JLabel(" Une erreur dans l'insertion du rendez-vous !!");
		labelPromptErr.setFont(new Font("Tahoma", Font.PLAIN, 12));
		labelPromptErr.setForeground(UIManager.getColor("ToolBar.dockingForeground"));
		labelPromptErr.setBounds(177, 322, 276, 14);
		labelPromptErr.setVisible(false);
		contentPane.add(labelPromptErr);
		
		JLabel lblNewLabel_3_1 = new JLabel("Client ID :");
		lblNewLabel_3_1.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 15));
		lblNewLabel_3_1.setBounds(177, 161, 68, 14);
		contentPane.add(lblNewLabel_3_1);

		
		JLabel lblNewLabel_3_2 = new JLabel("Creneau ID :");
		lblNewLabel_3_2.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 15));
		lblNewLabel_3_2.setBounds(177, 213, 98, 14);
		contentPane.add(lblNewLabel_3_2);
		
		CreneauxCombo = new JComboBox();
		CreneauxCombo.setBounds(276, 212, 204, 22);
		contentPane.add(CreneauxCombo);
		addCreneauxToCombo(CreneauxCombo );
		JComboBox ClientsCombo = new JComboBox();
		ClientsCombo.setBounds(276, 160, 204, 22);
		contentPane.add(ClientsCombo);
		addClientsToCombo(ClientsCombo );
		
		JButton btnAjouterRV = new JButton("Ajouter");
		btnAjouterRV.setForeground(Color.WHITE);
		btnAjouterRV.setFont(new Font("Segoe UI Historic", Font.BOLD, 14));
		btnAjouterRV.setBackground(SystemColor.textHighlight);
		btnAjouterRV.setBounds(177, 269, 303, 29);
		contentPane.add(btnAjouterRV);
		
		JDateChooser dateChooser = new JDateChooser();
		dateChooser.setPreferredSize(new Dimension(200, 30));
		dateChooser.setBounds(276, 109, 204, 30);
		contentPane.add(dateChooser);
		btnAjouterRV.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 java.util.Date utilDate = dateChooser.getDate();
				 java.sql.Date sqld= new java.sql.Date(utilDate.getTime());
                String selectedClient = (String) ClientsCombo.getSelectedItem();
                long selectedCreneau = (long) CreneauxCombo.getSelectedItem();

				if(sqld==null) {
					promptLabel.setVisible(true);
				}
				
				else {
					promptLabel.setVisible(false);
	                int startIndex=selectedClient.lastIndexOf(":")+2;
					 int endIndex=selectedClient.lastIndexOf(")")-1;
		                String idSubstring = selectedClient.substring(startIndex, endIndex);
		            
		                long idClient=Long.parseLong(idSubstring);
		                System.out.println(idClient);
		                
		                long idCreneau=selectedCreneau;
					rv rv=new rv(sqld,idClient,idCreneau);
					dao dao=new rvDAOImpl();
					try {
						labelPromptErr.setVisible(false);
						int rowsAffected=dao.insert(rv);
						
	                    JOptionPane.showMessageDialog(AjouterRV.this, "Rendez-vous inséré avec succès !", "Success", JOptionPane.INFORMATION_MESSAGE);
	                    
					} catch (SQLException e1) {
						labelPromptErr.setVisible(true);
						System.out.println("error had occured in insert Rendez-vous"+e);
						e1.printStackTrace();
						
					}
				}
				
				
				
				
			}
		});
	
		
	}
	private void addClientsToCombo(JComboBox jcombo ) {
		dao dao=new clientDAOImpl();
		List<Client> clients;
		try {
				clients=dao.getAll();
				for(Client c:clients) {
					jcombo.addItem(c.getNOM()+", "+c.getPRENOM()+"( id: "+c.getID()+" )");
				}
				
		}catch(SQLException e) {
			
		}
		
	}
	private void addCreneauxToCombo(JComboBox jcombo ) {
		dao dao=new creneauDAOImpl();
		List<creneau> creneaux;
		try {
				creneaux=dao.getAll();
				for(creneau c:creneaux) {
					jcombo.addItem(c.getID());
				}
				
		}catch(SQLException e) {
			
		}
		
	}
}
