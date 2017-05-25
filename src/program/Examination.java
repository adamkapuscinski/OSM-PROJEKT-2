package program;

import java.util.Date;

/**
 * Klasa, za pomoc¹ której uzupe³niane jest badanie pacjenta
 * @author Adam Kapuœciñski, Rados³aw Bu³a
 */
public class Examination {
/** Ciœnienie krwi*/
private String bloodPressure="";
/** Têtno*/
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
	 * Metoda, za pomoc¹ której uzupe³niane jest badanie pacjenta
	 * @param date Data
	 * @param bloodpressure Ciœnienie krwi
	 * @param heartRate Têtno
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
 * Metoda ustawiaj¹ca datê
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
