package bacit.web.utils;


import bacit.web.Modules.FileModel;
import bacit.web.utils.DBUtils;

import javax.sql.rowset.serial.SerialBlob;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

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

    public FileModel getFile(int toolID) throws Exception
    {
        Connection db = DBUtils.getNoErrorConnection();
        String query3 = "select Name, Content, ContentType,toolID from Files where id = ?";
        PreparedStatement statement = db.prepareStatement(query3);
        statement.setInt(1, toolID);
        ResultSet rs =  statement.executeQuery();
        FileModel model = null;
        if (rs.next()) {
            model = new FileModel(
                    rs.getString("Name"),
                    rs.getBytes("Content"),
                    rs.getString("ContentType"),
                    rs.getInt("toolID")
            );
        }
        db.close();
        return model;
    }
}

