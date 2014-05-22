package pl.poznan.put.TimeSeries.Reporting;

import java.io.File;
import java.io.IOException;
import java.util.List;

import pl.poznan.put.TimeSeries.Util.Configuration;
import jxl.LabelCell;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

public class ResultReporter {
	
	private String path; 
	private WritableWorkbook workbook;
	private WritableSheet currentSheet;
	private EntireReport report;
	
	public ResultReporter(EntireReport report) {
		this.path = Configuration.getProperty("xlsReportPath");
		this.report = report;
	}
	
	public void constructReport() throws Exception{
		getWorkbook();
		createSheet();
		writeReportContent();
		endReporting();
	}

	private void getWorkbook() throws Exception{
		File xlsFile = new File(path);
		if(xlsFile.exists()){
			//workbook = Workbook.getWorkbook(xlsFile);			
		}
		else{
			workbook = Workbook.createWorkbook(xlsFile);
		}
	}
	
	private void createSheet(){
		int alph = report.getAlphabeatUsed();
		int outp = report.getOutputLengthUsed();
		int attrLen = report.getAttributeLengthUsed();
		String sheetName = String.format("alph=%d outp=%d attrLen=%d", alph,outp,attrLen);
		currentSheet = workbook.createSheet(sheetName, workbook.getNumberOfSheets());
	}
	
	private void writeReportContent() throws Exception{
		List<String> classifiers= report.getClassifiersNames();
		List<ReportRecord> records = report.getRecords(); 
		for(int i=0;i<classifiers.size();i++){						
			String fullName =classifiers.get(i);
			String name = fullName.substring(fullName.lastIndexOf(".")+1, fullName.length());
			currentSheet.addCell(new Label(i+1,0,name));
		}
		
		for(int j=0;j<records.size();j++){
			currentSheet.addCell(new Label(0,j+1,records.get(j).getDatasetName()));
		}
		
		WritableCellFormat wcf1=new WritableCellFormat(new  jxl.write.NumberFormats().FLOAT);
		
		for(int i=0;i<classifiers.size();i++){						
			for(int j=0;j<records.size();j++){
				
				ReportRecord record = records.get(j);
				List<Double> accuracies = record.getAccuracies();
				currentSheet.addCell(new jxl.write.Number(i+1,j+1,accuracies.get(i),wcf1));
			}
		}
	}
	
	private void endReporting() throws Exception{
		workbook.write();
		workbook.close();
	}
}
