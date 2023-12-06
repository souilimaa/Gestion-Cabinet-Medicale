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
import java.util.Date;
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
import Models.creneau;
import Models.rv;
import daoImpl.creneauDAOImpl;
import daoImpl.rvDAOImpl;

public class GetAllRV extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GetAllRV frame = new GetAllRV();
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

	public GetAllRV() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 437);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		  JPanel panel = new JPanel();
	        contentPane.add(panel, BorderLayout.NORTH);

	        JLabel lblNewLabel = new JLabel("Affichage des Rendez-vous");
	        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
	        lblNewLabel.setFont(new Font("Arial Black", Font.BOLD, 19));
	        lblNewLabel.setBackground(Color.RED);
	        panel.add(lblNewLabel);
	        panel.add(Box.createVerticalStrut(40));
	        // Adding margin between the label and the panel
	       
		
		
	
		dao dao=new rvDAOImpl();
		List<rv> rvs;
		
	
		try {
			rvs= dao.getAll();
		    Vector<Vector<Object>> dataVector = new Vector<>();
		    for (rv  rv : rvs) {
		        Vector<Object> row = new Vector<>();
		        row.add(rv.getID());
		        row.add(rv.getJOUR());
		        row.add(rv.getID_CLIENT());
		        row.add(rv.getID_CRENEAU());
		      


                dataVector.add(row);
		    }
            
		    int increasedRowHeight = 50;
		    Vector<String> columnNames = new Vector<>(List.of("Rendez-vous ID", "Jour", "Client ID","Creneau ID", "Action"));
		    DefaultTableModel tableModel = new DefaultTableModel(dataVector, columnNames) {
		        @Override
		        public boolean isCellEditable(int row, int column) {
		            return column == 5;
		        }
		    };
		   
			 table = new JTable(tableModel);
            table.setRowHeight(increasedRowHeight);
		 // Apply custom renderer and editor to the "Action" column
            DeleteUpdateRendererRV renderer = new DeleteUpdateRendererRV();
            table.getColumnModel().getColumn(4).setCellRenderer(renderer);
            table.getColumnModel().getColumn(4).setCellEditor(new DeleteUpdateEditorrv(table));
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
        dao dao = new rvDAOImpl();
        List<rv> rvs;

        try {
            rvs = dao.getAll();
            DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
            tableModel.setRowCount(0); // Clear the existing data in the table

            for (rv rv : rvs) {
                Vector<Object> row = new Vector<>();
                row.add(rv.getID());
                row.add(rv.getJOUR());
                row.add(rv.getID_CLIENT());
                row.add(rv.getID_CRENEAU());
            

                tableModel.addRow(row);
            }
            tableModel.fireTableDataChanged();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}


class DeleteUpdatePaneRV extends JPanel {
	private JButton delete;
    private JButton update;
    private String state;
    private rv rv;
  
    public void setrvData(rv rv){
        this.rv = new rv(rv.getID(),rv.getJOUR(),rv.getID_CLIENT(),rv.getID_CRENEAU());
    	
    }
    public DeleteUpdatePaneRV() {
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
                	dao dao=new rvDAOImpl();
                	try {
                	int rowAffected=dao.delete(rv);
                    JOptionPane.showMessageDialog(DeleteUpdatePaneRV.this, "Rendez-vous supprimé avec succès !", "Success", JOptionPane.INFORMATION_MESSAGE);
                    GetAllCreneaux.update_table();
                	
                	
                	
                	}catch(SQLException ee) {
                		System.out.println("Error in deleting rendez-vous: " + ee.getMessage());
                        ee.printStackTrace(); // Print the stack trace for debugging

                        // Add the following line to show the exception message in a dialog
                        JOptionPane.showMessageDialog(DeleteUpdatePaneRV.this, "Erreur lors de la suppression du rendez-vous:" + ee.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                	}
                
                     } else {
                    	 System.out.println(rv.getID());
                    	 UpdateRV updateRVFrame = new UpdateRV(rv);
                         
                    	 JFrame currentFrame = (JFrame) SwingUtilities.getWindowAncestor(DeleteUpdatePaneRV.this);
                         currentFrame.dispose();

                         
                         updateRVFrame.setVisible(true);
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
class DeleteUpdateRendererRV extends DefaultTableCellRenderer {
/**
	 * 
	 */
	private static final long serialVersionUID = 309122585876296401L;
private DeleteUpdatePaneRV DeleteUpdatePaneRV;
public DeleteUpdateRendererRV() {
    DeleteUpdatePaneRV = new DeleteUpdatePaneRV();
}
@Override
public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
    if (isSelected) {
        DeleteUpdatePaneRV.setBackground(table.getSelectionBackground());
    } else {
        DeleteUpdatePaneRV.setBackground(table.getBackground());
    }
   
    Long ID = (Long) table.getModel().getValueAt(row, 0);
    Date jour=(Date) table.getModel().getValueAt(row, 1);
    Long IDClient=(Long) table.getModel().getValueAt(row, 2);
    Long IDCreneau=(Long) table.getModel().getValueAt(row, 3);

    rv rv = new rv(ID,jour,IDClient,IDCreneau);
    DeleteUpdatePaneRV.setrvData(rv);

    return DeleteUpdatePaneRV;
}
}
 class DeleteUpdateEditorrv extends AbstractCellEditor implements TableCellEditor {
	private JTable table;
	private DeleteUpdatePaneRV DeleteUpdatePaneRV;
	  public DeleteUpdateEditorrv(JTable table) {
		  this.table=table;
		  DeleteUpdatePaneRV = new DeleteUpdatePaneRV();
		  DeleteUpdatePaneRV.addActionListener(new ActionListener() {
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
          return DeleteUpdatePaneRV.getState();
      }
	@Override
	public boolean isCellEditable(EventObject e){
	    return true;
	}
      

      @Override
      public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
    	  if (isSelected) {
              DeleteUpdatePaneRV.setBackground(table.getSelectionBackground());
          } else {
              DeleteUpdatePaneRV.setBackground(table.getBackground());
          }

          // Assuming the first column is of type Long
    	  Long ID = (Long) table.getModel().getValueAt(row, 0);
    	    Date jour=(Date) table.getModel().getValueAt(row, 1);
    	    Long IDClient=(Long) table.getModel().getValueAt(row, 2);
    	    Long IDCreneau=(Long) table.getModel().getValueAt(row, 3);

    	    rv rv = new rv(ID,jour,IDClient,IDCreneau);

          DeleteUpdatePaneRV.setrvData(rv);

          return DeleteUpdatePaneRV;
      }

}
