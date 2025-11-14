package Render;

import Util.Screen;
import java.awt.*; 
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;

import java.util.Arrays;

//csv를 통해서 user, 플레이타임, 랭킹을 swing으로 보여줌 
public class RankingScreen extends JPanel implements IScreen{

    private ImageIcon resizeImage(ImageIcon icon, int width, int height) {
        Image img = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(img);
    }

    @Override
    public void init(ScreenManager manager) {
        setLayout(new BorderLayout());
        JLabel titleLabel = new JLabel("[LEADER BOARD]", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 40));
        titleLabel.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.GRAY));
        add(titleLabel, BorderLayout.NORTH);

        //플레이어(랭킹, 프로필, 이름, 플레이타임, 클리어 스테이지) JTable로 구현
        Object[][] userData={
            {0, null, "변재우", "17.20", "5"},
            {0, null, "김재민", "12:34", "3"},
            {0, null, "남석현", "06:12", "1"},
            {0, null, "신동민", "15:07", "4"},
            {0, null, "김주찬", "12:34", "2"},
            {0, null, "왕석빈", "20:58", "6"},
        };
        
        ImageIcon rank1 = resizeImage(new ImageIcon(getClass().getResource("/Render/img/first.png")), 50, 70);
        ImageIcon rank2 = resizeImage(new ImageIcon(getClass().getResource("/Render/img/second.png")), 50, 70);
        ImageIcon rank3 = resizeImage(new ImageIcon(getClass().getResource("/Render/img/third.png")), 50, 70);
        ImageIcon userProfile = resizeImage(new ImageIcon(getClass().getResource("/Render/img/user_profile.png")), 50, 50);          
            
        //Object를 String=>int로 변환 후, 내림차순 정렬
        Arrays.sort(userData, (a, b) -> Integer.parseInt((String)b[4]) - Integer.parseInt((String)a[4]));

        for (int i = 0; i < userData.length; i++) {
            //1 ~ 3위는 이미지 파일
            if (i == 0) userData[i][0] = rank1;
            else if (i == 1) userData[i][0] = rank2;
            else if (i == 2) userData[i][0] = rank3;
            else userData[i][0] = String.valueOf(i + 1); //4위부터는 문자열로 넣음
            userData[i][1] = userProfile; //모든 유저 기본 프로필 설정
        }
        String[] dataCol = {"Rank", "Profile", "Username", "Playtime", "Clear Stage"};

        //테이블 생성 및 값 고정(수정X)  
        JTable table = new JTable(userData, dataCol){
            @Override
            public boolean isCellEditable(int row, int column) {
            return false;  
            };
             
            @Override
            public Class<?> getColumnClass(int column){
                if (column == 0 || column == 1) return ImageIcon.class; // Rank, Profile 컬럼은 이미지
                return String.class; // 나머지는 문자열
            };
        };

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        table.setFont(new Font("SansSerif", Font.PLAIN, 18)); 
        table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 18));
        table.getTableHeader().setReorderingAllowed(false); //열 고정
        table.setRowHeight(70);
        table.getColumnModel().getColumn(0).setPreferredWidth(50);
        table.getColumnModel().getColumn(1).setPreferredWidth(50);
        table.getColumnModel().getColumn(2).setPreferredWidth(150);
        table.getColumnModel().getColumn(3).setPreferredWidth(100);
        table.getColumnModel().getColumn(4).setPreferredWidth(100);
        for (int i = 2; i <= 4; i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer); //텍스트들도 모두 가운데 정렬
        }
        
        JScrollPane scrollPane = new JScrollPane(table); //유저 수가 많아지면 스크롤 가능하도록
        add(scrollPane, BorderLayout.CENTER);

        JButton backButton = new JButton("Go Back");
        backButton.addActionListener(e -> manager.show(Screen.START));
        backButton.setFont(new Font("SansSerif", Font.PLAIN, 18));
        add(backButton, BorderLayout.SOUTH);
    }

    @Override
    public Screen getScreenType() {
        return Screen.RANKING;
    }

    @Override
    public void onShow() {
       System.out.println("Ranking: RankingScreen is now Rengering");
    }
    
}
