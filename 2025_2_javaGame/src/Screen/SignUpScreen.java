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

public class SignUpScreen extends JPanel implements IScreen
{
    private final int MAX_INPUT_SIZE = 15;
    private final String CANCLE_BTN = "..//assets//buttons//cancleBtn.png";
    private final String SIGNUP_BTN = "..//assets//buttons//signUpBtn.png";

    private AccountData accountData = DataManager.getInstance().getAccountMgr();
    private JLabel title = new JLabel("Sign Up");
    private JLabel label = new JLabel("Please enter ID and PW");
    private JTextField idField = new JTextField(MAX_INPUT_SIZE);
    private JPasswordField pwField1 = new JPasswordField(MAX_INPUT_SIZE);
    private JPasswordField pwField2 = new JPasswordField(MAX_INPUT_SIZE);
    private Constant constant;

    private JPanel buttonPanel = null;
    private GButton cancleBtn   = null;
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

        cancleBtn = new GButton(CANCLE_BTN, () -> {
            scManager.show(Screen.LOGIN);
        });

        signUpBtn = new GButton(SIGNUP_BTN, ()-> {
            boolean signUpSuccess = singUpLogic();
            if(signUpSuccess) { scManager.show(Screen.LOGIN);}
        });

        setComponent();
    }

    private boolean singUpLogic()
    {
        final String ID = idField.getText();
        final String PW1 = new String (pwField1.getPassword());
        final String PW2 = new String (pwField2.getPassword());

        if(!PW1.equals(PW2))
        {
            JOptionPane.showMessageDialog(this, "Wrong password please retype your password");
            return false;
        }

        accountData.insertData(ID, PW1);

        DataManager.getInstance().loadUser(ID);

        JOptionPane.showMessageDialog(this, "SingUp Successful");
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

        idField.setMaximumSize(new Dimension(300, 30)); // BoxLayout에서 폭 고정
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
        buttonPanel.add(signUpBtn);
        buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // 컴포넌트 순서대로 추가
        add(title);
        add(Box.createVerticalStrut(20)); // 간격
        add(label);
        add(Box.createVerticalStrut(15));
        add(idField);
        add(Box.createVerticalStrut(10));
        add(pwField1);
        add(Box.createVerticalStrut(10));
        add(pwField2);
        add(Box.createVerticalStrut(20));
        add(buttonPanel);
    }


    @Override
    public Screen getScreenType() {return Screen.SIGNUP;}

    @Override
    public void onShow() {
        System.out.println("Start: StartScreen is now Rendering");
    }
}
