<%@page import="in.co.rays.proj4.controller.ORSView"%>
<%@page import="in.co.rays.proj4.controller.OrderCtl"%>
<%@page import="in.co.rays.proj4.bean.OrderBean"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>

<html>
<head>
<title>Add Order</title>
<link rel="icon" type="image/png"
	href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16x16" />
</head>

<body>
	<form action="<%=ORSView.ORDER_CTL%>" method="POST">
		<%@ include file="Header.jsp"%>

		<jsp:useBean id="bean" class="in.co.rays.proj4.bean.OrderBean"
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
				Order
			</h1>

			<div style="height: 15px; margin-bottom: 12px">
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

			<input type="hidden" name="id" value="<%=bean.getId()%>">
			<input type="hidden" name="createdBy" value="<%=bean.getCreatedBy()%>">
			<input type="hidden" name="modifiedBy" value="<%=bean.getModifiedBy()%>">
			<input type="hidden" name="createdDatetime"
				value="<%=DataUtility.getTimestamp(bean.getCreatedDatetime())%>">
			<input type="hidden" name="modifiedDatetime"
				value="<%=DataUtility.getTimestamp(bean.getModifiedDatetime())%>">

			<table>
				<tr>
					<th align="left">Order Id<span style="color: red">*</span></th>
					<td>
						<input type="text" name="orderId"
						placeholder="Enter Order Id"
						value="<%=DataUtility.getStringData(bean.getOrderId())%>">
					</td>
					<td style="position: fixed;">
						<font color="red">
							<%=ServletUtility.getErrorMessage("orderId", request)%>
						</font>
					</td>
				</tr>

				<tr>
					<th align="left">Name<span style="color: red">*</span></th>
					<td>
						<input type="text" name="name"
						placeholder="Enter Name"
						value="<%=DataUtility.getStringData(bean.getName())%>">
					</td>
					<td style="position: fixed;">
						<font color="red">
							<%=ServletUtility.getErrorMessage("name", request)%>
						</font>
					</td>
				</tr>

				<tr>
					<th align="left">Mobile No<span style="color: red">*</span></th>
					<td>
						<input type="text" name="number" maxlength="10"
						placeholder="Enter Mobile No"
						value="<%=DataUtility.getStringData(bean.getNumber())%>">
					</td>
					<td style="position: fixed;">
						<font color="red">
							<%=ServletUtility.getErrorMessage("number", request)%>
						</font>
					</td>
				</tr>

				<tr>
					<th align="left">Address<span style="color: red">*</span></th>
					<td>
						<input type="text" name="address"
						placeholder="Enter Address"
						value="<%=DataUtility.getStringData(bean.getAddress())%>">
					</td>
					<td style="position: fixed;">
						<font color="red">
							<%=ServletUtility.getErrorMessage("address", request)%>
						</font>
					</td>
				</tr>

				<tr>
					<th align="left">Amount<span style="color: red">*</span></th>
					<td>
						<input type="text" name="amount"
						placeholder="Enter Amount"
						value="<%=DataUtility.getStringData(bean.getAmount())%>">
					</td>
					<td style="position: fixed;">
						<font color="red">
							<%=ServletUtility.getErrorMessage("amount", request)%>
						</font>
					</td>
				</tr>

				<tr>
					<th></th>
					<%
						if (bean != null && bean.getId() > 0) {
					%>
					<td align="left" colspan="2">
						<input type="submit" name="operation"
						value="<%=OrderCtl.OP_UPDATE%>">
						<input type="submit" name="operation"
						value="<%=OrderCtl.OP_CANCEL%>">
					</td>
					<%
						} else {
					%>
					<td align="left" colspan="2">
						<input type="submit" name="operation"
						value="<%=OrderCtl.OP_SAVE%>">
						<input type="submit" name="operation"
						value="<%=OrderCtl.OP_RESET%>">
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
