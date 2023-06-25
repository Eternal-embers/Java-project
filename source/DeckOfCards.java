package javaCode;

public class DeckOfCards {
	public static void main(String[] args) {
		int[]deck = new int[52];
		String[] suits = {"Spades","Hearts","Diamonds","Clubs"};//黑桃，红桃，方块，梅花
		String[] ranks = {"Ace","2","3","4","5","6","7","8","9","10","Jack","Queen","King"};
		//Initialize the cards
		for(int i = 0;i<deck.length;i++)
			deck[i] = i;
		//打乱牌序
		for(int i = 0;i<deck.length;i++) {
			int index = (int)Math.random()*52;
			int temp = deck[i];
			deck[i] = deck[index];
			deck[index] = temp;
		}
		//呈现最开始的那四张牌
		for(int i = 0;i<4;i++) {
			String suit = suits[deck[i]/13];
			String rank = ranks[deck[i]%13];
			System.out.println("Card number" + deck[i] + ": " + rank + " of " + suit);
		}
	}
}
