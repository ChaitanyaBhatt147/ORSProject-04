<%@page import="in.co.rays.proj4.controller.ORSView"%>
<%@page import="in.co.rays.proj4.controller.LanguageLearningProgressCtl"%>
<%@page import="in.co.rays.proj4.bean.LanguageLearningProgressBean"%>
<%@page import="java.util.HashMap"%>
<%@page import="in.co.rays.proj4.util.HTMLUtility"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>

<html>
<head>
<title>Add Language Learning Progress</title>

<link rel="icon" type="image/png"
	href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16x16" />

</head>

<body>

	<form action="<%=ORSView.LANGUAGE_LEARNING_PROGRESS_CTL%>"
		method="POST">

		<%@ include file="Header.jsp"%>

		<jsp:useBean id="bean"
			class="in.co.rays.proj4.bean.LanguageLearningProgressBean"
			scope="request"></jsp:useBean>

		<div align="center">

			<h1 style="color: navy">

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

				Language Learning Progress

			</h1>


			<h3 style="color: green">
				<%=ServletUtility.getSuccessMessage(request)%>
			</h3>

			<h3 style="color: red">
				<%=ServletUtility.getErrorMessage(request)%>
			</h3>


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

					<th align="left">Student Name<span style="color: red">*</span></th>

					<td><input type="text" name="studentName"
						placeholder="Enter Student Name"
						value="<%=DataUtility.getStringData(bean.getStudentName())%>">
					</td>

					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("studentName", request)%>
					</font></td>

				</tr>


				<tr>

					<th align="left">Language<span style="color: red">*</span></th>

					<td><input type="text" name="language"
						placeholder="Enter Language"
						value="<%=DataUtility.getStringData(bean.getLanguage())%>">
					</td>

					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("language", request)%>
					</font></td>

				</tr>


				<tr>

					<th align="left">Level<span style="color: red">*</span></th>

					<td>
						<%
							HashMap<String, String> map = new HashMap<String, String>();

							map.put("Beginner", "Beginner");
							map.put("Intermediate", "Intermediate");
							map.put("Advanced", "Advanced");

							String htmlList = HTMLUtility.getList("level", bean.getLevel(), map);
						%> <%=htmlList%>

					</td>

					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("level", request)%>
					</font></td>

				</tr>


				<tr>

					<th align="left">Completion Status<span style="color: red">*</span></th>

					<td>
						<%
							HashMap<String, String> map2 = new HashMap<String, String>();

							map2.put("Completed", "Completed");
							map2.put("In Progress", "In Progress");

							String htmlList2 = HTMLUtility.getList("completionStatus", bean.getCompletionStatus(), map2);
						%> <%=htmlList2%>

					</td>

					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("completionStatus", request)%>
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
						name="operation"
						value="<%=LanguageLearningProgressCtl.OP_UPDATE%>"> <input
						type="submit" name="operation"
						value="<%=LanguageLearningProgressCtl.OP_CANCEL%>"></td>

					<%
						} else {
					%>

					<td align="left" colspan="2"><input type="submit"
						name="operation" value="<%=LanguageLearningProgressCtl.OP_SAVE%>">

						<input type="submit" name="operation"
						value="<%=LanguageLearningProgressCtl.OP_RESET%>"></td>

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