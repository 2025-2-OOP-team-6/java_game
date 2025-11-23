package Screen;

import Data.DataManager;
import Action.GButton;
import Data.UserData;
import Util.Screen;
import Util.Constant;

import javax.swing.JPasswordField;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Box;

import java.awt.Font;
import java.awt.Color;
import java.awt.Image;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;

public class SignUpScreen extends JPanel implements IScreen
{
    private final int    MAX_INPUT_SIZE = 15;
    private final String CANCLE_BTN     = "assets//buttons//cancleBtn.png";
    private final String SIGNUP_BTN     = "assets//buttons//signUpBtn.png";

    private UserData      	userData = DataManager.getInstance().getUserMgr();
    private JLabel           title       = new JLabel("Sign Up");
    private JLabel           label       = new JLabel("Please enter ID and PW");
    private JLabel           idNotice    = new JLabel("ID    : ");
    private JLabel           pwNotice1   = new JLabel("PW1 : ");
    private JLabel           pwNotice2   = new JLabel("PW2 : ");
    private JTextField       idField     = new JTextField(MAX_INPUT_SIZE);
    private JPasswordField   pwField1    = new JPasswordField(MAX_INPUT_SIZE);
    private JPasswordField   pwField2    = new JPasswordField(MAX_INPUT_SIZE);

    private JPanel  buttonPanel = null;
    private GButton cancleBtn   = null;
    private GButton signUpBtn   = null;

    @Override
    public void init(final ScreenManager scManager)
    {
        // 세로로 쌓는 레이아웃
        setLayout(new BoxLayout(this , BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));

        // 배경색
        Color wallpaper = new Color(
            Constant.WALL_RED,
            Constant.WALL_GREEN,
            Constant.WALL_BLUE
        );
        setBackground(wallpaper);

        // 라벨 / 텍스트필드 기본 설정
        setLocationAndSize();

        // ─ 버튼 생성 ─
        cancleBtn = new GButton(getCancleBtn(), () -> {
            scManager.show(Screen.LOGIN);
        });

        signUpBtn = new GButton(SIGNUP_BTN, () -> {
            boolean signUpSuccess = singUpLogic();
            if (signUpSuccess)
            {
                scManager.show(Screen.LOGIN);
            }
        });

        // 아이콘이 안 잡혀도 최소 크기는 보이도록 강제
        Dimension btnSize = new Dimension(140, 40);
        cancleBtn.setPreferredSize(btnSize);
        signUpBtn.setPreferredSize(btnSize);

        // 컴포넌트 배치
        setComponent();
    }

    private ImageIcon getCancleBtn()
    {
        ImageIcon cancleImage = new ImageIcon(CANCLE_BTN);
        ImageIcon signUpImage = new ImageIcon(SIGNUP_BTN);

        int width  = signUpImage.getIconWidth();
        int height = signUpImage.getIconHeight();

        if (width <= 0 || height <= 0)
        {
            // 혹시 signUp 이미지가 없어서 0 이면, 기본 크기로라도 리턴
            width  = 140;
            height = 40;
        }

        Image resizedImage = cancleImage.getImage()
                                        .getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(resizedImage);
    }

    private boolean singUpLogic()
    {
        final String ID  = idField.getText();
        final String PW1 = new String(pwField1.getPassword());
        final String PW2 = new String(pwField2.getPassword());

        if (!PW1.equals(PW2))
        {
            JOptionPane.showMessageDialog(this,
                "Wrong password please retype your password");
            return false;
        }

        else if(!accountData.insertData(ID, PW1))
        {
            JOptionPane.showMessageDialog(this, "Duplicate ID please retype your ID and PW");
            return false;
        }

        accountData.insertData(ID, PW1);

        UserData userMgr = DataManager.getInstance().getUserMgr();
        userMgr.addUserData(ID, PW2);
        DataManager.getInstance().loadUser(ID);

        JOptionPane.showMessageDialog(this, "SignUp Successful");
        return true;
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

        pwNotice2.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
        pwNotice2.setAlignmentX(Component.CENTER_ALIGNMENT);
        pwNotice2.setForeground(Color.WHITE);
        pwNotice2.setOpaque(false);

        idField.setMaximumSize(new Dimension(300, 30));
        idField.setAlignmentX(Component.CENTER_ALIGNMENT);

        pwField1.setMaximumSize(new Dimension(300, 30));
        pwField1.setAlignmentX(Component.CENTER_ALIGNMENT);

        pwField2.setMaximumSize(new Dimension(300, 30));
        pwField2.setAlignmentX(Component.CENTER_ALIGNMENT);

        buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttonPanel.setOpaque(false);
    }

    private void setComponent()
    {
        buttonPanel.add(cancleBtn);
        buttonPanel.add(signUpBtn);                // ← 여기서 확인(회원가입) 버튼 추가
        buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        add(title);

        add(Box.createVerticalStrut(40)); // 간격
        add(label);
        add(setIdPanel());
        add(Box.createVerticalStrut(10));
        add(setPWPanle1());
        add(Box.createVerticalStrut(10));
        add(setPWPanel2());
        add(Box.createVerticalStrut(40));
        add(buttonPanel);
    }

    private JPanel setIdPanel()
    {
        JPanel idPanel = new JPanel();
        idPanel.setLayout(new BoxLayout(idPanel, BoxLayout.X_AXIS));
        idPanel.setOpaque(false);
        idPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        idNotice.setPreferredSize(new Dimension(60, 25));
        idPanel.add(idNotice);
        idPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        idPanel.add(idField);

        return idPanel;
    }

    private JPanel setPWPanle1()
    {
        JPanel pwPanel1 = new JPanel();
        pwPanel1.setLayout(new BoxLayout(pwPanel1, BoxLayout.X_AXIS));
        pwPanel1.setOpaque(false);
        pwPanel1.setAlignmentX(Component.CENTER_ALIGNMENT);

        pwNotice1.setPreferredSize(new Dimension(60, 25));
        pwPanel1.add(pwNotice1);
        pwPanel1.add(Box.createRigidArea(new Dimension(10, 0)));
        pwPanel1.add(pwField1);

        return pwPanel1;
    }

    private JPanel setPWPanel2()
    {
        JPanel pwPanel2 = new JPanel();
        pwPanel2.setLayout(new BoxLayout(pwPanel2, BoxLayout.X_AXIS));
        pwPanel2.setOpaque(false);
        pwPanel2.setAlignmentX(Component.CENTER_ALIGNMENT);

        pwNotice2.setPreferredSize(new Dimension(60, 25));
        pwPanel2.add(pwNotice2);
        pwPanel2.add(Box.createRigidArea(new Dimension(10, 0)));
        pwPanel2.add(pwField2);

        return pwPanel2;
    }

    @Override
    public Screen getScreenType()
    {
        return Screen.SIGNUP;
    }

    @Override
    public void onShow()
    {
        System.out.println("Start: SignUpScreen is now Rendering");
    }
}
