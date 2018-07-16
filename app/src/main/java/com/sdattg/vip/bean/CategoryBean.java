package com.sdattg.vip.bean;

public class CategoryBean {
    public String categoryHashName;
    public int id;
    public int categoryInsertOrder;
    public String categoryName;
    public String categoryPath;
    public String categoryListFilesName;
    public String categoryJieshao;
    public String categoryContent;


    @Override
    public String toString() {
        return "CategoryBean{" +
                "categoryHashName='" + categoryHashName + '\'' +
                ", id=" + id +
                ", categoryInsertOrder='" + categoryInsertOrder + '\'' +
                ", categoryName='" + categoryName + '\'' +
                ", categoryPath='" + categoryPath + '\'' +
                ", categoryListFilesName='" + categoryListFilesName + '\'' +
                ", categoryJieshao='" + categoryJieshao + '\'' +
                ", categoryContent='" + categoryContent + '\'' +
                '}';
    }
}
