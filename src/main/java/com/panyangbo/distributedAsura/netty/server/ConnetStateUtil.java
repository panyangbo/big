package com.panyangbo.distributedAsura.netty.server;

import com.panyangbo.domain.core.ServerState;

import java.net.InetSocketAddress;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * 连接状态帮助类
 * @author panyangbo panyangbo
 * @create 2017/8/1
 */

public class ConnetStateUtil {

    private Map<InetSocketAddress,Server> state = new HashMap<InetSocketAddress, Server>();



    public void serverConnectState(InetSocketAddress address,ServerState serverState){
        if (state.containsKey(address)){
            switch (serverState){
                case STATE_CONNECT_FAIL:
                    state.remove(address);
                   break;
                default:
                    state.get(address).getPortState().put(address.getPort(),serverState);
                    break;
            }
        }else{
            Map<Integer,ServerState> portState = new HashMap<Integer, ServerState>();
            portState.put(address.getPort(),serverState);
            state.put(address,new Server(address.getAddress().toString(),portState));
        }
    }

    public void serverConnectState(String serverId, String server_type,InetSocketAddress address,ServerState serverState){
        if (state.containsKey(address)){
            switch (serverState){
                case STATE_CONNECT_FAIL:
                    state.remove(address);
                    break;
                default:
                    state.get(address).getPortState().put(address.getPort(),serverState);
                    state.get(address).setServerId(serverId);
                    state.get(address).setServer_type(server_type);
                    break;
            }
        }else{
            Map<Integer,ServerState> portState = new HashMap<Integer, ServerState>();
            portState.put(address.getPort(),serverState);
            state.put(address,new Server(serverId,address.getAddress().toString(),server_type,portState));
        }
    }

    protected  static class Server {

        private String serverId;
        private String ip;
        private String server_type;
        private Map<Integer,ServerState> portState;

        public Server(String ip, Map<Integer, ServerState> portState) {
            this.ip = ip;
            this.portState = portState;
        }

        public Server(String serverId, String ip, String server_type, Map<Integer, ServerState> portState) {
            this.serverId = serverId;
            this.ip = ip;
            this.server_type = server_type;
            this.portState = portState;
        }


        public String getServerId() {
            return serverId;
        }

        public void setServerId(String serverId) {
            this.serverId = serverId;
        }

        public String getIp() {
            return ip;
        }

        public void setIp(String ip) {
            this.ip = ip;
        }

        public String getServer_type() {
            return server_type;
        }

        public void setServer_type(String server_type) {
            this.server_type = server_type;
        }

        public Map<Integer, ServerState> getPortState() {
            return portState;
        }

        public void setPortState(Map<Integer, ServerState> portState) {
            this.portState = portState;
        }
    }

    public Collection<Server> getState() {
        return state.values();
    }
}
