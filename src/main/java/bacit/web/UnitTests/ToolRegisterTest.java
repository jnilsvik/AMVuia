package bacit.web.UnitTests;

import bacit.web.AdminFunctions.ToolRegister;
import bacit.web.Modules.Certificate;
import bacit.web.Modules.ToolModel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ToolRegisterTest {

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
    public void doGet() throws IOException {
        FakeToolRegister unitUnderTest = new FakeToolRegister();

        unitUnderTest.doGet(null,null);

        assertEquals("Cat1 Cat2 Cert1 Cert2 Tool1 ", outputStreamCaptor.toString());
    }

    @Test
    public void doPost() throws IOException {
        FakeToolRegister unitUnderTest = new FakeToolRegister();

        unitUnderTest.doPost(null, null);

        assertEquals("Tool1 Tool2 ", outputStreamCaptor.toString());

        unitUnderTest.setNewToolName("error");

        unitUnderTest.doPost(null, null);

        assertEquals("Tool1 Tool2 error", outputStreamCaptor.toString());
    }
}

class FakeToolRegister extends ToolRegister {

    List<String> categories = new LinkedList<>();
    List<Certificate> certificates = new LinkedList<>();
    List<ToolModel> tools = new LinkedList<>();
    String newToolName = "Tool2";

    FakeToolRegister(){
        setCategories();
        setCertificates();
        setTools();
    }

    public void setNewToolName(String newToolName) {
        this.newToolName = newToolName;
    }

    private void setCategories(){
        categories.add("Cat1");
        categories.add("Cat2");
    }

    private void setCertificates(){
        certificates.add(new Certificate(1, "Cert1"));
        certificates.add(new Certificate(2, "Cert2"));
    }

    private void setTools(){
        tools.add(new ToolModel(
                1,
                "Tool1",
                "Cat1",
                false,
                0,
                3,
                1,
                "Description",
                ""));
    }

    @Override
    protected List<String> getCategories(){
        return categories;
    }

    @Override
    protected List<Certificate> getCertificates(){
        return certificates;
    }

    @Override
    protected void printJspGet(HttpServletRequest request, HttpServletResponse response, List<String> categories, List<Certificate> certificates){
        for(String cat : categories){
            System.out.print(cat + " ");
        }
        for(Certificate cert: certificates){
            System.out.print(cert.getCertificateName() + " ");
        }
        for(ToolModel tool: tools){
            System.out.print(tool.getToolName() + " ");
        }
    }

    @Override
    protected ToolModel getToolFromRequest(HttpServletRequest request){
        return new ToolModel(
                2,
                newToolName,
                "Cat1",
                false,
                0,
                3,
                1,
                "Description",
                "");
    }

    @Override
    protected int addTool(ToolModel tool) throws SQLException {
        if(tool.getToolName().equals("error")) throw new SQLException();
        tools.add(tool);
        return tool.getToolID();
    }

    @Override
    protected void printJspPost(HttpServletRequest request, HttpServletResponse response){
        for(ToolModel tool: tools){
            System.out.print(tool.getToolName()+" ");
        }
    }

    @Override
    protected void printJspError(HttpServletRequest request, HttpServletResponse response){
        System.out.print("error");
    }

    @Override
    protected boolean checkSession(HttpServletRequest request, HttpServletResponse response){
        return true;
    }
}