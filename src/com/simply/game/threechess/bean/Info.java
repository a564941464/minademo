package com.simply.game.threechess.bean;


import com.simply.game.threechess.server.Config;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static com.simply.game.threechess.bean.Constants.*;
import com.simply.game.threechess.client.GameLogic;

public class Info implements Serializable{
    private String action;  //动作
    private Integer desk_num; //桌台号
    private final static List<Desk> static_desks; //桌子列表
    private static List<Desk> desks; //桌子列表
    private String chat_msg; //聊天内容
    private String limit_time; //总限时
    private String countdown;  //倒计时
    private int id; //点击坐标
    private User user; //用户进入桌子时的用户信息
    private String turnicon; //棋手图标
    private String notice_msg; // 提示信息,比如进入桌台失败提示
    private String turn; //棋手
    private String cur_turn; //当前棋手
    private String status_msg; //状态信息， 成功，失败， ING 进行中。
    private User duiShou; //对手
    private Integer desk_position; //桌位号， 桌子上的哪个位置    索引从0开始
    private String deskinfo; //桌台信息
    private GameLogic gamelogic;
    
    private int from_id; //走棋起始id
    private int to_id; // 走棋结果id
    
    public String toString(){
        return "action:"+action+"   desk_num:"+desk_num+"    id:"+id+"turn:"+turn+"    cur_turn:"+cur_turn+"     status_msg:"+status_msg+"     from_id:"+from_id+"     to_id:"+to_id;
    }
    static {
        static_desks = new ArrayList<Desk>();
        for(int i=0; i< Config.getDeskAmount(); i++){
            Desk d = new Desk();
            d.setDesk_num(i);
            d.getTurns().add(WHITE_TURN);
            d.getTurns().add(BLACK_TURN);            
            static_desks.add(d);
        }
    }
    {
        desks = new ArrayList<Desk>();
        for(Desk d : static_desks){
            desks.add(d);
        }
    }
    public Integer getDesk_num() {
        return desk_num;
    }

    public String getTurnicon() {
        return turnicon;
    }

    public GameLogic getGamelogic() {
        return gamelogic;
    }

    public void setGamelogic(GameLogic gamelogic) {
        this.gamelogic = gamelogic;
    }

    public void setTurnicon(String turnicon) {
        this.turnicon = turnicon;
    }

    public String getDeskinfo() {
        return deskinfo;
    }

    public void setDeskinfo(String deskinfo) {
        this.deskinfo = deskinfo;
    }

    public String getCur_turn() {
        return cur_turn;
    }

    public void setCur_turn(String cur_turn) {
        this.cur_turn = cur_turn;
    }
    public void setDesk_num(Integer desk_num) {
        this.desk_num = desk_num;
    }

    public List<Desk> getDesks() {
        return desks;
    }

    public void setDesks(List<Desk> desks) {
        Info.desks = desks;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public static List<Desk> getStatic_desks() {
        return static_desks;
    }

    public String getChat_msg() {
        return chat_msg;
    }

    public void setChat_msg(String chat_msg) {
        this.chat_msg = chat_msg;
    }

    public String getLimit_time() {
        return limit_time;
    }

    public void setLimit_time(String limit_time) {
        this.limit_time = limit_time;
    }

    public String getCountdown() {
        return countdown;
    }

    public void setCountdown(String countdown) {
        this.countdown = countdown;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTurn() {
        return turn;
    }

    public void setTurn(String turn) {
        this.turn = turn;
    }

    public String getStatus_msg() {
        return status_msg;
    }

    public void setStatus_msg(String status_msg) {
        this.status_msg = status_msg;
    }

    public Integer getDesk_position() {
        return desk_position;
    }

    public void setDesk_position(Integer desk_position) {
        this.desk_position = desk_position;
    }

    public User getUser() {
        return user;
    }

    public String getNotice_msg() {
        return notice_msg;
    }

    public void setNotice_msg(String notice_msg) {
        this.notice_msg = notice_msg;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getDuiShou() {
        return duiShou;
    }

    public int getFrom_id() {
        return from_id;
    }

    public void setFrom_id(int from_id) {
        this.from_id = from_id;
    }

    public int getTo_id() {
        return to_id;
    }

    public void setTo_id(int to_id) {
        this.to_id = to_id;
    }

    public void setDuiShou(User user) {
        this.duiShou = user;
    }
}
