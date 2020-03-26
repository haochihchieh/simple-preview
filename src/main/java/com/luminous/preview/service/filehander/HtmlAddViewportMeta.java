package com.luminous.preview.service.filehander;

import com.luminous.preview.common.utils.FileCharsetDetector;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.mozilla.intl.chardet.nsPSMDetector;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
 /**
  *
 　　* @author 郝江波
 　　* @date 2020/3/26 9:24
 　　*/
public class HtmlAddViewportMeta {
    public static void add(File targetFile){
        try {
           FileCharsetDetector.Observer oCharset = FileCharsetDetector.guessFileEncoding(
                    targetFile, nsPSMDetector.CHINESE);
           String encoding =oCharset.getEncoding();
           Document document =  Jsoup.parse(targetFile, encoding);
           Element head = document.getElementsByTag("head").first();
           head.append("<meta name=\"viewport\" content=\"width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=0\"/>");
           targetFile.delete();
           FileOutputStream out =  new FileOutputStream(targetFile);
           out.write(document.toString().getBytes(encoding));
           out.flush();
           out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
