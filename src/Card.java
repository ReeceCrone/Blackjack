
public class Card {

    //The suit of the card
    private String suit;

    //The rank of the card
    private String rank;

    private int val;

    public Card(String suit, String rank, int val) {
        this.suit = suit;
        this.rank = rank;
        this.val = val;
    }

    public String getSuit() {
        return suit;
    }

    public String getRank() {
        return rank;
    }

    public int getVal() {
        return val;
    }

    public static void main(String[] args){
        Card c1 = new Card("Clubs", "Five", 5);
        System.out.println(c1.rank);
        System.out.println(" of ");
        System.out.println(c1.suit);
    }
}