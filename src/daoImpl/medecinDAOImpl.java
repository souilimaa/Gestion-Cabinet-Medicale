package daoImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import DAO.dao;
import Models.Medecin;
import singleton.SingletonDB;

public class medecinDAOImpl implements dao<Medecin>{

	@Override
	public Medecin get(int id) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Medecin> getAll() throws SQLException {
		Connection connection=null;
		PreparedStatement ps=null;
		ResultSet rs =null;
		String query="SELECT * FROM medecins";
		List<Medecin> medecins=new ArrayList<Medecin>(5);
		try {
			SingletonDB singletonDB=SingletonDB.getInstance();
			connection=singletonDB.getConnection();
			ps=connection.prepareStatement(query);
	        rs = ps.executeQuery();
	        while(rs.next()) {
	        	Medecin med=new Medecin(rs.getLong(1),rs.getInt(2),rs.getString(3),rs.getString(4),rs.getString(5));
	        	medecins.add(med);
	        }
		}catch(SQLException e) {
            throw new SQLException("Error while getting medecins data", e);

		}finally {
			 if (rs != null) {
			        rs.close();
			    }
			    if (ps != null) {
			        ps.close();
			    }
         SingletonDB.getInstance().closeConnection();
     }

		
		
		return medecins;
	}

	@Override
	public int insert(Medecin medecin) throws SQLException {
		Connection connection=null;
		int rowsAffected=0;
		PreparedStatement ps=null;
		String query="INSERT INTO `medecins`(`VERSION`, `TITRE`, `NOM`, `PRENOM`) VALUES (?,?,?,?)";
		try {
			SingletonDB singletonDB=SingletonDB.getInstance();
			connection=singletonDB.getConnection();
			ps=connection.prepareStatement(query);
			ps.setInt(1,1);
			ps.setString(2,medecin.getTITRE());
			ps.setString(3,medecin.getNOM());
			ps.setString(4,medecin.getPRENOM());
			rowsAffected=ps.executeUpdate();
		}catch(SQLException e) {
            throw new SQLException("Error while inserting medecin data", e);
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
	public int update(Medecin medecin) throws SQLException {
		Connection connection = null;
	    PreparedStatement preparedStatement = null;
	    int rowsAffected = 0;
	    String query = "UPDATE medecins SET VERSION=?, TITRE=?, NOM=?, PRENOM=? WHERE ID=?";

	    try {
	        SingletonDB singletonDB = SingletonDB.getInstance();
	        connection = singletonDB.getConnection();
	        preparedStatement = connection.prepareStatement(query);
	        preparedStatement.setInt(1, medecin.getVERSION());
	        preparedStatement.setString(2, medecin.getTITRE());
	        preparedStatement.setString(3, medecin.getNOM());
	        preparedStatement.setString(4, medecin.getPRENOM());
	        preparedStatement.setLong(5, medecin.getID());

	        // Execute the update operation
	        rowsAffected = preparedStatement.executeUpdate();
	        System.out.println("Updated");
	    } catch (SQLException e) {
	        throw new SQLException("Error while updating medecin with ID " + medecin.getID(), e);
	    } finally {
	        if (preparedStatement != null) {
	            preparedStatement.close();
	        }
	        SingletonDB.getInstance().closeConnection();
	    }

	    return rowsAffected;
	}

	@Override
	public int delete(Medecin medecin) throws SQLException {
		Connection connection = null;
	    PreparedStatement preparedStatement = null;
	    int rowsAffected = 0;
	    String query = "DELETE FROM medecins WHERE ID = ?";

	    try {
	        SingletonDB singletonDB = SingletonDB.getInstance();
	        connection = singletonDB.getConnection();
	        preparedStatement = connection.prepareStatement(query);
	        preparedStatement.setLong(1, medecin.getID());

	        // Execute the delete operation
	        rowsAffected = preparedStatement.executeUpdate();
	        System.out.println("Deleted");
	    } catch (SQLException e) {
	        throw new SQLException("Error while deleting medecin with ID " + medecin.getID(), e);
	    } finally {
	        if (preparedStatement != null) {
	            preparedStatement.close();
	        }
	        SingletonDB.getInstance().closeConnection();
	    }

	    return rowsAffected;
	}
	
	
}
