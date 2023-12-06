package Models;

public class Client {
	private long ID;
	private int VERSION;
	private String TITRE;
	private String NOM;
	private String PRENOM;
	public Client(long id,int version,String titre,String nom,String prenom) {
		ID=id;
		this.VERSION=version;
		this.TITRE=titre;
		this.NOM=nom;
		this.PRENOM=prenom;
		
	}
	
	
	public Client(int version,String titre,String nom,String prenom) {
		this.VERSION=version;
		this.TITRE=titre;
		this.NOM=nom;
		this.PRENOM=prenom;
		
	}
	
	
	public long getID() {
		return ID;
	}





	public void setID(long iD) {
		ID = iD;
	}





	public int getVERSION() {
		return VERSION;
	}





	public void setVERSION(int vERSION) {
		VERSION = vERSION;
	}





	public String getTITRE() {
		return TITRE;
	}





	public void setTITRE(String tITRE) {
		TITRE = tITRE;
	}





	public String getNOM() {
		return NOM;
	}





	public void setNOM(String nOM) {
		NOM = nOM;
	}





	public String getPRENOM() {
		return PRENOM;
	}





	public void setPRENOM(String pRENOM) {
		PRENOM = pRENOM;
	}





	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
