package swingInterfaces;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.EventObject;
import java.util.List;
import java.util.Vector;

import javax.swing.AbstractCellEditor;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.event.CellEditorListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;

import DAO.dao;
import Models.Client;
import Models.Medecin;
import daoImpl.clientDAOImpl;
import daoImpl.medecinDAOImpl;

public class GetAllMedecins extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GetAllMedecins frame = new GetAllMedecins();
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
	static JTable table=null;
	public GetAllMedecins() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1080, 437);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		  JPanel panel = new JPanel();
	        contentPane.add(panel, BorderLayout.NORTH);

	        JLabel lblNewLabel = new JLabel("Affichage des Medecins");
	        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
	        lblNewLabel.setFont(new Font("Arial Black", Font.BOLD, 19));
	        lblNewLabel.setBackground(Color.RED);
	        panel.add(lblNewLabel);
	        panel.add(Box.createVerticalStrut(40));
	        // Adding margin between the label and the panel
	       
		
		
	
		dao dao=new medecinDAOImpl();
		List<Medecin> medecins;
		
	
		try {
		    medecins= dao.getAll();
		    Vector<Vector<Object>> dataVector = new Vector<>();
		    for (Medecin medecin : medecins) {
		        Vector<Object> row = new Vector<>();
		        row.add(medecin.getID());
		        row.add(medecin.getVERSION());
		        row.add(medecin.getTITRE());
		        row.add(medecin.getNOM());
		        row.add(medecin.getPRENOM());


                dataVector.add(row);
		    }
            
		    int increasedRowHeight = 50;
		    Vector<String> columnNames = new Vector<>(List.of("Medecin ID", "Version", "Titre", "Nom", "Prenom", "Action"));
		    DefaultTableModel tableModel = new DefaultTableModel(dataVector, columnNames) {
		        @Override
		        public boolean isCellEditable(int row, int column) {
		            return column == 5;
		        }
		    };
		   
			 table = new JTable(tableModel);
            table.setRowHeight(increasedRowHeight);
		 // Apply custom renderer and editor to the "Action" column
            DeleteUpdateRendererMed renderer = new DeleteUpdateRendererMed();
            table.getColumnModel().getColumn(5).setCellRenderer(renderer);
            table.getColumnModel().getColumn(5).setCellEditor(new DeleteUpdateEditorMed(table));
            table.setRowHeight(renderer.getTableCellRendererComponent(table, null, true, true, 0, 0).getPreferredSize().height);
            tableModel.fireTableDataChanged();
		    JScrollPane scrollPane = new JScrollPane(table);
		    contentPane.add(scrollPane, BorderLayout.CENTER);
		    setLocationRelativeTo(null); // Center the JFrame on the screen
		    setVisible(true);
		} catch (SQLException e) {
		    e.printStackTrace(); // Handle the exception appropriately
		}
		
		 
	}
	public static void update_table() {
        // Fetch the updated data from the database and update the table
        dao dao = new medecinDAOImpl();
        List<Medecin> medecins;

        try {
            medecins = dao.getAll();
            DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
            tableModel.setRowCount(0); // Clear the existing data in the table

            for (Medecin medecin : medecins) {
                Vector<Object> row = new Vector<>();
                row.add(medecin.getID());
                row.add(medecin.getVERSION());
                row.add(medecin.getTITRE());
                row.add(medecin.getNOM());
                row.add(medecin.getPRENOM());

                tableModel.addRow(row);
            }
            tableModel.fireTableDataChanged();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}


class DeleteUpdatePaneMed extends JPanel {
	private JButton delete;
    private JButton update;
    private String state;
    private Medecin medecin;
  
    public void setMedecinData(Medecin medecin){
        this.medecin = new Medecin(medecin.getID(),medecin.getVERSION(),medecin.getTITRE(),medecin.getNOM(),medecin.getPRENOM());
    	
    }
    public DeleteUpdatePaneMed() {
        setLayout(new GridBagLayout());
        delete = new JButton("Supprimer");
        delete.setActionCommand("delete");
        update = new JButton("Update");
        update.setActionCommand("update");

        add(delete);
        add(update);

        ActionListener listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                state = e.getActionCommand();
                System.out.println("State = " + state);
                
                if(state.equals("delete")) {
                	dao dao=new medecinDAOImpl();
                	try {
                	int rowAffected=dao.delete(medecin);
                    JOptionPane.showMessageDialog(DeleteUpdatePaneMed.this, "Medecine supprimé avec succès !", "Success", JOptionPane.INFORMATION_MESSAGE);
                    GetAllMedecins.update_table();
                	
                	
                	
                	}catch(SQLException ee) {
                		System.out.println("Error in deleting client: " + ee.getMessage());
                        ee.printStackTrace(); // Print the stack trace for debugging

                        // Add the following line to show the exception message in a dialog
                        JOptionPane.showMessageDialog(DeleteUpdatePaneMed.this, "Erreur lors de la suppression du Medecin:" + ee.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                	}
                
                     } else {
                    	 System.out.println(medecin.getID());
                    	 updateMedecin updateMedecinFrame = new updateMedecin(medecin);
                         
                    	 JFrame currentFrame = (JFrame) SwingUtilities.getWindowAncestor(DeleteUpdatePaneMed.this);
                         currentFrame.dispose();

                         
                         updateMedecinFrame.setVisible(true);
                     }
                }
                
            
        };

        delete.addActionListener(listener);
        update.addActionListener(listener);
    }
    public void addActionListener(ActionListener listener) {
        delete.addActionListener(listener);
        update.addActionListener(listener);
    }

    public String getState() {
        return state;
    }
}
class DeleteUpdateRendererMed extends DefaultTableCellRenderer {
/**
	 * 
	 */
	private static final long serialVersionUID = 309122585876296401L;
private DeleteUpdatePaneMed DeleteUpdatePaneMed;
public DeleteUpdateRendererMed() {
    DeleteUpdatePaneMed = new DeleteUpdatePaneMed();
}
@Override
public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
    if (isSelected) {
        DeleteUpdatePaneMed.setBackground(table.getSelectionBackground());
    } else {
        DeleteUpdatePaneMed.setBackground(table.getBackground());
    }
   
