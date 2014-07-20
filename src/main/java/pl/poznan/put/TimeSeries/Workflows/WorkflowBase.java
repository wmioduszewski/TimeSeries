package pl.poznan.put.TimeSeries.Workflows;

public abstract class WorkflowBase {
	
	protected abstract void importData();
	
	protected abstract void processData();
	
	protected abstract void runExperiment();
	
	protected abstract void reportResult();
	
	public WorkflowBase() {
		super();
		// TODO Auto-generated constructor stub
	}

	public void runWorkflow()
	{
		System.out.println("Workflow has started.");
		importData();
		processData();
		runExperiment();
		reportResult();
		System.out.println("Workflow has ended.");
	}

}
