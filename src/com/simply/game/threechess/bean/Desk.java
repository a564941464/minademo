package com.simply.game.threechess.bean;

import org.apache.mina.core.session.IoSession;

import java.io.Serializable;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: who
 * Date: 13-3-16
 * Time: 上午12:57
 * To change this template use File | Settings | File Templates.
 */
public class Desk implements Serializable {
    public static final Integer full = 2;
    private Integer status = 0; //状态  0，1，2，没人， 1人， 2人
    private Integer game_status = 0;  //状态  0，1，2，没人， 1人， 2人
    private User[] users = new User[full];    //0,1
    private Set<IoSession> sessions = new LinkedHashSet<IoSession>(2);
    private int desk_num; 
    private Map<IoSession, User> session2user= new HashMap<IoSession, User>();
    private Stack<String> turns = new Stack<String>();
    
    
    public Integer getStatus() {
        return status;
    }

    public int getDesk_num() {
        return desk_num;
    }

    public void setDesk_num(int desk_num) {
        this.desk_num = desk_num;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Set<IoSession> getSessions() {
        return sessions;
    }

    public Integer getGame_status() {
        return game_status;
    }

    public void setGame_status(Integer game_status) {
        this.game_status = game_status;
    }

    public void setSessions(Set<IoSession> sessions) {
        this.sessions = sessions;
    }

    public User[] getUsers() {
        return users;
    }

    public static Integer getFull() {
        return full;
    }

    public Map<IoSession, User> getSession2user() {
        return session2user;
    }

    public void setSession2user(Map<IoSession, User> session2user) {
        this.session2user = session2user;
    }

    public Stack<String> getTurns() {
        return turns;
    }

    public void setTurns(Stack<String> turns) {
        this.turns = turns;
    }

    public void setUsers(User[] users) {
        this.users = users;
    }
    @Override
    public String toString(){
        Arrays.toString(this.users);
        return Arrays.toString(this.users);
    }
}
