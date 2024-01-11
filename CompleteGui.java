// CompleteGui.java
package test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CompleteGui extends JFrame {

    private Timer timer;  // Timer 추가

    public CompleteGui() {
        	getContentPane().setBackground(Color.WHITE);
        	getContentPane().setLayout(null);
        	
        	Label info_treatment = new Label("진료실 정보");
        	info_treatment.setFont(new Font("마루 부리 중간", Font.BOLD, 34));
        	info_treatment.setBackground(new Color(233, 233, 233));
        	info_treatment.setBounds(672, 167, 438, 385);
        	getContentPane().add(info_treatment);
        	
        	Button button_back = new Button("처음으로");
        	button_back.setBounds(377, 611, 145, 61);
        	getContentPane().add(button_back);
        	
        	Label Medihub = new Label("Medihub");
        	Medihub.setForeground(new Color(0, 255, 128));
        	Medihub.setFont(new Font("Dialog", Font.BOLD, 50));
        	Medihub.setBounds(60, 30, 400, 100);
        	getContentPane().add(Medihub);
        	
        	Label info_patientname = new Label("홍길동님.");
        	info_patientname.setBounds(133, 274, 100, 50);
        	getContentPane().add(info_patientname);
        	
        	Label info_label = new Label("접수가 완료되었습니다. 대기 공간에서 기다려주십시오. ");
        	info_label.setForeground(Color.BLACK);
        	info_label.setBounds(133, 479, 300, 300);
        	getContentPane().add(info_label);
            setBounds(100,100,1500,800);
            setVisible(true);
            
            button_back.addActionListener(new ActionListener()
    		{
    			@Override
    			public void actionPerformed(ActionEvent e) {
    				dispose();
    				new KioskMain();
    			}
    	
    		});
    }
}
