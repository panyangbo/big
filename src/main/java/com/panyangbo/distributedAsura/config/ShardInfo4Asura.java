package com.panyangbo.distributedAsura.config;

import com.panyangbo.distributedAsura.netty.client.ClientHandlerFactory;
import com.panyangbo.distributedAsura.netty.client.IOConnector;
import com.panyangbo.distributedAsura.netty.client.NettyClient;
import com.panyangbo.distributedAsura.netty.client.pool.ConnectionPoolFactory;
import com.panyangbo.distributedAsura.netty.client.pool.DefaultConnectionPoolFactory;
import com.panyangbo.distributedAsura.shard.ShardHandler;
import com.panyangbo.distributedAsura.shard.ShardInfo;
import io.netty.channel.Channel;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

/**
 * @author 潘洋波[panybest1991@gmail.com]
 * @Date 2017/6/27 13:59
 * @Description ShardInfo4Asura
 * @version 4.0.0
 */
public class ShardInfo4Asura extends ShardInfo<ShardHandler> {

    /**
     * shard的名
     */
    private final String shardName;

    /**
     * 服务器ip
     */
    private String ip;

    /**
     * 服务器断口
     */
    private Integer port;

    /**
     * 服务id
     */
    private String serverid;

    /**
     * 服务类型
     */
    private String servertype;

    /**
     * 配置信息
     */
    private GenericObjectPoolConfig config;

    private boolean blockedRead;


    public ShardInfo4Asura(int weight, String shardName, String ip, Integer port, GenericObjectPoolConfig config,String serverid,String servertype,boolean blockedRead) {
        super(weight);
        this.shardName = shardName;
        this.ip = ip;
        this.port = port;
        this.config = config;
        this.serverid = serverid;
        this.servertype = servertype;
        this.blockedRead  = blockedRead;
    }

    @Override
    public int getWeight() {
        return super.getWeight();
    }

    /**
     * 创建本片资源
     * @author 潘洋波[panybest1991@gmail.com]
     * @Date 2017/6/27 14:09
     * @Description ShardInfo4Asura
     * @version 4.0.0
     */
    @Override
    protected ShardHandler createResource() {
        ClientHandlerFactory factory = new ClientHandlerFactory(serverid,servertype);
        factory.setBlockingRead(blockedRead);
        NettyClient nettyClient = new IOConnector(factory);
        try {
            nettyClient.init();
        } catch (Exception e) {
            e.printStackTrace();
        }
        ConnectionPoolFactory connectionPoolFactory = new DefaultConnectionPoolFactory(nettyClient);
        GenericObjectPool<Channel> pool = connectionPoolFactory.buildPool(ip, port,config);
        return new ShardHandler(pool, this);
    }

    @Override
    public String getName() {
        return this.shardName;
    }

    public void setError() {

    }

    public boolean isActive(ShardHandler handler) {
        return handler.isActive();
    }

    public String getIp() {
        return ip;
    }

    public Integer getPort() {
        return port;
    }

    public String getServerid() {
        return serverid;
    }
}
