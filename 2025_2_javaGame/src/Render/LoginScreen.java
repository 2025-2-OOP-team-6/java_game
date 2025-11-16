package Render;

import Util.Screen;

import Action.GButton;
import Action.GButtonText;

import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

public class LoginScreen extends JPanel implements IScreen{
	JPasswordField pwField;
	JTextField idField;
	JLabel incorrectLabel;
	
    @Override
    public void init(ScreenManager scManager) {
        setLayout(new BoxLayout(this , BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50)); // 여백

        // 제목 (30pt, 가운데 정렬)
        JLabel title = new JLabel("회원 로그인");
        title.setFont(new Font("맑은 고딕", Font.BOLD, 30));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        // 레이블 (15pt)
        JLabel label = new JLabel("아이디와 비밀번호를 입력하세요");
        label.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);

        Color bgColor = label.getBackground();
        incorrectLabel = new JLabel("아이디 또는 비밀번호가 틀렸습니다.");
        incorrectLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
        incorrectLabel.setForeground(bgColor);
        incorrectLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // 입력칸 1
        idField = new JTextField(15);
        idField.setMaximumSize(new Dimension(300, 30)); // BoxLayout에서 폭 고정
        idField.setAlignmentX(Component.CENTER_ALIGNMENT);

        // 입력칸 2
        pwField = new JPasswordField(15);
        pwField.setMaximumSize(new Dimension(300, 30));
        pwField.setAlignmentX(Component.CENTER_ALIGNMENT);

        // 버튼 2개 (가로 가운데 배치용 패널)
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        GButtonText loginBtn = new GButtonText("로그인", () -> {loginButtonFunc(scManager);});
        
        GButtonText cancelBtn = new GButtonText("회원가입", () -> {scManager.show(Screen.SIGNUP);});
        
        buttonPanel.add(loginBtn);
        buttonPanel.add(cancelBtn);
        buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // 컴포넌트 순서대로 추가
	    add(title);
	    add(Box.createVerticalStrut(20)); // 간격
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
    	//보안을 위해서는 getText가 아닌 character[]로 받아와야 하나 이번 프로젝트의 핵심이 아니므로 뺐음
  
    	//User matches함수 사용 
    	if (false) { 
    		scManager.show(Screen.START);
    	}
    	else {
    		incorrectLabel.setForeground(Color.RED);
    	}
    }

    @Override
    public Screen getScreenType() {
        return Screen.LOGIN;
    }

    @Override
    public void onShow() {
        System.out.println("Start: StartScreen is now Rendering");
    }
}
