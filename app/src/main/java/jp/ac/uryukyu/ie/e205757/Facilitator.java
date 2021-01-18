package jp.ac.uryukyu.ie.e205757;

import java.util.Random;
import java.util.Scanner;
import java.util.ArrayList;

/**
 * ゲームの進行を担うクラス
 */
public class Facilitator {

    /**
     * ラウンドを保存するフィールド
     * 初期値はコンストラクタによって1に設定される
     */
    private int round;

    /**
     * プレイヤーとボットのインスタンスをフィールドに保存するためのリスト
     */
    ArrayList<Character> c = new ArrayList<Character>();

    /**
     * カードを配るメソッド
     * 
     * @param r 0~4の中からランダムに決まるカードの番号
     * @return ランダムに選ばれた5枚のカードの組
     */
    public ArrayList<Integer> distribute(Random r) {
        int cardNumber;
        ArrayList<Integer> distributeCards = new ArrayList<Integer>();
        for (int i = 0; i < 5; i++) {
            cardNumber = r.nextInt(5);
            distributeCards.add(cardNumber);
        }
        return distributeCards;
    }

    /**
     * ラウンドを1に初期化後、プレイヤーとボットのインスタンスを作り、distribute(Random r)でそれぞれにカードを配るコンストラクタ
     */
    public Facilitator() {
        this.round = 1;
        Random bCard = new Random();
        ArrayList<Integer> bHand = this.distribute(bCard);
        Character b = new Character(bHand);
        Random pCard = new Random();
        ArrayList<Integer> cHand = this.distribute(pCard);
        Character p = new Character(cHand);
        c.add(p);
        c.add(b);
    }

    /**
     * ゲームの進行を行うメソッド 基本的な出力とメソッド実行を担う
     */
    public void progress() {
        var p = c.get(0);
        var b = c.get(1);
        if (this.round >= 2) {
            Random bCard = new Random();
            ArrayList<Integer> bHand = this.distribute(bCard);
            b.setHand(bHand);
            Random pCard = new Random();
            ArrayList<Integer> cHand = this.distribute(pCard);
            p.setHand(cHand);
        }
        System.out.println("現在のポイントは" + p.getPoint() + ":" + b.getPoint() + "です。");
        if (this.round == 1) {
            System.out.println("3点先取で勝利です。");
        }
        System.out.println("あなたの手札は" + p.getHand() + "です");
        if (this.round == 1) {
            System.out.println("自分の手札から1枚だけ捨ててランダムな1枚と交換することができます。(捨てたくない場合はそのままEnter)");
        }
        System.out.println("捨てたいカードの数字を入力してください。");
        Scanner scanner = new Scanner(System.in);
        int num = scanner.nextInt();
        discard(p, num);
        addCard(p);
        System.out.println("ボットの手札は" + b.getHand() + "です");
        judge(p, b);
        int finish = isFinished();
        if (finish == 0 && finish == 1) {
            scanner.close();
        }
        this.round += 1;
    }

    /**
     * カードを捨てるメソッド
     * 入力した番号がプレイヤーの手札に存在する場合、1つだけ該当カードを削除する
     * 
     * @param p プレイヤーのインスタンス
     * @param cardNumber プレイヤーが入力したカード番号
     */
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
    }

    /**
     * カードを捨てた後、手札に1枚だけランダムでカードを追加するメソッド
     * @param p プレイヤーのインスタンス
     */
    public void addCard(Character p) {
        Random r = new Random();
        int getCard = r.nextInt(5);
        p.hand.add(getCard);
        System.out.println(getCard + "のカードを手に入れました。\nあなたの手札は" + p.getHand() + "です");
    }

    /**
     * プレイヤーとボットの手札のカード番号の合計を計算し、高い方のポイントを1プラスするメソッド
     * 引き分けだった場合はどちらのポイントも変化しない
     * @param p　プレイヤーのインスタンス
     * @param b　ボットのインスタンス
     */
    public void judge(Character p, Character b) {
        ArrayList<Integer> playerHand = new ArrayList<Integer>();
        playerHand = p.getHand();
        int playerPoint = 0;
        for (int i = 0; i < playerHand.size(); i++) {
            int j = playerHand.get(i);
            playerPoint += j;
        }
        ArrayList<Integer> botHand = new ArrayList<Integer>();
        botHand = b.getHand();
        int botPoint = 0;
        for (int i = 0; i < botHand.size(); i++) {
            int j = botHand.get(i);
            botPoint += j;
        }

        System.out.println("あなたの手札の合計は" + playerPoint + "です。");
        System.out.println("ボットの手札の合計は" + botPoint + "です。");

        if (playerPoint > botPoint) {
            p.setPoint();
            System.out.println("あなたの勝ちです！");
        } else if (playerPoint < botPoint) {
            b.setPoint();
            System.out.println("あなたの負けです…");
        } else {
            System.out.println("引き分けです。");
        }
    }

    /**
     * ゲームの状態を「勝利」「敗北」「継続中」の3つに分け、それぞれの状態をint型で返すメソッド
     * 「勝利」= 2、「敗北」= 1、「継続中」= 2 で表す
     * @return ゲームの状態
     */
    public int isFinished() {
        var p = c.get(0);
        var b = c.get(1);
        final int MATCHPOINT = 3;
        if (p.getPoint() == MATCHPOINT) {
            return 0;
        } else if (b.getPoint() == MATCHPOINT) {
            return 1;
        } else {
            return 2;
        }
    }
}