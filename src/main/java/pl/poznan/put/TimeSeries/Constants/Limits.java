package pl.poznan.put.TimeSeries.Constants;

import java.util.Arrays;
import java.util.List;

public final class Limits {
	
	public static final List<AgeLimit> AgeLimits = Arrays.asList(new AgeLimit(0, 29),
			new AgeLimit(30, 39), new AgeLimit(40, 49), new AgeLimit(50, 199));
	
	public static final List<TimeLimitPair> TimeLimits = Arrays.asList(
			new TimeLimitPair(TimeLimitPair.Asleep, -3,TimeLimitPair.Asleep,0), 
			new TimeLimitPair(TimeLimitPair.Asleep, 0,TimeLimitPair.Asleep,3),
			new TimeLimitPair(TimeLimitPair.Asleep, 3,TimeLimitPair.Awake,0),
			new TimeLimitPair(TimeLimitPair.Awake, 0,TimeLimitPair.Awake,5));
	
}
