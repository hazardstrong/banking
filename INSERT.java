package banking;

import java.beans.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class INSERT {
    private Connection connect() {
        // SQLite connection string
        String url = "jdbc:sqlite:card.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }
    public void insert(String number, String pin) {
        String sql = "INSERT INTO card(number,pin) VALUES(?,?)";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, number);
            pstmt.setString(2, pin);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public boolean loginAccount(String num, String pin){
    	String readb = "select number from card where pin =?";
		try (Connection conn = this.connect();
	             PreparedStatement pstmt = conn.prepareStatement(readb)) {
			pstmt.setString(1, pin);
			ResultSet rs = pstmt.executeQuery();
			rs.next();
			String s= rs.getString("number");
			if(num.equals(s)) {
				return true;
			}
			
		}catch (SQLException e) {
		}
		return false;
    }
    public void deleteAccount(String number) {
    	String del = "delete from card WHERE number = ?";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(del)) {
            pstmt.setString(1, number);
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
	public void readBalance(String num) throws SQLException {
		String readb = "select balance from card WHERE number = ?";
		try (Connection conn = this.connect();
	             PreparedStatement pstmt = conn.prepareStatement(readb)) {
			pstmt.setString(1, num);
			ResultSet rs = pstmt.executeQuery();
			rs.next();
			int b= rs.getInt("balance");
			
			System.out.println(b);
		}
	}
	public void balanceUpdate(int balance, String pin) {
    	String update = "UPDATE card SET balance = ? WHERE pin = ?";
    	String u2 = "select balance from card WHERE pin = ?";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(update);
        		PreparedStatement stat2 = conn.prepareStatement(u2)) {
        	stat2.setString(1, pin);
        	ResultSet rs = stat2.executeQuery();
			rs.next();
			int newb = rs.getInt("balance");
        	pstmt.setInt(1, balance+newb);
            pstmt.setString(2, pin);
            pstmt.addBatch();
            pstmt.executeBatch();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
	public boolean transferBoolean(String tcard) {
		String readb = "SELECT number FROM card WHERE number = ?";
		try (Connection conn = this.connect();
	             PreparedStatement pstmt = conn.prepareStatement(readb)) {
			pstmt.setString(1, tcard);
			ResultSet rs = pstmt.executeQuery();
			rs.next();
			String s= rs.getString("number");
			if(tcard.equals(s)) {
				return true;
			}else
				return false;
			
		}catch (SQLException e) {
		}
		return false;
		
	}
	public boolean moneyTransferBoolean(int tmoney, String card) {
		String readb = "select balance from card where number =?";
		try (Connection conn = this.connect();
	             PreparedStatement pstmt = conn.prepareStatement(readb)) {
			pstmt.setString(1, card);
			ResultSet rs = pstmt.executeQuery();
			rs.next();
			int money = rs.getInt("balance");
			if(tmoney > money) {
				return true;
			}
			
		}catch (SQLException e) {
		}
		return false;
	}
	public void finallyTransfer(int tmoney, String tcard) {
		String q1 = "UPDATE card SET balance = ? WHERE number = ?";
    	String q2 = "select balance from card WHERE number = ?";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(q1);
        		PreparedStatement stat2 = conn.prepareStatement(q2)) {
        	stat2.setString(1, tcard);
        	ResultSet rs = stat2.executeQuery();
			rs.next();
			int previous = rs.getInt("balance");
        	pstmt.setInt(1, tmoney+previous);
            pstmt.setString(2, tcard);
            pstmt.addBatch();
            pstmt.executeBatch();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
		
	}
	public void minusFinallyTransfer(int tmoney, String currentaccount) {
		String update = "UPDATE card SET balance = ? WHERE number = ?";
    	String u2 = "select balance from card WHERE number = ?";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(update);
        		PreparedStatement stat2 = conn.prepareStatement(u2)) {
        	stat2.setString(1, currentaccount);
        	ResultSet rs = stat2.executeQuery();
			rs.next();
			int old = rs.getInt("balance");
        	pstmt.setInt(1, old - tmoney);
            pstmt.setString(2, currentaccount);
            pstmt.addBatch();
            pstmt.executeBatch();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
	}
}
