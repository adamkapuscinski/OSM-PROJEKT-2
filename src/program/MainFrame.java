package program;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import org.jfree.ui.RefineryUtilities;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFormattedTextField.AbstractFormatter;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpinnerDateModel;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import org.jdatepicker.impl.*;
import org.jfree.ui.RefineryUtilities;

import javafx.scene.control.DatePicker;

import java.awt.Toolkit;





/**
 * Klasa, za pomoc� kt�rej tworzone jest g��wne okno programu 
 * @author Adam Kopu�ci�ski ,Rados�aw Bu�a
 */
public class MainFrame extends JFrame implements WindowListener {
	
	
	private JDBC JDBC;
	
	/** Flaga m�wi�ca czy nast�pi�o klikni�cie mysz� */
	private boolean isClicked=false, isClickedExamination=false;
	/** Pola tekstowe: Imi�, Nazwisko, Pesel, Ci�nienie krwi, T�tno */
	private JTextField firstNameToFillIn, surnameToFillIn,peselToFillIn, bloodPresure, heartRate;
	/** Panele z:  wynikami badania, list� pacjent�w, danymi i wynikami, danymi*/
	private JPanel PatientTest, PatientList, PatientDataToConfigure, PatientData, PatientListToConfigure, ExaminationList;
	/** Panel przewijany z suwakiem*/
	private JScrollPane pane;
	/** Wybor p�ci*/
	private String sexToChoose = "";
	/**Przyciski: dodaj dane pacjenta, zapisz dane, anuluj dane, usu�, zapisz badanie, anuluj badanie  */
	private JButton addButtonPatientList, saveButtonPatientData,deconditionButtonPatientData, removeButtonPatientList, saveButtonPatientTest, deconditionButtonPatientTest, GeneratePressureGraph, GenerateHeartRateGraph, addButtonExaminationList, removeButtonExaminationList;
	/** Data*/
	Date date;
	/** Badanie*/
	private Examination examination;
	/** Model*/
	private DefaultTableModel model1, model2;
	/** Lista pacjent�w*/
	private List<Patient> patientsList;
	
	private List<Examination> examinations, examinationsBloodPressure;
	
	/** Przycisk wyboru p�ci: m�czyzna, kobieta*/
	private JRadioButton  gentlemanInscription, ladykinInscription;
	/** Wiersz tabeli: listy pacjent�w, listy bada�*/
	private int j , jb;
	
	
	private JDatePickerImpl datePicker;
	
	
	private JSpinner timeSpinner;

	
	private JTable table1, table2;
	
	private String dataString;
	
	JSpinner.DateEditor de;
	
