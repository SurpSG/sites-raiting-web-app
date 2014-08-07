package com.leader.servlets;

import com.leader.bean_processor.SiteBeanProcessor;
import com.leader.beans.Category;
import com.leader.beans.Site;
import com.leader.settings.DBSettings;
import com.leader.settings.TemplateSettings;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;


@WebServlet("")
public class HomeServlet extends HttpServlet {

    private int userID;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        userID = Integer.parseInt(request.getAttribute(UserFilter.REQUEST_USER_ID_KEY).toString());
        DefaultResponseProcessor responseProcessor = responseProcessorFactory(request);
        try {
            String query = responseProcessor.getQuery();
            List<Site> sites = SiteBeanProcessor.getSiteBeans(query);
            request.setAttribute("page", Page.page);
            request.setAttribute("pageType", Page.LIST_PAGE);
            request.setAttribute("sites", sites);

            int pagesNumber = responseProcessor.calculatePagesNumber();
            request.setAttribute("page_number", pagesNumber);
            request.getRequestDispatcher(TemplateSettings.TEMPLATE_FILE_PATH).forward(request, response);
        }catch (SQLException e){
            // TODO error page
            e.printStackTrace();
        }
    }

    private DefaultResponseProcessor responseProcessorFactory(HttpServletRequest request) {

        Map parameters =request.getParameterMap();
        if (parameters.get(Category.CATEGORY_PARAM_NAME)!=null) {
            return new CategoryProcessor(request);
        }

        return new DefaultResponseProcessor(request);
    }

    private class DefaultResponseProcessor {

        protected static final int MAX_ITEMS_PER_PAGE = 2;

        protected static final String PAGE_URL_PARAM_NAME = "page";

        protected HttpServletRequest request;

        public DefaultResponseProcessor(HttpServletRequest request) {
            this.request = request;
        }

        /**
         * @return query according to GET parameters
         */
        protected String getQuery() {
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
                    "ORDER BY rating DESC LIMIT " + getStartItemNumber() + "," + MAX_ITEMS_PER_PAGE;
            return query;
        }

        public String getSitesNumberQuery() {
            String query = "SELECT COUNT(*) FROM " +DBSettings.SITES_TABLE_NAME;
            return query;
        }

        public int calculatePagesNumber(){
            int sitesNumber = SiteBeanProcessor.getSitesNumber(getSitesNumberQuery());
            return (int) Math.ceil((double)sitesNumber / MAX_ITEMS_PER_PAGE);
        }


        private int parsePageFromURL(HttpServletRequest request){
            String page = request.getParameter(PAGE_URL_PARAM_NAME);

            if(page != null){
                try {
                    return Integer.parseInt(page);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            return 1;

        }

        protected int getStartItemNumber(){

            int page = parsePageFromURL(request);

            return (page-1)* MAX_ITEMS_PER_PAGE;
        }

    }

    /**
     *
     */
    private class CategoryProcessor extends DefaultResponseProcessor {

        private String sitesQuery;
        private String sitesNumberQuery;
        public CategoryProcessor(HttpServletRequest request) {
            super(request);

            createQueries();
        }

        private int parseCategoryIdFromURL(HttpServletRequest request){
            String stringId = request.getParameter(Category.CATEGORY_PARAM_NAME);
            try {
                int categoryID =Integer.parseInt(stringId);
                if(categoryID > 0){
                    request.setAttribute(Category.CATEGORY_PARAM_NAME, "&"+Category.CATEGORY_PARAM_NAME+"="+categoryID);
                    return categoryID;
                }else{
                    throw new Exception("invalid category ID in URL");
                }
            }catch (Exception e){
                e.printStackTrace();
                return -1;
            }
        }

        private void createQueries() {
            int categoryID = parseCategoryIdFromURL(request);
            if(categoryID>0){
                sitesQuery = createQueryForSites(categoryID);
                sitesNumberQuery = createQueryForSitesNumber(categoryID);
            }else{
                sitesQuery = super.getQuery();
                sitesNumberQuery = super.getSitesNumberQuery();
            }
        }

        private String createQueryForSites(int categoryID){
            return  "SELECT " +
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
                    "where "+DBSettings.CATEGORY_SITE_TABLE_NAME+".category_id = "+categoryID+" "+
                    "ORDER BY rating DESC LIMIT " + getStartItemNumber() + "," +MAX_ITEMS_PER_PAGE;
        }

        private String createQueryForSitesNumber(int categoryID){
            return "SELECT COUNT(*) FROM \n" +
                    "(\n" +
                    "       select "+DBSettings.SITES_TABLE_NAME+".* from "+DBSettings.SITES_TABLE_NAME+"\n" +
                    "       left join "+DBSettings.CATEGORY_SITE_TABLE_NAME+" on sites.id = "+DBSettings.CATEGORY_SITE_TABLE_NAME+".site_id\n" +
                    "       where "+DBSettings.CATEGORY_SITE_TABLE_NAME+".category_id = "+categoryID+"\n" +
                    ") t1;";
        }

        @Override
        public String getQuery(){
            return sitesQuery;
        }

        @Override
        public String getSitesNumberQuery(){
            return sitesNumberQuery;
        }
    }
}
