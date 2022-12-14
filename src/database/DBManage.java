package database;

import javax.xml.transform.Result;
import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.text.Format;

public class DBManage {
    String url;

    String user;

    String pwd;

    String DB;

    public DBManage() {

    }

    public DBManage(String _url, String _user, String _pwd, String _DB) {
        url = String.valueOf(_url.toCharArray(), 0, _url.length());
        user = String.valueOf(_user.toCharArray(), 0, _url.length());
        pwd = String.valueOf(_pwd.toCharArray(), 0, _pwd.length());
        DB = String.valueOf(_DB.toCharArray(), 0, _DB.length());
    }

    boolean useLogin(String userName, String pwd) {
        Connection connection;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.printf("数据库驱动添加成功");
            connection = DriverManager.getConnection(url, user, pwd);
            System.out.printf("连接创键成功");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM USER WHERE uno = ?");
            statement.setString(1, userName);
            ResultSet re = statement.executeQuery();
            re.next();
            if (!re.next()) {
                String get_re = re.getString("PASSWORD");
                return get_re.equals(pwd);
            } else {
                return false;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
