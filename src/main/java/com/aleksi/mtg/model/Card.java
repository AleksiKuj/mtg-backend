package com.aleksi.mtg.model;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Document(collection = "fullcards")
public class Card {
    private String name;
    private String manaCost;
    private List<String> colors;
    private String type;
    private String rarity;
    private String set;
    private String setName;
    private String text;
    private String flavor;
    private String power;
    private String toughness;
    private String imageUrl;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    private Date date;

    private Map<String, String> attributeCorrectness = new HashMap<>();

    public void setAttributeCorrectness(String attribute, String status) {
        attributeCorrectness.put(attribute, status);
    }

    public Map<String, String> getAttributeCorrectness() {
        return attributeCorrectness;
    }

    public int getCmc() {
        return cmc;
    }

    public void setCmc(int cmc) {
        this.cmc = cmc;
    }

    public List<String> getSubtypes() {
        return subtypes;
    }

    public void setSubtypes(List<String> subtypes) {
        this.subtypes = subtypes;
    }

    public List<String> getSupertypes() {
        return supertypes;
    }

    public void setSupertypes(List<String> supertypes) {
        this.supertypes = supertypes;
    }

    public List<String> getTypes() {
        return types;
    }

    public void setTypes(List<String> types) {
        this.types = types;
    }

    private int cmc;
    private List<String> subtypes;
    private List<String> supertypes;
    private List<String> types;

    public String getFlavor() {
        return flavor;
    }

    public void setFlavor(String flavor) {
        this.flavor = flavor;
    }


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
                ", flavor='" + flavor + '\'' +
                ", power='" + power + '\'' +
                ", toughness='" + toughness + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", date=" + date +
                ", attributeCorrectness=" + attributeCorrectness +
                ", cmc=" + cmc +
                ", subtypes=" + subtypes +
                ", supertypes=" + supertypes +
                ", types=" + types +
                '}';
    }
}
