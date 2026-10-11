<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html><html lang="vi"><head><meta charset="UTF-8"><title>Thêm User</title></head>
<body style="font-family:Arial;background:#f8fafc">
<div style="width:420px;margin:50px auto;background:#fff;padding:25px">
<h2>Thêm User</h2>
<form action="${pageContext.request.contextPath}/users?action=create" method="post">
<p>Tên:</p><input name="name" required>
<p>Email:</p><input type="email" name="email" required>
<p>Quốc gia:</p><input name="country" required>
<p><button type="submit">Lưu</button></p>
</form>
<a href="${pageContext.request.contextPath}/users">Quay lại</a>
</div></body></html>
