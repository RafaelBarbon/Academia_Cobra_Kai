import java.sql.*;

class MysqlCon{
	public static void main(String args[]){
		float valores[] = new float[4];
		try{
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection link = DriverManager.getConnection("jdbc:mysql://localhost:3306/academia","root","");
			Statement stmt = link.createStatement();
			ResultSet result = stmt.executeQuery("select * from info;");
			/*while(result.next())
				System.out.println(result.getInt(1)+"  "+result.getString(2)+"  "+result.getString(3));*/
			valores[0] = result.getFloat(1);
			valores[1] = result.getFloat(2);
			valores[2] = result.getFloat(3);
			valores[3] = result.getFloat(4);
			/*final String senhaMestre = result.getString(5);
			final String password = result.getString(6);
			final String username = result.getString(7);*/
			link.close();
		}
		catch(Exception e){
			System.out.println(e);
			e.printStackTrace();
		}

		System.out.println(valores[0] + " " + valores[1] + " " + valores[2] + " " + valores[3]);
	}
}