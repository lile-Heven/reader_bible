package com.sdattg.vip;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.sdattg.vip.base.BaseFragment;
import com.sdattg.vip.local.FileBrowserActivity;
import com.sdattg.vip.local.MyStarAdapter;
import com.sdattg.vip.read.Book;
import com.sdattg.vip.read.BookInfoDao;
import com.sdattg.vip.read.MainReader;
import com.sdattg.vip.read_my.MyReadActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by yinqm on 2018/6/27.
 */
public class MyFragmentBendi extends BaseFragment {
    private String TAG = MyFragmentBendi.class.getSimpleName();
    public static final int FILE_RESULT_CODE = 1;
    private  ArrayList<String> paths;
    private ImageView iv_add;
    private ListView lv;
    private MyStarAdapter myStarAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.mfragment_bendi;
    }

    @Override
    protected void addViewLayout(View view) {

    }

    @Override
    protected void initView(View view) {
        iv_add = (ImageView) view.findViewById(R.id.iv_add);
        iv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), FileBrowserActivity.class);
                intent.putExtra("area", true);
                startActivityForResult(intent, FILE_RESULT_CODE);
            }
        });

        lv = (ListView)view.findViewById(R.id.lv);
        initlv();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void iniLogic() {

    }

    @Override
    protected void httpRequest() {

    }

    private void initlv(){
        paths = new ArrayList<String>();
        myStarAdapter = new MyStarAdapter(getContext(), paths);
        lv.setAdapter(myStarAdapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int position, long l) {
                Log.d(TAG, "int position:" + position);
                AlertDialog dialog = new AlertDialog.Builder(getContext())
                        .setTitle("提示：")
                        .setMessage("点击确定后开始阅读.")
                        .setPositiveButton("确 定", new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //jumpToReadActivity(paths.get(position));
                                jumpToReadActivity2(paths.get(position));
                                //Toast.makeText(FileBrowserActivity.this, "添加成功!", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("删除", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                paths.remove(position);
                                myStarAdapter.notifyDataSetChanged();
                                pathsChanged();
                            }
                        })
                        .create();
                dialog.show();
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.RED);
            }
        });

        Log.d(TAG, "findbug8" );
        initPaths();
        Log.d(TAG, "findbug9" );
        myStarAdapter.notifyDataSetChanged();
        Log.d(TAG, "findbug10" );
    }

    public void add(String path){
        paths.add(path);
        myStarAdapter.notifyDataSetChanged();
        pathsChanged();
    }

    public boolean contains(String path){
        return paths.contains(path);
    }

    private void initPaths(){
        Log.d(TAG, "findbug1" );
        SharedPreferences sharedPreferences = mActivity.getSharedPreferences(mActivity.getPackageName(), Context.MODE_PRIVATE);
        String stringAll = sharedPreferences.getString("stringAll", "nothing");
        Log.d(TAG, "findbug2" );
        if(!stringAll.equals("nothing")){
            String[] paths = stringAll.split("&&");
            //Toast.makeText(this, "stringInfos.length:" + stringInfos.length, Toast.LENGTH_SHORT).show();
            Log.d(TAG, "findbug3 paths.length:" + paths.length );
            for(int i = 0; i < paths.length; i++){
                Log.d(TAG, "findbug4 paths[i]:" + paths[i] );
                this.paths.add(paths[i]);
                Log.d(TAG, "findbug5 paths.length:" + paths.length );
            }
        }
    }

    private void jumpToReadActivity2(String path){
        Intent intent = new Intent(getActivity(), MyReadActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("path", path);//地址
        intent.putExtras(bundle);
        getActivity().startActivity(intent);
    }

    public void pathsChanged(){
        if(paths.size() > 0){
            SharedPreferences sharedPreferences = mActivity.getSharedPreferences(mActivity.getPackageName(), Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.putString("stringAll",generateString(paths) );
            editor.commit();
        }
    }

    private String generateString(ArrayList<String> paths){
        String stringAll = "";
        String splitSymbol = "#";
        for (String path : paths) {
            stringAll += path + "&&";
        }
        //Toast.makeText(this, stringAll, Toast.LENGTH_SHORT).show();
        //Toast.makeText(this, "stringAll.length():" + stringAll.length(), Toast.LENGTH_SHORT).show();
        return stringAll;
    }

    private BookInfoDao bookInfoDao;
    private void jumpToReadActivity(String path){
        File itemFile= null;
        if(path != null){
            itemFile = new File(path);
            if(itemFile.exists() && itemFile.getName().endsWith(".txt")){
                //Toast.makeText(getActivity(), "itemFile.getAbsolutePath():" + itemFile.getAbsolutePath(), Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(getActivity(), "抱歉，书籍资源有误", Toast.LENGTH_LONG).show();
                return;
            }
        }
        //进入阅读
        Log.d("Horizon","打开书籍");
        //查询该书籍是否存在书架中
        //Toast.makeText(HomeT1Filebrowsing.this, "itemFile.getAbsolutePath():" + itemFile.getAbsolutePath(), Toast.LENGTH_SHORT).show();
        if(bookInfoDao == null){
            bookInfoDao = new BookInfoDao(getActivity());
        }
        Book book = bookInfoDao.getBookByAddress(itemFile.getAbsolutePath());
        if(book == null) { //不在书架中，添加书籍到未分类
            book = new Book();
            String[] strs = itemFile.getName().split("\\.");
            book.setName(strs[0]);
            book.setAddress(itemFile.getAbsolutePath());
            book.setCover(book.getRandomCover());
            bookInfoDao.addBook(book);

            book.set_id(bookInfoDao.getLastTBBookInfoId()); //最后插入的书籍的编号
        }
        else { //在书架中，更新书籍最后阅读时间
            Calendar calendar = Calendar.getInstance();
            long time = calendar.getTimeInMillis();
            bookInfoDao.alterLatestReadTime(book.getId(), time);
        }

        //跳转到阅读页面
        Intent intent = new Intent(getActivity(), MainReader.class);
        Bundle bundle = new Bundle();
        bundle.putInt("id", book.getId());
        bundle.putString("name", book.getName());//书籍名
        bundle.putString("address", book.getAddress());//地址
        bundle.putInt("classifyId", book.getClassifyId());//分类编号
        bundle.putLong("latestReadTime", book.getLatestReadTime());//最后一次阅读时间
        bundle.putInt("readRate", book.getReadRate());//阅读进度
        bundle.putLong("wordsNum", book.getWordsNum());//总字数
        intent.putExtras(bundle);
        getActivity().startActivity(intent);
    }

}

