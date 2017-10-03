package com.tophatter.utils;

import static com.tophatter.common.Constants.BUY_NOW_PRICE_WITH_SYMBOL;
import static com.tophatter.common.Constants.CONDITION;
import static com.tophatter.common.Constants.COUNTRY;
import static com.tophatter.common.Constants.DESCRIPTION;
import static com.tophatter.common.Constants.ESTIMATED_DAYS_TO_DELIVER;
import static com.tophatter.common.Constants.IMAGE_URLS;
import static com.tophatter.common.Constants.PRODUCT_BRAND;
import static com.tophatter.common.Constants.PRODUCT_COLOR;
import static com.tophatter.common.Constants.PRODUCT_MATERIAL;
import static com.tophatter.common.Constants.RETAIL_PRICE_WITH_SYMBOL;
import static com.tophatter.common.Constants.SHIPPING_PRICE_WITH_PARTIAL_SYMBOL;
import static com.tophatter.common.Constants.SIZE;
import static com.tophatter.common.Constants.STARTING_BID_AMOUNT_LOCAL;
import static com.tophatter.common.Constants.TITLE;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.csvreader.CsvWriter;
import com.tophatter.bean.Product;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xianbin.yang on 2017/10/2.
 */
public class Utils {


  private static Map<String, String> map = new LinkedHashMap<>();

  static {
    map.put("Product Unique ID", "");
    map.put("Product Category", "productCategory");
    map.put("Product Title", "productTitle");
    map.put("Product Description", "productDescription");
    map.put("Product Condition", "productCondition");
    map.put("Product Brand", "productBrand");
    map.put("Product Material", "productMaterial");
    map.put("Variation Unique ID", "");
    map.put("Variation Size", "variationSize");
    map.put("Variation Color", "variationColor");
    map.put("Variation Quantity", "variationQuantity");
    map.put("Starting Bid", "startingBid");
    map.put("Buy Now Price", "buyNowPrice");
    map.put("Retail Price", "retailPrice");
    map.put("Target Price", "targetPrice");
    map.put("Shipping Price", "shippingPrice");
    map.put("Ships From Country", "shipsFromCountry");
    map.put("Shipping Weight In Ounces", "ShippingWeightInOunces");
    map.put("Days To Process Order", "daysToProcessOrder");
    map.put("Days To Deliver", "daysToDeliver");
    map.put("Expedited Shipping Price", "expeditedShippingPrice");
    map.put("Expedited Delivery Time", "expeditedDeliveryTime");
    map.put("Buy One Get One Price", "buyOneGetOnePrice");
    map.put("Accessory Price", "accessoryPrice");
    map.put("Accessory Description", "accessoryDescription");
    map.put("Primary Image URL", "primaryUrl");
    map.put("Extra Image URL 1", "img1");
    map.put("Extra Image URL 2", "img2");
    map.put("Extra Image URL 3", "img3");
    map.put("Extra Image URL 4", "img4");
    map.put("Status", "status");
    map.put("bought", "bought");
  }

  public static void mkdir(String dirPath) {
    File dir = new File(dirPath);
    if (!dir.exists()) {
      dir.mkdirs();
    }
  }

  public static void writeCsv(List<Product> products, String dest) throws Exception {
    CsvWriter csvWriter = new CsvWriter(dest, ',', Charset.forName("UTF-8"));
    String[] headers = map.keySet().toArray(new String[]{});
    csvWriter.writeRecord(headers);
    products.stream().forEach(product -> {
      try {
        JSONObject json = (JSONObject) JSON.toJSON(product);
        String[] record = Arrays.stream(headers).map(header -> {
          String field = map.get(header);
          return json.getString(field);
        }).toArray(String[]::new);
        csvWriter.writeRecord(record);
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    });
    csvWriter.flush();
    csvWriter.close();
  }

  public static Product json2Product(JSONObject details) throws Exception {
    Product product = Product.builder()
        .productTitle(details.getString(TITLE))
        .productDescription(details.getString(DESCRIPTION))
        .productCondition(details.getString(CONDITION))
        .productBrand(details.getString(PRODUCT_BRAND))
        .productMaterial(details.getString(PRODUCT_MATERIAL))
        .startingBid(details.getString(STARTING_BID_AMOUNT_LOCAL))
        .buyNowPrice(details.getString(BUY_NOW_PRICE_WITH_SYMBOL))
        .retailPrice(details.getString(RETAIL_PRICE_WITH_SYMBOL))
        .shippingPrice(details.getString(SHIPPING_PRICE_WITH_PARTIAL_SYMBOL))
        .shipsFromCountry(details.getString(COUNTRY))
        .daysToDeliver(details.getString(ESTIMATED_DAYS_TO_DELIVER))
        .productCondition(details.getString(CONDITION))
        .variationSize(details.getString(SIZE))
        .variationColor(details.getString(PRODUCT_COLOR))
        .variationSize(details.getString(SIZE))
        .variationSize(details.getString(SIZE))
        .build();
    JSONArray imgs = details.getJSONArray(IMAGE_URLS);
    for (int i = 1; i < imgs.size(); i++) {
      Product.class.getDeclaredMethod("setImg" + i, String.class)
          .invoke(product, imgs.get(i));
    }
    return product;
  }

}
