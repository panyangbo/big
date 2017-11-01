package com.panyangbo.distributedAsura.config;

import com.panyangbo.util.ResourceUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author panyangbo panyangbo
 * @create 2017/6/26
 */

public class ConfigManager {

    private static Logger logger = LoggerFactory.getLogger(ConfigManager.class);

    // 默认配置文件
    private static final String CONFIG = "base.xml";

    private String config = CONFIG;

    private AsuraConfig asuraConfig = new AsuraConfig("null","null",false);

    protected List<ShardInfo4Asura> lstShardInfo4Asura;
    
    public ConfigManager() {}

    private Document doc;

    public void setAsuraConfig(AsuraConfig asuraConfig) {
        this.asuraConfig = asuraConfig;
    }

    public ConfigManager(String config, AsuraConfig asuraConfig) {
        this.config = config;
        this.asuraConfig = asuraConfig;
    }


    /**加载配置文件
     * @author 潘洋波[panybest1991@gmail.com]
     * @Date 2017/6/27 17:37
     * @Description ConfigManager
     * @version 4.0.0
     */
    public void loadConfig() {
        InputStream inputStream = null;
        try {
            inputStream = ResourceUtils.getResourceAsStream(config);
            doc = new SAXReader().read(inputStream);
            // 解析配置文件
            parserWithDoc(doc);
        } catch (IOException e) {
            logger.error("IOException!", e);
            throw new RuntimeException("IOException!", e);
        } catch (DocumentException e) {
            logger.error("SAXReader parse cache xml error!", e);
            throw new RuntimeException("SAXReader parse cache xml error!", e);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    logger.error(e.getMessage());
                }
            }
        }

    }

    /**解析xml
     * @author 潘洋波[panybest1991@gmail.com]
     * @Date 2017/6/27 17:38
     * @Description ConfigManager
     * @version 4.0.0
     */
    private void parserWithDoc(Document doc) {
        Element serverElement = doc.getRootElement();
        GenericObjectPoolConfig poolConfig = this.parsePoolConfig(serverElement);
        parseShardingConfig(serverElement, poolConfig);
    }

    /**解析片区配置
     * @author 潘洋波[panybest1991@gmail.com]
     * @Date 2017/6/27 17:40
     * @Description ConfigManager
     * @version 4.0.0
     */
    private void parseShardingConfig(Element serverElement, GenericObjectPoolConfig poolConfig) {
        lstShardInfo4Asura = new ArrayList<ShardInfo4Asura>();
        List<Element> shards = serverElement.element("shardConfig").elements("shard");
        for (Element shardElement : shards) {
            String shardName = shardElement.attributeValue("name").trim();
            String ip = shardElement.elementTextTrim("ip");
            String port = shardElement.elementTextTrim("port");
            int weight = Integer.valueOf(shardElement.elementTextTrim("weight"));
            if (StringUtils.isBlank(ip)) {
                // 默认ip
                ip = "127.0.0.1";
            }
            if (StringUtils.isBlank(port)) {
                // 默认端口
                port = "9586";
            }

            lstShardInfo4Asura.add(new ShardInfo4Asura(weight,shardName,ip,Integer.valueOf(port),poolConfig,asuraConfig.getServerid(),asuraConfig.getServerType(),asuraConfig.isBlockedRead()));

        }
    }

    /**
     * 添加配置
     * @author 潘洋波[panybest1991@gmail.com]
     * @Date 2017/7/21 14:47
     * @Description ConfigManager
     * @version 4.0.0
     */
    public void addConfig( String ip , int port, int weight) throws IOException {
        Element shardConfig = doc.getRootElement().element("shardConfig");
        Element shard = shardConfig.addElement("shard")
                .addAttribute("name", ip+":"+port);

        shard.addElement("ip")
                .addText(ip);
        shard.addElement("port")
                .addText(String.valueOf(port));
        shard.addElement("weight")
                .addText(String.valueOf(weight));

        // Pretty print the document to System.out
        OutputFormat format = OutputFormat.createPrettyPrint();
        format.setEncoding("UTF-8");
        XMLWriter writer;
        writer = new XMLWriter( new FileWriter(ResourceUtils.getResourceAsFile(config)),format);
        writer.write( doc );
        writer.flush();
        writer.close();
    }


    /**
     * 删除配置
     * @author 潘洋波[panybest1991@gmail.com]
     * @Date 2017/7/21 17:46
     * @Description ConfigManager
     * @version 4.0.0
     */
    public void delConfig(String ip, int port) throws IOException {
        Element shardConfig = doc.getRootElement().element("shardConfig");
        List<Element> shards = shardConfig.elements("shard");
        for (Element shardElement : shards) {
            if (ip.equals(shardElement.elementTextTrim("ip")) && port == Integer.valueOf(shardElement.elementTextTrim("port"))){
                shards.remove(shardElement);
                break;
            }
        }
        // Pretty print the document to System.out
        OutputFormat format = OutputFormat.createPrettyPrint();
        format.setEncoding("UTF-8");
        XMLWriter writer;
        writer = new XMLWriter( new FileWriter(ResourceUtils.getResourceAsFile(config)),format);
        writer.write( doc );
        writer.flush();
        writer.close();
    }

    /**加载线程池配置
     * @author 潘洋波[panybest1991@gmail.com]
     * @Date 2017/6/27 17:39
     * @Description ConfigManager
     * @version 4.0.0
     */
    private GenericObjectPoolConfig parsePoolConfig(Element serverElement) {
        GenericObjectPoolConfig config = new GenericObjectPoolConfig();
        config.setMaxWaitMillis(2000L);
        Element poolConfigTag = serverElement.element("poolConfig");
        if (poolConfigTag != null) {
            String maxIdle = poolConfigTag.elementTextTrim("maxIdle");
            String minIdle = poolConfigTag.elementTextTrim("minIdle");
            String maxTotal = poolConfigTag.elementTextTrim("maxTotal");
            String maxWait = poolConfigTag.elementTextTrim("maxWait");
            String testOnBorrow = poolConfigTag.elementTextTrim("testOnBorrow");
            String testOnReturn = poolConfigTag.elementTextTrim("testOnReturn");
            String testWhileIdle = poolConfigTag.elementTextTrim("testWhileIdle");
            String timeBetweenEvictionRunsMillis = poolConfigTag.elementTextTrim("timeBetweenEvictionRunsMillis");
            String numTestsPerEvictionRun = poolConfigTag.elementTextTrim("numTestsPerEvictionRun");
            String minEvictableIdleTimeMillis = poolConfigTag.elementTextTrim("minEvictableIdleTimeMillis");
            String softMinEvictableIdleTimeMillis = poolConfigTag.elementTextTrim("softMinEvictableIdleTimeMillis");
            String lifo = poolConfigTag.elementTextTrim("lifo");
            if (!StringUtils.isBlank(maxIdle)) {
                config.setMaxIdle(Integer.valueOf(maxIdle));
            }
            if (!StringUtils.isBlank(minIdle)) {
                config.setMinIdle(Integer.valueOf(minIdle));
            }
            if (!StringUtils.isBlank(maxTotal)) {
                config.setMaxTotal(Integer.valueOf(maxTotal));
            }
            if (!StringUtils.isBlank(maxWait)) {
                config.setMaxWaitMillis(Long.valueOf(maxWait));
            }
            if (!StringUtils.isBlank(lifo)) {
                config.setLifo(Boolean.valueOf(lifo));
            }
            setTestParameters(config, testOnBorrow, testOnReturn, testWhileIdle,
                    timeBetweenEvictionRunsMillis, numTestsPerEvictionRun, minEvictableIdleTimeMillis,
                    softMinEvictableIdleTimeMillis);
        }
        return config;
    }

    /**验证连接可用的参数设置
     * @author 潘洋波[panybest1991@gmail.com]
     * @Date 2017/6/27 17:45
     * @Description ConfigManager
     * @version 4.0.0
     */
    private void setTestParameters(GenericObjectPoolConfig config, String testOnBorrow, String testOnReturn, String testWhileIdle, String timeBetweenEvictionRunsMillis, String numTestsPerEvictionRun, String minEvictableIdleTimeMillis, String softMinEvictableIdleTimeMillis) {
        if (!StringUtils.isBlank(testOnBorrow)) {
            // 获取连接池是否检测可用
            config.setTestOnBorrow(Boolean.valueOf(testOnBorrow));
        }

        if (!StringUtils.isBlank(testOnReturn)) {
            // 归还时是否检测可用
            config.setTestOnReturn(Boolean.valueOf(testOnReturn));
        }
        if (!StringUtils.isBlank(testWhileIdle)) {
            // 空闲时是否检测可用
            config.setTestWhileIdle(Boolean.valueOf(testWhileIdle));
        } else {
            config.setTestWhileIdle(true);
        }
        if (!StringUtils.isBlank(timeBetweenEvictionRunsMillis)) {
            config.setTimeBetweenEvictionRunsMillis(Long.valueOf(timeBetweenEvictionRunsMillis));
        } else {
            config.setTimeBetweenEvictionRunsMillis(30000L);
        }
        if (!StringUtils.isBlank(numTestsPerEvictionRun)) {
            config.setNumTestsPerEvictionRun(Integer.valueOf(numTestsPerEvictionRun));
        } else {
            config.setNumTestsPerEvictionRun(-1);
        }
        if (!StringUtils.isBlank(minEvictableIdleTimeMillis)) {
            config.setMinEvictableIdleTimeMillis(Integer.valueOf(minEvictableIdleTimeMillis));
        } else {
            config.setMinEvictableIdleTimeMillis(3600000L);
        }
        if (!StringUtils.isBlank(softMinEvictableIdleTimeMillis)) {
            config.setSoftMinEvictableIdleTimeMillis(Integer.valueOf(softMinEvictableIdleTimeMillis));
        }else{
            config.setSoftMinEvictableIdleTimeMillis(3600000L);
        }
    }

    public List<ShardInfo4Asura> getLstShardInfo4Asura() {
        return lstShardInfo4Asura;
    }

    public void setConfig(String config) {
    }

}
