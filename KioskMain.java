package oop_kiosk_medihub;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Panel;
import java.awt.Button;
import java.awt.Label;

public class KioskMain extends JFrame {
	public JFrame frame;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					KioskMain window = new KioskMain();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
    public KioskMain() {
    	getContentPane().setBackground(Color.WHITE);
    	getContentPane().setLayout(null);

	/* 접수 버튼 생성 */
    	Button btnrg = new Button("접수");
    	btnrg.setForeground(Color.WHITE);
    	btnrg.setFont(new Font("나눔스퀘어 Bold", Font.BOLD, 40));
    	btnrg.setBackground(new Color(0, 255, 128));
    	btnrg.setBounds(110, 300, 300, 300);
    	getContentPane().add(btnrg);

	/* 접수 화면으로 전환 */
    	btnrg.addActionListener(new ActionListener() {
    			@Override
    			public void actionPerformed(ActionEvent e) {
    				dispose();
    				new PatientGui();
    			}
    	});
    	getContentPane().add(btnrg);
    	
    	/* 관리자모드 버튼 생성 */
    	Button btnAdmin = new Button("관리자 모드");
    	btnAdmin.setForeground(Color.WHITE);
    	btnAdmin.setFont(new Font("나눔스퀘어 Bold", Font.BOLD, 20));
    	btnAdmin.setBackground(new Color(0,255,128));
    	btnAdmin.setBounds(1200, 20, 200, 50);
    	getContentPane().add(btnAdmin);
    	
    	/* 관리자 모드 로그인 화면으로 전환 */
    	btnAdmin.addActionListener(new ActionListener() {
    		@Override
    		public void actionPerformed(ActionEvent e) {
    			dispose();
    			new AdminMain();
    		}
    	});
    	
    	Label Medihub = new Label("Medihub");
    	Medihub.setForeground(new Color(0, 255, 128));
    	Medihub.setFont(new Font("나눔스퀘어 Bold", Font.BOLD, 50));
    	Medihub.setBounds(60, 30, 400, 50);
    	getContentPane().add(Medihub);
    	
    	Label greetings = new Label("안녕하세요. MediHub입니다.");
    	greetings.setForeground(Color.BLACK);
    	greetings.setFont(new Font("나눔스퀘어 Bold", Font.BOLD, 20));
    	greetings.setBounds(60, 100, 800, 50);
    	getContentPane().add(greetings);
    	
    	Label selecting = new Label("원하시는 항목을 선택해주십시오.");
    	selecting.setForeground(Color.BLACK);
    	selecting.setFont(new Font("나눔스퀘어 Bold", Font.BOLD, 30));
    	selecting.setBounds(300, 200, 800, 50);
    	getContentPane().add(selecting);
    	
    	setBounds(100,100,1500,800);
    	setLocationRelativeTo(null);
    	setVisible(true); 	
    }
}
