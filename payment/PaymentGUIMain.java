package oop_kiosk_medihub;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;

public class MainFrame {
	
	private JFrame frame;
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private DataUpdater dataUpdater;

    public MainFrame() {
        initialize();
    }

    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 1000, 700);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        cardLayout = new CardLayout(); // 패널간 화면전환을 위한 레이아웃
        cardPanel = new JPanel(cardLayout);

        dataUpdater = new DataUpdater();

        Panel1 panel1 = new Panel1(dataUpdater, cardLayout, cardPanel);
        Panel2 panel2 = new Panel2(dataUpdater, cardLayout, cardPanel);
        Panel3 panel3 = new Panel3(dataUpdater, cardLayout, cardPanel);
        Panel4 panel4 = new Panel4(dataUpdater, cardLayout, cardPanel);
        // Add other panels as needed...

        cardPanel.add(panel1.getPanel(), "panel1");
        cardPanel.add(panel2.getPanel(), "panel2");
        cardPanel.add(panel3.getPanel(), "panel3");
        cardPanel.add(panel4.getPanel(), "panel4");
        // Add other panels as needed...

        frame.getContentPane().add(cardPanel, BorderLayout.CENTER);

        // Set the initial panel to be shown
        cardLayout.show(cardPanel, "panel1");
    }

    public void showFrame() {
        EventQueue.invokeLater(() -> {
            try {
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                MainFrame window = new MainFrame();
                window.showFrame();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}

class Panel1 {// 첫 번째 패널 (첫 번째 화면)
	private JPanel panel1;
	private JTextField textField;
	private JFrame frame;
	
	public Panel1(DataUpdater dataUpdater, CardLayout cardLayout, JPanel cardPanel) {
		initialize(dataUpdater, cardLayout, cardPanel);
		
	}
	public void initialize(DataUpdater dataUpdater, CardLayout cardLayout, JPanel cardPanel) {
        panel1 = new JPanel();
        panel1.setForeground(new Color(192, 192, 192));
        panel1.setLayout(null);
        JLabel lb1 = new JLabel("본인 확인");
        lb1.setBounds(100, 100, 111, 43);
        lb1.setFont(new Font("나눔스퀘어 ExtraBold", Font.PLAIN, 28));
        panel1.add(lb1);
        
        JButton btnBack = new JButton("뒤로");
		btnBack.setBounds(100, 400, 100, 85);
		btnBack.setBackground(new Color(192, 192, 192));
		btnBack.setFont(new Font("나눔스퀘어 Bold", Font.PLAIN, 25));
		// 뒤로가기 버튼에 대한 이벤트 리스너 추가 : 버튼 클릭시 실행될 코드
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
//**				//back to home Screen.
			}
		});
		
		JLabel lb = new JLabel("접수자 등록번호를 입력해주세요");      
		lb.setBounds(100, 150, 423, 43);
		lb.setFont(new Font("나눔스퀘어 Bold", Font.PLAIN, 25));
		panel1.add(lb);
// 상단 레이블
        JLabel topLabel = new JLabel("MediHub");
        topLabel.setBounds(400, 5,200,50);
        topLabel.setForeground(new Color(18, 222, 191));
        topLabel.setFont(new Font("맑은 고딕", Font.BOLD, 35));
        topLabel.setHorizontalAlignment(JLabel.CENTER);
        topLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // 가운데 정렬 추가
        panel1.add(topLabel);
		
		textField = new JTextField();
		textField.setBounds(100, 220, 266, 43);
		textField.setFont(new Font("나눔스퀘어 Bold", Font.PLAIN, 22));
		
		JLabel lb_checking = new JLabel("확인 지연 ...");
        lb_checking.setHorizontalAlignment(SwingConstants.CENTER);
        lb_checking.setForeground(new Color(0, 0, 0));
        lb_checking.setFont(new Font("나눔스퀘어 Light", Font.PLAIN, 20));
        lb_checking.setBounds(280, 220, 272, 32);
        lb_checking.setVisible(false); 
        panel1.add(lb_checking);
		
		JButton btnNext = new JButton("다음");
		btnNext.setBounds(211, 400, 155, 85);
		btnNext.setBackground(new Color(27, 188, 155));
		btnNext.setFont(new Font("나눔스퀘어 Bold", Font.PLAIN, 25));
		btnNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lb_checking.setVisible(true);
				String userInput = textField.getText().trim(); // textField에 입력된 값 가져오기
				DocumentItem searchPatient = DBSearch.searchRecord(userInput); // DBSearch 클래스의 메서드 호출
			
				if (userInput.isEmpty()) { // 입력값이 비어 있을 경우 - 팝업 
		            JOptionPane.showMessageDialog(null, "본인 확인 번호를 입력해 주세요.", "입력 오류", JOptionPane.WARNING_MESSAGE);
		        } else { 		            // 입력값이 비어 있지 않은 경우, DBSearch 클래스의 메서드 호출
		            searchPatient = DBSearch.searchRecord(userInput);
		            System.out.println("<패널1> 회원 정보 검색중....");
		            if (searchPatient.getName() != null) { //searchPatient.get~ != null로 시도해보기
		                // DB에서 레코드를 찾았을 경우
		            	// 데이터 업데이트
		                dataUpdater.updateData(searchPatient);
		                // 다음 화면으로 이동
		                cardLayout.show(cardPanel, "panel2");
		 
		            } else {
		            	System.out.println("<패널1> 회원 정보를 못찾았음요");
		                // DB에서 레코드를 찾지 못했을 경우: 팝업 띄우기
		                JOptionPane.showMessageDialog(null, "입력 내용을 다시 확인해주세요.", "정보 없음", JOptionPane.WARNING_MESSAGE);
		            }
		        }
			}
		});
		panel1.add(textField);
		textField.setColumns(10);
		panel1.add(btnBack);	
		panel1.add(btnNext);
	}
	public JPanel getPanel() {
		return panel1;
	}
}

