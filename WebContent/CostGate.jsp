<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<!-- This page displays the form and instructions for how to use the tool.
	The form is displayed here and it accepts the users input values.
	There is javascript at the bottom that is responsible for the page validation -->

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel= "stylesheet" type="text/css" href="CostGate.css"/>
<title>Cost Gate</title>
</head>
<!-- Form input -->
<body>
<p>Cost Gate Analysis Tool</p>

<!-- Include paragraph of what the cost gate calculator does and how to insert valid input -->
<!-- 
<div class="inputDescription">

<p>This cost gate analysis tool is for calculating the dollar amount for estimated losses for the remaining years of the system and 
estimated cost of rework.</p>
</div>
 --> 
<div class="form-box">
	<form name="newform" action="/TestOpsWebTool/CostGateServlet" onsubmit="return validateForm()" method="get">
		<input type="hidden" name="send" value="values">
		365 Day Performance Ratio <input type="text" name="365DayPerformanceRatio"> <br>
		Performance Guarantee <input type="text" name="performanceGuarantee"> <br>
		kWh Rate <input type="text" name="kwhRate"> <br>
		Degradation Rate <input type="text" name="degradationRate"> <br>
		Current Lease Year <input type="text" name="currentLeaseYear"> <br>
		Rework - cost Per Watt <input type="text" name="costPerWatt"> <br>
		System Size <input type="text" name="systemSize"> <br>
		<input type="submit" value="submit">
	</form>


	<!-- Calculation output -->	 
 	
<div class="estimatedCosts">	
 	Total Estimated Losses: <fmt:formatNumber value="${estimatedLosses}" type="currency"/> <br>
	Estimated Cost of Rework: <fmt:formatNumber value="${estimatedCost}" type="currency"/> <br>
</div>

	<table>
	<tr><th>kWh Produced</th><th>kWh Lost</th></tr>
	<c:forEach items="${objectList}" var="myObjectList">
	<tr><td><fmt:formatNumber type="number" maxFractionDigits="2">${myObjectList.productionValue}</fmt:formatNumber></td>
	<td><fmt:formatNumber type="number" maxFractionDigits="2">${myObjectList.productionLostValue}</fmt:formatNumber></td></tr>
	</c:forEach>
	</table>
</div>	
<!-- Javascript functions client side validation -->
	
	<script>
		function validateForm() {
			var x = document.forms["newform"]["365DayPerformanceRatio"].value;
			if (x == "") {
				alert("365 Day Performance Ratio must be filled out and must be a in decimal format");
				return false;
			}
			var y = document.forms["newform"]["performanceGuarantee"].value;
			if (y == "") {
				alert("Performance Guarantee must be filled out");
				return false;
			}
			var z = document.forms["newform"]["kwhRate"].value;
			if (z == "") {
				alert("Kwh Rate must be filled out");
				return false;
			}
			var a = document.forms["newform"]["degradationRate"].value;
			if (a == "") {
				alert("Degradation Rate must be filled out");
				return false;
			}
			var b = document.forms["newform"]["currentLeaseYear"].value;
			if (b == "") {
				alert("Current Lease Year must be filled out");
				return false;
			}
			var c = document.forms["newform"]["costPerWatt"].value;
			if (c == "") {
				alert("Cost Per Watt must be filled out");
				return false;
			}
			var d = document.forms["newform"]["systemSize"].value;
			if (d == "") {
				alert("System size must be filled out");
				return false;
			}
		} 	
	</script>
	
</body>
</html>