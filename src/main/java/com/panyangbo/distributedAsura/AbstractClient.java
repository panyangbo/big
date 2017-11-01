package com.panyangbo.distributedAsura;

import com.panyangbo.distributedAsura.config.AsuraConfig;
import com.panyangbo.distributedAsura.config.ConfigManager;
import com.panyangbo.distributedAsura.shard.ShardHandler;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;

/**
 * 客户端抽象对象
 *
 * @author panyangbo panyangbo
 * @create 2017/6/26
 */

public abstract class AbstractClient {

    private ConfigManager cfgManager;

    protected ShardPools4Asura shardPools4Asura;

    private AsuraConfig asuraConfig;

    public AbstractClient() {
        cfgManager = new ConfigManager();
        this.init();
    }

    public AbstractClient(String config, AsuraConfig asuraConfig) {
        cfgManager = new ConfigManager(config,asuraConfig);
        this.init();
    }

   /** 初始化
    * @author 潘洋波[panybest1991@gmail.com]
    * @Date 2017/6/27 11:36
    * @Description AbstractClient
    * @version 4.0.0
    */
    private synchronized void init() {
        // 加载配置
        cfgManager.loadConfig();
        shardPools4Asura = new ShardPools4Asura(cfgManager.getLstShardInfo4Asura(),true);
    }

    public ShardPools4Asura getShardPools4Asura() {
        return shardPools4Asura;
    }

    // 刷新配置
    public synchronized void refresh(String config, AsuraConfig asuraConfig) {
        ShardPools4Asura old = shardPools4Asura;
        if (!StringUtils.isBlank(config)) {
            cfgManager.setConfig(config);
        }
        if (asuraConfig != null) {
            cfgManager.setAsuraConfig(asuraConfig);
        }
        this.init();
        // 旧池
        Collection<ShardHandler> allShards = old.getAllShards();
        for (ShardHandler shardHandler : allShards) {
            shardHandler.destroy();
        }
    }

    public ConfigManager getCfgManager() {
        return cfgManager;
    }
}
