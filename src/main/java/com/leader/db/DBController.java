package com.leader.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by ruki on 24.04.2014.
 */
public abstract class DBController {

    private String dbName;

    protected DBController(String dbName) {
        this.dbName = dbName;

        try {
            Class.forName(getDriverName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    protected abstract String getDriverName();

    protected abstract String getRDBMSName();

    protected abstract String getURL();

    protected abstract int getPort();

    protected abstract String getUser();

    protected abstract String getPassword();

    public Connection getConnection() {

        try {
            String url;
            if(Integer.toString(getPort()).length() > 0){
                url = String.format("jdbc:%s://%s:%s/%s",getRDBMSName(),getURL(),getPort(),dbName);
            }else{
                url = String.format("jdbc:%s://%s/%s",getRDBMSName(),getURL(),dbName);
            }

            if(getUser().length()>0){
                return DriverManager.getConnection(url, getUser(), getPassword());
            }else{
                return DriverManager.getConnection(url);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
