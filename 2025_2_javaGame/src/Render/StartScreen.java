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
        add(startButton, BorderLayout.SOUTH);
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
