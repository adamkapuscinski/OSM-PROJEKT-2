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

import java.awt.Toolkit;





/**
 * Klasa, za pomoc¹ której tworzone jest g³ówne okno programu 
 * @author Adam Kopuœciñski ,Rados³aw Bu³a
 */
public class MainFrame extends JFrame implements WindowListener {
	
	/** Baza danych*/
	private JDBC JDBC;
	/** Flaga mówi¹ca czy nast¹pi³o klikniêcie mysz¹ */
	private boolean isClicked=false, isClickedExamination=false;
	/** Pola tekstowe: Imiê, Nazwisko, Pesel, Ciœnienie krwi, Têtno */
	private JTextField firstNameToFillIn, surnameToFillIn,peselToFillIn, bloodPresure, heartRate;
	/** Panele z:  wynikami badania, list¹ pacjentów, danymi i wynikami, danymi*/
	private JPanel PatientTest, PatientList, PatientDataToConfigure, PatientData, PatientListToConfigure, ExaminationList;
	/** Panel przewijany z suwakiem*/
	private JScrollPane pane;
	/** Wybor p³ci*/
	private String sexToChoose = "";
	/**Przyciski: dodaj dane pacjenta, zapisz dane, anuluj dane, usuñ, zapisz badanie, anuluj badanie  */
	private JButton addButtonPatientList, saveButtonPatientData,deconditionButtonPatientData, removeButtonPatientList, saveButtonPatientTest, deconditionButtonPatientTest, GeneratePressureGraph, GenerateHeartRateGraph, addButtonExaminationList, removeButtonExaminationList;
	/** Data*/
	Date date;
	/** Badanie*/
	private Examination examination;
	/** Model*/
	private DefaultTableModel model1, model2;
	/** Lista pacjentów*/
	private List<Patient> patientsList;
	/** Lista badañ pacjentów*/
	private List<Examination> examinations, examinationsBloodPressure;
	/** Przycisk wyboru p³ci: mê¿czyzna, kobieta*/
	private JRadioButton  gentlemanInscription, ladykinInscription;
	/** Wiersz tabeli: listy pacjentów, listy badañ*/
	private int j , jb;
	/** Zmienna umo¿liwiaj¹ca utworzenie pola z dat¹ */
	private JDatePickerImpl datePicker;
	/** Tabele z wynikami pacjntów*/
	private JTable table1, table2;
	/** Zmienna umo¿liwiaj¹ca pobranie daty z pola Data*/
	private String dataString;
	/** Zmienna umo¿liwiaj¹ca edycjê daty*/
	JSpinner.DateEditor de;
	
