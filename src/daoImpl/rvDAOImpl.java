package daoImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import DAO.dao;
import Models.rv;
import singleton.SingletonDB;

public class rvDAOImpl implements dao<rv> {

	@Override
	public rv get(int id) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List getAll() throws SQLException {
		Connection connection = null;
	    PreparedStatement ps = null;
	    java.util.List<rv> rvs = new java.util.ArrayList<>(5);
	    ResultSet rs = null;
	    String query = "SELECT * FROM rv";

	    try {
	        SingletonDB singletonDB = SingletonDB.getInstance();
	        connection = singletonDB.getConnection();
	        ps = connection.prepareStatement(query);
	        rs = ps.executeQuery();
	        while (rs.next()) {
	            java.util.Date jourUtilDate = rs.getDate("JOUR");
	            rv r = new rv(jourUtilDate, rs.getLong("ID_CLIENT"), rs.getLong("ID_CRENEAU"));
	            r.setID(rs.getLong("ID"));
	            rvs.add(r);
	        }
	    } catch (SQLException e) {
	        throw new SQLException("Error while getting rv data", e);
	    } finally {
	        if (rs != null) {
	            rs.close();
	        }
	        if (ps != null) {
	            ps.close();
	        }
	        SingletonDB.getInstance().closeConnection();
	    }

	    return rvs;
	}

	@Override
	public int insert(rv rv) throws SQLException {
		Connection connection=null;
		int rowsAffected=0;
		PreparedStatement ps=null;
		String query="INSERT INTO `rv`(`JOUR`, `ID_CLIENT`, `ID_CRENEAU`) VALUES (?,?,?)";
		try {
			SingletonDB singletonDB=SingletonDB.getInstance();
			connection=singletonDB.getConnection();
			ps=connection.prepareStatement(query);
			java.util.Date jourUtilDate = rv.getJOUR();
			java.sql.Date sqlDate = new java.sql.Date(jourUtilDate.getTime());
			ps.setDate(1, sqlDate);
			ps.setLong(2,rv.getID_CLIENT());
			ps.setLong(3,rv.getID_CRENEAU());
			rowsAffected=ps.executeUpdate();
		}catch(SQLException e) {
            throw new SQLException("Error while inserting rv data", e);
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
	public int update(rv rv) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(rv rv) throws SQLException {
		 Connection connection = null;
		    PreparedStatement preparedStatement = null;
		    int rowsAffected = 0;
		    String query = "DELETE FROM rv WHERE ID = ?";

		    try {
		        SingletonDB singletonDB = SingletonDB.getInstance();
		        connection = singletonDB.getConnection();
		        preparedStatement = connection.prepareStatement(query);
		        preparedStatement.setLong(1, rv.getID());

		        // Execute the delete operation
		        rowsAffected = preparedStatement.executeUpdate();
		        System.out.println("Deleted");
		    } catch (SQLException e) {
		        throw new SQLException("Error while deleting rv with ID " + rv.getID(), e);
		    } finally {
		        if (preparedStatement != null) {
		            preparedStatement.close();
		        }
		        SingletonDB.getInstance().closeConnection();
		    }

		    return rowsAffected;
	}
	
}
