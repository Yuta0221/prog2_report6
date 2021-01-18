package jp.ac.uryukyu.ie.e205757;

/**
 * Mainクラス
 * Facilitatorクラスのインスタンスを生成し、ゲームを行う
 */
public class Main {
    /**
     * Mainメソッド
     * ゲームをループさせ、勝敗がついた時にループを終了させる
     * @param args
     */
    public static void main(String[] args) {
        Facilitator f = new Facilitator();
        while (true) {
            if (f.isFinished() == 0) {
                System.out.println(f.c.get(0).getPoint() + ":" + f.c.get(1).getPoint() + " であなたの勝利です！");
                break;
            } else if (f.isFinished() == 1) {
                System.out.println(f.c.get(0).getPoint() + ":" + f.c.get(1).getPoint() + " でボットの勝利です…");
                break;
            } else {
                f.progress();
            }
        }
    }
}