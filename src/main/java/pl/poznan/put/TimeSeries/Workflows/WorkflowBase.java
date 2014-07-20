package pl.poznan.put.TimeSeries.Workflows;

public abstract class WorkflowBase {
	
	protected abstract void importData();
	
	protected abstract void processData();
	
	protected abstract void runExperiment();
	
	protected abstract void reportResult();
	
	public void runWorkflow()
	{
		importData();
		processData();
		runExperiment();
		reportResult();
	}

}
