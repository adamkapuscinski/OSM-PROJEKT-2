package program;

import java.util.Date;

/**
 * Klasa, za pomoc� kt�rej uzupe�niane jest badanie pacjenta
 * @author Adam Kapu�ci�ski, Rados�aw Bu�a
 */
public class Examination {
/** Zmienna Ci�nienie krwi*/
private String bloodPressure="";
/** Zmienna T�tno*/
private int heartRate;
/** Zmienna Data*/
private Date date;
/** Zmmienna w kt�rej przechowywana jest data badania */
private String dateString;
/** Zmmienna identyfikuj�cy pacjenta*/
private int ID_PACJENTA;
/** Zmmienna identyfikuj�cy badanie*/
private int ID;

/**
 * Metoda wygenerowana automatycznie
 */
	public Examination() {
		// TODO Auto-generated constructor stub
	}
	/**
	 * Metoda, za pomoc� kt�rej uzupe�niane jest badanie pacjenta
	 * @param date Data
	 * @param bloodpressure Ci�nienie krwi
	 * @param heartRate T�tno
	 * @param ID_PACJENTA Numer identyfikuj�cy pacjenta
	 */
	public Examination(String date, String bloodpressure, int heartRate, int ID_PACJENTA){
		this.setDateString(date);
		this.setbloodPressure(bloodpressure);
		this.setheartRate(heartRate);
		this.setID_PACJENTA(ID_PACJENTA);
		
	}
	/**
	 * Metoda, za pomoc� kt�rej uzupe�niane jest badanie pacjenta
	 * @param date Data
	 * @param bloodpressure Ci�nienie krwi
	 * @param heartRate T�tno
	 * @param id Numer identyfikuj�cy pacjenta
	 * @param ID_PACJENTA Numer identyfikuj�cy pacjenta
	 */
	public Examination(int id, String date, String bloodpressure, int heartRate, int ID_PACJENTA){
		this.setID(id);
		this.setDateString(date);
		this.setbloodPressure(bloodpressure);
		this.setheartRate(heartRate);
		this.setID_PACJENTA(ID_PACJENTA);
		
	}

/**
 * Metoda, za pomoc� kt�rej uzupe�niamy tabel�
 * @return Tablica z danymi pacjent�w
 */
public Object[] toTable(){
	Object[] tablica = {getDateString(), getbloodPressure(), getheartRate(), getID()};
	return tablica;
}
/**
 * Metoda zwracj�ca ci�nienie krwi
 * @return Ci�nienie krwi
 */
public String getbloodPressure() {
	return bloodPressure;
}
/**
 * Metoda ustawiaj�ca warto�c zmiennej ci�nienie krwi
 * @param bloodPressure Ci�nienie krwi
 */
public void setbloodPressure(String bloodPressure) {
	this.bloodPressure = bloodPressure;
}
/**
 * Metoda zwracj�ca T�tno
 * @return T�tno
 */
public int getheartRate() {
	return heartRate;
}
/**
 * Metoda ustawiaj�ca warto�� zmiennej t�tno
 * @param heartRate T�tno
 */
public void setheartRate(int heartRate) {
	this.heartRate = heartRate;
}
/**
 * Metoda zwracj�ca dat�
 * @return Data 
 */
public Date getDate() {
	return date;
}

/**
 * Metoda zwracaj�ca dat�
 * @return dateString Data
 */
public String getDateString() {
	return dateString;
}
/**
 * Metoda ustawiaj�ca dat�
 * @param dateString Data
 */
public void setDateString(String dateString) {
	this.dateString = dateString;
}
/**
 * Metoda zwracaj�ca ID pacjenta
 * @return ID_PACJENTA ID pacjenta
 */
public int getID_PACJENTA() {
	return ID_PACJENTA;
}
/**
 * Metoda ustawiaj�ca ID pacjenta
 * @param iD_PACJENTA ID pacjenta
 */
public void setID_PACJENTA(int iD_PACJENTA) {
	ID_PACJENTA = iD_PACJENTA;
}
/**
 * Metoda zwracaj�ca ID badania
 * @return iD ID badania
 */
public int getID() {
	return ID;
}
/**
 * Metoda ustawiaj�ca ID badania
 * @param iD ID badania
 */
public void setID(int iD) {
	ID = iD;
}

}