	private int id;
	
	
	/**
	 * Konstruktor domy�ly, kt�ry ustawia tytu� aplikacji i pobiera domy�lny rozmiar okna
	 * 
	 */
	public MainFrame() {
		super("Rejestracja wynik�w bada�");
		patientsList = new ArrayList<Patient>();
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(new Dimension(d.width*9/10,d.height*9/10));
		JDBC = new JDBC();
		createMenu();
		createPanels();
		dataString="";
		
		setVisible(true);	
		setLocationRelativeTo(null);
	}
	
	
	/**
	 * Tworzenie MenuBara
	 */
	public void createMenu(){
		JMenuBar MenuBar = new JMenuBar();
		JMenu Menu = new JMenu("Aplikacja");
		JMenuItem Exit = new JMenuItem("Zako�cz");
		KeyStroke key = KeyStroke.getKeyStroke(KeyEvent.VK_F4, KeyEvent.ALT_DOWN_MASK);
		Exit.setAccelerator(key);
		Exit.addActionListener(new ActionListener(){


			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
				
			}
		});	
		Menu.add(Exit);
		MenuBar.add(Menu);
		setJMenuBar(MenuBar);
		
	}
	
	
	/**
	 * Tworzenie Paneli w oknie roboczym
	 */
	public void createPanels(){
		Border blackline;
		TitledBorder title;
		blackline = BorderFactory.createLineBorder(Color.black);
		
		
		/*
		 * 
		 */
		
		PatientListToConfigure=new JPanel(new BorderLayout());
		PatientListToConfigure.setPreferredSize(new Dimension((int) (0.65*getWidth()), getHeight()) );
		
		/*
		 * 
		 */
		PatientList=new JPanel(new BorderLayout());
		PatientList.setPreferredSize(new Dimension(getWidth(), (int)(0.5* getHeight())));
		title = BorderFactory.createTitledBorder( blackline, "Lista pacjent�w");
		title.setTitleJustification(TitledBorder.LEFT);
		PatientList.setBorder(title);
		patientListPanel(PatientList);
		
		/*
		 * 
		 */
		ExaminationList=new JPanel(new BorderLayout());
		ExaminationList.setPreferredSize(new Dimension(getWidth(), (int)(0.5* getHeight())));
		title = BorderFactory.createTitledBorder( blackline, "Badania Pacjent�w");
		title.setTitleJustification(TitledBorder.LEFT);
		ExaminationList.setBorder(title);
		examinationListPanel(ExaminationList);
		
		PatientListToConfigure.add(PatientList, BorderLayout.NORTH);
		PatientListToConfigure.add(ExaminationList, BorderLayout.CENTER);
		
		
		/*
		 * Panel z lewej strony, kt�ry b�dzie si� dzieli� na dwa mniejsze panele - dane pacjent�w oraz wyniki bada�
		 */
		PatientDataToConfigure=new JPanel(new BorderLayout());
		PatientDataToConfigure.setPreferredSize(new Dimension((int) (0.35*getWidth()), getHeight()) );
		
		/*
		 * Panel z lewej strony od g�ry, kt�ry zawiera pozycj� do uzupe�nienia o danych pacjent�w
		 */
		PatientData=new JPanel(new BorderLayout());
		PatientData.setPreferredSize(new Dimension(PatientDataToConfigure.getWidth(), (int)(0.5*getHeight())));
		title = BorderFactory.createTitledBorder( blackline, "Dane pacjenta");
		title.setTitleJustification(TitledBorder.LEFT);
		PatientData.setBorder(title);
		patientDataPanelComplement(PatientData);
		
		/*
		 * Panel z lewej strony od do�u, kt�ry zawiera wyniki bada� pacjent�w
		 */
		
		PatientTest=new JPanel(new BorderLayout());
		PatientTest.setPreferredSize(new Dimension(PatientDataToConfigure.getWidth(), (int)(0.5*getHeight())));
		title = BorderFactory.createTitledBorder( blackline, "Badanie");
		title.setTitleJustification(TitledBorder.LEFT);
		PatientTest.setBorder(title);
		patientTestComplement(PatientTest);
		
		
		PatientDataToConfigure.add(PatientData, BorderLayout.NORTH);
		PatientDataToConfigure.add(PatientTest, BorderLayout.CENTER);
		
		add(PatientDataToConfigure, BorderLayout.WEST);
		add(PatientListToConfigure, BorderLayout.CENTER);
		
		addComponentListener(new ComponentListener() {
			
			@Override
			public void componentShown(ComponentEvent e) {
				// TODO Auto-generated method stub
				
			}
			/**
			 * Dynamiczna zmiana rozmiaru okna
			 * @see java.awt.event.ComponentListener#componentResized(java.awt.event.ComponentEvent)
			 */
			@Override
			public void componentResized(ComponentEvent e) {
				PatientListToConfigure.setPreferredSize(new Dimension((int) (0.65*getWidth()), getHeight()));
				PatientDataToConfigure.setPreferredSize(new Dimension((int) (0.35*getWidth()), getHeight()));
				PatientList.setPreferredSize(new Dimension(PatientListToConfigure.getWidth(), (int) (0.5*PatientListToConfigure.getHeight())));
				ExaminationList.setPreferredSize(new Dimension(PatientListToConfigure.getWidth(), (int) (0.5*PatientListToConfigure.getHeight())));
				PatientData.setPreferredSize(new Dimension(PatientDataToConfigure.getWidth(), (int) (0.6*PatientDataToConfigure.getHeight())));
				PatientTest.setPreferredSize(new Dimension(PatientDataToConfigure.getWidth(), (int) (0.4*PatientDataToConfigure.getHeight())));
			}
			
			@Override
			public void componentMoved(ComponentEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void componentHidden(ComponentEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		
		
		
	}	
	
	/**
	 * Budowa panelu danych pacjenta
	 * @param PatientData dane pacjenta
	 */
	public void patientDataPanelComplement(JPanel PatientData){
		PatientData.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		
		gbc.weightx =1;
		gbc.weighty = 3;

		/*
		 * Napis Imi� w panelu pacjenta
		 */
		JLabel firstNameInscription = new JLabel("Imie:");
		firstNameInscription.setFont(new Font("Serif", Font.PLAIN, 20));
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 0;
		gbc.gridy = 0;	
		gbc.gridwidth = 1;
		PatientData.add(firstNameInscription, gbc);
		/*
		 * Miejce, w kt�rym nale�y wpisa� imi� pacjenta
		 */
		firstNameToFillIn = new JTextField(20);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.gridwidth = 2;
		PatientData.add(firstNameToFillIn, gbc);
		/*
		 * Napis Nazwisko w panelu pacjenta
		 */
		
		JLabel surnameInscription = new JLabel("Nazwisko:");
		surnameInscription.setFont(new Font("Serif", Font.PLAIN, 20));
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 1;	
		PatientData.add(surnameInscription, gbc);
		/*
		 * Miejce, w kt�rym nale�y wpisa� nazwisko pacjenta
		 */
		surnameToFillIn = new JTextField(20);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.gridwidth = 2;
		PatientData.add(surnameToFillIn, gbc);
		
		/*
		 * Napis PESEL w panelu pacjenta
		 */
		JLabel peselInscription = new JLabel("PESEL:");
		peselInscription.setFont(new Font("Serif", Font.PLAIN, 20));
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 0;
		gbc.gridy = 2;	
		gbc.gridwidth = 1;
		PatientData.add(peselInscription, gbc);
		/*
		 * Miejce, w kt�rym nale�y wpisa� PESEL pacjenta
		 */
		peselToFillIn = new JTextField(20);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 1;
		gbc.gridy = 2;
		gbc.gridwidth = 2;
		PatientData.add(peselToFillIn, gbc);
		
		/*
		 * Napis P�e� w panelu pacjenta
		 */
		JLabel sexInscription = new JLabel("P�e�:");
		sexInscription.setFont(new Font("Serif", Font.PLAIN, 20));
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 0;
		gbc.gridy = 3;	
		gbc.gridwidth = 1;
		PatientData.add(sexInscription, gbc);
		/*
		 * Wyb�r p�ci pacjenta, umo�liwia wyb�r tylko jednej 
		 */
		ButtonGroup bg1 = new ButtonGroup( );
		
		/*
		 * Przycisk umo�liwiaj�cy wyb�r opcji-Kobieta
		 */
		ladykinInscription = new JRadioButton("Kobieta");
		gbc.gridx = 1;
		gbc.gridy = 3;	
		gbc.gridwidth = 1;		
		bg1.add(ladykinInscription);
		PatientData.add(ladykinInscription, gbc);
		
		/*
		 * Przycisk umo�liwiaj�cy wyb�r opcji-M�czyzna
		 */
		gentlemanInscription = new JRadioButton("M�czyzna");
		gbc.gridx = 2;
		gbc.gridy = 3;
		bg1.add(gentlemanInscription);
		PatientData.add(gentlemanInscription, gbc);
		
		
		/*
		 * Listenery informuj�ce o wybranej p�ci
		 */
		ladykinInscription.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				sexToChoose = "K";
				
			}
		});
		
		gentlemanInscription.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				sexToChoose = "M";
				
			}
		});
		
		

	
		/*
		 * Tworzenie przycisku Zapisz w panelu Pacjenta
		 */
		saveButtonPatientData = new JButton("Zapisz");
		/*
		 * Funkcja obs�uguj�ca przycisk Zapisz 
		 */
		saveButtonPatientData.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				if(!isClicked)	
					if(isStringOk(firstNameToFillIn)){
							if(isStringOk(surnameToFillIn)){
								
									if(peselCheck(peselToFillIn)) {  
												if (isStringOk(sexToChoose)){
										
										
											
												Patient p = new Patient(firstNameToFillIn.getText(), surnameToFillIn.getText(), peselToFillIn.getText(), sexToChoose, false);
												patientsList.add(p);
												model1.addRow(new Patient(firstNameToFillIn.getText(), surnameToFillIn.getText(), peselToFillIn.getText(), sexToChoose, false).toTable());
												
												
												JDBC.insertPatient(p);
												
											
												}
												else
												JOptionPane.showMessageDialog(null, "NIE ZOSTA�A WYBRANA P�E�");
										
										}	
									else
										JOptionPane.showMessageDialog(null, "PESEL JEST NIEPOPRAWNY");	
							}
							else
							JOptionPane.showMessageDialog(null, "NAZWISKO NIEPRAWID�OWE");
						}
					else
						JOptionPane.showMessageDialog(null, "IMI� NIEPRAWID�OWE");
				
				
				
				 
		else{
			if(isStringOk(firstNameToFillIn)){
				if(isStringOk(surnameToFillIn)){
					
				
					
							Patient p = new Patient(patientsList.get(j).getId(),firstNameToFillIn.getText(), surnameToFillIn.getText(), peselToFillIn.getText(), sexToChoose, patientsList.get(j).isExamination());
							
							table1.getModel().setValueAt(p.getFirstName(), j, 0);
							table1.getModel().setValueAt(p.getSurname(), j, 1);
							table1.getModel().setValueAt(p.whatSex(), j, 2);
							table1.getModel().setValueAt(p.getPesel(), j, 3);
							table1.getModel().setValueAt(p.isExamination(), j, 4);
							System.out.println(patientsList.get(j).getId());
						
							JDBC.updatePatient(p);
					
					
					}
					else
					JOptionPane.showMessageDialog(null, "NAZWISKO NIEPRAWID�OWE");
				}
			else
				JOptionPane.showMessageDialog(null, "IMI� NIEPRAWID�OWE");		
				
		}
	
				
		isClicked=false;
		
		clearDataPanel();		
		makePatientPanelActiveOrDisabled(false);
		makePatientListActiveOrDisabled(true);
		
			}
		});
		gbc.gridx = 0;
		gbc.gridy = 5;
		gbc.gridwidth = 1;
		PatientData.add(saveButtonPatientData, gbc);
		
		
		/*
		 * Tworzenie przycisku Anuluj w panelu Pacjenta
		 */
		deconditionButtonPatientData = new JButton("Anuluj");
		/*
		 * Funkcja obs�uguj�ca przycisk Anuluj
		 */
		deconditionButtonPatientData.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				makeExaminationPanelActiveOrDisabled(false);
				makePatientPanelActiveOrDisabled(false);
				makePatientListActiveOrDisabled(true);
				clearExaminationPanel();
				clearDataPanel();
				ladykinInscription.setSelected(false);
				gentlemanInscription.setSelected(false);
				isClicked=false;
				
			}
		});
		
		gbc.gridx = 1;
		gbc.gridy = 5;
		gbc.gridwidth = 1;
		gbc.gridheight = 2;
		PatientData.add(deconditionButtonPatientData, gbc);
		
		
		makePatientPanelActiveOrDisabled(false);
	}
	
	
	
	public class DateFormatter extends AbstractFormatter {

	    /**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private String datePattern = "yyyy-MM-dd";
	    private SimpleDateFormat dateFormat = new SimpleDateFormat(datePattern);
	    @Override
	    public Object stringToValue(String text) throws ParseException {
	    	return dateFormat.parseObject(text);
	    }

	    @Override
	    public String valueToString(Object value) throws ParseException {
	        if (value != null) {
	            Calendar cal = (Calendar) value;
	            return dateFormat.format(cal.getTime());
	        }

	        return "";
	    }

	}

    
    
	/**
	 * Metoda za pomoc� kt�rej zostanie wype�niony panel Badanie Pacjenta
	 * @param PatientTest Panel badanie pacjenta 
	 */
	public void patientTestComplement(JPanel PatientTest){
		PatientTest.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		
		
		gbc.weightx =1;
		gbc.weighty = 1;
		/*
		 * Napis Data badania w panelu Badanie Pacjenta
		 */
		JLabel dataInscription = new JLabel("Data badania:");
		dataInscription.setFont(new Font("Serif", Font.PLAIN, 20));
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 0;
		gbc.gridy = 0;	
		gbc.gridwidth=1;
		PatientTest.add(dataInscription, gbc);
		

		

		UtilDateModel datemodel = new UtilDateModel();
		Properties prop = new Properties();
		prop.put("text.today", "Dzisiaj");
		prop.put("text.month", "Month");
		prop.put("text.year", "Year");   
		JDatePanelImpl datePanel = new JDatePanelImpl(datemodel,prop);
	    datePicker = new JDatePickerImpl(datePanel, new DateFormatter());
	    JFormattedTextField textFieldDatePicker = datePicker.getJFormattedTextField();
	    textFieldDatePicker.setFont(new Font("Some-Font-Name", Font.BOLD, 14));
	    textFieldDatePicker.setBackground(Color.WHITE);
	    
	    gbc.gridx = 1;	
		gbc.gridy = 0;	
		gbc.gridwidth=1;
		datePicker.setEnabled(false);
	    PatientTest.add(datePicker, gbc);
	    

	    JLabel hour = new JLabel( "Godzina:" );
	    hour.setFont(new Font("Serif", Font.PLAIN, 20));
	    timeSpinner = new JSpinner();
        timeSpinner.setModel(new SpinnerDateModel());
        de = new JSpinner.DateEditor(timeSpinner, "HH:mm");
        timeSpinner.setEditor(de);
        timeSpinner.setFont(new Font("Some-Font-Name", Font.BOLD, 14));
        gbc.gridx = 0;
		gbc.gridy = 1;	
		gbc.gridwidth=1;
        PatientTest.add(hour,gbc);
        
        gbc.gridx = 1;
		gbc.gridy = 1;	
		gbc.gridwidth=1;
		timeSpinner.setEnabled(false);
        PatientTest.add(timeSpinner,gbc);
        System.out.println(de.getFormat().format(timeSpinner.getValue()));
        
        
	    
	    
	    
		/*
		 * Napis Ci�nienie krwi w panelu Badanie Pacjenta
		 */
		JLabel bloodPresureInscription = new JLabel("Ci�nienie krwi:");
		bloodPresureInscription.setFont(new Font("Serif", Font.PLAIN, 20));
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 0;
		gbc.gridy = 2;	
		gbc.gridwidth = 1;
		PatientTest.add(bloodPresureInscription, gbc);
		
		/*
		 * Pole, do kt�rego nale�y wpisa� wynik badania - ci�nienie krwi
		 */
		bloodPresure = new JTextField(15);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 1;
		gbc.gridy = 2;
		gbc.gridwidth = 1;
		PatientTest.add(bloodPresure, gbc);
		
		/*
		 * Napis T�tno w panelu Badanie Pacjenta
		 */
		JLabel heartRateInscription = new JLabel("T�tno:");
		heartRateInscription.setFont(new Font("Serif", Font.PLAIN, 20));
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 0;
		gbc.gridy = 3;	
		gbc.gridwidth = 1;
		PatientTest.add(heartRateInscription, gbc);
		/*
		 * Pole, do kt�rego nale�y wpisa� wynik badania - T�tno
		 */
		heartRate = new JTextField(15);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 1;
		gbc.gridy = 3;
		gbc.gridwidth = 1;
		PatientTest.add(heartRate, gbc);
		
		
		
		
		
		/*
		 * Przycisk Zapisz w panelu Badanie Pacjenta
		 */
		saveButtonPatientTest = new JButton("Zapisz");
		/*
		 * Obs�uga przycisku Zapisz z panelu Badanie Pacjenta
		 */
		saveButtonPatientTest.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				if(isInt(bloodPresure)){
					if(isInt(heartRate)){
						
						if(isClickedExamination)
						{
							
							
							examination=new Examination(patientsList.get(j).getExaminationsList().get(jb).getID(),dataString, bloodPresure.getText(), Integer.parseInt(heartRate.getText()), patientsList.get(j).getId());
														
							
							
							patientsList.get(j).getExaminationsList().get(jb).setbloodPressure(examination.getbloodPressure());
							patientsList.get(j).getExaminationsList().get(jb).setDateString(examination.getDateString());
							patientsList.get(j).getExaminationsList().get(jb).setheartRate(examination.getheartRate());
							
							
							table2.getModel().setValueAt(examination.getDateString(), jb, 0);
							table2.getModel().setValueAt(examination.getbloodPressure(), jb, 1);
							table2.getModel().setValueAt(examination.getheartRate(), jb, 2);
							JDBC.updateExamination(examination);
							
							
						}
						
						
						else{
							
							examination=new Examination(dataString, bloodPresure.getText(), Integer.parseInt(heartRate.getText()), patientsList.get(j).getId());
							Patient p = new Patient(patientsList.get(j).getId(),patientsList.get(j).getFirstName(), patientsList.get(j).getSurname(), patientsList.get(j).getPesel(), patientsList.get(j).whatSex(), true);
							
							p.setExamination(true);
							
							JDBC.updatePatient(p);
							JDBC.insertExamination(p, examination);
							
							p.getExaminationsList().add(examination);
							
							table1.getModel().setValueAt(p.getFirstName(), j, 0);
							table1.getModel().setValueAt(p.getSurname(), j, 1);
							table1.getModel().setValueAt(p.whatSex(), j, 2);
							table1.getModel().setValueAt(p.getPesel(), j, 3);
							table1.getModel().setValueAt(p.isExamination(), j, 4);
							
							
						}
						
											
						
						
						
					
					
					}
					
					else
					JOptionPane.showMessageDialog(null, "Niepoprawne t�tno. Podaj liczb� ca�kowit�.");	
					
				}
				else
					JOptionPane.showMessageDialog(null, "Niepoprawne ci�nienie krwi. Podaj liczb� ca�kowit�.");
				
				
				
				for (int i=model2.getRowCount()-1 ; i>=0; i--){
					model2.removeRow(i);
					
				}
				List<Examination> examinations = JDBC.selectExaminations(patientsList.get(j));
				for (Examination examination : examinations) {
					model2.addRow(examination.toTable());
				}
				clearDataPanel();		
				makePatientPanelActiveOrDisabled(false);
				clearExaminationPanel();
				makeExaminationPanelActiveOrDisabled(false);
				isClickedExamination=false;
			}
		});
		gbc.gridx = 0;
		gbc.gridy = 4;
		gbc.gridwidth=1;
		PatientTest.add(saveButtonPatientTest, gbc);
		
		/*
		 * Przycisk Anuluj w panelu Badanie Pacjenta
		 */
		deconditionButtonPatientTest = new JButton("Anuluj");
		/*
		 * Obs�uga przycisku Anuluj z panelu Badanie Pacjenta
		 */
		deconditionButtonPatientTest.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				clearDataPanel();		
				makePatientPanelActiveOrDisabled(false);
				clearExaminationPanel();
				makeExaminationPanelActiveOrDisabled(false);
				ladykinInscription.setSelected(false);
				gentlemanInscription.setSelected(false);
				isClickedExamination=false;
				
			}
		});
		
		gbc.gridx = 1;
		gbc.gridy = 4;
		gbc.gridwidth=1;
		PatientTest.add(deconditionButtonPatientTest, gbc);
		
		makeExaminationPanelActiveOrDisabled(false);
		
	}
	
	/**
	 * 
	 */
	public void examinationListPanel(JPanel ExaminationList){
		/*
		 * Etap tworzenia tabeli
		 */
		table2 = new JTable(); 
        Object[] columns = {"Data", "Pomiar Ci�nienia","Pomiar t�tna", "ID"};

        model2 = new DefaultTableModel(){
        	/**
			 * 
			 */
        	private static final long serialVersionUID = 1L;
	/**
	* Funkcja za pomoc� kt�rej blokujemy mo�liwo�� edycji kom�rek w tabeli
	* @param row wiersze
	* @param column kolumny
	*/
	public boolean isCellEditable(int row, int column) {
		        return false;
		    }

	/*
	 * (non-Javadoc)
	 * @see javax.swing.table.AbstractTableModel#getColumnClass(int)
	 * Tworzymy obiekt typu checkbox w ostatniej kolumnie 
	 */
		public Class<?> getColumnClass(int c) {
            switch (c) {
               default: return String.class;
            }   
          } };
        //Tworzymy etykiety kolumn
        model2.setColumnIdentifiers(columns);
        
        
        
        table2.setModel(model2);
        table2.setBackground(Color.DARK_GRAY);
        table2.setForeground(Color.white);
        Font font = new Font("", 1, 10);
        table2.setFont(font);
        table2.setRowHeight(30);
        table2.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
        table2.setRowSelectionAllowed(true);
        
        

        
        pane = new JScrollPane(table2);
		pane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		ExaminationList.add(pane, BorderLayout.CENTER);
		
		
        
		/*
		 * Przycisk Dodaj w panelu Lista Bada�
		 */
		
        addButtonExaminationList = new JButton("Dodaj");
        addButtonExaminationList.setEnabled(false);
		
		/*
		 * Funkcja obs�uguj�ca naci�ni�cie przycisku Dodaj
		 */
        addButtonExaminationList.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				makeExaminationPanelActiveOrDisabled(true);
				makePatientPanelActiveOrDisabled(false);
				addButtonExaminationList.setEnabled(false);
				GeneratePressureGraph.setEnabled(false);
				GenerateHeartRateGraph.setEnabled(false);
			}
			
		});
        
        
        /*
		 * Przycisk Usu� w panelu Lista Bada�
		 */
        removeButtonExaminationList = new JButton("Usu�");
        removeButtonExaminationList.setEnabled(false);
		/*
		 * Funkcja obs�uguj�ca naci�ni�cie przycisku Usu�
		 */
        removeButtonExaminationList.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				int i=table2.getSelectedRow();
				if(i>=0){
					
					model2.removeRow(i);
					
					JDBC.removeExamination(patientsList.get(j).getExaminationsList().get(i));
					
					//Tu trzeba obs�u�y� badanka
					
				}
				else if(table2.getRowCount() == 0){
					JOptionPane.showMessageDialog(null, "Brak bada� dla wybranego pacjenta.");
					patientsList.get(j).setExamination(false);
				}
					
					
				else 
					JOptionPane.showMessageDialog(null, "Prosz� wybra� wiersz, kt�ry chcesz usun��");
				
				
				
				makeExaminationPanelActiveOrDisabled(false);
				makePatientPanelActiveOrDisabled(false);
				removeButtonExaminationList.setEnabled(false);
				clearDataPanel();
				clearExaminationPanel();
			}
		});
        
        
        
        
		
		
        /*
		 * Przycisk generuj wykres ci�nienia krwi w panelu Badanie Pacjenta
		 */
		
        GeneratePressureGraph= new JButton("Generuj wykres ci�nienia");
        GeneratePressureGraph.setEnabled(false);
		
		/*
		 * Funkcja obs�uguj�ca naci�ni�cie przycisku Generuj wykres ci�nienia
		 */
        GeneratePressureGraph.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				examinationsBloodPressure = JDBC.selectExaminations(patientsList.get(j));
				final Figure demo = new Figure("XY Series Demo");
			    demo.pack();
			    RefineryUtilities.centerFrameOnScreen(demo);
			    demo.setVisible(true);
				demo.getDefaultCloseOperation();
				
				
			}
			
		});
        
        
        /*
		 * Przycisk generuj wykres t�tna w panelu Badanie Pacjenta
		 */
		
        GenerateHeartRateGraph= new JButton("Generuj wykres t�tna");
        GenerateHeartRateGraph.setEnabled(false);
        /*
		 * Funkcja obs�uguj�ca naci�ni�cie przycisku Generuj wykres t�tna wykres ci�nienia
		 */
        GenerateHeartRateGraph.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				examinationsBloodPressure = JDBC.selectExaminations(patientsList.get(j));
				final Figure demo = new Figure("XY Series Demo");
			    demo.pack();
			    RefineryUtilities.centerFrameOnScreen(demo);
			    demo.setVisible(true);
				demo.setDefaultCloseOperation(DISPOSE_ON_CLOSE);	
				
			}
			
		});
        
        JPanel bp = new JPanel();
        
		bp.setLayout(new BoxLayout(bp, BoxLayout.X_AXIS));
		bp.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));



		bp.add(addButtonExaminationList);
		bp.add(removeButtonExaminationList);
		bp.add(GeneratePressureGraph);
		bp.add(GenerateHeartRateGraph);
		
		ExaminationList.add(bp, BorderLayout.SOUTH);
     
		table2.addMouseListener(new MouseListener() {
			
			
			
			/*
			 * (non-Javadoc)
			 * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
			 * 
			 * Po klikni�ciu na dany wiersz poka�� si� jego warto�ci w panelu Badanie Pacjenta
			 */
			
			@Override
			public void mouseClicked(MouseEvent e) {
				isClickedExamination=true;
				jb = table2.getSelectedRow();
				
				
				bloodPresure.setText(model2.getValueAt(jb, 1).toString());
				heartRate.setText(model2.getValueAt(jb, 2).toString());
				
				
				System.out.println(jb);
				
				makeExaminationPanelActiveOrDisabled(true);
				removeButtonExaminationList.setEnabled(true);
				
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});

		
		
	}
	
	
	
	
	/**
	 * Metoda uzupe�niaj�ca prawy panel, zawieraj�cy tabel� z wprowadzonymi do niej pacjentami i ich wynikami
	 * @param PatientList Panel lista pacjent�w
	 */
	public void patientListPanel(JPanel PatientList){
		/*
		 * Etap tworzenia tabeli
		 */
		table1 = new JTable(); 
        Object[] columns = {"Imi�", "Nazwisko","P�e�","Pesel","Badanie"};

        model1 = new DefaultTableModel(){
        	/**
			 * 
			 */
        	private static final long serialVersionUID = 1L;
	/**
	* Funkcja za pomoc� kt�rej blokujemy mo�liwo�� edycji kom�rek w tabeli
	* @param row wiersze
	* @param column kolumny
	*/
	public boolean isCellEditable(int row, int column) {
		        return false;
		    }

	/*
	 * (non-Javadoc)
	 * @see javax.swing.table.AbstractTableModel#getColumnClass(int)
	 * Tworzymy obiekt typu checkbox w ostatniej kolumnie 
	 */
		public Class<?> getColumnClass(int c) {
            switch (c) {
              case 4: return Boolean.class;
              default: return String.class;
            }   
          } };
        //Tworzymy etykiety kolumn
        model1.setColumnIdentifiers(columns);
        
        
        
        table1.setModel(model1);
        table1.setBackground(Color.DARK_GRAY);
        table1.setForeground(Color.white);
        Font font = new Font("", 1, 10);
        table1.setFont(font);
        table1.setRowHeight(30);
        table1.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
        table1.setRowSelectionAllowed(true);
        
        patientsList = JDBC.selectAllPatients();
        
        for (Patient patient : patientsList) {
			model1.addRow(patient.toTable());
			
			examinations = JDBC.selectExaminations(patient);
			patient.setExaminationsList(examinations);
		}
        
        

        
        pane = new JScrollPane(table1);
		pane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		PatientList.add(pane, BorderLayout.CENTER);
		
		
        
		/*
		 * Przycisk Dodaj w panelu Badanie Pacjenta
		 */
		
        addButtonPatientList = new JButton("Dodaj");
       
		
		/*
		 * Funkcja obs�uguj�ca naci�ni�cie przycisku Dodaj
		 */
        addButtonPatientList.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				makePatientPanelActiveOrDisabled(true);
				makeExaminationListActiveOrDisabled(false);
				makePatientListActiveOrDisabled(false);	
				isClicked=false;
				isClickedExamination=false;
			}
			
		});
        
        JPanel bp = new JPanel();
        
		bp.setLayout(new BoxLayout(bp, BoxLayout.X_AXIS));
		bp.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));

		/*
		 * Przycisk Usu� w panelu Lista Pacjent�w
		 */
		removeButtonPatientList = new JButton("Usu�");
		/*
		 * Funkcja obs�uguj�ca naci�ni�cie przycisku Usu�
		 */
		removeButtonPatientList.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				int i=table1.getSelectedRow();
				if(i>=0){
					System.out.println(patientsList.size());
					model1.removeRow(i);
					
					JDBC.removePatient(patientsList.get(i));
					patientsList.remove(i);
					
					for (i=model2.getRowCount()-1 ; i>=0; i--){
						model2.removeRow(i);
						
					}
					
				}
				else if(table1.getRowCount() == 0)
					JOptionPane.showMessageDialog(null, "Nie doda�e� �adnego Pacjenta do bazy. Co chcesz usun�� ?");
				else 
					JOptionPane.showMessageDialog(null, "Prosz� wybra� wiersz, kt�ry chcesz usun��");
				
				
				
				makeExaminationPanelActiveOrDisabled(false);
				makePatientPanelActiveOrDisabled(false);
				clearDataPanel();
				clearExaminationPanel();
			}
		});
		
		bp.add(addButtonPatientList);
		bp.add(removeButtonPatientList);
		
		
		
		PatientList.add(bp, BorderLayout.SOUTH);
     
		table1.addMouseListener(new MouseListener() {
			
			
			/*
			 * (non-Javadoc)
			 * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
			 * 
			 * Po klikni�ciu na dany wiersz poka�� si� jego warto�ci w panelach Pacjent oraz Badanie Pacjenta
			 */
			
			@Override
			public void mouseClicked(MouseEvent e) {
				isClicked=true;
				for (int i=model2.getRowCount()-1 ; i>=0; i--){
					model2.removeRow(i);
					
				}
				
				j = table1.getSelectedRow();
				
				
				firstNameToFillIn.setText(model1.getValueAt(j, 0).toString()); 
				surnameToFillIn.setText(model1.getValueAt(j, 1).toString());
				
				
				if(model1.getValueAt(j, 2).toString().equals("M")){
					gentlemanInscription.setSelected(true);
					ladykinInscription.setSelected(false);
					sexToChoose="M";
				}
				else 
				{
					ladykinInscription.setSelected(true);
					gentlemanInscription.setSelected(false);
					sexToChoose="K";
				}
				
				
				peselToFillIn.setText(model1.getValueAt(j, 3).toString());
				
				
				
				if(patientsList.get(j).isExamination()){
									
					
					List<Examination> examinations = JDBC.selectExaminations(patientsList.get(j));
					for (Examination examination : examinations) {
						if(examination.getID_PACJENTA() == patientsList.get(j).getId())
							model2.addRow(examination.toTable());
					}
					
					
					
				}
				
				addButtonExaminationList.setEnabled(true);
				
				if(patientsList.get(j).isExamination()){
					
					GeneratePressureGraph.setEnabled(true);
					GenerateHeartRateGraph.setEnabled(true);
				}
				else {
					
					GeneratePressureGraph.setEnabled(false);
					GenerateHeartRateGraph.setEnabled(false);
				}
				
				
				makePatientPanelActiveOrDisabled(true);
				makeExaminationPanelActiveOrDisabled(false);
				
				

								
				
				
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});

		
		
	}
	
