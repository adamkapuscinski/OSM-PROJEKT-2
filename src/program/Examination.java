package program;

import java.util.Date;

/**
 * Klasa, za pomoc� kt�rej uzupe�niane jest badanie pacjenta
 * @author Adam Kapu�ci�ski, Rados�aw Bu�a
 */
public class Examination {
/** Ci�nienie krwi*/
private String bloodPressure="";
/** T�tno*/
private int heartRate;
/** Data*/
private Date date;

/** Data*/
private String dateString;


private String hour;


private int ID_PACJENTA;

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
	 */
	public Examination(String date, String bloodpressure, int heartRate, int ID_PACJENTA){
		this.setDateString(date);
		this.setbloodPressure(bloodpressure);
		this.setheartRate(heartRate);
		this.setID_PACJENTA(ID_PACJENTA);
		
	}
	
	public Examination(int id, String date, String bloodpressure, int heartRate, int ID_PACJENTA){
		this.setID(id);
		this.setDateString(date);
		this.setbloodPressure(bloodpressure);
		this.setheartRate(heartRate);
		this.setID_PACJENTA(ID_PACJENTA);
		
	}
	
	public Examination(String date,String hour, String bloodpressure, int heartRate, int ID_PACJENTA){
		
		this.setDateString(date);
		this.setbloodPressure(bloodpressure);
		this.setheartRate(heartRate);
		this.setID_PACJENTA(ID_PACJENTA);
		
	}
	public Examination(int id, String date,String hour, String bloodpressure, int heartRate, int ID_PACJENTA){
		this.setID(id);
		this.setHour(hour);
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
 * Metoda ustawiaj�ca dat�
 * @param date Data
 */
public void setDate(Date date) {
	this.date = date;
}
public String getDateString() {
	return dateString;
}
public void setDateString(String dateString) {
	this.dateString = dateString;
}
public int getID_PACJENTA() {
	return ID_PACJENTA;
}
public void setID_PACJENTA(int iD_PACJENTA) {
	ID_PACJENTA = iD_PACJENTA;
}
public int getID() {
	return ID;
}
public void setID(int iD) {
	ID = iD;
}
public String getHour() {
	return hour;
}
public void setHour(String hour) {
	this.hour = hour;
}
	
	
	
}
