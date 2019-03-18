
import javax.swing.*;


import javax.swing.table.DefaultTableModel;
import org.apache.poi.ss.formula.functions.Column;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Row.MissingCellPolicy;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

public class InterfaceTrialC {

	public JFrame frame;
	public JFrame frame4;
	public JButton button;
	public JButton button1;
	public ImageIcon icon;
	public JLabel label;
	public File selectedFile;
	public JButton BigButton;
	public JFileChooser filechooser;
	public FileInputStream fis;
	public Workbook workbook;
	public Sheet firstsheet;
	public Row row;

	// ili�ki matrisini olu�turabilmek i�in gerekli parametrelerin giri�ini
	// sa�layacak olan ekran i�in frame ve components

	public JFrame frame2;
	public JTextField altmalzemeSay�s�;
	public JTextField �stmalzemeSay�s�;
	public int altMalzeme;
	public int �stMalzeme;
	public JButton onayButton;

	// ili�ki matrisi olu�turuldaktan sonra,algoritmay� �al��t�rabilmek i�in

	// gerekli parametrelerin giri�inin yap�laca��

	// frame i�in gerekli global de�i�kenler

	public JFrame frame3;
	public JTextField tepsiSay�s�;
	public Integer TepsiMiktar�;
	public JButton addButton;
	public ArrayList<JTextField> capacityTextField;
	public ArrayList<Integer> Capacities;
	public JButton addAllParameterButton;
	// Simulated Annealing

	public ArrayList<Integer> eachSolutions;
	public HashMap<Integer, ArrayList<Integer>> allSolutions;
	public double alpha;
	public int iterationNumber;
	public double sonkacDikkatY�zde;
	public double cag�r�lmaY�zde;

	// public double[][] utilityValues;

	public double temperature;
	public double min_Tempt;
	public int[][] Matrix;
	public ArrayList<String> MaterialsName;

	public int[] rowMatrixIndexGlobal;

	public Object[][] transpozedResults;

	public JButton excelButton;
	
	public JFrame loadFrame;

