package jp.ac.uryukyu.ie.e205757;

import java.util.Random;
import java.util.Scanner;
import java.util.ArrayList;

public class Facilitator {
    private int cardNumber;
    ArrayList<Character> c = new ArrayList<Character>();

    public ArrayList<Integer> distribute(Random r) {
        ArrayList<Integer> distributeCards = new ArrayList<Integer>();
        for (int i = 0; i < 5; i++) {
            this.cardNumber = r.nextInt(5);
            distributeCards.add(this.cardNumber);
        }
        return distributeCards;
    }

    public Facilitator() {
        Random bCard = new Random();
        ArrayList<Integer> bHand = this.distribute(bCard);
        Bot b = new Bot(bHand);
        Random pCard = new Random();
        ArrayList<Integer> cHand = this.distribute(pCard);
        Player p = new Player(cHand);
        c.add(p);
        c.add(b);

        progress();
    }

    /*
     * public boolean isFinished(){ var p = c.get(0); var b = c.get(1); if(){ return
     * true; }else{ return false; } }
     */

    public void progress() {
        var p = c.get(0);
        var b = c.get(1);
        System.out.println("あなたの手札は" + p.getHand() + "です");
        System.out.println("自分の手札から1枚だけ捨ててランダムな1枚と交換することができます。\n捨てたいカードの数字を入力してください。(捨てたくない場合はそのままEnter)");
        Scanner scanner = new Scanner(System.in);
        int num = scanner.nextInt();
        discard(p, num);
        scanner.close();
        System.out.println("ボットの手札は" + b.getHand() + "です");
        judge(p, b);
    }

    public void discard(Character p, int cardNumber) {
        ArrayList<Integer> playerHand = new ArrayList<Integer>();
        playerHand = p.getHand();
        System.out.println(cardNumber + "を捨てました。");
        for (int i = 0; i < playerHand.size(); i++) {
            int j = playerHand.get(i);
            if (cardNumber == j) {
                playerHand.remove(i);
                break;
            }
        }
        addCard(p);
    }

    public void addCard(Character p) {
        Random r = new Random();
        int getCard = r.nextInt(5);
        p.hand.add(getCard);
        System.out.println(getCard + "のカードを手に入れました。\nあなたの手札は" + p.getHand() + "です");
    }

    public void judge(Character p, Character b){
        ArrayList<Integer> playerHand = new ArrayList<Integer>();
        playerHand = p.getHand();
        int playerPoint = 0;
        for(int i=0; i<playerHand.size(); i++){
            int j = playerHand.get(i);
            playerPoint += j;
        }
        ArrayList<Integer> botHand = new ArrayList<Integer>();
        botHand = b.getHand();
        int botPoint = 0;
        for(int i=0; i<botHand.size(); i++){
            int j = botHand.get(i);
            botPoint += j;
        }

        System.out.println("あなたの手札の合計は" + playerPoint + "です。");
        System.out.println("ボットの手札の合計は" + botPoint + "です。");

        if(playerPoint > botPoint){
            int point = p.getPoint();
            p.setPoint(point+=1);
            System.out.println("あなたの勝ちです！");
        }else if(playerPoint < botPoint){
            int point = b.getPoint();
            b.setPoint(point+=1);
            System.out.println("あなたの負けです…");
        }
    }
}