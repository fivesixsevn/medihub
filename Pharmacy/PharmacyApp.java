package pharmacy;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.border.LineBorder;

public class PharmacyApp {

    private JFrame frame;
    private JSplitPane bottomSplitPane;
    private JTable table;
    private Pharmacy pharmacy;
    private String[][] data;
    private DefaultTableModel tableModel;
    private JSplitPane splitPane;
    private JPanel tablePanel;
    private JSplitPane finalSplitPane;
    private Marker marker;


    /**
     * @wbp.parser.entryPoint
     */
    public PharmacyApp() {
        initialize();
    }

    private void initialize() {
        frame = new JFrame("MediHub");
        frame.setBackground(new Color(255, 255, 255));
        frame.getContentPane().setFont(new Font("나눔스퀘어", Font.PLAIN, 12));
        frame.setBounds(100, 100, 1500, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 약국 목록 테이블이 출력되는 패널
        tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(new Color(255, 255, 255));

        // 약국 상세정보가 출력되는 패널
        JPanel detailsPanel = new JPanel();
        detailsPanel.setBackground(new Color(255, 255, 255));

        // Marker 초기화
        marker = new Marker();

        // TopPanel 패널 생성 및 설정
        JPanel TopPanel = new JPanel(new BorderLayout());
        TopPanel.setBackground(new Color(255, 255, 255));
        TopPanel.setBorder(null);
      
        JLabel mediHubLabel = new JLabel("MediHub");
        mediHubLabel.setForeground(new Color(32, 178, 170));
        mediHubLabel.setFont(new Font("나눔스퀘어 ExtraBold", Font.PLAIN, 40));

        // mediHubLabel를 갖는 JPanel을 생성
        JPanel mediHubLabelPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        mediHubLabelPanel.setBackground(new Color(255, 255, 255));
        mediHubLabelPanel.add(mediHubLabel);
        
        TopPanel.add(mediHubLabelPanel, BorderLayout.PAGE_START);

        // pageLabel 추가
        JLabel pageLabel = new JLabel("주변 추천 약국 보기");
        pageLabel.setFont(new Font("나눔스퀘어 ExtraBold", Font.PLAIN, 25));
        TopPanel.add(pageLabel, BorderLayout.CENTER);

        // JPanel을 만들어 pageLabel을 왼쪽 배치하고, 이 JPanel을 TopPanel에 추가
        JPanel pageLabelPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 0));
        pageLabelPanel.setBackground(new Color(255, 255, 255));

        JButton backBtn = new JButton("< 뒤로");
        backBtn.setBackground(new Color(32, 178, 170));
        backBtn.setBorder(new LineBorder(new Color(32, 178, 170), 9));
        backBtn.setForeground(Color.WHITE);
        backBtn.setBorder(UIManager.getBorder("Button.border"));
        backBtn.setBounds(491, 505, 207, 73);

        pageLabelPanel.add(backBtn);
        pageLabelPanel.add(pageLabel);
        TopPanel.add(pageLabelPanel, BorderLayout.LINE_START);  // pageLabel 왼쪽에 배치
        backBtn.setFont(new Font("나눔스퀘어 ExtraBold", Font.PLAIN, 25));

        backBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 뒤로가기 버튼 클릭 이벤트 ->모바일 처방전 화면으로 이동
                backBtn.setBackground(new Color(32, 178, 170));
                goReciptGUI();
            }
        });

        // JSplitPane 추가
        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setLeftComponent(new JScrollPane(tablePanel));
        splitPane.setResizeWeight(0.5); // 화면 분할 비율 설정
        splitPane.setEnabled(false); // 리사이즈 비활성화
        splitPane.setBorder(null);
        splitPane.setBackground(new Color(255, 255, 255));

        // JSplitPane을 한 번 더 추가하여 상세정보 패널과 마커 이미지 패널을 아래 위로 분할
        bottomSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        bottomSplitPane.setTopComponent(new JScrollPane(detailsPanel));
        bottomSplitPane.setBackground(new Color(255, 255, 255));
        bottomSplitPane.setResizeWeight(0.5); // 화면 분할 비율 설정
        bottomSplitPane.setEnabled(false); // 리사이즈 비활성화
        bottomSplitPane.setBorder(null);

        // 최종적으로 상세정보와 마커 이미지를 아래 위로 이분할한 구조
        finalSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, splitPane, bottomSplitPane);
        finalSplitPane.setBackground(new Color(255, 255, 255));
        finalSplitPane.setResizeWeight(0.5); // 화면 분할 비율 설정
        finalSplitPane.setEnabled(false); // 리사이즈 비활성화
        finalSplitPane.setBorder(null);  // finalSplitPane의 border 없애기

        // finalSplitPane의 상단에 여백을 주기 위한 EmptyBorder 추가
        finalSplitPane.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

        // 전체적인 화면 구조에 JSplitPane 추가
        frame.getContentPane().add(finalSplitPane, BorderLayout.CENTER);

        // TopPanel 패널을 프레임의 상단에 추가
        frame.getContentPane().add(TopPanel, BorderLayout.NORTH);

        // pharmacy 객체 초기화
        pharmacy = new Pharmacy();

        // 이벤트 처리
        showTable();

        frame.setSize(1500, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        // 초기 divider 위치 설정
        splitPane.setDividerLocation(0.5);
        bottomSplitPane.setDividerLocation(0.5);
        finalSplitPane.setDividerLocation(0.5);
    }

    private void goReciptGUI() {
        frame.dispose(); // 현재 프레임 닫기
      //  goReciptGUI.main(null); //모바일처방전 화면에 main 메서드 호출
    }

    private void showTable() {
        data = pharmacy.getPharmacies();
        String[] header = new String[]{"번호", "약국이름", "별점"};
        tableModel = new DefaultTableModel(data, header);
        table = new JTable(tableModel);
        
        table.setSelectionBackground(new Color(32, 178, 170));
        table.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        table.setBorder(BorderFactory.createEmptyBorder());
        table.setFont(new Font("나눔스퀘어", Font.PLAIN, 25));
        table.setRowHeight(70);

        table.getSelectionModel().addListSelectionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                String pharmacyName = (String) table.getValueAt(selectedRow, 1);
                showPharmacyDetails(pharmacyName);
            }
        });

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        table.getTableHeader().setFont(new Font("나눔스퀘어 ExtraBold", Font.BOLD, 30));
        table.getTableHeader().setPreferredSize(new Dimension(50, 75));
        table.setIntercellSpacing(new Dimension(0, 0));

        // 약국 목록 테이블이 출력되는 패널에 JScrollPane을 추가
        tablePanel.add(new JScrollPane(table), BorderLayout.CENTER);
    }

    private void showPharmacyDetails(String pharmacyName) {
        PharmacyDetails pharmacyDetails = null;

        if (pharmacyName != null) {
            pharmacyDetails = pharmacy.getPharmacyDetailsByName(pharmacyName);
        }

        JPanel detailsPanel = createDetailsPanel(pharmacyDetails);

        // 이미지가 그려지는 패널
        JPanel imagePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                // 배경 이미지 설정
                ImageIcon mapImage = new ImageIcon("images/map2.png");
                g.drawImage(mapImage.getImage(), 0, 0, getWidth(), getHeight(), this);

                // 마커 그리기
                marker.paintComponent(g);
            }
        };

        // 이미지와 마커를 함께 표시하는 패널
        JPanel mapAndMarkerPanel = new JPanel(new BorderLayout());
        mapAndMarkerPanel.add(new JScrollPane(imagePanel), BorderLayout.CENTER);

        // 전체 화면을 가로로 나누는 JSplitPane
        JSplitPane rightSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, mapAndMarkerPanel, new JScrollPane(detailsPanel));
        rightSplitPane.setResizeWeight(0.5);
        rightSplitPane.setEnabled(false);

        finalSplitPane.setRightComponent(rightSplitPane);
        finalSplitPane.setBackground(new Color(255, 255, 255));

        // JSplitPane의 divider 위치 고정
        int dividerLocation = (int) (frame.getWidth() * 0.5); // 화면 폭의 절반에 위치하도록 조절
        finalSplitPane.setDividerLocation(dividerLocation);

        if (pharmacyDetails != null) {
            // 마커 위치 업데이트
            marker.updateDistance(pharmacyDetails.getDistance());
        }
    }
    
    private JPanel createDetailsPanel(PharmacyDetails pharmacyDetails) {
        System.out.println("Creating details panel for: " + pharmacyDetails.getPharmacy());
        JPanel detailsPanel = new JPanel();
        detailsPanel.setBackground(Color.WHITE);
        detailsPanel.setLayout(new BoxLayout(detailsPanel, BoxLayout.Y_AXIS));

        // 이미지가 그려지는 패널
        JPanel imagePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                // 배경 이미지 설정
                ImageIcon mapImage = new ImageIcon("images/map2.png");
                g.drawImage(mapImage.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };

        // 이미지 패널과 마커 패널을 함께 추가하는 JSplitPane
        JSplitPane mapAndMarkerSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, new JScrollPane(imagePanel), new JScrollPane(detailsPanel));
        mapAndMarkerSplitPane.setEnabled(false);
        mapAndMarkerSplitPane.setBackground(new Color(255, 255, 255));

        // 상세 정보 표시 부분 추가
        JLabel nameLabel = new JLabel("약국 이름 " + pharmacyDetails.getPharmacy());
        nameLabel.setFont(new Font("나눔스퀘어 ExtraBold", Font.BOLD, 30));
        nameLabel.setBorder(BorderFactory.createEmptyBorder(0, 0,2, 0));
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        nameLabel.setBackground(new Color(255, 255, 255));

        JLabel starLabel = new JLabel("별점 " + pharmacyDetails.getStar());
        starLabel.setForeground(Color.GRAY);
        starLabel.setFont(new Font("나눔스퀘어 ExtraBold", Font.PLAIN, 25));
        starLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        starLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        starLabel.setBackground(new Color(255, 255, 255));

        JLabel addressLabel = new JLabel("상세주소 : " + pharmacyDetails.getAddress());
        addressLabel.setFont(new Font("나눔스퀘어", Font.PLAIN, 20));
        addressLabel.setHorizontalAlignment(JLabel.LEFT);
        addressLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));
        addressLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        addressLabel.setBackground(new Color(255, 255, 255));

        JLabel callnumberLabel = new JLabel("전화번호 : " + pharmacyDetails.getCallNumber());
        callnumberLabel.setFont(new Font("나눔스퀘어", Font.PLAIN, 20));
        callnumberLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));
        callnumberLabel.setHorizontalAlignment(JLabel.LEFT);
        callnumberLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        callnumberLabel.setBackground(new Color(255, 255, 255));

        JLabel distanceLabel = new JLabel("거리 : " + pharmacyDetails.getDistance());
        distanceLabel.setFont(new Font("나눔스퀘어", Font.PLAIN, 20));
        distanceLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));
        distanceLabel.setHorizontalAlignment(JLabel.RIGHT);
        distanceLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        distanceLabel.setBackground(new Color(255, 255, 255));

        JLabel pharmacyLabel = new JLabel("취급약품 : " + pharmacyDetails.getMedicine());
        pharmacyLabel.setFont(new Font("나눔스퀘어", Font.PLAIN, 20));
        pharmacyLabel.setBorder(BorderFactory.createEmptyBorder(0, 0,5, 0));
        pharmacyLabel.setHorizontalAlignment(JLabel.RIGHT);
        pharmacyLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        pharmacyLabel.setBackground(new Color(255, 255, 255));
        
        // 첫 화면으로 돌아가는 버튼 추가
        JButton mainBtn = new JButton("홈 화면으로 돌아가기");
        mainBtn.setFont(new Font("나눔스퀘어 ExtraBold", Font.BOLD, 25));
        mainBtn.setForeground(Color.WHITE);
        mainBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainBtn.setBackground(new Color(32, 178, 170));
        mainBtn.setBorder(UIManager.getBorder("Button.border"));
       
        // 버튼에 클릭 이벤트를 처리하는 리스너 추가
        mainBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 버튼 클릭 이벤트 처리 - 처음 화면으로 돌아가는 작업 수행
                goKioskMain();
            }
        });
        
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
       
       detailsPanel.add(Box.createVerticalStrut(20));
       detailsPanel.add(mainBtn); 

        return detailsPanel;
    }
    
    private void goKioskMain() {
        frame.dispose(); // 현재 프레임 닫기
       // KioskMain.main(null) ; //kioskmain으로 이동하기
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                PharmacyApp window = new PharmacyApp();
                window.frame.setVisible(true);
                window.frame.setBackground(new Color(255, 255, 255));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
    
}
