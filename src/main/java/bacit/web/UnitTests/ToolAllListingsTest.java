package bacit.web.UnitTests;

import bacit.web.Modules.ToolModel;
import bacit.web.ToolBooking.ToolAllListings;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ToolAllListingsTest {

    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @AfterEach
    void tearDown() {
        System.setOut(standardOut);
    }

    @Test
    void doGet() throws ServletException, IOException {
        FakeTAL unitUnderTest = new FakeTAL();

        unitUnderTest.doGet(null,null);

        assertEquals("Tool1 Tool2 Tool3 Cat1 Cat2 Cat3 ", outputStreamCaptor.toString());
    }
}

class FakeTAL extends ToolAllListings {
    ArrayList<String> toolCats = new ArrayList<>();
    ArrayList<ToolModel> tools = new ArrayList<>();

    FakeTAL(){
        toolCats.add("Cat1");
        toolCats.add("Cat2");
        toolCats.add("Cat3");

        tools.add(new ToolModel(1,"Tool1","Cat1",false,0,0,0,"no desc","amv.png"));
        tools.add(new ToolModel(2,"Tool2","Cat2",false,0,0,0,"no desc","amv.png"));
        tools.add(new ToolModel(3,"Tool3","Cat3",false,0,0,0,"no desc","amv.png"));
    }

    @Override
    protected List<String> getCategories(HttpServletRequest request) {
        return toolCats;
    }

    @Override
    protected List<ToolModel> GetSetTools(HttpServletRequest request) {
        return tools;
    }

    @Override
    protected boolean checkSession(HttpServletRequest request, HttpServletResponse response){
        return true;
    }

    @Override
    protected void printJsp(List<String> categories, List<ToolModel> tools, HttpServletRequest request, HttpServletResponse response){
        for(ToolModel tool : tools){
            System.out.print(tool.getToolName() + " ");
        }
        for(String cat: categories){
            System.out.print(cat + " ");
        }
    }
}