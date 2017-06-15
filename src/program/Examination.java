package program;

import java.util.Date;

/**
 * Klasa, za pomoc¹ której uzupe³niane jest badanie pacjenta
 * @author Adam Kapuœciñski, Rados³aw Bu³a
 */
public class Examination {
/** Zmienna Ciœnienie krwi*/
private String bloodPressure="";
/** Zmienna Têtno*/
private int heartRate;
/** Zmienna Data*/
private Date date;
/** Zmmienna w której przechowywana jest data badania */
private String dateString;
/** Zmmienna identyfikuj¹cy pacjenta*/
private int ID_PACJENTA;
/** Zmmienna identyfikuj¹cy badanie*/
private int ID;

/**
 * Metoda wygenerowana automatycznie
 */
	public Examination() {
		// TODO Auto-generated constructor stub
	}
	/**
	 * Metoda, za pomoc¹ której uzupe³niane jest badanie pacjenta
	 * @param date Data
	 * @param bloodpressure Ciœnienie krwi
	 * @param heartRate Têtno
	 * @param ID_PACJENTA Numer identyfikuj¹cy pacjenta
	 */
	public Examination(String date, String bloodpressure, int heartRate, int ID_PACJENTA){
		this.setDateString(date);
		this.setbloodPressure(bloodpressure);
		this.setheartRate(heartRate);
		this.setID_PACJENTA(ID_PACJENTA);
		
	}
	/**
	 * Metoda, za pomoc¹ której uzupe³niane jest badanie pacjenta
	 * @param date Data
	 * @param bloodpressure Ciœnienie krwi
	 * @param heartRate Têtno
	 * @param id Numer identyfikuj¹cy pacjenta
	 * @param ID_PACJENTA Numer identyfikuj¹cy pacjenta
	 */
	public Examination(int id, String date, String bloodpressure, int heartRate, int ID_PACJENTA){
		this.setID(id);
		this.setDateString(date);
		this.setbloodPressure(bloodpressure);
		this.setheartRate(heartRate);
		this.setID_PACJENTA(ID_PACJENTA);
		
	}

/**
 * Metoda, za pomoc¹ której uzupe³niamy tabelê
 * @return Tablica z danymi pacjentów
 */
public Object[] toTable(){
	Object[] tablica = {getDateString(), getbloodPressure(), getheartRate(), getID()};
	return tablica;
}
/**
 * Metoda zwracj¹ca ciœnienie krwi
 * @return Ciœnienie krwi
 */
public String getbloodPressure() {
	return bloodPressure;
}
/**
 * Metoda ustawiaj¹ca wartoœc zmiennej ciœnienie krwi
 * @param bloodPressure Ciœnienie krwi
 */
public void setbloodPressure(String bloodPressure) {
	this.bloodPressure = bloodPressure;
}
/**
 * Metoda zwracj¹ca Têtno
 * @return Têtno
 */
public int getheartRate() {
	return heartRate;
}
/**
 * Metoda ustawiaj¹ca wartoœæ zmiennej têtno
 * @param heartRate Têtno
 */
public void setheartRate(int heartRate) {
	this.heartRate = heartRate;
}
/**
 * Metoda zwracj¹ca datê
 * @return Data 
 */
public Date getDate() {
	return date;
}

/**
 * Metoda zwracaj¹ca datê
 * @return dateString Data
 */
public String getDateString() {
	return dateString;
}
/**
 * Metoda ustawiaj¹ca datê
 * @param dateString Data
 */
public void setDateString(String dateString) {
	this.dateString = dateString;
}
/**
 * Metoda zwracaj¹ca ID pacjenta
 * @return ID_PACJENTA ID pacjenta
 */
public int getID_PACJENTA() {
	return ID_PACJENTA;
}
/**
 * Metoda ustawiaj¹ca ID pacjenta
 * @param iD_PACJENTA ID pacjenta
 */
public void setID_PACJENTA(int iD_PACJENTA) {
	ID_PACJENTA = iD_PACJENTA;
}
/**
 * Metoda zwracaj¹ca ID badania
 * @return iD ID badania
 */
public int getID() {
	return ID;
}
/**
 * Metoda ustawiaj¹ca ID badania
 * @param iD ID badania
 */
public void setID(int iD) {
	ID = iD;
}

}
