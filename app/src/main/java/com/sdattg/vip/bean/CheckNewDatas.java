package com.sdattg.vip.bean;

import com.sdattg.vip.search.NewCategoryDBHelper;

public class CheckNewDatas {
    public boolean init(NewCategoryDBHelper DBhalper, String rootPath, String tableName) {
        InitDatas init = new InitDatas();
        init.initNewCategoryBean(DBhalper, rootPath, tableName);
        InitDatas.hasAllDone = false;
        InitDatas.onlyUpdateJieShao = false;
        init.initFromBooks(DBhalper);
        DBhalper.close();
        return true;
    }
}
