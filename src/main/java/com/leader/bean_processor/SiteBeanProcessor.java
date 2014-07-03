package com.leader.bean_processor;

import com.leader.beans.Site;
import com.leader.db.DBController;
import com.leader.db.MysqlDB;
import com.leader.servlets.UserFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ruki on 02.07.2014.
 */
public class SiteBeanProcessor {

    private static List<Site> getSites(String query, boolean all) throws SQLException {

        List<Site> sites = new ArrayList<Site>();

        Connection connection = getConnection();

        ResultSet resultSet = performDBQuery(query, connection);
        while (resultSet.next()) {

            Site site = new Site();
            site.setId(resultSet.getInt("Id"));
            site.setName(resultSet.getString("name"));
            site.setUrl(resultSet.getString("url"));
            site.setRating(resultSet.getDouble("rating"));
            site.setVotes(resultSet.getInt("votes"));

            Double userScore =  resultSet.getDouble("score");
            userScore = (userScore == null)? 0 : userScore;
            site.setCurrentUserRating(userScore);

            if(all){
                Object description = resultSet.getObject("short_description");
                if (description != null) {
                    site.setShortDescription(description.toString());
                } else {
                    site.setShortDescription("no description yet");
                }
            }else{
                Object description = resultSet.getObject("description");
                if (description != null) {
                    site.setDescription(description.toString());
                } else {
                    site.setDescription("no description yet");
                }
            }

            site.setPictureURL(resultSet.getString("picture"));

            sites.add(site);

            if(!all){
                break;
            }
        }

        connection.close();

        return sites;
    }

    public static List<Site> getSiteBean(String query) throws SQLException {
         return getSites(query,false);
    }

    public static List<Site> getSiteBeans(String query) throws SQLException {
       return getSites(query,true);
    }

    /**
     * @param query query to execute
     * @param connection connection object to database
     * @return result set
     */
    protected static ResultSet performDBQuery(String query, Connection connection) throws SQLException {
        Statement stmt = connection.createStatement();
        return stmt.executeQuery(query);
    }

    /**
     * @return connection according to database
     */
    private static Connection getConnection() {
        DBController db = new MysqlDB("leaderdb", "root", "root", "127.0.0.1", 3307);
        return db.getConnection();
    }

}
