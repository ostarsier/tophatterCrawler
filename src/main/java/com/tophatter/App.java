package com.tophatter;

import static com.tophatter.common.Constants.BASE_PATH;
import static com.tophatter.common.Constants.CATEGORY_PATH;
import static com.tophatter.common.Constants.DETAILS_URL;
import static com.tophatter.common.Constants.MAIN_URL;

import com.alibaba.fastjson.JSONObject;
import com.tophatter.bean.Product;
import com.tophatter.utils.HttpUtil;
import com.tophatter.utils.Utils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;


public class App {

  private static Map<String, String> map = new HashMap<>();

  static {
    map.put("9201", "Rings");
    map.put("9202", "Necklaces");
    map.put("9203", "Bracelets");
    map.put("9204", "Earrings");
    map.put("9205", "Other");
    map.put("9206", "Sets");
  }

  public static void main(String[] args){
    Utils.mkdir(BASE_PATH);
    ExecutorService service = Executors.newCachedThreadPool();
    map.keySet().forEach(categoryId -> {
      service.submit(new Task(categoryId));
    });
  }

  @AllArgsConstructor
  static class Task implements Runnable {

    private String categoryId;

    @Override
    public void run() {
      try {
        String categoryName = map.get(categoryId);
        int page = 1;
        List products = new ArrayList();
        while (true) {
          String url = String.format(MAIN_URL, categoryId, page++);
          System.out.println(url);
          String mainPage = HttpUtil.getData(url);
          Document document = Jsoup.parse(mainPage);
          Elements elements = document.getElementsByAttributeValue("data-catalog-id", categoryId);
          //TODO 测试
          if (page == 3) {
            break;
          }
          //后面的页都没有数据了，或者防爬虫，用代理再试几遍
          if (elements.isEmpty()) {
            break;
          }
          //TODO limit(3) 测试
          elements.stream().limit(3).forEach(element -> {
            try {
              String productId = element.getElementsByAttribute("data-lot-id").get(0)
                  .attr("data-lot-id");
              Elements boughtElement = element.getElementsByClass("bought-this");
              if (boughtElement.isEmpty()) {
                return;
              }
              String bought = boughtElement.get(0).text().trim().replace(" bought this", "");
//              if (!bought.contains("k")) {
//                return;
//              }
              String reminder = "0";
              Elements reminderElement = element.getElementsByClass("reminder-count");
              if (!reminderElement.isEmpty()) {
                reminder = reminderElement.get(0).text().trim();
              }
              JSONObject details = JSONObject
                  .parseObject(HttpUtil.getData(String.format(DETAILS_URL, productId))
                      .replaceAll("thumbnail.jpg", "large.jpg"));
              Product product = Utils.json2Product(details);
              product.setBought(bought);
              product.setProductCategory("Jewelry | " + categoryName);
              products.add(product);
            } catch (Exception e) {
              throw new RuntimeException(e);
            }
          });
        }
        String categoryFile = String.format(CATEGORY_PATH, categoryName);
        Utils.writeCsv(products, categoryFile);
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }
  }

}
