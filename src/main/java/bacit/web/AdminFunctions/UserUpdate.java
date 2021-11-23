package bacit.web.AdminFunctions;

import bacit.web.Modules.UserModel;
import bacit.web.utils.DBUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet(name = "UserUpdate", value = "/userUpdate")
public class UserUpdate extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // TODO: 23.11.2021 set JSP
        try {
            req.setAttribute("userDetailsOld",getUserDetailsOld("1"));
            req.getRequestDispatcher("UserUpdate.jsp").forward(req,resp);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            // TODO: 23.11.2021 set this upp
            UserModel uUser = compareNewToOldUserDetails(
                    getUserDetailsNew(req),
                    getUserDetailsOld(req.getParameter("user")));
            updateDB(uUser);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    UserModel getUserDetailsNew(HttpServletRequest req) {
        UserModel newUser = new UserModel();

        newUser.setEmail(req.getParameter("email"));
        newUser.setFirstname(req.getParameter("fname"));
        newUser.setLastname(req.getParameter("lname"));
        newUser.setPhoneNumber(req.getParameter("nmbr"));
        newUser.setUnionMember(Boolean.parseBoolean(req.getParameter("union")));
        newUser.setUserAdmin(Boolean.parseBoolean(req.getParameter("admin")));

        return newUser;
    }

    UserModel getUserDetailsOld(String userID) throws SQLException {
        UserModel oldUser = new UserModel();

        Connection db = DBUtils.getNoErrorConnection();
        PreparedStatement statement = db.prepareStatement(
                "select * from AMVUser where userID=?");
        statement.setString(1, userID);
        ResultSet rs1 = statement.executeQuery();

        if (rs1.next()){
            oldUser.setEmail(rs1.getString("email"));
            oldUser.setFirstname(rs1.getString("firstName"));
            oldUser.setLastname(rs1.getString("lastName"));
            oldUser.setPhoneNumber(rs1.getString("phoneNumber"));
            oldUser.setUnionMember(rs1.getBoolean("unionMember"));
            oldUser.setUserAdmin(rs1.getBoolean("userAdmin"));
        }

        statement.close();
        db.close();

        return oldUser;
    }

    UserModel compareNewToOldUserDetails(UserModel newUser, UserModel oldUser){
        UserModel updatedUser = new UserModel();
        //will load the default value into the html prob...
        // EMAIL
        if ((newUser.getEmail().equals("")) || newUser.getEmail().equals(oldUser.getEmail()) || newUser.getEmail()==null){
            updatedUser.setEmail(oldUser.getEmail());
        } else {
            updatedUser.setEmail(newUser.getEmail());
        }
        //FN
        //LN
        //NMBR
        //UNION
        //ADMIN

        return updatedUser;
    }

    void updateDB(UserModel user) throws SQLException {
        Connection db = DBUtils.getNoErrorConnection();
        changeEmail(user,db);
        //insert the rest once tested

    }


    void changeEmail(UserModel uUser, Connection db) throws SQLException {

        PreparedStatement st2 = db
                .prepareStatement("UPDATE AMVUser SET email = ? WHERE userID = ?");
        st2.setString(1, uUser.getEmail());
        st2.setInt(2, uUser.getUserID());
        st2.executeUpdate();

    }

    void changePW(){}
    void changeFName(){}
    void changeLName(){}
    void changePhone(){}
    void changeUnion(){}
    void changeAdmin(){}

    void addCertificate(){}
}
