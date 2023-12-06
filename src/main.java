import javax.swing.SwingUtilities;

import swingInterfaces.Dashboaard;

public class main {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
            // Create and show the dashboard frame
            Dashboaard dashboard = new Dashboaard();
            dashboard.setVisible(true);
        });
	}
	
}