    Long ID = (Long) table.getModel().getValueAt(row, 0);
    int version = (int) table.getModel().getValueAt(row, 1);
    String titre = (String) table.getModel().getValueAt(row, 2);
    String nom = (String) table.getModel().getValueAt(row, 3);
    String prenom = (String) table.getModel().getValueAt(row, 4);

    Medecin medecin = new Medecin(ID, version, titre, nom, prenom);
    DeleteUpdatePaneMed.setMedecinData(medecin);

    return DeleteUpdatePaneMed;
}
}
 class DeleteUpdateEditorMed extends AbstractCellEditor implements TableCellEditor {
	private JTable table;
	private DeleteUpdatePaneMed DeleteUpdatePaneMed;
	  public DeleteUpdateEditorMed(JTable table) {
		  this.table=table;
		  DeleteUpdatePaneMed = new DeleteUpdatePaneMed();
		  DeleteUpdatePaneMed.addActionListener(new ActionListener() {
              @Override
              public void actionPerformed(ActionEvent e) {
              }
              
          });
		  }
	
	  @Override
	public boolean shouldSelectCell(EventObject anEvent) {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean stopCellEditing() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public void cancelCellEditing() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void addCellEditorListener(CellEditorListener l) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void removeCellEditorListener(CellEditorListener l) {
		// TODO Auto-generated method stub
		
	}
	@Override
      public Object getCellEditorValue() {
          return DeleteUpdatePaneMed.getState();
      }
	@Override
	public boolean isCellEditable(EventObject e){
	    return true;
	}
      

      @Override
      public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
    	  if (isSelected) {
              DeleteUpdatePaneMed.setBackground(table.getSelectionBackground());
          } else {
              DeleteUpdatePaneMed.setBackground(table.getBackground());
          }

          // Assuming the first column is of type Long
          Long ID = (Long) table.getModel().getValueAt(row, 0);
          int version = (int) table.getModel().getValueAt(row, 1);
          String titre = (String) table.getModel().getValueAt(row, 2);
          String nom = (String) table.getModel().getValueAt(row, 3);
          String prenom = (String) table.getModel().getValueAt(row, 4);

          Medecin medecin = new Medecin(ID, version, titre, nom, prenom);
          DeleteUpdatePaneMed.setMedecinData(medecin);

          return DeleteUpdatePaneMed;
      }
  }
	  



