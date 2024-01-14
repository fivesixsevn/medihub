package test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Timer;
import java.util.TimerTask;

public class CompleteGui extends JFrame {

    private Timer timer;  // Timer 추가
    private String selectedDepartment;

    public CompleteGui(String selectedDepartment) {
        this.selectedDepartment = selectedDepartment;

        getContentPane().setBackground(Color.WHITE);
        getContentPane().setLayout(null);
                
                        JLabel info_treatment = new JLabel("대기 인원은 7명입니다. 예상 대기 시간은 49분입니다.");
                        info_treatment.setFont(new Font("나눔스퀘어 ExtraBold", Font.BOLD, 17));
                        info_treatment.setBackground(new Color(240, 240, 240));
                        info_treatment.setHorizontalAlignment(SwingConstants.CENTER);
                        info_treatment.setBounds(804, 340, 480, 80);
                        info_treatment.setOpaque(true);
                        getContentPane().add(info_treatment);
        
                JLabel info_patient = new JLabel("환자명 : 홍길동 / 생년월일 : 010101  /  성별 : 남");
                info_patient.setFont(new Font("나눔스퀘어 ExtraBold", Font.BOLD, 17));
                info_patient.setBackground(new Color(240, 240, 240));
                info_patient.setHorizontalAlignment(SwingConstants.CENTER);
                info_patient.setBounds(804, 420, 480, 80);
                info_patient.setOpaque(true);
                getContentPane().add(info_patient);

        JLabel info_treatmentname = new JLabel("진료실 정보");
        info_treatmentname.setForeground(new Color(255, 255, 255));
        info_treatmentname.setHorizontalAlignment(SwingConstants.CENTER);
        info_treatmentname.setFont(new Font("나눔스퀘어 ExtraBold", Font.BOLD, 27));
        info_treatmentname.setBackground(new Color(32, 178, 170));
        info_treatmentname.setBounds(804, 290, 480, 50);
        info_treatmentname.setOpaque(true);
        getContentPane().add(info_treatmentname);

        JButton button_back = new JButton("처음으로");
        button_back.setForeground(new Color(255, 255, 255));
        button_back.setBackground(new Color(32, 178, 170));
        button_back.setFont(new Font("나눔스퀘어 ExtraBold", Font.BOLD, 27));
        button_back.setBounds(650, 620, 150, 60);
        getContentPane().add(button_back);

        JLabel Medihub = new JLabel("Medihub");
        Medihub.setForeground(new Color(32, 178, 170));
        Medihub.setFont(new Font("나눔스퀘어 ExtraBold", Font.BOLD, 50));
        Medihub.setBounds(15, 15, 240, 75);
        getContentPane().add(Medihub);

        JLabel info_label = new JLabel("접수가 완료되었습니다. 대기 공간에서 기다려주십시오. ");
        info_label.setFont(new Font("나눔스퀘어 ExtraBold", Font.BOLD, 25));
        info_label.setForeground(Color.BLACK);
        info_label.setBounds(135, 400, 700, 100);
        getContentPane().add(info_label);

        JLabel info_patientname = new JLabel("홍길동님");
        info_patientname.setForeground(new Color(0, 0, 0));
        info_patientname.setFont(new Font("나눔스퀘어 ExtraBold", Font.BOLD, 40));
        info_patientname.setBounds(133, 280, 300, 100);
        getContentPane().add(info_patientname);
        
        JLabel tag_1 = new JLabel("1. 환자 정보 입력");
        tag_1.setOpaque(true);
        tag_1.setHorizontalAlignment(SwingConstants.CENTER);
        tag_1.setForeground(new Color(0, 0, 0));
        tag_1.setFont(new Font("나눔스퀘어 ExtraBold", Font.BOLD, 20));
        tag_1.setBackground(new Color(239, 239, 239));
        tag_1.setBounds(0, 100, 300, 60);
        getContentPane().add(tag_1);
        
        JSeparator separator = new JSeparator();
        separator.setForeground(Color.LIGHT_GRAY);
        separator.setBounds(1200, 159, 600, 8);
        getContentPane().add(separator);
        
        JLabel tag_2 = new JLabel("2. 진료 과목 선택");
        tag_2.setOpaque(true);
        tag_2.setHorizontalAlignment(SwingConstants.CENTER);
        tag_2.setFont(new Font("나눔스퀘어 ExtraBold", Font.BOLD, 20));
        tag_2.setBackground(UIManager.getColor("Button.background"));
        tag_2.setBounds(300, 100, 300, 60);
        getContentPane().add(tag_2);
        
        JLabel tag_3 = new JLabel("3. 진료 선생님 선택");
        tag_3.setOpaque(true);
        tag_3.setHorizontalAlignment(SwingConstants.CENTER);
        tag_3.setFont(new Font("나눔스퀘어 ExtraBold", Font.BOLD, 20));
        tag_3.setBackground(UIManager.getColor("Button.background"));
        tag_3.setBounds(600, 100, 300, 60);
        getContentPane().add(tag_3);
        
        JLabel tag_4 = new JLabel("4. 접수 완료");
        tag_4.setForeground(new Color(255, 255, 255));
        tag_4.setOpaque(true);
        tag_4.setHorizontalAlignment(SwingConstants.CENTER);
        tag_4.setFont(new Font("나눔스퀘어 ExtraBold", Font.BOLD, 20));
        tag_4.setBackground(new Color(32, 178, 170));
        tag_4.setBounds(900, 100, 300, 60);
        getContentPane().add(tag_4);

        // Database 연동하여 환자 정보 가져오기
        String url = "jdbc:mysql://medihub.cfk4qw22eogv.us-east-1.rds.amazonaws.com:3306/medihub";
        String username = "admin";
        String password = "12345678";

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String maxIdQuery = "SELECT name FROM patients WHERE id = (SELECT MAX(id) FROM patients)";
            try (PreparedStatement maxIdStatement = connection.prepareStatement(maxIdQuery);
                 ResultSet resultSet = maxIdStatement.executeQuery()) {

                if (resultSet.next()) {
                    String patientName = resultSet.getString("name");
                    info_patientname.setText(patientName + "님.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            // 진료실 정보 가져오기
            String treatmentInfoQuery = "SELECT office FROM patients WHERE id = (SELECT MAX(id) FROM patients)";
            try (PreparedStatement treatmentInfoStatement = connection.prepareStatement(treatmentInfoQuery)) {
                ResultSet treatmentResultSet = treatmentInfoStatement.executeQuery();

                if (treatmentResultSet.next()) {
                    String office = treatmentResultSet.getString("office");
                    info_treatmentname.setText("진료실 : " + office);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            // 대기 정보 가져오기
            String waitingInfoQuery = "SELECT waiting FROM hospital WHERE office IN (SELECT office FROM patients WHERE id = (SELECT MAX(id) FROM patients))";
            try (PreparedStatement waitingInfoStatement = connection.prepareStatement(waitingInfoQuery)) {
                ResultSet waitingResultSet = waitingInfoStatement.executeQuery();

                if (waitingResultSet.next()) {
                    int waitingCount = waitingResultSet.getInt("waiting");

                    info_treatment.setText("대기 인원은 " + waitingCount + "명입니다. 예상 대기 시간은 " + (waitingCount * 7) + "분입니다.");

                    // 환자 정보 가져오기
                    String patientInfoQuery = "SELECT name, number FROM patients WHERE id = (SELECT MAX(id) FROM patients)";
                    try (PreparedStatement patientInfoStatement = connection.prepareStatement(patientInfoQuery)) {
                        ResultSet patientResultSet = patientInfoStatement.executeQuery();

                        if (patientResultSet.next()) {
                            String patientName = patientResultSet.getString("name");
                            String number = patientResultSet.getString("number");

                            if (number != null && number.length() >= 7) {
                                String birth = number.substring(0, 6);

                                // 성별 추출
                                String sex = getGender(number);

                                info_patient.setText("환자명 : " + patientName + "  /  생년월일 :  " + birth + "  /  성별 : " + sex);
                            }
                        }
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            // Timer 설정 (3초 후에 창이 닫히도록 설정)
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    dispose();
                    new KioskMain();
                }
            }, 10000);

            setBounds(100, 100, 1500, 800);
            setVisible(true);
            setLocationRelativeTo(null);

            button_back.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Timer 취소 및 창 닫기
                    timer.cancel();
                    dispose();
                    new KioskMain();
                }
            });
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    


    // 성별 추출 메서드
    private String getGender(String jumin) {
        if (jumin.length() < 7) {
            return "알 수 없음";
        }

        char genderChar = jumin.charAt(6);

        if (genderChar == '1' || genderChar == '3') {
            return "남";
        } else if (genderChar == '2' || genderChar == '4') {
            return "여";
        } else {
            return "알 수 없음";
       
}
    }
    }