package bacit.web.utils;


import bacit.web.Modules.FileModel;
import bacit.web.utils.DBUtils;

import javax.sql.rowset.serial.SerialBlob;
import java.io.PrintWriter;
import java.sql.*;

public class FileDAO {
    public void persistFile(FileModel file) throws Exception{
        Connection db = DBUtils.getNoErrorConnection();
        String query = "insert into files (Name, Content, ContentType, toolID) values(?,?,?,?)";

        PreparedStatement statement = db.prepareStatement(query);
        statement.setString(1, file.getName());
        statement.setBlob(2,  new SerialBlob(file.getContent()));
        statement.setString(3, file.getContentType());
        statement.setInt(4, file.getToolID());
        statement.executeUpdate();
        db.close();
    }

    public Blob getBlob(int imgID) throws Exception
    {
        Connection db = DBUtils.getNoErrorConnection();
        String query3 = "select Content from Files where id = ?";
        PreparedStatement statement = db.prepareStatement(query3);
        statement.setInt(1, imgID);
        ResultSet rs =  statement.executeQuery();
        Blob blob = null;
        if (rs.next()) {
            blob = rs.getBlob("Content");
        }
        rs.close();
        statement.close();
        db.close();
        return blob;
    }
}

