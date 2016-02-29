/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simply.game.threechess.bean;

import com.simply.game.threechess.client.GameLogic;

/**
 *
 * @author who
 */
public class GoInfo extends Info{
    private int g_desk_num;
    private int g_from_id;
    private int g_to_id;
    private GameLogic g_gamelogic;
    private String g_cur_turn;

    public int getG_desk_num() {
        return g_desk_num;
    }

    public void setG_desk_num(int g_desk_num) {
        this.g_desk_num = g_desk_num;
    }

    public int getG_from_id() {
        return g_from_id;
    }

    public void setG_from_id(int g_from_id) {
        this.g_from_id = g_from_id;
    }

    public GameLogic getG_gamelogic() {
        return g_gamelogic;
    }

    public void setG_gamelogic(GameLogic g_gamelogic) {
        this.g_gamelogic = g_gamelogic;
    }

    public int getG_to_id() {
        return g_to_id;
    }

    public void setG_to_id(int g_to_id) {
        this.g_to_id = g_to_id;
    }

    public String getG_cur_turn() {
        return g_cur_turn;
    }

    public void setG_cur_turn(String g_cur_turn) {
        this.g_cur_turn = g_cur_turn;
    }
    
    
}