class Panel2 {
	private JPanel panel2;
	private JTable table;
	private DefaultTableModel tableModel;
	private JLabel lb_name;
	private DefaultTableCellRenderer renderer;
	private DataUpdateListener listener;

    public Panel2(DataUpdater dataUpdater, CardLayout cardLayout, JPanel cardPanel) {
        initialize(dataUpdater, cardLayout, cardPanel);
    }

    private void initialize(DataUpdater dataUpdater, CardLayout cardLayout, JPanel cardPanel) {
        panel2 = new JPanel();
        panel2.setLayout(null);
        JLabel lb2 = new JLabel("영수증 확인");
        lb2.setBounds(100, 100, 163, 43);
        lb2.setFont(new Font("나눔스퀘어 ExtraBold", Font.PLAIN, 28));	
        panel2.add(lb2);
        
        lb_name = new JLabel("(님)의 결제 금액");
        lb_name.setHorizontalAlignment(SwingConstants.CENTER);
        lb_name.setForeground(new Color(0, 0, 0));
        lb_name.setFont(new Font("나눔스퀘어 Light", Font.PLAIN, 20));
        lb_name.setBounds(60, 160, 272, 32);
        panel2.add(lb_name);

        // 테이블 모델 생성
        tableModel = new DefaultTableModel();
        // 테이블 생성 및 모델 설정
        table = new JTable(tableModel);
        // 데이터 표시할 헤더 열을 추가 (필요에 따라 수정)
        tableModel.addColumn("항목");
        tableModel.addColumn("값");
        table.setRowHeight(45);
        table.setFont(new Font("나눔스퀘어 Light", Font.PLAIN, 24));
        table.setBounds(100, 200, 464, 135);
        table.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        table.setSurrendersFocusOnKeystroke(true);
        panel2.add(table);
        
        // TableCellRenderer통해 특정 row에 대해서 색깔을 달리 설정
        renderer = new DefaultTableCellRenderer() {
        	@Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                // 마지막 3행만 따로 배경색 설정
                if (row == 2) {
                    component.setBackground(Color.YELLOW); // 원하는 배경색
                } else {
                    component.setBackground(table.getBackground());
                }
                return component;
            }
        };
        // 테이블에 DefaultTableCellRenderer 적용
        for (int columnIndex = 0; columnIndex < table.getColumnCount(); columnIndex++) {
            table.getColumnModel().getColumn(columnIndex).setCellRenderer(renderer);
            
        }
        
