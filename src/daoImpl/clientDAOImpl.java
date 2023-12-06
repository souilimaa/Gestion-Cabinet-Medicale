package daoImpl;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import DAO.dao;
import Models.Client;
import Models.Medecin;
import singleton.SingletonDB;
public class clientDAOImpl implements dao<Client>{
	
	
	@Override
	public Client get(int id) throws SQLException {
        Connection connection = null;
        Client client=null;
		String query="Select * from clients where ID=?";
		PreparedStatement preparedStatement=null;
		ResultSet rs =null;
		try {
			
        SingletonDB singletonDB = SingletonDB.getInstance();
        connection = singletonDB.getConnection();
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setLong(1, id);
        rs = preparedStatement.executeQuery();
		while(rs.next()) {
			long Id=rs.getLong(1);
			int version=rs.getInt(2);
			String titre=rs.getString(3);
			String nom=rs.getString(4);
			String prenom=rs.getString(5);
			client=new Client(Id,version,titre,nom,prenom);
		}
		 } catch (SQLException e) {
	            throw new SQLException("Error while fetching client with ID " + id, e);
	           }
		finally {
			 if (rs != null) {
			        rs.close();
			    }
			    if (preparedStatement != null) {
			        preparedStatement.close();
			    }
            SingletonDB.getInstance().closeConnection();
        }

		return client;
	}

	@Override
	public List getAll() throws SQLException {
		Connection connection=null;
		PreparedStatement ps=null;
		ResultSet rs =null;
		String query="SELECT * FROM clients";
		List<Client> clients=new ArrayList<Client>(5);
		try {
			SingletonDB singletonDB=SingletonDB.getInstance();
			connection=singletonDB.getConnection();
			ps=connection.prepareStatement(query);
	        rs = ps.executeQuery();
	        while(rs.next()) {
	        	Client c=new Client(rs.getLong(1),rs.getInt(2),rs.getString(3),rs.getString(4),rs.getString(5));
	        	clients.add(c);
	        }
		}catch(SQLException e) {
            throw new SQLException("Error while getting clients data", e);

		}finally {
			 if (rs != null) {
			        rs.close();
			    }
			    if (ps != null) {
			        ps.close();
			    }
         SingletonDB.getInstance().closeConnection();
     }

		
		
		return clients;
	}

	@Override
	public int insert(Client client) throws SQLException {
		Connection connection=null;
		int rowsAffected=0;
		PreparedStatement ps=null;
		String query="INSERT INTO `clients`(`VERSION`, `TITRE`, `NOM`, `PRENOM`) VALUES (?,?,?,?)";
		try {
			SingletonDB singletonDB=SingletonDB.getInstance();
			connection=singletonDB.getConnection();
			ps=connection.prepareStatement(query);
			ps.setInt(1,client.getVERSION());
			ps.setString(2,client.getTITRE());
			ps.setString(3,client.getNOM());
			ps.setString(4,client.getPRENOM());
			rowsAffected=ps.executeUpdate();
		}catch(SQLException e) {
            throw new SQLException("Error while inserting client data", e);
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
	public int update(Client client) throws SQLException {
		Connection connection = null;
	    PreparedStatement preparedStatement = null;
	    int rowsAffected = 0;
	    String query = "UPDATE clients SET VERSION=?, TITRE=?, NOM=?, PRENOM=? WHERE ID=?";

	    try {
	        SingletonDB singletonDB = SingletonDB.getInstance();
	        connection = singletonDB.getConnection();
	        preparedStatement = connection.prepareStatement(query);
	        preparedStatement.setInt(1, client.getVERSION());
	        preparedStatement.setString(2, client.getTITRE());
	        preparedStatement.setString(3, client.getNOM());
	        preparedStatement.setString(4, client.getPRENOM());
	        preparedStatement.setLong(5, client.getID());

	        // Execute the update operation
	        rowsAffected = preparedStatement.executeUpdate();
	        System.out.println("Updated");
	    } catch (SQLException e) {
	        throw new SQLException("Error while updating client with ID " + client.getID(), e);
	    } finally {
	        if (preparedStatement != null) {
	            preparedStatement.close();
	        }
	        SingletonDB.getInstance().closeConnection();
	    }

	    return rowsAffected;
	}

	@Override
	public int delete(Client client) throws SQLException {
		Connection connection = null;
	    PreparedStatement preparedStatement = null;
	    int rowsAffected = 0;
	    String query = "DELETE FROM clients WHERE ID = ?";

	    try {
	        SingletonDB singletonDB = SingletonDB.getInstance();
	        connection = singletonDB.getConnection();
	        preparedStatement = connection.prepareStatement(query);
	        preparedStatement.setLong(1, client.getID());

	        // Execute the delete operation
	        rowsAffected = preparedStatement.executeUpdate();
	    } catch (SQLException e) {
	        throw new SQLException("Error while deleting client with ID " + client.getID(), e);
	    } finally {
	        if (preparedStatement != null) {
	            preparedStatement.close();
	        }
	        SingletonDB.getInstance().closeConnection();
	    }

	    return rowsAffected;
	}
	
	
	
	
	
	
	
	
		
}
