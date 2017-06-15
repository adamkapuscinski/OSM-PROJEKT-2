package program;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Klasa sluzaca do polaczenia z lokalna baza danych w ktorej przechowywane sa informacje o pacjentach i badaniach
 *  @author Adam Kapuœciñski, Rados³aw Bu³a
 */
public class JDBC {
	/**
	 * Obiekt polaczenia z baza danych
	 */
	private Connection connection;
	/**
	 * Obiekt sluzacy do wysylania zapytan do bazy danych
	 */
	private Statement statement;
	/**
	 * Nazwa tabeli pacjentow
	 */
	private String patientsTableName;
	/**
	 * Nazwa tabeli badan
	 */
	private String examinationsTableName;
	
	/**
	 * Konstruktor klasy, ustanawia polaczenie z baza danych i tworzy tabele jesli nie isnieja
	 */
	public JDBC(){
		try {
			this.patientsTableName = "Pacjenci";
			this.examinationsTableName = "Badania";
			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection("jdbc:sqlite:./patients.db");
			statement = connection.createStatement();
			statement.execute("PRAGMA foreign_keys=ON");
			createPatientsTable();
			createExaminationsTable();
		} catch (ClassNotFoundException e) {
			System.err.println(e.getMessage());
		} catch (SQLException e) {
			System.err.println(e.getSQLState());
		}
	}
	
