<%@page import="in.co.rays.proj4.controller.ORSView"%>
<%@page
	import="in.co.rays.proj4.controller.LanguageLearningProgressListCtl"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>
<%@page import="in.co.rays.proj4.bean.LanguageLearningProgressBean"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>

<html>
<head>

<title>Language Learning Progress List</title>

<link rel="icon" type="image/png"
	href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16x16" />

</head>

<body>

	<%@include file="Header.jsp"%>

	<div align="center">

		<h1 style="color: navy">Language Learning Progress List</h1>

		<h3 style="color: red">
			<%=ServletUtility.getErrorMessage(request)%>
		</h3>

		<h3 style="color: green">
			<%=ServletUtility.getSuccessMessage(request)%>
		</h3>


		<form action="<%=ORSView.LANGUAGE_LEARNING_PROGRESS_LIST_CTL%>"
			method="post">

			<%
				int pageNo = ServletUtility.getPageNo(request);
				int pageSize = ServletUtility.getPageSize(request);

				int index = ((pageNo - 1) * pageSize) + 1;

				int nextPageSize = DataUtility.getInt(request.getAttribute("nextListSize").toString());

				List<LanguageLearningProgressBean> list = (List<LanguageLearningProgressBean>) ServletUtility
						.getList(request);

				Iterator<LanguageLearningProgressBean> it = list.iterator();
			%>


			<input type="hidden" name="pageNo" value="<%=pageNo%>"> <input
				type="hidden" name="pageSize" value="<%=pageSize%>">


			<table style="width: 100%">

				<tr>

					<td align="center"><label><b>Student Name :</b></label> <input
						type="text" name="studentName" placeholder="Enter Student Name"
						value="<%=ServletUtility.getParameter("studentName", request)%>">

						&emsp; <label><b>Language :</b></label> <input type="text"
						name="language" placeholder="Enter Language"
						value="<%=ServletUtility.getParameter("language", request)%>">

						&emsp; <input type="submit" name="operation"
						value="<%=LanguageLearningProgressListCtl.OP_SEARCH%>"> <input
						type="submit" name="operation"
						value="<%=LanguageLearningProgressListCtl.OP_RESET%>"></td>

				</tr>

			</table>

			<br>


			<table border="1" style="width: 100%">

				<tr style="background: #e1e6f1">

					<th width="5%"><input type="checkbox" id="selectall">
					</th>

					<th width="5%">S.No</th>

					<th width="20%">Student Name</th>

					<th width="20%">Language</th>

					<th width="20%">Level</th>

					<th width="20%">Completion Status</th>

					<th width="10%">Edit</th>

				</tr>


				<%
					while (it.hasNext()) {

						LanguageLearningProgressBean bean = it.next();
				%>

				<tr>

					<td align="center"><input type="checkbox" class="case"
						name="ids" value="<%=bean.getId()%>"></td>

					<td align="center"><%=index++%></td>

					<td align="center"><%=bean.getStudentName()%></td>

					<td align="center"><%=bean.getLanguage()%></td>

					<td align="center"><%=bean.getLevel()%></td>

					<td align="center"><%=bean.getCompletionStatus()%></td>

					<td align="center"><a
						href="<%=ORSView.LANGUAGE_LEARNING_PROGRESS_CTL%>?id=<%=bean.getId()%>">

							Edit </a></td>

				</tr>

				<%
					}
				%>

			</table>


			<br>

			<table style="width: 100%">

				<tr>

					<td width="25%"><input type="submit" name="operation"
						value="<%=LanguageLearningProgressListCtl.OP_PREVIOUS%>"
						<%=pageNo > 1 ? "" : "disabled"%>></td>

					<td width="25%" align="center"><input type="submit"
						name="operation"
						value="<%=LanguageLearningProgressListCtl.OP_NEW%>"></td>

					<td width="25%" align="center"><input type="submit"
						name="operation"
						value="<%=LanguageLearningProgressListCtl.OP_DELETE%>"></td>

					<td width="25%" align="right"><input type="submit"
						name="operation"
						value="<%=LanguageLearningProgressListCtl.OP_NEXT%>"
						<%=(nextPageSize != 0) ? "" : "disabled"%>></td>

				</tr>

			</table>


		</form>

	</div>

	<%@include file="Footer.jsp"%>

</body>

</html>