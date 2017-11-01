package com.panyangbo.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.URL;
import java.util.Properties;



/**
 * 
 * 
 * 功能描述�?获取classpath下配置文�? * 
 * @author 
 * @version 1.0.0
 */
public class ResourceUtils extends Object {
    private static Logger logger = LoggerFactory.getLogger(ResourceUtils.class);

    /**
     * 
     * 功能描述: Returns the URL of the resource on the classpath
     * 
     * @param resource The resource to find
     * @return The resource
     * @throws IOException
     */
    public static URL getResourceURL(String resource) throws IOException {
        URL url = null;
        ClassLoader loader = ResourceUtils.class.getClassLoader();
        if (loader != null) {
            url = loader.getResource(resource);
        }
        if (url == null) {
            url = ClassLoader.getSystemResource(resource);
        }
        if (url == null) {
            throw new IOException("Could not find resource " + resource);
        }
        return url;
    }

    /**
     * 
     * 功能描述: Returns the URL of the resource on the classpath
     * 
     * @param loader The classloader used to load the resource
     * @param resource The resource to find
     * @return The resource
     * @throws IOException If the resource cannot be found or read
     */
    public static URL getResourceURL(ClassLoader loader, String resource) throws IOException {
        URL url = null;
        if (loader != null) {
            url = loader.getResource(resource);
        }
        if (url == null) {
            url = ClassLoader.getSystemResource(resource);
        }
        if (url == null) {
            throw new IOException("Could not find resource " + resource);
        }
        return url;
    }

    /**
     * 功能描述:Returns a resource on the classpath as a Stream object<br>
     * 
     * @param resource The resource to find
     * @throws IOException If the resource cannot be found or read
     * @return The resource
     */
    public static InputStream getResourceAsStream(String resource) throws IOException {
        InputStream in = null;
        ClassLoader loader = ResourceUtils.class.getClassLoader();
        if (loader != null) {
            in = loader.getResourceAsStream(resource);
        }
        if (in == null) {
            in = ClassLoader.getSystemResourceAsStream(resource);
        }
        if (in == null) {
            throw new IOException("Could not find resource " + resource);
        }
        return in;
    }

    /**
     * 功能描述:Returns a resource on the classpath as a Stream object
     * 
     * @param loader The classloader used to load the resource
     * @param resource The resource to find
     * @throws IOException If the resource cannot be found or read
     * @return The resource
     */
    public static InputStream getResourceAsStream(ClassLoader loader, String resource) {
        InputStream in = null;
        if (loader != null) {
            in = loader.getResourceAsStream(resource);
        }
        if (in == null) {
            in = ClassLoader.getSystemResourceAsStream(resource);
        }
        if (in == null) {
            throw new RuntimeException("Could not find resource " + resource);
        }
        return in;
    }

    /**
     * 功能描述:Returns a resource on the classpath as a Properties object
     * 
     * @param resource The resource to find
     * @throws IOException If the resource cannot be found or read
     * @return The resource
     */
    public static Properties getResourceAsProperties(String resource) {
        Properties props = new Properties();
        InputStream in = null;
        String propfile = resource;
        try {
            in = getResourceAsStream(propfile);
            props.load(in);
            return props;
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    logger.error(e.getMessage());
                }
            }
        }

    }

    /**
     * 功能描述:Returns a resource on the classpath as a Properties object
     * 
     * @param loader The classloader used to load the resource
     * @param resource The resource to find
     * @throws IOException If the resource cannot be found or read
     * @return The resource
     */
    public static Properties getResourceAsProperties(ClassLoader loader, String resource) {
        Properties props = new Properties();
        InputStream in = null;
        String propfile = resource;
        try {
            in = getResourceAsStream(loader, propfile);
            props.load(in);
            return props;
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    logger.error(e.getMessage());
                }
            }
        }

    }

    /**
     * 功能描述:Returns a resource on the classpath as a Reader object
     * 
     * @param resource The resource to find
     * @throws IOException If the resource cannot be found or read
     * @return The resource
     */
    public static InputStreamReader getResourceAsReader(String resource) {
        try {
            return new InputStreamReader(getResourceAsStream(resource), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 功能描述:Returns a resource on the classpath as a Reader object
     * 
     * @param loader The classloader used to load the resource
     * @param resource The resource to find
     * @throws IOException If the resource cannot be found or read
     * @return The resource
     */
    public static Reader getResourceAsReader(ClassLoader loader, String resource) {
        try {
            // 支持UTF-8的编�?            
        	return new InputStreamReader(getResourceAsStream(loader, resource), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 功能描述:Returns a resource on the classpath as a File object
     * 
     * @param resource The resource to find
     * @throws IOException If the resource cannot be found or read
     * @return The resource
     */
    public static File getResourceAsFile(String resource) throws IOException {
        return new File(getResourceURL(resource).getFile());
    }

    /**
     * 功能描述:Returns a resource on the classpath as a File object
     * 
     * @param loader The classloader used to load the resource
     * @param resource The resource to find
     * @throws IOException If the resource cannot be found or read
     * @return The resource
     */
    public static File getResourceAsFile(ClassLoader loader, String resource) throws IOException {
        return new File(getResourceURL(loader, resource).getFile());
    }

}