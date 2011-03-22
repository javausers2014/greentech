package com.gtech.iarc.base.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


/**
 * A wrapper class for <code>java.security.MessageDigest</code> that provides 
 * additional functionality and convenient methods for using the underlying
 * class functionality.
 */
public class Digester {
    private String hashAlgorithm;
    
    
    /**
     * Computes the hash value of a given String using a preset message digest 
     * algorithm.
     * @param str The string to be hashed
     * @return The hash value in hex-encoded format
     * @throws UndefinedHashAlgorithmError
     */
    public String digest(String str){
        return this.digest(str.getBytes());          
    }
    
    public String digest(byte[] bytes){
        if (hashAlgorithm == null)
            throw new RuntimeException("hashAlgorithm must be initialized");
        MessageDigest md;
        try {
            md = MessageDigest.getInstance(hashAlgorithm);
        } catch (NoSuchAlgorithmException e) {
//            logger.error("NoSuchAlgorithmException caught. The hash algorithm \"" +
//                hashAlgorithm + "\" is not known. Please check Java Cryptography" +
//                " Architecture Specification for valid Hash Algorithm names");
            throw new RuntimeException("Unknown hash algorithm used");
        }
        md.update(bytes);
        byte[] digest = md.digest();
        return decodeHexStr(digest,0,digest.length);
    }
    
    /**
     * Computes the hash value of a given String using the given message digest 
     * algorithm.
     * @param str The string to be hashed
     * @param hashAlgorithm The name of message digest algorithm to be used for encoding
     * @return The hash value in hex-encoded format  
     */
    public String digest(String str, String hashAlgorithm){
        this.hashAlgorithm = hashAlgorithm;
        return digest(str);
    }
    
    /**
     * Converts an array of byte into a hexadecimal-encoded String 
     * @param buf array of bytes that will be encoded
     * @param pos
     * @param len
     * @return
     */
    private static String decodeHexStr(byte buf[], int pos, int len) {
        StringBuffer hex = new StringBuffer();
        while (len-- > 0) {
            byte ch = buf[pos++];
            int d = (ch >> 4) & 0xf;
            hex.append((char) (d >= 10 ? 'a' - 10 + d : '0' + d));
            d = ch & 0xf;
            hex.append((char) (d >= 10 ? 'a' - 10 + d : '0' + d));
        }
        return hex.toString();
    }

    public String getHashAlgorithm() {
        return hashAlgorithm;
    }

    public void setHashAlgorithm(String string) {
        hashAlgorithm = string;
    }
    
    /**
     * pa55word
     * 22665f9cd19cc9946cf921623d4dcab834b221e4
     * pa55w0rd
     * b644c3042fbed226b2c1a8250c4bc7b1178f80b1
     * @param arg
     */
    public static void main(String arg[]){
        Digester digest = new Digester();
        digest.setHashAlgorithm("SHA");
        System.out.println(digest.digest("pa55w0rd"));
    }
}
