package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Pharmacy {
	private PreparedStatement psmt;
	private Connection con;
	
	//jdbc에서 모든 약국의 ID, 약국명, 평점을 가져와 2차원 배열로 반환
	public String[][] getPharmacies(){ 
		try {
			con = getConnection();
			psmt = con.prepareStatement("SELECT id, pharmacy,star FROM pharmacy");
			ResultSet rs = psmt.executeQuery();
			ArrayList<String[]> list = new ArrayList<String[]>();  
			while(rs.next()) {
				list.add(new String[] {
						rs.getString("id"),
						rs.getString("pharmacy"),
						rs.getString("star")
				});
			}
			String[][] arr = new String[list.size()][3];
			System.out.println("데이터 불러오기 완료");
			return list.toArray(arr);

		} catch(Exception e) {
			System.out.println(e.getMessage());
			return null;
		} finally {
			close(psmt, con);
		}
	}
	
	//약국 목록에서 원하는 약국명 클릭하면, 약국상세정보를 가져옴
    public PharmacyDetails getPharmacyDetailsByName(String pharmacyName) {
        try {
            con = getConnection();
            psmt = con.prepareStatement("SELECT id, pharmacy, call_number, address, medicine, distance, star FROM pharmacy WHERE pharmacy = ?");
            psmt.setString(1, pharmacyName);
            ResultSet rs = psmt.executeQuery();

            if (rs.next()) {
                return new PharmacyDetails(
                        rs.getString("pharmacy"),
                        rs.getString("call_number"),
                        rs.getString("address"),
                        rs.getString("medicine"),
                        rs.getString("distance"),
                        rs.getString("star")
                );
            } else {
                System.out.println("약국 상세 정보를 찾을 수 없습니다: " + pharmacyName);
                return null;
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        } finally {
            close(psmt, con);
        }
    }
	
	//mysql - 이클립스 연결
	public Connection getConnection() {

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			String url = "jdbc:mysql://medihub.cfk4qw22eogv.us-east-1.rds.amazonaws.com:3306/medihub";
			String user = "admin";
			String pwd = "12345678";

			con = DriverManager.getConnection(url, user, pwd);
			System.out.println("DB 연결 완료");
			return con;

		} catch (ClassNotFoundException e) {
			System.out.println("JDBC 드라이버 로드 에러");
			return null;
		} catch (SQLException e) {
			System.out.println("DB 연결 오류");
			return null;
		}
	}
	
	//jdbc 연결 종료	
	public static void close(PreparedStatement psmt, Connection con) {
		
		try {
			if(psmt != null )
				psmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

