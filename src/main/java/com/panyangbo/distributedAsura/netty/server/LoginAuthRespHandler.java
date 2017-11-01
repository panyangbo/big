package com.panyangbo.distributedAsura.netty.server;

import com.panyangbo.domain.core.HeadType;
import com.panyangbo.domain.core.ServerState;
import com.panyangbo.domain.core.SocketPg;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.socket.SocketChannel;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.net.InetSocketAddress;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 认证Resp处理器
 * @author 潘洋波[panybest1991@gmail.com]
 * @Date 2017/7/7 9:58
 * @Description LoginAuthRespHandler
 * @version 4.0.0
 */
public class LoginAuthRespHandler extends ChannelInboundHandlerAdapter {

    private final Log log = LogFactory.getLog(getClass());

    private Map<String,Boolean> nodeCheak = new ConcurrentHashMap<String, Boolean>();

    private List<String> whiteList;

    private String serverid;

    private ConnetStateUtil connetStateUtil;


    public LoginAuthRespHandler(List<String> whiteList, String serverid, ConnetStateUtil connetStateUtil) {
        this.whiteList = whiteList;
        this.serverid = serverid;
        this.connetStateUtil = connetStateUtil;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        SocketChannel channel = (SocketChannel) ctx.channel();
        ctx.fireUserEventTriggered(ServerState.STATE_CONNECT_SUCCESS_LOGIN_FAIL);
        connetStateUtil.serverConnectState(channel.remoteAddress(),ServerState.STATE_CONNECT_SUCCESS_LOGIN_FAIL);
        log.debug(String.format("LoginAuthRespHandler New connection from %1$s:%2$d", channel.remoteAddress().getAddress(), channel.remoteAddress().getPort()));
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        SocketChannel channel = (SocketChannel) ctx.channel();
        ctx.fireUserEventTriggered(ServerState.STATE_CONNECT_FAIL);
        connetStateUtil.serverConnectState(channel.remoteAddress(),ServerState.STATE_CONNECT_SUCCESS_LOGIN_FAIL);
        log.debug(String.format("Connection from %1$s:%2$d closed", channel.remoteAddress().getAddress(), channel.remoteAddress().getPort()));
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        SocketChannel channel = (SocketChannel) ctx.channel();
        SocketPg sp = (SocketPg) msg;
        if (sp.getHeader() != null && sp.getHeader().getType() == HeadType.LOGIN_AUTH_REQ){
            String nodeip = ctx.channel().remoteAddress().toString();
            SocketPg loginResp = null;
            String type = (String) sp.getBody();
            if (nodeCheak.containsKey(nodeip) && nodeCheak.get(nodeip)){
                loginResp = new SocketPg(serverid, HeadType.LOGIN_AUTH_RESP,"-1",(byte)-1);
            }else{
                InetSocketAddress address = (InetSocketAddress) ctx.channel().remoteAddress();
                String ip = address.getAddress().getHostAddress();
                boolean isOK = false;
                for (String wip :  whiteList){
                    if (wip.equals(ip)){
                        isOK = true;
                        break;
                    }
                }
                loginResp = isOK?new SocketPg(serverid,HeadType.LOGIN_AUTH_RESP,"-1",(byte)0):
                        new SocketPg(serverid,HeadType.LOGIN_AUTH_RESP,"-1",(byte)-1);
                if (isOK) {
                    nodeCheak.put(nodeip,true);
//                    ServerState.STATE_LOGIN_SUCCESS.setServerType(type);
                    ctx.fireUserEventTriggered(ServerState.STATE_LOGIN_SUCCESS);
                    connetStateUtil.serverConnectState(sp.getHeader().getServerId(),String.valueOf(sp.getBody()), (InetSocketAddress) ctx.channel().remoteAddress(),ServerState.STATE_LOGIN_SUCCESS);
                }
            }
            log.debug(String.format("login req from%1$s:%2$d", channel.remoteAddress().getAddress(), channel.remoteAddress().getPort()));
            ctx.writeAndFlush(loginResp);
        }else{
            ctx.fireChannelRead(msg);
        }
    }
}
