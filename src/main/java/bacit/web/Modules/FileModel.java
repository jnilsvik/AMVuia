package bacit.web.Modules;

public class FileModel {

    private String name;
    private byte[] content;
    private String contentType;
    private int toolID;


    public FileModel(String name, byte[] content, String contentType, int toolID) {
        this.name = name;
        this.content = content;
        this.contentType = contentType;
        this.toolID = toolID;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public int getToolID() {
        return toolID;
    }

    public void setToolID(int toolID) {
        this.toolID = toolID;
    }
}
