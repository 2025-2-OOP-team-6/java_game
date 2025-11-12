package Login;

import Render.IScreen;
import Render.ScreenManager;
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
import javax.swing.JOptionPane;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

public class LoginScreen extends JPanel implements IScreen
{
    private final int MAX_INPUT_SIZE = 15;

    private LoginLogic loginFile = new LoginLogic();
    private JLabel title = new JLabel("LOGIN");
    private JLabel label = new JLabel("Please enter ID and PW");
    private JTextField idField = new JTextField(MAX_INPUT_SIZE);
    private JPasswordField pwField = new JPasswordField(MAX_INPUT_SIZE);

    private JPanel buttonPanel = null;
    private GButton loginBtn   = null;
    private GButton signUpBtn  = null;
    private GButton cancelBtn  = null;

    @Override
    public void init(ScreenManager scManager) {
        setLayout(new BoxLayout(this , BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50)); // 여백


        // - initialize settings -

        setLocationAndSize();
        loginFile.readAccountData();

        // - GButton settings -

        loginBtn = new GButton("..//loginButton.png", () -> {
            boolean loginSuccess = loginLogic();
            if (loginSuccess) {scManager.show(Screen.START);}
        });

        signUpBtn = new GButton("..//signUpButton.png", ()-> {
            singUpLogic();
        });

        setComponent();
    }

    private boolean loginLogic()
    {
        final String ID = idField.getText();
        final String PW = new String (pwField.getPassword());

        if(loginFile.matches(ID, PW))
        {
            loginFile.storeAccountData();
            JOptionPane.showMessageDialog(this, "Login Successful");
            return true;
        }

        JOptionPane.showMessageDialog(this, "Invailed ID or PW");
        return false;
    }

    private void singUpLogic()
    {
        final String ID = idField.getText();
        final String PW = new String (pwField.getPassword());

        loginFile.insertData(ID, PW);
        loginFile.storeAccountData();

        JOptionPane.showMessageDialog(this, "SingUp Successful");
    }


    private void setLocationAndSize()
    {
        title.setFont(new Font("맑은 고딕", Font.BOLD, 30));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        label.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);

        idField.setMaximumSize(new Dimension(300, 30)); // BoxLayout에서 폭 고정
        idField.setAlignmentX(Component.CENTER_ALIGNMENT);

        pwField.setMaximumSize(new Dimension(300, 30));
        pwField.setAlignmentX(Component.CENTER_ALIGNMENT);

        buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
    }

    private void setComponent()
    {
        buttonPanel.add(loginBtn);
        buttonPanel.add(signUpBtn);
        buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // 컴포넌트 순서대로 추가
        add(title);
        add(Box.createVerticalStrut(20)); // 간격
        add(label);
        add(Box.createVerticalStrut(15));
        add(idField);
        add(Box.createVerticalStrut(10));
        add(pwField);
        add(Box.createVerticalStrut(20));
        add(buttonPanel);
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
