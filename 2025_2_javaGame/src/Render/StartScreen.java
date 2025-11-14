package Render;

import Util.Screen;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.SwingConstants;

import java.awt.BorderLayout;
import java.awt.Font;

public class StartScreen extends JPanel implements IScreen{

    @Override
    public void init(ScreenManager manager) {
        setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Welcome to my world!", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 40));
        add(titleLabel, BorderLayout.CENTER);

        JButton startButton = new JButton("Start Game");
        startButton.setFont(new Font("SansSerif", Font.PLAIN, 18));
        startButton.addActionListener(e->manager.show(Screen.START));

        JButton rankingButton = new JButton("Show Ranking   "); 
        rankingButton.setFont(new Font("SansSerif", Font.PLAIN, 18));
        rankingButton.addActionListener(e -> manager.show(Screen.RANKING));

        JPanel menuPanel = new JPanel(); //랭킹버튼 추가 후 JPanel로 묶어서 StartScreen에 띄움
        menuPanel.add(startButton);
        menuPanel.add(rankingButton);

        add(menuPanel, BorderLayout.SOUTH);
    }

    @Override
    public Screen getScreenType() {
        return Screen.START;
    }

    @Override
    public void onShow() {
        System.out.println("Start: StartScreen is now Rendering");
    }
}