        JButton btnPayNow = new JButton("결제하기");
        btnPayNow.setFont(new Font("나눔스퀘어 Bold", Font.PLAIN, 25));
        btnPayNow.setBounds(100, 400, 151, 85);
        btnPayNow.setBackground(new Color(27, 188, 155));
        btnPayNow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(cardPanel, "panel3");
			}
		});
        panel2.add(btnPayNow);
        
        // 패널1에서 데이터 업데이트 시 호출될 메서드 설정
        dataUpdater.setListener(new DataUpdateListener() {
            @Override
            public void onDataUpdate(DocumentItem data) {
                // 업데이트된 데이터를 사용하여 JTable 모델 업데이트
            	updateReceipt(data);
            	//System.out.println("<패널2>"+data.getName());
            }
        });
    }
    private void updateReceipt(DocumentItem data) {
        // JTable 모델 업데이트
        tableModel.setRowCount(0); // 기존 데이터 삭제

        lb_name.setText(data.getName()+" (님)의 결제 금액");
        tableModel.addRow(new Object[]{"진료비", data.getFee()});
        tableModel.addRow(new Object[]{"진단서", data.getMedicalReport()});
        tableModel.addRow(new Object[]{"총 금액", data.getAmount()});
    }

    public JPanel getPanel() {
        return panel2;
    }
}
class Panel3 {
	private JPanel panel3;
	
	public Panel3(DataUpdater dataUpdater, CardLayout cardLayout, JPanel cardPanel) {
        initialize(dataUpdater, cardLayout, cardPanel);
    }

    private void initialize(DataUpdater dataUpdater, CardLayout cardLayout, JPanel cardPanel) {
        panel3 = new JPanel();
        panel3.setLayout(null);
        JLabel lb3 = new JLabel("결제하기");
        lb3.setFont(new Font("나눔스퀘어 ExtraBold", Font.PLAIN, 28));
        lb3.setBounds(100, 100, 163, 43);
        panel3.add(lb3);
        
        JLabel lb_selection = new JLabel("결제 수단을 선택하세요.");
        lb_selection.setFont(new Font("나눔스퀘어 Bold", Font.PLAIN, 25));
        lb_selection.setBounds(100, 162, 423, 43);
        panel3.add(lb_selection);
        JButton btnCard = new JButton("카드 결제");        
        btnCard.setFont(new Font("나눔스퀘어 Bold", Font.PLAIN, 24));
        btnCard.setBounds(100, 250, 137, 120);
        btnCard.setBackground(new Color(27, 188, 155));
        JButton btnEPay = new JButton("간편 결제");
        btnEPay.setFont(new Font("나눔스퀘어 Bold", Font.PLAIN, 24));
        btnEPay.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        	}
        });
        btnEPay.setBounds(250, 250, 129, 120);
        btnEPay.setEnabled(false);
        
        JProgressBar pgBar = new JProgressBar();
        pgBar.setForeground(new Color(27, 188, 155));
        pgBar.setLocation(100, 391);
        pgBar.setSize(279, 17);
        pgBar.setStringPainted(true); //문자열로 퍼센트 표시
        JLabel lb_completion = new JLabel("결제가 완료되었습니다 !");
        lb_completion.setHorizontalAlignment(SwingConstants.CENTER);
        lb_completion.setForeground(new Color(0, 0, 0));
        lb_completion.setFont(new Font("나눔스퀘어 Light", Font.PLAIN, 20));
        lb_completion.setBounds(100, 418, 272, 32);
        JButton btnPrescription = new JButton("처방전 확인");
        btnPrescription.setFont(new Font("나눔스퀘어 Bold", Font.PLAIN, 24));
        btnPrescription.setBounds(145, 461, 181, 96);
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
                worker.execute();            }
        });        
        panel3.add(btnCard);
        panel3.add(btnEPay);
        panel3.add(pgBar);
        panel3.add(lb_completion);
        panel3.add(btnPrescription);
        btnPrescription.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(cardPanel, "panel4");
			}
		});      
    }
	public JPanel getPanel() {
        return panel3;
    }
}