	/**
	 * Konstruktor domyœly, który ustawia tytu³ aplikacji i pobiera domyœlny rozmiar okna
	 * 
	 */
	public MainFrame() {
		super("Rejestracja wyników badañ");
		patientsList = new ArrayList<Patient>();
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(new Dimension(d.width*9/10,d.height*9/10));
		JDBC = new JDBC();
		createMenu();
		createPanels();
		setVisible(true);	
		setLocationRelativeTo(null);
	}
	
	
	/**
	 * Tworzenie MenuBara
	 */
	public void createMenu(){
		JMenuBar MenuBar = new JMenuBar();
		JMenu Menu = new JMenu("Aplikacja");
		JMenuItem Exit = new JMenuItem("Zakoñcz");
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
	 * Tworzenie Paneli 
	 */
	public void createPanels(){
		Border blackline;
		TitledBorder title;
		blackline = BorderFactory.createLineBorder(Color.black);
		
		
		/*
		 * Panel po prawej stronie, w którym znajduj¹ siê table z list¹ pacjentów i ich badaniami
		 */
		PatientListToConfigure=new JPanel(new BorderLayout());
		PatientListToConfigure.setPreferredSize(new Dimension((int) (0.65*getWidth()), getHeight()) );
		
		/*
		 * Górny panel po prawej stronie, gdzie wyœwietlna jest tabela z list¹ pacjentów 
		 */
		PatientList=new JPanel(new BorderLayout());
		PatientList.setPreferredSize(new Dimension(getWidth(), (int)(0.5* getHeight())));
		title = BorderFactory.createTitledBorder( blackline, "Lista pacjentów");
		title.setTitleJustification(TitledBorder.LEFT);
		PatientList.setBorder(title);
		patientListPanel(PatientList);
		
		/*
		 * Górny panel po prawej stronie, gdzie wyœwietlna jest tabela z list¹ badañ 
		 */
		ExaminationList=new JPanel(new BorderLayout());
		ExaminationList.setPreferredSize(new Dimension(getWidth(), (int)(0.5* getHeight())));
		title = BorderFactory.createTitledBorder( blackline, "Badania Pacjentów");
		title.setTitleJustification(TitledBorder.LEFT);
		ExaminationList.setBorder(title);
		examinationListPanel(ExaminationList);
		
		/*
		 * Panel z lewej strony, który bêdzie siê dzieli³ na dwa mniejsze panele - dane pacjentów oraz wyniki badañ
		 */
		PatientDataToConfigure=new JPanel(new BorderLayout());
		PatientDataToConfigure.setPreferredSize(new Dimension((int) (0.35*getWidth()), getHeight()) );
		
		/*
		 * Panel z lewej strony od góry, który zawiera pozycjê do uzupe³nienia o danych pacjentów
		 */
		PatientData=new JPanel(new BorderLayout());
		PatientData.setPreferredSize(new Dimension(PatientDataToConfigure.getWidth(), (int)(0.5*getHeight())));
		title = BorderFactory.createTitledBorder( blackline, "Dane pacjenta");
		title.setTitleJustification(TitledBorder.LEFT);
		PatientData.setBorder(title);
		patientDataPanelComplement(PatientData);
		
		/*
		 * Panel z lewej strony od do³u, który zawiera wyniki badañ pacjentów
		 */
		PatientTest=new JPanel(new BorderLayout());
		PatientTest.setPreferredSize(new Dimension(PatientDataToConfigure.getWidth(), (int)(0.5*getHeight())));
		title = BorderFactory.createTitledBorder( blackline, "Badanie");
		title.setTitleJustification(TitledBorder.LEFT);
		PatientTest.setBorder(title);
		patientTestComplement(PatientTest);
		
		
		PatientDataToConfigure.add(PatientData, BorderLayout.NORTH);
		PatientDataToConfigure.add(PatientTest, BorderLayout.CENTER);
		PatientListToConfigure.add(PatientList, BorderLayout.NORTH);
		PatientListToConfigure.add(ExaminationList, BorderLayout.CENTER);
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
		 * Napis Imiê w panelu pacjenta
		 */
		JLabel firstNameInscription = new JLabel("Imie:");
		firstNameInscription.setFont(new Font("Serif", Font.PLAIN, 20));
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 0;
		gbc.gridy = 0;	
		gbc.gridwidth = 1;
		PatientData.add(firstNameInscription, gbc);
		
		/*
		 * Miejce, w którym nale¿y wpisaæ imiê pacjenta
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
		 * Miejce, w którym nale¿y wpisaæ nazwisko pacjenta
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
		 * Miejce, w którym nale¿y wpisaæ PESEL pacjenta
		 */
		peselToFillIn = new JTextField(20);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 1;
		gbc.gridy = 2;
		gbc.gridwidth = 2;
		PatientData.add(peselToFillIn, gbc);
		
		/*
		 * Napis P³eæ w panelu pacjenta
		 */
		JLabel sexInscription = new JLabel("P³eæ:");
		sexInscription.setFont(new Font("Serif", Font.PLAIN, 20));
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 0;
		gbc.gridy = 3;	
		gbc.gridwidth = 1;
		PatientData.add(sexInscription, gbc);
		
		/*
		 * Wybór p³ci pacjenta, umo¿liwia wybór tylko jednej 
		 */
		ButtonGroup bg1 = new ButtonGroup( );
		
		/*
		 * Przycisk umo¿liwiaj¹cy wybór opcji-Kobieta
		 */
		ladykinInscription = new JRadioButton("Kobieta");
		gbc.gridx = 1;
		gbc.gridy = 3;	
		gbc.gridwidth = 1;		
		bg1.add(ladykinInscription);
		PatientData.add(ladykinInscription, gbc);
		
		/*
		 * Przycisk umo¿liwiaj¹cy wybór opcji-Mê¿czyzna
		 */
		gentlemanInscription = new JRadioButton("Mê¿czyzna");
		gbc.gridx = 2;
		gbc.gridy = 3;
		bg1.add(gentlemanInscription);
		PatientData.add(gentlemanInscription, gbc);
		
		
		/*
		 * Listenery informuj¹ce o wybranej p³ci
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
		 * Funkcja obs³uguj¹ca przycisk Zapisz 
		 */
		saveButtonPatientData.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				if(!isClicked)	
					if(isStringOk(firstNameToFillIn)){
						System.out.println("Imie ok");
							if(isStringOk(surnameToFillIn)){
								System.out.println("Nazwisko ok");
								
									if(peselCheck(peselToFillIn)) {  
										System.out.println("PESEL ok");
												if (isStringOk(sexToChoose)){
										
										
												/* Tworzenie nowego pacjenta*/
												Patient p = new Patient(firstNameToFillIn.getText(), surnameToFillIn.getText(), peselToFillIn.getText(), sexToChoose, false);
												patientsList.add(p);
												/* Dodanie nowego pacjenta do azy danych*/
												model1.addRow(new Patient(firstNameToFillIn.getText(), surnameToFillIn.getText(), peselToFillIn.getText(), sexToChoose, false).toTable());
												JDBC.insertPatient(p);
												
											
												}
												else
												JOptionPane.showMessageDialog(null, "NIE ZOSTA£A WYBRANA P£EÆ");
										
										}	
									else
										JOptionPane.showMessageDialog(null, "PESEL JEST NIEPOPRAWNY");	
							}
							else
							JOptionPane.showMessageDialog(null, "NAZWISKO NIEPRAWID£OWE");
						}
					else
						JOptionPane.showMessageDialog(null, "IMIÊ NIEPRAWID£OWE");
				
				
				
				 
		else{
			if(isStringOk(firstNameToFillIn)){
				if(isStringOk(surnameToFillIn)){
					if(peselCheck(peselToFillIn)) {  
					        /* Uaktualnianie danych pacjenta*/
							Patient p = new Patient(patientsList.get(j).getId(),firstNameToFillIn.getText(), surnameToFillIn.getText(), peselToFillIn.getText(), sexToChoose, patientsList.get(j).isExamination());
							/* Pobranie danych pacjenta do pól tekstowych w celu ³atwiejszej edycji danych*/
							table1.getModel().setValueAt(p.getFirstName(), j, 0);
							table1.getModel().setValueAt(p.getSurname(), j, 1);
							table1.getModel().setValueAt(p.whatSex(), j, 2);
							table1.getModel().setValueAt(p.getPesel(), j, 3);
							table1.getModel().setValueAt(p.isExamination(), j, 4);
							System.out.println(patientsList.get(j).getId());
						
							JDBC.updatePatient(p);
					}	
					else
						JOptionPane.showMessageDialog(null, "PESEL JEST NIEPOPRAWNY");	
					
					}
					else
					JOptionPane.showMessageDialog(null, "NAZWISKO NIEPRAWID£OWE");
				}
			else
				JOptionPane.showMessageDialog(null, "IMIÊ NIEPRAWID£OWE");		
				
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
		 * Funkcja obs³uguj¹ca przycisk Anuluj
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
	
	
	/**
	 * Klasa umo¿liwiaj¹ca edycjê daty
	 */
	public class DateFormatter extends AbstractFormatter {
		/** Wzór daty*/
		private String datePattern = "yyyy-MM-dd";
		/** Format daty*/
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
	 * Metoda za pomoc¹ której zostanie wype³niony panel Badanie Pacjenta
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
		
		/*
		 * Kalendarz 
		 */
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
	    
		/*
		 * Napis Ciœnienie krwi w panelu Badanie Pacjenta
		 */
		JLabel bloodPresureInscription = new JLabel("Ciœnienie krwi:");
		bloodPresureInscription.setFont(new Font("Serif", Font.PLAIN, 20));
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 0;
		gbc.gridy = 2;	
		gbc.gridwidth = 1;
		PatientTest.add(bloodPresureInscription, gbc);
		
		/*
		 * Pole, do którego nale¿y wpisaæ wynik badania - ciœnienie krwi
		 */
		bloodPresure = new JTextField(15);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 1;
		gbc.gridy = 2;
		gbc.gridwidth = 1;
		PatientTest.add(bloodPresure, gbc);
		
		/*
		 * Napis Têtno w panelu Badanie Pacjenta
		 */
		JLabel heartRateInscription = new JLabel("Têtno:");
		heartRateInscription.setFont(new Font("Serif", Font.PLAIN, 20));
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 0;
		gbc.gridy = 3;	
		gbc.gridwidth = 1;
		PatientTest.add(heartRateInscription, gbc);
		
		/*
		 * Pole, do którego nale¿y wpisaæ wynik badania - Têtno
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
		 * Obs³uga przycisku Zapisz z panelu Badanie Pacjenta
		 */
		saveButtonPatientTest.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				
				if(dateCheck(datePicker.getJFormattedTextField().getText()))
				
						if(convertionPossible(bloodPresure.getText())){
						
							if(isInt(heartRate)){
								
								if(isClickedExamination)
								{
									/* Pobranie z kalendarza zaznaczonej daty*/
									dataString=datePicker.getJFormattedTextField().getText();
									/* Pobranie wyników badania */	
									examination=new Examination(patientsList.get(j).getExaminationsList().get(jb).getID(),dataString, bloodPresure.getText(), Integer.parseInt(heartRate.getText()), patientsList.get(j).getId());
									/* Przypisanie badania pacjentowi -ciœnienie*/					
									patientsList.get(j).getExaminationsList().get(jb).setbloodPressure(examination.getbloodPressure());
									/* Przypisanie badania pacjentowi -data*/	
									patientsList.get(j).getExaminationsList().get(jb).setDateString(examination.getDateString());
									/* Przypisanie badania pacjentowi -puls*/	
									patientsList.get(j).getExaminationsList().get(jb).setheartRate(examination.getheartRate());
									/* Dodanie wyników badania do tabeli -data*/
									table2.getModel().setValueAt(examination.getDateString(), jb, 0);
									/* Dodanie wyników badania do tabeli -ciœnienie*/
									table2.getModel().setValueAt(examination.getbloodPressure(), jb, 1);
									/* Dodanie wyników badania do tabeli -puls*/
									table2.getModel().setValueAt(examination.getheartRate(), jb, 2);
									/* Odœwie¿enie bazy danych*/
									JDBC.updateExamination(examination);

								}				
								else{
									/* Inicjowanie listy pacjentów*/
									patientsList = JDBC.selectAllPatients();
									/* Pobranie z kalendarza zaznaczonej daty*/
									dataString=datePicker.getJFormattedTextField().getText();
									/* Pobranie wyników badania */	
									examination=new Examination(dataString, bloodPresure.getText(), Integer.parseInt(heartRate.getText()), patientsList.get(j).getId());
									/* Tworzenie nowego pacjenta */	
									Patient p = new Patient(patientsList.get(patientsList.size()-1).getId(),patientsList.get(j).getFirstName(), patientsList.get(j).getSurname(), patientsList.get(j).getPesel(), patientsList.get(j).whatSex(), true);
									/* Ustw=awienie pola czy jest wykonane jakieœ badania dla pacjenta  */	
									p.setExamination(true);
									/* Dodanie do pacjenta badania*/
									p.getExaminationsList().add(examination);
									/* Odœwie¿anie pacjenta*/
									JDBC.updatePatient(p);
									/* Dodawanie badania do bazy danych*/
									JDBC.insertExamination(p, examination);
									/* Uaktualnianie danych pacjenta-Imiê*/
									table1.getModel().setValueAt(p.getFirstName(), j, 0);
									/* Uaktualnianie danych pacjenta-Nazwisko*/
									table1.getModel().setValueAt(p.getSurname(), j, 1);
									/* Uaktualnianie danych pacjenta-P³eæ*/
									table1.getModel().setValueAt(p.whatSex(), j, 2);
									/* Uaktualnianie danych pacjenta-Pesel*/
									table1.getModel().setValueAt(p.getPesel(), j, 3);
									/* Uaktualnianie danych pacjenta-badanie*/
									table1.getModel().setValueAt(p.isExamination(), j, 4);
									
									/* Dodawanie badania do tabeli*/
									for (Examination examination : examinations) {
										if(examination.getID_PACJENTA() == patientsList.get(j).getId())
											model2.addRow(examination.toTable());
									}
									/* Inicjowanie listy pacjentów*/
									patientsList = JDBC.selectAllPatients();
							        
							        /* Odœwie¿anie badañ w tabeli*/
							        for (Patient patient : patientsList) {
										examinations = JDBC.selectExaminations(patient);
										patient.setExaminationsList(examinations);
									}
								}
							}
							else
							JOptionPane.showMessageDialog(null, "Niepoprawne têtno. Podaj liczbê ca³kowit¹.");		
						}	
						else
						JOptionPane.showMessageDialog(null, "Niepoprawne ciœnienie krwi. Nale¿y podaæ ciœnienie krwi w formacie xx/xx, gdzie xx jest integerem.");
						
				
				
				/*Usuwanie badania z tabeli*/
				for (int i=model2.getRowCount()-1 ; i>=0; i--){
					model2.removeRow(i);
					
				}
				/* Lista badañ*/
				List<Examination> examinations = JDBC.selectExaminations(patientsList.get(j));
				/* Dodawanie badania do tabeli*/
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
		 * Obs³uga przycisku Anuluj z panelu Badanie Pacjenta
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
	 * Funkcja tworz¹ca tabele badañ
	 * @param ExaminationList lista badañ
	 */
	public void examinationListPanel(JPanel ExaminationList){
		/*
		 * Etap tworzenia tabeli
		 */
		table2 = new JTable(); 
        Object[] columns = {"Data", "Pomiar Ciœnienia","Pomiar têtna", "ID"};
        
        /*
		 * Tworzenie modelu tabli
		 */
        model2 = new DefaultTableModel(){

	/**
	* Funkcja za pomoc¹ której blokujemy mo¿liwoœæ edycji komórek w tabeli
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

        /* Scroll w tabeli */
        pane = new JScrollPane(table2);
		pane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		ExaminationList.add(pane, BorderLayout.CENTER);
        
		/*
		 * Przycisk Dodaj w panelu Lista Badañ
		 */
        addButtonExaminationList = new JButton("Dodaj");
        addButtonExaminationList.setEnabled(false);
		
		/*
		 * Funkcja obs³uguj¹ca naciœniêcie przycisku Dodaj
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
		 * Przycisk Usuñ w panelu Lista Badañ
		 */
        removeButtonExaminationList = new JButton("Usuñ");
        removeButtonExaminationList.setEnabled(false);
		/*
		 * Funkcja obs³uguj¹ca naciœniêcie przycisku Usuñ
		 */
        removeButtonExaminationList.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				int i=table2.getSelectedRow();
				if(i>=0){
					
					model2.removeRow(i);
					JDBC.removeExamination(patientsList.get(j).getExaminationsList().get(i));
					
				}
				else if(table2.getRowCount() == 0){
					JOptionPane.showMessageDialog(null, "Brak badañ dla wybranego pacjenta.");
					patientsList.get(j).setExamination(false);
				}
				else 
					JOptionPane.showMessageDialog(null, "Proszê wybraæ wiersz, który chcesz usun¹æ");

				makeExaminationPanelActiveOrDisabled(false);
				makePatientPanelActiveOrDisabled(false);
				removeButtonExaminationList.setEnabled(false);
				clearDataPanel();
				clearExaminationPanel();
			}
		});
       
		
        /*
		 * Przycisk generuj wykres ciœnienia krwi w panelu Badanie Pacjenta
		 */
        GeneratePressureGraph= new JButton("Generuj wykres ciœnienia");
        GeneratePressureGraph.setEnabled(false);
		
		/*
		 * Funkcja obs³uguj¹ca naciœniêcie przycisku Generuj wykres ciœnienia
		 */
        GeneratePressureGraph.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("Generate pressureGraph index w tabeli: " + j);
				
				for (Examination examination : patientsList.get(j).getExaminationsList()) {
					System.out.println("Badnaie o id: "+ examination.getID());
				}
				examinationsBloodPressure = JDBC.selectExaminations(patientsList.get(j));
				/* Utorzenie obiektu wykres ciœnienia*/
				final Figure demo = new Figure(patientsList.get(j),"ciœnienie","Wykres ciœnienia");


				
				
			}
			
		});
        
        
        /*
		 * Przycisk generuj wykres têtna w panelu Badanie Pacjenta
		 */
		
