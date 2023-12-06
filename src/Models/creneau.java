package Models;

public class creneau {
	private long ID;
	private int VERSION;
	private int HDEBUT;
	private int MDEBUT;
	private int HFIN;
	private int MFIN;
	private long ID_MEDECIN;
	
	public creneau(long id,int version,int hdebut,int mdebut,int hfin,int mfin,long id_medecin) {
		ID=id;
		VERSION=version;
		HDEBUT=hdebut;
		MDEBUT=mdebut;
		HFIN=hfin;
		MFIN=mfin;
		ID_MEDECIN=id_medecin;
		
	}
	
	public creneau(int version,int hdebut,int mdebut,int hfin,int mfin,long id_medecin) {
		VERSION=version;
		HDEBUT=hdebut;
		MDEBUT=mdebut;
		HFIN=hfin;
		MFIN=mfin;
		ID_MEDECIN=id_medecin;
		
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

	public int getHDEBUT() {
		return HDEBUT;
	}

	public void setHDEBUT(int hDEBUT) {
		HDEBUT = hDEBUT;
	}

	public int getMDEBUT() {
		return MDEBUT;
	}

	public void setMDEBUT(int mDEBUT) {
		MDEBUT = mDEBUT;
	}

	public int getHFIN() {
		return HFIN;
	}

	public void setHFIN(int hFIN) {
		HFIN = hFIN;
	}

	public int getMFIN() {
		return MFIN;
	}

	public void setMFIN(int mFIN) {
		MFIN = mFIN;
	}

	public long getID_MEDECIN() {
		return ID_MEDECIN;
	}

	public void setID_MEDECIN(long iD_MEDECIN) {
		ID_MEDECIN = iD_MEDECIN;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
