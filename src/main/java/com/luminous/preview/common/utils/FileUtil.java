package com.luminous.preview.common.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.URLDecoder;
import java.nio.charset.Charset;

/**
 * @Auther: haozhijie
 * @Date: 2019/10/21 16:20
 * @Description: 文件操作类
 */

@Slf4j
public class FileUtil {
    /**
     * 获取文件名
     *
     * @param filePath
     * @return
     */
    public static String getFileName(String filePath) {
        File tempFile = new File(filePath.trim());
        String fileName = tempFile.getName();
        return fileName;
    }

    /**
     * 获取文件hash值
     *
     * @param filePath
     * @return
     */
    public static String getFileHashCode(String filePath) {
        String hashCode = SHAUtil.SHAHashCode(filePath);
        return hashCode;
    }

    /**
     * 获取文件大小
     *
     * @param filePath
     * @return
     */
    public static long getFileSize(String filePath) {
        File file = new File(filePath);
        if (file.exists() && file.isFile()) {
            return file.length();
        } else {
            return 0;
        }
    }

    /**
     * 创建文件目录
     *
     * @param destDirName
     * @return
     */
    public static File createDir(String destDirName) {
        File dir = new File(destDirName);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dir;
    }

    /**
     * 文件复制
     *
     * @param sourcePath
     * @param destPath
     */
    public static void copyFile(String sourcePath, String destPath) {
        InputStream fis = null;
        OutputStream fos = null;
        try {
            File source = FileUtil.createFile(sourcePath);
            int flag = 0;
            if (sourcePath.contains(File.separator)) {
                flag = sourcePath.lastIndexOf(File.separator);
            }
            String txts = sourcePath.substring(flag);
            File target = FileUtil.createFile(destPath + File.separator + txts);
            fis = new FileInputStream(source);
            fos = new FileOutputStream(target);
            byte[] buf = new byte[4096];
            int i;
            while ((i = fis.read(buf)) != -1) {
                fos.write(buf, 0, i);
            }
        } catch (Exception ex) {
            log.error(ex.getMessage());
        } finally {
            try {
                if (fos != null) {
                    fos.flush();
                    fos.close();
                }
                if (fis != null) {
                    fis.close();
                }
            } catch (IOException ex) {
                log.error(ex.getMessage());
            }
        }
    }

    /**
     * 文件按指定编码复制
     *
     * @param inputFile
     * @param inputCharset
     * @param outputFile
     * @param outputCharset
     * @throws Exception
     */
    public static void copyFile(String inputFile, String inputCharset,
                                String outputFile, String outputCharset) throws Exception {
        Reader reader = new InputStreamReader(new FileInputStream(new File(
                inputFile)), inputCharset);
        Writer writer = new OutputStreamWriter(new FileOutputStream(new File(
                outputFile)), outputCharset);
        int temp = 0;
        try {
            while ((temp = reader.read()) != -1) {
                writer.write(temp);
            }
        } catch (IOException ex) {
            log.error(ex.getMessage());
        } finally {
            try {
                reader.close();
                writer.close();
            } catch (IOException ex) {
                log.error(ex.getMessage());
            }
        }
    }

    /**
     * 创建文件
     *
     * @param filePath
     */
    public static File createFile(String filePath) {
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                // 先得到文件的上级目录，并创建上级目录，在创建文件
                file.getParentFile().mkdir();
                file.createNewFile();
            }
            return file;
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }
        return null;
    }


    /**
     * 获取文件扩展名
     *
     * @param fileName
     * @return String
     */
    public static String getFileExtension(String fileName) {
        int splitIndex = fileName.lastIndexOf('.');
        return fileName.substring(splitIndex + 1);
    }

    /**
     * 判断文件编码格式
     *
     * @param path
     * @return
     */
    public static String getFileEncodeUTFGBK(String path) {
        String enc = Charset.forName("GBK").name();
        File file = new File(path);
        InputStream in = null;
        try {
            in = new FileInputStream(file);
            byte[] b = new byte[3];
            in.read(b);
            if (b[0] == -17 && b[1] == -69 && b[2] == -65) {
                enc = Charset.forName("UTF-8").name();
            }
        } catch (FileNotFoundException ex) {
            log.error(ex.getMessage());
        } catch (IOException ex) {
            log.error(ex.getMessage());
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                log.error(ex.getMessage());
            }
        }
        System.out.println("文件编码格式为:" + enc);
        return enc;
    }

    /**
     * 查看文件类型(防止参数中存在.点号或者其他特殊字符，所以先抽取文件名，然后再获取文件类型)
     *
     * @param url
     * @return
     */
    public static String getFileExtensionFromUrl(String url) {
        int lastPoint = url.lastIndexOf('.');
        String extension = url.substring(lastPoint + 1);
        return extension;
    }

    public static String getFileNameFromUrl(String url) {
        String fileName = "";
        int lastPoint = url.lastIndexOf('.');
        if (lastPoint > 0) {
            int lastSlash = url.lastIndexOf('/');
            int lastEqual = url.lastIndexOf('=');
            if (lastEqual > 0 && lastEqual > lastSlash) {
                fileName = url.substring(lastEqual + 1);
                fileName = URLDecoder.decode(fileName);
            } else if (lastSlash > 0) {
                fileName = url.substring(lastSlash + 1);
                fileName = URLDecoder.decode(fileName);
            }
            return fileName;
        } else {
            throw new RuntimeException("file name or type is not find!");
        }

    }
}
