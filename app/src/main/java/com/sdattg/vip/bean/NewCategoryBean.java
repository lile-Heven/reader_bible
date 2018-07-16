package com.sdattg.vip.bean;

/**
 *  20180716
 *  eg: NewCategoryBean{, name='01-圣经', path='/storage/emulated/0/lxgj/unzip/01-书库/01-圣经', parentPath='/storage/emulated/0/lxgj/unzip/01-书库'}
 *      NewCategoryBean{, name='02-怀著', path='/storage/emulated/0/lxgj/unzip/01-书库/02-怀著', parentPath='/storage/emulated/0/lxgj/unzip/01-书库'}
 */
public class NewCategoryBean {
    public String name;
    public String path;
    public String parentPath;

    @Override
    public String toString() {
        return "NewCategoryBean{" +
                ", name='" + name + '\'' +
                ", path='" + path + '\'' +
                ", parentPath='" + parentPath + '\'' +
                '}';
    }
}
