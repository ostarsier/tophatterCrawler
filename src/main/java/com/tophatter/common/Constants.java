package com.tophatter.common;

import java.io.File;

/**
 * Created by xianbin.yang on 2017/10/2.
 */
public interface Constants {

  Integer THRESHOLD = 1000;
  String BASE_PATH = "D:\\tophatter";
  String CATEGORY_PATH = BASE_PATH + File.separator + "%s.csv";
  String MAIN_URL = "https://tophatter.com/catalogs/%s?page=%s";
  String DETAILS_URL = "https://tophatter.com/api/v1/lots/%s?source=lot-view-catalog";

  String NAME = "name";
  String SIZE = "size";
  String PRODUCT_COLOR = "product_color";
  String IMAGE_URLS = "image_urls";
  String TITLE = "title";
  String DESCRIPTION = "description";
  String CONDITION = "condition";
  String PRODUCT_BRAND = "product_brand";
  String VARIATIONS = "variations";
  String BUY_NOW_PRICE_WITH_SYMBOL = "buy_now_price_with_symbol";
  String RETAIL_PRICE_WITH_SYMBOL = "retail_price_with_symbol";
  String STARTING_BID_AMOUNT_LOCAL = "starting_bid_amount_local";
  String SHIPPING_PRICE_WITH_PARTIAL_SYMBOL = "shipping_price_with_partial_symbol";
  String COUNTRY = "country";
  String ESTIMATED_DAYS_TO_DELIVER = "estimated_days_to_deliver";
  String PRODUCT_MATERIAL = "product_material";
}
