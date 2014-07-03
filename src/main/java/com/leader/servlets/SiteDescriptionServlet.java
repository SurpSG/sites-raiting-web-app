package com.leader.servlets;

import com.leader.bean_processor.SiteBeanProcessor;
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
import java.util.ArrayList;
import java.util.List;


@WebServlet("/site")
public class SiteDescriptionServlet extends HttpServlet {

    private int userID;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {}

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        userID = Integer.parseInt(request.getAttribute(UserFilter.REQUEST_USER_ID_KEY).toString());
        ResponseProcessor responseProcessor = responseProcessorFactory(request);
        try {
            String query = responseProcessor.getQuery();
            List<Site> sites = SiteBeanProcessor.getSiteBean(query);
            request.setAttribute("page", Page.page);
            request.setAttribute("pageType", Page.SITE_INFO_PAGE);
            request.setAttribute("sites", sites);
            request.getRequestDispatcher(TemplateSettings.TEMPLATE_FILE_PATH).forward(request, response);
        }catch (SQLException e){
            // TODO error page
            e.printStackTrace();
        }
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
            String query ="select t1.score,sites.*\n" +
                    "from\n" +
                    "(\n" +
                    "    select user_site.* from user_site where user_site.user_id="+userID+"\n" +
                    ") t1\n" +
                    "right join sites on t1.site_id=sites.id where sites.id = "+siteId;
            return query;
        }

        @Override
        protected String getQuery() {
            return query;
        }
    }
}