/**
 * Metoda sprawdzaj�ca czy zosta� podany jakikolwiek znak 
 * @param c zmienna pomocnicza
 * @return warto�� logiczna
 */
	 public boolean isStringOk(JTextField c)
		{
			if(c.getText().length()>0)
					return true;
					
			else 
			return false;
		}
	
	 /**
	  * Metoda sprawdzaj�ca czy zosta� podany jakikolwiek znak 
	  * @param c zmienna pomocnicza
	  * @return warto�� logiczna
	  */
	 public boolean isStringOk(String c)
		{
			if(c.length()>0)
				return true;
					
			
			else 
			return false;
		}
	 
	 /**
	  * Metoda sprawdzaj�ca pesel
	  * @param c zmienna pomocnicza
	  * @return warto�� logiczna
	  */
	public boolean peselCheck(JTextField c)
	{
		if(c.getText().length()==11 && isInt(c))
		{
			for(int i=0; i<model1.getRowCount(); i++)
			{
				
				if(c.getText().equals(model1.getValueAt(i, 3).toString()) ){
					JOptionPane.showMessageDialog(null, "Nie ma dw�ch os�b o takim samym PESELU");
					return false;
				}	
			}
			
			return true;
		}
		else 
		return false;
	}
	
	
	/**
	 * Metoda sprawdzaj�ca czy podana warto�� jest zmienn� typu INT
	 * @param c zmienna pomocnicza
	 * @return warto�� logiczna
	 */
	public boolean isInt(JTextField c){
		
		for(int a=0;a<c.getText().length();a++)
		 {
		    if(a==0 && c.getText().charAt(a) == '-') continue;
		    if( !Character.isDigit(c.getText().charAt(a))) return false;
		 }
		if(c.getText().length()==0)
			return false;
		 return true;
	}
	

	
	/**
	 * Metoda sprawdzaj�ca czy podana warto�� jest zmienn� typu DOUBLE
	 * @param c zmienna pomocnicza
	 * @return warto�� logiczna
	 */
    boolean isDouble(JTextField c) {
        try {
            Double.valueOf(c.getText());      
        } catch (NumberFormatException e) {
            return false;
        } catch(NullPointerException e) {
	        return false;
	    } 
        return true;
    }
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Metoda wygenerowana automatycznie
	 */
	public void windowActivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	/**
	 * Metoda wygenerowana automatycznie
	 */
	public void windowClosed(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	/**
	 * Metoda wygenerowana automatycznie
	 */
	public void windowClosing(WindowEvent arg0) {
		// TODO Auto-generated method stub
		JDBC.closeConnection();
	}


	/**
	 * Metoda wygenerowana automatycznie
	 */
	public void windowDeactivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	/**
	 * Metoda wygenerowana automatycznie
	 */
	public void windowDeiconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	/**
	 * Metoda wygenerowana automatycznie
	 */
	public void windowIconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Metoda wygenerowana automatycznie
	 */
	public void windowOpened(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	
	/**
	 * Metoda czyszcz�ca panel pacjenta
	 */
	public void clearDataPanel() {
		firstNameToFillIn.setText("");
		surnameToFillIn.setText("");
		peselToFillIn.setText("");	
		addButtonExaminationList.setEnabled(false);
		GeneratePressureGraph.setEnabled(false);
		GenerateHeartRateGraph.setEnabled(false);
		ladykinInscription.setSelected(false);
		gentlemanInscription.setSelected(false);
		
	}

	/**
	 * Metoda czyszcz�ca panel badania pacjenta
	 */
	public void clearExaminationPanel(){
		bloodPresure.setText("");
		heartRate.setText("");
		addButtonExaminationList.setEnabled(false);
		GeneratePressureGraph.setEnabled(false);
		GenerateHeartRateGraph.setEnabled(false);
	}
	
	/**
	* Metoda aktywuj�ca lub blokuj�ca panel pacjenta 
	* @param c zmienna pomocnicza
	*/
	public void makePatientPanelActiveOrDisabled(boolean c ){
				
		peselToFillIn.setEnabled(c);
		firstNameToFillIn.setEnabled(c);
		surnameToFillIn.setEnabled(c);
		
		ladykinInscription.setEnabled(c);
		gentlemanInscription.setEnabled(c);
				
		saveButtonPatientData.setEnabled(c);
		deconditionButtonPatientData.setEnabled(c);
	}
	/**
	* Metoda aktywuj�ca lub blokuj�ca panel badania pacjenta
	* @param c zmienna pomocnicza
	*/
	public void makeExaminationPanelActiveOrDisabled(boolean c){
		deconditionButtonPatientTest.setEnabled(c);
		saveButtonPatientTest.setEnabled(c);
		datePicker.setEnabled(c);
		timeSpinner.setEnabled(c);
		bloodPresure.setEnabled(c);
		heartRate.setEnabled(c);
	}
	
	
	/**
	* 
	* @param c zmienna pomocnicza
	*/
	public void makeExaminationListActiveOrDisabled(boolean c){
		addButtonExaminationList.setEnabled(c);
		removeButtonExaminationList.setEnabled(c);
		GenerateHeartRateGraph.setEnabled(c);
		GeneratePressureGraph.setEnabled(c);
	}
	
	public void makePatientListActiveOrDisabled(boolean c){
		addButtonPatientList.setEnabled(c);
		removeButtonPatientList.setEnabled(c);
	}
	
}
