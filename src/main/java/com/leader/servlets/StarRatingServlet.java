package com.leader.servlets;

import com.leader.db.DBController;
import com.leader.db.MysqlDB;
import com.leader.settings.DBSettings;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.LinkedHashMap;
import java.util.Map;


@WebServlet("/rating")
public class StarRatingServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }



    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        response.setContentType("application/json");
        response.setHeader("Cache-Control", "nocache");
        response.setCharacterEncoding("utf-8");
        PrintWriter out = response.getWriter();

        int siteID = Integer.parseInt(request.getParameter("id"));
        double score = Integer.parseInt(request.getParameter("score"));

        int userID = getUserID(request);

        double newScore = vote(userID, siteID, score);
        int voteDelta = 1;

        if(newScore<0){
            newScore*=-1;
            voteDelta = 0;
        }

        newScore = (double)Math.round(newScore * 100) / 100;


        Map<String, String> jsonMap = new LinkedHashMap<String, String>();
        jsonMap.put("status", "OK");
        jsonMap.put("score", ""+newScore);
        jsonMap.put("votes", ""+voteDelta);


        JSONObject jsonObject = new JSONObject(jsonMap);

        out.print(jsonObject);
    }

    private synchronized double vote(int userID, int siteID, double score){

        String query = "{?= call vote(?,?,?)}";
        try {
            Connection connection = getConnection();

            CallableStatement callableStatement = connection.prepareCall(query);
            callableStatement.registerOutParameter(1, java.sql.Types.DOUBLE);
            callableStatement.setInt(2, siteID);
            callableStatement.setInt(3, userID);
            callableStatement.setDouble(4, score);

            callableStatement.executeUpdate();

            return callableStatement.getDouble(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1.0;
    }

    private Connection getConnection() {
        DBController db = new MysqlDB(DBSettings.DB_NAME, "root", "root", DBSettings.DB_URL, DBSettings.DB_PORT);//TODO create another user not root
        return db.getConnection();
    }

    private int getUserID(HttpServletRequest request){
        HttpSession session = request.getSession(false);
        return Integer.parseInt(session.getAttribute(UserFilter.SESSION_USER_ID_KEY).toString());
    }

}
