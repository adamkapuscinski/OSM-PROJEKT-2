package program;

import java.util.ArrayList;
import java.util.List;

/**
 * Klasa, za pomoc¹ której mo¿na uzupe³niæ dane pacjenta
 * @author Adam Kopuœciñski ,Rados³aw Bu³a
 */
public class Patient {
/** Imiê*/
private String firstName;
/** Nazwisko*/
private String surname;
/** Pesel*/
private String pesel;
/** P³eæ*/
private String sex;
/** Badanie*/
private List<Examination> examinationsList;
/** Wystêpowanie wyników badania*/
private boolean isExamination;
/** ID */
private int id;

/** Konstruktor bezparametrowy*/
public Patient(){}

/**
 * Konstruktor umo¿liwiaj¹cy wype³nienie danych pacjnta
 * @param firstName Imiê
 * @param surname Nazwisko
 * @param pesel Pesel
 * @param sex P³eæ
 * @param isExamination  Wystêpowanie wyników badania
 */
public Patient(String firstName, String surname, String pesel, String sex, boolean isExamination) {
	this.setFirstName(firstName);
	this.setSurname(surname);
	this.setPesel(pesel);
	this.setSex(sex);
	this.setExamination(isExamination);
	this.examinationsList = new ArrayList<Examination>();
}

/**
 * Metoda umo¿liwiaj¹ca wype³nienie danych pacjnta
 * @param id ID
 * @param firstName Imiê
 * @param surname Nazwisko
 * @param pesel Pesel
 * @param sex P³eæ
 * @param isExamination  Wystêpowanie wyników badania
 */
public Patient(int id, String firstName, String surname, String pesel, String sex, boolean isExamination) {
	this.setId(id);
	this.setFirstName(firstName);
	this.setSurname(surname);
	this.setPesel(pesel);
	this.setSex(sex);
	this.setExamination(isExamination);
	this.examinationsList = new ArrayList<Examination>();
}



/**
 * Metoda zwracj¹ca ID
 * @return ID 
 */ 
public int getId() {
	return id;
}
/**
 * Metoda ustawiaj¹ca ID
 * @param id ID
 */ 
public void setId(int id) {
	this.id = id;
}

/**
 * Metoda zwracj¹ca Imiê
 * @return Imiê
 */ 
public String getFirstName() {
	return firstName;
}

/**
 * Metoda ustawiaj¹ca Imie
 * @param firstName Imiê
 */
public void setFirstName(String firstName) {
	this.firstName = firstName;
}

/**
 * Metoda zwracj¹ca Nazwisko
 * @return Nazwisko
 */
public String getSurname() {
	return surname;
}

/**
 * Metoda ustawiaj¹ca Nazwisko
 * @param surname Nazwisko
 */
public void setSurname(String surname) {
	this.surname = surname;
}

/**
 * Metoda zwracj¹ca pesel
 * @return pesel
 */
public String getPesel() {
	return pesel;
}

/**
 * Metoda ustawiaj¹ca Pesel
 * @param pesel pesel
 */
public void setPesel(String pesel) {
	this.pesel = pesel;
}

/**
 * Poierz p³eæ
 * @return p³eæ
 */
public String whatSex() {
	return sex;
}

/**
 * Metoda ustawiaj¹ca p³eæ
 * @param sex p³eæ
 */
public void setSex(String sex) {
	this.sex = sex;
}

/**
 * Metoda, za pomoc¹ której uzupe³niamy tabelê
 * @return Tablica z danymi pacjentów
 */
public Object[] toTable(){
	Object[] tablica = {getFirstName() , getSurname(), whatSex(), getPesel(), isExamination()};
	return tablica;
}

/**
 * Metoda sprawdzaj¹ca czy s¹ wyniki badania
 * @return zmienna logiczna
 */
public boolean isExamination() {
	return isExamination;
}

/**
 * Metoda ustawiaj¹caiaj¹ca czy badanie zosta³o wykonane 
 * @param isExamination wyniki badania
 */
public void setExamination(boolean isExamination) {
	this.isExamination = isExamination;
}
/**
 * Metoda zwracaj¹ca listê badañ
 * @return lista badañ
 */
public List<Examination> getExaminationsList() {
	return examinationsList;
}
/**
 * Metoda ustawiaj¹caiaj¹ca listê badañ
 * @param examinationsList lista badañ
 */
public void setExaminationsList(List<Examination> examinationsList) {
	this.examinationsList = examinationsList;
}

}
