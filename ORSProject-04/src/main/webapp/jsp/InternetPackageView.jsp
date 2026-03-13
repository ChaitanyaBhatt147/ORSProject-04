<%@page import="in.co.rays.proj4.controller.ORSView"%>
<%@page import="in.co.rays.proj4.controller.InternetPackageCtl"%>
<%@page import="in.co.rays.proj4.bean.InternetPackageBean"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>

<html>
<head>
<title>Add Internet Package</title>

<link rel="icon" type="image/png"
	href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16x16" />

</head>

<body>

	<form action="<%=ORSView.INTERNET_PACKAGE_CTL%>" method="POST">

		<%@ include file="Header.jsp"%>

		<jsp:useBean id="bean"
			class="in.co.rays.proj4.bean.InternetPackageBean" scope="request"></jsp:useBean>

		<div align="center">

			<h1 style="color: navy">

				<%
					if (bean != null && bean.getId() > 0) {
				%>

				Update Internet Package

				<%
					} else {
				%>

				Add Internet Package

				<%
					}
				%>

			</h1>


			<div style="height: 15px">

				<h3>
					<font color="green"> <%=ServletUtility.getSuccessMessage(request)%>
					</font>
				</h3>

				<h3>
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
					<th align="left">Package Name <span style="color: red">*</span>
					</th>

					<td><input type="text" name="packageName"
						placeholder="Enter Package Name"
						value="<%=DataUtility.getStringData(bean.getPackageName())%>">

					</td>

					<td style="position: fixed"><font color="red"> <%=ServletUtility.getErrorMessage("packageName", request)%>

					</font></td>

				</tr>


				<tr>

					<th align="left">Price <span style="color: red">*</span>

					</th>

					<td><input type="text" name="price" placeholder="Enter Price"
						value="<%=DataUtility.getStringData(bean.getPrice())%>"></td>

					<td style="position: fixed"><font color="red"> <%=ServletUtility.getErrorMessage("price", request)%>

					</font></td>

				</tr>


				<tr>

					<th align="left">Data Limit (GB) <span style="color: red">*</span>

					</th>

					<td><input type="text" name="dataLimit"
						placeholder="Enter Data Limit"
						value="<%=DataUtility.getStringData(bean.getDataLimit())%>">

					</td>

					<td style="position: fixed"><font color="red"> <%=ServletUtility.getErrorMessage("dataLimit", request)%>

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

					<td align="left"><input type="submit" name="operation"
						value="<%=InternetPackageCtl.OP_UPDATE%>"> <input
						type="submit" name="operation"
						value="<%=InternetPackageCtl.OP_CANCEL%>"></td>

					<%
						} else {
					%>

					<td align="left"><input type="submit" name="operation"
						value="<%=InternetPackageCtl.OP_SAVE%>"> <input
						type="submit" name="operation"
						value="<%=InternetPackageCtl.OP_RESET%>"></td>

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