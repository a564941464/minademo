package com.simply.game.threechess.client;

import com.simply.game.threechess.bean.Info;
import com.simply.game.threechess.bean.User;
import org.apache.log4j.Logger;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.serialization.ObjectSerializationCodecFactory;
import org.apache.mina.transport.socket.nio.NioSocketConnector;
import static com.simply.game.threechess.bean.Constants.*;
import com.simply.game.threechess.bean.SelectInfo;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import java.net.InetSocketAddress;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import org.apache.mina.core.service.IoHandlerAdapter;

public class Communicator extends IoHandlerAdapter {

    private static Logger logger = Logger.getLogger(Communicator.class);
    private IoSession session = null;
    private String host;
    private int port;
    private MainGUI m;

    public Communicator(String host, int port, MainGUI m) {
        this.host = host;
        this.port = port;
        this.m = m;
        
    }

    public boolean send(Info req) {
        if (this.session == null) {
            // 创建一个非阻塞的客户端程序
            IoConnector connector = new NioSocketConnector();  // 创建连接
            // 设置链接超时时间
            connector.setConnectTimeout(30000);
            // 添加过滤器
            connector.getFilterChain().addLast("codec",
                    new ProtocolCodecFilter(new ObjectSerializationCodecFactory()));
            // 添加业务逻辑处理器类
            connector.setHandler(this);// 添加业务处理
            try {
                ConnectFuture future = connector.connect(new InetSocketAddress(
                        this.host, this.port));// 创建连接
                future.awaitUninterruptibly();// 等待连接创建完成
                this.session = future.getSession();// 获得session

            } catch (Exception e) {
                logger.error("客户端链接异常...", e);
                return false;
            }
        }
        this.session.write(req);
        return true;
    }

    public void close() {
        if (session != null) {
            if (session.isConnected()) {
                //session.write("QUIT");
                // Wait until the chat ends.
                session.getCloseFuture().awaitUninterruptibly();
            }
            session.close(true);
        }
    }

