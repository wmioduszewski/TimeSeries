package pl.poznan.put.TimeSeries;

import pl.poznan.put.TimeSeries.Constants.DivisionOptions;
import pl.poznan.put.TimeSeries.Workflows.EamonnRegressionWorkflow;
import pl.poznan.put.TimeSeries.Workflows.EamonnSaxWorkflow;
import pl.poznan.put.TimeSeries.Workflows.PatientRegressionWorkflow;
import pl.poznan.put.TimeSeries.Workflows.PatientSaxWorkflow;
import pl.poznan.put.TimeSeries.Workflows.PatientSaxWorkflowTest;
import pl.poznan.put.TimeSeries.Workflows.WorkflowBase;

public class Main {

		public static void main(String[] args) throws Exception {

		DivisionOptions divisionOption = DivisionOptions.PeriodicThenRegular; 
		
//		 WorkflowBase workflow = new PatientRegressionWorkflow(divisionOption,true);
//		 WorkflowBase workflow = new EamonnRegressionWorkflow(divisionOption,false);
//		WorkflowBase workflow = new PatientSaxWorkflow(divisionOption,true);
		 WorkflowBase workflow = new EamonnSaxWorkflow(divisionOption,false);

		workflow.runWorkflow();

		System.out.println("End.");
	}

}
