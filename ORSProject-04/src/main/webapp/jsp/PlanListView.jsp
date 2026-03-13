<%@page import="in.co.rays.proj4.controller.ORSView"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.controller.PlanListCtl"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>
<%@page import="in.co.rays.proj4.bean.PlanBean"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>

<html>

<head>

<title>Plan List</title>

<link rel="icon" type="image/png"
	href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16x16" />

</head>

<body>

	<%@include file="Header.jsp"%>

	<div align="center">

		<h1 align="center" style="margin-bottom: -15; color: navy;">Plan
			List</h1>


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


		<form action="<%=ORSView.PLAN_LIST_CTL%>" method="post">

			<%
				int pageNo = ServletUtility.getPageNo(request);
				int pageSize = ServletUtility.getPageSize(request);

				int index = ((pageNo - 1) * pageSize) + 1;

				int nextPageSize = DataUtility.getInt(request.getAttribute("nextListSize").toString());

				List<PlanBean> list = (List<PlanBean>) ServletUtility.getList(request);

				Iterator<PlanBean> it = list.iterator();

				if (list.size() != 0) {
			%>


			<input type="hidden" name="pageNo" value="<%=pageNo%>"> <input
				type="hidden" name="pageSize" value="<%=pageSize%>">


			<table style="width: 100%">

				<tr>

					<td align="center"><label><b>Plan Name :</b></label> <input
						type="text" name="planName" placeholder="Enter Plan Name"
						value="<%=ServletUtility.getParameter("planName", request)%>">

						&nbsp; <input type="submit" name="operation"
						value="<%=PlanListCtl.OP_SEARCH%>"> &nbsp; <input
						type="submit" name="operation" value="<%=PlanListCtl.OP_RESET%>">

					</td>

				</tr>

			</table>

			<br>


			<table border="1" style="width: 100%; border: groove;">

				<tr style="background-color: #e1e6f1e3;">

					<th width="5%"><input type="checkbox" id="selectall" /></th>

					<th width="5%">S.No</th>

					<th width="40%">Plan Name</th>

					<th width="25%">Price</th>

					<th width="15%">Validity Days</th>

					<th width="10%">Edit</th>

				</tr>


				<%
					while (it.hasNext()) {

							PlanBean bean = it.next();
				%>


				<tr>

					<td style="text-align: center"><input type="checkbox"
						class="case" name="ids" value="<%=bean.getId()%>"></td>

					<td style="text-align: center"><%=index++%></td>

					<td style="text-align: center"><%=bean.getPlanName()%></td>

					<td style="text-align: center"><%=bean.getPrice()%></td>

					<td style="text-align: center"><%=bean.getValidityDays()%></td>

					<td style="text-align: center"><a
						href="<%=ORSView.PLAN_CTL%>?id=<%=bean.getId()%>"> Edit </a></td>

				</tr>

				<%
					}
				%>

			</table>


			<table style="width: 100%">

				<tr>

					<td style="width: 25%"><input type="submit" name="operation"
						value="<%=PlanListCtl.OP_PREVIOUS%>"
						<%=pageNo > 1 ? "" : "disabled"%>></td>


					<td align="center" style="width: 25%"><input type="submit"
						name="operation" value="<%=PlanListCtl.OP_NEW%>"></td>


					<td align="center" style="width: 25%"><input type="submit"
						name="operation" value="<%=PlanListCtl.OP_DELETE%>"></td>


					<td align="right" style="width: 25%"><input type="submit"
						name="operation" value="<%=PlanListCtl.OP_NEXT%>"
						<%=(nextPageSize != 0) ? "" : "disabled"%>></td>

				</tr>

			</table>


			<%
				}

				if (list.size() == 0) {
			%>

			<table>

				<tr>

					<td align="right"><input type="submit" name="operation"
						value="<%=PlanListCtl.OP_BACK%>"></td>

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