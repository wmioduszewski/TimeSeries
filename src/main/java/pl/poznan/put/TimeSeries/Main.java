package pl.poznan.put.TimeSeries;

import pl.poznan.put.TimeSeries.Constants.DivisionOptions;
import pl.poznan.put.TimeSeries.Workflows.EamonnRegressionWorkflow;
import pl.poznan.put.TimeSeries.Workflows.EamonnSaxWorkflow;
import pl.poznan.put.TimeSeries.Workflows.PatientRegressionWorkflow;
import pl.poznan.put.TimeSeries.Workflows.PatientSaxWorkflow;
import pl.poznan.put.TimeSeries.Workflows.WorkflowBase;

public class Main {

	static int smoothingSize = 5;
	static float omega = 0.1f;
	static int ngramSize = 1;

	public static void main(String[] args) throws Exception {

		DivisionOptions divisionOption = DivisionOptions.PeriodicThenRegular; 
		
		 WorkflowBase workflow = new PatientRegressionWorkflow(divisionOption);
//		 WorkflowBase workflow = new EamonnRegressionWorkflow(divisionOption);
//		WorkflowBase workflow = new PatientSaxWorkflow(divisionOption);
//		 WorkflowBase workflow = new EamonnSaxWorkflow(divisionOption);

		workflow.runWorkflow();

		System.out.println("End.");
	}

}
