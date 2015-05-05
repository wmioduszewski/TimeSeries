package pl.poznan.put.TimeSeries;

import pl.poznan.put.TimeSeries.Constants.DivisionOptions;
import pl.poznan.put.TimeSeries.Workflows.EamonnRegressionWorkflow;
import pl.poznan.put.TimeSeries.Workflows.EamonnSaxWorkflow;
import pl.poznan.put.TimeSeries.Workflows.PatientSaxWorkflow;
import pl.poznan.put.TimeSeries.Workflows.WorkflowBase;

public class Main {

		public static void main(String[] args) throws Exception {

		DivisionOptions divisionOption = DivisionOptions.Regular; 
		
//		 WorkflowBase workflow = new PatientRegressionWorkflow(divisionOption,false);
//		 WorkflowBase workflow = new EamonnRegressionWorkflow(divisionOption,false);
//		WorkflowBase workflow = new PatientSaxWorkflow(divisionOption,false);
		 WorkflowBase workflow = new EamonnSaxWorkflow(divisionOption,false);

		workflow.runWorkflow();
		
		System.out.println("End.");
	}

}
