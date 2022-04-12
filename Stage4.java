package banking;

import java.sql.SQLException;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.IntStream;

public class Stage4 {
	static boolean exit = true;
    static boolean exit2 = true;

    public static void main(String[] args) throws SQLException {
        //-----------SQLite codes:
        Operations connect = new Operations();
        connect.connect();
        connect.createNewDatabase(args[1]);
        connect.createNewTable();
        //------------------------------------
        Random r = new Random();
        final int[] card = new int[9];
        int check = 0;
        String number = "";
        String pins = "";
        int[] temp = new int[9];
        int[] pin = new int[4];
        long bin = 400000;
        int count = 0;
        String[] nums = new String[count];
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
                    //SQLite related codes:-------------------------------------------------

                    INSERT app = new INSERT();
                    String s = String.join("", IntStream.of(card).mapToObj(String::valueOf).toArray(String[]::new));
                    number = "400000"+s+check;
                    pins = String.join("", IntStream.of(pin).mapToObj(String::valueOf).toArray(String[]::new));
                    app.insert(number, pins);
                    count++;
                    for(int i=0; i < nums.length; ++i) {
                    	if(i == count) {
                    		nums[i] = number;
                    	}
                    }
                    //-----------------------------------------------------------------------
                    break;

                case 2:
                    System.out.println("Enter your card number:");
                    String c= sc.next();
                    System.out.println("Enter your PIN:");
                    String p = sc.next();
                    INSERT bln = new INSERT();
                    if(bln.loginAccount(c, p)) {
                        System.out.println("You have successfully logged in!");
                        //---------------------------------------------------------
                        do {
                            System.out.println("1. Balance\n2. Add income\n3. Do transfer\n4. Close account\n5. Log out\n0. Exit");
                            int opt2 = sc.nextInt();
                            
                            switch(opt2) {
                                case 1:
                                	
                                    System.out.print("Balance: ");
                                    bln.readBalance(c);
                                    break;
                                case 2:
                                	System.out.println("Enter income:");
                                	int balance = sc.nextInt();
                                	bln.balanceUpdate(balance, p);
                                	System.out.println("Income was added!");
                                	break;
                                case 3:
                                	System.out.println("Transfer\nEnter card number:");
                                	String tcard = sc.next();
                                	//checking Luhn algorithm:
                                	char[] ch = tcard.toCharArray();
                                	int[] temp2 = new int[9];
                                	int x2 = 8;
                                	for(int i=0; i < 9; ++i) {
                                		temp2[i] = ch[i+6] -48;
                                	}
                                	for(int i = 0; i < 9; ++i) {
                                        if(i == 0 || i%2 == 0) {
                                            temp2[i] = 2*temp2[i];
                                        }
                                    }
                                    for(int i = 0; i < 9; ++i) {
                                        if(temp2[i] >= 10) {
                                            temp2[i] = temp2[i] - 9;
                                        }
                                    }
                                    for(int i = 0; i < 9; ++i) {
                                        x2 = x2 + temp[i];
                                    }
                                	if(tcard.equals(c)) {
                                		System.out.println("You can't transfer money to the same account!");
                                	}else if(((x2+ch[15]-48)%10) != 0 && ch[0] -48 == 4 && ch[1] == 48 && ch[2] == 48 && ch[3] == 48 && ch[4] == 48 && ch[5] == 48) {
                                		System.out.println("Probably you made a mistake in the card number. Please try again!");
                                	}else if(((x2+ch[15]-48)%10) == 0 && ch[0] -48 == 4 && ch[1] == 48 && ch[2] == 48 && ch[3] == 48 && ch[4] == 48 && ch[5] == 48) {
                                		
                                	}
                                	break;
                                case 4:
                                	bln.deleteAccount(c);
                                	System.out.println("The account has been closed!");
                                	--count;
                                	exit2 = false;
                                	break;
                                case 5:
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
                        //--------------------------------------------------------
                    }
                    else
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

