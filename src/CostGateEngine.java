
import java.util.ArrayList;
import java.util.List;

/***
 *  This class holds all the calculations used for the Cost Gate Calculator, these calculations are used in the Cost Gate Servlet.
 *  These calculations are all executed in doubles. This ensures the most accurate number will be calculated.
 * @author Markquis Simmons
 *
 */
public class CostGateEngine {

	private static final double DEFAULT_LEASE_LENGTH_YEARS = 20.0;
	
	//--This function calculates an estimate for the cost of rework on a solar system
	
	public double calculateEstimatedCostOfRework(double reworkCost, double systemSizeKwh) {
		double estimatedCost = reworkCost * systemSizeKwh * 1000;
		return estimatedCost;
	}
	
	//--This function calculates the Kwh production lists. The first list is the expected production "as-built", the second list is the "kWhs lost" due to under production and degradation
	
	public kwhDegradationLists calculateKWhProductionLists(double YearOneExpectedPeGu, double degradationRate,
			double expectedPerformance) {
		List<Double> kwhProductionList = new ArrayList<>();
		List<Double> kwhLostList = new ArrayList<>();
		kwhDegradationLists degradationLists = null;

		double degradationRateDecimal = 1.0 - degradationRate;			
		double expectedPerformancePercent = 1.0 - expectedPerformance;
		kwhProductionList.add(YearOneExpectedPeGu);
		double firstYearKwhLostRaw = (YearOneExpectedPeGu * expectedPerformancePercent);
		kwhLostList.add(firstYearKwhLostRaw);
		
		for (int i = 0; i < DEFAULT_LEASE_LENGTH_YEARS; i++) {
			double followingYearExpectedPeGu = (YearOneExpectedPeGu * degradationRateDecimal);
			kwhProductionList.add(followingYearExpectedPeGu);
			
			double followingYearKwhLost = (followingYearExpectedPeGu * expectedPerformancePercent);
			kwhLostList.add(followingYearKwhLost);

			YearOneExpectedPeGu = followingYearExpectedPeGu;
			
		}
		degradationLists = new kwhDegradationLists(kwhProductionList, kwhLostList);
		return degradationLists;
	}
	
	//--This function creates an object that holds the expected PeGu value and kwh lost value and adds them to a list
	
	public List<listValues> createKWhObjectList(double YearOneExpectedPeGu, double degradationRate,
			double expectedPerformance) {
		List<listValues> kwhListValues = new ArrayList<>();
		
		
		double degradationRateDecimal = 1.0 - degradationRate;			
		double expectedPerformancePercent = 1.0 - expectedPerformance;
		double firstYearKwhLostRaw = (YearOneExpectedPeGu * expectedPerformancePercent);
		listValues firstValue = new listValues(YearOneExpectedPeGu, firstYearKwhLostRaw);
		kwhListValues.add(firstValue);
		
		for (int i = 0; i < DEFAULT_LEASE_LENGTH_YEARS; i++) {
			double followingYearExpectedPeGu = (YearOneExpectedPeGu * degradationRateDecimal);
			
			double followingYearKwhLost = (followingYearExpectedPeGu * expectedPerformancePercent);

			listValues newValue = new listValues(followingYearExpectedPeGu, followingYearKwhLost);
			kwhListValues.add(newValue);
			
			YearOneExpectedPeGu = followingYearExpectedPeGu;
		}
		return kwhListValues;
	}
	
	
	//--This function calculates the total estimated losses as currency for the remaining years in the lease
	public double calculateTotalEstimatedLosses(double operationalYearIndexValue, double kwhRate, List<Double> kwhLostList) {
		double totalRemainingLeaseYears = (DEFAULT_LEASE_LENGTH_YEARS - operationalYearIndexValue);
		double nextValue = 0;
		
		for (int i = 0; i < totalRemainingLeaseYears; i++) {
			double kwhLostValue = kwhLostList.get((int) operationalYearIndexValue);
			nextValue = kwhLostValue + nextValue;
			operationalYearIndexValue++;
		}
		
		double totalEstimatedLosses = nextValue * kwhRate;
 		return  totalEstimatedLosses;
		}
}
