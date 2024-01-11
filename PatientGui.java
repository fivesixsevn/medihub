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


public class PatientGui extends JFrame {

    private JButton btnBack;
    private KioskMain kioskMain;

    public PatientGui() {
    
    	getContentPane().setBackground(Color.WHITE);
    	getContentPane().setLayout(null);
    	
    	Label pagecheck_patient = new Label("this is patient");
    	pagecheck_patient.setBounds(84, 181, 64, 22);
    	getContentPane().add(pagecheck_patient);
    	
    	Button button_next = new Button("next");
    	button_next.setBounds(993, 661, 72, 22);
    	getContentPane().add(button_next);
    	
    	Button button_back = new Button("back");
    	button_back.setBounds(450, 650, 72, 22);
    	getContentPane().add(button_back);
    	
    	Label Medihub = new Label("Medihub");
    	Medihub.setForeground(new Color(0, 255, 128));
    	Medihub.setFont(new Font("Dialog", Font.BOLD, 50));
    	Medihub.setBounds(60, 30, 400, 100);
    	getContentPane().add(Medihub);
    	
    	Label label_name = new Label("1. 성함");
    	label_name.setBounds(304, 288, 64, 22);
    	getContentPane().add(label_name);
    	
    	TextField name = new TextField();
    	name.setBounds(384, 288, 24, 22);
    	getContentPane().add(name);
    	
    	Label label_number = new Label("2. 주민번호");
    	label_number.setBounds(304, 363, 64, 22);
    	getContentPane().add(label_number);
    	
    	TextField number = new TextField();
    	number.setBounds(384, 363, 24, 22);
    	getContentPane().add(number);
    	
    	Checkbox checkboxAgree = new Checkbox("개인정보 동의");
    	checkboxAgree.setBounds(528, 602, 98, 22);
    	getContentPane().add(checkboxAgree);
        setBounds(100,100,1500,800);
        setVisible(true);
        
        button_back.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				new KioskMain();
			}
	
		});
        
        button_next.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) {
				String enteredName = name.getText();
		        String enteredNumberText = number.getText();

		        // 빈 값인지 확인
		        if (enteredName.isEmpty() || enteredNumberText.isEmpty()) {
		            System.out.println("값이 입력되지 않았습니다. 다음 단계로 진행할 수 없습니다.");
		            // 빈 값이면 여기서 리턴하거나, 사용자에게 메시지를 보여줄 수 있습니다.
		            return;
		        }

		        // 값이 비어있지 않으면 데이터베이스에 저장
		       
		        int enteredNumber = Integer.parseInt(enteredNumberText);
		        saveToDatabase(enteredName, enteredNumber);
				//saveToDatabase(name.getText(), Integer.parseInt(number.getText()));
		        
		        
				dispose();
				new DepartmentGui();
			}
	
		});
        
       
    }
    
    
    
    // db에 저장하는 코드
    private void saveToDatabase(String enteredName, int enteredNumber) {
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
                        preparedStatement.setInt(3, enteredNumber);

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
}
