package com.myGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import static javax.swing.JOptionPane.showMessageDialog;

public class myGame extends JFrame implements ActionListener

{   JLabel heading, clockLabel;
    Font font = new Font("", Font.BOLD,40);
    JPanel mainPanel;
    JButton[] btns = new JButton[9];

    //game instance variable
    int[] gameChances = {2,2,2,2,2,2,2,2,2};
    int activePlayer = 0;

    int[][] wps = {
            {0, 1, 2},
            {3, 4, 5},
            {6, 7, 8},
            {0, 3, 6},
            {1, 4, 7},
            {2, 5, 8},
            {0, 4, 8},
            {2, 4, 6}

    };
    int winner = 2;
    boolean gameOver = false;
    myGame()
    {
        System.out.println("Creating instance of Game here...");
        setTitle("TIC TAC TOE GAME");
        setSize(900,1200);
        setLocation(500,0);

        ImageIcon icon= new ImageIcon("src/img/icon.png");
        setIconImage(icon.getImage());

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.createGUI();

        setVisible(true);
    }
    private void createGUI()
    {
        this.getContentPane().setBackground(Color.decode("#7C7C7C"));
        this.setLayout(new BorderLayout());
        // NORTH HEADING..
        heading = new JLabel("TIC TAC TOE");
        heading.setIcon(new ImageIcon("src/img/icon.png"));
        heading.setFont(font);
        heading.setHorizontalAlignment(SwingConstants.CENTER);
        heading.setForeground(Color.white);


        heading.setHorizontalTextPosition(SwingConstants.CENTER);
        heading.setVerticalTextPosition(SwingConstants.BOTTOM);
        this.add(heading, BorderLayout.NORTH);

        //creating object of JLabel for clock
        clockLabel = new JLabel("C lock");
        clockLabel.setFont(font);
        clockLabel.setForeground(Color.white);
        clockLabel.setHorizontalAlignment(SwingConstants.CENTER);

        this.add(clockLabel, BorderLayout.SOUTH);
        Thread t = new Thread(() -> {
            try
            {
                while (true)
                {
                    String dateTime = new Date().toLocaleString();
                    clockLabel.setText(dateTime );
                    Thread.sleep(1000);
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        });
        t.start();
        //........panel Section
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(3,3));
         for(int i=1; i<=9; i++)
         {
            JButton btn = new JButton();
//            btn.setIcon(new ImageIcon("src/img/1.png"));
            btn.setBackground(Color.decode("#7C7C7C"));//not working

            btn.setFont(font);
            mainPanel.add(btn);
            btns[i-1] = btn;
            btn.addActionListener(this);
            btn.setName(String.valueOf(i-1));
         }
         this.add(mainPanel, BorderLayout.CENTER);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("clicked");
        JButton currentBtn = (JButton) e.getSource();
        String nameStr = currentBtn.getName();
        int name = Integer.parseInt(nameStr.trim());
        if(gameOver){
            JOptionPane.showMessageDialog(this, "GAME ALREADY OVER !!");
            return;
        }


        if(gameChances[name] == 2)
        {
            if(activePlayer == 1)
            {
                currentBtn.setIcon(new ImageIcon("src/img/1.png"));
                gameChances[name] = activePlayer;
                activePlayer = 0;
            }else {
                currentBtn.setIcon(new ImageIcon("src/img/0.png"));
                gameChances[name] = activePlayer;
                activePlayer = 1;
            }
            //find the winner........
            for(int []temp : wps)
            {
                if((gameChances[temp[0]] == gameChances[temp[1]])
                        &&(gameChances[temp[1]] == gameChances[temp[2]])
                            && gameChances[temp[2]] != 2)
                {
                    winner = gameChances[temp[0]];
                    gameOver = true;
                    if(winner == 1){
                        showMessageDialog(null, "PLAYER - 'X' HAS WON THE GAME!!");
                    }else
                    showMessageDialog(null, "PLAYER - '" + winner +"' HAS WON THE GAME!!");
                    int i = JOptionPane.showConfirmDialog(null,"Do you want to play more ?");
                    System.out.println(i);
                    if(i==0) {
                        this.setVisible(false);
                        new myGame();
                    } else if (i==1)
                    {
                        System.exit(1432);
                    }else {

                    }
                    break;
                }
            }
            // Draw Logic
            int c = 0;
            for(int x : gameChances){
                if(x==2){
                    c++;
                    break;
                }
            }
            if(c==0 && gameOver==false)
            {
                JOptionPane.showMessageDialog(this, "It's A DRAW");
                int i =  JOptionPane.showConfirmDialog(this, "Do You Want To Play More?");
                if(i==0){
                    this.setVisible(false);
                    new myGame();
                }else if(i==1){
                    System.exit(1432);
                }else {}
                gameOver = true;

            }
        }else{
            showMessageDialog(null, "Position already Occupied");
        }
    }
}
