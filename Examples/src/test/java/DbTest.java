import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DbTest {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");

        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/thrift?serverTimezone=UTC",
                "root","123456");
        
        String sql = "insert into user(user_id) values(?)";

        PreparedStatement statement = connection.prepareCall(sql);
        
        statement.setInt(1, 0);
        
        int i = statement.executeUpdate();
        
        System.out.println(i);
        statement.close();
        connection.close();
    }
}
