package com.simply.minademo;
import org.apache.log4j.Logger;
import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Demo1ServerHandler extends IoHandlerAdapter {
	public static Logger logger = Logger.getLogger(Demo1ServerHandler.class);
    private final Set<IoSession> sessions = Collections
            .synchronizedSet(new HashSet<IoSession>());
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
		MsgBean msg = (MsgBean)message;
		logger.info("服务端接收到的数据为：" + msg.getName() + ",,," + msg.getAddress());
		if ("bye".equals(msg.getName())) { // 服务端断开连接的条件
			session.close();
		}
        sessions.add(session);
        synchronized (sessions) {
            for (IoSession ss : sessions) {

                if(ss.getId() !=session.getId())
                    if (ss.isConnected()) {
                        ss.write(msg);
                    }
            }
        }
	}
	@Override
	public void messageSent(IoSession session, Object message) throws Exception {
		logger.info("服务端发送信息成功...");
	}
	@Override
	public void sessionClosed(IoSession session) throws Exception {

	}
	@Override
	public void sessionIdle(IoSession session, IdleStatus status)
			throws Exception {
		logger.info("服务端进入空闲状态...");
	}
	@Override
	public void exceptionCaught(IoSession session, Throwable cause)
			throws Exception {
		logger.error("服务端发送异常...", cause);
	}
}