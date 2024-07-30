

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

public class Game {
    

    private Deck gameDeck;

    private ArrayList<Card> playerHand;

    private ArrayList<Card> dealerHand;

    private ArrayList<Integer> playerCordsX;

    private ArrayList<Integer> dealerCordsX;

    private ArrayList<Integer> playerCordsY;

    private ArrayList<Integer> dealerCordsY;

    private ArrayList<Integer> chipsX;

    private ArrayList<Integer> chipsY;

    private ArrayList<Integer> chipsImg;

    private int playerVal = 0;

    private int dealerVal = 0;

    private int playerAces = 0;

    private int dealerAces = 0;

    private double playerChips = 15.00;

    private float currentBet = 1;

    private String message = "None";

    //Window
    int boardWidth = 800;
    int boardHeight = 800;

    int cardWidth = 150;
    int cardHeight = 210;
    
    int rand1;
    int rand2;

    JFrame frame = new JFrame("BlackJack");
    
    JPanel gamePanel = new JPanel() {
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            
            try {
                Font font = new Font("Times New Roman", Font.BOLD, 20);
                g.setFont(font);
                g.setColor(new Color(255, 216, 0));
                
                g.drawString("Balance: $" + String.valueOf(playerChips * 5) + "0", 15, 700);
                g.drawString("Bet: $" + String.valueOf(currentBet * 5) + "0", 15, 720);

            
                //draws the rules
                Image rulesLabel = new ImageIcon(getClass().getResource("/images/thing.png")).getImage();
                g.drawImage(rulesLabel, 150, 250, 450, 120, null);

                if (playerChips == 0 && message == "None") {
                    g.drawString("YOU'RE BROKE!", boardWidth/2 - 75, 720);
                }

                //draws all the chips
                if (playerChips != 0) {
                    for (int i = 0; i < playerChips; i++) {
                        while (chipsX.size() < playerChips) {
                            int rand1 = (int) ((Math.random() * (150 + 150)) - 150);
                            int rand2 = (int) ((Math.random() * (80 + 80)) - 80);
                            int rand3 = (int) ((Math.random() * (5 - 1)) + 1);
                            chipsX.add(rand1); //x coord of a chip
                            chipsY.add(rand2); //y coord of a chip
                            chipsImg.add(rand3); //array for storing the chip image for each chip
                        }
                        String path = "/images/CHIP" + chipsImg.get(i) + ".png";
                        Image chipImage = new ImageIcon(getClass().getResource(path)).getImage();
                        g.drawImage(chipImage, 550 + chipsX.get(i), 550 + chipsY.get(i), 90, 90, null);
                    }
                }
                
                //draws the dealers hidden card if cards are to be dealt
                if (!dealButton.isEnabled()) {
                    Image hiddenCardImage = new ImageIcon(getClass().getResource("/images/1BACK.png")).getImage();
                    if (!stayButton.isEnabled()) { //if player stays show hidden card
                        Card card = dealerHand.get(0);
                        String path = "/images/" + "1" + card.getRank() + card.getSuit() + ".png";
                        hiddenCardImage = new ImageIcon(getClass().getResource(path)).getImage();
                    }
                    g.drawImage(hiddenCardImage, 20, 20, cardWidth, cardHeight, null);
                }
                

                //draw dealer hand
                for (int i = 1; i < dealerHand.size(); i++) {
                    Card card = dealerHand.get(i);
                    String path = "/images/" + "1" + card.getRank() + card.getSuit() + ".png";
                    Image cardImage = new ImageIcon(getClass().getResource(path)).getImage();
                    g.drawImage(cardImage, cardWidth + 25 + (cardWidth - dealerCordsX.get(i))*(i - 1), 20 + dealerCordsY.get(i), cardWidth, cardHeight, null);
                }
                //draw players hand
                for (int i = 0; i < playerHand.size(); i++) {
                    Card card = playerHand.get(i);
                    String path = "/images/" + "1" + card.getRank() + card.getSuit() + ".png";
                    Image cardImage = new ImageIcon(getClass().getResource(path)).getImage();
                    g.drawImage(cardImage, 20 + (cardWidth - playerCordsX.get(i))*i, 390 + playerCordsY.get(i), cardWidth, cardHeight, null);
                }

                if (message != "None") {
                    g.drawString(message, boardWidth/2 - 75, 720);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };




    JPanel buttonPanel = new JPanel();
    JButton dealButton = new JButton("DEAL");
    JButton resetButton = new JButton("RESET");
    JButton hitButton = new JButton("HIT");
    JButton stayButton = new JButton("STAY");
    JButton increaseButton = new JButton("INCREASE BET");
    JButton decreaseButton = new JButton("DECREASE BET");

    public Game() {
        this.dealerHand = new ArrayList<Card>();
        this.playerHand = new ArrayList<Card>();
        this.dealerCordsX = new ArrayList<Integer>();
        this.dealerCordsY = new ArrayList<Integer>();
        this.playerCordsX = new ArrayList<Integer>();
        this.playerCordsY = new ArrayList<Integer>();
        this.chipsX = new ArrayList<Integer>();
        this.chipsY = new ArrayList<Integer>();
        this.chipsImg = new ArrayList<Integer>();
        this.gameDeck = new Deck();



        frame.setVisible(true);
        frame.setSize(boardWidth, boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        

        gamePanel.setLayout(new BorderLayout());
        gamePanel.setBackground(new Color(30, 90, 50));
        
        frame.add(gamePanel);

        hitButton.setEnabled(false);
        stayButton.setEnabled(false);
        resetButton.setEnabled(false);

        //stylize the buttons and buttonPanel
        buttonPanel.setBorder(new LineBorder(new Color(255, 216, 0)));
        buttonPanel.setBackground(Color.BLACK);

        dealButton.setBorder(new LineBorder(new Color(255, 216, 0)));
        dealButton.setForeground(new Color(255, 216, 0));
        dealButton.setBackground(Color.BLACK);
        dealButton.setFont(new Font("Times New Roman", Font.BOLD, 15));

        hitButton.setBorder(new LineBorder(new Color(255, 216, 0)));
        hitButton.setForeground(new Color(255, 216, 0));
        hitButton.setBackground(Color.BLACK);
        hitButton.setFont(new Font("Times New Roman", Font.BOLD, 15));
        
        stayButton.setBorder(new LineBorder(new Color(255, 216, 0)));
        stayButton.setForeground(new Color(255, 216, 0));
        stayButton.setBackground(Color.BLACK);
        stayButton.setFont(new Font("Times New Roman", Font.BOLD, 15));
        
        increaseButton.setBorder(new LineBorder(new Color(255, 216, 0)));
        increaseButton.setForeground(new Color(255, 216, 0));
        increaseButton.setBackground(Color.BLACK);
        increaseButton.setFont(new Font("Times New Roman", Font.BOLD, 15));

        decreaseButton.setBorder(new LineBorder(new Color(255, 216, 0)));
        decreaseButton.setForeground(new Color(255, 216, 0));
        decreaseButton.setBackground(Color.BLACK);
        decreaseButton.setFont(new Font("Times New Roman", Font.BOLD, 15));

        resetButton.setBorder(new LineBorder(new Color(255, 216, 0)));
        resetButton.setForeground(new Color(255, 216, 0));
        resetButton.setBackground(Color.BLACK);
        resetButton.setFont(new Font("Times New Roman", Font.BOLD, 15));

        dealButton.setFocusable(false);
        buttonPanel.add(dealButton);
        resetButton.setFocusable(false);
        buttonPanel.add(resetButton);
        hitButton.setFocusable(false);
        buttonPanel.add(hitButton);
        stayButton.setFocusable(false);
        buttonPanel.add(stayButton);
        increaseButton.setFocusable(false);
        buttonPanel.add(increaseButton);
        decreaseButton.setFocusable(false);
        buttonPanel.add(decreaseButton);
        frame.add(buttonPanel, BorderLayout.SOUTH);
        
        // deal button
        dealButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                hitButton.setEnabled(true);
                stayButton.setEnabled(true);
                resetButton.setEnabled(false);
                firstDeal();
                dealButton.setEnabled(false);
                increaseButton.setEnabled(false);
                decreaseButton.setEnabled(false);
                gamePanel.repaint();

            }
        });

        //reset button
        resetButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                gameDeck.shuffle();
                playerHand.clear();
                dealerHand.clear();
                playerCordsX.clear();
                playerCordsY.clear();
                dealerCordsX.clear();
                dealerCordsY.clear();
                
                playerVal = 0;
                dealerVal = 0;
                playerAces = 0;
                dealerAces = 0;
                if (currentBet > playerChips) {
                    dealButton.setEnabled(false);
                } else {dealButton.setEnabled(true);}
                increaseButton.setEnabled(true);
                decreaseButton.setEnabled(true);
                gamePanel.repaint();
                message = "None";
            }
        });

        //hit button
        hitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Card card = gameDeck.deal();
                playerHand.add(card);

                rand1 = (int) ((Math.random() * (80 - 70)) + 70);
                rand2 = (int) ((Math.random() * (15 + 15)) - 15);

                playerCordsX.add(rand1);
                playerCordsY.add(rand2);

                if (aceCheck(playerHand)) {playerAces++;}
                playerVal += playerHand.get(playerHand.size() -1).getVal();
                if (reducePlayerAces() > 21) {
                    message = "PLAYER BUSTS";
                    playerChips-=currentBet;
                    stayButton.setEnabled(false);
                    hitButton.setEnabled(false);
                    resetButton.setEnabled(true);
                }
                gamePanel.repaint();
            }
        });

        //stay button
        stayButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                hitButton.setEnabled(false);
                stayButton.setEnabled(false);
                resetButton.setEnabled(true);

                while (dealerVal < 17) {
                    Card card = gameDeck.deal();
                    dealerHand.add(card);

                    rand1 = (int) ((Math.random() * (80 - 70)) + 70);
                    rand2 = (int) ((Math.random() * (10 + 10)) - 10);

                    dealerCordsX.add(rand1);
                    dealerCordsY.add(rand2);

                    if (aceCheck(dealerHand)) {dealerAces++;}
                    dealerVal += dealerHand.get(dealerHand.size() -1).getVal();
                    if (reduceDealerAces() > 21) { //dealer busts
                        message = "DEALER BUSTS";
                    }
                }
                if (dealerVal > playerVal && dealerVal <= 21) {
                    playerChips-=currentBet;
                    message = "DEALER WINS";
                } else if (dealerVal != playerVal) {
                    playerChips+=currentBet;
                    message = "PLAYER WINS";
                } else {message = "PUSH";}
                gamePanel.repaint();
            }
        });

        //increase bet button
        increaseButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (playerChips >= 1 && currentBet < playerChips) {
                    currentBet+=1;
                }
                gamePanel.repaint();
            }
        });

        //decrease bet button
        decreaseButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (currentBet > 1) {
                    currentBet-=1;
                }
                if (currentBet < playerChips) {dealButton.setEnabled(true);}
                gamePanel.repaint();
            }
        });

    }

    public ArrayList<Card> getDealerHand() {
        return dealerHand;
    }

    public ArrayList<Card> getPlayerHand() {
        return playerHand;
    }

    

    public void firstDeal() {
        playerHand.add(gameDeck.deal());
        playerVal += playerHand.get(playerHand.size() -1).getVal();
        if (aceCheck(playerHand)) {playerAces++;}
        dealerHand.add(gameDeck.deal());
        dealerVal += dealerHand.get(dealerHand.size() -1).getVal();
        if (aceCheck(dealerHand)) {dealerAces++;}
        playerHand.add(gameDeck.deal());
        playerVal += playerHand.get(playerHand.size() -1).getVal();
        if (aceCheck(playerHand)) {playerAces++;}
        dealerHand.add(gameDeck.deal());
        dealerVal += dealerHand.get(dealerHand.size() -1).getVal();
        if (aceCheck(dealerHand)) {dealerAces++;}
        rand1 = (int) ((Math.random() * (80 - 70)) + 70);
        rand2 = (int) ((Math.random() * (7 + 7)) - 7);

        dealerCordsX.add(0);
        dealerCordsY.add(0);
        dealerCordsX.add(rand1);
        dealerCordsY.add(rand2);

        rand1 = (int) ((Math.random() * (80 - 70)) + 70);
        rand2 = (int) ((Math.random() * (10 + 10)) - 10);

        playerCordsX.add(0);
        playerCordsY.add(0);
        playerCordsX.add(rand1);
        playerCordsY.add(rand2);

        if (playerVal == 21) {
            message = "BLACKJACK";
            playerChips+=currentBet*1.5;
            resetButton.setEnabled(true);
            hitButton.setEnabled(false);
            stayButton.setEnabled(false);
        }
    }

    public boolean aceCheck(ArrayList<Card> hand) {
        return (hand.get(hand.size() -1).getRank() == "ACE");
    }

    /*
     * old method for check the game in the console
     */
    public String GameToString() {
        String result = "DEALER:\n";
        result += dealerHand.get(0).getRank() + " of " + dealerHand.get(0).getSuit() + "\n";
        result += dealerHand.get(1).getRank() + " of " + dealerHand.get(1).getSuit() + "\n";
        result += "Dealer has " + dealerVal + "\n";
        result += "Dealer has " + dealerAces + " Aces\n";
        result += "PLAYER:\n";
        result += playerHand.get(0).getRank() + " of " + playerHand.get(0).getSuit() + "\n";
        result += playerHand.get(1).getRank() + " of " + playerHand.get(1).getSuit() + "\n";
        result += "Player has " + playerVal + "\n";
        result += "Player has " + playerAces + " Aces";
        return result;
    }

    /*
     * Uses alternate value of aces to get value back under 21 for the player
     */
   public int reducePlayerAces(){
    while (playerVal > 21 && playerAces > 0) {
        playerAces--;
        playerVal-=10;
    }
    return playerVal;
   }

   /*
     * Uses alternate value of aces to get value back under 21 for the dealer
     */
   public int reduceDealerAces(){
    while (dealerVal > 21 && dealerAces > 0) {
        dealerAces--;
        dealerVal-=10;
    }
    return dealerVal;
   }

    public static void main(String[] args){
    }


}
