package com.leader.servlets;

import com.leader.beans.Category;
import com.leader.db.DBController;
import com.leader.db.MysqlDB;
import com.leader.settings.DBSettings;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by ruki on 09.07.2014.
 */
@WebFilter(filterName = "LeftMenuFilter", urlPatterns = "/*")
public class LeftMenuFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        System.out.println("leftMenuFilter");
        int categoryID = getCategoryIdFromURL(request);
        List<Category> categories = getCategoriesList();
        request.setAttribute("categories",categories);
        chain.doFilter(req, resp);
    }

    private String getCategoriesQuery(){
        return "SELECT * FROM "+DBSettings.CATEGORY_TABLE_NAME+" ";
    }

    private List<Category> getCategoriesList(){

        List<Category> categories = new ArrayList<Category>();
        ResultSet resultSet = getCategoriesResultSet();

        try {
            List<Category> temp = new CopyOnWriteArrayList<Category>();
            while (resultSet.next()){
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int parentID = resultSet.getInt("parent");
                Category category = new Category(id,name,parentID);

                if(parentID > 0){
                    temp.add(category);
                }else{
                    categories.add(category);
                }
            }

            findParent(categories,temp);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return categories;
    }

    private void findParent(List<Category> parents, List<Category> children){
        int counter = 0;
        while (children.size() > 0){
            for (Category subcategory: children){
                for (Category category:parents){
                    if(category.addSubcategory(subcategory)){
                        children.remove(subcategory);
                        break;
                    }
                }
            }
            counter++;
            if( counter >= 100 ){
                System.err.println(""+this.getClass().getName());
                System.err.println("parent");
                System.err.println(parents + "\n");
                System.err.println("child");
                System.err.println(children + "\n");
                break;
            }
        }
        System.out.println(counter);
    }

    private ResultSet getCategoriesResultSet(){
        Connection connection = getConnection();
        try {
            return performDBQuery(getCategoriesQuery(), connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Connection getConnection() {
        DBController db = new MysqlDB(DBSettings.DB_NAME, "root", "root", DBSettings.DB_URL, DBSettings.DB_PORT);//TODO create another user not root
        return db.getConnection();
    }

    private ResultSet performDBQuery(String query, Connection connection) throws SQLException {
        Statement stmt = connection.createStatement();
        return stmt.executeQuery(query);
    }

    private int getCategoryIdFromURL(HttpServletRequest request){
        String stringId = request.getParameter(Category.CATEGORY_PARAM_NAME);
        Map params = request.getParameterMap();
        Object category = params.get(Category.CATEGORY_PARAM_NAME);
        if(category != null){
            try {
                return Integer.parseInt(stringId);
            }catch (Exception e){
                System.err.println("incorrect category id ("+this.getClass().getName()+")");
                return -1;
            }
        }

        return -1;
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
