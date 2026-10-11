<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html><html lang="vi"><head><meta charset="UTF-8"><title>Sửa User</title></head>
<body style="font-family:Arial;background:#f8fafc">
<div style="width:420px;margin:50px auto;background:#fff;padding:25px">
<h2>Sửa User</h2>
<form action="${pageContext.request.contextPath}/users?action=edit" method="post">
<input type="hidden" name="id" value="${user.id}">
<p>Tên:</p><input name="name" value="${user.name}" required>
<p>Email:</p><input type="email" name="email" value="${user.email}" required>
<p>Quốc gia:</p><input name="country" value="${user.country}" required>
<p><button type="submit">Cập nhật</button></p>
</form>
<a href="${pageContext.request.contextPath}/users">Quay lại</a>
</div></body></html>
