package database;

import java.sql.*;

public class DBManage {
    String url = "jdbc:mysql://localhost:3306/land?&useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";

    String user = "root";

    String pwd = "1234";

//    int uno = -1;

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

    public int useLogin(String userName, String pwd) {
        Connection connection = this.GetConnection();
        if (connection == null) {
            System.out.println("unable to connect the mysql database");
            return -1;
        } else {
            System.out.println("connect succeed");
            try {
                PreparedStatement getPwd = connection.prepareStatement("SELECT PASSWORD FROM USER WHERE UNAME = ?;");
                getPwd.setString(1, userName);
                ResultSet re = getPwd.executeQuery();
                System.out.println(re);
                if (re.next()) {
                    int dbPwd = re.getInt(1);
                    int inputPwd = Integer.parseInt(pwd);
                    if (dbPwd == inputPwd) {
                        PreparedStatement getUNO = connection.prepareStatement("SELECT UNO FROM user WHERE UNAME = ?;");
                        getUNO.setString(1, userName);
                        ResultSet getRe = getUNO.executeQuery();
                        getRe.next();
                        return getRe.getInt(1);
                    } else {

                        return -1;
                    }
                } else {
                    System.out.println("re is null");
                    return -1;
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public boolean userRegister(String userName, String pwd) {
        Connection connection = this.GetConnection();
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM USER WHERE uno = ?;");
            statement.setString(1, userName);
            ResultSet re = statement.executeQuery();
            if (re.next()) {
                return false;
            } else {
                PreparedStatement insert = connection.prepareStatement("INSERT INTO user (UNAME, PASSWORD) VALUES (?, ?);");
                insert.setString(1, userName);
                insert.setInt(2, Integer.parseInt(pwd));
                System.out.println(insert);
                insert.execute();
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int postLand(int uno, String LTYPE, String TTYPE, String location, int price) {
        Connection connection = this.GetConnection();
        try {
            Statement getLandNum = connection.createStatement();
            ResultSet numRe = getLandNum.executeQuery("SELECT COUNT(*) FROM land;");
            numRe.next();
            int LNO = numRe.getInt(1) + 1;

            PreparedStatement insertLand =
                    connection.prepareStatement("INSERT INTO land (LNO, UNO, LTYPE, TTYPE, LOCATION, PRICE) " +
                            "VALUES (?, ?, ?, ?, ?, ?);");

            insertLand.setInt(1, LNO);
            insertLand.setInt(2, uno);
            insertLand.setString(3, LTYPE);
            insertLand.setString(4, TTYPE);
            insertLand.setString(5, location);
            insertLand.setInt(6, price);

            insertLand.execute();
            return LNO;
        } catch (SQLException e) {
            System.out.println(e.getErrorCode());
            return -1;
        }
    }
}
