# BT31 - Thực thi SQL không sử dụng Transaction

Bài thực hành JDBC giúp quan sát hiện tượng dữ liệu được ghi một phần khi JDBC chạy ở chế độ auto-commit và một câu lệnh SQL phía sau bị lỗi.

## Mục tiêu

- Hiểu bản chất JDBC Transaction
- Hiểu nguyên lý All-or-Nothing
- Quan sát hành vi mặc định auto-commit
- Thấy hiện tượng "rác dữ liệu" khi không rollback

## Nội dung bài

Phương thức `insertUpdateWithoutTransaction()` thực hiện:

1. Xóa và tạo lại bảng `Employee`
2. Insert nhân viên `Quynh`
3. Insert nhân viên `Ngan`
4. Cố tình gây lỗi ở câu UPDATE
5. Không sử dụng Transaction nên 2 bản ghi Insert vẫn tồn tại

## Cấu hình MySQL

Mở:

`src/main/java/com/codegym/dao/UserDAO.java`

và sửa:

```java
private final String jdbcUsername = "root";
private final String jdbcPassword = "password";
```

cho đúng với MySQL trên máy.

## Chuẩn bị database

Chạy file:

`database.sql`

## Build

```bash
mvn clean package
```

File WAR:

`target/user-management.war`

## Chạy bài test

Deploy lên Tomcat 10.1+ rồi truy cập:

`http://localhost:8080/user-management/users?action=test-without-tran`

Sau đó kiểm tra MySQL:

```sql
SELECT * FROM Employee;
```

Kết quả mong đợi: hai bản ghi `Quynh` và `Ngan` vẫn tồn tại mặc dù câu UPDATE phía sau bị lỗi.
