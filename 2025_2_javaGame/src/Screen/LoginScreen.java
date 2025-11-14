package Screen;

import Data.AccountData;
import Data.DataManager;
import Action.GButton;
import Util.Screen;
import Util.Constant;


import javax.swing.JPasswordField;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Box;


import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Color;
import java.awt.Font;

public class LoginScreen extends JPanel implements IScreen
{
    private final int MAX_INPUT_SIZE = 15;
    private final String LOGIN_BTN = "..//assets//buttons//loginBtn.png";
    private final String SIGNUP_BTN = "..//assets//buttons//signUpBtn.png";

    private AccountData accountData = DataManager.getInstance().getAccountMgr();
    private JLabel title = new JLabel("LOGIN");
    private JLabel label = new JLabel("Please enter ID and PW");
    private JLabel idNotice = new JLabel("ID   : ");
    private JLabel pwNotice1 = new JLabel("PW : ");
    private JTextField idField = new JTextField(MAX_INPUT_SIZE);
    private JPasswordField pwField = new JPasswordField(MAX_INPUT_SIZE);
    private Constant constant;

    private JPanel buttonPanel = null;
    private GButton loginBtn   = null;
    private GButton signUpBtn  = null;
    private Color wallpaper;

    @Override
    public void init(ScreenManager scManager) {
        setLayout(new BoxLayout(this , BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50)); // 여백

        wallpaper = new Color(constant.WALL_RED, constant.WALL_GREEN, constant.WALL_BLUE);
        setBackground(wallpaper);
        // - initialize settings -

        setLocationAndSize();

        // - GButton settings -

        loginBtn = new GButton(LOGIN_BTN, () -> {
            boolean loginSuccess = loginLogic();
            if (loginSuccess)
            {
                scManager.initAllScreens();
                scManager.show(Screen.HOME);
            }
        });

        signUpBtn = new GButton(SIGNUP_BTN, ()-> {
            scManager.show(Screen.SIGNUP);
        });

        setComponent();
    }

    private boolean loginLogic()
    {
        final String ID = idField.getText();
        final String PW = new String (pwField.getPassword());


        if(accountData.matches(ID, PW))
        {
            DataManager.getInstance().loadUser(ID);

            JOptionPane.showMessageDialog(this, "Login Successful");
            return true;
        }

        JOptionPane.showMessageDialog(this, "Invailed ID or PW");
        return false;
    }


    private void setLocationAndSize()
    {
        title.setFont(new Font("맑은 고딕", Font.BOLD, 30));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setForeground(Color.WHITE);
        title.setOpaque(false);

        label.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        label.setForeground(Color.WHITE);

        idNotice.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
        idNotice.setAlignmentX(Component.CENTER_ALIGNMENT);
        idNotice.setForeground(Color.WHITE);
        idNotice.setOpaque(false);

        pwNotice1.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
        pwNotice1.setAlignmentX(Component.CENTER_ALIGNMENT);
        pwNotice1.setForeground(Color.WHITE);
        pwNotice1.setOpaque(false);


        idField.setMaximumSize(new Dimension(300, 30)); // BoxLayout에서 폭 고정
        idField.setAlignmentX(Component.CENTER_ALIGNMENT);

        pwField.setMaximumSize(new Dimension(300, 30));
        pwField.setAlignmentX(Component.CENTER_ALIGNMENT);

        buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttonPanel.setOpaque(false);
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
        add(Box.createVerticalStrut(5));
        add(setIdPanel());
        add(Box.createVerticalStrut(5));
        add(setPWPanle1());
        add(Box.createVerticalStrut(20));
        add(buttonPanel);
    }

    private JPanel setIdPanel()
    {
        JPanel idPanel = new JPanel();
        idPanel.setLayout(new BoxLayout(idPanel, BoxLayout.X_AXIS));
        idPanel.setOpaque(false);
        idPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        idNotice.setPreferredSize(new Dimension(60, 25)); // 라벨 너비 고정
        idPanel.add(idNotice);
        idPanel.add(Box.createRigidArea(new Dimension(10, 0))); // 라벨과 입력창 사이 간격
        idPanel.add(idField);

        return idPanel;
    }

    private JPanel setPWPanle1()
    {
        JPanel pwPanel1 = new JPanel();

        pwPanel1.setLayout(new BoxLayout(pwPanel1, BoxLayout.X_AXIS));
        pwPanel1.setOpaque(false);
        pwPanel1.setAlignmentX(Component.CENTER_ALIGNMENT);

        pwNotice1.setPreferredSize(new Dimension(60, 25)); // 라벨 너비 고정
        pwPanel1.add(pwNotice1);
        pwPanel1.add(Box.createRigidArea(new Dimension(10, 0))); // 라벨과 입력창 사이 간격
        pwPanel1.add(pwField);

        return pwPanel1;
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
