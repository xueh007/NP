package ime.contrib.np.handler;

import ime.contrib.np.model.Solution;

public class EvaluateHandler {
    public boolean evaluate(Solution solution) {
        return this.doEvaluate(solution);
    }

    protected boolean doEvaluate(Solution solution) {
        return solution.initTimePair();
    }
}
