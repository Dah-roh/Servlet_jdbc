package DAO;

import Models.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


@NoArgsConstructor
@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class UserDao {
    private String jdbcURL;
    private String jdbcUsername;
    private String jdbcPassword;
    private Connection jdbcConnection;

    protected void connect () throws SQLException {
        if(jdbcConnection==null || jdbcConnection.isClosed()){
            try{
                Class.forName("com.mysql.jdbc.Driver");

            }
            catch(ClassNotFoundException e){
                throw new SQLException(e);
            }
            jdbcConnection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
        }

    }

    protected void disconnect () throws SQLException {
        if(jdbcConnection != null || !jdbcConnection.isClosed()) {
            jdbcConnection.close();
        }
    }

    public boolean insertUser(User user) throws SQLException {
        String sql = "insert into Users (username, password, userDescription) values (?,?,?)";
        connect();
        PreparedStatement statement = jdbcConnection.prepareStatement(sql);
        statement.setString(1, user.getUsername());
        statement.setString(2, user.getPassword());
        statement.setString(1, user.getUserDescription());
        boolean rowInserted = statement.executeUpdate() > 0;
        statement.close();
        disconnect();
        return rowInserted;
    }

    public List<User> listUserDetails (User user) throws SQLException {
        List<User> userDetails = new ArrayList<User>();
        String sql = "select * from Users where username = ?, password = ?";
        connect();
        PreparedStatement statement = jdbcConnection.prepareStatement(sql);
        statement.setString(1, user.getUserDescription());
        ResultSet resultSet = statement.executeQuery();

        return userDetails;
    }

}
