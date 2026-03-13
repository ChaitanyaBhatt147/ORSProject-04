<%@page import="in.co.rays.proj4.controller.ORSView"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.controller.TravelPackageListCtl"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>
<%@page import="in.co.rays.proj4.bean.TravelPackageBean"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>

<html>
<head>
<title>Travel Package List</title>

<link rel="icon" type="image/png"
	href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16x16" />
</head>

<body>

	<%@include file="Header.jsp"%>

	<div align="center">

		<h1 align="center" style="margin-bottom: -15; color: navy;">
			Travel Package List</h1>

		<div style="height: 15px; margin-bottom: 12px">
			<h3>
				<font color="red"><%=ServletUtility.getErrorMessage(request)%></font>
			</h3>
			<h3>
				<font color="green"><%=ServletUtility.getSuccessMessage(request)%></font>
			</h3>
		</div>

		<form action="<%=ORSView.TRAVEL_PACKAGE_LIST_CTL%>" method="post">

			<%
				int pageNo = ServletUtility.getPageNo(request);
				int pageSize = ServletUtility.getPageSize(request);
				int index = ((pageNo - 1) * pageSize) + 1;
				int nextPageSize = DataUtility.getInt(request.getAttribute("nextListSize").toString());

				@SuppressWarnings("unchecked")
				List<TravelPackageBean> list = (List<TravelPackageBean>) ServletUtility.getList(request);

				Iterator<TravelPackageBean> it = list.iterator();

				if (list.size() != 0) {
			%>

			<input type="hidden" name="pageNo" value="<%=pageNo%>"> <input
				type="hidden" name="pageSize" value="<%=pageSize%>">

			<!-- ================= SEARCH PANEL ================= -->

			<table style="width: 100%">
				<tr>
					<td align="center"><label><b>Traveler Name :</b></label> <input
						type="text" name="travelerName" placeholder="Enter Traveler Name"
						value="<%=ServletUtility.getParameter("travelerName", request)%>">

						&emsp; <label><b>Destination :</b></label> <input type="text"
						name="destination" placeholder="Enter Destination"
						value="<%=ServletUtility.getParameter("destination", request)%>">

						&emsp; <input type="submit" name="operation"
						value="<%=TravelPackageListCtl.OP_SEARCH%>"> <input
						type="submit" name="operation"
						value="<%=TravelPackageListCtl.OP_RESET%>"></td>
				</tr>
			</table>

			<br>

			<!-- ================= TABLE ================= -->

			<table border="1" style="width: 100%; border: groove;">

				<tr style="background-color: #e1e6f1e3;">
					<th width="5%"><input type="checkbox" id="selectall" /></th>
					<th width="5%">S.No</th>
					<th width="15%">Traveler Name</th>
					<th width="15%">Destination</th>
					<th width="12%">Package Type</th>
					<th width="12%">Travel Date</th>
					<th width="12%">Return Date</th>
					<th width="10%">Cost</th>
					<th width="10%">Status</th>
					<th width="5%">Edit</th>
				</tr>

				<%
					while (it.hasNext()) {

							TravelPackageBean bean = it.next();

							SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
							String tDate = sdf.format(bean.getTravelDate());
							String rDate = sdf.format(bean.getReturnDate());
				%>

				<tr>

					<td align="center"><input type="checkbox" class="case"
						name="ids" value="<%=bean.getId()%>"></td>

					<td align="center"><%=index++%></td>

					<td align="center"><%=bean.getTravelerName()%></td>
					<td align="center"><%=bean.getDestination()%></td>
					<td align="center"><%=bean.getPackageType()%></td>
					<td align="center"><%=tDate%></td>
					<td align="center"><%=rDate%></td>
					<td align="center"><%=bean.getPackageCost()%></td>
					<td align="center"><%=bean.getBookingStatus()%></td>

					<td align="center"><a
						href="<%=ORSView.TRAVEL_PACKAGE_CTL%>?id=<%=bean.getId()%>">
							Edit </a></td>

				</tr>

				<%
					}
				%>

			</table>

			<!-- ================= PAGINATION ================= -->

			<table style="width: 100%">

				<tr>

					<td style="width: 25%"><input type="submit" name="operation"
						value="<%=TravelPackageListCtl.OP_PREVIOUS%>"
						<%=pageNo > 1 ? "" : "disabled"%>></td>

					<td align="center" style="width: 25%"><input type="submit"
						name="operation" value="<%=TravelPackageListCtl.OP_NEW%>">
					</td>

					<td align="center" style="width: 25%"><input type="submit"
						name="operation" value="<%=TravelPackageListCtl.OP_DELETE%>">
					</td>

					<td align="right" style="width: 25%"><input type="submit"
						name="operation" value="<%=TravelPackageListCtl.OP_NEXT%>"
						<%=(nextPageSize != 0) ? "" : "disabled"%>></td>

				</tr>

			</table>

			<%
				}
			%>

			<%
				if (list.size() == 0) {
			%>

			<table>
				<tr>
					<td align="right"><input type="submit" name="operation"
						value="<%=TravelPackageListCtl.OP_BACK%>"></td>
				</tr>
			</table>

			<%
				}
			%>

		</form>

	</div>

	<%@ include file="Footer.jsp"%>

</body>
</html>