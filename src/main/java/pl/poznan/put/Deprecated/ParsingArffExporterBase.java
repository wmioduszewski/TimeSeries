package pl.poznan.put.Deprecated;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

@Deprecated
public abstract class ParsingArffExporterBase {

	protected StringBuilder arffFileContent;
	protected String relationTitle;
	protected Map<String, String> attributes;

	protected abstract void assignAttributes();

	protected abstract void insertData();

	public ParsingArffExporterBase(String relationTitle) {
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
		System.out.println(String.format(
				"Exported arff file for Relation %s to %s", relationTitle,
				destinationPath));
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
