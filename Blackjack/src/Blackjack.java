import java.util.Random;
import java.util.Scanner;
import java.util.ArrayList;

public class Blackjack{
	public static void main(String[] args) {
		int seed = Integer.parseInt(args[0]);
		int numPlayers = Integer.parseInt(args[1]);
		
		Deck deck = new Deck();
		deck.shuffle(seed);
		
		Random random = new Random();
		
		Hand[] players = new Hand[numPlayers];
		
		players[0] = new Player();
		for(int i = 1; i < numPlayers; i++) {
			players[i] = new Computer("Player"+(i+1));
		}
		
		House house = new House();
		//카드 받기
		for(int i = 0; i < 2; i++) {
			for(int j = 0; j < numPlayers; j++) {
				players[j].getCard(deck);
			}
			house.getCard(deck);
		}
		//받은 카드 계산
		for(int j = 0; j < numPlayers; j++) {
			players[j].sumCard();
		}
		house.sumCard();
		
		
		
		//각 카드 보이기
		house.printInital();
		((Player)players[0]).printHand();
		for(int j = 1; j < numPlayers; j++) {
			((Computer)players[j]).printHand();
		}
		System.out.println();
		
		//House가 21
		if(house.sumOfCards == 21) {
			Blackjack.showInitalResults(players, house);
			return;
		}
		//각 턴 실행
		((Player)players[0]).showTurn();
		((Player)players[0]).hitOrStand(deck);
		for(int j = 1;j < numPlayers; j++) {
			((Computer)players[j]).showTurn();
			((Computer)players[j]).hitOrStand(deck, random);
		}
		
		//공백 2줄
		System.out.println();
		
		//하우스 턴 실행
		house.showTurn();
		house.hitOrStand(deck);
		
		//게임 종료, 결과 출력
		Blackjack.showResults(players, house);
	}
	public static void showInitalResults(Hand[] players, House house) {
		System.out.println("--- Game Results ---");
		house.printHand();
		for(int i = 0; i < players.length; i++) {
			System.out.print("[Lose] ");
			if(i == 0) {
				((Player)players[i]).printHand();
			}
			else {
				((Computer)players[i]).printHand();
			}
		}
	}
	public static void showResults(Hand[] players, House house) {
		System.out.println("--- Game Results ---");
		house.printHand();
		if(house.isBusted()) { //house가 bust된 상태 -> 플레이어도 bust가 아니면 win
			for(int i = 0; i < players.length; i++) {
				if(i == 0) {
					if(((Player)players[i]).isBusted()){ //player1도 busted면
						System.out.print("[Lose] ");
					}
					else {
						System.out.print("[Win] ");
					}
					((Player)players[i]).printHand();
				}
				else {
					if(((Computer)players[i]).isBusted()){
						System.out.print("[Lose] ");
					}
					else {
						System.out.print("[Win] ");
					}
					((Computer)players[i]).printHand();
				}
			}
		}
		else {  //house가 bust아닌 상태 -> 플레이어가 
			for(int i = 0; i < players.length; i++) {
				if(i == 0) {
					if(((Player)players[i]).isBusted()){ //busted면
						System.out.print("[Lose] ");
					}
					else {
						if(((Player)players[i]).sumOfCards > house.sumOfCards)
							System.out.print("[Win] ");
						else if (((Player)players[i]).sumOfCards == house.sumOfCards)
							System.out.print("[Draw] ");
						else
							System.out.print("[Lose] ");
					}
					((Player)players[i]).printHand();
				}
				else {
					if(((Computer)players[i]).isBusted()){ //busted면
						System.out.print("[Lose] ");
					}
					else {
						if(((Computer)players[i]).sumOfCards > house.sumOfCards)
							System.out.print("[Win] ");
						else if (((Computer)players[i]).sumOfCards ==  house.sumOfCards)
							System.out.print("[Draw] ");
						else
							System.out.print("[Lose] ");
					}
					((Computer)players[i]).printHand();
				}
			}
		}
		
	}
}

class Card{
	private String number;
	private String suit;
	
	public Card() {}
	public Card(String theValue, String theSuit) {
		this.number = theValue;
		this.suit = theSuit;
	}
	public String getNumber() {
		return this.number;
	}
	public String getSuit() {
		return this.suit;
	}
}

class Deck{
	private Card[] deck;
	private int cardsUsed;
	private String numbers[] = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"}; //charAt(0) is all different -> use this!!
	private String symbols[] = {"c", "h", "d", "s"};

	public Deck() {
		deck = new Card[52];
		
		for(int idx = 0; idx < 52; idx++) {
			deck[idx] = new Card(numbers[idx/4], symbols[idx%4]);
		}
	}
	public void shuffle(int seed) {
		Random random = new Random(seed);
		for(int i = deck.length-1; i > 0; i--) {
			int rand = (int)(random.nextInt(i+1));
			//swap method
			Card temp = deck[i];
			deck[i] = deck[rand];
			deck[rand] = temp;
		}
		cardsUsed = 0;
	}
	public Card dealCard() {
		if(cardsUsed == deck.length) 
			throw new IllegalStateException("No cards are left in the deck.");
		cardsUsed++;
		return deck[cardsUsed - 1]; 
	}
}

