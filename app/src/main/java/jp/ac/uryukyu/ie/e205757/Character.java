package jp.ac.uryukyu.ie.e205757;
import java.util.ArrayList;

/**
 * プレイヤーとボットの雛形
 */
public class Character {
    /**
     * 手札のリスト
     * インスタンス生成時に5枚のカード番号が保存される
     */
    ArrayList<Integer> hand = new ArrayList<Integer>();

    /**
     * 手持ちポイント
     * 勝った時に1ポイントプラスされる
     */
    private int point = 0;

    /**
     * Facilitatorコンストラクタ内で実行されるコンストラクタ
     * 手札とポイントの初期値0が設定される
     * @param cards distributeメソッドで得られた手札
     */
    Character(ArrayList<Integer> cards){
        this.hand = cards;
        this.point = 0;
    }

    /**
     * getterメソッド
     * @return 手札のリスト
     */
    public ArrayList<Integer> getHand(){
        return this.hand;
    }

    /**
     * getterメソッド
     * @return 手持ちポイント
     */
    public int getPoint(){
        return this.point;
    }

    /**
     * setterメソッド
     * 勝った時に実行され、ポイントが1プラスされる
     */
    public void setPoint(){
        this.point += 1;
    }

    /**
     * setterメソッド
     * ラウンドが変わるごとに手札をリセットする
     * @param hand
     */
    public void setHand(ArrayList<Integer> hand){
        this.hand = hand;
    }
}