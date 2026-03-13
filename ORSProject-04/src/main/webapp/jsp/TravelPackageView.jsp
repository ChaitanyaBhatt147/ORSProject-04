<%@page import="in.co.rays.proj4.controller.ORSView"%>
<%@page import="in.co.rays.proj4.bean.TravelPackageBean"%>
<%@page import="in.co.rays.proj4.controller.TravelPackageCtl"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>
<%@page import="java.util.HashMap"%>
<%@page import="in.co.rays.proj4.util.HTMLUtility"%>

<html>
<head>
<title>Add Travel Package</title>

<link rel="icon" type="image/png"
	href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16x16" />
</head>

<body>

	<form action="<%=ORSView.TRAVEL_PACKAGE_CTL%>" method="POST">

		<%@ include file="Header.jsp"%>

		<jsp:useBean id="bean" class="in.co.rays.proj4.bean.TravelPackageBean"
			scope="request"></jsp:useBean>

		<div align="center">

			<h1 align="center" style="margin-bottom: -15; color: navy">

				<%
					if (bean != null && bean.getId() > 0) {
				%>
				Update Travel Package
				<%
					} else {
				%>
				Add Travel Package
				<%
					}
				%>

			</h1>

			<div style="height: 15px; margin-bottom: 12px">
				<h3 align="center">
					<font color="green"> <%=ServletUtility.getSuccessMessage(request)%>
					</font>
				</h3>

				<h3 align="center">
					<font color="red"> <%=ServletUtility.getErrorMessage(request)%>
					</font>
				</h3>
			</div>

			<input type="hidden" name="id" value="<%=bean.getId()%>"> <input
				type="hidden" name="createdBy" value="<%=bean.getCreatedBy()%>">
			<input type="hidden" name="modifiedBy"
				value="<%=bean.getModifiedBy()%>"> <input type="hidden"
				name="createdDatetime"
				value="<%=DataUtility.getTimestamp(bean.getCreatedDatetime())%>">

			<input type="hidden" name="modifiedDatetime"
				value="<%=DataUtility.getTimestamp(bean.getModifiedDatetime())%>">


			<table>

				<tr>
					<th align="left">Traveler Name<span style="color: red">*</span></th>
					<td><input type="text" name="travelerName"
						placeholder="Enter Traveler Name"
						value="<%=DataUtility.getStringData(bean.getTravelerName())%>">
					</td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("travelerName", request)%>
					</font></td>
				</tr>

				<tr>
					<th align="left">Destination<span style="color: red">*</span></th>
					<td><input type="text" name="destination"
						placeholder="Enter Destination"
						value="<%=DataUtility.getStringData(bean.getDestination())%>">
					</td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("destination", request)%>
					</font></td>
				</tr>

				<tr>
					<th align="left">Package Type<span style="color: red">*</span></th>
					<td>
						<%
							HashMap<String, String> packageTypeMap = new HashMap<>();
						packageTypeMap.put("Standard", "Standard");
						packageTypeMap.put("Premium", "Premium");

							String packageTypeHtmlList = HTMLUtility.getList("packageType", bean.getBookingStatus(), packageTypeMap);
						%> <%=packageTypeHtmlList%>

					</td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("packageType", request)%>
					</font></td>
				</tr>

				<tr>
					<th align="left">Travel Date<span style="color: red">*</span></th>
					<td><input type="text" id="udatee" name="travelDate"
						placeholder="Select Travel Date"
						value="<%=DataUtility.getDateString(bean.getTravelDate())%>">
					</td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("travelDate", request)%>
					</font></td>
				</tr>

				<tr>
					<th align="left">Return Date<span style="color: red">*</span></th>
					<td><input type="text" id="udatee" name="returnDate"
						placeholder="Select Return Date"
						value="<%=DataUtility.getDateString(bean.getReturnDate())%>">
					</td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("returnDate", request)%>
					</font></td>
				</tr>

				<tr>
					<th align="left">Package Cost<span style="color: red">*</span></th>
					<td><input type="text" name="packageCost"
						placeholder="Enter Cost"
						value="<%=DataUtility.getStringData(bean.getPackageCost())%>">
					</td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("packageCost", request)%>
					</font></td>
				</tr>

				<tr>
					<th align="left">Booking Status<span style="color: red">*</span></th>
					<td>
						<%
							HashMap<String, String> bookingStatusMap = new HashMap<>();
						bookingStatusMap.put("Booked", "Booked");
						bookingStatusMap.put("Pending", "Pending");
						bookingStatusMap.put("Cancelled", "Cancelled");

							String bookingStatusHtmlList = HTMLUtility.getList("bookingStatus", bean.getBookingStatus(), bookingStatusMap);
						%> <%=bookingStatusHtmlList%>

					</td>

					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("bookingStatus", request)%>
					</font></td>
				</tr>

				<tr>
					<th></th>
					<td></td>
				</tr>

				<tr>
					<th></th>

					<%
						if (bean != null && bean.getId() > 0) {
					%>

					<td align="left" colspan="2"><input type="submit"
						name="operation" value="<%=TravelPackageCtl.OP_UPDATE%>">

						<input type="submit" name="operation"
						value="<%=TravelPackageCtl.OP_CANCEL%>"></td>

					<%
						} else {
					%>

					<td align="left" colspan="2"><input type="submit"
						name="operation" value="<%=TravelPackageCtl.OP_SAVE%>"> <input
						type="submit" name="operation"
						value="<%=TravelPackageCtl.OP_RESET%>"></td>

					<%
						}
					%>

				</tr>

			</table>

		</div>

	</form>

	<%@ include file="Footer.jsp"%>

</body>
</html>