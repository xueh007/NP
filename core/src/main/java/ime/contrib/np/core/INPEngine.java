package ime.contrib.np.core;

import ime.contrib.np.model.SchedulerRequest;
import ime.contrib.np.model.SchedulerResponse;

public interface INPEngine {
    SchedulerResponse run(SchedulerRequest request);
}
