package com.simply.game.threechess.server;

import static com.simply.game.threechess.bean.Constants.*;
import com.simply.game.threechess.bean.Desk;
import com.simply.game.threechess.bean.GoInfo;
import com.simply.game.threechess.bean.Info;
import com.simply.game.threechess.bean.SelectInfo;
import com.simply.game.threechess.client.GameLogic;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;
import org.apache.log4j.Logger;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

public class SmainHandler extends IoHandlerAdapter {

    public static Logger logger = Logger.getLogger(SmainHandler.class);
    private final Set<IoSession> sessions = Collections.synchronizedSet(new LinkedHashSet<IoSession>());

    @Override
    public void sessionCreated(IoSession session) throws Exception {
        logger.info("服务端与客户端创建连接...");
    }

    @Override
    public void sessionOpened(IoSession session) throws Exception {
        logger.info("服务端与客户端连接打开...");
    }

    @Override
    public void messageReceived(IoSession session, Object message)
            throws Exception {

        sessions.add(session);//所有连接，用于广播

        Info req = (Info) message;
        logger.info(req.getAction());
        logger.info("=================================================================");
        logger.info("\nreq:"+req);
        logger.info("=================================================================");
        Info res = new Info();
        if (req.getAction().equals(ROOM)) {  //进入房间，查看桌子列表
            res.setAction(ROOM);
            session.write(res);
        } else if (req.getAction().equals(DESK)) {    //查看桌台信息
            res.setAction(DESK);
            res.setDesk_num(req.getDesk_num());
            res.setDesk_position(req.getDesk_position());
            res.setDeskinfo(Info.getStatic_desks().get(req.getDesk_num()).getUsers()[req.getDesk_position()] == null ? "空" : "占");

            session.write(res);
        } else if (req.getAction().equals(ENTER_DESK)) {    //进入桌子
            Desk desk = Info.getStatic_desks().get(req.getDesk_num());
            synchronized (sessions) {
                if ((Boolean) session.getAttribute("ENTER_DESK", false)) {
                    res.setAction(ENTER_DESK_FAIL);
                    res.setNotice_msg("请先退出对战桌");
                    session.write(res);
                } else if (desk.getStatus() < Desk.full && desk.getUsers()[req.getDesk_position()] == null) { //进入游戏界面 ，初始化用户及对手相关信息
                    session.setAttribute("ENTER_DESK", true);
                    res.setAction(ENTER_DESK_OK);
                    res.setDesk_num(req.getDesk_num());
                    res.setDesk_position(req.getDesk_position());
                    desk.setStatus(desk.getStatus() + 1);
                    res.setUser(req.getUser());
                    res.setDuiShou(desk.getUsers()[req.getDesk_position() == 0 ? 1 : 0]);
                    res.setDesk_num(req.getDesk_num());
                    desk.setDesk_num(req.getDesk_num());

                    String turnicon = desk.getTurns().pop();
                    System.out.println("turnicon:" + turnicon);
                    session.setAttribute("turnicon", turnicon);
                    res.setTurnicon(turnicon);

                    desk.getSessions().add(session);
                    desk.getUsers()[req.getDesk_position()] = req.getUser();
                    desk.getSession2user().put(session, req.getUser());
                    session.write(res);

                    res.setAction(UPDATE_DESK);  //刷新桌台状态，提醒已入座玩家更新对手信息
                    this.broadcast(res, this.sessions);

                    res.setAction(CHAT_MSG);
                    res.setChat_msg(req.getUser().getName() + "进来了");
                    this.broadcast(res, desk.getSessions());
                } else {
                    res.setAction(ENTER_DESK_FAIL);
                    res.setNotice_msg("位置被占");
                    session.write(res);
                }
            }
        } else if (req.getAction().equals(START)) {
            Desk desk = Info.getStatic_desks().get(req.getDesk_num());
            synchronized (sessions) {
                desk.setGame_status(desk.getGame_status() + 1);
                //System.out.println(desk.getGame_status());
                if (desk.getGame_status().equals(Desk.full)) {
                    Set<IoSession> two_sessions = desk.getSessions();
                    res.setAction(START_OK);

                    GameLogic gamelogic = new GameLogic();
                    for (IoSession s : two_sessions) {
                        s.setAttribute("gamelogic", gamelogic);
                    }
                    res.setGamelogic(gamelogic);
                    this.broadcast(res, two_sessions);
                } else {
                    res.setAction(START_AWAIT);
                    session.write(res);
                }
            }
        } else if (req.getAction().equals(DOGFALL)) {    //求和
            Desk desk = Info.getStatic_desks().get(req.getDesk_num());
            res.setAction(DOGFALL);

            this.broadcast_to_others(res, session, desk.getSessions());
        } else if (req.getAction().equals(DOGFALL_REJECT)) {  //拒绝求和
            Desk desk = Info.getStatic_desks().get(req.getDesk_num());
            res.setAction(DOGFALL_REJECT);
            res.setUser(req.getUser());
            this.broadcast_to_others(res, session, desk.getSessions());
        } else if (req.getAction().equals(DOGFALL_OK)) {    //求和成功
            Desk desk = Info.getStatic_desks().get(req.getDesk_num());
            res.setAction(DOGFALL_OK);
            res.setUser(req.getUser());
            this.broadcast_to_others(res, session, desk.getSessions());
        } else if (req.getAction().equals(SURRENDER)) {         //认输
            Desk desk = Info.getStatic_desks().get(req.getDesk_num());
            res.setAction(SURRENDER);
            res.setUser(req.getUser());
            this.broadcast_to_others(res, session, desk.getSessions());
        } else if (req.getAction().equals(RETRACT)) {  //悔棋
            Desk desk = Info.getStatic_desks().get(req.getDesk_num());
            res.setAction(RETRACT);
            res.setUser(req.getUser());
            this.broadcast_to_others(res, session, desk.getSessions());
        } else if (req.getAction().equals(RETRACT_REJECT)) {  //拒绝悔棋
            Desk desk = Info.getStatic_desks().get(req.getDesk_num());
            res.setAction(RETRACT_REJECT);
            res.setUser(req.getUser());
            this.broadcast_to_others(res, session, desk.getSessions());
        } else if (req.getAction().equals(RETRACT_OK)) {  //同意悔棋
            Desk desk = Info.getStatic_desks().get(req.getDesk_num());
            res.setAction(RETRACT_OK);
            res.setUser(req.getUser());
            this.broadcast_to_others(res, session, desk.getSessions());
        } else if (req.getAction().equals(CHAT_MSG_TO_ALL)) { //聊天信息
            res.setAction(CHAT_MSG_TO_ALL);
            res.setChat_msg(req.getChat_msg());
            this.broadcast(res, this.sessions); //这里用于发送成功才响应到双方的客户端
        } else if (req.getAction().equals(CHAT_MSG)) { //聊天信息
            Desk desk = Info.getStatic_desks().get(req.getDesk_num());
            res.setAction(CHAT_MSG);
            res.setChat_msg(req.getChat_msg());
            this.broadcast(res, desk.getSessions()); //这里用于发送成功才响应到双方的客户端
        } else if (req.getAction().equals(PUT)) {  //落子
            synchronized (sessions) {
                Desk desk = Info.getStatic_desks().get(req.getDesk_num());
                GameLogic gamelogic = (GameLogic) (session.getAttribute("gamelogic"));
//                System.out.println("gamelogic"+ gamelogic);
                gamelogic.put(req.getCur_turn(), req.getId());

                if (gamelogic.isIsChenglian()) {
                    res.setAction(TO_GET);//准备取子
                } else{
                    res.setAction(PUT);//落子
                }
                res.setGamelogic(gamelogic);
                this.broadcast(res, desk.getSessions()); //这里用于发送成功才响应到双方的客户端
            }
        } else if (req.getAction().equals(GET)) {  //取子
            synchronized (sessions) {
                Desk desk = Info.getStatic_desks().get(req.getDesk_num());
                GameLogic gamelogic = (GameLogic) (session.getAttribute("gamelogic"));
                int result = gamelogic.get(req.getCur_turn(), req.getId());
                if (result == 0) {
                    //////////////////////////////////////////////////////////////////////////////////////
                    //这里还缺代码逻辑
                    res.setAction(GET);//取子  
                    res.setGamelogic(gamelogic);
                    this.broadcast(res, desk.getSessions()); //这里用于发送成功才响应到双方的客户端
                }
                
            }
        } else if (req.getAction().equals(SELECT)) { //选子
            SelectInfo req_select = (SelectInfo)message;
            Desk desk = Info.getStatic_desks().get(req_select.getS_desk_num());
            GameLogic gamelogic = (GameLogic) (session.getAttribute("gamelogic"));
            
            SelectInfo res_select = new SelectInfo();
            if(gamelogic.getMap()[req_select.getS_select_id()]!=0){
                res_select.setAction(SELECT);
                res_select.setS_select_id(req_select.getS_select_id());
                res_select.setS_cur_turn(req_select.getS_cur_turn());                
                res_select.setS_desk_num(req_select.getS_desk_num());
                session.write(res_select);   
                
                this.broadcast(res_select, desk.getSessions()); //这里用于发送成功才响应到双方的客户端
            }

        } else if (req.getAction().equals(GO)) { // 走棋
            synchronized (sessions) {
                GoInfo g_req = (GoInfo)message;
                Desk desk = Info.getStatic_desks().get(g_req.getG_desk_num());
                GameLogic gamelogic = (GameLogic) (session.getAttribute("gamelogic"));
                int result = gamelogic.go(g_req.getG_from_id(), g_req.getG_to_id());
//                System.out.println("GO action ::: from_id:"+ req.getFrom_id() + ",to_id:"+ req.getTo_id()+",result:"+result);
                if (result==0) {
                    GoInfo g_res = new GoInfo();
                    res.setFrom_id(req.getFrom_id());
                    res.setTo_id(req.getTo_id());
                    res.setGamelogic(gamelogic);
                    res.setDesk_num(req.getDesk_num());
                    
                    res.setAction(GO);//走棋     
                    res.setGamelogic(gamelogic);
                    
                    this.broadcast(res, desk.getSessions()); //这里用于发送成功才响应到双方的客户端
                }else if(result==1) {
                    res.setAction(SELECT);//选子                            
                    res.setId(req.getTo_id());
                    res.setCur_turn(req.getCur_turn());
                    res.setDesk_num(req.getDesk_num());
                    
//                    SelectInfo res_select = new SelectInfo();
//
//                    res_select.setAction(SELECT);
//                    res_select.setS_select_id(req_select.getS_select_id());
//                    res_select.setS_cur_turn(req_select.getS_cur_turn());                
//                    res_select.setS_desk_num(req_select.getS_desk_num());
//                    
                    this.broadcast(res, desk.getSessions()); //这里用于发送成功才响应到双方的客户端
                }                  
            }

        } else if (req.getAction().equals(QUIT_DESK)) { //退出桌台
            synchronized (sessions) {
                res.setAction(QUIT_DESK);
                res.setDesk_num(req.getDesk_num());
                res.setDesk_position(req.getDesk_position());
                session.setAttribute("ENTER_DESK", false);
                Desk desk = Info.getStatic_desks().get(req.getDesk_num());
                desk.setStatus(desk.getStatus() - 1);
                desk.setGame_status(desk.getGame_status() - 1);
                desk.getUsers()[req.getDesk_position()] = null;
                desk.getTurns().add((String) (session.getAttribute("turnicon")));
                desk.getSessions().remove(session);
                this.broadcast(res, this.sessions); //这里用于发送成功才响应到客户端
            }
        }
    }

