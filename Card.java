import java.util.ArrayList;
import java.util.Random;

public class Card {
    private int value;
    private String name;

    public Card(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static Card generateCard(){
        Random random = new Random();

        int[] values = {11, 2, 3, 4, 5, 6, 7, 8, 9, 10, 10, 10, 10};
        String[] names = {"Ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};

        int randomValue = random.nextInt(1,13);

        return new Card(values[randomValue], names[randomValue]);
    }

    public static void showCards(ArrayList<Card> cards, String userDealer){
        if(userDealer.equals("dealer")) System.out.println("The dealer's cards are: ");
        else if(userDealer.equals("user")) System.out.println("Your cards are: ");

        for(int i = 0; i < cards.size(); i++){
            Card card = cards.get(i);
            System.out.println("Card " + (i+1) + ": " + card.getName() + " (Value: " + card.getValue() + ")");
        }
        if(userDealer.equals("dealer")) System.out.println("The total value of the dealer's cards is: " + totalValue(cards));
        else if(userDealer.equals("user")) System.out.println("The total value of your cards is: " + totalValue(cards));
    }

    public static int totalValue(ArrayList<Card> cards){
        int counter = 0;
        for(int i = 0; i < cards.size(); i++){
            counter = counter + cards.get(i).getValue();
        }
        return counter;
    }

    public static boolean equals(ArrayList<Card> cards, Card card){
        boolean resultado = false;
        for(int i = 0; i < cards.size(); i++){
            Card cardList = cards.get(i);
            if(cardList.getValue() == card.getValue() && cardList.getName().equals(card.getName())) resultado= true;
        }
        return resultado;
    }

    public static void delete(ArrayList<Card> cards, Card card){
        for(int i = 0; i < cards.size(); i++){
            Card cardList = cards.get(i);
            if(cardList.getValue() == card.getValue() && cardList.getName().equals(card.getName())) cards.remove(cardList);
        }
    }
}