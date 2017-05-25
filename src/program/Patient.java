package program;

import java.util.ArrayList;
import java.util.List;

/**
 * Klasa, za pomoc� kt�rej m�na uzupe�ni� danae pacjenta
 * @author Adam Kopu�ci�ski ,Rados�aw Bu�a
 */
public class Patient {
/** Imi�*/
private String firstName;
/** Nazwisko*/
private String surname;
/** Pesel*/
private String pesel;
/** P�e�*/
private String sex;
/** Badanie*/
private List<Examination> examinationsList;
/** Wyst�powanie wynik�w badania*/
private boolean isExamination;


private int id;

/**
 * Metoda umo�liwiaj�ca wype�nienie danych pacjnta
 * @param firstName Imi�
 * @param surname Nazwisko
 * @param pesel Pesel
 * @param sex P�e�
 * @param isExamination  Wyst�powanie wynik�w badania
 * @param examination Wyniki badania
 */

public Patient(){}


public Patient(String firstName, String surname, String pesel, String sex, boolean isExamination) {
	this.setFirstName(firstName);
	this.setSurname(surname);
	this.setPesel(pesel);
	this.setSex(sex);
	this.setExamination(isExamination);
	this.examinationsList = new ArrayList<Examination>();
}

public Patient(int id, String firstName, String surname, String pesel, String sex, boolean isExamination) {
	this.setId(id);
	this.setFirstName(firstName);
	this.setSurname(surname);
	this.setPesel(pesel);
	this.setSex(sex);
	this.setExamination(isExamination);
	this.examinationsList = new ArrayList<Examination>();
}





public int getId() {
	return id;
}

public void setId(int id) {
	this.id = id;
}

/**
 * Metoda zwracj�ca Imi�
 * @return Imi�
 */ 
public String getFirstName() {
	return firstName;
}

/**
 * Metoda ustawiaj�ca Imie
 * @param firstName Imi�
 */
public void setFirstName(String firstName) {
	this.firstName = firstName;
}

/**
 * Metoda zwracj�ca Nazwisko
 * @return Nazwisko
 */
public String getSurname() {
	return surname;
}

/**
 * Metoda ustawiaj�ca Nazwisko
 * @param surname Nazwisko
 */
public void setSurname(String surname) {
	this.surname = surname;
}

/**
 * Metoda zwracj�ca pesel
 * @return pesel
 */
public String getPesel() {
	return pesel;
}

/**
 * Metoda ustawiaj�ca Pesel
 * @param pesel pesel
 */
public void setPesel(String pesel) {
	this.pesel = pesel;
}

/**
 * Poierz p�e�
 * @return p�e�
 */
public String whatSex() {
	return sex;
}

/**
 * Metoda ustawiaj�ca p�e�
 * @param sex p�e�
 */
public void setSex(String sex) {
	this.sex = sex;
}

/**
 * Metoda, za pomoc� kt�rej uzupe�niamy tabel�
 * @return Tablica z danymi pacjent�w
 */
public Object[] toTable(){
	Object[] tablica = {getFirstName() , getSurname(), whatSex(), getPesel(), isExamination()};
	return tablica;
}

/**
 * Metoda sprawdzaj�ca czy s� wyniki badania
 * @return zmienna logiczna
 */
public boolean isExamination() {
	return isExamination;
}

/**
 * Metoda ustawiaj�caiaj�ca czy badanie zosta�o wykonane 
 * @param isExamination wyniki badania
 */
public void setExamination(boolean isExamination) {
	this.isExamination = isExamination;
}

public List<Examination> getExaminationsList() {
	return examinationsList;
}

public void setExaminationsList(List<Examination> examinationsList) {
	this.examinationsList = examinationsList;
}

}
