import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExperimentalThread implements Runnable {
	private Thread t;
	private String threadName;
	private int numTests;
	private int lengthOfData;
	private String outputFile;

	@Override
	public void run() {
		// TODO Auto-generated method stub
		System.out.println("Running " + threadName);

		Workbook wb = new XSSFWorkbook();
		Main.ls = new LoadingScreen(numTests * 6);
		Sheet sheet1 = wb.createSheet("Sheet");
		int[] data = new int[lengthOfData];

		String[] algorithmNames = { "Bubble Sort", "Insertion Sort", "Selection Sort", "Merge Sort", "Quicksort",
				"Arrays.Sort" };

		Row headerRow = sheet1.createRow(0);

		for (int i = 0; i < algorithmNames.length; i++) {
			headerRow.createCell(headerRow.getPhysicalNumberOfCells() + 1)
					.setCellValue(algorithmNames[i] + " Start Time");
			headerRow.createCell(headerRow.getPhysicalNumberOfCells() + 1)
					.setCellValue(algorithmNames[i] + " End Time");
			headerRow.createCell(headerRow.getPhysicalNumberOfCells() + 1)
					.setCellValue(algorithmNames[i] + " Time Taken");
		}

		Random rand = new Random();

		for (int i = 0; i < lengthOfData; i++) {
			data[i] = rand.nextInt(Integer.MAX_VALUE) + Integer.MIN_VALUE;
		}

		Row firstTrialRow = null;

		for (int i = 0; i <= numTests; i++) {
			Row currentRow = sheet1.createRow(i + 1);
			if (i == 0) {
				firstTrialRow = currentRow;
			}
			currentRow.createCell(currentRow.getPhysicalNumberOfCells()).setCellValue("Trial " + i);
			// Test and record data for Bubble Sort
			Long startTime = System.nanoTime();
			BubbleSort(data);
			Long endTime = System.nanoTime();
			Long timeSpent = endTime - startTime;
			currentRow.createCell(currentRow.getPhysicalNumberOfCells()).setCellValue(startTime);
			currentRow.createCell(currentRow.getPhysicalNumberOfCells()).setCellValue(endTime);
			currentRow.createCell(currentRow.getPhysicalNumberOfCells()).setCellValue(timeSpent);
			incrementBar();

			// Test and record data for Insertion Sort
			startTime = System.nanoTime();
			InsertionSort(data);
			endTime = System.nanoTime();
			timeSpent = endTime - startTime;
			currentRow.createCell(currentRow.getPhysicalNumberOfCells()).setCellValue(startTime);
			currentRow.createCell(currentRow.getPhysicalNumberOfCells()).setCellValue(endTime);
			currentRow.createCell(currentRow.getPhysicalNumberOfCells()).setCellValue(timeSpent);
			incrementBar();

			// Test and record data for Selection Sort
			startTime = System.nanoTime();
			SelectionSort(data);
			endTime = System.nanoTime();
			timeSpent = endTime - startTime;
			currentRow.createCell(currentRow.getPhysicalNumberOfCells()).setCellValue(startTime);
			currentRow.createCell(currentRow.getPhysicalNumberOfCells()).setCellValue(endTime);
			currentRow.createCell(currentRow.getPhysicalNumberOfCells()).setCellValue(timeSpent);
			incrementBar();

			// Test and record data for Merge Sort
			startTime = System.nanoTime();
			MergeSort(data);
			endTime = System.nanoTime();
			timeSpent = endTime - startTime;
			currentRow.createCell(currentRow.getPhysicalNumberOfCells()).setCellValue(startTime);
			currentRow.createCell(currentRow.getPhysicalNumberOfCells()).setCellValue(endTime);
			currentRow.createCell(currentRow.getPhysicalNumberOfCells()).setCellValue(timeSpent);
			incrementBar();

			// Test and record data for QuickSort
			startTime = System.nanoTime();
			Quicksort(data);
			endTime = System.nanoTime();
			timeSpent = endTime - startTime;
			currentRow.createCell(currentRow.getPhysicalNumberOfCells()).setCellValue(startTime);
			currentRow.createCell(currentRow.getPhysicalNumberOfCells()).setCellValue(endTime);
			currentRow.createCell(currentRow.getPhysicalNumberOfCells()).setCellValue(timeSpent);
			incrementBar();

			// Test and record data for Arrays.Sort
			startTime = System.nanoTime();
			Arrays.sort(data);
			endTime = System.nanoTime();
			timeSpent = endTime - startTime;
			currentRow.createCell(currentRow.getPhysicalNumberOfCells()).setCellValue(startTime);
			currentRow.createCell(currentRow.getPhysicalNumberOfCells()).setCellValue(endTime);
			currentRow.createCell(currentRow.getPhysicalNumberOfCells()).setCellValue(timeSpent);
			incrementBar();
		}

		if (firstTrialRow != null) {
			sheet1.removeRow(firstTrialRow);
		}

		char[] excelLetters = { 'D', 'G', 'J', 'M', 'P', 'S', 'V', 'Z' };

		Row meanRow = sheet1.createRow(sheet1.getPhysicalNumberOfRows() + 1);
		meanRow.createCell(0).setCellValue("Mean:");
		Row medianRow = sheet1.createRow(sheet1.getPhysicalNumberOfRows() + 1);
		medianRow.createCell(0).setCellValue("Median:");
		Row modeRow = sheet1.createRow(sheet1.getPhysicalNumberOfRows() + 1);
		modeRow.createCell(0).setCellValue("Mode:");

		int endRowNumber = sheet1.getPhysicalNumberOfRows() - 3;

		for (int i = 3; i < ((algorithmNames.length * 3) + 1); i += 3) {
			char letter = excelLetters[(i / 3) - 1];
			String formula;

			Cell currentMeanCell = meanRow.createCell(i);
			currentMeanCell.setCellType(CellType.FORMULA);
			formula = "AVERAGE(" + letter + 3 + ":" + letter + endRowNumber + ")";
			currentMeanCell.setCellFormula(formula);

			Cell currentMedianCell = medianRow.createCell(i);
			currentMedianCell.setCellType(CellType.FORMULA);
			formula = "MEDIAN(" + letter + 3 + ":" + letter + endRowNumber + ")";
			currentMedianCell.setCellFormula(formula);

			Cell currentModeCell = modeRow.createCell(i);
			currentModeCell.setCellType(CellType.FORMULA);
			formula = "MODE(" + letter + 3 + ":" + letter + endRowNumber + ")";
			currentModeCell.setCellFormula(formula);
		}

		for (int i = 0; i < headerRow.getPhysicalNumberOfCells(); i++) {
			sheet1.autoSizeColumn(i);
		}

		FileOutputStream fileOut;
		try {
			fileOut = new FileOutputStream(outputFile + ".xlsx");
			wb.write(fileOut);
			fileOut.close();
			wb.close();
			Main.ls.closeWinder();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("Thread " + threadName + " exiting.");
	}

	public void start() {
		System.out.println("Starting " + threadName);
		if (t == null) {
			t = new Thread(this, threadName);
			t.start();
		}
	}

	private void incrementBar() {
		Main.ls.setProgressBar(Main.ls.getProgress() + 1);
	}

	ExperimentalThread(String name, int numTests, int lengthOfData, String outputFile) {
		this.threadName = name;
		this.numTests = numTests;
		this.lengthOfData = lengthOfData;
		this.outputFile = outputFile;
		System.out.println("Creating " + threadName);
	}

	public static void BubbleSort(int[] data) {
		for (int i = data.length - 1; i >= 0; i--) {
			for (int j = 1; j <= i; j++) {
				if (data[j - 1] > data[j]) {
					int temp = data[j - 1];
					data[j - 1] = data[j];
					data[j] = temp;
				}
			}
		}
	}

	public static void InsertionSort(int[] data) {
		for (int i = 1; i < data.length; i++) {
			int index = data[i];
			int j = 1;
			while (j > 0 && data[j - 1] > index) {
				data[j] = data[j - 1];
				j--;
			}
			data[j] = index;
		}
	}

	public static void SelectionSort(int[] data) {
		for (int i = 0; i < data.length - 1; i++) {
			int min = i;
			for (int j = i + 1; j < data.length; j++) {
				if (data[j] < data[min])
					min = j;
				int temp = data[i];
				data[i] = data[min];
				data[min] = temp;
			}
		}
	}

	public int[] MergeSort(int array[]) {
		if (array.length > 1) {
			int elementsInA1 = array.length / 2;
			int elementsInA2 = elementsInA1;
			if ((array.length % 2) == 1)
				elementsInA2 += 1;
			int arr1[] = new int[elementsInA1];
			int arr2[] = new int[elementsInA2];
			for (int i = 0; i < elementsInA1; i++)
				arr1[i] = array[i];
			arr1 = MergeSort(arr1);
			arr2 = MergeSort(arr2);
			int i = 0, j = 0, k = 0;
			while (arr1.length != j && arr2.length != k) {
				if (arr1[j] < arr2[k]) {
					array[i] = arr1[j];
					i++;
					j++;
				}

				else {
					array[i] = arr2[k];
					i++;
					k++;
				}
			}

			while (arr1.length != j) {
				array[i] = arr1[j];
				i++;
				j++;
			}
			while (arr2.length != k) {
				array[i] = arr2[k];
				i++;
				k++;
			}
		}
		return array;
	}

	public static void Quicksort(int[] data) {
		IntArrayQuickSort(data, 0, data.length - 1);
	}

	public static void exchange(int[] data, int m, int n) {
		int temporary;

		temporary = data[m];
		data[m] = data[n];
		data[n] = temporary;
	}

	public static void IntArrayQuickSort(int[] data, int l, int r) {
		int i, j;
		int x;

		i = l;
		j = r;

		x = data[(l + r) / 2]; /* find pivot item */
		while (true) {
			while (data[i] < x)
				i++;
			while (x < data[j])
				j--;
			if (i <= j) {
				exchange(data, i, j);
				i++;
				j--;
			}
			if (i > j)
				break;
		}
		if (l < j)
			IntArrayQuickSort(data, l, j);
		if (i < r)
			IntArrayQuickSort(data, i, r);
	}

}