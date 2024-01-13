package oop_kiosk_medihub;

public class DocumentItem {
	private int id;		//added for query update
	private String name;
	private String number;
	private String medicalReport;
	private String office;
	private String subject;
	private String disease;
	private String medicine;
	private String doses;
	private String fee;
	
	public int getID() { //added
		return id;
	}
	public void setID(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String n) {
		this.name = n;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String num) {
		this.number = num;
	}
	public String getOffice() {
		return office;
	}
	public void setOffice(String o) {
		this.office = o+" 원장님"; // 진료실 A~H
	}
	public void setSubject() {
		
		if ("A 원장님".equals(office)|| "B 원장님".equals(office)) {
			subject = "내과";
		}else if ("C 원장님".equals(office) || "D 원장님".equals(office)) {
			subject = "정형외과";
		}else if ("E 원장님".equals(office) || "F 원장님".equals(office)) {
			subject = "피부과";
		}else if ("G 원장님".equals(office) || "H 원장님".equals(office)) {
			subject = "정신의학과";}
	}
	public String getSubject() { //진료 과목 따로
		setSubject();
		return subject;
	}
	public String getDisease() {
		return disease;
	}
	public void setDisease(String d) {
		this.disease = d;
	}
	public String getMedicine() {
		return medicine;
	}
	public void setMedicine(String medicine) {
		this.medicine = medicine;
	}
	public String getDoses() {
		return doses;
	}
	public void setDoses(String doses) {
		this.doses = doses;
	}
	public String getFee() {
		return fee;
	}
	public void setFee(String f) {
		this.fee = f;
	}
	public String getMedicalReport() {
		return medicalReport;
	}
	public void setMedicalReport(String m) {
		if ("O".equals(m)) { // not m == "O"
			this.medicalReport = "20000원";
		} else this.medicalReport = m;
		
	}
	public int getAmount() {
		try {
			String trimmedFee = fee.replace("원", "").trim();
			int intFee = Integer.parseInt(trimmedFee);
			int ReportPrice = 20000;
			if (medicalReport == "20000원") 
				return intFee + ReportPrice;
			return intFee;
		} catch (NumberFormatException e) { // 예외 처리
			e.printStackTrace();
			return 0;
		}		
	}
}
