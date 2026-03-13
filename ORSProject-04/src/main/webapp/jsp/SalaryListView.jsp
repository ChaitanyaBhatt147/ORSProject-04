<%@page import="in.co.rays.proj4.controller.ORSView"%>
<%@page import="in.co.rays.proj4.controller.SalaryListCtl"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>
<%@page import="in.co.rays.proj4.bean.SalaryBean"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.text.SimpleDateFormat"%>

<html>
<head>
<title>Salary List</title>

<link rel="icon" type="image/png"
	href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16x16" />

</head>

<body>

	<%@include file="Header.jsp"%>

	<div align="center">

		<h1 style="margin-bottom: -15; color: navy;">Salary List</h1>

		<div style="height: 15px; margin-bottom: 12px">

			<h3>
				<font color="red"> <%=ServletUtility.getErrorMessage(request)%>
				</font>
			</h3>

			<h3>
				<font color="green"> <%=ServletUtility.getSuccessMessage(request)%>
				</font>
			</h3>

		</div>

		<form action="<%=ORSView.SALARY_LIST_CTL%>" method="post">

			<%
				int pageNo = ServletUtility.getPageNo(request);
				int pageSize = ServletUtility.getPageSize(request);

				int index = ((pageNo - 1) * pageSize) + 1;

				int nextPageSize = DataUtility.getInt(request.getAttribute("nextListSize").toString());

				@SuppressWarnings("unchecked")
				List<SalaryBean> list = (List<SalaryBean>) ServletUtility.getList(request);

				Iterator<SalaryBean> it = list.iterator();
			%>

			<input type="hidden" name="pageNo" value="<%=pageNo%>"> <input
				type="hidden" name="pageSize" value="<%=pageSize%>">

			<!-- ===== SEARCH AREA ===== -->

			<table style="width: 100%">
				<tr>
					<td align="center"><label><b>Salary Slip Code :</b></label> <input
						type="text" name="salarySlipCode"
						value="<%=ServletUtility.getParameter("salarySlipCode", request)%>"
						placeholder="Enter Slip Code"> &nbsp;&nbsp; <label><b>Employee
								Name :</b></label> <input type="text" name="employeeName"
						value="<%=ServletUtility.getParameter("employeeName", request)%>"
						placeholder="Enter Employee Name"> &nbsp;&nbsp; <input
						type="submit" name="operation"
						value="<%=SalaryListCtl.OP_SEARCH%>"> <input type="submit"
						name="operation" value="<%=SalaryListCtl.OP_RESET%>"></td>
				</tr>
			</table>

			<br>

			<!-- ===== TABLE ===== -->

			<table border="1" style="width: 100%; border: groove;">

				<tr style="background-color: #e1e6f1e3;">

					<th width="5%"><input type="checkbox" id="selectall" /></th>
					<th width="5%">S.No</th>
					<th width="15%">Salary Slip Code</th>
					<th width="20%">Employee Name</th>
					<th width="15%">Basic Salary</th>
					<th width="10%">Bonus</th>
					<th width="15%">Salary Date</th>
					<th width="5%">Edit</th>

				</tr>

				<%
					SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

					while (it.hasNext()) {

						SalaryBean bean = it.next();
				%>

				<tr>

					<td align="center"><input type="checkbox" class="case"
						name="ids" value="<%=bean.getId()%>"></td>

					<td align="center"><%=index++%></td>

					<td align="center"><%=bean.getSalarySlipCode()%></td>

					<td align="center" style="text-transform: capitalize;"><%=bean.getEmployeeName()%>
					</td>

					<td align="center"><%=bean.getBasicSalary()%></td>

					<td align="center"><%=bean.getBonus()%></td>

					<td align="center"><%=sdf.format(bean.getSalaryDate())%></td>

					<td align="center"><a
						href="<%=ORSView.SALARY_CTL%>?id=<%=bean.getId()%>">Edit</a></td>

				</tr>

				<%
					}
				%>

			</table>

			<!-- ===== PAGINATION ===== -->

			<table style="width: 100%">
				<tr>

					<td width="25%"><input type="submit" name="operation"
						value="<%=SalaryListCtl.OP_PREVIOUS%>"
						<%=pageNo > 1 ? "" : "disabled"%>></td>

					<td align="center" width="25%"><input type="submit"
						name="operation" value="<%=SalaryListCtl.OP_NEW%>"></td>

					<td align="center" width="25%"><input type="submit"
						name="operation" value="<%=SalaryListCtl.OP_DELETE%>"></td>

					<td align="right" width="25%"><input type="submit"
						name="operation" value="<%=SalaryListCtl.OP_NEXT%>"
						<%=(nextPageSize != 0) ? "" : "disabled"%>></td>

				</tr>
			</table>

		</form>
	</div>

	<%@ include file="Footer.jsp"%>

</body>
</html>