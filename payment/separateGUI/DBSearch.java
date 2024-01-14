import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBSearch {
	private static final String URL = "jdbc:mysql://medihub.cfk4qw22eogv.us-east-1.rds.amazonaws.com:3306/medihub";
	private static final String user = "admin";
	private static final String pw = "12345678";
	
	public static DocumentItem searchRecord(String input) {
		// DB patients table에 접근해서 인풋String을 가지고 환자를 식별하고 
		// 해당 레코드의 유효 정보를 모두 가져옴. (DocumnetItem클래스로 저장)
		DocumentItem PatientDoc = new DocumentItem();
		
		try (Connection c = DriverManager.getConnection(URL, user, pw)){
			String sql = "SELECT * FROM patients WHERE number = ?";
			try (PreparedStatement pstmt = c.prepareStatement(sql)) {
				pstmt.setString(1, input); //접수주민번호
				try (ResultSet rs = pstmt.executeQuery()) { //rs = pstmt.executeQuery() 쿼리에서 식별한 레코드 결과
					if (rs.next()) {
						int id = rs.getInt("id");
						String name = rs.getString("name"); //레코드의 각 칼럼 정보를 가져와 저장.
						String number = rs.getString("number");
						String medicalReport = rs.getString("medicalreport");
						String office = rs.getString("office");
						String disease = rs.getString("disease");
						String medicine = rs.getString("medicine");
						String doses = rs.getString("doses");
						String fee = rs.getString("fee"); 
						
						PatientDoc.setID(id); //추가
						PatientDoc.setName(name); //가져온 정보를 토대로 환자 문서 정보 구성
						PatientDoc.setNumber(number);
						PatientDoc.setMedicalReport(medicalReport);
						PatientDoc.setOffice(office);
						PatientDoc.setDisease(disease);
						PatientDoc.setMedicine(medicine);
						PatientDoc.setDoses(doses);
						PatientDoc.setFee(fee);
						
					}
				}
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return PatientDoc;
	}
}
