
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;
import java.util.Properties;
import java.io.InputStream;

/**
 * Lớp tiện ích hỗ trợ làm việc với CSDL quan hệ
 *
 */
public class XJdbc {

    private static Connection connection;
   
    private static String driver;
    private static String dburl;
    private static String username;
    private static String password;

    /**
     * Mở kết nối nếu chưa mở hoặc đã đóng
     *
     * @return Kết nối đã sẵn sàng
     */

    public static Connection openConnection() {

    	driver = p.getProperty("db.driver");
		dburl = p.getProperty("db.url");
		username = p.getProperty("db.user");
		password = p.getProperty("db.pass");
		Class.forName(driver);

		try {
			if (!XJdbc.isReady()) {
				Class.forName(driver);
				connection = DriverManager.getConnection(dburl, username, password);
				return connection;
			}
		} catch (ClassNotFoundException | SQLException e) {
			throw new RuntimeException(e);
		}
    }

    /**
     * Đóng kết nối
     */
    public static void closeConnection() {
        try {
            if (XJdbc.isReady()) {
                connection.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Kiểm tra kết nối đã sẵn sàng hay chưa
     * @return true nếu kết nối đã được mở
     */
    public static boolean isReady() {
        try {
            return (connection != null && !connection.isClosed());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Thao tác dữ liệu
     *
     * @param sql câu lệnh SQL (INSERT, UPDATE, DELETE)
     * @param values các giá trị cung cấp cho các tham số trong SQL
     * @return số lượng bản ghi đã thực hiện
     * @throws RuntimeException không thực thi được câu lệnh SQL
     */
    public static int executeUpdate(String sql, Object... values) {
        try {
            var stmt = XJdbc.getStmt(sql, values);
            return stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * Truy vấn dữ liệu
     *
     * @param sql câu lệnh SQL (SELECT)
     * @param values các giá trị cung cấp cho các tham số trong SQL
     * @return tập kết quả truy vấn
     * @throws RuntimeException không thực thi được câu lệnh SQL
     */
    public static ResultSet executeQuery(String sql, Object... values) {
        try {
            var stmt = XJdbc.getStmt(sql, values);
            return stmt.executeQuery();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * Truy vấn một giá trị
     *
     * @param <T> kiểu dữ liệu kết quả
     * @param sql câu lệnh SQL (SELECT)
     * @param values các giá trị cung cấp cho các tham số trong SQL
     * @return giá trị truy vấn hoặc null
     * @throws RuntimeException không thực thi được câu lệnh SQL
     */
    public static <T> T getValue(String sql, Object... values) {
        try {
            var resultSet = XJdbc.executeQuery(sql, values);
            if (resultSet.next()) {
                return (T) resultSet.getObject(1);
            }
            return null;
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * Truy vấn danh sách bản ghi và ánh xạ sang List<Entity>
     *
     * @param <T> kiểu dữ liệu của entity
     * @param entityClass lớp của entity
     * @param sql câu lệnh SQL (SELECT)
     * @param values các giá trị cung cấp cho các tham số trong SQL
     * @return danh sách các entity
     * @throws RuntimeException nếu không thực thi được câu lệnh hoặc ánh xạ thất bại
     */
    public static <T> List<T> getBeanList(Class<T> entityClass, String sql, Object... values) {
        List<T> list = new ArrayList<>();
        try {
            ResultSet rs = executeQuery(sql, values);
            while (rs.next()) {
                T entity = mapResultSetToEntity(rs, entityClass);
                list.add(entity);
            }
            rs.close();
            return list;
        } catch (SQLException | ReflectiveOperationException ex) {
            throw new RuntimeException("Lỗi khi ánh xạ ResultSet sang danh sách entity", ex);
        }
    }

    /**
     * Truy vấn một bản ghi và ánh xạ sang Entity
     *
     * @param <T> kiểu dữ liệu của entity
     * @param entityClass lớp của entity
     * @param sql câu lệnh SQL (SELECT)
     * @param values các giá trị cung cấp cho các tham số trong SQL
     * @return entity hoặc null nếu không tìm thấy
     * @throws RuntimeException nếu không thực thi được câu lệnh hoặc ánh xạ thất bại
     */
    public static <T> T getSingleBean(Class<T> entityClass, String sql, Object... values) {
        try {
            ResultSet rs = executeQuery(sql, values);
            if (rs.next()) {
                T entity = mapResultSetToEntity(rs, entityClass);
                rs.close();
                return entity;
            }
            rs.close();
            return null;
        } catch (SQLException | ReflectiveOperationException ex) {
            throw new RuntimeException("Lỗi khi ánh xạ ResultSet sang entity", ex);
        }
    }

    /**
     * Ánh xạ ResultSet sang một đối tượng entity
     *
     * @param rs ResultSet chứa dữ liệu
     * @param entityClass lớp của entity
     * @return đối tượng entity đã ánh xạ
     * @throws SQLException nếu truy vấn ResultSet thất bại
     * @throws ReflectiveOperationException nếu ánh xạ thất bại
     */
    private static <T> T mapResultSetToEntity(ResultSet rs, Class<T> entityClass) throws SQLException, ReflectiveOperationException {
        T entity = entityClass.getDeclaredConstructor().newInstance();
        Field[] fields = entityClass.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            String columnName = field.getName();
            try {
                Object value = rs.getObject(columnName);
                if (value != null) {
                    if (field.getType() == boolean.class && value instanceof Boolean) {
                        field.set(entity, value);
                    } else if (field.getType() == int.class && value instanceof Number) {
                        field.set(entity, ((Number) value).intValue());
                    } else if (field.getType() == long.class && value instanceof Number) {
                        field.set(entity, ((Number) value).longValue());
                    } else if (field.getType() == double.class && value instanceof Number) {
                        field.set(entity, ((Number) value).doubleValue());
                    } else if (field.getType() == Date.class && value instanceof java.sql.Date) {
                        field.set(entity, new Date(((java.sql.Date) value).getTime()));
                    } else {
                        field.set(entity, value);
                    }
                }
            } catch (SQLException ex) {
                // Bỏ qua nếu cột không tồn tại trong ResultSet
            }
        }
        return entity;
    }

    /**
     * Tạo PreparedStatement từ câu lệnh SQL/PROC
     *
     * @param sql câu lệnh SQL/PROC
     * @param values các giá trị cung cấp cho các tham số trong SQL/PROC
     * @return đối tượng đã tạo
     * @throws SQLException không tạo được PreparedStatement
     */
    private static PreparedStatement getStmt(String sql, Object... values) throws SQLException {
        var conn = XJdbc.openConnection();
        var stmt = sql.trim().startsWith("{") ? conn.prepareCall(sql) : conn.prepareStatement(sql);
        for (int i = 0; i < values.length; i++) {
            stmt.setObject(i + 1, values[i]);
        }
        return stmt;
    }
//
//    public static void main(String[] args) {
//        demo1();
//        demo2();
//        demo3();
//    }
//
//    private static void demo1() {
//        String sql = "SELECT * FROM Drinks WHERE UnitPrice BETWEEN ? AND ?";
//        var rs = XJdbc.executeQuery(sql, 1.5, 5.0);
//    }
//
//    private static void demo2() {
//        String sql = "SELECT max(UnitPrice) FROM Drinks WHERE UnitPrice > ?";
//        var maxPrice = XJdbc.getValue(sql, 1.5);
//    }
//
//    private static void demo3() {
//        String sql = "DELETE FROM Drinks WHERE UnitPrice < ?";
//        var count = XJdbc.executeUpdate(sql, 0.0);
//    }
}