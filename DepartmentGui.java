package test;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class DepartmentGui extends JFrame {
	
	private String selectedDepartment = ""; // 선택한 부서를 저장하는 변수
	private String firstTreatment = "";
    private String secondTreatment = "";
    private Map<Button, Color> originalButtonColors = new HashMap<>(); // 버튼의 원래 색상을 저장하는 맵
    
    public DepartmentGui() {
    	getContentPane().setBackground(Color.WHITE);
    	getContentPane().setLayout(null);
    	
    	Label pagecheck = new Label("this is Department");
    	pagecheck.setBounds(84, 181, 64, 22);
    	getContentPane().add(pagecheck);
    	
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
    	
    	Button Internal = new Button("내과");
    	Internal.setBounds(368, 285, 72, 22);
    	getContentPane().add(Internal);
    	
    	Button Dermatology = new Button("피부과");
    	Dermatology.setBounds(729, 285, 72, 22);
    	getContentPane().add(Dermatology);
    	
    	Button Osteo = new Button("정형외과");
    	Osteo.setBounds(368, 440, 72, 22);
    	getContentPane().add(Osteo);
    	
    	Button NeuroPsychiatry = new Button("정신의학과");
    	NeuroPsychiatry.setBounds(729, 440, 72, 22);
    	getContentPane().add(NeuroPsychiatry);
        setBounds(100,100,1500,800);
        
        //setVisible(true); 원래 색상을 저장
        originalButtonColors.put(Internal, Internal.getBackground());
        originalButtonColors.put(Dermatology, Dermatology.getBackground());
        originalButtonColors.put(Osteo, Osteo.getBackground());
        originalButtonColors.put(NeuroPsychiatry, NeuroPsychiatry.getBackground());

        setBounds(100, 100, 1500, 800);
        setVisible(true);
        
        // 과목 btn actionlistener
        Internal.addActionListener(e -> handleDepartmentButtonClick("내과", Internal));
        Dermatology.addActionListener(e -> handleDepartmentButtonClick("피부과", Dermatology));
        Osteo.addActionListener(e -> handleDepartmentButtonClick("정형외과", Osteo));
        NeuroPsychiatry.addActionListener(e -> handleDepartmentButtonClick("정신의학과", NeuroPsychiatry));
        
        button_back.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				new PatientGui();
			}
	
		});
        
        button_next.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				openTreatmentGui();
			}
	
		});
    }
        
        
        private void handleDepartmentButtonClick(String department, Button clickedButton) {
            // 선택한 부서를 설정
            selectedDepartment = department;

            // 선택한 부서에 따라 firstTreatment 및 secondTreatment 설정
            switch (selectedDepartment) {
                case "내과":
                    firstTreatment = "A";
                    secondTreatment = "B";
                    break;
                case "정형외과":
                    firstTreatment = "C";
                    secondTreatment = "D";
                    break;
                case "피부과":
                    firstTreatment = "E";
                    secondTreatment = "F";
                    break;
                case "정신의학과":
                    firstTreatment = "G";
                    secondTreatment = "H";
                    break;
                default:
                    // 다른 경우에 대한 처리가 필요하다면 추가
                    break;
            }

            // 클릭한 버튼의 색상을 변경하고 다른 버튼의 색상을 원래대로 복원
            for (Map.Entry<Button, Color> entry : originalButtonColors.entrySet()) {
                Button button = entry.getKey();
                Color originalColor = entry.getValue();
                button.setBackground(button == clickedButton ? Color.GREEN : originalColor);
            }

            System.out.println("선택한 부서: " + selectedDepartment);
    }
    
    // 부서 버튼 클릭 처리 메서드
//    private void handleDepartmentButtonClick(String department, Button clickedButton) {
//        // 선택한 부서를 설정
//        selectedDepartment = department;
//
//        // 클릭한 버튼의 색상을 변경하고 다른 버튼의 색상을 원래대로 복원
//        for (Map.Entry<Button, Color> entry : originalButtonColors.entrySet()) {
//            Button button = entry.getKey();
//            Color originalColor = entry.getValue();
//            button.setBackground(button == clickedButton ? Color.YELLOW : originalColor);
//        }
//        
//        System.out.println("선택한 부서: " + selectedDepartment);
//    }
        private void openTreatmentGui() {
            dispose();
            new TreatmentGui(selectedDepartment, firstTreatment, secondTreatment);
        }
}
