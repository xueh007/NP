package ime.contrib.np.core;

import ime.contrib.np.model.Solution;

import java.util.ArrayList;
import java.util.List;

public class NPEngineBiMinEnergyMinMakeSpan extends AbstractNPEngine implements INPEngine {

	@Override
	protected Solution findBestSolution(List<Solution> solutions) {
		Solution bestSolution = null;
		
		double maxEnergy = this.getMaxEnergy(solutions);
		double minEnergy = this.getMinEnergy(solutions);
		double maxMakespan = this.getMaxMakespan(solutions);
		double minMakespan = this.getMinMakespan(solutions);
		
		for (Solution s : solutions) {
			double bestObj = -1;
			double currentObj = calcuateObj(s, maxEnergy, minEnergy, maxMakespan, minMakespan);

			if (bestObj == -1 || bestObj > currentObj) {
				bestObj = currentObj;
				bestSolution = s;
			} 
		}
		return bestSolution;
	}
	
	private double getMaxEnergy(List<Solution> solutions) {
		double maxEnergy = -1;
		for (Solution s : solutions) {
			if (maxEnergy == -1 || maxEnergy < s.calTotalEnergy()) {
				maxEnergy = s.calTotalEnergy();
			}
		}
		return maxEnergy;
	}
	
	private double getMinEnergy(List<Solution> solutions) {
		double minEnergy = -1;
		for (Solution s : solutions) {
			if (minEnergy == -1 || minEnergy > s.calTotalEnergy()) {
				minEnergy = s.calTotalEnergy();
			}
		}
		return minEnergy;
	}
	
	private double getMaxMakespan(List<Solution> solutions) {
		double maxMakespan = -1;
		for (Solution s : solutions) {
			if (maxMakespan == -1 || maxMakespan < s.calMakeSpanTime()) {
				maxMakespan = s.calMakeSpanTime();
			}
		}
		return maxMakespan;
	}
	
	private double getMinMakespan(List<Solution> solutions) {
		double minMakespan = -1;
		for (Solution s : solutions) {
			if (minMakespan == -1 || minMakespan > s.calMakeSpanTime()) {
				minMakespan = s.calMakeSpanTime();
			}
		}
		return minMakespan;
	}
	
	private double calcuateObj(Solution s, double maxEnergy, double minEnergy, double maxMakespan, double minMakespan) {
		return ((s.calTotalEnergy() - minEnergy) / (maxEnergy - minEnergy)) + ((s.calMakeSpanTime() - minMakespan) / (maxMakespan - minMakespan)); 
	}
}
