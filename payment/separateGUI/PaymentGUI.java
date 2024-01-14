import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;

public class PaymentGUI extends JFrame { //수납 세 번째 화면

	public PaymentGUI(DocumentItem patient) {

		setBounds(100,100,1500,800);
		setBackground(Color.WHITE);
		setLocationRelativeTo(null); //window창을 화면 가운데 띄우는 역할
		getContentPane().setLayout(null);
	
		JLabel topLabel = new JLabel("MediHub");
        topLabel.setBounds(15, 15,240,55);
        topLabel.setForeground(new Color(32, 178, 170));
        topLabel.setFont(new Font("나눔스퀘어 ExtraBold", Font.BOLD, 50));
        topLabel.setHorizontalAlignment(JLabel.CENTER);
        topLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // 가운데 정렬 추가
        getContentPane().add(topLabel);
        
        JSeparator separator = new JSeparator();
		separator.setBounds(0, 90, 1500, 2);
		getContentPane().add(separator);
		
		JLabel lb3 = new JLabel("결제하기");
		lb3.setBounds(640, 140, 163, 43);
        lb3.setFont(new Font("나눔스퀘어 ExtraBold", Font.PLAIN, 33));
        getContentPane().add(lb3);
        
        JLabel lb_selection = new JLabel("결제 수단을 선택하세요.");
        lb_selection.setBounds(580, 200, 423, 43);
        lb_selection.setFont(new Font("나눔스퀘어", Font.PLAIN, 25));
        getContentPane().add(lb_selection);
        
        JButton btnCard = new JButton("카드 결제");        
        btnCard.setBounds(480, 260, 200, 200);
        btnCard.setFont(new Font("나눔스퀘어 ExtraBold", Font.PLAIN, 29));
        btnCard.setBackground(new Color(27, 188, 155));
        
        JButton btnEPay = new JButton("간편 결제");
        btnEPay.setBounds(710, 260, 200, 200);
        btnEPay.setFont(new Font("나눔스퀘어 ExtraBold", Font.PLAIN, 29));
        btnEPay.setEnabled(false);
        
        JProgressBar pgBar = new JProgressBar();
        pgBar.setBounds(485, 485, 420, 17);
        pgBar.setForeground(new Color(27, 188, 155));
        pgBar.setStringPainted(true); //문자열로 퍼센트 표시
        
        JLabel lb_completion = new JLabel("결제가 완료되었습니다 !");
        lb_completion.setBounds(550, 520, 272, 32);
        lb_completion.setText(patient.getAmount()+"(원) 결제가 완료되었습니다 !");
        lb_completion.setHorizontalAlignment(SwingConstants.CENTER);
        lb_completion.setForeground(new Color(0, 0, 0));
        lb_completion.setFont(new Font("나눔스퀘어", Font.PLAIN, 18));
        
        JButton btnPrescription = new JButton("처방전 확인");
        btnPrescription.setBounds(600, 580, 200, 120);
        btnPrescription.setFont(new Font("나눔스퀘어 ExtraBold", Font.PLAIN, 25));
        
        pgBar.setVisible(false);  //초기 상태 = 안보이게
        lb_completion.setVisible(false); 
        btnPrescription.setVisible(false);
        
        btnCard.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	
            	pgBar.setVisible(true);
                pgBar.setStringPainted(true); // 퍼센트 보이게

                SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
                    @Override
                    protected Void doInBackground() throws Exception {
                        for (int i = 0; i <= 10; i++) {
                            int progressValue = i * 10; // progressValue 선언 및 초기화
                            pgBar.setValue(progressValue);
                            pgBar.setString(progressValue + "%");
                            Thread.sleep(160); // 200ms(=2초) 대기
                        }
                        return null;
                    }
                    
                    @Override
                    protected void done() {
                        lb_completion.setVisible(true);
                        btnPrescription.setVisible(true);
                    }
                    
                };
                worker.execute();
            }
        });    
        
        getContentPane().add(btnCard);
        getContentPane().add(btnEPay);
        getContentPane().add(pgBar);
        getContentPane().add(lb_completion);
        getContentPane().add(btnPrescription);
        btnPrescription.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (patient != null) {
	            			updatePaymentState(patient.getID());
	            			updateWaiting(patient.getOffice());
	            		}
				dispose();
				new PrescriptionGUI(patient);
			}
		});
        
        setVisible(true);
	}
    private void updatePaymentState(int idNum) { //접근지정자 고민 // 매개변수로 getID()를 넘겨주게 호출
        String url = "jdbc:mysql://medihub.cfk4qw22eogv.us-east-1.rds.amazonaws.com:3306/medihub";
        String username = "admin";
        String password = "12345678";

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            
            // 결제까지 완료한 환자를 반영해 데이터베이스에 대기인원 수를 갱신(감소)하는 쿼리
            String afterPayQuery = "update patients set payment = ? where id = ?";
                    try (PreparedStatement preparedStatement = connection.prepareStatement(afterPayQuery)) {
                    preparedStatement.setString(1, "O");
                    preparedStatement.setInt(2, idNum);

                    int rowsAffected = preparedStatement.executeUpdate(); //  < ?잘모르겟음
                    if (rowsAffected > 0) {
                        System.out.println("데이터베이스에 성공적으로 저장되었습니다.");
                    } else {
                        System.out.println("데이터베이스 저장에 실패하였습니다.");
                    }
                }
            } catch (Exception e) {
            	e.printStackTrace();
            }
    }
    private void updateWaiting(String office) { //접근지정자 고민
        String url = "jdbc:mysql://medihub.cfk4qw22eogv.us-east-1.rds.amazonaws.com:3306/medihub";
        String username = "admin";
        String password = "12345678";
        System.out.println(office);
        office = office.substring(0,1);

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
			String query = "select waiting from hospital where office = ?";

            try (PreparedStatement waitingStmt = connection.prepareStatement(query)) {
				waitingStmt.setString(1, office);

                try (ResultSet resultSet = waitingStmt.executeQuery()) {
                	int waitingNum = 0; // 기본적으로 0으로 초기화
                	if (resultSet.next())  {// 결과가 있다면
                        waitingNum = resultSet.getInt(1) - 1; //현재 대기인수에 -1 하여 서비스 마친 환자 반영
                        System.out.println(office+" 진료실 대기인원 수 ="+waitingNum);
                	}

                    // 데이터베이스에 대기인원 수를 갱신(감소)하는 쿼리 for 결제까지 완료한 환자 반영
                    String afterPayQuery = "update hospital set waiting = ? where office = ?";
                    try (PreparedStatement preparedStatement = connection.prepareStatement(afterPayQuery)) {
                        preparedStatement.setInt(1, waitingNum);
                        preparedStatement.setString(2, office);

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
