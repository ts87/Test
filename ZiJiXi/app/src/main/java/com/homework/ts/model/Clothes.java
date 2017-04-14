package com.homework.ts.model;

/**
 * Created by ts on 2017/4/14.
 */

public class Clothes {
    private String clothesName;
    private String clothesPrice;
    private String clothesImage;

    public Clothes(String clothesName, String clothesPrice, String clothesImage){
        this.clothesImage = clothesImage;
        this.clothesName = clothesName;
        this.clothesPrice = clothesPrice;
    }

    public String getClothesName() {
        return clothesName;
    }

    public void setClothesName(String clothesName) {
        this.clothesName = clothesName;
    }

    public String getClothesPrice() {
        return clothesPrice;
    }

    public void setClothesPrice(String clothesPrice) {
        this.clothesPrice = clothesPrice;
    }

    public String getClothesImage() {
        return clothesImage;
    }

    public void setClothesImage(String clothesImage) {
        this.clothesImage = clothesImage;
    }
}