    public void broadcast_to_others(Info res, IoSession session, Set<IoSession> sessions) throws Exception {
        synchronized (sessions) {
            for (IoSession s : sessions) {
                if (s.isConnected() && s.getId() != session.getId()) {
                    s.write(res);
                }
            }
        }
    }

    public void broadcast(Info res, Set<IoSession> sessions) throws Exception {
        synchronized (sessions) {
            System.out.println("sessions.size():" + sessions.size());
            for (IoSession s : sessions) {
                if (s.isConnected()) {
                    s.write(res);
                }
            }
        }
    }

    @Override
    public void messageSent(IoSession session, Object message) throws Exception {
        //logger.info("服务端发送信息成功...");
    }

    @Override
    public void sessionClosed(IoSession session) throws Exception {
    }

    @Override
    public void sessionIdle(IoSession session, IdleStatus status)
            throws Exception {
        //logger.info("服务端进入空闲状态...");
    }

    @Override
    public void exceptionCaught(IoSession session, Throwable cause)
            throws Exception {
        synchronized (session) {
            for (Desk d : Info.getStatic_desks()) {
                if (d.getSessions().contains(session)) {
                    session.removeAttribute("ENTER_DESK");
                    d.getSessions().remove(session);
                    d.getTurns().add((String) (session.getAttribute("turnicon")));
                    sessions.remove(session);
                    d.setStatus(d.getStatus() - 1);
                    for (int i = 0; i < d.getUsers().length; i++) {
                        if (d.getUsers()[i] == d.getSession2user().get(session)) {
                            d.getUsers()[i] = null;
                            Info res = new Info();
                            res.setAction(OFFLINE);
                            res.setDesk_num(d.getDesk_num());
                            res.setDesk_position(i);
                            this.broadcast(res, sessions);
                        }
                    }
                }
            }
        }
        sessions.remove(session);
        logger.error("服务端发送异常...", cause);
    }
}