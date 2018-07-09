package com.sdattg.vip.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.sdattg.vip.R;
import com.sdattg.vip.adapter.Tab01ProductAdapter;
import com.sdattg.vip.base.BaseFragment;
import com.sdattg.vip.tool.ZipTool;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yinqm on 2018/6/26.
 */
public class Main02FragmentList extends BaseFragment {
    private RecyclerView rv;
    private List<String> list;
        Tab01ProductAdapter adapter;
    @Override
    protected int getLayoutId() {
        return R.layout.main02fragment_list;
    }

    @Override
    protected void addViewLayout(View view) {

    }

    @Override
    protected void initView(View view) {
        list = new ArrayList<>();
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        adapter = new Tab01ProductAdapter(R.layout.recy_item, list, getActivity());
        rv = view.findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv.setAdapter(adapter);
    }

    @Override
    protected void initData() {
        unZip();
    }

    private void unZip(){
        Log.d("Tab01ProductAdapter", ZipTool.APP_DIR);
        isAPP_DIRExists();
        //ZipTool.upZipFileDir();

        //ZipTool.upZipFileDir(checkBooksExists("02-灵修.zip"), ZipTool.APP_DIR_UNZIP);
        ZipTool.upZipFileDir(checkBooksExists("testunzip.zip"), ZipTool.APP_DIR_UNZIP);
    }

    private void isAPP_DIRExists(){
        File appFile = new File(ZipTool.APP_DIR);
        if(!appFile.exists()){
            appFile.mkdirs();
            Log.d("Tab01ProductAdapter", "!appFile.exists()");
        }

        File appUnzipFile = new File(ZipTool.APP_DIR_UNZIP);
        if(!appUnzipFile.exists()){
            appUnzipFile.mkdirs();
            Log.d("Tab01ProductAdapter", "!appUnzipFile.exists()");
        }
        /*File appFile2 = new File("/storage/emulated/0/CloudDrive");
        if(!appFile2.exists()){
            //appFile2.mkdirs();
            Log.d("Tab01ProductAdapter", "!appFile222.exists()");
        }else{
            Log.d("Tab01ProductAdapter", "!appFile222.exists() is false");

        }*/


    }

    private File checkBooksExists(String book){
        String bookFilePath = ZipTool.APP_DIR + "/" + book;
        Log.d("Tab01ProductAdapter", "into checkBooksExists() bookFilePath:" + bookFilePath);
        File bookFile = new File(bookFilePath);
        if(!bookFile.exists()){
            //bookFile.mkdirs();
            //Log.d("Tab01ProductAdapter", "!appUnzipFile.exists()");
            Log.d("Tab01ProductAdapter", "!bookFile.exists() is true");

        }else{

            Log.d("Tab01ProductAdapter", "!bookFile.exists() is false");
        }
        return bookFile;
    }

    @Override
    protected void iniLogic() {

    }

    @Override
    protected void httpRequest() {

    }
}
