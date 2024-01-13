package oop_kiosk_medihub;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import java.util.*;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

public class PatientsManage {
	
	public JFrame frame;
	private JTextField searchTextField;
	private DefaultTableModel tableModel;  // 테이블 데이터 편집하기 위해 필요
	private JTable table;
	private JTextField idLabel;
	private JTextField nameLabel;
	private JTextField numberLabel;
	private JTextField medicalreportLabel;
	private JTextField paymentLabel;
	private JTextField officeLabel;
	private JTextField diseaseLabel;
	private JTextField medicineLabel;
	private JTextField dosesLabel;
	private JTextField feeLabel;
	
	private JTextField idTextfield;
	private JTextField nameTextfield;
	private JTextField numberTextfield;
	private JTextField medicalreportTextfield;
	private JTextField paymentTextfield;
	private JTextField officeTextfield;
	private JTextField diseaseTextfield;
	private JTextField medicineTextfield;
	private JTextField dosesTextfield;
	private JTextField feeTextfield;
	
	private JButton btnSave;
	private JButton btnCancel;
	private JPanel panelTop;
	private JPanel panelMedium;
	private JPanel panelBottom;
	private JPanel tablePanel;
	
	private Patients patients;
	private String[][] data;

	private JButton btnDelete;
	private JButton btnUpdate; 
	
