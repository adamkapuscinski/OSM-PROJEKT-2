package program;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.text.SimpleDateFormat;
import java.util.Arrays;

import javax.swing.JFrame;
import javax.swing.JLabel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.MinMaxCategoryRenderer;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.time.Day;
import org.jfree.data.time.Month;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

/**
 * Klasa, za pomoc� kt�rej tworzone s� wykresy 
 * @author Adam Kopu�ci�ski ,Rados�aw Bu�a
 */
public class Figure extends ApplicationFrame {
	/**
	* Zmienna serii danych
	*/
	private XYSeries series;
	/**
	* Zmienne okr�laj�ce min, max i �r�dni� warto��
	*/
	private int min=0,min1=0, max=0, max1=0, sre=0, sre1=0;
	
	/**
	* Konstruktor wykresu
	* @param patient dost�p do bada� pacjenta
	* @param flaga zmienna okr�laj�cy, kt�ry wykres ma by� utworzony
	* @param title Zmienna tytu� wykresu
	*/
	public Figure(Patient patient, String flaga, final String title) {

		super(title);
		/*
		 * Zmienna rok wykonania badnia-string
		 */
		String[] r = new String[patient.getExaminationsList().size()];
		/*
		 * Zmienna miesi�c wykonania badnia-string
		 */
		String[] m = new String[patient.getExaminationsList().size()];
		/*
		 * Zmienna dzie� wykonania badnia-string
		 */
		String[] d = new String[patient.getExaminationsList().size()];
		/*
		 * Zmienna rok wykonania badnia-int
		 */
		int[] ir = new int[patient.getExaminationsList().size()];
		/*
		 * Zmienna miesi�c wykonania badnia-int
		 */
		int[] im = new int[patient.getExaminationsList().size()];
		/*
		 * Zmienna dzie� wykonania badnia-int
		 */
		int[] id = new int[patient.getExaminationsList().size()];
		/*
		 * Zmienna zawieraj�ca pe�n� date badania
		 */
		 String datas;
		int q = 0;
		
		for (Examination examination : patient.getExaminationsList()) {

			// konwersja
			datas = examination.getDateString();

			String splited = new String(datas);
			String[] splitedArray = null;
			/* Spearator*/
			splitedArray = splited.split("-");
			/* Rok-string*/
			r[q] = splitedArray[0];
			/* miesi�c-string*/
			m[q] = splitedArray[1];
			/* dzie�-string*/
			d[q] = splitedArray[2];
			/* rok-int*/
			ir[q] = Integer.valueOf(r[q]);
			/* miesi�c-int*/
			im[q] = Integer.valueOf(m[q]);
			/* dzie�-int*/
			id[q] = Integer.valueOf(d[q]);
			q = q + 1;

		}
		
		
		
		if (flaga == "t�tno") {
			/*
			 * Tablica przechowuj�ca wyniki pomiar�w t�tna
			 */
			int[] heart_ratee = new int[patient.getExaminationsList().size()];
			/*
			 * Zmienna pomocnicza
			 */
			int i = 0;
			/*
			 * Wpisywanie wynik�w pomiru t�tna do tablicy
			 */
			for (Examination examination : patient.getExaminationsList()) {
				heart_ratee[i] = examination.getheartRate();
				i = i + 1;

			};
			/*
			 * Seria z odst�pami czasowymi na osi
			 */
	        final TimeSeries s1 = new TimeSeries("T�tno", Day.class);
	        
	        /*
			 * Obliczanie min max i �redniego wyniku spo�r�d wynik�w t�tna
			 */
	        for (int n = 0; n < patient.getExaminationsList().size(); n++) {
				
				s1.add(new Day(id[n] , im[n], ir[n] ), heart_ratee[n] );
				int sr = 0;
				min = Arrays.stream(heart_ratee).min().getAsInt();
				max = Arrays.stream(heart_ratee).max().getAsInt();
				for (int mm = 0; mm < patient.getExaminationsList().size(); mm++){
				sr += heart_ratee[mm] ;}
		        sre= sr/patient.getExaminationsList().size();
	        }

	        final TimeSeriesCollection dataset = new TimeSeriesCollection();
	        dataset.addSeries(s1);
	        /*
			 * Inicjalizacja wykresu wykresu t�tna
			 */
			final JFreeChart chart = ChartFactory.createTimeSeriesChart(
					title, 
					"Data", 
					"uderze�/min", 
					dataset,
					 true, 
					 true, 
					 false);

			
			final XYPlot plot = chart.getXYPlot();
			plot.mapDatasetToRangeAxis(1, 1);
			/*
			 * O� x wykresu
			 */
			final DateAxis axis = (DateAxis) plot.getDomainAxis();
			/*
			 * Format daty na osi x
			 */
			axis.setDateFormatOverride(new SimpleDateFormat("dd-MMM-yyyy"));

			/*
			 * Utworzenie okna z wykresem
			 */
	        JFrame jframe = new JFrame("Wykres t�tna");
	        jframe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);  
	        jframe.getContentPane().setLayout(new FlowLayout());
	        jframe.setSize(700,510);
	        ChartPanel chartPanel = new ChartPanel(chart);
	        jframe.getContentPane().add(chartPanel);
	        jframe.getContentPane().add(new JLabel("Minimalna warto��: " + min));
	        jframe.getContentPane().add(new JLabel("Maksymalna warto��: " + max));
	        jframe.getContentPane().add(new JLabel("�rednia: " + sre));
	        jframe.setVisible(true);
	        jframe.setResizable(false);
	        /*
			 * Umieszczenie okna z wykresem na �rodku ekranu
			 */
			Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
			int x = (int) ((dimension.getWidth() - jframe.getWidth()) / 2);
			int y = (int) ((dimension.getHeight() - jframe.getHeight()) / 2);
			jframe.setLocation(x, y);
			
		} else if (flaga == "ci�nienie") {
			/*
			 * Tablica przechowuj�ca wynik ci�nienia skurczowego-string
			 */
			String[] x = new String[patient.getExaminationsList().size()];
			/*
			 * Tablica przechowuj�ca wynik ci�nienia rozkurczowego-string
			 */
			String[] y = new String[patient.getExaminationsList().size()];
			/*
			 * Tablica przechowuj�ca wynik ci�nienia skurczowego-int
			 */
			int[] ix = new int[patient.getExaminationsList().size()];
			/*
			 * Tablica przechowuj�ca wynik ci�nienia rozkurczowego-int
			 */
			int[] iy = new int[patient.getExaminationsList().size()];
			/*
			 * Zmienna w przechowuj�ca pobrane wyniki bada�
			 */
			String pres;
			/*
			 * Zmienna pomocnicza
			 */
			int i = 0;
			for (Examination examination : patient.getExaminationsList()) {

				// konwersja
				pres = examination.getbloodPressure();

				String splited = new String(pres);
				String[] splitedArray = null;
				/*
				 * Separator
				 */
				splitedArray = splited.split("/");
				/*
				 * Ci�nienie skurczowe-string
				 */
				x[i] = splitedArray[0];
				/*
				 * Ci�nienie rozkurczowe-string
				 */
				y[i] = splitedArray[1];
				/*
				 * Ci�nienie skurczowe-int
				 */
				ix[i] = Integer.valueOf(x[i]);
				/*
				 * Ci�nienie rozkurczowe-int
				 */
				iy[i] = Integer.valueOf(y[i]);

				i = i + 1;

			}
	        final TimeSeries s2 = new TimeSeries("Ci�nienie t�tnicze skurczowe", Day.class);
	        final TimeSeries s3 = new TimeSeries("Ci�nienie t�tnicze rozkurczowe", Day.class);
	        
	        /*
			 * Obliczanie min max i �redniego wyniku spo�r�d wynik�w t�tna
			 */
	        for (int n = 0; n < patient.getExaminationsList().size(); n++) {
				
				s2.add(new Day(id[n] , im[n], ir[n] ), ix[n] );
				s3.add(new Day(id[n] , im[n], ir[n] ), iy[n] );
				int sr = 0;
				int sr1 = 0;
				min = Arrays.stream(ix).min().getAsInt();
				max = Arrays.stream(ix).max().getAsInt();
				for (int mm = 0; mm < patient.getExaminationsList().size(); mm++){
				sr += ix[mm] ;}
		        sre= sr/patient.getExaminationsList().size();
		        min1 = Arrays.stream(ix).min().getAsInt();
				max1 = Arrays.stream(ix).max().getAsInt();
				for (int mm = 0; mm < patient.getExaminationsList().size(); mm++){
				sr1 += ix[mm] ;}
		        sre1= sr/patient.getExaminationsList().size();
			}


	        final TimeSeriesCollection dataset = new TimeSeriesCollection();
	        dataset.addSeries(s2);
	        dataset.addSeries(s3);
	        /*
			 * Inicjalizacja wykresu cisnienia
			 */
	        final JFreeChart chart = ChartFactory.createTimeSeriesChart(
					title, 
					"Data", 
					"uderze�/min", 
					dataset,
					 true, 
					 true, 
					 false);

			
			final XYPlot plot = chart.getXYPlot();
			plot.mapDatasetToRangeAxis(1, 1);
			/*
			 * O� x wykresu
			 */
			final DateAxis axis = (DateAxis) plot.getDomainAxis();
			/*
			 * Format daty na osi x wykresu
			 */
			axis.setDateFormatOverride(new SimpleDateFormat("dd-MMM-yyyy"));

			/*
			 * Utworzenie okna z wykresem
			 */
	        JFrame jframe = new JFrame("Wykres ci�nienia");
	        jframe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);  
	        jframe.getContentPane().setLayout(new FlowLayout());
	        jframe.setSize(700,510);
	        ChartPanel chartPanel = new ChartPanel(chart);
	        jframe.getContentPane().add(chartPanel);
	        jframe.getContentPane().add(new JLabel("Warto�� dla ci�nienia skurczowego:    "));
	        jframe.getContentPane().add(new JLabel("Minimalna warto��: " + min));
	        jframe.getContentPane().add(new JLabel("Maksymalna warto��: " + max));
	        jframe.getContentPane().add(new JLabel("�rednia: " + sre));
	        jframe.getContentPane().add(new JLabel("Warto�� dla ci�nienia rozkurczowego:   "));
	        jframe.getContentPane().add(new JLabel("Minimalna warto��: " + min1));
	        jframe.getContentPane().add(new JLabel("Maksymalna warto��: " + max1));
	        jframe.getContentPane().add(new JLabel("�rednia: " + sre1));
	        jframe.setVisible(true);
	        jframe.setResizable(false);

			Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
			int xx = (int) ((dimension.getWidth() - jframe.getWidth()) / 2);
			int yy = (int) ((dimension.getHeight() - jframe.getHeight()) / 2);
			jframe.setLocation(xx, yy);

		}

	}

}