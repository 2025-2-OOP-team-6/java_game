package Render;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import Action.GButtonText;
import Screen.IScreen;
import Screen.ScreenManager;
import Util.Screen;

public class GameScreen extends JPanel implements IScreen{
	JPasswordField pwField;
	JTextField idField;
	JLabel incorrectLabel;
	
    @Override
    public void init(ScreenManager scManager) {
        setLayout(new BoxLayout(this , BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50)); 

        JLabel title = new JLabel("회원 로그인");
        title.setFont(new Font("맑은 고딕", Font.BOLD, 30));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);


        JLabel label = new JLabel("아이디와 비밀번호를 입력하세요");
        label.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);

        Color bgColor = label.getBackground();
        incorrectLabel = new JLabel("아이디 또는 비밀번호가 틀렸습니다.");
        incorrectLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
        incorrectLabel.setForeground(bgColor);
        incorrectLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        idField = new JTextField(15);
        idField.setMaximumSize(new Dimension(300, 30)); 
        idField.setAlignmentX(Component.CENTER_ALIGNMENT);

        pwField = new JPasswordField(15);
        pwField.setMaximumSize(new Dimension(300, 30));
        pwField.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        GButtonText loginBtn = new GButtonText("로그인", () -> {loginButtonFunc(scManager);});
        
        GButtonText cancelBtn = new GButtonText("회원가입", () -> {scManager.show(Screen.SIGNUP);});
        
        buttonPanel.add(loginBtn);
        buttonPanel.add(cancelBtn);
        buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

	    add(title);
	    add(Box.createVerticalStrut(20)); 
	    add(label);
	    add(Box.createVerticalStrut(15));
	    add(incorrectLabel);
	    add(Box.createVerticalStrut(15));
	    add(idField);
	    add(Box.createVerticalStrut(10));
	    add(pwField);
	    add(Box.createVerticalStrut(20));
	    add(buttonPanel);


    }
    
    private void loginButtonFunc(ScreenManager scManager) {
    	String ID = idField.getText();
    	String PW = pwField.getText(); 
 
    	if (false) { 
    		scManager.show(Screen.START);
    	}
    	else {
    		incorrectLabel.setForeground(Color.RED);
    	}
    }

    @Override
    public Screen getScreenType() {
        return Screen.GAME;
    }

    @Override
    public void onShow() {
        System.out.println("Start: StartScreen is now Rendering");
    }
}
