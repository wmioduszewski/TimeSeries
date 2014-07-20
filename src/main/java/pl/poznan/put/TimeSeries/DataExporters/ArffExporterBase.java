package pl.poznan.put.TimeSeries.DataExporters;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

public abstract class ArffExporterBase {

	protected StringBuilder arffFileContent;
	protected String relationTitle;
	protected Map<String, String> attributes;

	protected abstract void assignAttributes();

	protected abstract void insertData();

	public ArffExporterBase(String relationTitle) {
		super();
		this.relationTitle = relationTitle;
	}

	protected void performExport(String destinationPath)
			throws FileNotFoundException {
		arffFileContent = new StringBuilder();
		attributes = new LinkedHashMap<String, String>();
		buildFileContent();
		PrintWriter writer = new PrintWriter(new File(destinationPath));
		writer.write(arffFileContent.toString());
		writer.flush();
		writer.close();
	}

	protected void buildFileContent() {
		assignAttributes();
		insertRelationName();
		insertAttributesNames();
		insertData();
	}

	protected void insertRelationName() {
		arffFileContent.append("@RELATION " + relationTitle + "\n");
	}

	protected void insertAttributesNames() {
		for (Entry<String, String> attr : attributes.entrySet()) {
			arffFileContent.append("@ATTRIBUTE " + attr.getKey() + " "
					+ attr.getValue() + "\n");
		}
	}
}
