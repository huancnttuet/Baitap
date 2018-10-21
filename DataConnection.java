
import com.mysql.jdbc.Connection;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author huanc_9jkjulx
 */
public class DataConnection {
 
    private static Connection con;
    
    public DataConnection(){
         
    }
 
    public static Connection getConnection() {
        con = null;
        Properties properties = new Properties();
        try {
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost/sakila", "root", "341997mok");
        } catch (SQLException ex) {
            Logger.getLogger(DataConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return (con);
    }
 
    public static void freeConnection() {
        try {
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(DataConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public ArrayList<Word> getListWordDatabase() throws ClassNotFoundException,SQLException {
        ArrayList<Word> listWordData = new ArrayList<>();
        // Tạo đối tượng Statement.
        try ( // Lấy ra đối tượng Connection kết nối vào DB.
                Connection c = getConnection()) {
            // Tạo đối tượng Statement.
            Statement statement = c.createStatement();
            String sql = "Select word,wordtype, definition from entries";
            // Thực thi câu lệnh SQL trả về đối tượng ResultSet.
            ResultSet rs = statement.executeQuery(sql);
            // Duyệt trên kết quả trả về.
            while (rs.next()) {
                // Di chuyển con trỏ xuống bản ghi kế tiếp.
                String word_target = rs.getString(1);
                String word_explain1 = rs.getString(2);
                String word_explain2 = rs.getString("definition");
                String word_explain = word_explain1 + "\n" + word_explain2;
                Word word_new = new Word(word_target, word_explain);
                listWordData.add(word_new);
            }
            
        }
        return listWordData;
  }
 
}
