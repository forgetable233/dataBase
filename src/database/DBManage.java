package database;

import frame.BasicFrame;

import javax.print.attribute.standard.OrientationRequested;
import java.security.cert.CertPath;
import java.sql.*;
import java.util.Vector;

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

    public void getMyPost(int uno,
                          Vector<String> LTypes,
                          Vector<String> TTypes,
                          Vector<String> locations,
                          Vector<Integer> applyNum,
                          Vector<Integer> prices,
                          Vector<Integer> LNOs,
                          Vector<Integer> UNOs) {
        Connection connection = this.GetConnection();
        try {
            PreparedStatement getAllLands = connection.prepareStatement("SELECT LNO, LTYPE, TTYPE, PRICE, LOCATION FROM land WHERE UNO = ?;");
            getAllLands.setInt(1, uno);
            getLandInfo(LTypes, TTypes, locations, applyNum, prices, LNOs, UNOs, connection, getAllLands);
        } catch (SQLException e) {
            System.out.println(e.getErrorCode());
        }
    }

    public void deleteLand(int LNO) {
        Connection connection = this.GetConnection();
        try {
            PreparedStatement deleteLandRec = connection.prepareStatement("DELETE FROM land WHERE LNO = ?;");
            deleteLandRec.setInt(1, LNO);
            deleteLandRec.execute();
        } catch (SQLException e) {
            System.out.println(e.getErrorCode());
        }
    }

    public void changeLand(int LNO, String lType, String tType, String location, int price) {
        Connection connection = this.GetConnection();
        try {
            PreparedStatement changeLand =
                    connection.prepareStatement("UPDATE land SET LTYPE = ?, TTYPE = ?, LOCATION = ?, PRICE = ? WHERE LNO = ?;");
            changeLand.setString(1, lType);
            changeLand.setString(2, tType);
            changeLand.setString(3, location);
            changeLand.setInt(4, price);
            changeLand.setInt(5, LNO);
            changeLand.execute();
        } catch (SQLException e) {
            System.out.println(e.getErrorCode());
        }
    }

    public void getAllLands(Vector<String> LTypes,
                            Vector<String> TTypes,
                            Vector<String> locations,
                            Vector<Integer> applyNum,
                            Vector<Integer> prices,
                            Vector<Integer> LNOs,
                            Vector<Integer> UNOs) {
        Connection connection = this.GetConnection();
        try {
            PreparedStatement getLands = connection.prepareStatement("SELECT * FROM land;");
            getLandInfo(LTypes, TTypes, locations, applyNum, prices, LNOs, UNOs, connection, getLands);
        } catch (SQLException e) {
            System.out.println(e.getErrorCode());
        }
    }

    private void getLandInfo(Vector<String> LTypes,
                             Vector<String> TTypes,
                             Vector<String> locations,
                             Vector<Integer> applyNum,
                             Vector<Integer> prices,
                             Vector<Integer> LNOs,
                             Vector<Integer> UNOs,
                             Connection connection,
                             PreparedStatement getLands) throws SQLException {
        ResultSet re = getLands.executeQuery();
        while (re.next()) {
            int lno = re.getInt(1);
            int price = re.getInt(6);
            int uno = re.getInt(2);
            String lType = re.getString(3);
            String tType = re.getString(4);
            String location = re.getString(5);
            PreparedStatement getAllApplies = connection.prepareStatement("SELECT COUNT(*) FROM apply WHERE LNO = ?;");
            getAllApplies.setInt(1, lno);
            ResultSet tempApplyNum = getAllApplies.executeQuery();
            tempApplyNum.next();
            int num = tempApplyNum.getInt(1);
            applyNum.addElement(num);
            prices.addElement(price);
            LTypes.addElement(lType);
            TTypes.addElement(tType);
            locations.addElement(location);
            LNOs.addElement(lno);
            UNOs.addElement(uno);
        }
    }

    public void submitApply(int LNO, int UNO1, int UNO2) {
        Connection connection = this.GetConnection();
        try {
            PreparedStatement statement = connection.prepareStatement("UPDATE APPLY SET STATE = '通过' WHERE LNO = ? AND APPLICANT_UNO = ? AND UNO = ?;");
            statement.setInt(1, LNO);
            statement.setInt(2, UNO1);
            statement.setInt(3, UNO2);
            statement.execute();
        } catch (SQLException e) {
            System.out.println(e.getErrorCode());
        }
    }

    public void getTarLands(String tarLand,
                            String tarTrans,
                            String tarLocation,
                            Vector<String> LTypes,
                            Vector<String> TTypes,
                            Vector<String> locations,
                            Vector<Integer> applyNum,
                            Vector<Integer> prices,
                            Vector<Integer> LNOs,
                            Vector<Integer> UNOs) {
        Connection connection = this.GetConnection();
        try {
            PreparedStatement getLands = connection.prepareStatement("SELECT * FROM land WHERE LTYPE = ? AND TTYPE = ? AND LOCATION = ?;");
            getLands.setString(1, tarLand);
            getLands.setString(2, tarTrans);
            getLands.setString(3, tarLocation);
            getLandInfo(LTypes, TTypes, locations, applyNum, prices, LNOs, UNOs, connection, getLands);
            System.out.println(LNOs.size());
        } catch (SQLException e) {
            System.out.println(e.getErrorCode());
        }
    }
}
