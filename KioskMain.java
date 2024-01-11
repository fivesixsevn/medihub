package test;

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


    public KioskMain() {
    	getContentPane().setBackground(Color.WHITE);
    	getContentPane().setLayout(null);
    	
    	Button button_register = new Button("접수");
    	button_register.setForeground(Color.WHITE);
    	button_register.setFont(new Font("마루 부리 중간", Font.BOLD, 40));
    	button_register.setBackground(new Color(0, 255, 128));
    	button_register.setBounds(110, 153, 300, 300);
    	getContentPane().add(button_register);
    	
    	button_register.addActionListener(new ActionListener()
    			{
    			@Override
    			public void actionPerformed(ActionEvent e) {
    				dispose();
    				new PatientGui();
    			}
    		
    			});
    	getContentPane().add(button_register);
    	
    	
    	Label Medihub = new Label("Medihub");
    	Medihub.setForeground(new Color(0, 255, 128));
    	Medihub.setFont(new Font("Dialog", Font.BOLD, 50));
    	Medihub.setBounds(60, 30, 400, 100);
    	getContentPane().add(Medihub);
    	setBounds(100,100,1500,800);
    	setVisible(true);
    	
    	
    	
    	
    }
}
