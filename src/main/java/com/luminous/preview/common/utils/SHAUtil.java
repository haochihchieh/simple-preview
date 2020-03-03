package com.luminous.preview.common.utils;

import lombok.extern.slf4j.Slf4j;
import sun.misc.BASE64Encoder;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;

/**
 * @Auther: haozhijie
 * @Date: 2019/10/18 15:13
 * @Description: SHA-256计算文件的hash值
 */

@Slf4j
public class SHAUtil {
    /**
     * 获取文件hash值
     * @param fileName
     * @return string
     */
    public static String SHAHashCode(String fileName) {
        BufferedInputStream bis = null;
        try {

            byte[] buffer = new byte[8192];
            int count;
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            bis = new BufferedInputStream(new FileInputStream(fileName));
            while ((count = bis.read(buffer)) > 0) {
                digest.update(buffer, 0, count);
            }
            byte[] hash = digest.digest();
            String hashCode = new BASE64Encoder().encode(hash);
            hashCode = byte2Hex(hash);
            return hashCode;
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return null;
        } finally {
            try {
                if( bis != null) {
                    bis.close();
                }
            }catch (IOException ex) {
                log.error(ex.getMessage());
            }
        }
    }

    /**
     * 将byte转为16进制
     * @param bytes
     * @return
     */
    private static String byte2Hex(byte[] bytes) {
        StringBuffer stringBuffer = new StringBuffer();
        String temp = null;
        for (int i = 0; i < bytes.length; i++) {
            temp = Integer.toHexString(bytes[i] & 0xFF);
            if (temp.length() == 1){
                // 1得到一位的进行补0操作
                stringBuffer.append("0");
            }
            stringBuffer.append(temp);
        }
        return stringBuffer.toString();
    }


}
