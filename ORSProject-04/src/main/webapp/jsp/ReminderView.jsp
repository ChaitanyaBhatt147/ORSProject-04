<%@page import="in.co.rays.proj4.controller.ORSView"%>
<%@page import="in.co.rays.proj4.controller.ReminderCtl"%>
<%@page import="in.co.rays.proj4.bean.ReminderBean"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>

<html>
<head>
<title>Add Reminder</title>
</head>

<body>

	<form action="<%=ORSView.REMINDER_CTL%>" method="POST">
		<%@ include file="Header.jsp"%>
		<jsp:useBean id="bean" class="in.co.rays.proj4.bean.ReminderBean"
			scope="request" />

		<div align="center">

			<h1>
				<%
					if (bean.getId() > 0) {
				%>
				Update
				<%
					} else {
				%>
				Add
				<%
					}
				%>
				Reminder
			</h1>

			<font color="green"><%=ServletUtility.getSuccessMessage(request)%></font>
			<font color="red"><%=ServletUtility.getErrorMessage(request)%></font>

			<input type="hidden" name="id" value="<%=bean.getId()%>">

			<table>

				<tr>
					<th>Reminder Code*</th>
					<td><input type="text" name="reminderCode"
						value="<%=DataUtility.getStringData(bean.getReminderCode())%>">
					</td>
				</tr>

				<tr>
					<th>Title*</th>
					<td><input type="text" name="reminderTitle"
						value="<%=DataUtility.getStringData(bean.getReminderTitle())%>">
					</td>
				</tr>

				<tr>
					<th>Date*</th>
					<td><input type="date" name="reminderDate"
						placeholder="dd-mm-yyyy"
						value="<%=DataUtility.getStringData(bean.getReminderDate())%>">
					</td>
				</tr>

				<tr>
					<th>Status*</th>
					<td><select name="reminderStatus">
							<option value="">--Select--</option>
							<option
								<%="Active".equals(bean.getReminderStatus()) ? "selected" : ""%>>Active</option>
							<option
								<%="Pending".equals(bean.getReminderStatus()) ? "selected" : ""%>>Pending</option>
							<option
								<%="Completed".equals(bean.getReminderStatus()) ? "selected" : ""%>>Completed</option>
					</select></td>
				</tr>

				<tr>
					<td></td>
					<td>
						<%
							if (bean.getId() > 0) {
						%> <input type="submit" name="operation"
						value="<%=ReminderCtl.OP_UPDATE%>"> <input type="submit"
						name="operation" value="<%=ReminderCtl.OP_CANCEL%>"> <%
 	} else {
 %> <input type="submit" name="operation"
						value="<%=ReminderCtl.OP_SAVE%>"> <input type="submit"
						name="operation" value="<%=ReminderCtl.OP_RESET%>"> <%
 	}
 %>
					</td>
				</tr>

			</table>

		</div>
	</form>
</body>
</html>