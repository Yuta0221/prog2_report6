package jp.ac.uryukyu.ie.e205757;

import java.util.Random;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.InputMismatchException;

/**
 * ゲームの進行を担うクラス
 */
public class Facilitator {

    /**
     * ラウンドを保存するフィールド 初期値はコンストラクタによって1に設定される
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
            System.out.println("自分の手札から1枚だけ捨ててランダムな1枚と交換することができます。");
        }

        Scanner scanner1 = new Scanner(System.in);
        Scanner scanner2 = new Scanner(System.in);
        scan(scanner1, scanner2, p);

        System.out.println("ボットの手札は" + b.getHand() + "です");
        judge(p, b);
        int finish = isFinished();
        if (finish == 0 && finish == 1) {
            scanner1.close();
            scanner2.close();
        }
        this.round += 1;
    }

    /**
     * カード交換を行うメソッド
     * カードを捨てる場合はy、捨てない場合はnを入力することでカード交換をするかどうかの選択ができる
     * カードを捨てる場合、カードの数字を入力することでそのカードを手札から捨てる
     * 手札に存在しない数字、または数字以外の入力がされた場合はカード交換が行われない
     * 
     * @param scanner1 カードを捨てるかどうかを選択した際の入力内容を保存する
     * @param scanner2 入力された捨てるカードの番号を保存する
     * @param p        プレイヤーのインスタンス
     */
    public void scan(Scanner scanner1, Scanner scanner2, Character p) {
        System.out.printf("カードを捨てますか？捨てる場合はy、捨てない場合はnを押してください。:");
        String answer = scanner1.nextLine();
        Integer num;
        if (answer.equals("y")) {
            System.out.printf("捨てたいカードの数字を入力してください。:");
            try {
                num = scanner2.nextInt();
                discard(p, num);
                addCard(p);
            } catch (InputMismatchException i) {
                System.out.println("入力が正しくありません。カードは変更されませんでした。");
            }
        } else if (answer.equals("n")) {
            System.out.println("カードを捨てませんでした。");
        } else {
            System.out.println("入力が正しくありません。カードは変更されませんでした。");
        }
    }

    /**
     * カードを捨てるメソッド 入力した番号がプレイヤーの手札に存在する場合、1つだけ該当カードを削除する
     * 手札に入力した番号のカードが存在しない場合、カードを捨てずにゲームを進める
     * @param p          プレイヤーのインスタンス
     * @param cardNumber プレイヤーが入力したカード番号
     */
    public void discard(Character p, int cardNumber) {
        boolean existNumber = false;
        ArrayList<Integer> playerHand = new ArrayList<Integer>();
        playerHand = p.getHand();
        for (int i = 0; i < playerHand.size(); i++) {
            int j = playerHand.get(i);
            if (cardNumber == j) {
                playerHand.remove(i);
                System.out.println(cardNumber + "のカードを捨てました。");
                existNumber = true;
                break;
            }
        }
        if (existNumber == false) {
            System.out.println("該当のカードがありません。");
        }
    }

    /**
     * カードを捨てた後、手札に1枚だけランダムでカードを追加するメソッド
     * カードを捨てなかった場合、カードは追加されない
     * @param p プレイヤーのインスタンス
     */
    public void addCard(Character p) {
        ArrayList<Integer> playerHand = new ArrayList<Integer>();
        playerHand = p.getHand();
        if (playerHand.size() < 5) {
            Random r = new Random();
            int getCard = r.nextInt(5);
            p.hand.add(getCard);
            System.out.println(getCard + "のカードを手に入れました。\nあなたの手札は" + p.getHand() + "です");
        }
    }

    /**
     * プレイヤーとボットの手札のカード番号の合計を計算し、高い方のポイントを1プラスするメソッド 引き分けだった場合はどちらのポイントも変化しない
     * 
     * @param p プレイヤーのインスタンス
     * @param b ボットのインスタンス
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
     * ゲームの状態を「勝利」「敗北」「継続中」の3つに分け、それぞれの状態をint型で返すメソッド 「勝利」= 2、「敗北」= 1、「継続中」= 2 で表す
     * 
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