        GenerateHeartRateGraph= new JButton("Generuj wykres têtna");
        GenerateHeartRateGraph.setEnabled(false);
        /*
		 * Funkcja obs³uguj¹ca naciœniêcie przycisku Generuj wykres têtna 
		 */
        GenerateHeartRateGraph.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				examinationsBloodPressure = JDBC.selectExaminations(patientsList.get(j));
				/* Utorzenie obiektu wykres ciœnienia*/
				final Figure demo = new Figure(patientsList.get(j),"têtno","Wykres têtna");
	
			}
			
		});
        /* Panel w którym znajduj¹ siê przyciski z generacj¹ wykresów */
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
			 * Po klikniêciu na dany wiersz poka¿¹ siê jego wartoœci w panelu Badanie Pacjenta
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
	 * Metoda uzupe³niaj¹ca prawy panel, zawieraj¹cy tabelê z wprowadzonymi do niej pacjentami i ich wynikami
	 * @param PatientList Panel lista pacjentów
	 */
	public void patientListPanel(JPanel PatientList){
		/*
		 * Etap tworzenia tabeli
		 */
		table1 = new JTable(); 
        Object[] columns = {"Imiê", "Nazwisko","P³eæ","Pesel","Badanie"};
        /*
		 * Tworzenie modelu tabeli
		 */
        model1 = new DefaultTableModel(){

	/**
	* Funkcja za pomoc¹ której blokujemy mo¿liwoœæ edycji komórek w tabeli
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
        /** Inicjowanie listy pacjentów*/
        patientsList = JDBC.selectAllPatients();
        
        
        for (Patient patient : patientsList) {
			model1.addRow(patient.toTable());
			/** Inicjowanie listy badañ*/
			examinations = JDBC.selectExaminations(patient);
			patient.setExaminationsList(examinations);
		}
        /* Scroll w tabeli*/
        pane = new JScrollPane(table1);
		pane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		PatientList.add(pane, BorderLayout.CENTER);
        
		/*
		 * Przycisk Dodaj w panelu Badanie Pacjenta
		 */
        addButtonPatientList = new JButton("Dodaj");
       
		/*
		 * Funkcja obs³uguj¹ca naciœniêcie przycisku Dodaj
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
        /* Panel z przyciskami pod tabel¹ z list¹ pacjentów*/
        JPanel bp = new JPanel();
        
		bp.setLayout(new BoxLayout(bp, BoxLayout.X_AXIS));
		bp.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));

		/*
		 * Przycisk Usuñ w panelu Lista Pacjentów
		 */
		removeButtonPatientList = new JButton("Usuñ");
		/*
		 * Funkcja obs³uguj¹ca naciœniêcie przycisku Usuñ
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
					JOptionPane.showMessageDialog(null, "Nie doda³eœ ¿adnego Pacjenta do bazy. Co chcesz usun¹æ ?");
				else 
					JOptionPane.showMessageDialog(null, "Proszê wybraæ wiersz, który chcesz usun¹æ");
				
				
				
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
			 * Po klikniêciu na dany wiersz poka¿¹ siê jego wartoœci w panelach Pacjent oraz Badanie Pacjenta
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
				
				
				
				
					List<Examination> examinations = JDBC.selectExaminations(patientsList.get(j));
					for (Examination examination : examinations) {
						if(examination.getID_PACJENTA() == patientsList.get(j).getId())
							model2.addRow(examination.toTable());
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
 * Metoda sprawdzaj¹ca czy zosta³ podany jakikolwiek znak 
 * @param c zmienna pomocnicza
 * @return wartoœæ logiczna
 */
	 public boolean isStringOk(JTextField c)
		{
			if(c.getText().length()>0)
					return true;
					
			else 
			return false;
		}
	
	 /**
	  * Metoda sprawdzaj¹ca czy zosta³ podany jakikolwiek znak 
	  * @param c zmienna pomocnicza
	  * @return wartoœæ logiczna
	  */
	 public boolean isStringOk(String c)
		{
			if(c.length()>0)
				return true;
					
			
			else 
			return false;
		}
	 
	 /**
	  * Metoda sprawdzaj¹ca pesel
	  * @param c zmienna pomocnicza
	  * @return wartoœæ logiczna
	  */
	public boolean peselCheck(JTextField c)
	{
		if(c.getText().length()==11 && isInt(c))
		{
			for(int i=0; i<model1.getRowCount(); i++)
			{
				
				if(c.getText().equals(model1.getValueAt(i, 3).toString()) ){
					JOptionPane.showMessageDialog(null, "Nie ma dwóch osób o takim samym PESELU");
					return false;
				}	
			}
			
			return true;
		}
		else 
		return false;
	}
	
	
	 /**
	  * Druga metoda sprawdzaj¹ca pesel
	  * @param c zmienna pomocnicza
	  * @return wartoœæ logiczna
	  */
	public boolean peselCheck2(JTextField c)
	{
		if(c.getText().length()==11 && isInt(c))
			return true;
		
		else 
			return false;
	}
	
	
	
	/** Funkcja sprawdzaj¹ca datê
	 * @param c zmienna pomocnicza
	 * @return true
	 */
	public boolean dateCheck(String c)
	{
		if(c.isEmpty()){
			JOptionPane.showMessageDialog(null, "Podaj datê");
			return false;
		}
			
					
			for(int i=0; i<model2.getRowCount(); i++)
			{
				
				if(c.equals(model2.getValueAt(i, 0).toString()) ){
					JOptionPane.showMessageDialog(null, "Istnieje badanie z tak¹ dat¹");
					return false;
					
				}
				
				
			}
			
			return true;
	}
	
	
	/**
	 * Metoda sprawdzaj¹ca czy podana wartoœæ jest zmienn¹ typu INT
	 * @param c zmienna pomocnicza
	 * @return wartoœæ logiczna
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
	 * Metoda sprawdzaj¹ca czy podana wartoœæ jest zmienn¹ typu DOUBLE
	 * @param c zmienna pomocnicza
	 * @return wartoœæ logiczna
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
	 * Metoda czyszcz¹ca panel pacjenta
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
	 * Metoda czyszcz¹ca panel badania pacjenta
	 */
	public void clearExaminationPanel(){
		bloodPresure.setText("");
		heartRate.setText("");
		addButtonExaminationList.setEnabled(false);
		GeneratePressureGraph.setEnabled(false);
		GenerateHeartRateGraph.setEnabled(false);
	}
	
	/**
	* Metoda aktywuj¹ca lub blokuj¹ca panel pacjenta 
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
	* Metoda aktywuj¹ca lub blokuj¹ca panel badania pacjenta
	* @param c zmienna pomocnicza
	*/
	public void makeExaminationPanelActiveOrDisabled(boolean c){
		deconditionButtonPatientTest.setEnabled(c);
		saveButtonPatientTest.setEnabled(c);
		datePicker.setEnabled(c);
		bloodPresure.setEnabled(c);
		heartRate.setEnabled(c);
	}
	
	
	/**
	* Metoda aktywuj¹ca lub blokuj¹ca przyciski w panelu z list¹ badañ
	* @param c zmienna pomocnicza
	*/
	public void makeExaminationListActiveOrDisabled(boolean c){
		addButtonExaminationList.setEnabled(c);
		removeButtonExaminationList.setEnabled(c);
		GenerateHeartRateGraph.setEnabled(c);
		GeneratePressureGraph.setEnabled(c);
	}
	
	/**
	* Metoda aktywuj¹ca lub blokuj¹ca przyciski w panelu z list¹ pacjentów
	* @param c zmienna pomocnicza
	*/
	public void makePatientListActiveOrDisabled(boolean c){
		addButtonPatientList.setEnabled(c);
		removeButtonPatientList.setEnabled(c);
	}

	/**
	* Metoda przeprowadzaj¹ca konwersje ciœnienia ze  stringa na 2 inty
	* @param c zmienna pomocnicza
	*/
	boolean convertionPossible(String c){
		
			try {
				String[] x = new String[2];
				String[] y = new String[2];
				int[] ix = new int[2];
				int[] iy = new int[2];
				String pres;
				int i = 0;
				//konwersja
					pres = c;
					String splited = new String(pres);
					String[] splitedArray = null;
					/* Separtor*/
					splitedArray = splited.split("/");
					/* Ciœnieni skurczowe-string*/
					x[i] = splitedArray[0];
					/* Ciœnieni rozkurczowe-string*/
					y[i] = splitedArray[1];
					/* Ciœnieni skurczowe-int*/
					ix[i] = Integer.valueOf(x[i]);
					/* Ciœnieni rozkurczowe-int*/
					iy[i] = Integer.valueOf(y[i]);
					i = i + 1;
	        } catch (Exception e) {
	            return false;
	        }
	        return true;
		}	
}
