package ime.contrib.np.core;

import java.util.List;

import ime.contrib.np.model.Solution;

public class NPEngineMinEnergy extends AbstractNPEngine implements INPEngine {

    @Override
   	protected Solution findBestSolution(List<Solution> solutions) {
   		Solution bestSolution = null;

   		for (Solution s : solutions) {
   			if (bestSolution == null || bestSolution.calEnergyConsumption() > s.calEnergyConsumption()) {
   				bestSolution = s;
   			}
   		}
   		return bestSolution;
   	}
}
