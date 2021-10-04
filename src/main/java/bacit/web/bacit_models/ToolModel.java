package bacit.web.bacit_models;

import javax.tools.Tool;

public class ToolModel {

    private int id;
    private String name;
    private String toolType;
    private String location;
    private String status;

    public ToolModel(int id, String name, String toolType, String location, String status) {
        this.id = id;
        this.name = name;
        this.toolType = toolType;
        this.location = location;
        this.status = status;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setToolType(String toolType) {
        this.toolType = toolType;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getToolType() {
        return toolType;
    }

    public String getLocation() {
        return location;
    }

    public String getStatus() {
        return status;
    }
}
