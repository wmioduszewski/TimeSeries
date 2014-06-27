package pl.poznan.put.TimeSeries.Constants;

import java.util.Arrays;
import java.util.List;

public final class Limits {

	public static final List<AgeLimit> AgeLimits = Arrays.asList(
			new AgeLimit(0, 29), 
			new AgeLimit(30, 39), 
			new AgeLimit(40, 49), 
			new AgeLimit(50, 199));

	public static final List<TimeLimit> TimeLimits = Arrays.asList(
			new TimeLimit(TimeLimit.Asleep, -3, TimeLimit.Asleep, 0),
			new TimeLimit(TimeLimit.Asleep, 0, TimeLimit.Asleep, 3),
			new TimeLimit(TimeLimit.Asleep, 3, TimeLimit.Awake, 0),
			new TimeLimit(TimeLimit.Awake, 0, TimeLimit.Awake, 5));

}
