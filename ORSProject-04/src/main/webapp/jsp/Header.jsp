<%@page import="in.co.rays.proj4.bean.RoleBean"%>
<%@page import="in.co.rays.proj4.controller.ORSView"%>
<%@page import="in.co.rays.proj4.controller.LoginCtl"%>
<%@page import="in.co.rays.proj4.bean.UserBean"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>ORS Header</title>

<!-- jQuery -->
<script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>

<!-- jQuery UI -->
<script src="https://code.jquery.com/ui/1.13.2/jquery-ui.min.js"></script>
<link rel="stylesheet"
	href="https://code.jquery.com/ui/1.13.2/themes/base/jquery-ui.css">

<!-- Project JS -->
<script src="<%=ORSView.APP_CONTEXT%>/js/checkbox.js"></script>
<script src="<%=ORSView.APP_CONTEXT%>/js/datepicker.js"></script>

</head>

<body>

	<!-- Logo -->
	<img src="<%=ORSView.APP_CONTEXT%>/img/customLogo.jpg" align="right"
		width="100" height="40">

	<%
		UserBean user = (UserBean) session.getAttribute("user");
		boolean loggedIn = user != null;
	%>

	<%
		if (loggedIn) {
	%>

	<h3>
		Hi,
		<%=user.getFirstName()%>
		(<%=session.getAttribute("role")%>)
	</h3>

	<!-- Common Menus -->
	<a href="<%=ORSView.MY_PROFILE_CTL%>"><b>My Profile</b></a> |
	<a href="<%=ORSView.CHANGE_PASSWORD_CTL%>"><b>Change Password</b></a> |
	<a href="<%=ORSView.GET_MARKSHEET_CTL%>"><b>Get Marksheet</b></a> |
	<a href="<%=ORSView.MARKSHEET_MERIT_LIST_CTL%>"><b>Merit List</b></a> |

	<!-- ================= ADMIN ONLY ================= -->
	<%
		if (user.getRoleId() == RoleBean.ADMIN) {
	%>

	<a href="<%=ORSView.USER_CTL%>"><b>Add User</b></a> |
	<a href="<%=ORSView.USER_LIST_CTL%>"><b>User List</b></a> |
	<a href="<%=ORSView.ROLE_CTL%>"><b>Add Role</b></a> |
	<a href="<%=ORSView.ROLE_LIST_CTL%>"><b>Role List</b></a> |
	<a href="<%=ORSView.PATIENT_CTL%>"><b>Add Patient</b></a> |
	<a href="<%=ORSView.PATIENT_LIST_CTL%>"><b>Patient List</b></a> |
	<a href="<%=ORSView.COLLEGE_CTL%>"><b>Add College</b></a> |
	<a href="<%=ORSView.COLLEGE_LIST_CTL%>"><b>College List</b></a> |

	<%
		}
	%>

	<!-- ========== FACULTY + ADMIN MENUS ========== -->
	<%
		if (user.getRoleId() == RoleBean.FACULTY || user.getRoleId() == RoleBean.ADMIN) {
	%>

	<a href="<%=ORSView.STUDENT_CTL%>"><b>Add Student</b></a> |
	<a href="<%=ORSView.STUDENT_LIST_CTL%>"><b>Student List</b></a> |

	<!-- ===== TRAINING MODULE ===== -->
	<a href="<%=ORSView.TRAINING_CTL%>"><b>Add Training</b></a> |
	<a href="<%=ORSView.TRAINING_LIST_CTL%>"><b>Training List</b></a> |

	<!-- ===== ISSUE MODULE ===== -->
	<a href="<%=ORSView.ISSUE_CTL%>"><b>Add Issue</b></a> |
	<a href="<%=ORSView.ISSUE_LIST_CTL%>"><b>Issue List</b></a> |

	<a href="<%=ORSView.ORDER_CTL%>"><b>Add Order</b></a> |
	<a href="<%=ORSView.ORDER_LIST_CTL%>"><b>Order List</b></a> |

	<a href="<%=ORSView.REMINDER_CTL%>"><b>Add Reminder</b></a> |
	<a href="<%=ORSView.REMINDER_LIST_CTL%>"><b>Reminder List</b></a> |

	<a href="<%=ORSView.MARKSHEET_CTL%>"><b>Add Marksheet</b></a> |
	<a href="<%=ORSView.MARKSHEET_LIST_CTL%>"><b>Marksheet List</b></a> |

	<a href="<%=ORSView.COURSE_CTL%>"><b>Add Course</b></a> |
	<a href="<%=ORSView.COURSE_LIST_CTL%>"><b>Course List</b></a> |

	<a href="<%=ORSView.SUBJECT_CTL%>"><b>Add Subject</b></a> |
	<a href="<%=ORSView.SUBJECT_LIST_CTL%>"><b>Subject List</b></a> |

	<a href="<%=ORSView.FACULTY_CTL%>"><b>Add Faculty</b></a> |
	<a href="<%=ORSView.FACULTY_LIST_CTL%>"><b>Faculty List</b></a> |

	<a href="<%=ORSView.TIMETABLE_CTL%>"><b>Add Timetable</b></a> |
	<a href="<%=ORSView.TIMETABLE_LIST_CTL%>"><b>Timetable List</b></a> |

	<!-- ===== TRAVEL PACKAGE MODULE ===== -->
	<a href="<%=ORSView.TRAVEL_PACKAGE_CTL%>"><b>Add Travel Package</b></a>
	|
	<a href="<%=ORSView.TRAVEL_PACKAGE_LIST_CTL%>"><b>Travel
			Package List</b></a> |

	<!-- ===== SALARY MODULE ===== -->
	<a href="<%=ORSView.SALARY_CTL%>"><b>Add Salary</b></a> |
	<a href="<%=ORSView.SALARY_LIST_CTL%>"><b>Salary List</b></a> |

	<!-- ===== SEMINAR MODULE ===== -->
	<a href="<%=ORSView.SEMINAR_CTL%>"><b>Add Seminar</b></a> |
	<a href="<%=ORSView.SEMINAR_LIST_CTL%>"><b>Seminar List</b></a> |

	<!-- ===== MEDICINE MODULE ===== -->
	<a href="<%=ORSView.MEDICINE_CTL%>"><b>Add Medicine</b></a> |
	<a href="<%=ORSView.MEDICINE_LIST_CTL%>"><b>Medicine List</b></a> |

	<!-- ===== COMPLAINT TICKET MODULE ===== -->
	<a href="<%=ORSView.COMPLAINT_TICKET_CTL%>"><b>Add Complaint
			Ticket</b></a> |
	<a href="<%=ORSView.COMPLAINT_TICKET_LIST_CTL%>"><b>Complaint
			Ticket List</b></a> |

	<!-- ===== PAYMENT MODULE ===== -->
	<a href="<%=ORSView.PAYMENT_CTL%>"><b>Add Payment</b></a> |
	<a href="<%=ORSView.PAYMENT_LIST_CTL%>"><b>Payment List</b></a> |

	<!-- ===== INTERNET PACKAGE MODULE ===== -->
	<a href="<%=ORSView.INTERNET_PACKAGE_CTL%>"><b>Add Internet
			Package</b></a> |
	<a href="<%=ORSView.INTERNET_PACKAGE_LIST_CTL%>"><b>Internet
			Package List</b></a> |

	<!-- ===== PLAN MODULE ===== -->
	<a href="<%=ORSView.PLAN_CTL%>"><b>Add Plan</b></a> |
	<a href="<%=ORSView.PLAN_LIST_CTL%>"><b>Plan List</b></a> |

	<!-- ===== ROOM MODULE ===== -->
	<a href="<%=ORSView.ROOM_CTL%>"><b>Add Room</b></a> |
	<a href="<%=ORSView.ROOM_LIST_CTL%>"><b>Room List</b></a> |

	<!-- ===== LANGUAGE LEARNING PROGRESS MODULE ===== -->
	<a href="<%=ORSView.LANGUAGE_LEARNING_PROGRESS_CTL%>"><b>Add
			Language Learning Progress</b></a> |
	<a href="<%=ORSView.LANGUAGE_LEARNING_PROGRESS_LIST_CTL%>"><b>Language
			Learning Progress List</b></a> |

	<%
		}
	%>

	<!-- Java Doc -->
	<a href="<%=ORSView.JAVA_DOC%>" target="_blank"><b>Java Doc</b></a> |

	<!-- Logout -->
	<a href="<%=ORSView.LOGIN_CTL%>?operation=<%=LoginCtl.OP_LOG_OUT%>">
		<b>Logout</b>
	</a>

	<%
		} else {
	%>

	<h3>Hi, Guest</h3>
	<a href="<%=ORSView.WELCOME_CTL%>"><b>Welcome</b></a> |
	<a href="<%=ORSView.LOGIN_CTL%>"><b>Login</b></a>

	<%
		}
	%>

	<hr>

</body>
</html>