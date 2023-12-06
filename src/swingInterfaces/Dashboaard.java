package swingInterfaces;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class Dashboaard extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Dashboaard frame = new Dashboaard();
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
	public Dashboaard() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Medical Management System");
        setSize(970, 300);
        setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        setBackground(new Color(230, 230, 250)); // Lavender color, you can change it

        JLabel welcomeLabel = new JLabel("Bienvenue sur la gestion de Cabinet Médical!");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 18));
        welcomeLabel.setForeground(new Color(60, 60, 80)); // Dark blueish color, you can change it

        add(welcomeLabel);
        JPanel panel = new JPanel(new GridLayout(4, 3, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        addButton(panel, "Ajouter Client", e -> openFrame("AjouterClient"));
        addButton(panel, "Ajouter Medecin", e -> openFrame("AjouterMedecin"));
        addButton(panel, "Ajouter Creneau", e -> openFrame("AjouterCreneau"));
        addButton(panel, "Ajouter Rendez-vous", e -> openFrame("AjouterRV"));
        addButton(panel, "Afficher Clients", e -> openFrame("GetAllClients"));
        addButton(panel, "Afficher Medecins", e -> openFrame("Afficher Medecins"));
        addButton(panel, "Afficher Creneaux", e -> openFrame("Afficher Creneaux"));
        addButton(panel, "Afficher Rendez-vous", e -> openFrame("Afficher Rendez-vous"));
        addButton(panel, "Update Client", e -> openFrame("Update Client"));
        addButton(panel, "Update Medecin", e -> openFrame("Update Medecin"));
        addButton(panel, "Update Creneau", e -> openFrame("Update Creneau"));
        addButton(panel, "Update Rendez-vous", e -> openFrame("Update Rendez-vous"));

        getContentPane().add(panel);

}

    private void addButton(JPanel panel, String text, ActionListener listener) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.PLAIN, 14));
        button.addActionListener(listener);
        panel.add(button);
    }
    private void openFrame(String frameName) {
        JFrame frame = new JFrame(frameName);

        // Add your specific content for each frame here

        frame.setVisible(true);
    }

}