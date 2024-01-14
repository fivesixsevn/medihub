package test;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Label;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.TextField;
import java.awt.Checkbox;
import java.awt.Button;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class PatientGui extends JFrame {

    private JButton btnBack;
    private KioskMain kioskMain;

    public PatientGui() {
        Font maruFont = new Font("나눔스퀘어 ExtraBold", Font.BOLD, 12);

        setFont(maruFont);
        getContentPane().setBackground(Color.WHITE);
        getContentPane().setLayout(null);

        // 변경된 부분: JButton으로 수정
        JButton button_next = new JButton("next");
        button_next.setBackground(new Color(32, 178, 170));
        button_next.setFont(new Font("나눔스퀘어 ExtraBold", Font.PLAIN, 27));
        button_next.setBounds(900, 620, 150, 60);
        getContentPane().add(button_next);

        // 변경된 부분: JButton으로 수정
        JButton button_back = new JButton("back");
        button_back.setBackground(new Color(240, 240, 240));
        button_back.setFont(new Font("나눔스퀘어 ExtraBold", Font.PLAIN, 27));
        button_back.setBounds(370, 620, 150, 60);
        getContentPane().add(button_back);

        // 변경된 부분: JLabel로 수정
        JLabel Medihub = new JLabel("Medihub");
        Medihub.setVerticalAlignment(SwingConstants.TOP);
        Medihub.setForeground(new Color(32, 178, 170));
        Medihub.setFont(new Font("나눔스퀘어 ExtraBold", Font.BOLD, 50));
        Medihub.setBounds(15, 15, 240, 75);
        getContentPane().add(Medihub);

        // 변경된 부분: JLabel로 수정
        JLabel label_name = new JLabel("1. 성함");
        label_name.setBackground(new Color(128, 255, 128));
        label_name.setFont(new Font("나눔스퀘어 ExtraBold", Font.BOLD, 30));
        label_name.setBounds(400, 288, 150, 51);
        getContentPane().add(label_name);

        // 변경된 부분: JTextField로 수정
        JTextField name = new JTextField();
        name.setBounds(700, 288, 349, 51);
        getContentPane().add(name);

        // 변경된 부분: JLabel로 수정
        JLabel label_number = new JLabel("2. 주민번호");
        label_number.setBackground(new Color(128, 255, 128));
        label_number.setFont(new Font("나눔스퀘어 ExtraBold", Font.BOLD, 30));
        label_number.setBounds(400, 410, 202, 44);
        getContentPane().add(label_number);

        // 변경된 부분: JTextField로 수정
        JTextField number = new JTextField();
        number.setBounds(700, 400, 349, 60);
        getContentPane().add(number);

        // 변경된 부분: JCheckBox로 수정
        JCheckBox checkboxAgree = new JCheckBox("  개인정보 동의(필수)");
        checkboxAgree.setBackground(Color.WHITE);
        checkboxAgree.setVerticalAlignment(SwingConstants.BOTTOM);
        checkboxAgree.setFont(new Font("나눔스퀘어 ExtraBold", Font.BOLD, 18));
        checkboxAgree.setBounds(605, 581, 233, 51);
        getContentPane().add(checkboxAgree);
        
        JLabel tag_1 = new JLabel("1. 환자 정보 입력");
        tag_1.setForeground(new Color(255, 255, 255));
        tag_1.setHorizontalAlignment(SwingConstants.CENTER);
        tag_1.setFont(new Font("나눔스퀘어 ExtraBold", Font.BOLD, 20));
        tag_1.setBackground(new Color(32, 178, 170));
        tag_1.setBounds(0, 110, 300, 60);
        tag_1.setBackground(new Color(32, 178, 170));
        tag_1.setOpaque(true);
        getContentPane().add(tag_1);
        
        JSeparator separator = new JSeparator();
        separator.setForeground(new Color(192, 192, 192));
        separator.setBounds(1200, 169, 600, 8);
        getContentPane().add(separator);
        
        JLabel tag_2 = new JLabel("2. 진료 과목 선택");
        tag_2.setHorizontalAlignment(SwingConstants.CENTER);
        tag_2.setFont(new Font("나눔스퀘어 ExtraBold", Font.BOLD, 20));
        tag_2.setBackground(new Color(240, 240, 240));
        tag_2.setBounds(300, 110, 300, 60);
        tag_2.setOpaque(true);
        getContentPane().add(tag_2);
        
        JLabel tag_3 = new JLabel("3. 진료 선생님 선택");
        tag_3.setHorizontalAlignment(SwingConstants.CENTER);
        tag_3.setFont(new Font("나눔스퀘어 ExtraBold", Font.BOLD, 20));
        tag_3.setBackground(new Color(240, 240, 240));
        tag_3.setBounds(600, 110, 300, 60);
        tag_3.setOpaque(true);
        getContentPane().add(tag_3);
        
        JLabel tag_4 = new JLabel("4. 접수 완료");
        tag_4.setHorizontalAlignment(SwingConstants.CENTER);
        tag_4.setFont(new Font("나눔스퀘어 ExtraBold", Font.BOLD, 20));
        tag_4.setBackground(new Color(240, 240, 240));
        tag_4.setBounds(900, 110, 300, 60);
        tag_4.setOpaque(true);
        getContentPane().add(tag_4);
        
        setBounds(100, 100, 1500, 800);
        setVisible(true);

        // 변경된 부분: ActionListener 내에서 KioskMain 객체를 사용하여 뒤로 가도록 변경
        button_next.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String enteredName = name.getText();
                String enteredNumber = number.getText();

                // 빈 값인지 확인
                if (enteredName.isEmpty() || enteredNumber.isEmpty()) {
                    System.out.println("값이 입력되지 않았습니다. 다음 단계로 진행할 수 없습니다.");
                    // 빈 값이면 여기서 리턴하거나, 사용자에게 메시지를 보여줄 수 있습니다.
                    return;
                }

                // checkbox가 체크되었는지 확인
                if (!checkboxAgree.isSelected()) {
                    System.out.println("개인정보 동의가 필요합니다. 동의하지 않으면 다음 단계로 진행할 수 없습니다.");
                    // 개인정보 동의가 필요한 경우 여기서 리턴하거나, 사용자에게 메시지를 보여줄 수 있습니다.
                    return;
                }

                // 값이 비어있지 않고 checkbox도 체크되었다면 데이터베이스에 저장
                saveToDatabase(enteredName, enteredNumber);

                dispose();
                new DepartmentGui();
            }
        });

        // 변경된 부분: ActionListener 내에서 KioskMain 객체를 사용하여 뒤로 가도록 변경
        button_back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                if (kioskMain == null) {
                    kioskMain = new KioskMain();
                }
                kioskMain.setVisible(true);
            }
        });

        setLocationRelativeTo(null);
    }

    private void saveToDatabase(String enteredName, String enteredNumber) {
        String url = "jdbc:mysql://medihub.cfk4qw22eogv.us-east-1.rds.amazonaws.com:3306/medihub";
        String username = "admin";
        String password = "12345678";

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            // 가장 큰 ID 값을 가져오는 쿼리
            String maxIdQuery = "SELECT MAX(id) FROM patients";

            try (PreparedStatement maxIdStatement = connection.prepareStatement(maxIdQuery)) {
                try (ResultSet resultSet = maxIdStatement.executeQuery()) {
                    int newId = 1; // 기본적으로 1로 초기화

                    if (resultSet.next()) {
                        // 결과가 있다면 가져온 가장 큰 ID 값에 1을 더해 새로운 ID를 생성
                        newId = resultSet.getInt(1) + 1;
                    }

                    // 새로운 ID 값과 사용자로부터 입력받은 값들을 데이터베이스에 저장하는 쿼리
                    String insertQuery = "INSERT INTO patients (id, name, number) VALUES (?, ?, ?)";
                    try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                        preparedStatement.setInt(1, newId);
                        preparedStatement.setString(2, enteredName);
                        preparedStatement.setString(3, enteredNumber);

                        int rowsAffected = preparedStatement.executeUpdate();
                        if (rowsAffected > 0) {
                            System.out.println("데이터베이스에 성공적으로 저장되었습니다.");
                        } else {
                            System.out.println("데이터베이스 저장에 실패하였습니다.");
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PatientGui());
    }
}
