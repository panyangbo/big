package com.panyangbo.distributedAsura.netty.client;


import com.panyangbo.domain.core.HeadType;
import com.panyangbo.domain.core.SocketPg;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.socket.SocketChannel;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 认证req处理器
 * @author 潘洋波[panybest1991@gmail.com]
 * @Date 2017/7/7 11:37
 * @Description LoginAuthReqHandler
 * @version 4.0.0
 */
public class LoginAuthReqHandler extends ChannelInboundHandlerAdapter {
    private final Log log = LogFactory.getLog(getClass());

    private String serverid;
    private String serverType;

    public LoginAuthReqHandler(String serverid, String serverType) {
        this.serverid = serverid;
        this.serverType = serverType;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(new SocketPg(serverid, HeadType.LOGIN_AUTH_REQ,"-1",serverType));
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        SocketChannel channel = (SocketChannel) ctx.channel();
        SocketPg socketPg = (SocketPg) msg;
        if (socketPg.getHeader() != null && socketPg.getHeader().getType() == HeadType.LOGIN_AUTH_RESP){
            byte loginResult = (Byte) socketPg.getBody();
            if (loginResult != (byte)0){
                //握手失败
                ctx.close();
                log.error(String.format("login req to%1$s:%2$d fail ctx close", channel.remoteAddress().getAddress(), channel.remoteAddress().getPort()));
            }else{
                log.debug(String.format("login req to%1$s:%2$d success", channel.remoteAddress().getAddress(), channel.remoteAddress().getPort()));
                ctx.fireChannelRead(msg);
            }
        }else {
            ctx.fireChannelRead(msg);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.fireExceptionCaught(cause);
    }
}
