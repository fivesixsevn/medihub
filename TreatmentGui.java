package test;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class TreatmentGui extends JFrame {
	
	private String selectedDepartment;
    private String firstTreatment;
    private String secondTreatment;
    
	public TreatmentGui(String selectedDepartment, String firstTreatment, String secondTreatment) {
		
			// 전달받은 값으로 클래스 변수 설정
		    this.selectedDepartment = selectedDepartment;
		    this.firstTreatment = firstTreatment;
		    this.secondTreatment = secondTreatment;
		
		
        	getContentPane().setBackground(Color.WHITE);
        	getContentPane().setLayout(null);
        	
        	Label pagecheck = new Label("this is Treatment");
        	pagecheck.setBounds(84, 181, 64, 22);
        	getContentPane().add(pagecheck);
        	
        	Button button_skip = new Button("skip");
        	button_skip.setBounds(993, 661, 72, 22);
        	getContentPane().add(button_skip);
        	
        	Button button_back = new Button("back");
        	button_back.setBounds(450, 650, 72, 22);
        	getContentPane().add(button_back);
        	
        	Label Medihub = new Label("Medihub");
        	Medihub.setForeground(new Color(0, 255, 128));
        	Medihub.setFont(new Font("Dialog", Font.BOLD, 50));
        	Medihub.setBounds(60, 30, 400, 100);
        	getContentPane().add(Medihub);
        	
        	// 선택한 부서, 진료 정보를 사용하여 GUI에서 필요한 대로 처리
            Label label_1 = new Label("진료실 " + firstTreatment);
            label_1.setBounds(230, 310, 64, 22);
            getContentPane().add(label_1);

            Label label_2 = new Label("진료실 " + secondTreatment);
            label_2.setBounds(230, 414, 64, 22);
            getContentPane().add(label_2);
        	
        	
        	Label waiting1 = new Label("대기 정보");
        	waiting1.setBounds(362, 310, 64, 22);
        	getContentPane().add(waiting1);
        	
        	Label waiting2 = new Label("대기정보 2");
        	waiting2.setBounds(362, 414, 64, 22);
        	getContentPane().add(waiting2);
        	
        	Button register_1 = new Button("접수하기");
        	register_1.setBounds(547, 310, 72, 22);
        	getContentPane().add(register_1);
        	
        	Button register_2 = new Button("접수하기2");
        	register_2.setBounds(547, 414, 72, 22);
        	getContentPane().add(register_2);
        	
        	// '접수하기' 버튼 ActionListener
            register_1.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // 대기 정보 가져오기
                    int waitingNumber = getWaitingNumber(firstTreatment);

                    // 대기 정보 출력
                    waiting1.setText("대기환자는 " + waitingNumber + "명입니다. \n진료대기 시간은 " + (waitingNumber * 7) + "분 입니다.");

                   
                }
            });

            // '접수하기2' 버튼 ActionListener
            register_2.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // 대기 정보 가져오기
                    int waitingNumber = getWaitingNumber(secondTreatment);

                    // 대기 정보 출력
                    waiting2.setText("대기환자는 " + waitingNumber + "명입니다. \n진료대기 시간은 " + (waitingNumber * 7) + "분 입니다.");

                    // 여기서 진료 등록 등 추가 로직을 수행할 수 있습니다.
                }
            });
        	
        	Label msg = new Label("가장 빠른 진료실로 예약됩니다.");
        	msg.setBackground(new Color(233, 233, 233));
        	msg.setBounds(850, 603, 64, 22);
        	getContentPane().add(msg);
            setBounds(100,100,1500,800);
            setVisible(true);
            
            button_back.addActionListener(new ActionListener()
    		{
    			@Override
    			public void actionPerformed(ActionEvent e) {
    				dispose();
    				new DepartmentGui();
    			}
    	
    		});
            
            button_skip.addActionListener(new ActionListener()
    		{
    			@Override
    			public void actionPerformed(ActionEvent e) {
    				dispose();
    				new CompleteGui();
    			}
    	
    		});
    }
	
	private int getWaitingNumber(String treatment) {
		String url = "jdbc:mysql://medihub.cfk4qw22eogv.us-east-1.rds.amazonaws.com:3306/medihub";
        String username = "admin";
        String password = "12345678";
        
        int waitingNumber = 0;

        try {
            // 실제 데이터베이스 연동 코드로 수정해주세요.
            Connection connection = DriverManager.getConnection(url, username, password)
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
}
