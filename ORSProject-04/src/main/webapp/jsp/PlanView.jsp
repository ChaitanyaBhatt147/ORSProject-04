<%@page import="in.co.rays.proj4.controller.ORSView"%>
<%@page import="in.co.rays.proj4.controller.PlanCtl"%>
<%@page import="in.co.rays.proj4.bean.PlanBean"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>

<html>
<head>
<title>Add Plan</title>

<link rel="icon" type="image/png"
	href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16x16" />

</head>

<body>

	<form action="<%=ORSView.PLAN_CTL%>" method="POST">

		<%@ include file="Header.jsp"%>

		<jsp:useBean id="bean" class="in.co.rays.proj4.bean.PlanBean"
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

				Plan
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
					<th align="left">Plan Name<span style="color: red">*</span></th>

					<td><input type="text" name="planName"
						placeholder="Enter Plan Name"
						value="<%=DataUtility.getStringData(bean.getPlanName())%>">
					</td>

					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("planName", request)%>
					</font></td>
				</tr>


				<tr>
					<th align="left">Price<span style="color: red">*</span></th>

					<td><input type="text" name="price" placeholder="Enter Price"
						value="<%=DataUtility.getStringData(bean.getPrice())%>"></td>

					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("price", request)%>
					</font></td>
				</tr>


				<tr>
					<th align="left">Validity Days<span style="color: red">*</span></th>

					<td><input type="text" name="validityDays"
						placeholder="Enter Validity Days"
						value="<%=DataUtility.getStringData(bean.getValidityDays())%>">
					</td>

					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("validityDays", request)%>
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
						name="operation" value="<%=PlanCtl.OP_UPDATE%>"> <input
						type="submit" name="operation" value="<%=PlanCtl.OP_CANCEL%>">

						<%
							} else {
						%>
					<td align="left" colspan="2"><input type="submit"
						name="operation" value="<%=PlanCtl.OP_SAVE%>"> <input
						type="submit" name="operation" value="<%=PlanCtl.OP_RESET%>">

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