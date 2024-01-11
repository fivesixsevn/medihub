package jdbc;

//약국의 세부정보를 나타냄
//약국명, 전화번호, 주소, 취급약품, 거리, 평점을 저장하는 class
public class PharmacyDetails {
 private String pharmacy;
 private String callNumber;
 private String address;
 private String medicine;
 private String distance;
 private String star;

 public PharmacyDetails(String pharmacy, String callNumber, String address, String medicine, String distance, String star) {
     this.pharmacy = pharmacy;
     this.callNumber = callNumber;
     this.address = address;
     this.medicine = medicine;
     this.distance = distance;
     this.star = star;
     
 }

 public String getPharmacy() {
     return pharmacy;
 }

 public String getCallNumber() {
     return callNumber;
 }

 public String getAddress() {
     return address;
 }

 public String getMedicine() {
     return medicine;
 }

 public String getDistance() {
     return distance;
 }

 public String getStar() {
     return star;
 }
 
}