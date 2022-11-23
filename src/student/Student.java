package student;

//import db.MyConnection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class Student {

    private String jdbcURL = "jdbc:mysql://localhost:3306/student_management?useSSL=false";
    private String jdbcUsername = "root";
    private String jdbcPassword = "QWERTY@12";

    protected Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return connection;
    }

    Connection con = getConnection();
    PreparedStatement ps;

    // get table max row
    public int getMax() {
        int id = 0;
        Statement st;
        try {
            st = con.createStatement();
            ResultSet rs = st.executeQuery("select max(id) from student");
            while (rs.next()) {
                id = rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
        }
        return id + 1;
    }

    public void insert(int id, String sname, String gender, String age, String email, String phone, String father, String mother, String address, String imagePath) {
        String sql = "insert into student values(?,?,?,?,?,?,?,?,?,?)";
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.setString(2, sname);
            ps.setString(3, gender);
            ps.setString(4, age);
            ps.setString(5, email);
            ps.setString(6, phone);
            ps.setString(7, father);
            ps.setString(8, mother);
            ps.setString(9, address);
            ps.setString(10, imagePath);

            if (ps.executeUpdate() > 0) {
                JOptionPane.showMessageDialog(null, "New student added successfully");

            }

        } catch (SQLException ex) {
            Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    // check if email already exists in the database
    public boolean doesEmailAlreadyExist(String email) {
        try {
            ps = con.prepareStatement("select * from student where email = ?");
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    // check if student id already exists in the db before updating
    public boolean doesIdAlreadyExist(int id) {
        try {
            ps = con.prepareStatement("select * from student where id = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    // display students from the database
    public void getStudentValue(JTable table, String searchValue) {
        String sql = "select * from student where concat(id,name,phone)like ? order by id desc";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, "%" + searchValue + "%");
            ResultSet rs = ps.executeQuery();
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            Object[] row;
            while (rs.next()) {
                row = new Object[10];
                row[0] = rs.getInt(1);
                row[1] = rs.getString(2);
                row[2] = rs.getString(3);
                row[3] = rs.getString(4);
                row[4] = rs.getString(5);
                row[5] = rs.getString(6);
                row[6] = rs.getString(7);
                row[7] = rs.getString(8);
                row[8] = rs.getString(9);
                row[9] = rs.getString(10);
                model.addRow(row);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // update student value
    public void update(int id, String sname, String gender, String age, String email, String phone,
            String father, String mother, String address, String imagePath) {
        String sql = "update student set name=?,gender=?,age=?,email=?,phone=?,father_name=?,mother_name=?,address=?,image_path=? where id=?";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, sname);
            ps.setString(2, gender);
            ps.setString(3, age);
            ps.setString(4, email);
            ps.setString(5, phone);
            ps.setString(6, father);
            ps.setString(7, mother);
            ps.setString(8, address);
            ps.setString(9, imagePath);
            ps.setInt(10, id);

            if (ps.executeUpdate() > 0) {
                JOptionPane.showMessageDialog(null, "Student data updated successfully!");
            }

        } catch (SQLException ex) {
            Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    // delete student data
    public void delete(int id) {
        int confirm = JOptionPane.showConfirmDialog(null, "All data for this student will be deleted","Delete Student?",JOptionPane.OK_CANCEL_OPTION,0);
        if(confirm == JOptionPane.OK_OPTION) {
            try {
                ps = con.prepareStatement("delete from student where id = ?");
                ps.setInt(1, id);
                if(ps.executeUpdate() > 0) {
                    JOptionPane.showMessageDialog(null, "Student deleted successfully!");
                }
            } catch (SQLException ex) {
                Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