	/**
	 * Metoda zamykajaca polaczenie z baza danych
	 */
	public void closeConnection(){
		try {
			statement.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Jesli tabela pacjentow nie istnieje, to jest tworzona
	 */
	public void createPatientsTable(){
		try {
			String createTable = "CREATE TABLE IF NOT EXISTS " + patientsTableName  
				+ " (ID_PACJENTA INTEGER PRIMARY KEY AUTOINCREMENT, " 
				+ " IMIE VARCHAR(255) NOT NULL, " 
				+ " NAZWISKO VARCHAR(255) NOT NULL, " 
				+ " PESEL VARCHAR(255) NOT NULL, " 
				+ " PLEC CHARACTER(20) NOT NULL, "
				+ " BADANIE BOOLEAN NOT NULL);" ;
			
			statement.executeUpdate(createTable);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Jesli tabela z wynikami badan nie istnieje, to jest tworzona
	 */
	public void createExaminationsTable(){
		try {
			String createTable = "CREATE TABLE IF NOT EXISTS " + examinationsTableName  
				+ " (ID_BADANIA INTEGER, " 
				+ " DATA VARCHAR(255) NOT NULL, " 
				+ " CISNIENIE VARCHAR(255) NOT NULL, " 
				+ " TETNO INTEGER NOT NULL, " 
				+ " ID_PACJENTA INTEGER,"
				+ " PRIMARY KEY(ID_BADANIA ASC),"
				+ " FOREIGN KEY(ID_PACJENTA) REFERENCES Pacjenci(ID_PACJENTA) ON DELETE CASCADE);"; 
			
			statement.executeUpdate(createTable);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Metoda pozwalajaca na usuniecie tabeli z bazy danych
	 * @param tableName Tabela jaka ma zostac usunieta
	 */
	public void dropTable(String tableName) {
		try {
			statement.executeUpdate("DROP TABLE IF EXISTS " + tableName);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Metoda pozwalajaca na dodanie nowego pacjenta do bazy danych
	 * @param patient pacjent
	 */
	public void insertPatient(Patient patient){
		try {
			PreparedStatement preparedStatement = connection.prepareStatement("INSERT OR IGNORE INTO " 
					+ patientsTableName + " (IMIE, NAZWISKO, PESEL, PLEC, BADANIE) "
					+ "VALUES (?, ?, ?, ?, ?);");
			
			preparedStatement.setString(1, patient.getFirstName());
			preparedStatement.setString(2, patient.getSurname());
			preparedStatement.setString(3, patient.getPesel());
			preparedStatement.setString(4, patient.whatSex());
			preparedStatement.setBoolean(5, patient.isExamination());
			
			preparedStatement.executeUpdate();
			preparedStatement.close();
					
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Metoda pozwalajaca na dodanie dodanie badania do okreslonego pacjenta
	 * @param patient pacjent
	 * @param examination badanie
	 */
	public void insertExamination(Patient patient, Examination examination) {
		try {
			
			PreparedStatement preparedStatement1 = connection.prepareStatement("INSERT OR IGNORE INTO " 
			+ examinationsTableName + " (DATA, CISNIENIE, TETNO, ID_PACJENTA ) "
					+ "VALUES (?, ?, ?, ?);");
			
			
			preparedStatement1.setString(1, examination.getDateString());
			preparedStatement1.setString(2, examination.getbloodPressure());
			preparedStatement1.setInt(3, examination.getheartRate());
			preparedStatement1.setInt(4, examination.getID_PACJENTA());
			
			preparedStatement1.executeUpdate();
			preparedStatement1.close();
			
			ResultSet examination_ID = statement.executeQuery("select last_insert_rowid(); ");
			
			examination.setID(examination_ID.getInt(1));
	
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Metoda pozwalajaca na usuniecie pacjenta z bazy danych
	 * @param patient pacjent
	 */
	public void removePatient(Patient patient){
		try {
			PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM " + patientsTableName + " "
					+ "WHERE ID_PACJENTA=?;");	
			preparedStatement.setInt(1, patient.getId());
			preparedStatement.executeUpdate();
			preparedStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Metoda pozwalajaca na pobranie wszystkich rekordow z tabeli pacjentow
	 * @return lista pacjentów
	 */
	public List<Patient> selectAllPatients(){
		List<Patient> patientsList = null;
		try {
			String selectPatient = "SELECT * FROM " + patientsTableName + ";";		
			ResultSet resultSet = statement.executeQuery(selectPatient);
			patientsList = new ArrayList<Patient>();
			while (resultSet.next()){
					patientsList.add(new Patient(resultSet.getInt(1), resultSet.getString(2), 
							resultSet.getString(3), resultSet.getString(4),  resultSet.getString(5), resultSet.getBoolean(6)));
					//select badania danego pacjenta
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}		
		return patientsList;
	}
	
	
	/**
	 * Metoda pozwalajaca na aktualizacje informacji o pacjencie
	 * @param patient pacjent
	 */
	public void updatePatient(Patient patient) {
		try {
			PreparedStatement preparedStatement = connection.prepareStatement("UPDATE " + patientsTableName + " SET "
					+ "IMIE=?, NAZWISKO=?, PESEL=?, PLEC=?, BADANIE=? WHERE ID_PACJENTA=?;");

			
			preparedStatement.setString(1, patient.getFirstName());
			preparedStatement.setString(2, patient.getSurname());
			preparedStatement.setString(3, patient.getPesel());
			preparedStatement.setString(4, patient.whatSex());
			preparedStatement.setBoolean(5, patient.isExamination());
			preparedStatement.setInt(6, patient.getId());
			
			preparedStatement.executeUpdate();
			preparedStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Metoda pozwalajaca na pobranie wszystkich rekordow z tabeli badañ
	 * @param patient pacjent
	 * @return lista pacjentów
	 */
	public List<Examination> selectExaminations(Patient patient) {
		List<Examination> examinationsList = new ArrayList<Examination>();
		try {
			PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM " + examinationsTableName + " "
					+ "WHERE ID_PACJENTA=?;");
			preparedStatement.setInt(1, patient.getId());
			
			
			ResultSet resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){
				examinationsList.add(new Examination(resultSet.getInt(1),
					resultSet.getString(2), 
					resultSet.getString(3), 
					resultSet.getInt(4), 
					resultSet.getInt(5)));
				
			}
			
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		} 
		return examinationsList;
	}
	
	/**
	 * Metoda pozwalajaca na uaktualnienie informacji o badaniu
	 * @param examination badanie
	 */
	public void updateExamination(Examination examination) {
		try {
			PreparedStatement preparedStatement = connection.prepareStatement("UPDATE " + examinationsTableName + " SET "
					+ "DATA=?, CISNIENIE=?, TETNO=? WHERE ID_BADANIA=?;");
			
			preparedStatement.setString(1, examination.getDateString());
			preparedStatement.setString(2, examination.getbloodPressure());
			preparedStatement.setInt(3, examination.getheartRate());
			preparedStatement.setInt(4, examination.getID());
			
			preparedStatement.executeUpdate();
			preparedStatement.close();
		} catch (SQLException e) {
			System.err.println(e.getSQLState());
		}
	}
	
	/**
	 * Metoda pozwalajaca na usuniêcie badania
	 * @param examination badanie
	 */
	public void removeExamination(Examination examination){
		try {
			PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM " + examinationsTableName + " "
					+ "WHERE ID_BADANIA=?;");	
			preparedStatement.setInt(1, examination.getID());
			preparedStatement.executeUpdate();
			preparedStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	
}