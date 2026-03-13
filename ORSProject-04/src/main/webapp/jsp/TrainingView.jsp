<%@page import="in.co.rays.proj4.controller.ORSView"%>
<%@page import="in.co.rays.proj4.controller.TrainingCtl"%>
<%@page import="in.co.rays.proj4.bean.TrainingBean"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>

<html>
<head>
<title>Add Training</title>
<link rel="icon" type="image/png"
	href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16x16" />
</head>
<body>

	<form action="<%=ORSView.TRAINING_CTL%>" method="POST">

		<%@ include file="Header.jsp"%>

		<jsp:useBean id="bean" class="in.co.rays.proj4.bean.TrainingBean"
			scope="request"></jsp:useBean>

		<div align="center">

			<h1 align="center" style="margin-bottom: -15; color: navy">
				<%
					if (bean != null && bean.getId() > 0) {
				%>
				Update
				<%
					} else {
				%>
				Add
				<%
					}
				%>
				Training
			</h1>

			<div style="height: 15px; margin-bottom: 12px">
				<h3 align="center">
					<font color="green"><%=ServletUtility.getSuccessMessage(request)%></font>
				</h3>
				<h3 align="center">
					<font color="red"><%=ServletUtility.getErrorMessage(request)%></font>
				</h3>
			</div>

			<input type="hidden" name="id" value="<%=bean.getId()%>">
			<input type="hidden" name="createdBy" value="<%=bean.getCreatedBy()%>">
			<input type="hidden" name="modifiedBy" value="<%=bean.getModifiedBy()%>">
			<input type="hidden" name="createdDatetime"
				value="<%=DataUtility.getTimestamp(bean.getCreatedDatetime())%>">
			<input type="hidden" name="modifiedDatetime"
				value="<%=DataUtility.getTimestamp(bean.getModifiedDatetime())%>">

			<table>

				<tr>
					<th align="left">Training Code<span style="color: red">*</span></th>
					<td>
						<input type="text" name="trainingCode"
							placeholder="Enter Training Code"
							value="<%=DataUtility.getStringData(bean.getTrainingCode())%>">
					</td>
					<td style="position: fixed;">
						<font color="red">
							<%=ServletUtility.getErrorMessage("trainingCode", request)%>
						</font>
					</td>
				</tr>

				<tr>
					<th align="left">Training Name<span style="color: red">*</span></th>
					<td>
						<input type="text" name="trainingName"
							placeholder="Enter Training Name"
							value="<%=DataUtility.getStringData(bean.getTrainingName())%>">
					</td>
					<td style="position: fixed;">
						<font color="red">
							<%=ServletUtility.getErrorMessage("trainingName", request)%>
						</font>
					</td>
				</tr>

				<tr>
					<th align="left">Trainer Name<span style="color: red">*</span></th>
					<td>
						<input type="text" name="trainerName"
							placeholder="Enter Trainer Name"
							value="<%=DataUtility.getStringData(bean.getTrainerName())%>">
					</td>
					<td style="position: fixed;">
						<font color="red">
							<%=ServletUtility.getErrorMessage("trainerName", request)%>
						</font>
					</td>
				</tr>

				<tr>
					<th align="left">Training Date<span style="color: red">*</span></th>
					<td>
						<input type="text" id = "udatee" name="trainingDate"
							placeholder="Select Training Date"
							value="<%=DataUtility.getDateString(bean.getTrainingDate())%>">
					</td>
					<td style="position: fixed;">
						<font color="red">
							<%=ServletUtility.getErrorMessage("trainingDate", request)%>
						</font>
					</td>
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
					<td align="left" colspan="2">
						<input type="submit" name="operation" value="<%=TrainingCtl.OP_UPDATE%>">
						<input type="submit" name="operation" value="<%=TrainingCtl.OP_CANCEL%>">
					</td>
					<%
						} else {
					%>
					<td align="left" colspan="2">
						<input type="submit" name="operation" value="<%=TrainingCtl.OP_SAVE%>">
						<input type="submit" name="operation" value="<%=TrainingCtl.OP_RESET%>">
					</td>
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