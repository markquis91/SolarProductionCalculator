
public class listValues {
	
	private double productionValue;
	private double productionLostValue;
	
	public listValues(double prodVal, double prodLost) {
		productionValue = prodVal;
		productionLostValue = prodLost;
	}
	
	public double getProductionValue() {
		return productionValue;
	}
	
	public double getProductionLostValue() {
		return productionLostValue;
	}
}
