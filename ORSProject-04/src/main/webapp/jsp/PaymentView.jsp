<%@page import="in.co.rays.proj4.controller.ORSView"%>
<%@page import="in.co.rays.proj4.controller.PaymentCtl"%>
<%@page import="in.co.rays.proj4.bean.PaymentBean"%>
<%@page import="java.util.HashMap"%>
<%@page import="in.co.rays.proj4.util.HTMLUtility"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>

<html>
<head>
<title>Add Payment</title>

<link rel="icon" type="image/png"
	href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16x16" />

</head>

<body>

	<form action="<%=ORSView.PAYMENT_CTL%>" method="POST">

		<%@ include file="Header.jsp"%>

		<jsp:useBean id="bean" class="in.co.rays.proj4.bean.PaymentBean"
			scope="request" />

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

				Payment

			</h1>


			<div style="height: 15px; margin-bottom: 12px">

				<h3 align="center">
					<font color="green"> <%=ServletUtility.getSuccessMessage(request)%>
					</font>
				</h3>

				<h3 align="center">
					<font color="red"> <%=ServletUtility.getErrorMessage(request)%>
					</font>
				</h3>

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
					<th align="left">Payment Code<span style="color: red">*</span></th>

					<td><input type="text" name="paymentCode"
						placeholder="Enter Payment Code"
						value="<%=DataUtility.getStringData(bean.getPaymentCode())%>">

					</td>

					<td style="position: fixed"><font color="red"> <%=ServletUtility.getErrorMessage("paymentCode", request)%>

					</font></td>

				</tr>


				<tr>
					<th align="left">User ID<span style="color: red">*</span></th>

					<td><input type="text" name="userId"
						placeholder="Enter User ID"
						value="<%=DataUtility.getStringData(bean.getUserId())%>">

					</td>

					<td style="position: fixed"><font color="red"> <%=ServletUtility.getErrorMessage("userId", request)%>

					</font></td>

				</tr>


				<tr>

					<th align="left">Amount<span style="color: red">*</span></th>

					<td><input type="text" name="amount"
						placeholder="Enter Amount"
						value="<%=DataUtility.getStringData(bean.getAmount())%>">

					</td>

					<td style="position: fixed"><font color="red"> <%=ServletUtility.getErrorMessage("amount", request)%>

					</font></td>

				</tr>


				<tr>

					<th align="left">Payment Status<span style="color: red">*</span></th>

					<td>
						<%
							HashMap<String, String> map = new HashMap<String, String>();

							map.put("Pending", "Pending");
							map.put("Completed", "Completed");
							map.put("Failed", "Failed");

							String htmlList = HTMLUtility.getList("paymentStatus", bean.getPaymentStatus(), map);
						%> <%=htmlList%>

					</td>

					<td style="position: fixed"><font color="red"> <%=ServletUtility.getErrorMessage("paymentStatus", request)%>

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
						name="operation" value="<%=PaymentCtl.OP_UPDATE%>"> <input
						type="submit" name="operation" value="<%=PaymentCtl.OP_CANCEL%>">

						<%
							} else {
						%>
					<td align="left" colspan="2"><input type="submit"
						name="operation" value="<%=PaymentCtl.OP_SAVE%>"> <input
						type="submit" name="operation" value="<%=PaymentCtl.OP_RESET%>">

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