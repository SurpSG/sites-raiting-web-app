package com.leader.beans;

import com.leader.db.DBController;
import com.leader.db.MysqlDB;
import com.leader.settings.DBSettings;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by ruki on 23.06.2014.
 */
public class User {

    private int id;
    private String ip;

    public User(String ip) {
        this.ip = ip;
        Connection connection = getConnection();
        initUser(connection, ip);

        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getId() {
        return id;
    }

    public String getIp() {
        return ip;
    }

    public void setId(int id) {
        this.id = id;
    }

    private void initUser(Connection connection, String userIP){
        addUser(connection, userIP);
        id = getUserIdByIP(connection, userIP);

    }

    private int getUserIdByIP(Connection connection, String userIP){

        String query = "SELECT Id FROM "+ DBSettings.USERS_TABLE_NAME+" WHERE `ip`='"+userIP+"'";

        try {
            ResultSet resultSet = performDBQuery(query,connection);
            resultSet.next();
            Integer userID = resultSet.getInt("Id");

            if(userID != null){
                return userID;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1;
    }

    private void addUser(Connection connection, String userIP){
        String query = "INSERT IGNORE INTO `"+ DBSettings.USERS_TABLE_NAME+"` (`ip`) VALUES('"+userIP+"')";
        try {
            Statement stmt = connection.createStatement();
            stmt.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    private ResultSet performDBQuery(String query, Connection connection) throws SQLException {
        Statement stmt = connection.createStatement();
        return stmt.executeQuery(query);
    }

    public static Connection getConnection() {
        DBController db = new MysqlDB(DBSettings.DB_NAME, "root", "root", DBSettings.DB_URL, DBSettings.DB_PORT);//TODO create another user not root
        return db.getConnection();
    }
}
