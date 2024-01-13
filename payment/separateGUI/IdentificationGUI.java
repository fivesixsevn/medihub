import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;


public class IdentificationGUI extends JFrame {

	private JTextField textField;
	
	public IdentificationGUI(DataUpdater dataUpdater) {
		
		setBounds(100,100,1500,800);
		setBackground(Color.WHITE);
		setLayout(null);
    	
    	JLabel lb1 = new JLabel("본인 확인");
        lb1.setBounds(100, 100, 111, 43);
        lb1.setFont(new Font("나눔스퀘어 ExtraBold", Font.PLAIN, 28));
        getContentPane().add(lb1);
        
        JButton btnBack = new JButton("뒤로");
		btnBack.setBounds(100, 400, 100, 85);
		btnBack.setBackground(new Color(192, 192, 192));
		btnBack.setFont(new Font("나눔스퀘어 Bold", Font.PLAIN, 25));
		// 뒤로가기 버튼에 대한 이벤트 리스너 추가 : 버튼 클릭시 실행될 코드
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//back to home Screen.
				new KioskMain();
			}
		});
		
		JLabel lb = new JLabel("접수자 등록번호를 입력해주세요");      
		lb.setBounds(100, 150, 423, 43);
		lb.setFont(new Font("나눔스퀘어 Bold", Font.PLAIN, 25));
		getContentPane().add(lb);
// 상단 레이블
        JLabel topLabel = new JLabel("MediHub");
        topLabel.setBounds(400, 5,200,50);
        topLabel.setForeground(new Color(32, 178, 170));
        topLabel.setFont(new Font("맑은 고딕", Font.BOLD, 35));
        topLabel.setHorizontalAlignment(JLabel.CENTER);
        topLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // 가운데 정렬 추가
        getContentPane().add(topLabel);
		
		textField = new JTextField();
		textField.setBounds(100, 220, 266, 43);
		textField.setFont(new Font("나눔스퀘어 Bold", Font.PLAIN, 22));
		
		JLabel lb_checking = new JLabel("확인 지연 ...");
        lb_checking.setHorizontalAlignment(SwingConstants.CENTER);
        lb_checking.setForeground(new Color(0, 0, 0));
        lb_checking.setFont(new Font("나눔스퀘어 Light", Font.PLAIN, 20));
        lb_checking.setBounds(280, 220, 272, 32);
        lb_checking.setVisible(false); 
        getContentPane().add(lb_checking);
		
		JButton btnNext = new JButton("다음");
		btnNext.setBounds(211, 400, 155, 85);
		btnNext.setBackground(new Color(32, 178, 170));
		btnNext.setFont(new Font("나눔스퀘어 Bold", Font.PLAIN, 25));
		btnNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lb_checking.setVisible(true);
				String userInput = textField.getText().trim(); // textField에 입력된 값 가져오기
				DocumentItem searchPatient = DBSearch.searchRecord(userInput); // DBSearch 클래스의 메서드 호출
			
				if (userInput.isEmpty()) { // 입력값이 비어 있을 경우 - 팝업 
		            JOptionPane.showMessageDialog(null, "본인 확인 번호를 입력해 주세요.", "입력 오류", JOptionPane.WARNING_MESSAGE);
		        } else { 		            // 입력값이 비어 있지 않은 경우 - DBSearch 클래스의 메서드 호출
		            searchPatient = DBSearch.searchRecord(userInput);
		            System.out.println("<패널1> 회원 정보 검색중....");
		            
		            if (searchPatient.getName() != null) { 
		                // DB에서 레코드를 찾았을 경우 - 다음 화면
		                dispose();
		                new ReceiptGUI(dataUpdater, searchPatient);
		 
		            } else {
		            	System.out.println("<패널1> 회원 정보를 못찾았음요");
		                // DB에서 레코드를 찾지 못했을 경우: 팝업 띄우기
		                JOptionPane.showMessageDialog(null, "입력 내용을 다시 확인해주세요.", "정보 없음", JOptionPane.WARNING_MESSAGE);
		            }
		        }
			}
		});
		getContentPane().add(textField);
		textField.setColumns(10);
		getContentPane().add(btnBack);	
		getContentPane().add(btnNext);
		setVisible(true);
		
	}
}
