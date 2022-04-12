package banking;

import java.util.Random;
import java.util.Scanner;

public class Stage2 {

	public static void main(String[] args) {
		boolean exit = true;
		boolean exit2 = true;
		Random r = new Random();
		final int[] card = new int[9];
		int check = 0;
		int[] temp = new int[9];
		int[] pin = new int[4];
		long bin = 400000;
		Scanner sc = new Scanner(System.in);
		do {
			System.out.println("1. Create an account\n2. Log into account\n0. Exit");
			int option = sc.nextInt();
			switch(option) {
			case 1:
				System.out.println("Your card has been created\nYour card number:");
				int x = 0;
				
				System.out.print(bin);
				for(int i=0; i < card.length; ++i) {
					card[i] = r.nextInt(10);
					System.out.print(card[i]);
				}
				for(int i=0; i < 9; ++i) {
					temp[i] = card[i];
				}
				for(int i = 0; i < 9; ++i) {
					if(i == 0 || i%2 == 0) {
						temp[i] = 2*temp[i];
					}
				}
				for(int i = 0; i < 9; ++i) {
					if(temp[i] >= 10) {
						temp[i] = temp[i] - 9;
					}
				}
				for(int i = 0; i < 9; ++i) {
					x = x + temp[i];
				}
				x = x + 8;
				for(int i=0; i < 10; ++i) {
					if((x + i) % 10 == 0) {
						check = i;
					}
				}
				System.out.print(check);
				System.out.println();
				System.out.println("Your card PIN:");
				for(int i=0; i < pin.length; ++i) {
					pin[i] = r.nextInt(10);
					System.out.print(pin[i]);
				}System.out.println();
				break;
				
			case 2:
				System.out.println("Enter your card number:");
				String c= sc.next();
				char[] cch = c.toCharArray();
				System.out.println("Enter your PIN:");
				String p = sc.next();
				char[] pinch = p.toCharArray();
				if(pinch[0]-48 == pin[0] && pinch[1]-48 == pin[1] && pinch[2]-48 == pin[2] && pinch[3]-48 == pin[3] && cch[0]-48 == 4 && cch[1] == 48 && cch[2] == 48 && cch[3] == 48 &&cch[4] == 48 && cch[5] == 48 && cch[6]-48 == card[0] && cch[7]-48 == card[1] && cch[8]-48 == card[2] && cch[9]-48 == card[3] && cch[10]-48 == card[4] && cch[11]-48 == card[5] && cch[12]-48 == card[6] && cch[13]-48 == card[7] && cch[14]-48 == card[8] && cch[15]-48 == check) {
					System.out.println("You have successfully logged in!");
					do {
					System.out.println("1. Balance\n2. Log out\n0. Exit");
					int opt2 = sc.nextInt();
					switch(opt2) {
					case 1:
						System.out.println("Balance: 0");
						break;
					case 2:
						System.out.println("You have successfully logged out!");
						exit2 = false;
						break;
					case 0:
						System.out.println("Bye!");
						exit2 = false;
						exit = false;
						break;
					}
				}while(exit2);
				}else
					System.out.println("Wrong card number or PIN!");
				break;
			case 0:
				System.out.println("Bye!");
				exit = false;
				break;
			}
		}while(exit);
	}
}
