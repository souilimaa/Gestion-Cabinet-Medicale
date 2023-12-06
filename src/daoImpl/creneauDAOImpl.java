package daoImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Models.Medecin;
import Models.creneau;
import singleton.SingletonDB;
import DAO.dao;

public class creneauDAOImpl implements dao<creneau>{

	@Override
	public creneau get(int id) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List  getAll() throws SQLException {
		Connection connection=null;
		PreparedStatement ps=null;
		ResultSet rs =null;
		String query="SELECT * FROM creneaux";
		List<creneau> creneaux=new ArrayList<creneau>(5);
		try {
			SingletonDB singletonDB=SingletonDB.getInstance();
			connection=singletonDB.getConnection();
			ps=connection.prepareStatement(query);
	        rs = ps.executeQuery();
	        while(rs.next()) {
	        	creneau c=new creneau(rs.getLong(1),rs.getInt(2),rs.getInt(3),rs.getInt(4),rs.getInt(5),rs.getInt(6),rs.getLong(7));
	        	creneaux.add(c);
	        }
		}catch(SQLException e) {
            throw new SQLException("Error while getting creneaux data", e);

		}finally {
			 if (rs != null) {
			        rs.close();
			    }
			    if (ps != null) {
			        ps.close();
			    }
         SingletonDB.getInstance().closeConnection();
     }

		
		
		return creneaux;
	}

	@Override
	public int insert(creneau creneau) throws SQLException {
		Connection connection=null;
		int rowsAffected=0;
		PreparedStatement ps=null;
		String query="INSERT INTO `creneaux`(`VERSION`, `HDEBUT`, `MDEBUT`, `HFIN`, `MFIN`, `ID_MEDECIN`) VALUES (?,?,?,?,?,?)";
		try {
			SingletonDB singletonDB=SingletonDB.getInstance();
			connection=singletonDB.getConnection();
			ps=connection.prepareStatement(query);
			ps.setInt(1,1);
			ps.setInt(2,creneau.getHDEBUT());
			ps.setInt(3,creneau.getMDEBUT());
			ps.setInt(4,creneau.getHFIN());
			ps.setInt(5, creneau.getMFIN());
			ps.setLong(6, creneau.getID_MEDECIN());
			rowsAffected=ps.executeUpdate();
		}catch(SQLException e) {
            throw new SQLException("Error while inserting creneau data", e);
		}
		finally {
			    if (ps != null) {
			        ps.close();
			    }
            SingletonDB.getInstance().closeConnection();

		}
		return rowsAffected;
		
	}

	@Override
	public int update(creneau creneau) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(creneau creneau) throws SQLException {
		 Connection connection = null;
		    PreparedStatement preparedStatement = null;
		    int rowsAffected = 0;
		    String query = "DELETE FROM creneaux WHERE ID = ?";

		    try {
		        SingletonDB singletonDB = SingletonDB.getInstance();
		        connection = singletonDB.getConnection();
		        preparedStatement = connection.prepareStatement(query);
		        preparedStatement.setLong(1, creneau.getID());

		        // Execute the delete operation
		        rowsAffected = preparedStatement.executeUpdate();
		        System.out.println("Deleted");
		    } catch (SQLException e) {
		        throw new SQLException("Error while deleting creneau with ID " + creneau.getID(), e);
		    } finally {
		        if (preparedStatement != null) {
		            preparedStatement.close();
		        }
		        SingletonDB.getInstance().closeConnection();
		    }

		    return rowsAffected;
	}
	

}
