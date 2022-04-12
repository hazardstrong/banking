package banking;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class UpdateDisplay {
//	Connection conn = null;
//	PreparedStatement preparedStatement = null;
	public void balanceUpdate(int balance, String pin) {
		String sql = "INSERT INTO card(number,pin) VALUES(?,?)";

		try{			
			//get connection
			conn = JDBCUtil.getConnection();

			//create preparedStatement
			preparedStatement = conn.prepareStatement(query);

			//set values
			preparedStatement.setInt(2, 1);
			preparedStatement.setInt(1, 65000);

			//execute query
			preparedStatement.executeUpdate();

			//close connection
			preparedStatement.close();
			conn.close();

		      System.out.println("Record updated successfully.");
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
