import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class ReceiptGUI extends JFrame { //수납 두 번째 화면
	
	private JTable table;
	private DefaultTableModel tableModel;
	private JLabel lb_name;
	private DefaultTableCellRenderer renderer;

	public ReceiptGUI(DocumentItem patient) {
		
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
		
		JLabel lb2 = new JLabel("영수증 확인");
        lb2.setBounds(640, 160, 163, 43);
        lb2.setFont(new Font("나눔스퀘어 ExtraBold", Font.PLAIN, 33));	
        getContentPane().add(lb2);
        
        lb_name = new JLabel("(님)의 결제 금액");
        lb_name.setHorizontalAlignment(SwingConstants.CENTER);
        lb_name.setForeground(new Color(0, 0, 0));
        lb_name.setFont(new Font("나눔스퀘어", Font.PLAIN, 25));
        lb_name.setBounds(480, 260, 272, 32);
        getContentPane().add(lb_name);

        // 테이블 모델 생성
        tableModel = new DefaultTableModel();
        // 테이블 생성 및 모델 설정
        table = new JTable(tableModel);
        // 데이터 표시할 헤더 열을 추가 (필요에 따라 수정)
        tableModel.addColumn("항목");
        tableModel.addColumn("값");
        table.setRowHeight(45);
        table.setFont(new Font("나눔스퀘어 Light", Font.PLAIN, 24));
        table.setBounds(500, 310, 464, 135);
        table.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        table.setSurrendersFocusOnKeystroke(true);
        getContentPane().add(table);
        
        // TableCellRenderer통해 특정 row에 대해서 색깔을 달리 설정
        renderer = new DefaultTableCellRenderer() {
        	@Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                // 마지막 3행만 따로 배경색 설정
                if (row == 2) {
                    component.setBackground(new Color(252, 247, 188)); // 원하는 배경색 setForeground(Color(252, 247, 188))
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
        btnPayNow.setFont(new Font("나눔스퀘어 ExtraBold", Font.BOLD, 29));
        btnPayNow.setBounds(620, 530, 200, 130);
        btnPayNow.setBackground(new Color(27, 188, 155));
        btnPayNow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				new PaymentGUI(patient); 
			}
		});
        getContentPane().add(btnPayNow);
        setVisible(true);
        if (patient != null) {
            //onDataUpdate(patient);
        	updateReceipt(patient);
        }
    }
    private void updateReceipt(DocumentItem data) {
        // JTable 모델 업데이트
        tableModel.setRowCount(0); // 기존 데이터 삭제

        lb_name.setText(data.getName()+" (님)의 결제 금액");
        tableModel.addRow(new Object[]{"진료비", data.getFee()});
        tableModel.addRow(new Object[]{"진단서", data.getMedicalReport()});
        tableModel.addRow(new Object[]{"총 금액", data.getAmount()});
    }
    
}
