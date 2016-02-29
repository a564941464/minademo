package com.simply.game.threechess.bean;

import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class Constants {
    public final static String ROOM = "ROOM"; //房间
    public final static String ENTER_DESK = "ENTER_DESK"; //进入桌子
    public final static String ENTER_DESK_OK = "ENTER_DESK_OK"; //进入桌子 ok
    public final static String ENTER_DESK_FAIL = "ENTER_DESK_FAIL"; //进入桌子 fail
    public final static String DESK = "DESK"; //桌子
    public final static String START = "START";  // 开始
    public final static String RESTART = "RESTART";  // 重新开始
    public final static String DOGFALL = "DOGFALL";   //求和
    public final static String SURRENDER = "SURRENDER";   //认输
    public final static String RETRACT = "RETRACT";   // 悔棋
    public final static String USER_INFO = "USER_INFO";    //  用户信息
    public final static String CHAT_MSG = "CHAT_MSG";      //聊天内容
    public final static String CHAT_MSG_TO_ALL = "CHAT_MSG_TO_ALL";      //聊天内容
    public final static String LIMIT_TIME = "LIMIT_TIME"; // 总限时
    public final static String COUNTDOWN = "COUNTDOWN";   //倒计时
    public final static String PUT = "PUT";       //落子
    public final static String GET = "GET";       //取子
    public final static String SELECT = "SELECT"; //选子
    public final static String GO = "GO";   //走棋 from_id to_id
    
    public final static String TO_GET = "TO_GET";   //准备取子
    public final static String TO_SELECT = "TO_SELECT";   //准备选子
    public final static String TO_PUT = "TO_PUT";   //准备落子
    
    public final static String START_AWAIT = "START_AWAIT"; // 等待开始
    public final static String START_OK = "START_OK";  //准备好了， 可以开始
    public final static String DOGFALL_REJECT = "DOGFALL_REJECT";  //拒绝求和
    public final static String DOGFALL_OK = "DOGFALL_OK";     //同意求和
    public final static String RETRACT_REJECT = "RETRACT_REJECT";   //拒绝悔棋
    public final static String RETRACT_OK = "RETRACT_OK";  //同意悔棋
    public final static String STATUS = "STATUS";  // 状态 fail success
    public final static String TURN = "TURN";  // 顺序

    public final static String SUCCESS = "SUCCESS"; //成功
    public final static String FAIL = "FAIL"; //失败
    public final static String ING = "ING"; //进行中
    public final static String QUIT = "QUIT"; //退出
    public final static String OFFLINE = "OFFLINE"; //断线
    public final static String QUIT_DESK = "QUIT_DESK"; //退出桌台
    public final static String UPDATE_DESK = "UPDATE_DESK"; //刷新桌台状态
    
    
    //游戏状态
    public final static String UNSTARTED = "UNSTARTED"; //未开始
    public final static String PUTTING = "PUTTING"; //落子
    public final static String GETTING = "GETTING"; //取子
    public final static String SELECTTING = "SELECTTING"; //选子
    public final static String GOING = "GOING"; //走子
    
    public final static String WHITE_TURN = "WHITE_TURN";
    public final static String BLACK_TURN = "BLACK_TURN";
    public final static String EMPTY_TURN = "EMPTY_TURN";   
    
    public final static Map<String,String> turn2str;
    static {
        turn2str = new HashMap<String, String>();
        turn2str.put(WHITE_TURN,"白棋取子");
        turn2str.put(BLACK_TURN,"黑棋取子");    
    }

}
