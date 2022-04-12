package banking;

import java.util.Random;
import java.util.Scanner;
import java.util.stream.IntStream;

public class Stage3 {

    public static void main(String[] args) {
        //-----------SQLite codes:
        Operations connect = new Operations();
        connect.connect();
        connect.createNewDatabase(args[1]);
        connect.createNewTable();
        //------------------------------------

        boolean exit = true;
        boolean exit2 = true;
        Random r = new Random();
        final int[] card = new int[9];
        int check = 0;
        String number = "";
        String pins = "";
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
                    //SQLite related codes:-------------------------------------------------

                    INSERT app = new INSERT();
                    String s = String.join("", IntStream.of(card).mapToObj(String::valueOf).toArray(String[]::new));
                    number = "400000"+s+check;
                    pins = String.join("", IntStream.of(pin).mapToObj(String::valueOf).toArray(String[]::new));
                    app.insert(number, pins);
                    //-----------------------------------------------------------------------
                    break;

                case 2:
                    System.out.println("Enter your card number:");
                    String c= sc.next();
                    char[] cch = c.toCharArray();
                    System.out.println("Enter your PIN:");
                    String p = sc.next();
                    char[] pinch = p.toCharArray();
                    if(c.equals(number) && p.equals(pins)) {
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

