package com.aleksi.mtg.model;

import java.util.List;

public class Card {
    private String name;
    private String manaCost;
    private List<String> colors;
    private String type;
    private String rarity;
    private String set;
    private String setName;
    private String text;
    private String power;
    private String toughness;
    private String imageUrl;

    public String getFlavor() {
        return flavor;
    }

    public void setFlavor(String flavor) {
        this.flavor = flavor;
    }

    private String flavor;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getManaCost() {
        return manaCost;
    }

    public void setManaCost(String manaCost) {
        this.manaCost = manaCost;
    }


    public List<String> getColors() {
        return colors;
    }

    public void setColors(List<String> colors) {
        this.colors = colors;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRarity() {
        return rarity;
    }

    public void setRarity(String rarity) {
        this.rarity = rarity;
    }

    public String getSet() {
        return set;
    }

    public void setSet(String set) {
        this.set = set;
    }

    public String getSetName() {
        return setName;
    }

    public void setSetName(String setName) {
        this.setName = setName;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getPower() {
        return power;
    }

    public void setPower(String power) {
        this.power = power;
    }

    public String getToughness() {
        return toughness;
    }

    public void setToughness(String toughness) {
        this.toughness = toughness;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public String toString() {
        return "Card{" +
                "name='" + name + '\'' +
                ", manaCost='" + manaCost + '\'' +
                ", colors=" + colors +
                ", type='" + type + '\'' +
                ", rarity='" + rarity + '\'' +
                ", set='" + set + '\'' +
                ", setName='" + setName + '\'' +
                ", text='" + text + '\'' +
                ", power='" + power + '\'' +
                ", toughness='" + toughness + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }
}
