package com.panyangbo.distributedAsura.shard;

/**
 * 分片器来源jedis
 *
 * @author panyangbo panyangbo
 * @create 2017/6/26
 */

public abstract class ShardInfo<T> {
    /**
     * 该shard的权重
     */
    private int weight = 1;

    public ShardInfo() {
    }

    public ShardInfo(int weight) {
        this.weight = weight;
    }

    public int getWeight() {
        return weight;
    }

    /**
   * 创建片资源
   * @author 潘洋波[panybest1991@gmail.com]
   * @Date 2017/6/26 16:35
   * @Description ShardInfo
   * @version 4.0.0
   */
    protected abstract T createResource();

  /**
   * 获取片名
   * @author 潘洋波[panybest1991@gmail.com]
   * @Date 2017/6/26 16:36
   * @Description ShardInfo
   * @version 4.0.0
   */
    public abstract String getName();


    public abstract void setError();
}
