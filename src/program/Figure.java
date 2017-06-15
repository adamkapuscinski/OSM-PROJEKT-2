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
 * Klasa, za pomoc¹ której tworzone s¹ wykresy 
 * @author Adam Kopuœciñski ,Rados³aw Bu³a
 */
public class Figure extends ApplicationFrame {
	/**
	* Zmienna serii danych
	*/
	private XYSeries series;
	/**
	* Zmienne okrêœlaj¹ce min, max i œrêdni¹ wartoœæ
	*/
	private int min=0,min1=0, max=0, max1=0, sre=0, sre1=0;
	
	/**
	* Konstruktor wykresu
	* @param patient dostêp do badañ pacjenta
	* @param flaga zmienna okrœlaj¹cy, który wykres ma byæ utworzony
	* @param title Zmienna tytu³ wykresu
	*/
	public Figure(Patient patient, String flaga, final String title) {

		super(title);
		/*
		 * Zmienna rok wykonania badnia-string
		 */
		String[] r = new String[patient.getExaminationsList().size()];
		/*
		 * Zmienna miesi¹c wykonania badnia-string
		 */
		String[] m = new String[patient.getExaminationsList().size()];
		/*
		 * Zmienna dzieñ wykonania badnia-string
		 */
		String[] d = new String[patient.getExaminationsList().size()];
		/*
		 * Zmienna rok wykonania badnia-int
		 */
		int[] ir = new int[patient.getExaminationsList().size()];
		/*
		 * Zmienna miesi¹c wykonania badnia-int
		 */
		int[] im = new int[patient.getExaminationsList().size()];
		/*
		 * Zmienna dzieñ wykonania badnia-int
		 */
		int[] id = new int[patient.getExaminationsList().size()];
		/*
		 * Zmienna zawieraj¹ca pe³n¹ date badania
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
			/* miesi¹c-string*/
			m[q] = splitedArray[1];
			/* dzieñ-string*/
			d[q] = splitedArray[2];
			/* rok-int*/
			ir[q] = Integer.valueOf(r[q]);
			/* miesi¹c-int*/
			im[q] = Integer.valueOf(m[q]);
			/* dzieñ-int*/
			id[q] = Integer.valueOf(d[q]);
			q = q + 1;

		}
		
		
		
