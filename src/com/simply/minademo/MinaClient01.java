package com.simply.minademo;

import org.apache.log4j.Logger;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.serialization.ObjectSerializationCodecFactory;
import org.apache.mina.filter.codec.textline.LineDelimiter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;

public class MinaClient01 {
	private static Logger logger = Logger.getLogger(MinaClient01.class);

	private static String HOST = "127.0.0.1";

	private static int PORT = 3005;

	public static void main(String[] args) {
		// 创建一个非阻塞的客户端程序
		IoConnector connector = new NioSocketConnector();  // 创建连接
		// 设置链接超时时间
		connector.setConnectTimeout(30000);
		// 添加过滤器
		/*connector.getFilterChain().addLast(   //添加消息过滤器
				"codec",
				new ProtocolCodecFilter(new TextLineCodecFactory(Charset
						.forName("UTF-8"), LineDelimiter.WINDOWS.getValue(),
						LineDelimiter.WINDOWS.getValue())));*/


        connector.getFilterChain().addLast("codec",
                new ProtocolCodecFilter(new ObjectSerializationCodecFactory()));
		// 添加业务逻辑处理器类
		connector.setHandler(new Demo1ClientHandler());// 添加业务处理
		IoSession session = null;
        try {
        ConnectFuture future = connector.connect(new InetSocketAddress(
                HOST, PORT));// 创建连接
        future.awaitUninterruptibly();// 等待连接创建完成
        session = future.getSession();// 获得session
        while(true){

                InputStreamReader isr = new InputStreamReader(System.in);

                BufferedReader br = new BufferedReader(isr);
                String s = null;
                try {
                    s = br.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
               MsgBean msg = new MsgBean();
               String[] ss = s.split(",");
               msg.setName(ss[0]);
               msg.setAddress(ss[1]);
                session.write(msg);// 发送消息

        }
        } catch (Exception e) {
            logger.error("客户端链接异常...", e);
        }

	}
}