	public InterfaceTrialC() throws IOException {

		// Initial Values for Simulated Annealing

		eachSolutions = new ArrayList<Integer>();
		allSolutions = new HashMap<Integer, ArrayList<Integer>>();
		alpha = 0.99;
		iterationNumber = 30;
		sonkacDikkatY�zde = 0.6;
		cag�r�lmaY�zde = 0.6;
		// utilityValues = null;
		temperature = 1.00;
		min_Tempt = 0.000001;
		icon = new ImageIcon("F16.jpg");
		Image scaleImage = icon.getImage().getScaledInstance(1430, 1000, Image.SCALE_DEFAULT);
		icon = new ImageIcon(scaleImage);
		JLabel label1 = new JLabel(icon);
		ImageIcon loading = new ImageIcon("plane.ico");
		
	    loadFrame = new JFrame("LOading");

	    ImageIcon loading2 = new ImageIcon("loader-white.gif");
	    loadFrame.add(new JLabel("loading... ", loading2, JLabel.CENTER));

	    loadFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    loadFrame.setSize(1000, 1000);
	    
	
		
		
		

		button = new JButton("Dosya Se�mek i�in T�klay�n�z");
		button.setBackground(Color.YELLOW);

		// button1 = new JButton("S�reci Ba�lat");

		BigButton = new JButton("�IK �IK BA�LAT");
		BigButton.setBackground(Color.ORANGE);
		BigButton.setPreferredSize(new Dimension(100, 100));

		filechooser = new JFileChooser();
		fis = null;
		workbook = null;
		firstsheet = null;
		row = null;
		selectedFile = null;

		// for second frame.
		// set up components

		altmalzemeSay�s� = new JTextField(5);
		�stmalzemeSay�s� = new JTextField(5);
		tepsiSay�s� = new JTextField(5);

		altMalzeme = 0;
		�stMalzeme = 0;
		TepsiMiktar� = 0;
		Matrix = null;

		rowMatrixIndexGlobal = null;

		MaterialsName = new ArrayList<>();

		onayButton = new JButton("TAMAM");

		addAllParameterButton = new JButton("Hepsi Eklendi");

		addButton = new JButton("Tepsi Kapasiteleri ��in T�klay�n�z");

		capacityTextField = new ArrayList<JTextField>();

		Capacities = new ArrayList<Integer>();
 		excelButton = new JButton();

		excelButton.setIcon(new ImageIcon("excel_icon.png"));

		// T�m sonu�lar al�nd�ktan sonra frame5'in i�erisinde bulunan excel
		// butonu, i�levi;sonu�lar� excel olarak masas�st�ne yans�t�r.

		excelButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				try {
					writeResultstoExcel();
					JOptionPane.showMessageDialog(null,
							"Sonu�lar Masa�st�ne SONU�LAR isimli excel dosyas� olarak olu�turuldu");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			};
		});

		onayButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// read height and weight info from text fields
				String altMalzemeST = altmalzemeSay�s�.getText();
				altMalzeme = Integer.parseInt(altMalzemeST);
				String �stMalzemeST = �stmalzemeSay�s�.getText();
				�stMalzeme = Integer.parseInt(�stMalzemeST);
				// compute BMI and display it onscreen
				frame2.setVisible(false);

				if (altMalzeme != 0 && �stMalzeme != 0) {
					try {
						loadFrame.setVisible(true);
						generatingMatrix(GettingData(altMalzeme, �stMalzeme), altMalzeme, �stMalzeme);
						loadFrame.setVisible(false);
						int response = JOptionPane.showConfirmDialog(null, "Malzeme ili�ki Matrisi Olu�turuldu,"
								+ " Excel Format�nda indirmek ister misiniz?");
						if (response == JOptionPane.YES_OPTION) {
							loadFrame.setVisible(true);
							writeMatrixtoExcel();
							loadFrame.setVisible(false);
							JOptionPane.showMessageDialog(null, "SONU� MATR�S olarak excel dosyan�z olu�turuldu");
						}

						int response2 = JOptionPane.showConfirmDialog(null, " S�re� Devam Etsin mi?");
						if (response2 == JOptionPane.YES_OPTION) {
							frame3.setVisible(true);
						} else {
							System.exit(0);
						}
					} catch (IOException e1) {
						e1.printStackTrace();
					}

					// System.exit(0);
				}
			}
		});

		// layout
		JPanel north = new JPanel(new GridLayout(2, 2));
		north.add(new JLabel("Malzeme Say�s�: "));
		north.add(altmalzemeSay�s�);
		north.add(new JLabel("Toplam Operasyon: "));
		north.add(�stmalzemeSay�s�);

		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// filechooser = new JFileChooser();
				int returnValue = filechooser.showOpenDialog(null);
				if (returnValue == JFileChooser.APPROVE_OPTION) {
					selectedFile = filechooser.getSelectedFile();
					JOptionPane.showMessageDialog(null, "Dosyan�z Y�klendi, �imdi S�reci Ba�latabilirsiniz");
				}
			};
		});

		BigButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				frame2.setVisible(true);
			}
		});

		JLabel label=new JLabel();
		label.setOpaque(true);
		label.setBackground(Color.WHITE);
		
		frame = new JFrame("OPTi");
		frame.setLayout(new BorderLayout());
		
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setDefaultLookAndFeelDecorated(true);
		
		frame.setSize(new Dimension(500, 500));
		frame.setLocation(new Point(0, 0));
		frame.add(label, BorderLayout.CENTER);
		frame.add(button, BorderLayout.NORTH);

		// frame.add(button1, BorderLayout.EAST);
		frame.add(BigButton, BorderLayout.SOUTH);
		// frame.add(new JLabel("",loading,JLabel.CENTER));
		frame.pack();
		frame.setVisible(true);
		// overall frame

		frame2 = new JFrame("parametreler");
		frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame2.setDefaultLookAndFeelDecorated(true);
		frame2.setLayout(new BorderLayout());
		frame2.add(north, BorderLayout.NORTH);
		frame2.add(onayButton, BorderLayout.SOUTH);
		frame2.pack();

		JPanel �st = new JPanel(new GridLayout(1, 3));
		�st.add(new JLabel("Tepsi Say�s�:"));
		�st.add(tepsiSay�s�);
		�st.add(addButton);
		frame3 = new JFrame("Algoritma i�in Gerekli Parametreler");
		frame3.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame3.setSize(new Dimension(800, 500));
		frame3.setLocation(new Point(500, 300));
		frame3.setLayout(new BorderLayout());
		frame3.add(�st, BorderLayout.NORTH);
		addButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				String temp = tepsiSay�s�.getText();
				TepsiMiktar� = Integer.parseInt(temp);
				for (int i = 0; i < TepsiMiktar�; i++) {
					capacityTextField.add(new JTextField());
				}
				JPanel center = new JPanel(new GridLayout(TepsiMiktar�, 2));
				for (int i = 0; i < TepsiMiktar�; i++) {
					center.add(new JLabel("Tepsi " + (i + 1)));
					center.add(capacityTextField.get(i));
				}
				frame3.setVisible(false);
				frame3.add(center, BorderLayout.CENTER);
				frame3.add(addAllParameterButton, BorderLayout.SOUTH);
				frame3.pack();
				frame3.setVisible(true);
			}
		});

		addAllParameterButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for (int i = 0; i < TepsiMiktar�; i++) {
					int tempcapacity;
					String temp = capacityTextField.get(i).getText();
					Capacities.add(Integer.parseInt(temp));
					System.out.println((i + 1) + ". tepsi capacity:" + Capacities.get(i));
				}
				JOptionPane.showMessageDialog(null, "Kapasiteler Girildi, Algoritma �al��t�r�l�yor");
				frame3.setVisible(false);
				InitialSolution();
				HashMap<Integer, ArrayList<Integer>> endallSolutions = new HashMap<Integer, ArrayList<Integer>>();
				try {
					endallSolutions = Tavlama(allSolutions);
					System.out.println(endallSolutions.toString());
					sonu�lar�nEkranaYazd�r�lmas�();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
	}

	public int[][] GettingData(int altMalzemeX, int �stMalzemeX) throws IOException {

		fis = new FileInputStream(selectedFile);
		// create workbook instance that refers to.xlsx file
		workbook = new XSSFWorkbook(fis);
		// workbook.setMissingCellPolicy(MissingCellPolicy.RETURN_NULL_AND_BLANK);
		firstsheet = workbook.getSheetAt(0);
		int Data[][] = new int[altMalzemeX][�stMalzemeX + 1];
		int newData[][] = new int[altMalzemeX][�stMalzemeX];
		System.out.println("girdim");
		for (int i = 0; i < altMalzemeX; i++) {
			row = firstsheet.getRow(i);
			for (int j = 1; j < �stMalzemeX + 1; j++) {
				if (row.getCell(j) == null) {
					row.createCell(j);
				} else {
					Data[i][j] = (int) row.getCell(j).getNumericCellValue();
				}
			}
		}

		String[] MaterialsNameX = new String[altMalzemeX];

		DataFormatter formatter = new DataFormatter();

		for (int i = 0; i < altMalzemeX; i++) {
			row = firstsheet.getRow(i);
			MaterialsNameX[i] = formatter.formatCellValue(row.getCell(0));
			MaterialsName.add(MaterialsNameX[i]);
		}

		for (int i = 0; i < altMalzemeX; i++) {
			for (int j = 0; j < �stMalzemeX - 2; j++) {
				newData[i][j] = Data[i][j + 2];
			}
		}
		workbook.close();

		return newData;
	}

	public int[][] generatingMatrix(int dataFromExcel[][], int altMalzemeX, int �stMalzemeX) {
		
		int[][] DMatrix = new int[altMalzemeX][altMalzemeX];
		int[][] newTempMatrix = new int[altMalzemeX][altMalzemeX];

		int[][] newTempMatrix2 = new int[altMalzemeX][altMalzemeX];
		int RowMatrixUti[] = new int[altMalzemeX];
		int ColumnMatrixUti[] = new int[altMalzemeX];

		int RowMatrixIndex[] = new int[altMalzemeX];

		for (int i = 0; i < RowMatrixIndex.length; i++) {
			RowMatrixIndex[i] = i;
		}

		int ColumnMatrixIndex[] = new int[altMalzemeX];

		for (int i = 0; i < RowMatrixIndex.length; i++) {
			ColumnMatrixIndex[i] = i;
		}

		ArrayList<ArrayList<Integer>> allArrays = new ArrayList<ArrayList<Integer>>();

		for (int i = 0; i < altMalzemeX; i++) {
			ArrayList<Integer> smallArray = new ArrayList<Integer>();
			allArrays.add(smallArray);
		}

		for (int i = 0; i < altMalzemeX; i++) {
			for (int j = 0; j < �stMalzemeX; j++) {
				if (dataFromExcel[i][j] >= 1) {
					allArrays.get(i).add(j);
				}
			}
		}

		/*
		 * for (int i = 0; i < allArrays.size(); i++) {
		 * 
		 * System.out.print(MaterialsName.get(i) + ":");
		 * 
		 * for (int j = 0; j < allArrays.get(i).size(); j++) {
		 * 
		 * System.out.print(allArrays.get(i).get(j) + ",");
		 * 
		 * }
		 * 
		 * System.out.println();
		 * 
		 * }
		 * 
		 */

		for (int i = 0; i < altMalzemeX; i++) {
			// System.out.println(i+".i:"+allArrays.get(i).size());
			for (int j = 0; j < altMalzemeX; j++) {
				for (int k = 0; k < allArrays.get(i).size(); k++) {
					for (int m = 0; m < allArrays.get(j).size(); m++) {
						// System.out.print("k "+k+":m "+m+" & ");
						if ((allArrays.get(i).get(k).intValue() == allArrays.get(j).get(m).intValue()) && (i != j)) {
							// System.out.print(k+":"+m+">"+DMatrix[i][j]+" ");
							DMatrix[i][j] = DMatrix[i][j] + 1;
							// System.out.print(DMatrix[i][j]);
						}
					}
					System.out.println();
				} // System.out.println();
			}
		}
		/*
		 * 
		 * for (int i = 0; i < altMalzemeX; i++) { for (int j = 0; j <
		 * altMalzemeX; j++) { System.out.print(Matrix[i][j] + ","); }
		 * System.out.println(); }

		 */

		/*
		 * for (int i = 0; i < allArrays.size(); i++) {
		 * 
		 * System.out.print(MaterialsName.get(i) + ":");
		 * 
		 * for (int j = 0; j < allArrays.get(i).size(); j++) {
		 * 
		 * System.out.print(allArrays.get(i).get(j) + ",");
		 * 
		 * }
		 * 
		 * System.out.println();
		 * 
		 * }
		 * 
		 */

		// Sat�rlar� dikkate alarak b�y�kten k����e malzemeleri s�ralamak i�in

		// Sat�rlar�n Toplam�n� alan yer

		for (int i = 0; i < altMalzemeX; i++) {

			for (int j = 0; j < altMalzemeX; j++) {

				RowMatrixUti[i] = DMatrix[i][j] + RowMatrixUti[i];
			}
		}
		boolean swapped;

		for (int i = RowMatrixUti.length - 1; i > 0; i--) {

			swapped = false;

			for (int j = RowMatrixUti.length - 1; j > 0; j--)
				if (RowMatrixUti[j] > RowMatrixUti[j - 1]) {
					int temp = RowMatrixUti[j - 1];
					RowMatrixUti[j - 1] = RowMatrixUti[j];
					RowMatrixUti[j] = temp;
					int temp2 = RowMatrixIndex[j - 1];
					RowMatrixIndex[j - 1] = RowMatrixIndex[j];
					RowMatrixIndex[j] = temp2;
					swapped = true;
				}
			if (!swapped)

				break;

		}

		/*
		 * 
		 * for (int i = 0; i < RowMatrixIndex.length; i++) {
		 * 
		 * System.out.println(RowMatrixIndex[i]+","); }
		 * 
		 */

		for (int i = 0; i < RowMatrixIndex.length; i++) {
			for (int j = 0; j < RowMatrixIndex.length; j++) {
				newTempMatrix[i][j] = DMatrix[RowMatrixIndex[i]][j];
			}
		}

		for (int i = 0; i < RowMatrixIndex.length; i++) {
			for (int j = 0; j < RowMatrixIndex.length; j++) {
				DMatrix[i][j] = newTempMatrix[i][j];
			}
		}

		// RowMatrixIndex i global olarak kullanabilmek i�in
		// rowMatrixIndexGlobal global de�i�kenine atan�yor

		rowMatrixIndexGlobal = RowMatrixIndex;
		// S�tunlar� dikkate alarak b�y�kten k����e malzemeleri s�ralamak i�in
		// S�tunlar�n Toplam�n� alan yer
		for (int j = 0; j < altMalzemeX; j++) {
			for (int i = 0; i < altMalzemeX; i++) {
				ColumnMatrixUti[j] = DMatrix[i][j] + ColumnMatrixUti[j];
			}
		}
		boolean swapped2;
		for (int i = ColumnMatrixUti.length - 1; i > 0; i--) {
			swapped2 = false;
			for (int j = ColumnMatrixUti.length - 1; j > 0; j--)
				if (ColumnMatrixUti[j] > ColumnMatrixUti[j - 1]) {
					int temp = ColumnMatrixUti[j - 1];
					ColumnMatrixUti[j - 1] = ColumnMatrixUti[j];
					ColumnMatrixUti[j] = temp;
					int temp2 = ColumnMatrixIndex[j - 1];
					ColumnMatrixIndex[j - 1] = ColumnMatrixIndex[j];
					ColumnMatrixIndex[j] = temp2;
					swapped2 = true;
				}
			if (!swapped2)
			break;
		}

		for (int j = 0; j < ColumnMatrixIndex.length; j++) {
			for (int i = 0; i < ColumnMatrixIndex.length; i++) {
				newTempMatrix2[i][j] = newTempMatrix[i][ColumnMatrixIndex[j]];
			}
		}

		for (int i = 0; i < ColumnMatrixIndex.length; i++) {
			for (int j = 0; j < ColumnMatrixIndex.length; j++) {
				DMatrix[i][j] = newTempMatrix2[i][j];
			}
		}

		// YAZDIRMAK ���N
		for (int i = 0; i < altMalzemeX; i++) {
			System.out.print(MaterialsName.get(rowMatrixIndexGlobal[i]) + ":");
			for (int j = 0; j < altMalzemeX; j++) {
				System.out.print(DMatrix[i][j] + ",");
			}
			System.out.println();
		}

		Matrix = DMatrix;
		return Matrix;

	}

	public void writeMatrixtoExcel() throws IOException {
		FileOutputStream out = new FileOutputStream(new File("C:\\Users\\Emin\\Desktop\\SONU� MATR�S.xlsx"));
		Workbook myworkbook = new XSSFWorkbook();
		Sheet spreadSheet = myworkbook.createSheet("sonu�lar");
		Row row;
		Cell cell;
		// Excelin sol column'na malzeme ID lerini yazma i�in

		for (int i = 0; i < altMalzeme; i++) {
			row = spreadSheet.createRow(i);
			cell = row.createCell(0);
			String id = "" + (i);
			cell.setCellValue(id);
		}

		// Matrisin kendisi, malzeme IDleri yok

		for (int i = 0; i < altMalzeme; i++) {
			row = spreadSheet.createRow(i);
			for (int j = 0; j < altMalzeme + 1; j++) {
				if (j == 0) {
					cell = row.createCell(j);
					String s = "" + MaterialsName.get(rowMatrixIndexGlobal[i]);
					cell.setCellValue(s);
				} else {
					if (Matrix[i][j - 1] >= 1) {
						cell = row.createCell(j);
						String s = "" + Matrix[i][j - 1];
						cell.setCellValue(s);
					}
				}
			}
		}

		System.out.println("B� BAK BAKALIM, Day�m�n p�tt���");
		myworkbook.write(out);
		myworkbook.close();
	}

	public void sonu�lar�nEkranaYazd�r�lmas�() {

		JFrame frame5 = new JFrame("SONU�LAR");
		frame5.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame5.setLayout(new BorderLayout());
		frame5.setSize(new Dimension(800, 500));
		frame5.setLocation(new Point(200, 100));
		frame5.add(excelButton, BorderLayout.EAST);
		String[] column = new String[TepsiMiktar�];
		for (int i = 0; i < column.length; i++) {
			column[i] = (i + 1) + ".TEPS� ";
		}

		int maxCapacity = 0;
		for (int i = 0; i < TepsiMiktar�; i++) {
			if (Capacities.get(i) > maxCapacity) {
				maxCapacity = Capacities.get(i);
			}
		}

		Object[][] ob = new Object[allSolutions.size()][maxCapacity];
		for (int i = 0; i < allSolutions.size(); i++) {
			for (int j = 0; j < allSolutions.get(i).size(); j++) {
				ob[i][j] = MaterialsName.get(rowMatrixIndexGlobal[allSolutions.get(i).get(j)]);
			}
		}

		Object[][] ob2 = new Object[maxCapacity][allSolutions.size()];

		for (int i = 0; i < maxCapacity; i++) {
			for (int j = 0; j < allSolutions.size(); j++) {
				ob2[i][j] = ob[j][i];
			}
		}

		DefaultTableModel model = new DefaultTableModel(ob2, column);
		JTable table = new JTable(model);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.setAutoCreateRowSorter(true);

		for (int i = 0; i < TepsiMiktar�; i++) {
			table.getColumnModel().getColumn(i).setPreferredWidth(145);
		}

		JScrollPane pane = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		// pane.getHorizontalScrollBar();

	int panelsizeXaxis;
		int panelsizeYaxis;

		if (145 * TepsiMiktar� < 1015) {
			panelsizeXaxis = 145 * TepsiMiktar�;
		} else {
			panelsizeXaxis = 1015;
		}

		if (table.getRowHeight() * maxCapacity < 600) {
			panelsizeYaxis = (int) (table.getRowHeight() * maxCapacity * 1.5);
		} else {
			panelsizeYaxis = 750;
		}

		pane.setPreferredSize(new Dimension(panelsizeXaxis, panelsizeYaxis));
		JPanel panel = new JPanel();
		panel.add(pane);
		frame5.add(panel, BorderLayout.CENTER);
		frame5.setVisible(true);

	}

	public void writeResultstoExcel() throws IOException {

		FileOutputStream out = new FileOutputStream(new File("C:\\Users\\Emin\\Desktop\\SONU�LAR.xlsx"));
		Workbook myworkbook = new XSSFWorkbook();
		Sheet spreadSheet = myworkbook.createSheet("malzeme_yer_sonuclari");
		Row row;
		Cell cell;
		Column column;
		// Excelin en �st sat�r�na tepsi isimlerini yazmak i�in

		row = spreadSheet.createRow(0);

		for (int i = 0; i < TepsiMiktar�; i++) {
			cell = row.createCell(i);
			String tepsi = (i + 1) + ". TEPS�";
			cell.setCellValue(tepsi);
		}

		// Sonu�lar�n uygun formata getirilerek excele yazd�r�lmas� i�in yap�lan
		// �al��malar
		// Nedeni olarak, ilk �nce rowlar sonra celler yazd�r�l�yor, fakar ben
		// ise sonu�lar� Column Column olarak yazd�rmak istiyorum
		// O y�zden eldeki sonu�un transpozu al�nd�.

		int maxCapacity = 0;
		for (int i = 0; i < TepsiMiktar�; i++) {
			if (Capacities.get(i) > maxCapacity) {
				maxCapacity = Capacities.get(i);
			}
		}

		String[][] tepsideliMat = new String[allSolutions.size()][maxCapacity];
		for (int i = 0; i < allSolutions.size(); i++) {
			for (int j = 0; j < allSolutions.get(i).size(); j++) {
				tepsideliMat[i][j] = MaterialsName.get(rowMatrixIndexGlobal[allSolutions.get(i).get(j)]);
			}
		}

		String[][] tepsideliMatREV = new String[maxCapacity][allSolutions.size()];

		for (int i = 0; i < maxCapacity; i++) {
			for (int j = 0; j < allSolutions.size(); j++) {
				tepsideliMatREV[i][j] = tepsideliMat[j][i];
			}
		}

		// Malzeme isimleri, her bir s�tunun alt�na, o tepside bulunmas� gereken
		// malzemeler

		for (int i = 1; i < maxCapacity + 1; i++) {
			row = spreadSheet.createRow(i);
			for (int j = 0; j < allSolutions.size(); j++) {
				cell = row.createCell(j);
				String s = "" + tepsideliMatREV[i - 1][j];
				cell.setCellValue(s);
			}
		}

		System.out.println("Tepsi Sonu�lar�n� Yazd�rd�m");
		myworkbook.write(out);
		myworkbook.close();
	}

	////////////////////////////////// SIMULATED ANNEALING ///////////////////////////////////////

	public HashMap<Integer, ArrayList<Integer>> InitialSolution() {
		int ba�lang�� = 0;
		for (int i = 0; i < TepsiMiktar�; i++) {
			ArrayList<Integer> solutions = new ArrayList<Integer>();
			for (int j = ba�lang��; j < ba�lang�� + Capacities.get(i); j++) {
				solutions.add(j);
			}
			allSolutions.put(i, solutions);
			ba�lang�� = ba�lang�� + Capacities.get(i);
		}
		for (int i = 0; i < allSolutions.size(); i++) {
			System.out.println(allSolutions.get(i).toString());
		}

		return allSolutions;
	}

	public HashMap<Integer, ArrayList<Integer>> Tavlama(HashMap<Integer, ArrayList<Integer>> solution)
			throws IOException {

		double oldutility = utilityFunction(solution);
		HashMap<Integer, ArrayList<Integer>> newsolution = new HashMap<Integer, ArrayList<Integer>>();
		HashMap<Integer, ArrayList<Integer>> temppsolution = new HashMap<Integer, ArrayList<Integer>>();
		while (temperature > min_Tempt) {
			temppsolution.putAll(solution);
			for (int i = 0; i < iterationNumber; i++) {
				for (int j = i + 1; j < solution.size(); j++) {
					double newutility = 0;
					int ide�eri = i % (solution.size() - 1);
					// if (i == j && i < solution.size() - 1) {
					// j++;
					// }
					// if (Temperature > 0.0001) {
					newsolution = generatingNeighbor(temppsolution, ide�eri, j);
					newutility = utilityFunction(newsolution);
					double acceptPro = acceptanceProbability(oldutility, newutility, temperature);
					System.out.println("old: " + oldutility + " new: " + newutility);
					if (acceptPro > Math.random()) {
						solution = newsolution;
						oldutility = newutility;
						System.out.println("girdim");
					}
					// }

					/*
					 * 
					 * else {
					 *
					 * 
					 * 
					 * for (int m = 0; m < temppsolution.get(j).size() - 1; m++)
					 * 
					 * for (int k = m + 1; k < temppsolution.get(j).size(); k++)
					 * 
					 * { newsolution = generatingNeighbor2(temppsolution,
					 * 
					 * ide�eri, j, m, k); newutility =
					 * 
					 * utilityFunction(newsolution); double acceptPro =
					 * 
					 * acceptanceProbability(oldutility, newutility,
					 * 
					 * Temperature);
					 *
					 * 
					 * System.out.println("old: " + oldutility + " new: " +
					 * 
					 * newutility); if (acceptPro > Math.random()) { solution =
					 * 
					 * newsolution; oldutility = newutility;
					 * 
					 * System.out.println("arkadan girdim"); }
					 *
					 * 
					 * 
					 * } }
					 * 
					 */
				}
			}
			temperature = temperature * alpha;
		}
		this.allSolutions = solution;
		return allSolutions;
	}

	public int utilityFunction(HashMap<Integer, ArrayList<Integer>> solutionx) {
		int utilityValue = 0;
		ArrayList<Integer> subsolution = new ArrayList<Integer>();
		for (int i = 0; i < solutionx.size(); i++) {
			subsolution = solutionx.get(i);
			// System.out.println(subsolution.toString());
			for (int j = 0; j < subsolution.size(); j++) {
				for (int k = 0; k < subsolution.size(); k++) {
					utilityValue = utilityValue + Matrix[subsolution.get(j)][subsolution.get(k)];
				}
			}
		}
		return utilityValue;
	}

	public HashMap<Integer, ArrayList<Integer>> generatingNeighbor(HashMap<Integer, ArrayList<Integer>> solutionxx,

			int ii, int jj) throws IOException {
		HashMap<Integer, ArrayList<Integer>> newsolution = new HashMap<Integer, ArrayList<Integer>>();
		for (int i = 0; i < solutionxx.size(); i++) {
			ArrayList<Integer> ttt = new ArrayList<Integer>();
			for (int j = 0; j < solutionxx.get(i).size(); j++) {
				ttt.add(solutionxx.get(i).get(j));
			}
			newsolution.put(i, ttt);
		}

		ArrayList<Integer> temp = new ArrayList<Integer>();
		ArrayList<Integer> temp2 = new ArrayList<Integer>();
		ArrayList<Integer> sortedtemp = new ArrayList<Integer>();
		ArrayList<Integer> sortedtemp2 = new ArrayList<Integer>();

		temp = newsolution.get(ii);
		temp2 = newsolution.get(jj);
		sortedtemp = sort(temp);
		// System.out.println("s�ralanm�� olarak1 :" + sortedtemp);
		sortedtemp2 = sort(temp2);
		// System.out.println("s�ralanm�� olarak2 :" + sortedtemp2);
		int whichone = chooseOne(sortedtemp);
		int whichone2 = chooseOne(sortedtemp2);
		int temptemp = sortedtemp.get(whichone);
		sortedtemp.set(whichone, sortedtemp2.get(whichone2));
		sortedtemp2.set(whichone2, temptemp);
		return newsolution;

	}

	public double acceptanceProbability(double oldUtility, double newUtility, double Temperature) {

		double accepPro = 0;
		// accepPro = Math.exp((newUtility - oldUtility) / Temperature);
		accepPro = Math.pow(Math.E, ((newUtility - oldUtility) / Temperature));
		// System.out.println(accepPro + "-" + newUtility + "-" + oldUtility +
		// "-" + Temperature);
		return accepPro;

	}

	public int chooseOne(ArrayList<Integer> solution) {

		int ChangeValue = 0;
		int size = solution.size();
		int finalcutsize = ((int) (size * sonkacDikkatY�zde));
		if (cag�r�lmaY�zde > Math.random()) {
			return ChangeValue = (int) (size - finalcutsize + (Math.random() * finalcutsize));
		}
		return ChangeValue = (int) ((size - finalcutsize) * Math.random());
	}

	public ArrayList<Integer> sort(ArrayList<Integer> solution) throws IOException {
		// double utilityValue[][] = getUtilityValue(filename, malnoo);
		ArrayList<Integer> toplam = new ArrayList<Integer>();
		int total = 0;
		for (int i = 0; i < solution.size(); i++) {
			for (int j = 0; j < solution.size(); j++) {
				total = total + Matrix[solution.get(i)][solution.get(j)];
			}
			toplam.add(total);
			total = 0;
		}

		boolean swapped;

		for (int i = toplam.size() - 1; i > 0; i--) {
			swapped = false;
			for (int j = toplam.size() - 1; j > 0; j--)
				if (toplam.get(j) > toplam.get(j - 1)) {
					int temp = toplam.get(j - 1);
					toplam.set(j - 1, toplam.get(j));
					toplam.set(j, temp);
					int temp2 = solution.get(j - 1);
					solution.set(j - 1, solution.get(j));
					solution.set(j, temp2);
					swapped = true;
				}

			if (!swapped)
				break;
		}
		return solution;
	}

}
