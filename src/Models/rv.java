package Models;

import java.util.Date;

public class rv {
	private long ID;
	private Date JOUR;
	private long ID_CLIENT;
	private long ID_CRENEAU;
	public rv(long id,Date jour,long id_client,long id_creneau) {
		ID=id;
		JOUR=jour;
		ID_CLIENT=id_client;
		ID_CRENEAU=id_creneau;
		
	}
	public rv(Date jour,long id_client,long id_creneau) {
		JOUR=jour;
		ID_CLIENT=id_client;
		ID_CRENEAU=id_creneau;
		
	}
	
	public long getID() {
		return ID;
	}
	public void setID(long iD) {
		ID = iD;
	}
	public Date getJOUR() {
		return JOUR;
	}
	public void setJOUR(Date jOUR) {
		JOUR = jOUR;
	}
	public long getID_CLIENT() {
		return ID_CLIENT;
	}
	public void setID_CLIENT(long iD_CLIENT) {
		ID_CLIENT = iD_CLIENT;
	}
	public long getID_CRENEAU() {
		return ID_CRENEAU;
	}
	public void setID_CRENEAU(long iD_CRENEAU) {
		ID_CRENEAU = iD_CRENEAU;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
