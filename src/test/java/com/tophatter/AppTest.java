package com.tophatter;

import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.jsoup.Jsoup;

/**
 * Unit test for simple App.
 */
public class AppTest {

  public static void main(String[] args) throws Exception {

    String url = "http://zhidao.baidu.com/daily";
    String contents = Jsoup.connect(url).post().html();

    HtmlCleaner hc = new HtmlCleaner();
    TagNode tn = hc.clean(contents);
    String xpath = "//h2/a/@href";
    Object[] objects = tn.evaluateXPath(xpath);
    System.out.println(objects.length);

//
//    String url = "https://tophatter.com/catalogs/9200";
//    String contents = Jsoup.connect(url).post().html();
//
//    HtmlCleaner hc = new HtmlCleaner();
//    TagNode tn = hc.clean(contents);
//    String xpath = "//*[@id=\"grid\"]/div[1]/div[1]/div[1]/img";
//    Object[] objects = tn.evaluateXPath(xpath);
//    System.out.println(objects.length);
  }


}
