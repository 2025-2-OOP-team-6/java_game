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


import java.awt.*;


public class LoginScreen extends JPanel implements IScreen
{
    // CONSTANTS & DIMENSIONS
    private final int MAX_INPUT_SIZE = 15;

    private final int FIELD_WIDTH = 300;
    private final int LABEL_WIDTH = 80;
    private final int PADDING_WIDTH = 10;
    private final int PANEL_INNER_WIDTH = FIELD_WIDTH + LABEL_WIDTH + PADDING_WIDTH;

    private Constant constant;

    private final Color WALLPAPER_COLOR = new Color(constant.WALL_RED, constant.WALL_GREEN, constant.WALL_BLUE); // 임시 하드코딩
    private final Color ROUND_PANEL_COLOR = new Color(67, 46, 129);

    private final String LOGIN_BTN = "assets//buttons//loginBtn.png";
    private final String SIGNUP_BTN = "assets//buttons//signUpBtn.png";

    private AccountData accountData = DataManager.getInstance().getAccountMgr();
    private JLabel title = new JLabel("LOGIN");
    private JLabel label = new JLabel("Please enter ID and PW");
    private JLabel idNotice = new JLabel("ID   : ");
    private JLabel pwNotice1 = new JLabel("PW : ");
    private JTextField idField = new JTextField(MAX_INPUT_SIZE);
    private JPasswordField pwField = new JPasswordField(MAX_INPUT_SIZE);


    private JPanel buttonPanel = null;
    private GButton loginBtn   = null;
    private GButton signUpBtn  = null;
    private Color wallpaper;

    @Override
    public void init(ScreenManager scManager) {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));

        wallpaper = WALLPAPER_COLOR;
        setBackground(wallpaper);

        setLocationAndSize();

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
        title.setFont(new Font("맑은 고딕", Font.BOLD, 80));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setForeground(Color.WHITE);
        title.setOpaque(false);

        label.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        label.setForeground(Color.WHITE);

        idNotice.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
        idNotice.setAlignmentX(Component.CENTER_ALIGNMENT);
        idNotice.setForeground(Color.WHITE);
        idNotice.setOpaque(false);

        pwNotice1.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
        pwNotice1.setAlignmentX(Component.CENTER_ALIGNMENT);
        pwNotice1.setForeground(Color.WHITE);
        pwNotice1.setOpaque(false);


        idField.setMaximumSize(new Dimension(FIELD_WIDTH, 45));
        idField.setAlignmentX(Component.CENTER_ALIGNMENT);

        pwField.setMaximumSize(new Dimension(FIELD_WIDTH, 45));
        pwField.setAlignmentX(Component.CENTER_ALIGNMENT);

        buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttonPanel.setOpaque(false);
    }

    private void setComponent()
    {
        buttonPanel.add(loginBtn);
        buttonPanel.add(signUpBtn);
        buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        RoundPanel panel = new RoundPanel(25);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(ROUND_PANEL_COLOR);

        final int MAX_PANEL_WIDTH = PANEL_INNER_WIDTH + 60;
        final int MAX_PANEL_HEIGHT = 500;
        panel.setMaximumSize(new Dimension(MAX_PANEL_WIDTH, MAX_PANEL_HEIGHT));


        panel.add(Box.createVerticalGlue());

        panel.add(Box.createVerticalStrut(30));
        panel.add(title);
        panel.add(Box.createVerticalStrut(40));
        panel.add(label);
        panel.add(Box.createVerticalStrut(20));
        panel.add(setIdPanel());
        panel.add(Box.createVerticalStrut(15));
        panel.add(setPWPanle1());
        panel.add(Box.createVerticalStrut(40));
        panel.add(buttonPanel);
        panel.add(Box.createVerticalStrut(30));

        panel.add(Box.createVerticalGlue()); // 하단 Glue


        JPanel centerWrapper = new JPanel(new GridBagLayout());
        centerWrapper.setOpaque(false);

        centerWrapper.add(panel, new GridBagConstraints());

        add(centerWrapper, BorderLayout.CENTER);
    }

    private JPanel setIdPanel()
    {
        JPanel idPanel = new JPanel();
        idPanel.setLayout(new BoxLayout(idPanel, BoxLayout.X_AXIS));
        idPanel.setOpaque(false);
        idPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        final Dimension fixedSize = new Dimension(PANEL_INNER_WIDTH, idField.getMaximumSize().height);
        idPanel.setMaximumSize(fixedSize);
        idPanel.setPreferredSize(fixedSize);
        idPanel.setMinimumSize(fixedSize);

        idNotice.setPreferredSize(new Dimension(LABEL_WIDTH, 40));
        idPanel.add(idNotice);
        idPanel.add(Box.createRigidArea(new Dimension(PADDING_WIDTH, 0)));
        idPanel.add(idField);

        return idPanel;
    }

    private JPanel setPWPanle1()
    {
        JPanel pwPanel1 = new JPanel();

        pwPanel1.setLayout(new BoxLayout(pwPanel1, BoxLayout.X_AXIS));
        pwPanel1.setOpaque(false);

        final Dimension fixedSize = new Dimension(PANEL_INNER_WIDTH, pwField.getMaximumSize().height);
        pwPanel1.setMaximumSize(fixedSize);
        pwPanel1.setPreferredSize(fixedSize);
        pwPanel1.setMinimumSize(fixedSize);
        pwPanel1.setAlignmentX(Component.CENTER_ALIGNMENT);

        pwNotice1.setPreferredSize(new Dimension(LABEL_WIDTH, 40));
        pwPanel1.add(pwNotice1);
        pwPanel1.add(Box.createRigidArea(new Dimension(PADDING_WIDTH, 0)));
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