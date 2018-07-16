package com.sdattg.vip.bean;

/**
 *  20180716
 *  eg: NewCategoryBean{, chapter='00-创世记_jieshao', path='/storage/emulated/0/lxgj/unzip/01-书库/01-旧约/01-创世记/00-创世记_jieshao', parentPath='/storage/emulated/0/lxgj/unzip/01-书库/01-旧约/01-创世记', jieshao='jieshao'}
 *
 */
public class NewBookBean {
    public String chapter;
    public String path;
    public String parentPath;
    public String jieshao;

    @Override
    public String toString() {
        return "NewSecondCategoryBean{" +
                ", chapter='" + chapter + '\'' +
                ", path='" + path + '\'' +
                ", parentPath='" + parentPath + '\'' +
                ", jieshao='" + jieshao + '\'' +
                '}';
    }
}
