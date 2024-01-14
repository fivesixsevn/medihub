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

public class TreatmentGui extends JFrame {

    private String selectedDepartment;
    private String firstTreatment;
    private String secondTreatment;
    private JLabel waiting1;
    private JLabel waiting2;

    public TreatmentGui(String selectedDepartment, String firstTreatment, String secondTreatment) {

        // 전달받은 값으로 클래스 변수 설정
        this.selectedDepartment = selectedDepartment;
        this.firstTreatment = firstTreatment;
        this.secondTreatment = secondTreatment;

        getContentPane().setBackground(Color.WHITE);
        getContentPane().setLayout(null);

        JButton button_skip = new JButton("skip");
        button_skip.setBackground(new Color(32, 178, 170));
        button_skip.setFont(new Font("나눔스퀘어 ExtraBold", Font.BOLD, 27));
        button_skip.setBounds(900, 620, 150, 60);
        getContentPane().add(button_skip);

        button_skip.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 대기 인원을 비교해서 더 작은 진료실 정보 가져오기
                String selectedOffice = getPreferredTreatment();

                // 환자 정보 저장
                savePatientInfo(selectedOffice);

                // completeGui로 이동
                dispose(); // 현재 창 닫기
                new CompleteGui(selectedDepartment);
            }
        });

        JButton button_back = new JButton("back");
        button_back.setBackground(new Color(240, 240, 240));
        button_back.setFont(new Font("나눔스퀘어 ExtraBold", Font.BOLD, 27));
        button_back.setBounds(370, 620, 150, 60);
        button_back.setOpaque(true);
        getContentPane().add(button_back);

        JLabel Medihub = new JLabel("Medihub");
        Medihub.setForeground(new Color(32, 178, 170));
        Medihub.setFont(new Font("나눔스퀘어 ExtraBold", Font.BOLD, 50));
        Medihub.setBounds(15, 15, 223, 75);
        getContentPane().add(Medihub);

        // 선택한 부서, 진료 정보를 사용하여 GUI에서 필요한 대로 처리
        JLabel label_1 = new JLabel("진료실 " + firstTreatment);
        label_1.setBackground(Color.LIGHT_GRAY);
        label_1.setFont(new Font("나눔스퀘어 ExtraBold", Font.BOLD, 20));
        label_1.setBounds(180, 314, 150, 60);
        getContentPane().add(label_1);

        JLabel label_2 = new JLabel("진료실 " + secondTreatment);
        label_2.setBackground(new Color(192, 192, 192));
        label_2.setFont(new Font("나눔스퀘어 ExtraBold", Font.BOLD, 20));
        label_2.setBounds(180, 414, 150, 60);
        getContentPane().add(label_2);

        waiting1 = new JLabel("대기 정보");
        waiting1.setFont(new Font("나눔스퀘어 ExtraBold", Font.BOLD, 17));
        waiting1.setBounds(362, 332, 420, 22);
        getContentPane().add(waiting1);

        waiting2 = new JLabel("대기정보 2");
        waiting2.setFont(new Font("나눔스퀘어 ExtraBold", Font.BOLD, 17));
        waiting2.setBounds(362, 435, 430, 22);
        getContentPane().add(waiting2);

        updateWaitingLabels();

        JButton register_1 = new JButton("접수하기");
        register_1.setForeground(new Color(255, 255, 255));
        register_1.setBackground(new Color(32, 178, 170));
        register_1.setFont(new Font("나눔스퀘어 ExtraBold", Font.BOLD, 25));
        register_1.setBounds(800, 310, 237, 61);
        getContentPane().add(register_1);

        JButton register_2 = new JButton("접수하기");
        register_2.setForeground(new Color(255, 255, 255));
        register_2.setBackground(new Color(32, 178, 170));
        register_2.setFont(new Font("나눔스퀘어 ExtraBold", Font.BOLD, 25));
        register_2.setBounds(800, 414, 237, 61);
        getContentPane().add(register_2);

        register_1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                savePatientInfo(firstTreatment);

                // completeGui로 이동
                dispose(); // 현재 창 닫기
                new CompleteGui(selectedDepartment);
            }
        });

        // '접수하기2' 버튼 ActionListener
        register_2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 환자 정보 저장
                savePatientInfo(secondTreatment);
                // completeGui로 이동
                dispose(); // 현재 창 닫기
                new CompleteGui(selectedDepartment);
            }
        });

        JLabel msg = new JLabel("가장 빠른 진료실로 예약됩니다");
        msg.setForeground(new Color(32, 178, 170));
        msg.setFont(new Font("나눔스퀘어 ExtraBold", Font.BOLD, 22));
        msg.setBackground(new Color(233, 233, 233));
        msg.setBounds(823, 580, 400, 30);
        getContentPane().add(msg);
        
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
        tag_3.setForeground(new Color(255, 255, 255));
        tag_3.setOpaque(true);
        tag_3.setHorizontalAlignment(SwingConstants.CENTER);
        tag_3.setFont(new Font("나눔스퀘어 ExtraBold", Font.BOLD, 20));
        tag_3.setBackground(new Color(32, 178, 170));
        tag_3.setBounds(600, 100, 300, 60);
        getContentPane().add(tag_3);
        
        JLabel tag_4 = new JLabel("4. 접수 완료");
        tag_4.setOpaque(true);
        tag_4.setHorizontalAlignment(SwingConstants.CENTER);
        tag_4.setFont(new Font("나눔스퀘어 ExtraBold", Font.BOLD, 20));
        tag_4.setBackground(UIManager.getColor("Button.background"));
        tag_4.setBounds(900, 100, 300, 60);
        getContentPane().add(tag_4);
        setBounds(100, 100, 1500, 800);
        setVisible(true);
        setLocationRelativeTo(null);

        button_back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new DepartmentGui();
            }
        });
    }

    // 대기 인원을 비교해서 더 작은 진료실의 정보 가져오는 메서드
    private String getPreferredTreatment() {
        String url = "jdbc:mysql://medihub.cfk4qw22eogv.us-east-1.rds.amazonaws.com:3306/medihub";
        String username = "admin";
        String password = "12345678";

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            // firstTreatment와 secondTreatment의 대기 인원을 가져옴
            int waitingNumber1 = getWaitingNumber(firstTreatment);
            int waitingNumber2 = getWaitingNumber(secondTreatment);

            // 대기 인원이 적은 진료실 선택
            String selectedOffice = (waitingNumber1 <= waitingNumber2) ? firstTreatment : secondTreatment;

            return selectedOffice;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return null;
    }

    // 환자 정보를 저장하는 메서드
    private void savePatientInfo(String treatment) {
        String url = "jdbc:mysql://medihub.cfk4qw22eogv.us-east-1.rds.amazonaws.com:3306/medihub";
        String username = "admin";
        String password = "12345678";

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String maxIdQuery = "SELECT MAX(id) FROM patients";

            try (PreparedStatement maxIdStatement = connection.prepareStatement(maxIdQuery);
                 ResultSet resultSet = maxIdStatement.executeQuery()) {

                int maxId = 0;

                if (resultSet.next()) {
                    maxId = resultSet.getInt(1);
                    System.out.println(maxId);
                }

                String updatePatientQuery = "UPDATE patients SET office = ? WHERE id = ?";
                try (PreparedStatement updatePatientStatement = connection.prepareStatement(updatePatientQuery)) {
                    updatePatientStatement.setString(1, treatment);
                    updatePatientStatement.setInt(2, maxId);
                    updatePatientStatement.executeUpdate();
                }

                String updateWaitingQuery = "UPDATE hospital SET waiting = waiting + 1 WHERE office = ?";
                try (PreparedStatement updateWaitingStatement = connection.prepareStatement(updateWaitingQuery)) {
                    updateWaitingStatement.setString(1, treatment);
                    updateWaitingStatement.executeUpdate();
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    // 대기 정보 갱신 메서드
    private void updateWaitingLabels() {
        // 대기 정보 가져오기
        int waitingNumber1 = getWaitingNumber(firstTreatment);
        int waitingNumber2 = getWaitingNumber(secondTreatment);

        // 대기 정보 출력
        waiting1.setText("대기환자는 " + waitingNumber1 + "명입니다. \n진료대기 시간은 " + (waitingNumber1 * 7) + "분 입니다.");
        waiting2.setText("대기환자는 " + waitingNumber2 + "명입니다. \n진료대기 시간은 " + (waitingNumber2 * 7) + "분 입니다.");
    }

    private int getWaitingNumber(String treatment) {
        String url = "jdbc:mysql://medihub.cfk4qw22eogv.us-east-1.rds.amazonaws.com:3306/medihub";
        String username = "admin";
        String password = "12345678";

        int waitingNumber = 0;

        try {
            // 실제 데이터베이스 연동 코드로 수정해주세요.
            Connection connection = DriverManager.getConnection(url, username, password);
            String query = "SELECT waiting FROM hospital WHERE office = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, treatment);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                waitingNumber = resultSet.getInt("waiting");
            }

            // 리소스 닫기 (Connection, PreparedStatement, ResultSet)
            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
            // 예외 처리를 적절히 수행하세요.
        }

        return waitingNumber;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TreatmentGui("selectedDepartment", "firstTreatment", "secondTreatment"));
    }
}
