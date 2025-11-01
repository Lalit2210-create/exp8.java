import java.io.*;
import java.sql.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

public class EmployeeServlet extends HttpServlet {
    private static final String URL = "jdbc:mysql://localhost:3306/testdb";
    private static final String USER = "root";
    private static final String PASS = "root";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String empIdParam = request.getParameter("empid");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(URL, USER, PASS);

            Statement st;
            ResultSet rs;

            if (empIdParam != null && !empIdParam.isEmpty()) {
                int empId = Integer.parseInt(empIdParam);
                st = con.createStatement();
                rs = st.executeQuery("SELECT * FROM Employee WHERE EmpID=" + empId);
            } else {
                st = con.createStatement();
                rs = st.executeQuery("SELECT * FROM Employee");
            }

            out.println("<h2>Employee Records</h2>");
            out.println("<table border='1'><tr><th>EmpID</th><th>Name</th><th>Salary</th></tr>");

            boolean found = false;
            while (rs.next()) {
                found = true;
                out.println("<tr><td>" + rs.getInt("EmpID") + "</td><td>" 
                        + rs.getString("Name") + "</td><td>" 
                        + rs.getDouble("Salary") + "</td></tr>");
            }
            out.println("</table>");

            if (!found) out.println("<p>No employee found with given ID.</p>");

            con.close();
        } catch (Exception e) {
            out.println("<p>Error: " + e.getMessage() + "</p>");
        }
    }
}