class Panel4 {
	private JPanel panel4;
	private JTable table;
	private DefaultTableModel tableModel;
	private DefaultTableCellRenderer renderer;
	private LocalDate date = LocalDate.now();
	
	public Panel4(DataUpdater dataUpdater, CardLayout cardLayout, JPanel cardPanel) {
        initialize(dataUpdater, cardLayout, cardPanel);
    }

    private void initialize(DataUpdater dataUpdater, CardLayout cardLayout, JPanel cardPanel) {
        panel4 = new JPanel();
        panel4.setLayout(null);
        JLabel lb4 = new JLabel("처방전 확인");
        lb4.setBounds(100, 100, 135, 32);
        lb4.setFont(new Font("나눔스퀘어 ExtraBold", Font.PLAIN, 28));
        panel4.add(lb4);
        
        // 테이블 모델 생성
        tableModel = new DefaultTableModel();
        // 테이블 생성 및 모델 설정
        table = new JTable(tableModel);
        // 데이터 표시할 헤더 열을 추가 (필요에 따라 수정)
        tableModel.addColumn("항목");
        tableModel.addColumn("내용");
        table.setRowHeight(45);
		table.setFont(new Font("굴림체", Font.PLAIN, 23));
		table.setBounds(100, 150, 587, 315);
		table.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		table.setSurrendersFocusOnKeystroke(true);
        panel4.add(table);       
     // TableCellRenderer통해 특정 row에 대해서 색깔을 달리 설정
        renderer = new DefaultTableCellRenderer() {
        	@Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                // 5, 6행만 따로 배경색 설정
                if (row >= 5) {
                    component.setBackground(Color.YELLOW); // 원하는 배경색
                } else {
                    component.setBackground(table.getBackground());
                }
                return component;
            }
        };
        // 테이블에 DefaultTableCellRenderer 적용
        for (int columnIndex = 0; columnIndex < table.getColumnCount(); columnIndex++) {
            table.getColumnModel().getColumn(columnIndex).setCellRenderer(renderer);
            
        }
        
        JButton btnClose = new JButton("확인");
        btnClose.setBounds(100, 500, 155, 85);
        btnClose.setBackground(new Color(192, 192, 192));
        btnClose.setFont(new Font("나눔스퀘어 Bold", Font.PLAIN, 25));
        btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//back to home Screen. 메인 클래스 객체 
			}
		});
		JButton btnPharmacy = new JButton("주변 약국 보기");
		btnPharmacy.setBounds(260, 500, 201, 85);
		btnPharmacy.setBackground(new Color(27, 188, 155));
		btnPharmacy.setFont(new Font("나눔스퀘어 Bold", Font.PLAIN, 25));
		btnPharmacy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//주변 약국보기 페이지로 넘어가기
				new PharmacyApp();
			}
		});
		panel4.add(btnClose);
		panel4.add(btnPharmacy);
		
		
		dataUpdater.setListener(new DataUpdateListener() {
            @Override
            public void onDataUpdate(DocumentItem data) {
                // 업데이트된 데이터를 사용하여 JTable 모델 업데이트
            	updatePreScription(data);
            	//System.out.println("<패널4>"+data.getName());
            }
        });
    }
    private void updatePreScription(DocumentItem data) {
        // JTable 모델 업데이트
        tableModel.setRowCount(0); // 기존 데이터 삭제
		
        tableModel.addRow(new Object[]{"환자명", data.getName()});
        tableModel.addRow(new Object[] {"교부일", date});
        tableModel.addRow(new Object[] {"발행기관", "567+ 종합병원"});
        tableModel.addRow(new Object[]{"진료과목", data.getSubject()}); 
        tableModel.addRow(new Object[]{"담당의", data.getOffice()});
        tableModel.addRow(new Object[]{"처방내역", data.getMedicine()});
        tableModel.addRow(new Object[]{"복약정보", data.getDoses()});
    }

	public JPanel getPanel() {
        return panel4;
    }
}
