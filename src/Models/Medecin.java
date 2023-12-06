package Models;

public class Medecin {
	private long ID;
	private int VERSION;
	private String TITRE;
	private String NOM;
	private String PRENOM;
	public Medecin(long id,int version,String titre,String nom,String prenom) {
		ID=id;
		VERSION=version;
		TITRE=titre;
		NOM=nom;
		PRENOM=prenom;
		
	}
	public Medecin(int version,String titre,String nom,String prenom) {
		VERSION=version;
		TITRE=titre;
		NOM=nom;
		PRENOM=prenom;
		
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
