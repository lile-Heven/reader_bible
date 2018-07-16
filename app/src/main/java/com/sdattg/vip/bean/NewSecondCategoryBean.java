package com.sdattg.vip.bean;

/**
 *  20180716
 *  eg: NewCategoryBean{, name='01-旧约', path='/storage/emulated/0/lxgj/unzip/01-书库/01-旧约', parentPath='/storage/emulated/0/lxgj/unzip/01-圣经'}
 *      NewCategoryBean{, name='02-新约', path='/storage/emulated/0/lxgj/unzip/01-书库/02-新约', parentPath='/storage/emulated/0/lxgj/unzip/01-圣经'}
 */
public class NewSecondCategoryBean {
    public String name;
    public String path;
    public String parentPath;

    @Override
    public String toString() {
        return "NewSecondCategoryBean{" +
                ", name='" + name + '\'' +
                ", path='" + path + '\'' +
                ", parentPath='" + parentPath + '\'' +
                '}';
    }
}
