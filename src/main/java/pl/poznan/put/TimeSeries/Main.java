package pl.poznan.put.TimeSeries;

import pl.poznan.put.TimeSeries.Workflows.EamonnRegressionWorkflow;
import pl.poznan.put.TimeSeries.Workflows.EamonnSaxWorkflow;
import pl.poznan.put.TimeSeries.Workflows.PatientSaxWorkflow;
import pl.poznan.put.TimeSeries.Workflows.WorkflowBase;

public class Main {

	static int smoothingSize = 5;
	static float omega = 0.1f;
	static int ngramSize = 1;

	public static void main(String[] args) throws Exception {

		// WorkflowBase workflow = new PatientRegressionWorkflow();
		 WorkflowBase workflow = new EamonnRegressionWorkflow();
//		WorkflowBase workflow = new PatientSaxWorkflow();
//		 WorkflowBase workflow = new EamonnSaxWorkflow();

		workflow.runWorkflow();

		System.out.println("End.");
	}

}
