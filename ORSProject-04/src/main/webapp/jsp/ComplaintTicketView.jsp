<%@page import="in.co.rays.proj4.controller.ORSView"%>
<%@page import="in.co.rays.proj4.controller.ComplaintTicketCtl"%>
<%@page import="in.co.rays.proj4.bean.ComplaintTicketBean"%>
<%@page import="java.util.HashMap"%>
<%@page import="in.co.rays.proj4.util.HTMLUtility"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>

<html>
<head>
<title>Add Complaint Ticket</title>

<link rel="icon" type="image/png"
	href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16x16" />

</head>

<body>

	<form action="<%=ORSView.COMPLAINT_TICKET_CTL%>" method="POST">

		<%@ include file="Header.jsp"%>

		<jsp:useBean id="bean"
			class="in.co.rays.proj4.bean.ComplaintTicketBean" scope="request"></jsp:useBean>

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

				Complaint Ticket

			</h1>

			<div style="height: 15px; margin-bottom: 12px">

				<H3 align="center">
					<font color="green"> <%=ServletUtility.getSuccessMessage(request)%>
					</font>
				</H3>

				<H3 align="center">
					<font color="red"> <%=ServletUtility.getErrorMessage(request)%>
					</font>
				</H3>

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

					<th align="left">Complaint Ticket ID <span style="color: red">*</span>
					</th>

					<td><input type="text" name="complaintTicketId"
						placeholder="Enter Complaint Ticket ID"
						value="<%=DataUtility.getStringData(bean.getComplaintTicketId())%>">

					</td>

					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("complaintTicketId", request)%>
					</font></td>

				</tr>

				<tr>

					<th align="left">Issue Type <span style="color: red">*</span>
					</th>

					<td><input type="text" name="issueType"
						placeholder="Enter Issue Type"
						value="<%=DataUtility.getStringData(bean.getIssueType())%>">

					</td>

					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("issueType", request)%>
					</font></td>

				</tr>

				<tr>

					<th align="left">Created Date <span style="color: red">*</span>
					</th>

					<td><input type="text" id="udatee" name="createdDate"
						placeholder="Select Created Date"
						value="<%=DataUtility.getDateString(bean.getCreatedDate())%>">

					</td>

					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("createdDate", request)%>
					</font></td>

				</tr>

				<tr>

					<th align="left">Status <span style="color: red">*</span>
					</th>

					<td>
						<%
							HashMap<String, String> map = new HashMap<String, String>();

							map.put("Open", "Open");
							map.put("In Progress", "In Progress");
							map.put("Closed", "Closed");

							String htmlList = HTMLUtility.getList("status", bean.getStatus(), map);
						%> <%=htmlList%>

					</td>

					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("status", request)%>
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
						name="operation" value="<%=ComplaintTicketCtl.OP_UPDATE%>">

						<input type="submit" name="operation"
						value="<%=ComplaintTicketCtl.OP_CANCEL%>"> <%
 	} else {
 %>
					<td align="left" colspan="2"><input type="submit"
						name="operation" value="<%=ComplaintTicketCtl.OP_SAVE%>">

						<input type="submit" name="operation"
						value="<%=ComplaintTicketCtl.OP_RESET%>"> <%
 	}
 %>
				</tr>

			</table>

		</div>

	</form>

	<%@ include file="Footer.jsp"%>

</body>
</html>