    @Override
    public void messageReceived(IoSession session, Object message)
            throws Exception {
        Info res = (Info) message;
        logger.info("\nres:"+res);
        logger.info("=================================================================");
        logger.info("\res:"+res);
        logger.info("=================================================================");
        if (res.getAction().equals(ROOM)) {
            m.initRoom(res.getDesks());
        } else if (res.getAction().equals(CHAT_MSG_TO_ALL)) {
            m.getjTextPane1().setText(m.getjTextPane1().getText() + res.getChat_msg() + "\n\n");
            m.getjTextField2().setText("");
        } else if (res.getAction().equals(DESK)) {
            DeskGUI dui = m.getDuilist().get(res.getDesk_num());
            if (res.getDesk_position() == 0) {
                dui.getUser1().setText(res.getDeskinfo());
            } else {
                dui.getUser2().setText(res.getDeskinfo());
            }

        } else if (res.getAction().equals(ENTER_DESK_OK)) {
            //System.out.println("res.getTurnicon()"+res.getTurnicon());
            
            m.initGameGUI(res.getTurnicon());
            m.getGamegui().setMy_turn(res.getTurnicon());
            m.getGamegui().setDesk_num(res.getDesk_num());
            m.getGamegui().setDesk_position(res.getDesk_position());

            m.getGamegui().getjLabel2().setText(res.getDesk_num() + "");
            m.getGamegui().getjLabel4().setText(res.getUser().getName()); 

            if(res.getDuiShou()!=null){
                m.getGamegui().getjLabel6().setText(res.getDuiShou().getName());
            }else{
                m.getGamegui().getjLabel6().setText("");
            }

        } else if(res.getAction().equals(UPDATE_DESK)){
            DeskButton dbtn = m.getDuilist().get(res.getDesk_num()).getDeskButton(res.getDesk_position());
            dbtn.setText("占");
            System.out.println(res.getDesk_num()+" "+m +" "+ m.getGamegui());
            if(m.getGamegui()!=null){
                if(res.getDesk_num() == m.getGamegui().getDesk_num() && res.getDesk_position() != m.getGamegui().getDesk_position()){
                    if(res.getUser()!=null){
                        m.getGamegui().getjLabel6().setText(res.getUser().getName());
                    }else{
                        m.getGamegui().getjLabel6().setText("");
                    }
                }
            }
 
        }  else if (res.getAction().equals(ENTER_DESK_FAIL)) {
            JOptionPane.showMessageDialog(m, res.getNotice_msg());
        } else if (res.getAction().equals(OFFLINE)) {
            System.out.println(res.getDesk_position());
            DeskButton dbtn = m.getDuilist().get(res.getDesk_num()).getDeskButton(res.getDesk_position());
            dbtn.setText("空");
        } else if(res.getAction().equals(QUIT_DESK)){             
             DeskButton dbtn = m.getDuilist().get(res.getDesk_num()).getDeskButton(res.getDesk_position());
             dbtn.setText("空");
        } else if(res.getAction().equals(CHAT_MSG)){             
             m.getGamegui().getjTextPane1().setText(m.getGamegui().getjTextPane1().getText() + res.getChat_msg() + "\n\n");
        } else if(res.getAction().equals(START_AWAIT)){             
             m.getGamegui().getjLabel7().setText("准备");
             m.getGamegui().getDeskButton1().setEnabled(false);
        } else if(res.getAction().equals(START_OK)){             
             m.getGamegui().getjLabel7().setText("开始");
             m.getGamegui().getjLabel8().setText("当前：");

             m.getGamegui().setCur_turn(res.getGamelogic().getCur_turn());
             Turn t = m.getGamegui().getTurn2();
             t.setBufImg(this.getBufImg(res.getGamelogic().getCur_turn()));
             t.repaint();
             m.getGamegui().setStatus(PUTTING);
             
             m.getGamegui().getDeskButton1().setEnabled(false);
             
        } else if(res.getAction().equals(PUT)){
            m.getGamegui().drawPiece(res.getGamelogic().getPre_turn(), res.getGamelogic().getId());
            m.getGamegui().setCur_turn(res.getGamelogic().getCur_turn());
            String get_str = turn2str.get(res.getGamelogic().getPre_turn());

            Turn t = m.getGamegui().getTurn2();
            t.setBufImg(this.getBufImg(res.getGamelogic().getCur_turn()));
            t.repaint();
            System.out.println("res.getGamelogic().isGoing():"+res.getGamelogic().isGoing());
            if(res.getGamelogic().isGoing()){
                m.getGamegui().setStatus(SELECTTING);
            }else{
                m.getGamegui().setStatus(PUTTING);    
            }
            

        } else if(res.getAction().equals(TO_GET)){ 
            //m.getGamegui().clearPiece(res.getGamelogic().getId());
            m.getGamegui().drawPiece(res.getGamelogic().getCur_turn(), res.getGamelogic().getId());
            m.getGamegui().setCur_turn(res.getGamelogic().getCur_turn());
            String get_str = turn2str.get(res.getGamelogic().getCur_turn());
            m.getGamegui().getjLabel7().setText(get_str);
            m.getGamegui().setStatus(GETTING);
            
        }  else if(res.getAction().equals(GET)){ 
            m.getGamegui().clearPiece(res.getGamelogic().getId()); 
            m.getGamegui().setCur_turn(res.getGamelogic().getCur_turn());
      
             Turn t = m.getGamegui().getTurn2();
             t.setBufImg(this.getBufImg(res.getGamelogic().getCur_turn()));
             t.repaint();
            if(res.getGamelogic().isGoing()){
                m.getGamegui().setStatus(SELECTTING);
                m.getGamegui().getjLabel7().setText(res.getGamelogic().getCur_turn() + "走");  
            }else{
                m.getGamegui().setStatus(PUTTING);
                m.getGamegui().getjLabel7().setText("落子ing");                
            }            
         } else if(res.getAction().equals(TO_SELECT)){             
            m.getGamegui().setCur_turn(res.getGamelogic().getPre_turn());
            m.getGamegui().getjLabel7().setText(res.getGamelogic().getCur_turn() + "走");           
            m.getGamegui().setStatus(SELECTTING); 
            
         } else if(res.getAction().equals(SELECT)){  
            SelectInfo res_select = (SelectInfo)message;
            if(m.getGamegui().getSelect_id()!=0){
                m.getGamegui().unselect(m.getGamegui().getSelect_id());
            }
            
            m.getGamegui().select(res_select.getS_select_id());
            m.getGamegui().setCur_turn(res_select.getS_cur_turn());
            m.getGamegui().setDesk_num(res_select.getS_desk_num());
            m.getGamegui().setStatus(GOING);
            
         }else if(res.getAction().equals(GO)){             
            m.getGamegui().go(res.getFrom_id(), res.getTo_id());
            m.getGamegui().setCur_turn(res.getGamelogic().getCur_turn());
            if(res.getGamelogic().isIsChenglian()){
                m.getGamegui().setStatus(GETTING);
            }else{
                m.getGamegui().setStatus(SELECTTING);
            }
            Turn t = m.getGamegui().getTurn2();
            t.setBufImg(this.getBufImg(res.getGamelogic().getCur_turn()));
            t.repaint();
         }

    }
    public BufferedImage getBufImg(String turn) throws IOException{
        File buffile;
        if((WHITE_TURN).equals(turn)){
            buffile  = new File("images/white.png");
        }else{
            buffile  = new File("images/black.png");
        }
        return ImageIO.read(buffile);
    }
    @Override
    public void exceptionCaught(IoSession session, Throwable cause)
            throws Exception {
        logger.error("客户端发生异常...", cause);
    }

    public static void main(String[] args) {
        Communicator c = new Communicator("127.0.0.1", 3005, null);
        Info inn = new Info();
        inn.setAction(ROOM);
        c.send(inn);

        inn.setAction(START);
        inn.setUser(new User());
        c.send(inn);


        inn.setAction(ENTER_DESK);
        inn.setDesk_num(2);
        inn.setDesk_position(1);  //
        c.send(inn);

        inn.setAction(ENTER_DESK);
        inn.setDesk_num(2);
        inn.setDesk_position(1);  //
        c.send(inn);

        inn.setAction(START);
        c.send(inn);
    }
}