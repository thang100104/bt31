<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html><html lang="vi"><head><meta charset="UTF-8"><title>Xóa User</title></head>
<body style="font-family:Arial;background:#f8fafc">
<div style="width:420px;margin:50px auto;background:#fff;padding:25px">
<h2>Xác nhận xóa User</h2>
<p>${user.name} - ${user.email}</p>
<form action="${pageContext.request.contextPath}/users?action=delete" method="post">
<input type="hidden" name="id" value="${user.id}">
<button type="submit">Đồng ý xóa</button>
<a href="${pageContext.request.contextPath}/users">Hủy</a>
</form>
</div></body></html>
