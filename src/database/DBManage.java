package database;

import javax.swing.*;
import javax.xml.transform.Result;
import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.text.Format;

public class DBManage {
    String url = "jdbc:mysql://localhost:3306/land?&useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";

    String user = "root";

    String pwd = "1234";

    public DBManage() {

    }

    private Connection GetConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("数据库驱动添加成功");
            connection = DriverManager.getConnection(url, user, pwd);
            System.out.println("连接创键成功");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return connection;
    }

    public boolean useLogin(String userName, String pwd) {
        Connection connection = this.GetConnection();
        if (connection == null) {
            System.out.println("unable to connect the mysql database");
            return false;
        } else {
            System.out.println("connect succeed");
            try {
                PreparedStatement get_pwd = connection.prepareStatement("SELECT PASSWORD FROM USER WHERE UNAME = ?;");
                get_pwd.setString(1, userName);
                ResultSet re = get_pwd.executeQuery();
                if (!re.next()) {
                    int get_re = re.getInt(1);
                    System.out.println("the password :" + get_re);
                    int input_pwd = Integer.parseInt(pwd);
                    return get_re == input_pwd;
                } else {
                    System.out.println("re is null");
                    return false;
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public boolean userRegister(String userName, String pwd) {
        Connection connection = this.GetConnection();
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM USER WHERE uno = ?");
            statement.setString(1, userName);
            ResultSet re = statement.executeQuery();
            re.next();
            if (!re.next()) {
                return false;
            } else {
                PreparedStatement insert = connection.prepareStatement("INSERT INTO user VALUES (?, ?)");
                insert.setString(1, userName);
                insert.setInt(2, Integer.parseInt(pwd));
                insert.executeQuery();
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