		if (flaga == "têtno") {
			/*
			 * Tablica przechowuj¹ca wyniki pomiarów têtna
			 */
			int[] heart_ratee = new int[patient.getExaminationsList().size()];
			/*
			 * Zmienna pomocnicza
			 */
			int i = 0;
			/*
			 * Wpisywanie wyników pomiru têtna do tablicy
			 */
			for (Examination examination : patient.getExaminationsList()) {
				heart_ratee[i] = examination.getheartRate();
				i = i + 1;

			};
			/*
			 * Seria z odstêpami czasowymi na osi
			 */
	        final TimeSeries s1 = new TimeSeries("Têtno", Day.class);
	        
	        /*
			 * Obliczanie min max i œredniego wyniku spoœród wyników têtna
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
			 * Inicjalizacja wykresu wykresu têtna
			 */
			final JFreeChart chart = ChartFactory.createTimeSeriesChart(
					title, 
					"Data", 
					"uderzeñ/min", 
					dataset,
					 true, 
					 true, 
					 false);

			
			final XYPlot plot = chart.getXYPlot();
			plot.mapDatasetToRangeAxis(1, 1);
			/*
			 * Oœ x wykresu
			 */
			final DateAxis axis = (DateAxis) plot.getDomainAxis();
			/*
			 * Format daty na osi x
			 */
			axis.setDateFormatOverride(new SimpleDateFormat("dd-MMM-yyyy"));

			/*
			 * Utworzenie okna z wykresem
			 */
	        JFrame jframe = new JFrame("Wykres têtna");
	        jframe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);  
	        jframe.getContentPane().setLayout(new FlowLayout());
	        jframe.setSize(700,510);
	        ChartPanel chartPanel = new ChartPanel(chart);
	        jframe.getContentPane().add(chartPanel);
	        jframe.getContentPane().add(new JLabel("Minimalna wartoœæ: " + min));
	        jframe.getContentPane().add(new JLabel("Maksymalna wartoœæ: " + max));
	        jframe.getContentPane().add(new JLabel("Œrednia: " + sre));
	        jframe.setVisible(true);
	        jframe.setResizable(false);
	        /*
			 * Umieszczenie okna z wykresem na œrodku ekranu
			 */
			Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
			int x = (int) ((dimension.getWidth() - jframe.getWidth()) / 2);
			int y = (int) ((dimension.getHeight() - jframe.getHeight()) / 2);
			jframe.setLocation(x, y);
			
		} else if (flaga == "ciœnienie") {
			/*
			 * Tablica przechowuj¹ca wynik ciœnienia skurczowego-string
			 */
			String[] x = new String[patient.getExaminationsList().size()];
			/*
			 * Tablica przechowuj¹ca wynik ciœnienia rozkurczowego-string
			 */
			String[] y = new String[patient.getExaminationsList().size()];
			/*
			 * Tablica przechowuj¹ca wynik ciœnienia skurczowego-int
			 */
			int[] ix = new int[patient.getExaminationsList().size()];
			/*
			 * Tablica przechowuj¹ca wynik ciœnienia rozkurczowego-int
			 */
			int[] iy = new int[patient.getExaminationsList().size()];
			/*
			 * Zmienna w przechowuj¹ca pobrane wyniki badañ
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
				 * Ciœnienie skurczowe-string
				 */
				x[i] = splitedArray[0];
				/*
				 * Ciœnienie rozkurczowe-string
				 */
				y[i] = splitedArray[1];
				/*
				 * Ciœnienie skurczowe-int
				 */
				ix[i] = Integer.valueOf(x[i]);
				/*
				 * Ciœnienie rozkurczowe-int
				 */
				iy[i] = Integer.valueOf(y[i]);

				i = i + 1;

			}
	        final TimeSeries s2 = new TimeSeries("Ciœnienie têtnicze skurczowe", Day.class);
	        final TimeSeries s3 = new TimeSeries("Ciœnienie têtnicze rozkurczowe", Day.class);
	        
	        /*
			 * Obliczanie min max i œredniego wyniku spoœród wyników têtna
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
					"uderzeñ/min", 
					dataset,
					 true, 
					 true, 
					 false);

			
			final XYPlot plot = chart.getXYPlot();
			plot.mapDatasetToRangeAxis(1, 1);
			/*
			 * Oœ x wykresu
			 */
			final DateAxis axis = (DateAxis) plot.getDomainAxis();
			/*
			 * Format daty na osi x wykresu
			 */
			axis.setDateFormatOverride(new SimpleDateFormat("dd-MMM-yyyy"));

			/*
			 * Utworzenie okna z wykresem
			 */
	        JFrame jframe = new JFrame("Wykres ciœnienia");
	        jframe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);  
	        jframe.getContentPane().setLayout(new FlowLayout());
	        jframe.setSize(700,510);
	        ChartPanel chartPanel = new ChartPanel(chart);
	        jframe.getContentPane().add(chartPanel);
	        jframe.getContentPane().add(new JLabel("Wartoœæ dla ciœnienia skurczowego:    "));
	        jframe.getContentPane().add(new JLabel("Minimalna wartoœæ: " + min));
	        jframe.getContentPane().add(new JLabel("Maksymalna wartoœæ: " + max));
	        jframe.getContentPane().add(new JLabel("Œrednia: " + sre));
	        jframe.getContentPane().add(new JLabel("Wartoœæ dla ciœnienia rozkurczowego:   "));
	        jframe.getContentPane().add(new JLabel("Minimalna wartoœæ: " + min1));
	        jframe.getContentPane().add(new JLabel("Maksymalna wartoœæ: " + max1));
	        jframe.getContentPane().add(new JLabel("Œrednia: " + sre1));
	        jframe.setVisible(true);
	        jframe.setResizable(false);

			Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
			int xx = (int) ((dimension.getWidth() - jframe.getWidth()) / 2);
			int yy = (int) ((dimension.getHeight() - jframe.getHeight()) / 2);
			jframe.setLocation(xx, yy);

		}

	}

}