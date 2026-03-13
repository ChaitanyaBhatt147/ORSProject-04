<%@page import="in.co.rays.proj4.controller.ORSView"%>
<%@page import="in.co.rays.proj4.controller.SeminarCtl"%>
<%@page import="in.co.rays.proj4.bean.SeminarBean"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>

<html>
<head>
<title>Add Seminar</title>

<link rel="icon" type="image/png"
	href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16x16" />
</head>

<body>

<form action="<%=ORSView.SEMINAR_CTL%>" method="POST">

	<%@ include file="Header.jsp"%>

	<jsp:useBean id="bean"
		class="in.co.rays.proj4.bean.SeminarBean"
		scope="request"></jsp:useBean>

	<div align="center">

		<h1 align="center" style="margin-bottom:-15; color: navy">
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
			Seminar
		</h1>

		<div style="height:15px; margin-bottom:12px">

			<h3 align="center">
				<font color="green">
					<%=ServletUtility.getSuccessMessage(request)%>
				</font>
			</h3>

			<h3 align="center">
				<font color="red">
					<%=ServletUtility.getErrorMessage(request)%>
				</font>
			</h3>

		</div>

		<!-- Hidden Fields -->
		<input type="hidden" name="id" value="<%=bean.getId()%>">
		<input type="hidden" name="createdBy"
			value="<%=bean.getCreatedBy()%>">
		<input type="hidden" name="modifiedBy"
			value="<%=bean.getModifiedBy()%>">

		<input type="hidden" name="createdDatetime"
			value="<%=DataUtility.getTimestamp(bean.getCreatedDatetime())%>">

		<input type="hidden" name="modifiedDatetime"
			value="<%=DataUtility.getTimestamp(bean.getModifiedDatetime())%>">

		<table>

			<tr>
				<th align="left">
					Seminar Title<span style="color:red">*</span>
				</th>

				<td>
					<input type="text" name="seminarTitle"
						placeholder="Enter Seminar Title"
						value="<%=DataUtility.getStringData(bean.getSeminarTitle())%>">
				</td>

				<td style="position:fixed;">
					<font color="red">
						<%=ServletUtility.getErrorMessage("seminarTitle", request)%>
					</font>
				</td>
			</tr>

			<tr>
				<th align="left">
					Seminar Date<span style="color:red">*</span>
				</th>

				<td>
					<input type="text" id="udatee" name="seminarDate"
						placeholder="Select Seminar Date"
						value="<%=DataUtility.getDateString(bean.getSeminarDate())%>">
				</td>

				<td style="position:fixed;">
					<font color="red">
						<%=ServletUtility.getErrorMessage("seminarDate", request)%>
					</font>
				</td>
			</tr>

			<tr>
				<th align="left">
					Speaker<span style="color:red">*</span>
				</th>

				<td>
					<input type="text" name="speaker"
						placeholder="Enter Speaker Name"
						value="<%=DataUtility.getStringData(bean.getSpeaker())%>">
				</td>

				<td style="position:fixed;">
					<font color="red">
						<%=ServletUtility.getErrorMessage("speaker", request)%>
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
					<input type="submit" name="operation"
						value="<%=SeminarCtl.OP_UPDATE%>">

					<input type="submit" name="operation"
						value="<%=SeminarCtl.OP_CANCEL%>">
				</td>

				<%
					} else {
				%>

				<td align="left" colspan="2">
					<input type="submit" name="operation"
						value="<%=SeminarCtl.OP_SAVE%>">

					<input type="submit" name="operation"
						value="<%=SeminarCtl.OP_RESET%>">
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