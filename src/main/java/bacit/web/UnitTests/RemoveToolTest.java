package bacit.web.UnitTests;


import bacit.web.AdminFunctions.RemoveTool;
import bacit.web.Modules.ToolModel;

import java.util.LinkedList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class RemoveToolTest {

    @org.junit.jupiter.api.Test
    void doGet() throws Exception{
        //Arrange
        FakeRemoveTool unitUnderTest = new FakeRemoveTool();
        //Act
        unitUnderTest.doGet(null,null);
    }
}

class FakeRemoveTool extends RemoveTool {

    @Override
    protected List<ToolModel> getTools(){
        List<ToolModel> tools = new LinkedList<>();
        tools.add(
                new ToolModel(
                        1,"TEST1", "Nailgun", false, 0, 1, 1, "Desc.", "")
                );
        tools.add(
                new ToolModel(
                        2,"TEST2", "Nailgun", false, 1, 2, 2, "Desc.", "")
        );
        return tools;
    }


}
