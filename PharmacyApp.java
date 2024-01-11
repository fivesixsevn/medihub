package jdbc;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//약국 GUI
public class PharmacyApp {

    private JFrame frame;
    private JTable table;
    private Pharmacy pharmacy;
    private String[][] data;
    private DefaultTableModel tableModel;
    private JSplitPane splitPane;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                PharmacyApp window = new PharmacyApp();
                window.frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Create the application.
     * @wbp.parser.entryPoint
     */
    public PharmacyApp() {
        frame = new JFrame("주변추천약국 보기"); // 프레임 초기화
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {

        pharmacy = new Pharmacy();

        // 상단 레이블 패널 생성
        JPanel topLabelPanel = new JPanel();
        topLabelPanel.setLayout(new BoxLayout(topLabelPanel, BoxLayout.Y_AXIS));

        // 상단 레이블
        JLabel lblNewLabel = new JLabel("MediHub");
        lblNewLabel.setForeground(new Color(18, 222, 191));
        lblNewLabel.setFont(new Font("맑은 고딕", Font.BOLD, 42));
        lblNewLabel.setHorizontalAlignment(JLabel.CENTER);
        lblNewLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // 가운데 정렬 추가

        // "주변 추천 약국 보기" 문장 추가
        JLabel infoLabel = new JLabel("주변 추천 약국 보기");
        infoLabel.setFont(new Font("맑은 고딕", Font.BOLD, 23));
        infoLabel.setHorizontalAlignment(JLabel.CENTER);
        infoLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // 가운데 정렬 추가

        // 레이블을 패널에 추가
        topLabelPanel.add(Box.createVerticalGlue()); // 상단 여백 추가
        topLabelPanel.add(lblNewLabel);
        topLabelPanel.add(Box.createVerticalStrut(10)); // 추가된 여백
        topLabelPanel.add(infoLabel);
        topLabelPanel.add(Box.createVerticalGlue()); // 하단 여백 추가

        frame.getContentPane().add(topLabelPanel, BorderLayout.NORTH);

        // 중앙 테이블 패널
        JPanel tablePanel = new JPanel();
        frame.getContentPane().add(tablePanel, BorderLayout.CENTER);

        // 이벤트 처리
        showTable();

        // JSplitPane 추가
        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, new JScrollPane(table), new JPanel());
        splitPane.setResizeWeight(0.5); // 화면 분할 비율 설정
        splitPane.setEnabled(false); // 리사이즈 비활성화
        frame.getContentPane().add(splitPane, BorderLayout.CENTER);

        // 뒤로가기 버튼 추가
        JButton backButton = new JButton("뒤로가기");
        backButton.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 뒤로가기 동작 추가
                goBack();
            }
        });

        // 메디허브 라벨 옆에 뒤로가기 버튼 추가
        topLabelPanel.add(backButton);

        // 메인화면으로 가기 버튼 추가
        JButton mainButton = new JButton("메인화면으로 가기");
        mainButton.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
        mainButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 메인화면으로 이동하는 동작 추가
                goToMain();
            }
        });

        // 메디허브 라벨 옆에 메인화면으로 가기 버튼 추가
        topLabelPanel.add(mainButton);

        frame.setSize(1500, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    // 약국 목록을 테이블로 표시하고 테이블의 특정 약국이 선택되면 'showPharmacyDetails'메소드 호출해 해당 약국의 세부 정보 표시
    public void showTable() {
        data = pharmacy.getPharmacies();
        String[] header = new String[]{"번호", "약국이름", "별점"};
        tableModel = new DefaultTableModel(data, header);
        table = new JTable(tableModel); // 셀에서 편집할 수 없게 함.
        table.setAutoResizeMode(JTable.AUTO_RESIZE_NEXT_COLUMN);
        table.setSelectionBackground(new Color(18, 222, 191));
        table.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        table.setBorder(BorderFactory.createEmptyBorder());

        // 테이블 헤더 숨김
        //JTableHeader tableHeader = table.getTableHeader();
        //tableHeader.setVisible(false);

        // 약국 정보 글자 크기
        table.setFont(new Font("맑은 고딕", Font.PLAIN, 30));
      
        table.setRowHeight(75);

        table.getSelectionModel().addListSelectionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                String pharmacyName = (String) table.getValueAt(selectedRow, 1);
                showPharmacyDetails(pharmacyName);
            }
        });

        // 셀 값 가운데 정렬
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        TableColumnModel colModel = table.getColumnModel();
        colModel.getColumn(0).setPreferredWidth(400);
        colModel.getColumn(1).setPreferredWidth(800);
        DefaultTableCellRenderer leftRenderer = new DefaultTableCellRenderer();
        colModel.getColumn(1).setCellRenderer(leftRenderer);
        colModel.getColumn(2).setPreferredWidth(300);

        table.getTableHeader().setFont(new Font("NanumGothic", Font.BOLD, 30));
        table.getTableHeader().setPreferredSize(new Dimension(50, 75));
        table.setIntercellSpacing(new Dimension(0, 0));
    }

    private void showPharmacyDetails(String pharmacyName) {
        // 약국 이름을 기반으로 약국 정보를 가져오는 Pharmacy 클래스의 메서드를 가정
        PharmacyDetails pharmacyDetails = pharmacy.getPharmacyDetailsByName(pharmacyName);

        // 정보가 있는지 확인
        if (pharmacyDetails != null) {
            JPanel detailsPanel = createDetailsPanel(pharmacyDetails);
            splitPane.setRightComponent(new JScrollPane(detailsPanel));
        } else {
            // 정보가 없는 경우 처리
            System.out.println("약국에 대한 세부 정보가 없습니다: " + pharmacyName);
        }
    }

    private JPanel createDetailsPanel(PharmacyDetails pharmacyDetails) {
        JPanel detailsPanel = new JPanel();
        detailsPanel.setLayout(new BoxLayout(detailsPanel, BoxLayout.Y_AXIS));

        JLabel nameLabel = new JLabel("약국 이름: " + pharmacyDetails.getPharmacy());
        nameLabel.setFont(new Font("나눔고딕", Font.BOLD, 30));
        nameLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel starLabel = new JLabel("별점: " + pharmacyDetails.getStar());
        starLabel.setFont(new Font("나눔고딕", Font.PLAIN, 20));
        starLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0));
        starLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel addressLabel = new JLabel("상세주소: " + pharmacyDetails.getAddress());
        addressLabel.setFont(new Font("나눔고딕", Font.PLAIN, 20));
        addressLabel.setHorizontalAlignment(JLabel.LEFT);
        addressLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0));
        addressLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel callnumberLabel = new JLabel("전화번호: " + pharmacyDetails.getCallNumber());
        callnumberLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0));
        callnumberLabel.setFont(new Font("나눔고딕", Font.PLAIN, 20));
        callnumberLabel.setHorizontalAlignment(JLabel.LEFT);
        callnumberLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel distanceLabel = new JLabel("거리: " + pharmacyDetails.getDistance());
        distanceLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0));
        distanceLabel.setFont(new Font("나눔고딕", Font.PLAIN, 20));
        distanceLabel.setHorizontalAlignment(JLabel.RIGHT);
        distanceLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel pharmacyLabel = new JLabel("취급약품: " + pharmacyDetails.getMedicine());
        pharmacyLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0));
        pharmacyLabel.setFont(new Font("나눔고딕", Font.PLAIN, 20));
        pharmacyLabel.setHorizontalAlignment(JLabel.RIGHT);
        pharmacyLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        detailsPanel.add(Box.createVerticalStrut(20));
        detailsPanel.add(nameLabel);

        detailsPanel.add(Box.createVerticalStrut(10));
        detailsPanel.add(starLabel);

        detailsPanel.add(Box.createVerticalStrut(10));
        detailsPanel.add(addressLabel);

        detailsPanel.add(Box.createVerticalStrut(10));
        detailsPanel.add(callnumberLabel);

        detailsPanel.add(Box.createVerticalStrut(10));
        detailsPanel.add(distanceLabel);

        detailsPanel.add(Box.createVerticalStrut(10));
        detailsPanel.add(pharmacyLabel);

        return detailsPanel;
    }

    private void goBack() {
        // 뒤로가기 동작 추가
        splitPane.setRightComponent(new JPanel());
    }

    private void goToMain() {
        // 메인화면으로 이동하는 동작 추가
        // new Main().setVisible(true);
    }
}
