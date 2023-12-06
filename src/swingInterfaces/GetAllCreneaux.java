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
import Models.Medecin;
import Models.creneau;
import daoImpl.creneauDAOImpl;
import daoImpl.medecinDAOImpl;

public class GetAllCreneaux extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GetAllCreneaux frame = new GetAllCreneaux();
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
	public GetAllCreneaux() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1300, 437);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		  JPanel panel = new JPanel();
	        contentPane.add(panel, BorderLayout.NORTH);

	        JLabel lblNewLabel = new JLabel("Affichage des Creneaux");
	        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
	        lblNewLabel.setFont(new Font("Arial Black", Font.BOLD, 19));
	        lblNewLabel.setBackground(Color.RED);
	        panel.add(lblNewLabel);
	        panel.add(Box.createVerticalStrut(40));
	        // Adding margin between the label and the panel
	       
		
		
	
		dao dao=new creneauDAOImpl();
		List<creneau> creneaux;
		
	
		try {
			creneaux= dao.getAll();
		    Vector<Vector<Object>> dataVector = new Vector<>();
		    for (creneau  creneau : creneaux) {
		        Vector<Object> row = new Vector<>();
		        row.add(creneau.getID());
		        row.add(creneau.getVERSION());
		        row.add(creneau.getHDEBUT());
		        row.add(creneau.getMDEBUT());
		        row.add(creneau.getHFIN());
		        row.add(creneau.getMFIN());
		        row.add(creneau.getID_MEDECIN());


                dataVector.add(row);
		    }
            
		    int increasedRowHeight = 50;
		    Vector<String> columnNames = new Vector<>(List.of("Creneau ID", "Version", "Heure Debut", "Minute Debut", "Heure Fin","Minute Fin","Medecin ID", "Action"));
		    DefaultTableModel tableModel = new DefaultTableModel(dataVector, columnNames) {
		        @Override
		        public boolean isCellEditable(int row, int column) {
		            return column == 7;
		        }
		    };
		   
			 table = new JTable(tableModel);
            table.setRowHeight(increasedRowHeight);
		 // Apply custom renderer and editor to the "Action" column
            DeleteUpdateRendererCre renderer = new DeleteUpdateRendererCre();
            table.getColumnModel().getColumn(7).setCellRenderer(renderer);
            table.getColumnModel().getColumn(7).setCellEditor(new DeleteUpdateEditorCre(table));
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
        dao dao = new creneauDAOImpl();
        List<creneau> creneaux;

        try {
            creneaux = dao.getAll();
            DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
            tableModel.setRowCount(0); // Clear the existing data in the table

            for (creneau c : creneaux) {
                Vector<Object> row = new Vector<>();
                row.add(c.getID());
                row.add(c.getVERSION());
                row.add(c.getHDEBUT());
                row.add(c.getMDEBUT());
                row.add(c.getHFIN());
                row.add(c.getMFIN());
                row.add(c.getID_MEDECIN());

                tableModel.addRow(row);
            }
            tableModel.fireTableDataChanged();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}


class DeleteUpdatePaneCre extends JPanel {
	private JButton delete;
    private JButton update;
    private String state;
    private creneau creneau;
  
    public void setCreneauData(creneau c){
        this.creneau = new creneau(c.getID(),c.getVERSION(),c.getHDEBUT(),c.getMDEBUT(),c.getHFIN(),c.getMFIN(),c.getID_MEDECIN());
    	
    }
    public DeleteUpdatePaneCre() {
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
                	dao dao=new creneauDAOImpl();
                	try {
                	int rowAffected=dao.delete(creneau);
                    JOptionPane.showMessageDialog(DeleteUpdatePaneCre.this, "Creneau supprimé avec succès !", "Success", JOptionPane.INFORMATION_MESSAGE);
                    GetAllCreneaux.update_table();
                	
                	
                	
                	}catch(SQLException ee) {
                		System.out.println("Error in deleting creneau: " + ee.getMessage());
                        ee.printStackTrace(); // Print the stack trace for debugging

                        // Add the following line to show the exception message in a dialog
                        JOptionPane.showMessageDialog(DeleteUpdatePaneCre.this, "Erreur lors de la suppression du creneau:" + ee.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                	}
                
                     } else {
                    	 System.out.println(creneau.getID());
                    	 UpdateCreneau updateCreneauFrame = new UpdateCreneau(creneau);
                         
                    	 JFrame currentFrame = (JFrame) SwingUtilities.getWindowAncestor(DeleteUpdatePaneCre.this);
                         currentFrame.dispose();

                         
                         updateCreneauFrame.setVisible(true);
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
class DeleteUpdateRendererCre extends DefaultTableCellRenderer {
/**
	 * 
	 */
	private static final long serialVersionUID = 309122585876296401L;
private DeleteUpdatePaneCre DeleteUpdatePaneCre;
public DeleteUpdateRendererCre() {
    DeleteUpdatePaneCre = new DeleteUpdatePaneCre();
}
@Override
public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
    if (isSelected) {
        DeleteUpdatePaneCre.setBackground(table.getSelectionBackground());
    } else {
        DeleteUpdatePaneCre.setBackground(table.getBackground());
    }
   
    Long ID = (Long) table.getModel().getValueAt(row, 0);
    int version = (int) table.getModel().getValueAt(row, 1);
    int HDEBUT=(int) table.getModel().getValueAt(row, 2);
    int MDEBUT=(int) table.getModel().getValueAt(row, 3);
    int HFIN=(int) table.getModel().getValueAt(row, 4);
    int MFIN=(int) table.getModel().getValueAt(row, 5);
    Long IDMedecin=(Long) table.getModel().getValueAt(row, 6);
    creneau c = new creneau(ID, version,HDEBUT,MDEBUT,HFIN,MFIN,IDMedecin);
    DeleteUpdatePaneCre.setCreneauData(c);

    return DeleteUpdatePaneCre;
}
}
 class DeleteUpdateEditorCre extends AbstractCellEditor implements TableCellEditor {
	private JTable table;
	private DeleteUpdatePaneCre DeleteUpdatePaneCre;
	  public DeleteUpdateEditorCre(JTable table) {
		  this.table=table;
		  DeleteUpdatePaneCre = new DeleteUpdatePaneCre();
		  DeleteUpdatePaneCre.addActionListener(new ActionListener() {
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
          return DeleteUpdatePaneCre.getState();
      }
	@Override
	public boolean isCellEditable(EventObject e){
	    return true;
	}
      

      @Override
      public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
    	  if (isSelected) {
              DeleteUpdatePaneCre.setBackground(table.getSelectionBackground());
          } else {
              DeleteUpdatePaneCre.setBackground(table.getBackground());
          }

          // Assuming the first column is of type Long
    	  Long ID = (Long) table.getModel().getValueAt(row, 0);
    	    int version = (int) table.getModel().getValueAt(row, 1);
    	    int HDEBUT=(int) table.getModel().getValueAt(row, 2);
    	    int MDEBUT=(int) table.getModel().getValueAt(row, 3);
    	    int HFIN=(int) table.getModel().getValueAt(row, 4);
    	    int MFIN=(int) table.getModel().getValueAt(row, 5);
    	    Long IDMedecin=(Long) table.getModel().getValueAt(row, 6);
    	    creneau c = new creneau(ID, version,HDEBUT,MDEBUT,HFIN,MFIN,IDMedecin);

          DeleteUpdatePaneCre.setCreneauData(c);

          return DeleteUpdatePaneCre;
      }

}
