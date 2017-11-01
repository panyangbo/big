package com.panyangbo.util;


import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * murmur哈希
 *
 * @author panyangbo panyangbo
 * @create 2017/6/26
 */


public interface Hashing {
    public static final Hashing MURMUR_HASH = new MurmurHash();
    public ThreadLocal<MessageDigest> md5Holder = new ThreadLocal<MessageDigest>();

    public static final Hashing MD5 = new Hashing() {
        public long hash(String key) {
            byte[] code = new byte[0] ;
            try {
                code = key.getBytes("UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            return hash(code);
        }

        public long hash(byte[] key) {
            try {
                if (md5Holder.get() == null) {
                    md5Holder.set(MessageDigest.getInstance("MD5"));
                }
            } catch (NoSuchAlgorithmException e) {
                throw new IllegalStateException("++++ no md5 algorythm found");
            }
            MessageDigest md5 = md5Holder.get();

            md5.reset();
            md5.update(key);
            byte[] bKey = md5.digest();
            long res = ((long) (bKey[3] & 0xFF) << 24)
                    | ((long) (bKey[2] & 0xFF) << 16)
                    | ((long) (bKey[1] & 0xFF) << 8) | (long) (bKey[0] & 0xFF);
            return res;
        }
    };

    public long hash(String key);

    public long hash(byte[] key);


}