class Hand{ //Set of Cards in your hand -> Player 각각에게 상속됨
	private int numberOfCards = 0; //가지고 있는 카드의 개수
	protected int sumOfCards = 0; //카드의 점수
	private int numberOfAce = 0; //Ace 카드 개수
	private ArrayList<Card> myCards = new ArrayList<>();
	
	public void getCard(Deck deck) {
		myCards.add(deck.dealCard());
		numberOfCards++;
	}
	
	public void sumCard() {
		this.sumOfCards = 0; this.numberOfAce = 0;
		for(int i = 0; i < myCards.size(); i++) {
			char number = myCards.get(i).getNumber().charAt(0);
			if(number >= '2' && number <= '9') {
				sumOfCards += number - '0';
			}
			else if(number == 'J' || number =='Q' || number == 'K' || number == '1') {
				sumOfCards += 10;
			}
			else { //Ace
				numberOfAce++;
				sumOfCards += 11; //일단 더하기
			}
		}
		int tempAce = numberOfAce;
		while(sumOfCards > 21 && tempAce > 0) {
			sumOfCards -= 10; tempAce--;
		}
	}
	public void printCards() {
		for(int i = 0; i < myCards.size(); i++) {
			System.out.print(myCards.get(i).getNumber());
			System.out.print(myCards.get(i).getSuit());
			if(i == myCards.size()-1) {
				continue;
			}
			System.out.print(", ");
		}
		System.out.print( " (" + this.sumOfCards + ")");
	}
	public ArrayList<Card> houseCards(){
		return myCards;
	}
} 

class Computer extends Hand{ //Player automatically participates
	private String name;
	private boolean busted = false; //이거 1이면 죽은거
	
	public Computer(String input) {
		this.name = input;
	}
	
	public void hitOrStand(Deck deck, Random randomSeed) {
		int is_hit = 0; int tempSum = this.sumOfCards;
		if(tempSum < 14) {
			is_hit = 1;
		}
		else if(tempSum > 17) {
			is_hit = 0;
		}
		else {
		is_hit = (int)(randomSeed.nextInt(2));
		}
		if(is_hit == 1) { //Hit
			System.out.println("Hit");
			this.getCard(deck);
			this.sumCard();
			if(this.sumOfCards > 21) {
				busted = true;
			}
			printHand();
			if(busted) {
				System.out.println();
				return;
			}
			hitOrStand(deck, randomSeed);
		}
		else { //Stand
			System.out.println("Stand");
			printHand();
			System.out.println();
		}
	}
	public void printHand() {
		System.out.print(this.name); System.out.print(": ");
		this.printCards();
		if(busted) {
			System.out.print(" - Bust!");
		}
		System.out.println();
	}
	public void showTurn() {
		System.out.println("--- " + name + " turn ---");
		printHand();
	}
	public boolean isBusted() {
		return busted;
	}
} 
class Player extends Hand{ //Player you control
	private static String name = "Player1";
	Scanner scn = new Scanner(System.in);
	private String input;
	private boolean busted = false; //이거 1이면 죽은거
	
	
	public Player() {}
	
	public void hitOrStand(Deck deck) { //입력받기
		input = scn.nextLine();
		if(input.equals("Hit")) { //Hit면
			this.getCard(deck);
			this.sumCard(); //합 업데이트 하기
			if(this.sumOfCards > 21) {
				busted = true;
			}
			printHand();
			if(busted) {
				System.out.println();
				return; //끝내기
			}
			hitOrStand(deck);
		}
		else {
			printHand();
			System.out.println();
		}
	}
	
	public void printHand() {
		System.out.print(this.name); System.out.print(": ");
		this.printCards();
		if(busted) {
			System.out.print(" - Bust!");
		}
		System.out.println();
	}
	
	public void showTurn() {
		System.out.println("--- " + name + " turn ---");
		printHand();
	}
	
	public boolean isBusted() {
		return busted;
	}

} 
class House extends Hand { //House	
	private static String name = "House";
	private boolean busted = false;
	
	public House() {}
	
	public void printInital() { //처음 카드는 가려야함 상속된 함수 말고 따로 구현
		System.out.print(this.name); System.out.print(": ");
		for(int i = 0; i < this.houseCards().size(); i++) { //첫번째 카드 넘기고
			if(i == 0) {
				System.out.print("HIDDEN");
			}else {
				System.out.print(this.houseCards().get(i).getNumber());
				System.out.print(this.houseCards().get(i).getSuit());
			}
			if(i == this.houseCards().size()-1)
				continue;
			System.out.print(", ");
		}
		System.out.println();
	}
	
	public void printHand() {
		System.out.print(this.name); System.out.print(": ");
		this.printCards();
		if(busted) {
			System.out.print(" - Bust!");
		}
		System.out.println();
	}
	public boolean isBusted() {
		return busted;
	}
	public void showTurn() {
		System.out.println("--- House turn ---");
		printHand();
	}
	public void hitOrStand(Deck deck) {
		if(this.sumOfCards <= 16) {
			System.out.println("Hit");this.getCard(deck);
			this.sumCard(); //합 업데이트 하기
			if(this.sumOfCards > 21) {
				busted = true;
			}
			printHand();
			if(busted) {
				System.out.println();
				return; //끝내기
			}
			hitOrStand(deck);
		}
		else {
			System.out.println("Stand");
			printHand();
			System.out.println();
		}
	}
	public int getSum() {
		return sumOfCards;
	}
} 
