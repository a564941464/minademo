package com.simply.minademo;

import org.apache.log4j.Logger;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;

public class Demo1ClientHandler extends IoHandlerAdapter {
	private static Logger logger = Logger.getLogger(Demo1ClientHandler.class);

	@Override
	public void messageReceived(IoSession session, Object message)
			throws Exception {
		MsgBean msg = (MsgBean)message;
		logger.info("客户端接收到的信息为：" + msg.getName() + ",,, " + msg.getAddress());
	}

	@Override
	public void exceptionCaught(IoSession session, Throwable cause)
			throws Exception {
		logger.error("客户端发生异常...", cause);
	}
}