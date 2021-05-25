

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;


/*
 8버전은 한국시간대를 인식하지 못해서 db연결정보에 아래를 추가해줘야함(ServerTimezone정보)
 ?characterEncoding=UTF-8&serverTimezone=UTC
 */
public class DBConnect {

	public static void main(String[] args) {

		try {
			String url="jdbc:mysql://localhost?characterEncoding=UTF-8&serverTimezone=UTC&useSSL=false";
			Connection con = null;

			con = DriverManager.getConnection(url,

					"cindy", "961102");



			java.sql.Statement st = null;

			ResultSet rs = null;

			st = con.createStatement();

			rs = st.executeQuery("SHOW DATABASES");



			if (st.execute("SHOW DATABASES")) {

				rs = st.getResultSet();

			}



			while (rs.next()) {

				String str = rs.getNString(1);

				System.out.println(str);

			}

		} catch (SQLException sqex) {

			System.out.println("SQLException: " + sqex.getMessage());

			System.out.println("SQLState: " + sqex.getSQLState());

		}



	}

}


