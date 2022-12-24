package database;

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
//                System.out.println(re);
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
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM USER WHERE UNAME = ?;");
            statement.setString(1, userName);
            ResultSet re = statement.executeQuery();
            if (re.next()) {
                return false;
            } else {
                PreparedStatement insert = connection.prepareStatement("INSERT INTO user (UNAME, PASSWORD) VALUES (?, ?);");
                insert.setString(1, userName);
                insert.setInt(2, Integer.parseInt(pwd));
//                System.out.println(insert);
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
            ResultSet numRe = getLandNum.executeQuery("SELECT MAX(LNO) FROM land;");
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
            System.out.println(e.getSQLState());
            System.out.println(e.getMessage());
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
//            System.out.println("Enter get Land Info");
            ResultSet re = getAllLands.executeQuery();
            while (re.next()) {
                int lno = re.getInt(1);
                PreparedStatement getAllApplies = connection.prepareStatement("SELECT COUNT(*) FROM apply WHERE LNO = ?;");
                getAllApplies.setInt(1, lno);
                ResultSet tempApplyNum = getAllApplies.executeQuery();
                tempApplyNum.next();
                int num = tempApplyNum.getInt(1);
                LNOs.addElement(re.getInt(1));
                LTypes.addElement(re.getString(2));
                TTypes.addElement(re.getString(3));
                prices.addElement(re.getInt(4));
                locations.addElement(re.getString(5));
                applyNum.addElement(num);
            }
//            System.out.println("Exit get land info");
//            getLandInfo(LTypes, TTypes, locations, applyNum, prices, LNOs, UNOs, connection, getAllLands);
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
                            Vector<Integer> UNOs,
                            int uno) {
        Connection connection = this.GetConnection();
        try {
            PreparedStatement getLands = connection.prepareStatement("SELECT * FROM land WHERE land.UNO != ?;");
            getLands.setInt(1, uno);
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
        System.out.println("Enter get Land Info");
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
//            System.out.println("The size is " + LTypes.size());
        }
        System.out.println("Exit get land info");
    }

    public void submitApply(int LNO, int UNO1, int UNO2) {
        Connection connection = this.GetConnection();
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO apply (LNO, APPLICANT_UNO, STATE) VALUES (?, ?, '审核');");
            statement.setInt(1, LNO);
            statement.setInt(2, UNO1);
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

    public void getMyApply(int uno,
                           Vector<String> LTypes,
                           Vector<String> TTypes,
                           Vector<String> locations,
                           Vector<String> state,
                           Vector<Integer> prices,
                           Vector<Integer> LNOs,
                           Vector<Integer> UNOs) {
        Connection connection = this.GetConnection();
        try {
            PreparedStatement getLands = connection.prepareStatement("SELECT * FROM APPLY, land WHERE APPLICANT_UNO = ? AND APPLY.LNO = land.LNO;");
            getLands.setInt(1, uno);
            ResultSet re = getLands.executeQuery();
            while (re.next()) {
                LNOs.addElement(re.getInt(1));
                state.addElement(re.getString(3));
                UNOs.addElement(re.getInt(4));
                LTypes.addElement(re.getString(6));
                TTypes.addElement(re.getString(7));
                locations.addElement(re.getString(8));
                prices.addElement(re.getInt(9));
            }
            System.out.println(LNOs.size());
        } catch (SQLException e) {
            System.out.println(e.getErrorCode());
        }
    }

    public void withdrawApply(int uno1, int lno) {
        Connection connection = this.GetConnection();
        try {
            PreparedStatement withdraw = connection.prepareStatement("DELETE FROM apply WHERE APPLICANT_UNO = ? AND LNO = ?;");
            withdraw.setInt(1, uno1);
            withdraw.setInt(2, lno);
            withdraw.execute();
        } catch (SQLException e) {
            System.out.println(e.getErrorCode());
        }
    }

    public void getReceiveApplies(int uno,
                                  Vector<String> LTypes,
                                  Vector<String> TTypes,
                                  Vector<String> locations,
                                  Vector<String> userName,
                                  Vector<Integer> prices,
                                  Vector<Integer> LNOs,
                                  Vector<Integer> UNOs){
        Connection connection = this.GetConnection();
        try {
            PreparedStatement getInfo = connection.prepareStatement("SELECT * FROM APPLY, land, user WHERE land.LNO = APPLY.LNO AND APPLICANT_UNO = user.UNO AND land.UNO = ? AND apply.STATE = '审核';");
            getInfo.setInt(1, uno);
            ResultSet re = getInfo.executeQuery();
            while (re.next()) {
                LNOs.addElement(re.getInt(1));
                LTypes.addElement(re.getString(6));
                TTypes.addElement(re.getString(7));
                locations.addElement(re.getString(8));
                prices.addElement(re.getInt(9));
                userName.addElement(re.getString(11));
                UNOs.addElement(re.getInt(10));
            }
        } catch (SQLException e) {
            System.out.println(e.getErrorCode());
        }
    }

    public void changeState(int uno, int lno, String state) {
        Connection connection = this.GetConnection();
        try {
            if (state.equals("通过")) {
                PreparedStatement change = connection.prepareStatement("CALL change_state(?, ?)");
                change.setInt(1, uno);
                change.setInt(2, lno);
                change.execute();
                return;
            }
            PreparedStatement update = connection.prepareStatement("UPDATE apply SET STATE = ? WHERE APPLICANT_UNO = ? AND LNO = ?");
            update.setString(1, state);
            update.setInt(2, uno);
            update.setInt(3, lno);
            update.execute();
        } catch (SQLException e) {
            System.out.println(e.getErrorCode());
        }
    }
}
