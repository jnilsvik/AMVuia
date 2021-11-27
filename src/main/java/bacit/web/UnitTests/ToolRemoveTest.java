package bacit.web.UnitTests;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import static org.junit.jupiter.api.Assertions.*;

import bacit.web.Admin.ToolRemove;
import bacit.web.Modules.ToolModel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.LinkedList;
import java.util.List;

public class ToolRemoveTest {

    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @AfterEach
    public void tearDown() {
        System.setOut(standardOut);
    }

    @Test
    void doGet() throws Exception{

        FakeToolRemove unitUnderTest = new FakeToolRemove();

        unitUnderTest.doGet(null,null);

        assertEquals("TEST1 TEST2 ", outputStreamCaptor.toString());
    }

    @Test
    void doPost() throws Exception{

        FakeToolRemove unitUnderTest = new FakeToolRemove();

        unitUnderTest.doPost(null,null);

        assertEquals("true TEST2 ", outputStreamCaptor.toString());

        unitUnderTest.doPost(null,null);

        assertEquals("true TEST2 false TEST2 ", outputStreamCaptor.toString());
    }
}

class FakeToolRemove extends ToolRemove {

    List<ToolModel> tools = new LinkedList<>();

    FakeToolRemove(){
        setTools();
    }

    private void setTools(){
        tools.add(
                new ToolModel(
                        1, "TEST1", "Nailgun", false, 0, 1, 1, "Desc.", "")
        );
        tools.add(
                new ToolModel(
                        2, "TEST2", "Nailgun", false, 1, 2, 2, "Desc.", "")
        );
    }

    @Override
    protected List<ToolModel> getTools() {
        return tools;
    }

    @Override
    protected boolean checkSession(HttpServletRequest request, HttpServletResponse response) {
        return true;
    }

    @Override
    protected void writeGetToJSP(List<ToolModel> tools, HttpServletRequest request, HttpServletResponse response) {
        for (ToolModel tool : tools) {
            System.out.print(tool.getToolName() + " ");
        }
    }

    @Override
    protected int getID(HttpServletRequest request){
        return 1;
    }

    @Override
    protected boolean deleteRow(int id) {
        for(ToolModel tool: tools){
            if(tool.getToolID() == id){
                tools.remove(tool);
                return true;
            }
        }
        return false;
    }

    @Override
    protected void writePostToJSP(List<ToolModel> tools, boolean success, HttpServletRequest request, HttpServletResponse response) {
        System.out.print(success + " ");
        for (ToolModel tool : this.tools) {
            System.out.print(tool.getToolName() + " ");
        }
    }
}