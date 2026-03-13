<%@page import="in.co.rays.proj4.controller.ORSView"%>
<%@page import="in.co.rays.proj4.controller.IssueCtl"%>
<%@page import="in.co.rays.proj4.bean.IssueBean"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>
<html>
<head>
<title>Add Issue</title>
<link rel="icon" type="image/png"
	href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16x16" />
</head>
<body>
	<form action="<%=ORSView.ISSUE_CTL%>" method="POST">
		<%@ include file="Header.jsp"%>

		<jsp:useBean id="bean" class="in.co.rays.proj4.bean.IssueBean"
			scope="request"></jsp:useBean>

		<div align="center">
			<h1 align="center" style="margin-bottom: -15; color: navy">
				<%
					if (bean != null && bean.getId() > 0) {
				%>Update<%
					} else {
				%>Add<%
					}
				%>
				Issue
			</h1>

			<div style="height: 15px; margin-bottom: 12px">
				<H3 align="center">
					<font color="green">
						<%=ServletUtility.getSuccessMessage(request)%>
					</font>
				</H3>
				<H3 align="center">
					<font color="red">
						<%=ServletUtility.getErrorMessage(request)%>
					</font>
				</H3>
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
					<th align="left">Issue Code<span style="color: red">*</span></th>
					<td>
						<input type="text" name="issueCode"
							placeholder="Enter Issue Code"
							value="<%=DataUtility.getStringData(bean.getIssueCode())%>">
					</td>
					<td style="position: fixed;">
						<font color="red">
							<%=ServletUtility.getErrorMessage("issueCode", request)%>
						</font>
					</td>
				</tr>

				<tr>
					<th align="left">Student Name<span style="color: red">*</span></th>
					<td>
						<input type="text" name="studentName"
							placeholder="Enter Student Name"
							value="<%=DataUtility.getStringData(bean.getStudentName())%>">
					</td>
					<td style="position: fixed;">
						<font color="red">
							<%=ServletUtility.getErrorMessage("studentName", request)%>
						</font>
					</td>
				</tr>

				<tr>
					<th align="left">Issue Date<span style="color: red">*</span></th>
					<td>
						<input type="text" id="udatee" name="issueDate"
							placeholder="Select Issue Date"
							value="<%=DataUtility.getDateString(bean.getIssueDate())%>">
					</td>
					<td style="position: fixed;">
						<font color="red">
							<%=ServletUtility.getErrorMessage("issueDate", request)%>
						</font>
					</td>
				</tr>

				<tr>
					<th align="left">Return Date<span style="color: red">*</span></th>
					<td>
						<input type="text" id="udatee" name="returnDate"
							placeholder="Select Return Date"
							value="<%=DataUtility.getDateString(bean.getReturnDate())%>">
					</td>
					<td style="position: fixed;">
						<font color="red">
							<%=ServletUtility.getErrorMessage("returnDate", request)%>
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
							value="<%=IssueCtl.OP_UPDATE%>">
						<input type="submit" name="operation"
							value="<%=IssueCtl.OP_CANCEL%>">
					<%
						} else {
					%>
					<td align="left" colspan="2">
						<input type="submit" name="operation"
							value="<%=IssueCtl.OP_SAVE%>">
						<input type="submit" name="operation"
							value="<%=IssueCtl.OP_RESET%>">
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