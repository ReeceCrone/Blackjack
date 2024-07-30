

import java.util.ArrayList;
import java.util.Collections;

public class Deck {
    private ArrayList<Card> cards;

    private int topCard;

    public Deck() {
        this.cards = new ArrayList<Card>();
        topCard = 0;
        String[] values = {"TWO", "THREE", "FOUR", "FIVE", "SIX", "SEVEN", "EIGHT", "NINE", "TEN",
        "JACK", "QUEEN", "KING", "ACE"};
        String[] suits = {"DIAMONDS", "CLUBS", "HEARTS", "SPADES"};
        int weight = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 13; j++) {
                if (j <= 8) {
                    weight = j;
                } else if (j == 12) {
                    weight = 9;
                } else {
                    weight = 8;
                }
                Card newCard = new Card(suits[i], values[j], weight + 2);
                cards.add(newCard);
            }
        }
        shuffle();
    }

    public ArrayList<Card> getCards() {
        return this.cards;
    }

    public void shuffle() {
        topCard = 0;
        Collections.shuffle(cards);
    }

    public Card deal() {
        Card dealt = cards.get(topCard);
        topCard++;
        return dealt;
    }

    public static void main(String[] args) {
        Deck deck1 = new Deck();
        deck1.shuffle();
        for (int i = 0; i < 52; i++){
            System.out.println(deck1.cards.get(i).getRank() + " of " + deck1.cards.get(i).getSuit());
        }
    }
}
