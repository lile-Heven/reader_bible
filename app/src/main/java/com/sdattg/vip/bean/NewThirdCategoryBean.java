package com.sdattg.vip.bean;

/**
 *  20180716
 *  eg: NewCategoryBean{, name='01-创世记', path='/storage/emulated/0/lxgj/unzip/01-书库/01-旧约/01-创世记', parentPath='/storage/emulated/0/lxgj/unzip/01-书库/01-旧约'}
 *      NewCategoryBean{, name='02-利未记', path='/storage/emulated/0/lxgj/unzip/01-书库/01-旧约/02-利未记', parentPath='/storage/emulated/0/lxgj/unzip/01-书库/01-旧约'}
 */
public class NewThirdCategoryBean {
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
