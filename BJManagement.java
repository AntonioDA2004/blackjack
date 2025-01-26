import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class BJManagement {

    Blackjack blackjack;

    User user;

    ArrayList<Card> dealerCards = new ArrayList<>();

    ArrayList<Card> userCards = new ArrayList<>();

    public BJManagement() {
        int option;
        do {
            option = menu();
            executeOption(option);
        } while (option != 0);
    }

    public int menu() {

        String menuText;

        menuText = """
                
                1. Initialize user
                2. Start Blackjack match
                3. Check balance
                0. Terminate the program.

                Enter a number between 0 and 3:\s""";

        int option = readNumber(0,3, menuText);

        return option;
    }

    public int readNumber(int n1, int n2, String text) {
        Scanner scanner = new Scanner(System.in);
        int num = -1;
        do {
            System.out.print(text);
            try {
                num = scanner.nextInt();
                if (num < n1)
                    System.out.println("The number " + num + " is not greater than " + n1);
                else if (num > n2)
                    System.out.println("The number " + num + " is not less than " + n2);
            } catch (InputMismatchException e){
                System.out.print("Not a number. ");
                scanner.next();
            }
        } while (num < n1 || num > n2);
        return num;
    }

    public void executeOption(int option) {

        switch(option){

            case -1: {
                break;
            }

            case 1: {
                String cashText = "\nPlease enter the cash for the user (1-10000): ";
                int cash = readNumber(1,10000, cashText);

                user = new User(cash);

                System.out.println("User initialized! You have " + cash + "€");

                break;
            }

            case 2: {
                if(user != null) {
                    if(user.getCash() != 0) {
                        int userCash = user.getCash();
                        String betText = "\nPlease enter the bet for the match (1-" + userCash + "): ";
                        int bet = readNumber(1, userCash, betText);
                        int hitOrStand;

                        blackjack = new Blackjack(bet);

                        Card firstDealerCard = Card.generateCard();
                        Card secondDealerCard = Card.generateCard();

                        Card firstUserCard = Card.generateCard();
                        Card secondUserCard = Card.generateCard();

                        dealerCards.add(firstDealerCard);

                        userCards.add(firstUserCard);
                        userCards.add(secondUserCard);

                        Card.showCards(dealerCards, "dealer");

                        dealerCards.add(secondDealerCard);

                        System.out.println();

                        Card.showCards(userCards, "user");

                        String hitOrStandText = "\nHit (0) or stand (1): ";

                        do {
                            hitOrStand = readNumber(0, 1, hitOrStandText);

                            if (hitOrStand == 0) {
                                Card userCard = Card.generateCard();

                                userCards.add(userCard);

                                if (!(Card.totalValue(userCards) > 21 && Card.equals(userCards, new Card(11, "Ace"))))
                                    Card.showCards(userCards, "user");
                                else {
                                    Card.delete(userCards, new Card(11, "Ace"));
                                    userCards.add(new Card(1, "Ace"));
                                    Card.showCards(userCards, "user");
                                }
                            }
                        } while (hitOrStand == 0 && Card.totalValue(userCards) <= 21);

                        if (Card.totalValue(userCards) > 21 && Card.equals(userCards, new Card(11, "Ace"))) {
                            Card.delete(userCards, new Card(11, "Ace"));
                            userCards.add(new Card(1, "Ace"));
                            do {
                                hitOrStand = readNumber(0, 1, hitOrStandText);

                                if (hitOrStand == 0) {
                                    Card userCard = Card.generateCard();

                                    userCards.add(userCard);

                                    Card.showCards(userCards, "user");
                                }
                            } while (hitOrStand == 0 && Card.totalValue(userCards) <= 21);
                        } else if (Card.totalValue(userCards) > 21 && !Card.equals(userCards, new Card(11, "Ace"))) {
                            System.out.println("You have busted, you lost.");
                            user.setCash(user.getCash() - bet);
                            userCards.clear();
                            dealerCards.clear();
                            System.out.println("You now have: " + user.getCash() + "€");
                            break;
                        }

                        if (Card.totalValue(userCards) > 21) {
                            System.out.println("You have busted, you lost.");
                            user.setCash(user.getCash() - bet);
                            userCards.clear();
                            dealerCards.clear();
                            System.out.println("You now have: " + user.getCash() + "€");
                            break;
                        }

                        if (Card.totalValue(dealerCards) < 17 && Card.totalValue(dealerCards) <= Card.totalValue(userCards)) {
                            do {
                                Card dealearCard = Card.generateCard();

                                dealerCards.add(dealearCard);

                                Card.showCards(dealerCards, "dealer");

                            } while (Card.totalValue(dealerCards) < 17 && Card.totalValue(dealerCards) <= Card.totalValue(userCards));
                        } else {
                            Card.showCards(dealerCards, "dealer");
                        }

                        if (Card.totalValue(dealerCards) > 21) {
                            System.out.println("The dealer busted, you win!");
                            user.setCash(user.getCash() + bet);
                        } else if (Card.totalValue(dealerCards) > Card.totalValue(userCards) && Card.totalValue(dealerCards) <= 21) {
                            System.out.println("You lose!");
                            user.setCash(user.getCash() - bet);
                        } else if (Card.totalValue(dealerCards) == Card.totalValue(userCards)) {
                            System.out.println("It's a draw!");
                        } else if (Card.totalValue(dealerCards) < Card.totalValue(userCards)) {
                            System.out.println("You win!");
                            user.setCash(user.getCash() + bet);
                        }

                        System.out.println("You now have: " + user.getCash() + "€");

                        userCards.clear();
                        dealerCards.clear();
                    } else System.out.println("You have no money left!");
                } else {
                    System.out.println("User not initialized!");
                }

                break;

            }

            case 3: {
                if (user != null) {
                    System.out.println("You have " + user.getCash() + "€");
                } else {
                    System.out.println("User not initialized!");
                }

                break;
            }

            case 0: {
                System.out.print("\nThanks for playing!\n");
                break;
            }
        }

    }

    public static void main(String[] args) {
        BJManagement BJManagement = new BJManagement();
    }

}
