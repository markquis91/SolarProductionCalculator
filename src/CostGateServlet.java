
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This servlet acts as the controller for the Cost Gate tool. 
 * This servlet accepts input from the cost gate jsp and 
 * performs calculations on this data. When the data is accepted from the client
 * it is validated here in the controller. Once the calculations are executed they are sent back to the jsp/client.
 */
@WebServlet("/CostGateServlet")
public class CostGateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CostGateServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String url = "/CostGate.jsp";
			
		String actionParameter = request.getParameter("send");
		
		if (actionParameter != null && actionParameter.equals("values")) {
		
		CostGateEngine engine = new CostGateEngine();
		
		//add server side valadation, null check, date type check, etc..
		//todo (important): Handle the following cases: 1) First time page load 2) Page load with parameters defined (successful), 3) Page load with parameters defined (failure of some sort)
		
		//if user input is not null && is not less than 0 && is a numeric
		//remove spaces
		
		String performanceRatioRaw = request.getParameter("365DayPerformanceRatio");
		double performanceRatio = Double.parseDouble(performanceRatioRaw);
		String currentLeaseYearsRaw = request.getParameter("currentLeaseYear");
		double currentLeaseYear = Double.parseDouble(currentLeaseYearsRaw);
		String performanceGuaranteeRaw = request.getParameter("performanceGuarantee");
		double performanceGuaranteeValue = Integer.parseInt(performanceGuaranteeRaw);
		String kwhRateRaw = request.getParameter("kwhRate");
		double kwhRate = Double.parseDouble(kwhRateRaw);
		String degradationRateRaw = request.getParameter("degradationRate");
		double degradationRate = Double.parseDouble(degradationRateRaw);
		String costPerWattRaw = request.getParameter("costPerWatt");
		double reworkCostPerWatt = Double.parseDouble(costPerWattRaw);
		String systemSizeRaw =  request.getParameter("systemSize");
		double systemSize = Double.parseDouble(systemSizeRaw);
		
		double estimatedCost = engine.calculateEstimatedCostOfRework(reworkCostPerWatt, systemSize);
		kwhDegradationLists kwhLists = engine.calculateKWhProductionLists(performanceGuaranteeValue, degradationRate, performanceRatio);
		
		
		List<Double> kwhLostList = kwhLists.getKwhLostList();
		List<Double> kwhProducedList = kwhLists.getKwhProductionList();
		
		double estimatedLosses = engine.calculateTotalEstimatedLosses(currentLeaseYear, kwhRate, kwhLostList);
		List<listValues> objectList = engine.createKWhObjectList(performanceGuaranteeValue, degradationRate, performanceRatio);
		
		request.setAttribute("objectList", objectList);
		request.setAttribute("estimatedLosses", estimatedLosses);
		request.setAttribute("kwhLists", kwhLists);
		request.setAttribute("kwhLostList", kwhLostList);
		request.setAttribute("kwhProducedList", kwhProducedList);
		request.setAttribute("estimatedCost", estimatedCost);
		
		}
		
		getServletContext().getRequestDispatcher(url).forward(request, response);
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
