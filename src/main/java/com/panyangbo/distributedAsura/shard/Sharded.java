package com.panyangbo.distributedAsura.shard;

import com.panyangbo.util.Hashing;

import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 分片器
 *
 * @author panyangbo panyangbo
 * @create 2017/6/26
 */

public class Sharded<R, S extends ShardInfo<R>> {

    // the tag is anything between {}
    public static final Pattern DEFAULT_KEY_TAG_PATTERN = Pattern.compile("\\{(.+?)\\}");

    private TreeMap<Long, S> nodes;

    private final Hashing algo;

    private final Map<ShardInfo<R>, R> resources = new LinkedHashMap<ShardInfo<R>, R>();

    private final Map<String, R> rs = new HashMap<String, R>();

    /**
     *异常tag正则
     */
    private Pattern tagPattern = null;

    /**采用murmurhash hash速度比较快，不加密，散列较均匀
     * @author 潘洋波[panybest1991@gmail.com]
     * @Date 2017/6/27 17:09
     * @Description Sharded
     * @version 4.0.0
     */
    public Sharded(List<S> shards,boolean isHashing) {
        this(shards, Hashing.MURMUR_HASH,isHashing);
    }

    public Sharded(List<S> shards, Hashing algo,boolean isHashing) {
        this.algo = algo;
        initialize(shards,isHashing);
    }

    public Sharded(List<S> shards, Pattern tagPattern,boolean isHashing) {
        this(shards, Hashing.MURMUR_HASH, tagPattern,isHashing);
    }

    public Sharded(List<S> shards, Hashing algo, Pattern tagPattern,boolean isHashing) {
        this.algo = algo;
        this.tagPattern = tagPattern;
        initialize(shards,isHashing);
    }

    /**
     * 按照权重比创建基础服务资源
     * @author 潘洋波[panybest1991@gmail.com]
     * @Date 2017/6/26 16:43
     * @Description Sharded
     * @version 4.0.0
     */
    private void initialize(List<S> shards,boolean isHashing) {
        int shardsSize=shards.size();
        final int factor = 160;
        nodes = new TreeMap<Long, S>();
        for (int i = 0; i != shardsSize; ++i) {
            final S shardInfo = shards.get(i);
            R resource = shardInfo.createResource();
            final int weight = shardInfo.getWeight();
            final int fweight = factor * weight;
            if (shardInfo.getName() == null) {
                for (int n = 0; n < fweight; n++) {
                    nodes.put(this.algo.hash("SHARD-" + i + "-NODE-" + n), shardInfo);
                }
            } else {
                for (int n = 0; n < fweight; n++) {
                    nodes.put(this.algo.hash(shardInfo.getName() + "*" + shardInfo.getWeight() + n), shardInfo);
                }
            }
            resources.put(shardInfo, resource);
            rs.put(shardInfo.getName(),resource);
        }
    }


    /**移除失效nodes
     * @author 潘洋波[panybest1991@gmail.com]
     * @Date 2017/10/28 17:40
     * @Description Sharded
     * @version 4.0.0
     */
   private void removeNodes(String shardName){

   }
   /**
    * 获取指定片
    * @author 潘洋波[panybest1991@gmail.com]
    * @Date 2017/6/26 16:44
    * @Description Sharded
    * @version 4.0.0
    */
    public R getShardByName(String key) {
        Set<String> keySet = rs.keySet();
        for (String KS: keySet) {
            if (key.equals(KS)) {
                return rs.get(KS);
            }
        }
        return null;
    }

    public R getShardByHash(String key) {
        return resources.get(getShardInfo(key));
    }

   /**
    * 获取分片信息
    * @author 潘洋波[panybest1991@gmail.com]
    * @Date 2017/6/26 16:44
    * @Description Sharded
    * @version 4.0.0
    */
    public S getShardInfo(byte[] key) {
        SortedMap<Long, S> tail = nodes.tailMap(algo.hash(key));
        if (tail.size() == 0) {
            return nodes.get(nodes.firstKey());
        }
        System.out.println("操作节点hash值："+tail.firstKey());
        return tail.get(tail.firstKey());
    }

    /**
     * 获取ShardInfo
     * @author 潘洋波[panybest1991@gmail.com]
     * @Date 2017/6/26 16:47
     * @Description Sharded
     * @version 4.0.0
     */
    public S getShardInfo(String key) {
        byte[] code = new byte[0] ;
        try {
            code = getKeyTag(key).getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return getShardInfo(code);
    }


    /**
     * 正则筛选，只取可以正常hash的tag
     * @author 潘洋波[panybest1991@gmail.com]
     * @Date 2017/6/26 16:48
     * @Description Sharded
     * @version 4.0.0
     */
    public String getKeyTag(String key) {
        if (tagPattern != null) {
            Matcher m = tagPattern.matcher(key);
            if (m.find()) {
                return m.group(1);
            }
        }
        return key;
    }

  /**
   * getAllShardInfo
   * @author 潘洋波[panybest1991@gmail.com]
   * @Date 2017/6/26 16:48
   * @Description Sharded
   * @version 4.0.0
   */
    public Collection<S> getAllShardInfo() {
        return Collections.unmodifiableCollection(nodes.values());
    }

   /**.
    * getAllShards
    * @author 潘洋波[panybest1991@gmail.com]
    * @Date 2017/6/26 16:48
    * @Description Sharded
    * @version 4.0.0
    */
    public Collection<R> getAllShards() {
        return Collections.unmodifiableCollection(resources.values());
    }

    public TreeMap<Long, S> getNodes() {
        return nodes;
    }


}
