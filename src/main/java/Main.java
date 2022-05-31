import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;
import java.util.Objects;

public class Main {

    JLabel counterLabel, perSecLabel;
    JButton button1, button2, button3, button4;
    int cookieCounter, timerSpeed;
    double perSecond;
    boolean timerOn;
    Font font1, font2;
    CookieHandler cHandler = new CookieHandler();
    Timer timer;

    public static void main(String[] args) {
        new Main();
    }

    public Main() {

        timerOn = false;
        perSecond = 0;
        cookieCounter = 0;

        createFont();
        createUI();
    }

    public void createFont() {

        font1 = new Font("Comic Sans MS", Font.PLAIN, 32);
        font2 = new Font("Comic Sans MS", Font.PLAIN, 16);
    }

    public void createUI() {

        JFrame window = new JFrame();
        window.setSize(800, 600);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.getContentPane().setBackground(Color.black);
        window.setLayout(null);

        JPanel cookiePanel = new JPanel();
        cookiePanel.setBounds(100, 220, 200, 210);
        cookiePanel.setBackground(Color.black);
        window.add(cookiePanel);

        ImageIcon cookie = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("cookie.png")));

        JButton cookieButton = new JButton();
        cookieButton.setBackground(Color.black);
        cookieButton.setFocusPainted(false);
        cookieButton.setBorder(null);
        cookieButton.setIcon(cookie);
        cookieButton.addActionListener(cHandler);
        cookieButton.setActionCommand("cookie");
        cookiePanel.add(cookieButton);

        JPanel counterPanel = new JPanel();
        counterPanel.setBounds(100, 100, 200, 100);
        counterPanel.setBackground(Color.black);
        counterPanel.setLayout(new GridLayout(2, 1));
        window.add(counterPanel);

        counterLabel = new JLabel(cookieCounter + " cookies");
        counterLabel.setForeground(Color.white);
        counterLabel.setFont(font1);
        counterPanel.add(counterLabel);

        perSecLabel = new JLabel();
        perSecLabel.setForeground(Color.white);
        counterLabel.setFont(font2);
        counterPanel.add(perSecLabel);

        JPanel itemPanel = new JPanel();
        itemPanel.setBounds(500, 170, 250, 250);
        itemPanel.setBackground(Color.black);
        itemPanel.setLayout(new GridLayout(4, 1));
        window.add(itemPanel);

        button1 = new JButton("Cursor");
        button1.setFont(font1);
        button1.setFocusPainted(false);
        button1.addActionListener(cHandler);
        button1.setActionCommand("Cursor");
        itemPanel.add(button1);

        button2 = new JButton("?");
        button2.setFont(font1);
        button2.setFocusPainted(false);
        button2.addActionListener(cHandler);
        button2.setActionCommand("Cursor");
        itemPanel.add(button2);

        button3 = new JButton("?");
        button3.setFont(font1);
        button3.setFocusPainted(false);
        button3.addActionListener(cHandler);
        button3.setActionCommand("Cursor");
        itemPanel.add(button3);

        button4 = new JButton("?");
        button4.setFont(font1);
        button4.setFocusPainted(false);
        button4.addActionListener(cHandler);
        button4.setActionCommand("Cursor");
        itemPanel.add(button4);


        window.setVisible(true);
    }

    public void setTimer() {

        timer = new Timer(timerSpeed, actionListener -> {

            cookieCounter++;
            counterLabel.setText(cookieCounter + " cookies");
        });
    }

    public void timerUpdate() {

        if (!timerOn) {
            timerOn = true;
        } else {
            timer.stop();
        }

        double speed = 1 / perSecond * 1000;
        timerSpeed = (int) Math.round(speed);

        String s = String.format(Locale.US,"%.1f", perSecond);
        perSecLabel.setText("per second: " + s);

        setTimer();
        timer.start();

    }

    public class CookieHandler implements ActionListener {

        public void actionPerformed(ActionEvent event) {

            String action = event.getActionCommand();

            switch (action) {
                case "cookie":
                    cookieCounter++;
                    counterLabel.setText(cookieCounter + " cookies");
                    break;
                case "Cursor":
                    perSecond = perSecond + 0.1;
                    timerUpdate();
            }

        }

    }

}
