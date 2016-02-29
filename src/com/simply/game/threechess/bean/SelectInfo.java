/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simply.game.threechess.bean;

/**
 *
 * @author who
 */
public class SelectInfo extends Info{
    private int s_desk_num;
    private int s_select_id;
    private String s_cur_turn;

    public String getS_cur_turn() {
        return s_cur_turn;
    }

    public void setS_cur_turn(String s_cur_turn) {
        this.s_cur_turn = s_cur_turn;
    }

    public int getS_desk_num() {
        return s_desk_num;
    }

    public void setS_desk_num(int s_desk_num) {
        this.s_desk_num = s_desk_num;
    }

    public int getS_select_id() {
        return s_select_id;
    }

    public void setS_select_id(int s_select_id) {
        this.s_select_id = s_select_id;
    }   
}
