/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simply.game.threechess.client;

import static com.simply.game.threechess.bean.Constants.*;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author who
 */
public class GameLogic implements Serializable {

    public GameLogic() {
    }
    private String cur_turn = BLACK_TURN;
    private String pre_turn;
    private int totalcount = 4;
    private int whitecount = 2;
    private int blackcount = 2;
    public static final int white = 1;
    public static final int black = 2;
    public static final int empty = 0;
    private boolean isChenglian = false;
    private String winner;
    private int id;
    public static final Map<String, Integer> str2int = new HashMap<String, Integer>();

    static {
        str2int.put(WHITE_TURN, 1);
        str2int.put(BLACK_TURN, 2);
        str2int.put(EMPTY_TURN, 0);

    }

    public int go(int from_id, int to_id){
        System.out.println("go: from:"+ from_id + " to_id:" + to_id  );
        if(this.map[to_id] != 0 && this.map[from_id] != 0 && this.map[from_id] ==  this.map[to_id]){
            return 1;//选棋
        }
                
        int x = from_id/10;
        int y = from_id%10;

        int x1 = to_id/10;
        int y1 = to_id%10;
        
        int distanse = (y1 - y) * (y1 - y) + (x1 - x) * (x1 - x);
        
        if(this.map[from_id] == 0 || this.map[to_id] != 0) return 2;//非法操作
        
        if(distanse == 1 || distanse == 49 ){
            if((y == 0&&y1==0) || (y == 2&&y1==2) || (y ==4&&y1==4) || (y ==6&&y1==6)){
                return 2;//排除斜线 
            }
            this.map[from_id] = GameLogic.empty;
            this.map[to_id] = str2int.get(this.cur_turn);
            this.isChenglian = this.checkChenglian(to_id);
            if(this.isChenglian){//成连不换操作棋手
                
            }else{
                if (this.cur_turn.equals(WHITE_TURN)) {//交换棋手
                    this.cur_turn = BLACK_TURN;
                    this.pre_turn = WHITE_TURN;
                } else {
                    this.cur_turn = WHITE_TURN;
                    this.pre_turn = BLACK_TURN;
                }  
            }            
            return 0;
        }
        return 2;
    }

    public boolean isGoing(){
        //System.out.println("this.totalcount:"+this.totalcount);
        return this.totalcount <= 0;
    }
    /**
     *
     * @param turn WHITE_TURN | BLACK_TURN
     * @param id
     */
    public void put(String turn, int id) {
        if(this.totalcount <= 0){
            return;
        }
        if (this.map[id] == 0) {
            this.map[id] = this.str2int.get(turn);
            this.totalcount--;
            this.isChenglian = this.checkChenglian(id);
//            System.out.println("this.isChenglian"+this.isChenglian);
//            System.out.println("cur_turn:"+turn);
            this.id = id;
            if(this.isChenglian){//成连不换操作棋手
                
            }else{
                if (turn.equals(WHITE_TURN)) {//交换棋手
                    this.cur_turn = BLACK_TURN;
                    this.pre_turn = WHITE_TURN;
                } else {
                    this.cur_turn = WHITE_TURN;
                    this.pre_turn = BLACK_TURN;
                }  
            }
            
        }
    }
    /**
     *
     * @param turn WHITE_TURN | BLACK_TURN
     * @param id
     * @return int 0 可取且已取，1在连中不可取， 2取子位置不对
     */ 
    public int get(String turn, int id) {
        int clicked_turn = this.str2int.get(turn);
        if (this.map[id] != 0  && clicked_turn != this.map[id]) {
            boolean can_get = true;
            for(int[] ok: okk){
                if(ok[0] == id || ok[1] == id || ok[2] == id){
                    if(map[ok[0]] == clicked_turn && map[ok[1]] == clicked_turn && map[ok[2]] == clicked_turn ){
                        can_get = false;
                    }
                }
            } 
           if(can_get){
                this.map[id] = empty;  
                this.id = id;
                if (turn.equals(WHITE_TURN)) {
                    this.blackcount--;
                    this.cur_turn = BLACK_TURN;
                    this.pre_turn = WHITE_TURN;
                } else {
                    this.whitecount--;
                    this.cur_turn = WHITE_TURN;
                    this.pre_turn = BLACK_TURN;
                }
                this.isChenglian = false;
                return 0;
                
            }
            return 1;
        }
        return 2;
    }
        /**
     * 检查是否成连
     *
     * @return
     */
    public boolean checkChenglian(int id) {
        for (int[] ii : this.okk) {
            if(ii[0] == id || ii[1] == id || ii[2] == id){//判断是否当前位置
                if (this.map[ii[0]] != empty && this.map[ii[0]] == this.str2int.get(this.cur_turn)) {//判断当前位置不为空 且为当前棋手
                    if (this.map[ii[0]] == this.map[ii[1]] && this.map[ii[1]] == this.map[ii[2]]) {
                        return true;
                    }
                }
            }            
        }
        return false;
    }

    /**
     * 检查是否
     *
     * @return
     */
    private String checkWin() {
        if (this.blackcount <= 2) {
            return WHITE_TURN;
        } else if (this.whitecount <= 2) {
            return BLACK_TURN;
        }
        return null;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTotalcount() {
        return totalcount;
    }

    public void setTotalcount(int totalcount) {
        this.totalcount = totalcount;
    }

    public int getWhitecount() {
        return whitecount;
    }

    public String getPre_turn() {
        return pre_turn;
    }

    public void setPre_turn(String pre_turn) {
        this.pre_turn = pre_turn;
    }
    /**
     * 获取当前的玩家
     *
     * @return
     */
    public String getCur_turn() {
        return this.cur_turn;
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }

    public int getBlackcount() {
        return blackcount;
    }

    public void setBlackcount(int blackcount) {
        this.blackcount = blackcount;
    }

    public boolean isIsChenglian() {
        return isChenglian;
    }

    public void setIsChenglian(boolean isChenglian) {
        this.isChenglian = isChenglian;
    }

    public int[] getMap() {
        return map;
    }

    public void setMap(int[] map) {
        this.map = map;
    }

    public int[][] getOkk() {
        return okk;
    }

    public void setOkk(int[][] okk) {
        this.okk = okk;
    }
    public void setWhitecount(int whitecount) {
        this.whitecount = whitecount;
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
    }
    private int[] map = new int[38];
//    private int[][] okk = { //有取子的情况
//        {0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {9, 10, 11}, {12, 13, 14}, {15, 16, 17}, {18, 19, 20}, {21, 22, 23},
//        {0, 9, 21}, {3, 10, 18}, {6, 11, 15}, {1, 4, 7}, {16, 19, 22}, {8, 12, 17}, {5, 13, 20}, {2, 14, 23}
//    };
    private int[][] okk = { //有取子的情况
        {10, 11, 12}, {20, 21, 22}, {30, 31, 32}, {17, 27, 37}, {33, 23, 13}, {36, 35, 34}, {26, 25, 24}, {16, 15, 14},
        {10, 17, 16}, {20, 27, 26}, {30, 37, 36}, {11, 21, 31}, {35, 25, 22}, {32, 33, 34}, {22, 23, 24}, {12, 13, 14}
    };
}
