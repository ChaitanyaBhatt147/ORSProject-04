<%@page import="in.co.rays.proj4.controller.ORSView"%>
<%@page import="in.co.rays.proj4.controller.RoomCtl"%>
<%@page import="in.co.rays.proj4.bean.RoomBean"%>
<%@page import="java.util.HashMap"%>
<%@page import="in.co.rays.proj4.util.HTMLUtility"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>

<html>
<head>
<title>Add Room</title>

<link rel="icon" type="image/png"
	href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16x16" />

</head>

<body>

	<form action="<%=ORSView.ROOM_CTL%>" method="POST">

		<%@ include file="Header.jsp"%>

		<jsp:useBean id="bean" class="in.co.rays.proj4.bean.RoomBean"
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

				Room

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
					<th align="left">Room Code<span style="color: red">*</span></th>

					<td><input type="text" name="roomCode"
						placeholder="Enter Room Code"
						value="<%=DataUtility.getStringData(bean.getRoomCode())%>">
					</td>

					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("roomCode", request)%>
					</font></td>

				</tr>


				<tr>
					<th align="left">Room Name<span style="color: red">*</span></th>

					<td><input type="text" name="roomName"
						placeholder="Enter Room Name"
						value="<%=DataUtility.getStringData(bean.getRoomName())%>">
					</td>

					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("roomName", request)%>
					</font></td>

				</tr>


				<tr>
					<th align="left">Capacity<span style="color: red">*</span></th>

					<td><input type="text" name="capacity"
						placeholder="Enter Capacity"
						value="<%=DataUtility.getStringData(bean.getCapacity())%>">
					</td>

					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("capacity", request)%>
					</font></td>

				</tr>


				<tr>
					<th align="left">Room Status<span style="color: red">*</span></th>

					<td>
						<%
							HashMap<String, String> map = new HashMap<String, String>();

							map.put("Available", "Available");
							map.put("Occupied", "Occupied");
							map.put("Maintenance", "Maintenance");

							String htmlList = HTMLUtility.getList("roomStatus", bean.getRoomStatus(), map);
						%> <%=htmlList%>

					</td>

					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("roomStatus", request)%>
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
						name="operation" value="<%=RoomCtl.OP_UPDATE%>"> <input
						type="submit" name="operation" value="<%=RoomCtl.OP_CANCEL%>">

						<%
							} else {
						%>
					<td align="left" colspan="2"><input type="submit"
						name="operation" value="<%=RoomCtl.OP_SAVE%>"> <input
						type="submit" name="operation" value="<%=RoomCtl.OP_RESET%>">

						<%
							}
						%></td>

				</tr>

			</table>

		</div>

	</form>

	<%@ include file="Footer.jsp"%>

</body>
</html>