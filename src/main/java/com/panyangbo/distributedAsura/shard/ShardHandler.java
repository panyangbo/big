package com.panyangbo.distributedAsura.shard;

import com.panyangbo.distributedAsura.config.CallBack;
import com.panyangbo.distributedAsura.config.ShardInfo4Asura;
import io.netty.channel.Channel;
import org.apache.commons.pool2.impl.GenericObjectPool;

/**
 * shardHandler操作处理
 *
 * @author panyangbo panyangbo
 * @create 2017/6/26
 */

public class ShardHandler {
    private GenericObjectPool<Channel> channelpool;
    private ShardInfo4Asura shardInfo4Asura;

    public ShardHandler(GenericObjectPool<Channel> channelpool, ShardInfo4Asura shardInfo4Asura) {
        this.channelpool = channelpool;
        this.shardInfo4Asura = shardInfo4Asura;
    }

    public GenericObjectPool<Channel> getChannelpool() {
        return channelpool;
    }

    public void setChannelpool(GenericObjectPool<Channel> channelpool) {
        this.channelpool = channelpool;
    }

    public ShardInfo4Asura getShardInfo4Asura() {
        return shardInfo4Asura;
    }

    public void setShardInfo4Asura(ShardInfo4Asura shardInfo4Asura) {
        this.shardInfo4Asura = shardInfo4Asura;
    }

    /**
     * @author 潘洋波[panybest1991@gmail.com]
     * @Date 2017/6/28 10:29
     * @Description ShardHandler
     * @version 4.0.0
     */
    public <R> R execute(CallBack<R> callBack) {
        Channel channel = null;
        try {
            //从channel池中获取对象
            channel = getChannelpool().borrowObject();
        } catch (Exception e) {
            shardInfo4Asura.setError();
            e.printStackTrace();
        }
        //channel处理callback
        R  r =  invoke(channel, callBack);
        //返还对象回channel池
        getChannelpool().returnObject(channel);
        return r ;
    }

    /**
     * @author 潘洋波[panybest1991@gmail.com]
     * @Date 2017/6/28 10:29
     * @Description ShardHandler
     * @version 4.0.0
     */
    private <R> R invoke(Channel channel, CallBack<R> callBack) {

        try {
            return callBack.invoke(channel);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }

    public void destroy() {
        channelpool.close();
    }

    public boolean isActive() {
        return getChannelpool().getBorrowedCount()>0;
    }
}
