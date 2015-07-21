package pl.poznan.put.TimeSeries.Reporting;

import java.io.File;
import java.util.List;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.NumberFormats;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import pl.poznan.put.TimeSeries.Util.CommonConfig;

public class ResultReporter {

	private String path;
	private WritableWorkbook workbook;
	private WritableSheet currentSheet;
	private EntireReport report;
	private String newXlsFilePath;

	public ResultReporter(EntireReport report) {
		this.path = CommonConfig.getInstance().getXlsReportPath();
		this.report = report;
	}

	public void constructReport() throws Exception {
		getWorkbook();
		createSheet();
		writeReportContent();
		endReporting();
	}

	private void getWorkbook() throws Exception {
		File xlsFile = new File(path);
		if (xlsFile.exists()) {
			newXlsFilePath = path.substring(0, path.lastIndexOf("."));
			newXlsFilePath += " temp";
			newXlsFilePath += path.substring(path.lastIndexOf("."),
					path.length());
			Workbook existingWorkbook = Workbook.getWorkbook(xlsFile);
			File newXlsFile = new File(newXlsFilePath);
			workbook = Workbook.createWorkbook(newXlsFile, existingWorkbook);
		} else {
			workbook = Workbook.createWorkbook(xlsFile);
		}
	}

	private void createSheet() {
		int alph = report.getAlphabeatUsed();
		int outp = report.getOutputLengthUsed();
		String sheetName = String.format("alph=%d outp=%d", alph,
				outp);
		currentSheet = workbook.createSheet(sheetName,
				workbook.getNumberOfSheets());
	}

	private void writeReportContent() throws Exception {
		List<String> classifiers = report.getClassifiersNames();
		List<ReportRecord> records = report.getRecords();
		for (int i = 0; i < classifiers.size(); i++) {
			String fullName = classifiers.get(i);
			String name = fullName.substring(fullName.lastIndexOf(".") + 1,
					fullName.length());
			currentSheet.addCell(new Label(i + 1, 0, name));
		}

		for (int j = 0; j < records.size(); j++) {
			currentSheet.addCell(new Label(0, j + 1, records.get(j)
					.getDatasetName()));
		}

		new jxl.write.NumberFormats();
		WritableCellFormat wcf1 = new WritableCellFormat(NumberFormats.FLOAT);

		for (int i = 0; i < classifiers.size(); i++) {
			for (int j = 0; j < records.size(); j++) {

				ReportRecord record = records.get(j);
				List<Double> accuracies = record.getAccuracies();
				currentSheet.addCell(new jxl.write.Number(i + 1, j + 1,
						accuracies.get(i), wcf1));
			}
		}
	}

	private void endReporting() throws Exception {
		workbook.write();
		workbook.close();

		if (newXlsFilePath != null) {
			File newReport = new File(newXlsFilePath);

			if (newReport.exists()) {
				File oldReport = new File(path);
				// String name = path.substring(path.lastIndexOf("/"),
				// path.length());
				oldReport.delete();
				newReport.renameTo(oldReport);
			}
		}
	}
}
