package com.panyangbo.distributedAsura;

import com.panyangbo.distributedAsura.config.AsuraConfig;
import com.panyangbo.distributedAsura.config.CallBack;
import com.panyangbo.distributedAsura.netty.blocking.BlockingRead;
import com.panyangbo.distributedAsura.netty.blocking.DefaultBlockingRead;
import io.netty.channel.Channel;
import io.netty.util.Attribute;

/**
 * @author panyangbo panyangbo
 * @create 2017/6/28
 */

public class AsuraClient extends AbstractClient{
    public AsuraClient() {
    }

    public AsuraClient(String config,AsuraConfig asuraConfig) {
        super(config,asuraConfig);
    }


    private <R> R performFunctionByHash(String S, CallBack<R> callBack) throws NullPointerException {
        //initParam(keyMap);
            return shardPools4Asura.getShardByHash(S).execute(callBack);
    }

    private <R> R performFunctionByName(String S, CallBack<R> callBack) throws NullPointerException {
        //initParam(keyMap);
        return shardPools4Asura.getShardByName(S).execute(callBack);
    }


    public Channel writeandflushByHash(final String key , final Object value) {
        return this.performFunctionByHash(key, new CallBack<Channel>() {
            // set
            public Channel invoke(Channel channel) {
                return channel.writeAndFlush(value).channel();
            }
        });
    }

    public Channel writeandflushByName(final String key , final Object value) {
        return this.performFunctionByName(key, new CallBack<Channel>() {
            // set
            public Channel invoke(Channel channel) {
                return channel.writeAndFlush(value).channel();
            }
        });
    }

    public DefaultBlockingRead writeandflushByNameBlocked(final String key , final Object value) {
        return this.performFunctionByName(key, new CallBack<DefaultBlockingRead>() {
            // set
            public DefaultBlockingRead invoke(Channel channel) {
                channel.writeAndFlush(value).channel();
                Attribute<BlockingRead> r = channel.attr(BlockingRead.CHANNEL_BLOCKING_READ_KEY);
                DefaultBlockingRead read = (DefaultBlockingRead) r.get();
                return read;
            }
        });
    }
}
