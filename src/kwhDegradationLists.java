import java.util.List;

public class kwhDegradationLists {

	private List<Double> actualKwhProductionList;
	private List<Double> actualKwhLostList;
	
	public kwhDegradationLists(List<Double> prodList, List<Double> lostList) {
		actualKwhProductionList = prodList;
		actualKwhLostList = lostList;
	}
	
	public List<Double> getKwhProductionList() {
		return actualKwhProductionList;
	}
	
	public List<Double> getKwhLostList() {
		return actualKwhLostList;
	}
}
