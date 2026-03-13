<%@page import="in.co.rays.proj4.controller.ORSView"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.controller.TrainingListCtl"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>
<%@page import="in.co.rays.proj4.bean.TrainingBean"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>

<html>
<head>
<title>Training List</title>
<link rel="icon" type="image/png"
	href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16x16" />
</head>

<body>
<%@include file="Header.jsp"%>

<div align="center">

	<h1 align="center" style="margin-bottom: -15; color: navy;">
		Training List
	</h1>

	<div style="height: 15px; margin-bottom: 12px">
		<h3><font color="red">
			<%=ServletUtility.getErrorMessage(request)%>
		</font></h3>
		<h3><font color="green">
			<%=ServletUtility.getSuccessMessage(request)%>
		</font></h3>
	</div>

	<form action="<%=ORSView.TRAINING_LIST_CTL%>" method="post">

	<%
		int pageNo = ServletUtility.getPageNo(request);
		int pageSize = ServletUtility.getPageSize(request);
		int index = ((pageNo - 1) * pageSize) + 1;
		int nextPageSize = DataUtility.getInt(request.getAttribute("nextListSize").toString());

		@SuppressWarnings("unchecked")
		List<TrainingBean> list = (List<TrainingBean>) ServletUtility.getList(request);
		Iterator<TrainingBean> it = list.iterator();
	%>

	<input type="hidden" name="pageNo" value="<%=pageNo%>">
	<input type="hidden" name="pageSize" value="<%=pageSize%>">

	<!-- Search Section -->
	<table style="width: 100%">
		<tr>
			<td align="center">
				<label><b>Training Code :</b></label>
				<input type="text" name="trainingCode"
					value="<%=ServletUtility.getParameter("trainingCode", request)%>"
					placeholder="Enter Training Code">&emsp;

				<label><b>Training Name :</b></label>
				<input type="text" name="trainingName"
					value="<%=ServletUtility.getParameter("trainingName", request)%>"
					placeholder="Enter Training Name">&emsp;

				<label><b>Trainer Name :</b></label>
				<input type="text" name="trainerName"
					value="<%=ServletUtility.getParameter("trainerName", request)%>"
					placeholder="Enter Trainer Name">&emsp;

				<input type="submit" name="operation"
					value="<%=TrainingListCtl.OP_SEARCH%>">
				<input type="submit" name="operation"
					value="<%=TrainingListCtl.OP_RESET%>">
			</td>
		</tr>
	</table>

	<br>

	<%
		if (list != null && list.size() > 0) {
	%>

	<table border="1" style="width: 100%; border: groove;">
		<tr style="background-color: #e1e6f1e3;">
			<th width="5%"><input type="checkbox" id="selectall" /></th>
			<th width="5%">S.No</th>
			<th width="15%">Training Code</th>
			<th width="25%">Training Name</th>
			<th width="20%">Trainer Name</th>
			<th width="15%">Training Date</th>
			<th width="10%">Edit</th>
		</tr>

		<%
			while (it.hasNext()) {
				TrainingBean bean = it.next();
				SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
				String date = sdf.format(bean.getTrainingDate());
		%>

		<tr>
			<td style="text-align: center;">
				<input type="checkbox" class="case"
					name="ids" value="<%=bean.getId()%>">
			</td>
			<td style="text-align: center;"><%=index++%></td>
			<td style="text-align: center;"><%=bean.getTrainingCode()%></td>
			<td style="text-align: center; text-transform: capitalize;">
				<%=bean.getTrainingName()%>
			</td>
			<td style="text-align: center; text-transform: capitalize;">
				<%=bean.getTrainerName()%>
			</td>
			<td style="text-align: center;"><%=date%></td>
			<td style="text-align: center;">
				<a href="<%=ORSView.TRAINING_CTL%>?id=<%=bean.getId()%>">Edit</a>
			</td>
		</tr>

		<%
			}
		%>

	</table>

	<br>

	<table style="width: 100%">
		<tr>
			<td style="width: 25%">
				<input type="submit" name="operation"
					value="<%=TrainingListCtl.OP_PREVIOUS%>"
					<%=pageNo > 1 ? "" : "disabled"%>>
			</td>

			<td align="center" style="width: 25%">
				<input type="submit" name="operation"
					value="<%=TrainingListCtl.OP_NEW%>">
			</td>

			<td align="center" style="width: 25%">
				<input type="submit" name="operation"
					value="<%=TrainingListCtl.OP_DELETE%>">
			</td>

			<td align="right" style="width: 25%">
				<input type="submit" name="operation"
					value="<%=TrainingListCtl.OP_NEXT%>"
					<%=(nextPageSize != 0) ? "" : "disabled"%>>
			</td>
		</tr>
	</table>

	<%
		} else {
	%>
	<table>
		<tr>
			<td>
				<input type="submit" name="operation"
					value="<%=TrainingListCtl.OP_BACK%>">
			</td>
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