package bacit.web.UnitTests;

import bacit.web.Modules.ToolModel;
import bacit.web.ToolBooking.ToolAllListings;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

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
        //Arrange
        FakeTAL unitUnderTest = new FakeTAL();
        //Act
        unitUnderTest.doGet(null,null);
        //Assert
    }
}

class FakeTAL extends ToolAllListings {
    ArrayList<String> toolCats = new ArrayList<>();
    ArrayList<ToolModel> tools = new ArrayList<>();

    FakeTAL(){
        toolCats.add("tc1");
        toolCats.add("tc2");
        toolCats.add("tc3");

        tools.add(new ToolModel(1,"tool1","tc1",false,0,0,0,"no desc","amv.png"));
        tools.add(new ToolModel(2,"tool2","tc2",false,0,0,0,"no desc","amv.png"));
        tools.add(new ToolModel(3,"tool3","tc3",false,0,0,0,"no desc","amv.png"));
    }

    @Override
    protected List<String> getCategories(HttpServletRequest request) {
        return toolCats;
    }

    @Override
    protected List<ToolModel> GetSetTools(HttpServletRequest request) {
        return tools;
    }
}