package com.goutamrestaurant.beanmodelhelper;

import java.io.Serializable;

/**
 * Created by Bubun Goutam on 6/19/2016.
 */
public class FoodCourtModel{
    String foodCourtId;
    String foodCourtName;
    String foodCourtAddress;
    String foodCourtOffer;
    String foodCourtURL;

    public FoodCourtModel(String foodCourtId, String foodCourtName, String foodCourtAddress, String foodCourtOffer, String foodCourtURL) {
        this.foodCourtId = foodCourtId;
        this.foodCourtName = foodCourtName;
        this.foodCourtAddress = foodCourtAddress;
        this.foodCourtOffer = foodCourtOffer;
        this.foodCourtURL = foodCourtURL;
    }

    public String getFoodCourtId() {
        return foodCourtId;
    }

    public void setFoodCourtId(String foodCourtId) {
        this.foodCourtId = foodCourtId;
    }

    public String getFoodCourtName() {
        return foodCourtName;
    }

    public void setFoodCourtName(String foodCourtName) {
        this.foodCourtName = foodCourtName;
    }

    public String getFoodCourtAddress() {
        return foodCourtAddress;
    }

    public void setFoodCourtAddress(String foodCourtAddress) {
        this.foodCourtAddress = foodCourtAddress;
    }

    public String getFoodCourtOffer() {
        return foodCourtOffer;
    }

    public void setFoodCourtOffer(String foodCourtOffer) {
        this.foodCourtOffer = foodCourtOffer;
    }

    public String getFoodCourtURL() {
        return foodCourtURL;
    }

    public void setFoodCourtURL(String foodCourtURL) {
        this.foodCourtURL = foodCourtURL;
    }
}