	private int selectedIdx;

	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {    // ?? invokeLater 찾기
			public void run() {
				try {
					PatientsManage window = new PatientsManage();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public PatientsManage() {
		initialize();
		showTable();
		deleteTableRow();
		updateTableRow();
		saveData();
		cancelDate();
	}

	private void initialize() {

		patients = new Patients();

		/* 패널 배치하기 */
		frame = new JFrame("환자 관리");    						// 메인 프레임
		frame.setSize(1500, 800);								// 프레임 크기
		frame.setBackground(Color.WHITE);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	// 종료 버튼 누르면 프로그램 종료
		frame.setLocationRelativeTo(null);						// 프레임을 화면 중앙에 배치
		frame.setResizable(false);								// 프레임 크기를 변경하지 못하도록 설정
		frame.getContentPane().setLayout(null);					// 프레임에 추가되는 컴포넌트 레이아웃 -> Absolute

		panelTop = new JPanel();								// 상단 패널
		panelTop.setBackground(Color.WHITE);
		panelTop.setBounds(1, 1, 1500, 80);						// 패널 위치와 크기  -> (x좌표, y좌표, 넓이, 높이)
		panelTop.setLayout(null);								// 상단 패널에 추가되는 컴포넌트 레이아웃 -> Absolute
		frame.getContentPane().add(panelTop);					// 프레임에 추가하기
		panelTop.setVisible(true);								// 패널 보이기
		
		panelMedium = new JPanel();								// 중간 패널
		panelMedium.setBackground(Color.WHITE);
		panelMedium.setBounds(1, 75, 1500, 300);				// 패널 위치와 크기  -> (x좌표, y좌표, 넓이, 높이)
		panelMedium.setLayout(null);							// 상단 패널에 추가되는 컴포넌트 레이아웃 -> Absolute
		frame.getContentPane().add(panelMedium);				// 프레임에 추가하기
		panelMedium.setVisible(true);							// 패널 보이기

		panelBottom = new JPanel();								// 하단 패널
		panelBottom.setBackground(Color.WHITE);
		panelBottom.setBounds(1, 372, 1500, 400);				// 패널 위치와 크기  -> (x좌표, y좌표, 넓이, 높이)
		panelBottom.setLayout(null);							// 하단 패널에 추가되는 컴포넌트 레이아웃 -> Absolute
		frame.getContentPane().add(panelBottom);				// 프레임에 추가하기
		panelBottom.setVisible(false);  						// 하단 패널 가리기

		tablePanel = new JPanel();								// 테이블 패널 생성
		tablePanel.setBackground(Color.WHITE);
		tablePanel.setBounds(1, 130, 1300, 150);				// 테이블 패널 위치와 크기
		frame.getContentPane().add(tablePanel);					// 테이블 패널 추가


		/* 검색 필드 배치하기 */
		JButton btnSearch = new JButton("검색어 입력"); 			// 검색어 입력 레이블을 버튼으로 구현(버튼 모양이 레이블보다 예뻐서)
		btnSearch.setBounds(161, 10, 120, 30);         			// 검색어 입력 레이블 위치와 크기 -> (x좌표, y좌표, 넓이, 높이)
		panelMedium.add(btnSearch);					 			// 상단 패널에 붙이기

		searchTextField = new JTextField();   		 			// 검색어 입력 텍스트필드 생성
		searchTextField.setBounds(290, 10, 857, 30);			// 검색어 입력 텍스트필드 위치와 크기
		searchTextField.setColumns(10);				 			// 검색어 길이 설정 
		panelMedium.add(searchTextField);				 		// 상단 패널에 붙이기
		
		searchTextField.addKeyListener(new KeyAdapter() {    	// 검색어 입력 텍스트필드 이벤트
			public void keyReleased(KeyEvent e) {
				String val = searchTextField.getText();		 
				TableRowSorter<TableModel> trs = new TableRowSorter<>(table.getModel());
				table.setRowSorter(trs);
				trs.setRowFilter(RowFilter.regexFilter(val));
			}
		});

		
		/* 제목, 버튼 배치하기 */
		JLabel Label = new JLabel("MediHub");
		Label.setBackground(new Color(240, 240, 240));
		Label.setBounds(10, 3, 236, 55);
		Label.setForeground(new Color(0, 255, 128));
		Label.setFont(new Font("맑은 고딕", Font.BOLD, 50));
		panelTop.add(Label);
		
		btnUpdate = new JButton("수정");
		btnUpdate.setBounds(952, 220, 100, 30);
		panelMedium.add(btnUpdate);

		btnDelete = new JButton("삭제");
		btnDelete.setBounds(1055, 220, 100, 30);
		panelMedium.add(btnDelete);
		
		JButton btnBefore = new JButton("로그아웃");
		btnBefore.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		btnBefore.setBounds(1200, 15, 165, 40);
		panelTop.add(btnBefore);	
		
		btnBefore.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
			
		btnBefore.addActionListener(new ActionListener() {         //로그아웃 이벤트(재정의)
			@Override
			public void actionPerformed(ActionEvent e) {
				EventQueue.invokeLater(new Runnable() {
	                public void run() {
	                    try {
	                        AdminMain loginWindow = new AdminMain();
	                        loginWindow.frame.setVisible(true);
	                    } catch (Exception e) {
	                        e.printStackTrace();
	                    }
	                }
	            });
				frame.setVisible(false);
			}
		});

		/* 상세정보 관련 Label과 TextField 배치하기 */
		JLabel details = new JLabel("  상세 정보");
		details.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		details.setBounds(190, 30, 100, 30);
		panelBottom.add(details);
		
		idLabel = new JTextField();
		idLabel.setEditable(false);
		idLabel.setText(" id ");
		idLabel.setFont(new Font("맑은 고딕", Font.BOLD, 14));
		idLabel.setBounds(190, 70, 130, 40);
		idLabel.setHorizontalAlignment(JLabel.CENTER);
		panelBottom.add(idLabel);

		nameLabel = new JTextField();
		nameLabel.setEditable(false);
		nameLabel.setFont(new Font("맑은 고딕", Font.BOLD, 14));
		nameLabel.setText("환 자 이 름");
		nameLabel.setBounds(190, 120, 130, 40);
		nameLabel.setHorizontalAlignment(JLabel.CENTER);
		panelBottom.add(nameLabel);
		nameLabel.setColumns(12);
		
		numberLabel = new JTextField();
		numberLabel.setEditable(false);
		numberLabel.setText("주 민 번 호");
		numberLabel.setFont(new Font("맑은 고딕", Font.BOLD, 14));
		numberLabel.setColumns(10);
		numberLabel.setBounds(190, 170, 130, 40);
		numberLabel.setHorizontalAlignment(JLabel.CENTER);
		panelBottom.add(numberLabel);

		medicalreportLabel = new JTextField();
		medicalreportLabel.setEditable(false);
		medicalreportLabel.setText("진단서 출력여부");
		medicalreportLabel.setFont(new Font("맑은 고딕", Font.BOLD, 14));
		medicalreportLabel.setColumns(10);
		medicalreportLabel.setBounds(190, 220, 130, 40);
		medicalreportLabel.setHorizontalAlignment(JLabel.CENTER);
		panelBottom.add(medicalreportLabel);
		
		paymentLabel = new JTextField();
		paymentLabel.setEditable(false);
		paymentLabel.setText("수납 여부");
		paymentLabel.setFont(new Font("맑은 고딕", Font.BOLD, 14));
		paymentLabel.setColumns(10);
		paymentLabel.setBounds(190, 270, 130, 40);
		paymentLabel.setHorizontalAlignment(JLabel.CENTER);
		panelBottom.add(paymentLabel);
		
		officeLabel = new JTextField();
		officeLabel.setEditable(false);
		officeLabel.setText("진 료 실");
		officeLabel.setFont(new Font("맑은 고딕", Font.BOLD, 14));
		officeLabel.setColumns(10);
		officeLabel.setBounds(513, 70, 130, 40);
		officeLabel.setHorizontalAlignment(JLabel.CENTER);
		panelBottom.add(officeLabel);

		diseaseLabel = new JTextField();
		diseaseLabel.setEditable(false);
		diseaseLabel.setText("진단 결과");
		diseaseLabel.setFont(new Font("맑은 고딕", Font.BOLD, 14));
		diseaseLabel.setColumns(10);
		diseaseLabel.setBounds(513, 120, 130, 40);
		diseaseLabel.setHorizontalAlignment(JLabel.CENTER);
		panelBottom.add(diseaseLabel);

		medicineLabel = new JTextField();
		medicineLabel.setEditable(false);
		medicineLabel.setText("처 방 약");
		medicineLabel.setFont(new Font("맑은 고딕", Font.BOLD, 14));
		medicineLabel.setColumns(10);
		medicineLabel.setBounds(513, 170, 130, 40);
		medicineLabel.setHorizontalAlignment(JLabel.CENTER);
		panelBottom.add(medicineLabel);
		
	    dosesLabel = new JTextField();
	    dosesLabel.setEditable(false);
	    dosesLabel.setText("복 용 횟 수");
	    dosesLabel.setFont(new Font("맑은 고딕", Font.BOLD, 14));
	    dosesLabel.setColumns(10);
	    dosesLabel.setBounds(513, 220, 130, 40);
	    dosesLabel.setHorizontalAlignment(JLabel.CENTER);
		panelBottom.add(dosesLabel);
		
		feeLabel = new JTextField();
	    feeLabel.setEditable(false);
	    feeLabel.setText("금 액");
	    feeLabel.setFont(new Font("맑은 고딕", Font.BOLD, 14));
	    feeLabel.setColumns(10);
	    feeLabel.setBounds(513, 270, 130, 40);
	    feeLabel.setHorizontalAlignment(JLabel.CENTER);
		panelBottom.add(feeLabel);

		idTextfield = new JTextField();
		idTextfield.setBackground(new Color(228, 229, 228));
		idTextfield.setEditable(false);
		idTextfield.setBounds(325, 70, 130, 40);
		idTextfield.setHorizontalAlignment(JTextField.CENTER);
		panelBottom.add(idTextfield);
		idTextfield.setColumns(10);
		
		nameTextfield = new JTextField();
		nameTextfield.setBounds(325, 120, 130, 40);
		nameTextfield.setHorizontalAlignment(JTextField.CENTER);
		panelBottom.add(nameTextfield);
		nameTextfield.setColumns(10);
		
		numberTextfield = new JTextField();
		numberTextfield.setColumns(10);
		numberTextfield.setBounds(325, 170, 130, 40);
		numberTextfield.setHorizontalAlignment(JTextField.CENTER);
		panelBottom.add(numberTextfield);

		medicalreportTextfield = new JTextField();
		medicalreportTextfield.setColumns(10);
		medicalreportTextfield.setBounds(325, 220, 80, 40);
		medicalreportTextfield.setHorizontalAlignment(JTextField.CENTER);
		panelBottom.add(medicalreportTextfield);
		
		paymentTextfield = new JTextField();
		paymentTextfield.setColumns(10);
		paymentTextfield.setBounds(325, 270, 80, 40);
		paymentTextfield.setHorizontalAlignment(JTextField.CENTER);
		panelBottom.add(paymentTextfield);
		
		officeTextfield = new JTextField();
		officeTextfield.setColumns(10);
		officeTextfield.setBounds(651, 70, 130, 40);
		officeTextfield.setHorizontalAlignment(JTextField.CENTER);
		panelBottom.add(officeTextfield);

		diseaseTextfield = new JTextField();
		diseaseTextfield.setColumns(10);
		diseaseTextfield.setBounds(651, 120, 130, 40);
		diseaseTextfield.setHorizontalAlignment(JTextField.CENTER);
		panelBottom.add(diseaseTextfield);

		medicineTextfield = new JTextField();
		medicineTextfield.setColumns(10);
		medicineTextfield.setBounds(651, 170, 130, 40);
		medicineTextfield.setHorizontalAlignment(JTextField.CENTER);
		panelBottom.add(medicineTextfield);
		
		dosesTextfield = new JTextField();
		dosesTextfield.setColumns(10);
		dosesTextfield.setBounds(651, 220, 130, 40);
		dosesTextfield.setHorizontalAlignment(JTextField.CENTER);
		panelBottom.add(dosesTextfield);
		
		feeTextfield = new JTextField();
		feeTextfield.setColumns(10);
		feeTextfield.setBounds(651, 270, 130, 40);
		feeTextfield.setHorizontalAlignment(JTextField.CENTER);
		panelBottom.add(feeTextfield);

		btnSave = new JButton("저장");
		btnSave.setBounds(1055, 33, 100, 30);
		panelBottom.add(btnSave);

		btnCancel = new JButton("취소");
		btnCancel.setBounds(952, 33, 100, 30);
		panelBottom.add(btnCancel);
	}

	public void showTable() {
		data = patients.getPatients();
		String[] header = new String[]{"환자번호", "이름", "주민번호", "진단서출력여부", "수납여부", "진료실", "병명", "처방약", "복용횟수", "금액"};

		tableModel = new DefaultTableModel(data, header);  
		table = new JTable(tableModel) {                    // 셀에서 편집할 수 없게 함.
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		table.setBounds(50, 50, 1010, 270);

		// 테이블 셀 값들 가운데 정렬하기
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment( SwingConstants.CENTER );
		table.getColumnModel().getColumn(0).setCellRenderer( centerRenderer );
		table.getColumnModel().getColumn(1).setCellRenderer( centerRenderer );
		table.getColumnModel().getColumn(2).setCellRenderer( centerRenderer );
		table.getColumnModel().getColumn(3).setCellRenderer( centerRenderer );
		table.getColumnModel().getColumn(4).setCellRenderer( centerRenderer );
		table.getColumnModel().getColumn(5).setCellRenderer( centerRenderer );
		table.getColumnModel().getColumn(6).setCellRenderer( centerRenderer );
		table.getColumnModel().getColumn(7).setCellRenderer( centerRenderer );
		table.getColumnModel().getColumn(8).setCellRenderer( centerRenderer );
		table.getColumnModel().getColumn(9).setCellRenderer( centerRenderer );
		TableCellRenderer rendererFromHeader = table.getTableHeader().getDefaultRenderer();
		JLabel headerLabel = (JLabel) rendererFromHeader;
		headerLabel.setHorizontalAlignment(JLabel.CENTER);
		
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);  		//행을 하나만 선택할 수 있도록 하기

		// 컬럼 크기
		TableColumnModel colModel = table.getColumnModel();
		colModel.getColumn(0).setPreferredWidth(10);
		colModel.getColumn(1).setPreferredWidth(30);
		colModel.getColumn(2).setPreferredWidth(55);
		colModel.getColumn(3).setPreferredWidth(50);
		colModel.getColumn(4).setPreferredWidth(50);
		colModel.getColumn(5).setPreferredWidth(50);
		colModel.getColumn(6).setPreferredWidth(50);
		colModel.getColumn(7).setPreferredWidth(50);
		colModel.getColumn(8).setPreferredWidth(50);
		colModel.getColumn(9).setPreferredWidth(50);

		table.getTableHeader().setFont(new Font("맑은 고딕", Font.BOLD, 13));  	// header 폰트 설정
		table.getTableHeader().setPreferredSize(new Dimension(100, 30));		// header 넓이, 높이 
		table.setFont(new Font("맑은 고딕", Font.PLAIN, 13));                 		// 셀 폰트, 크기
		table.setRowHeight(30);
		//table.setAlignmentX(0);
		
		// 테이블에 스크롤바 추가
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setPreferredSize(new Dimension(995, 150));
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 0));	// padding(상, 좌, 하, 우)
		tablePanel.add(scrollPane); // JScrollPane을 panelTop에 바로 올리면 안 보임. 전용 tablePanel에 올려야 보임

	}

	/* UI테이블에서 선택한 행의 환자번호(id) 가져오기 */
	public String getPatientsId(int seledtedIdx) {
		String selectedId = (String) table.getValueAt(seledtedIdx, 0);  // 인덱스 행의 첫번째 컬럼값 반환
		return selectedId;
	}

	/* UI테이블에서 선택한 행 삭제 메소드 */
	public void deleteTableRow() {

		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				selectedIdx = table.getSelectedRow();    // 선택한 행 인덱스 반환. 선택한 행이 없으면 -1 반환 
				if( selectedIdx == -1) {   
					JOptionPane.showMessageDialog(null, "테이블에서 삭제할 행을 선택하세요.");
				} else {
					// 선택한 행의 환자번호(id) 가져오기
					String selectedId = getPatientsId(selectedIdx); 
					// 테이블 속 데이터 삭제
					patients.deletePatients(selectedId);
					// UI 테이블 행 삭제
					tableModel.removeRow(selectedIdx);
					
					JOptionPane.showMessageDialog(null, "고객 정보가 삭제되었습니다");
				}
			}
		});
	}

	/* UI테이블에서 선택한 환자 정보 수정하기 위해 데이터 불러오기 */
	public void updateTableRow() {
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectedIdx = table.getSelectedRow();    // 선택한 행이 없으면 -1 반환 
				if( selectedIdx == -1) {   
					JOptionPane.showMessageDialog(null, "테이블에서 변경할 행을 선택하세요.");
				} else {
					idTextfield.setText((String)table.getValueAt(selectedIdx, 0));
					nameTextfield.setText((String)table.getValueAt(selectedIdx, 1));
					numberTextfield.setText((String)table.getValueAt(selectedIdx, 2));
					medicalreportTextfield.setText((String)table.getValueAt(selectedIdx, 3));
					paymentTextfield.setText((String)table.getValueAt(selectedIdx, 4));
					officeTextfield.setText((String)table.getValueAt(selectedIdx, 5));
					diseaseTextfield.setText((String)table.getValueAt(selectedIdx, 6));
					medicineTextfield.setText((String)table.getValueAt(selectedIdx, 7));
					dosesTextfield.setText((String)table.getValueAt(selectedIdx, 8));
					feeTextfield.setText((String)table.getValueAt(selectedIdx, 9));
					
					panelBottom.setVisible(true); 			// 테이블에서 다른 곳을 선택할 수 없도록 설정 
				}
			}
		});
	}
	
	/* UI테이블에 수정한 내용 저장 */
	public void saveData() {
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String[] inputList = new String[10];
				inputList[0] = idTextfield.getText();
				inputList[1] = nameTextfield.getText();
				inputList[2] = numberTextfield.getText();
				inputList[3] = medicalreportTextfield.getText();
				inputList[4] = paymentTextfield.getText();
				inputList[5] = officeTextfield.getText();
				inputList[6] = diseaseTextfield.getText();
				inputList[7] = medicineTextfield.getText();
				inputList[8] = dosesTextfield.getText();
				inputList[9] = feeTextfield.getText();
				
				if (!idTextfield.getText().equals("")) {
					// 테이블 update
					patients.updatePatients(/*idTextfield.getText(),*/ inputList[0], inputList[1], inputList[2], 
							inputList[3], inputList[4], inputList[5], inputList[6], inputList[7], inputList[8], inputList[9]);
					
					// JTable에서 해당 행을 업데이트
                    Vector<String> updatedRow = new Vector<>(Arrays.asList(inputList));
                    tableModel.removeRow(selectedIdx);
                    tableModel.insertRow(selectedIdx, updatedRow);
                    
                    JOptionPane.showMessageDialog(null, "환자 정보가 변경되었습니다");
                    tableModel.fireTableDataChanged();
                    panelBottom.setVisible(false);
				}
			}
		});
	}

	/* 취소 버튼 설정 */
	public void cancelDate() {
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelBottom.setVisible(false);
			}
		});
	}
}