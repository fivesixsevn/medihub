package test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class DepartmentGui extends JFrame {

    private String selectedDepartment = ""; // 선택한 부서를 저장하는 변수
    private String firstTreatment = "";
    private String secondTreatment = "";
    private Map<JButton, Color> originalButtonColors = new HashMap<>(); // 버튼의 원래 색상을 저장하는 맵

    public DepartmentGui() {
        getContentPane().setBackground(Color.WHITE);
        getContentPane().setLayout(null);

        JButton button_next = new JButton("next");
        button_next.setFont(new Font("나눔스퀘어 ExtraBold", Font.BOLD, 27));
        button_next.setBackground(new Color(32, 178, 170));
        button_next.setBounds(900, 620, 150, 60);
        getContentPane().add(button_next);

        JButton button_back = new JButton("back");
        button_back.setBackground(new Color(240, 240, 240));
        button_back.setFont(new Font("나눔스퀘어 ExtraBold", Font.BOLD, 27));
        button_back.setBounds(370, 620, 150, 60);
        button_back.setOpaque(true);
        getContentPane().add(button_back);

        JLabel Medihub = new JLabel("Medihub");
        Medihub.setBackground(new Color(240, 240, 240));
        Medihub.setForeground(new Color(32, 178, 170));
        Medihub.setFont(new Font("나눔스퀘어 ExtraBold", Font.BOLD, 50));
        Medihub.setBounds(15, 15, 240, 75);
        getContentPane().add(Medihub);

        JButton Internal = new JButton("내과");
        Internal.setBackground(new Color(239, 239, 239));
        Internal.setFont(new Font("나눔스퀘어 ExtraBold", Font.BOLD, 35));
        Internal.setBounds(340, 285, 350, 100);
        getContentPane().add(Internal);

        JButton Dermatology = new JButton("피부과");
        Dermatology.setBackground(new Color(239, 239, 239));
        Dermatology.setFont(new Font("나눔스퀘어 ExtraBold", Font.BOLD, 35));
        Dermatology.setBounds(729, 285, 350, 100);
        getContentPane().add(Dermatology);

        JButton Osteo = new JButton("정형외과");
        Osteo.setBackground(new Color(239, 239, 239));
        Osteo.setFont(new Font("나눔스퀘어 ExtraBold", Font.BOLD, 35));
        Osteo.setBounds(340, 440, 350, 100);
        getContentPane().add(Osteo);

        JButton NeuroPsychiatry = new JButton("정신의학과");
        NeuroPsychiatry.setBackground(new Color(239, 239, 239));
        NeuroPsychiatry.setFont(new Font("나눔스퀘어 ExtraBold", Font.BOLD, 35));
        NeuroPsychiatry.setBounds(729, 440, 350, 100);
        getContentPane().add(NeuroPsychiatry);
        setBounds(100, 100, 1500, 800);

        // setVisible(true); 원래 색상을 저장
        originalButtonColors.put(Internal, Internal.getBackground());
        originalButtonColors.put(Dermatology, Dermatology.getBackground());
        originalButtonColors.put(Osteo, Osteo.getBackground());
        originalButtonColors.put(NeuroPsychiatry, NeuroPsychiatry.getBackground());
        
        JLabel tag_1 = new JLabel("1. 환자 정보 입력");
        tag_1.setOpaque(true);
        tag_1.setHorizontalAlignment(SwingConstants.CENTER);
        tag_1.setForeground(new Color(0, 0, 0));
        tag_1.setFont(new Font("나눔스퀘어 ExtraBold", Font.BOLD, 20));
        tag_1.setBackground(new Color(239, 239, 239));
        tag_1.setBounds(0, 110, 300, 60);
        getContentPane().add(tag_1);
        
        JSeparator separator = new JSeparator();
        separator.setForeground(Color.LIGHT_GRAY);
        separator.setBounds(1200, 169, 600, 8);
        getContentPane().add(separator);
        
        JLabel tag_2 = new JLabel("2. 진료 과목 선택");
        tag_2.setForeground(new Color(255, 255, 255));
        tag_2.setOpaque(true);
        tag_2.setHorizontalAlignment(SwingConstants.CENTER);
        tag_2.setFont(new Font("나눔스퀘어 ExtraBold", Font.BOLD, 20));
        tag_2.setBackground(new Color(32, 178, 170));
        tag_2.setBounds(300, 110, 300, 60);
        getContentPane().add(tag_2);
        
        JLabel tag_3 = new JLabel("3. 진료 선생님 선택");
        tag_3.setOpaque(true);
        tag_3.setHorizontalAlignment(SwingConstants.CENTER);
        tag_3.setFont(new Font("나눔스퀘어 ExtraBold", Font.BOLD, 20));
        tag_3.setBackground(UIManager.getColor("Button.background"));
        tag_3.setBounds(600, 110, 300, 60);
        getContentPane().add(tag_3);
        
        JLabel tag_4 = new JLabel("4. 접수 완료");
        tag_4.setOpaque(true);
        tag_4.setHorizontalAlignment(SwingConstants.CENTER);
        tag_4.setFont(new Font("나눔스퀘어 ExtraBold", Font.BOLD, 20));
        tag_4.setBackground(UIManager.getColor("Button.background"));
        tag_4.setBounds(900, 110, 300, 60);
        getContentPane().add(tag_4);

        setBounds(100, 100, 1500, 800);
        setVisible(true);
        setLocationRelativeTo(null);

        // 과목 btn actionlistener
        Internal.addActionListener(e -> handleDepartmentButtonClick("내과", Internal));
        Dermatology.addActionListener(e -> handleDepartmentButtonClick("피부과", Dermatology));
        Osteo.addActionListener(e -> handleDepartmentButtonClick("정형외과", Osteo));
        NeuroPsychiatry.addActionListener(e -> handleDepartmentButtonClick("정신의학과", NeuroPsychiatry));

        button_back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteLastPatient(); // 마지막 환자 정보 삭제
                dispose();
                new PatientGui();
            }
        });

        button_next.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                openTreatmentGui();
            }
        });
    }

    private void handleDepartmentButtonClick(String department, JButton clickedButton) {
        // 선택한 부서를 설정
        selectedDepartment = department;

        // 선택한 부서에 따라 firstTreatment 및 secondTreatment 설정
        switch (selectedDepartment) {
            case "내과":
                firstTreatment = "A";
                secondTreatment = "B";
                break;
            case "정형외과":
                firstTreatment = "C";
                secondTreatment = "D";
                break;
            case "피부과":
                firstTreatment = "E";
                secondTreatment = "F";
                break;
            case "정신의학과":
                firstTreatment = "G";
                secondTreatment = "H";
                break;
            default:
                // 다른 경우에 대한 처리가 필요하다면 추가
                break;
        }

        // 클릭한 버튼의 색상을 변경하고 다른 버튼의 색상을 원래대로 복원
        for (Map.Entry<JButton, Color> entry : originalButtonColors.entrySet()) {
            JButton button = entry.getKey();
            Color originalColor = entry.getValue();
            button.setBackground(button == clickedButton ? new Color(32, 178, 170) : originalColor);

        }

        System.out.println("선택한 부서: " + selectedDepartment);
    }

    private void deleteLastPatient() {
        String url = "jdbc:mysql://medihub.cfk4qw22eogv.us-east-1.rds.amazonaws.com:3306/medihub";
        String username = "admin";
        String password = "12345678";

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            // 마지막 환자의 ID를 저장할 임시 테이블을 만듭니다.
            String tempTableQuery = "CREATE TEMPORARY TABLE TempLastPatient AS SELECT MAX(id) AS lastPatientId FROM patients";

            try (PreparedStatement tempTableStatement = connection.prepareStatement(tempTableQuery)) {
                tempTableStatement.executeUpdate();

                // 임시 테이블을 사용하여 마지막 환자를 삭제합니다.
                String deleteQuery = "DELETE FROM patients WHERE id = (SELECT lastPatientId FROM TempLastPatient)";

                try (PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
                    int rowsAffected = preparedStatement.executeUpdate();
                    if (rowsAffected > 0) {
                        System.out.println("마지막 환자 정보를 성공적으로 삭제하였습니다.");
                    } else {
                        System.out.println("마지막 환자 정보 삭제에 실패하였습니다.");
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void openTreatmentGui() {
        dispose();
        new TreatmentGui(selectedDepartment, firstTreatment, secondTreatment);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new DepartmentGui());
    }
}
