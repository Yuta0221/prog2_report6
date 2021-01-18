package jp.ac.uryukyu.ie.e205757;
import java.util.ArrayList;

public class Character {
    ArrayList<Integer> hand = new ArrayList<Integer>();
    private int point;

    Character(ArrayList<Integer> cards){
        this.hand = cards;
        this.point = 0;
    }

    public ArrayList<Integer> getHand(){
        return this.hand;
    }

    public int getPoint(){
        return this.point;
    }

    public void setPoint(int point){
        this.point = point;
    }

}