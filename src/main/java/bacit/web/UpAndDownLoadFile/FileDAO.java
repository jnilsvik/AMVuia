package bacit.web.UpAndDownLoadFile;


import bacit.web.bacit_database.DBUtils;

import javax.sql.rowset.serial.SerialBlob;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class FileDAO {

    public void persistFile(FileModel file, PrintWriter out) throws Exception{
        Connection db = DBUtils.getNoErrorConnection(out);
        String query3 = "insert into files (Name, Content, ContentType) values(?,?,?)";

        PreparedStatement statement = db.prepareStatement(query3);
        statement.setString(1, file.getName());
        statement.setBlob(2,  new SerialBlob(file.getContents()));
        statement.setString(3, file.getContentType());
        statement.executeUpdate();
        db.close();
    }

    public FileModel getFile(int id) throws Exception
    {
        Connection db = DBUtils.getINSTANCE().getConnection();
        String query3 = "select Name, Content, ContentType from Files where id = ?";
        PreparedStatement statement = db.prepareStatement(query3);
        statement.setInt(1, id);
        ResultSet rs =  statement.executeQuery();
        FileModel model = null;
        if (rs.next()) {
            model = new FileModel(
                    rs.getString("Name"),
                    rs.getBytes("Content"),
                    rs.getString("ContentType")
            );
        }
        db.close();
        return model;
    }
}

