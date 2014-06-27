package com.leader.servlets;

import com.leader.beans.Site;
import com.leader.db.DBController;
import com.leader.db.MysqlDB;
import com.leader.settings.DBSettings;
import com.leader.settings.TemplateSettings;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@WebServlet("")
public class HomeServlet extends HttpServlet {

    private int userID;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        userID = getUserID(request);
        ResponseProcessor responseProcessor = responseProcessorFactory(request);
        try {
            List<Site> sites = responseProcessor.getListsOfSites();
            request.setAttribute("page", Page.page);
            request.setAttribute("pageType", Page.LIST_PAGE);
            request.setAttribute("sites", sites);
            request.getRequestDispatcher(TemplateSettings.TEMPLATE_FILE_PATH).forward(request, response);
        }catch (SQLException e){
            // TODO error page
            e.printStackTrace();
        }
    }

    private int getUserID(HttpServletRequest request){
        HttpSession session = request.getSession(false);
        return Integer.parseInt(session.getAttribute(UserFilter.SESSION_USER_ID_KEY).toString());
    }

    private ResponseProcessor responseProcessorFactory(HttpServletRequest request) {
        Map parameters = request.getParameterMap();

        if (parameters.size() > 0) {
            return new CategoryProcessor(request);
        }

        return new ListItemsProcessor(request);
    }

    private abstract class ResponseProcessor {

        protected static final int MAX_ITEMS_PER_PAGE = 10;

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

        List<Site> getListsOfSites() throws SQLException {

            List<Site> sites = new ArrayList<Site>();

            Connection connection = getConnection();

            ResultSet resultSet = performDBQuery(getQuery(), connection);
            while (resultSet.next()) {

                Site site = new Site();
                site.setId(resultSet.getInt("Id"));
                site.setName(resultSet.getString("name"));
                site.setRating(resultSet.getDouble("rating"));
                site.setVotes(resultSet.getInt("votes"));

                Double userScore =  resultSet.getDouble("score");
                userScore = (userScore == null)? 0 : userScore;
                site.setCurrentUserRating(userScore);

                Object description = resultSet.getObject("short_description");
                if (description != null) {
                    site.setShortDescription(description.toString());
                } else {
                    site.setShortDescription("no description yet");
                }

                site.setPictureURL(resultSet.getString("picture"));

                sites.add(site);
            }

            connection.close();

            return sites;
        }
    }

    private class CategoryProcessor extends ResponseProcessor {

        private static final String CATEGORY_URL_PARAM_NAME = "category";
        private String query;

        public CategoryProcessor(HttpServletRequest request) {
            super(request);

            query = createQuery(parseCategoryIdFromURL(request));
        }

        private int parseCategoryIdFromURL(HttpServletRequest request){
            String stringId = request.getParameter(CATEGORY_URL_PARAM_NAME);
            return Integer.parseInt(stringId);//TODO null pointer
        }

        private String createQuery(int categoryId) {
            /*


    select user_site.* from user_site where user_site.user_id=97
) t1
right join sites on t1.site_id=sites.id
left  JOIN category_site ON category_site.site_id = sites.Id
    where category_site.category_id = 3 ;

             */
            String query =
                    "SELECT " +
                            "t1.score, " +
                            DBSettings.SITES_TABLE_NAME+".* " +
                            "FROM " +
                            "(" +
                            "    SELECT "+DBSettings.USER_SITE_TABLE_NAME+".* " +
                            "FROM " +DBSettings.USER_SITE_TABLE_NAME+" "+
                            "where "+DBSettings.USER_SITE_TABLE_NAME+".user_id=" +userID+
                            ") t1 " +
                            "RIGHT JOIN "+DBSettings.SITES_TABLE_NAME+" ON t1.site_id="+DBSettings.SITES_TABLE_NAME+".id " +
                            "left  JOIN "+DBSettings.CATEGORY_SITE_TABLE_NAME+" ON "+DBSettings.CATEGORY_SITE_TABLE_NAME+".site_id = "+DBSettings.SITES_TABLE_NAME+".Id " +
                            "where "+DBSettings.CATEGORY_SITE_TABLE_NAME+".category_id = "+categoryId+" "+
                            "ORDER BY rating DESC LIMIT "+MAX_ITEMS_PER_PAGE;
            return query;
        }

        @Override
        protected String getQuery() {
            return query;
        }
    }

    private class ListItemsProcessor extends ResponseProcessor {

        private String query;

        public ListItemsProcessor(HttpServletRequest request) {
            super(request);
            query = createQuery();
        }

        private String createQuery() {
            String query =
                    "SELECT " +
                            "t1.score, " +
                            DBSettings.SITES_TABLE_NAME+".* " +
                    "FROM " +
                    "(" +
                    "    SELECT "+DBSettings.USER_SITE_TABLE_NAME+".* " +
                         "FROM " +DBSettings.USER_SITE_TABLE_NAME+" "+
                         "where "+DBSettings.USER_SITE_TABLE_NAME+".user_id=" +userID+
                    ") t1 " +
                    "RIGHT JOIN "+DBSettings.SITES_TABLE_NAME+" ON t1.site_id="+DBSettings.SITES_TABLE_NAME+".id " +
                    "ORDER BY rating DESC LIMIT "+MAX_ITEMS_PER_PAGE;
            return query;
        }

        @Override
        protected String getQuery() {
            return query;
        }
    }
}
