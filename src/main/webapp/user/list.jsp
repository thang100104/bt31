<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="vi">
<head>
<meta charset="UTF-8">
<title>Quản lý User</title>
<style>
body{font-family:Arial;background:#f8fafc;margin:30px}
h2{text-align:center;color:#1b2a7a}
.actions{width:85%;margin:auto;display:flex;gap:10px}
.btn{padding:8px 12px;text-decoration:none;color:#fff;border-radius:4px}
.add{background:#27ae60}.test{background:#8e44ad}.edit{background:#2980b9}.delete{background:#c0392b}
table{width:85%;margin:20px auto;border-collapse:collapse;background:#fff}
th,td{border:1px solid #ddd;padding:12px}
th{background:#1b2a7a;color:#fff}
</style>
</head>
<body>
<h2>Danh sách User</h2>
<div class="actions">
<a class="btn add" href="${pageContext.request.contextPath}/users?action=create">Thêm mới User</a>
<a class="btn test" href="${pageContext.request.contextPath}/users?action=test-without-tran">Test không Transaction</a>
</div>
<table>
<tr><th>ID</th><th>Tên</th><th>Email</th><th>Quốc gia</th><th>Hành động</th></tr>
<c:forEach var="user" items="${listUser}">
<tr>
<td>${user.id}</td><td>${user.name}</td><td>${user.email}</td><td>${user.country}</td>
<td>
<a class="btn edit" href="${pageContext.request.contextPath}/users?action=edit&id=${user.id}">Sửa</a>
<a class="btn delete" href="${pageContext.request.contextPath}/users?action=delete&id=${user.id}">Xóa</a>
</td>
</tr>
</c:forEach>
</table>
</body>
</html>
