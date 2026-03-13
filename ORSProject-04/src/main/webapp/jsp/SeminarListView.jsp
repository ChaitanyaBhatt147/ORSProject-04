<%@page import="in.co.rays.proj4.controller.ORSView"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.controller.SeminarListCtl"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>
<%@page import="in.co.rays.proj4.bean.SeminarBean"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>

<html>
<head>
<title>Seminar List</title>

<link rel="icon" type="image/png"
	href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16x16" />
</head>

<body>

	<%@include file="Header.jsp"%>

	<div align="center">

		<h1 align="center" style="margin-bottom: -15; color: navy;">
			Seminar List</h1>

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

		<form action="<%=ORSView.SEMINAR_LIST_CTL%>" method="post">

			<%
				int pageNo = ServletUtility.getPageNo(request);
				int pageSize = ServletUtility.getPageSize(request);

				int index = ((pageNo - 1) * pageSize) + 1;

				int nextPageSize = DataUtility.getInt(request.getAttribute("nextListSize").toString());

				@SuppressWarnings("unchecked")
				List<SeminarBean> list = (List<SeminarBean>) ServletUtility.getList(request);

				Iterator<SeminarBean> it = list.iterator();

				if (list.size() != 0) {
			%>

			<input type="hidden" name="pageNo" value="<%=pageNo%>"> <input
				type="hidden" name="pageSize" value="<%=pageSize%>">

			<!-- ================= SEARCH BAR ================= -->
			<table style="width: 100%">
				<tr>
					<td align="center"><label><b>Seminar Title :</b></label> <input
						type="text" name="seminarTitle" placeholder="Enter Seminar Title"
						value="<%=ServletUtility.getParameter("seminarTitle", request)%>">

						&emsp; <label><b>Speaker :</b></label> <input type="text"
						name="speaker" placeholder="Enter Speaker"
						value="<%=ServletUtility.getParameter("speaker", request)%>">

						&emsp; <input type="submit" name="operation"
						value="<%=SeminarListCtl.OP_SEARCH%>"> <input
						type="submit" name="operation"
						value="<%=SeminarListCtl.OP_RESET%>"></td>
				</tr>
			</table>

			<br>

			<!-- ================= TABLE ================= -->

			<table border="1" style="width: 100%; border: groove;">

				<tr style="background-color: #e1e6f1e3;">
					<th width="5%"><input type="checkbox" id="selectall" /></th>
					<th width="5%">S.No</th>
					<th width="30%">Seminar Title</th>
					<th width="25%">Speaker</th>
					<th width="20%">Seminar Date</th>
					<th width="10%">Edit</th>
				</tr>

				<%
					while (it.hasNext()) {

							SeminarBean bean = it.next();
				%>

				<tr>

					<td align="center"><input type="checkbox" class="case"
						name="ids" value="<%=bean.getId()%>"></td>

					<td align="center"><%=index++%></td>

					<td align="center" style="text-transform: capitalize;"><%=bean.getSeminarTitle()%>
					</td>

					<td align="center" style="text-transform: capitalize;"><%=bean.getSpeaker()%>
					</td>

					<%
						SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
								String date = sdf.format(bean.getSeminarDate());
					%>

					<td align="center"><%=date%></td>

					<td align="center"><a
						href="<%=ORSView.SEMINAR_CTL%>?id=<%=bean.getId()%>"> Edit </a></td>

				</tr>

				<%
					}
				%>

			</table>

			<!-- ================= PAGINATION ================= -->

			<table style="width: 100%">

				<tr>

					<td style="width: 25%"><input type="submit" name="operation"
						value="<%=SeminarListCtl.OP_PREVIOUS%>"
						<%=pageNo > 1 ? "" : "disabled"%>></td>

					<td align="center" style="width: 25%"><input type="submit"
						name="operation" value="<%=SeminarListCtl.OP_NEW%>"></td>

					<td align="center" style="width: 25%"><input type="submit"
						name="operation" value="<%=SeminarListCtl.OP_DELETE%>"></td>

					<td align="right" style="width: 25%"><input type="submit"
						name="operation" value="<%=SeminarListCtl.OP_NEXT%>"
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
						value="<%=SeminarListCtl.OP_BACK%>"></td>
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