import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Locale;
import java.util.Objects;

// https://cookieclicker.fandom.com/wiki/Building

public class Main {

    JLabel counterLabel, perSecLabel;
    JButton button1, button2, button3, button4;
    int cookieCounter, timerSpeed, cursorNumber, cursorPrice, grandmaNumber, grandmaPrice;
    double perSecond;
    boolean timerOn, grandmaUnlocked;
    Font font1, font2;
    CookieHandler cHandler = new CookieHandler();
    Timer timer;
    JTextArea messageText;
    MouseHandler mHandler = new MouseHandler();

    public static void main(String[] args) {
        new Main();
    }

    public Main() {

        timerOn = false;
        grandmaUnlocked = false;
        perSecond = 0;
        //TODO: change
        cookieCounter = 90;
        cursorNumber = 0;
        cursorPrice = 10;
        grandmaNumber = 0;
        grandmaPrice = 100;

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

        JPanel cookiePanel = cookiePanel();
        window.add(cookiePanel);

        JButton cookieButton = cookieButton();
        cookiePanel.add(cookieButton);

        JPanel counterPanel = customPanel(100, 100, 200, 100, 2, 1);
        window.add(counterPanel);

        counterLabel = new JLabel(cookieCounter + " cookies");
        counterLabel.setForeground(Color.white);
        counterLabel.setFont(font1);
        counterPanel.add(counterLabel);

        perSecLabel = new JLabel();
        perSecLabel.setForeground(Color.white);
        counterLabel.setFont(font2);
        counterPanel.add(perSecLabel);

        JPanel itemPanel = customPanel(500, 170, 250, 250, 4, 1);
        window.add(itemPanel);

        button1 = buildingButton("Cursor", "Cursor");
        itemPanel.add(button1);

        button2 = buildingButton("?", "Grandma");
        itemPanel.add(button2);

        button3 = buildingButton("?", "");
        itemPanel.add(button3);

        button4 = buildingButton("?", "");
        itemPanel.add(button4);

        JPanel messagePanel = new JPanel();
        messagePanel.setBounds(500, 70, 250, 150);
        messagePanel.setBackground(Color.black);
        window.add(messagePanel);

        messageText = new JTextArea();
        messageText.setBounds(500, 70, 250, 150);
        messageText.setForeground(Color.white);
        messageText.setBackground(Color.black);
        messageText.setFont(font2);
        messageText.setLineWrap(true);
        messageText.setWrapStyleWord(true);
        messageText.setEditable(false);
        messagePanel.add(messageText);


        window.setVisible(true);
    }

    public JPanel cookiePanel() {
        JPanel cookiePanel = new JPanel();
        cookiePanel.setBounds(100, 220, 200, 210);
        cookiePanel.setBackground(Color.black);
        return cookiePanel;
    }

    public JButton cookieButton() {

        ImageIcon cookie = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("cookie.png")));

        JButton cookieButton = new JButton();
        cookieButton.setBackground(Color.black);
        cookieButton.setFocusPainted(false);
        cookieButton.setBorder(null);
        cookieButton.setContentAreaFilled(false);
        cookieButton.setIcon(cookie);
        cookieButton.addActionListener(cHandler);
        cookieButton.setActionCommand("cookie");
        return  cookieButton;
    }

    public JPanel customPanel(int x, int y, int width, int height, int rows, int cols) {
        JPanel counterPanel = new JPanel();
        counterPanel.setBounds(x, y, width, height);
        counterPanel.setBackground(Color.black);
        counterPanel.setLayout(new GridLayout(rows, cols));
        return counterPanel;
    }

    public JButton buildingButton(String text, String actionCommand) {
        JButton button = new JButton(text);
        button.setFont(font1);
        button.setFocusPainted(false);
        button.addActionListener(cHandler);
        button.setActionCommand(actionCommand);
        button.addMouseListener(mHandler);

        return button;
    }

    public void setTimer() {

        timer = new Timer(timerSpeed, actionListener -> {

            cookieCounter++;
            counterLabel.setText(cookieCounter + " cookies");

            if (!grandmaUnlocked) {
                if (cookieCounter >= 100) {
                    grandmaUnlocked = true;
                    button2.setText("Grandma " + "(" + grandmaNumber + ")");
                }
            }
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

        public void actionCase(String name, int buildingPrice, int priceInc, double clicksPS, int buildingNumber, JButton button) {

            if (cookieCounter >= buildingPrice) {
                cookieCounter -= buildingPrice;
                buildingPrice += priceInc;
                counterLabel.setText(cookieCounter + " cookies");

                buildingNumber++;
                button.setText(name + " " + "(" + buildingNumber + ")");
                messageText.setText(name + "\n[price: " + buildingPrice + "]\nGenerates " + clicksPS + " cookie per second");
                perSecond += clicksPS;

                if (name.equals("Cursor")) {
                    cursorPrice = buildingPrice;
                    cursorNumber = buildingNumber;
                }
                if (name.equals("Grandma")) {
                    grandmaPrice = buildingPrice;
                    grandmaNumber = buildingNumber;
                }

                timerUpdate();
            } else {
                messageText.setText("You need more cookies!");
            }

        }

        public void actionPerformed(ActionEvent event) {

            String action = event.getActionCommand();

            switch (action) {
                case "cookie":
                    cookieCounter++;
                    counterLabel.setText(cookieCounter + " cookies");

                    if (!grandmaUnlocked) {
                        if (cookieCounter >= 100) {
                            grandmaUnlocked = true;
                            button2.setText("Grandma " + "(" + grandmaNumber + ")");
                        }
                    }

                    break;
                case "Cursor":
                    actionCase("Cursor", cursorPrice, 5, 0.1, cursorNumber, button1);
                    break;
                case "Grandma":
                    actionCase("Grandma", grandmaPrice, 50, 1, grandmaNumber, button2);
                    break;
            }

        }

    }

    public class MouseHandler implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {

        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

            JButton button = (JButton) e.getSource();

            if (button == button1) {
                messageText.setText("Cursor\n[price: " + cursorPrice + "]\nGenerates 0.1 cookie per second");
            } else if (button == button2) {
                if (!grandmaUnlocked) {
                    messageText.setText("This item is currently locked!");
                } else {
                    messageText.setText("Grandma\n[price: " + grandmaPrice + "]\nGenerates 1 cookie per second");
                }

            } else if (button == button3) {
                messageText.setText("This item is currently locked!");
            } else if (button == button4) {
                messageText.setText("This item is currently locked!");
            }
        }

        @Override
        public void mouseExited(MouseEvent e) {

            JButton button = (JButton) e.getSource();
            if (button == button1) {
                messageText.setText(null);
            } else if (button == button2) {
                messageText.setText(null);
            } else if (button == button3) {
                messageText.setText(null);
            } else if (button == button4) {
                messageText.setText(null);
            }
        }
    }

}
