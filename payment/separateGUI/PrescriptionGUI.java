import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class PrescriptionGUI extends JFrame{
	private JTable table;
	private DefaultTableModel tableModel;
	private DefaultTableCellRenderer renderer;
	
	private LocalDate date = LocalDate.now();
	
	public PrescriptionGUI(DocumentItem patient) {
		
		setBounds(100,100,1500,800);
		setBackground(Color.WHITE);
		getContentPane().setLayout(null);
		setLocationRelativeTo(null); //window창을 화면 가운데 띄우는 역할

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
		
		JLabel lb4 = new JLabel("처방전 확인");
        lb4.setBounds(640, 140, 180, 47);
        lb4.setFont(new Font("나눔스퀘어 ExtraBold", Font.PLAIN, 33));
        getContentPane().add(lb4);
        
        // 테이블 모델 생성
        tableModel = new DefaultTableModel();
        // 테이블 생성 및 모델 설정
        table = new JTable(tableModel);
        // 데이터 표시할 헤더 열을 추가 (필요에 따라 수정)
        tableModel.addColumn("항목");
        tableModel.addColumn("내용");
        table.setRowHeight(45);
		table.setFont(new Font("굴림체", Font.PLAIN, 23));
		table.setBounds(420, 210, 587, 315);
		table.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		table.setSurrendersFocusOnKeystroke(true);
		getContentPane().add(table);       
     // TableCellRenderer통해 특정 row에 대해서 색깔을 달리 설정
        renderer = new DefaultTableCellRenderer() {
        	@Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                // 5, 6행만 따로 배경색 설정
                if (row >= 5) {
                    component.setBackground(new Color(252, 247, 188)); // 원하는 배경색
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
        btnClose.setBounds(545, 580, 155, 120);
        btnClose.setBackground(new Color(192, 192, 192));
        btnClose.setFont(new Font("나눔스퀘어 ExtraBold", Font.PLAIN, 25));
        btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				new KioskMain(); //홈 화면
			}
		});
		JButton btnPharmacy = new JButton("주변 약국 보기");
		btnPharmacy.setBounds(720, 580, 201, 120);
		btnPharmacy.setBackground(new Color(27, 188, 155));
		btnPharmacy.setFont(new Font("나눔스퀘어 ExtraBold", Font.PLAIN, 25));
		btnPharmacy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				//주변 약국보기 페이지로 넘어가기
				new PharmacyApp();
			}
		});
		getContentPane().add(btnClose);
		getContentPane().add(btnPharmacy);
		
		setVisible(true);
		if (patient != null) {
        		updatePreScription(patient);
        	}
    }
    private void updatePreScription(DocumentItem data) {
        // JTable 모델 업데이트
        tableModel.setRowCount(0); // 기존 데이터 삭제
		
        tableModel.addRow(new Object[]{"환자명", data.getName()});
        tableModel.addRow(new Object[] {"교부일", date});
        tableModel.addRow(new Object[] {"발행기관", "객체지향 종합병원"});
        tableModel.addRow(new Object[]{"진료과목", data.getSubject()}); 
        tableModel.addRow(new Object[]{"담당의", data.getOffice()});
        tableModel.addRow(new Object[]{"처방내역", data.getMedicine()});
        tableModel.addRow(new Object[]{"복약정보", data.getDoses()});
    }
    	
}
