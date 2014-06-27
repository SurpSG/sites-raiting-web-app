package com.leader.db;

import com.leader.settings.DBSettings;

import java.sql.Connection;

/**
 * Created by ruki on 24.04.2014.
 */
public class MysqlDB extends DBController{

    public static final String DRIVER_NAME="com.mysql.jdbc.Driver";
    public static final String RDBMS_NAME="mysql";

    private String url;
    private int port;
    private String user;
    private String password;

    public MysqlDB(String dbName, String user, String password, String url, int port) {
        super(dbName);
        this.url = url;
        this.port = port;
        this.user = user;
        this.password = password;
    }

    @Override
    protected String getDriverName() {
        return DRIVER_NAME;
    }

    @Override
    protected String getRDBMSName() {
        return RDBMS_NAME;
    }

    @Override
    protected String getURL() {
        return url;
    }

    @Override
    protected int getPort() {
        return port;
    }

    @Override
    protected String getUser() {
        return user;
    }

    @Override
    protected String getPassword() {
        return password;
    }

}
