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
public class UserUpdate_v2 extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            // TODO: 24.11.2021 change userid
            req.setAttribute("userDetailsOld",getUserDetailsOld(1));
            req.getRequestDispatcher("UserUpdate.jsp").forward(req,resp);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Connection db = DBUtils.getNoErrorConnection();
            int userID = 1;
            updateDB(req,db,userID);
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    UserModel getUserDetailsOld(int userID) throws SQLException {
        UserModel oldUser = new UserModel();

        Connection db = DBUtils.getNoErrorConnection();
        PreparedStatement st = db.prepareStatement(
                "select * from AMVUser where userID=?");
         st.setInt(1, userID);
        ResultSet rs1 = st.executeQuery();

        if (rs1.next()){
            oldUser.setEmail(rs1.getString("email"));
            oldUser.setFirstname(rs1.getString("firstName"));
            oldUser.setLastname(rs1.getString("lastName"));
            oldUser.setPhoneNumber(rs1.getString("phoneNumber"));
            oldUser.setUnionMember(rs1.getBoolean("unionMember"));
            oldUser.setUserAdmin(rs1.getBoolean("userAdmin"));
        }

        st.close();
        db.close();

        return oldUser;
    }
    void updateDB(HttpServletRequest req, Connection db, int userID) throws SQLException {
        if (req.getParameter("email") != null && !req.getParameter("email").equals("")) {
            changeEmail(req.getParameter("email"), db, userID);
        }
        if (req.getParameter("fname") != null && !req.getParameter("fname").equals("")) {
            changeFName(req.getParameter("fname"), db, userID);
        }
        if (req.getParameter("lname") != null && !req.getParameter("lname").equals("")) {
            changeLName(req.getParameter("lname"), db, userID);
        }
        if (req.getParameter("nmbr") != null && !req.getParameter("nmbr").equals("")) {
            changePhone(req.getParameter("nmbr"), db, userID);
        }
        if (req.getParameter("union") != null){
            changeUnion(Boolean.parseBoolean(req.getParameter("union")), db, userID);
        }
        if (req.getParameter("admin") != null){
            changeAdmin(Boolean.parseBoolean(req.getParameter("admin")), db, userID);
        }
    }
    
    void changeEmail(String email, Connection db, int userID) throws SQLException {
        PreparedStatement st = db.prepareStatement(
                "UPDATE AMVUser SET email = ? WHERE userID = ?");
        st.setString(1, email);
        st.setInt(2, userID);
        st.executeUpdate();
    }

    void changeFName(String fname, Connection db, int userID) throws SQLException{
        PreparedStatement st = db.prepareStatement(
                "UPDATE AMVUser SET firstName = ? WHERE userID = ?");
        st.setString(1, fname);
        st.setInt(2, userID);
        st.executeUpdate();
    }

    void changeLName(String lname, Connection db, int userID) throws SQLException{
        PreparedStatement st = db.prepareStatement(
                "UPDATE AMVUser SET lastName = ? WHERE userID = ?");
        st.setString(1, lname);
        st.setInt(2, userID);
        st.executeUpdate();
    }

    void changePhone(String nmbr, Connection db, int userID) throws SQLException{
        PreparedStatement st = db.prepareStatement(
                "UPDATE AMVUser SET phoneNumber = ? WHERE userID = ?");
        st.setString(1, nmbr);
        st.setInt(2, userID);
        st.executeUpdate();
    }

    void changeUnion(boolean union, Connection db, int userID) throws SQLException {
        PreparedStatement st = db.prepareStatement(
                "UPDATE AMVUser SET unionMember = ? WHERE userID = ?");
        st.setBoolean(1, union);
        st.setInt(2, userID);
        st.executeUpdate();
    }
    void changeAdmin(boolean admin, Connection db, int userID) throws SQLException {
        PreparedStatement st = db.prepareStatement(
                "UPDATE AMVUser SET userAdmin = ? WHERE userID = ?");
        st.setBoolean(1, admin);
        st.setInt(2, userID);
        st.executeUpdate();
    }

    void addCertificate(){}
}
