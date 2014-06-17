package com.leader.servlets;

import com.leader.beans.Site;
import com.leader.db.DBController;
import com.leader.db.MysqlDB;
import com.leader.settings.TemplateSettings;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;


@WebServlet("/rating")
public class StarRatingServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.getRemoteUser();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        request.getRequestDispatcher("template/index.html").forward(request, response);
    }

    private ResponseProcessor responseProcessorFactory(HttpServletRequest request) {
//        Map parameters = request.getParameterMap();
        return new SiteDescriptionProcessor(request);
    }

    private abstract class ResponseProcessor {

        protected HttpServletRequest request;

        public ResponseProcessor(HttpServletRequest request) {
            this.request = request;
        }

        /**
         * @return query according to GET parameters
         */
        protected abstract String getQuery();

        /**
         * @return connection according to database
         */
        private Connection getConnection() {
            DBController db = new MysqlDB("leaderdb", "root", "root", "127.0.0.1", 3307);
            return db.getConnection();
        }

        /**
         * @param query query to execute
         * @param connection connection object to database
         * @return result set
         */
        protected ResultSet performDBQuery(String query, Connection connection) throws SQLException {
            Statement stmt = connection.createStatement();
            return stmt.executeQuery(query);
        }

        Site getSiteBean() throws SQLException {

            Site site = new Site();

            Connection connection = getConnection();

            ResultSet resultSet = performDBQuery(getQuery(), connection);
            while (resultSet.next()) {

                site.setId(resultSet.getInt("Id"));
                site.setName(resultSet.getString("name"));
                site.setRating(resultSet.getDouble("rating"));

                Object description = resultSet.getObject("description");
                if (description != null) {
                    site.setDescription(description.toString());
                } else {
                    site.setDescription("no description");
                }

                site.setPictureURL(resultSet.getString("picture"));

                site.setUrl(resultSet.getString("url"));

                break;//i need only one site bean
            }

            connection.close();

            return site;
        }
    }

    private class SiteDescriptionProcessor extends ResponseProcessor {

        private static final String ID_PARAM_NAME = "id";
        private String query;

        public SiteDescriptionProcessor(HttpServletRequest request) {
            super(request);

            query = createQuery(parseSiteIdFromURL(request));
        }

        private int parseSiteIdFromURL(HttpServletRequest request){
            String stringId = request.getParameter(ID_PARAM_NAME);
            return Integer.parseInt(stringId);//TODO null pointer
        }

        private String createQuery(int siteId) {
            String query ="SELECT sites.*" +
                    " FROM sites" +
                    " where id = "+siteId;
            return query;
        }

        @Override
        protected String getQuery() {
            return query;
        }
    }
}
