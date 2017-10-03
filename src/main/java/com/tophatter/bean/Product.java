package com.tophatter.bean;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by xianbin.yang on 2017/10/2.
 */
@Getter
@Setter
@Builder
public class Product {

  private String productCategory;
  private String productTitle;
  private String productDescription;
  private String productCondition;
  private String productBrand;
  private String productMaterial;
  private String variationSize;
  private String variationColor;
  private String variationQuantity;
  private String startingBid;
  private String buyNowPrice;
  private String retailPrice;
  private String targetPrice;
  private String shippingPrice;
  private String shipsFromCountry;
  private String ShippingWeightInOunces;
  private String daysToProcessOrder;
  private String daysToDeliver;
  private String expeditedShippingPrice;
  private String expeditedDeliveryTime;
  private String buyOneGetOnePrice;
  private String accessoryPrice;
  private String accessoryDescription;
  private String primaryUrl;
  private String img1;
  private String img2;
  private String img3;
  private String img4;
  private String status;
  private String bought;

}
