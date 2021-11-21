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
    ArrayList<String> toolCAT = new ArrayList<>();
    ArrayList<ToolModel> toolALL = new ArrayList<>();

    FakeTAL(){
        toolCAT.add("tc1");
        toolCAT.add("tc2");
        toolCAT.add("tc3");

        toolALL.add(new ToolModel(1,"tool1","tc1",false,0,0,0,"no desc","amv.png"));
        toolALL.add(new ToolModel(2,"tool2","tc2",false,0,0,0,"no desc","amv.png"));
        toolALL.add(new ToolModel(3,"tool3","tc3",false,0,0,0,"no desc","amv.png"));
    }

    @Override
    protected void GetSetCategories(HttpServletRequest request) {

    }

    @Override
    protected void GetSetTools(HttpServletRequest request) {

    }
}