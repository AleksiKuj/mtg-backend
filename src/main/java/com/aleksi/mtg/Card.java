package com.aleksi.mtg;

import java.util.List;

public class Card {
    private String name;
    private List<String> names;
    private String manaCost;
    private int cmc;
    private List<String> colors;
    private String type;
    private List<String> supertypes;
    private List<String> types;
    private List<String> subtypes;
    private String rarity;
    private String set;
    private String setName;
    private String text;
    private String artist;
    private String number;
    private String power;
    private String toughness;

    public String getOriginalText() {
        return originalText;
    }

    public void setOriginalText(String originalText) {
        this.originalText = originalText;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String originalText;
    private String id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getNames() {
        return names;
    }

    public void setNames(List<String> names) {
        this.names = names;
    }

    public String getManaCost() {
        return manaCost;
    }

    public void setManaCost(String manaCost) {
        this.manaCost = manaCost;
    }

    public int getCmc() {
        return cmc;
    }

    public void setCmc(int cmc) {
        this.cmc = cmc;
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

    public List<String> getSubtypes() {
        return subtypes;
    }

    public void setSubtypes(List<String> subtypes) {
        this.subtypes = subtypes;
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

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
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

    public String getLayout() {
        return layout;
    }

    public void setLayout(String layout) {
        this.layout = layout;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    private String layout;
    private String imageUrl;

    @Override
    public String toString() {
        return "Card{" +
                "name='" + name + '\'' +
                ", names=" + names +
                ", manaCost='" + manaCost + '\'' +
                ", cmc=" + cmc +
                ", colors=" + colors +
                ", type='" + type + '\'' +
                ", supertypes=" + supertypes +
                ", types=" + types +
                ", subtypes=" + subtypes +
                ", rarity='" + rarity + '\'' +
                ", set='" + set + '\'' +
                ", setName='" + setName + '\'' +
                ", text='" + text + '\'' +
                ", artist='" + artist + '\'' +
                ", number='" + number + '\'' +
                ", power='" + power + '\'' +
                ", toughness='" + toughness + '\'' +
                ", layout='" + layout + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }
}
