package com.sdattg.vip.search;

import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.sdattg.vip.MainActivity;
import com.sdattg.vip.R;
import com.sdattg.vip.base.BaseFragment;
import com.sdattg.vip.bean.CategoryBean;
import com.sdattg.vip.fragment.Main02FragmentBible;
import com.sdattg.vip.util.FileUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by yinqm on 2018/7/5.
 */
public class SerachFragmentX extends BaseFragment implements View.OnClickListener{
    private LinearLayout ll_fragmentx_root;
    private List<TextView> list_tv = new ArrayList<TextView>();
    private List<Map<String, Object>> data_list;

    @Override
    protected int getLayoutId() {
        return R.layout.serach_fragment_x;
    }

    @Override
    protected void addViewLayout(View view) {

    }


    @Override
    protected void initView(View view) {
        switch (SerachActivity.category_selected){
            case 0:
                querybooks("01-圣经");
                break;
            case 1:
                querybooks("02-怀著");
                break;
            case 2:
                querybooks("其他");
                break;
            case 3:
                querybooks("英文");
                break;
        }
        //String[] test = new String[]{"01_旧约", "02_新约"};
        /*List<String> test = new ArrayList<String>();
        test.add("01_旧约");
        test.add("02_新约");*/

        Set<String> keySet = results.keySet();
        Iterator iterator = keySet.iterator();
        String[] keys = new String[keySet.size()];
        int ii = 0;
        while (iterator.hasNext()){
            String temp = (String)iterator.next();
            keys[ii] = temp;
            ii++;
        }

        List fileList = Arrays.asList(keys);
        Collections.sort(fileList, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });


        List<String> keys2 = new ArrayList<>();
        keys2.add("全部");
        for (String one:
             keys) {
            keys2.add(one);
        }

        ll_fragmentx_root = view.findViewById(R.id.ll_fragmentx_root);

        //实例化一个LinearLayout
        LinearLayout linearLayout = new LinearLayout(getContext());
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        //设置LinearLayout属性(宽和高)
        LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        //设置边距
        layoutParams.gravity = Gravity.CENTER_VERTICAL;
        //将以上的属性赋给LinearLayout
        linearLayout.setLayoutParams(layoutParams);

        for (String one:
                keys2) {
            //实例化一个TextView
            TextView tv = new TextView(getContext());
            //设置宽高以及权重
            LinearLayout.LayoutParams tvParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            tvParams.setMargins(30, 2, 0, 0);
            //设置textview垂直居中
            tvParams.gravity = Gravity.CENTER;
            tv.setTextSize(25);
            tv.setTextColor(getResources().getColor(R.color.text_333));
            tv.setText(one.substring(one.indexOf("_") + 1));
            tv.setLayoutParams(tvParams);
            tv.setTag(one);
            tv.setOnClickListener(this);
            list_tv.add(tv);
            linearLayout.addView(tv);
        }

        HorizontalScrollView hScrollView = new HorizontalScrollView(getContext());
        //scrollView.setHorizontalScrollBarEnabled(false);
        //设置LinearLayout属性(宽和高)
        HorizontalScrollView.LayoutParams scrolllayoutParams=new HorizontalScrollView.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        //设置边距
        scrolllayoutParams.gravity = Gravity.CENTER;
        //将以上的属性赋给LinearLayout
        hScrollView.setLayoutParams(scrolllayoutParams);

        hScrollView.addView(linearLayout);
        ll_fragmentx_root.addView(hScrollView);

    }

    @Override
    public void onClick(View view) {
        if(view.getTag() != null){
            if(((String)view.getTag()).equals("全部")){
                createScrollViewQuanBu((String)view.getTag());
            }else {
                //TODO
            }
        }
    }

    private void createScrollViewQuanBu(String name){
        Log.d("findbug071506", "into createScrollViewQuanBu()");
        if(ll_fragmentx_root != null){
            Log.d("findbug071506", "into createScrollViewQuanBu() 1");
            TextView tv = (TextView)ll_fragmentx_root.findViewWithTag(name);
            Log.d("findbug071506", "into createScrollViewQuanBu() 2");
            if(tv != null){
                Log.d("findbug071506", "into createScrollViewQuanBu() 3");
                initAllTextViewColor();
                tv.setTextColor(getResources().getColor(R.color.blue));

                ScrollView scrollView = new ScrollView(getContext());
                //scrollView.setHorizontalScrollBarEnabled(false);
                //设置LinearLayout属性(宽和高)
                ScrollView.LayoutParams scrolllayoutParams=new ScrollView.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                //设置边距
                scrolllayoutParams.gravity = Gravity.CENTER;
                //将以上的属性赋给LinearLayout
                scrollView.setLayoutParams(scrolllayoutParams);
                Log.d("findbug071506", "into createScrollViewQuanBu() 4");

                //实例化一个LinearLayout
                LinearLayout linearLayout0 = new LinearLayout(getContext());
                linearLayout0.setOrientation(LinearLayout.VERTICAL);
                //设置LinearLayout属性(宽和高)
                LinearLayout.LayoutParams layoutParams0=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                //设置边距
                layoutParams0.gravity = Gravity.CENTER_VERTICAL;
                //将以上的属性赋给LinearLayout
                linearLayout0.setLayoutParams(layoutParams0);
                Log.d("findbug071506", "into createScrollViewQuanBu() 5");



                Set<String> keySet = results.keySet();
                Iterator iterator = keySet.iterator();
                String[] keys = new String[keySet.size()];
                int ii = 0;
                while (iterator.hasNext()){
                    String temp = (String)iterator.next();
                    keys[ii] = temp;
                    ii++;
                }

                List fileList = Arrays.asList(keys);
                Collections.sort(fileList, new Comparator<String>() {
                    @Override
                    public int compare(String o1, String o2) {
                        return o1.compareTo(o2);
                    }
                });


                List<String> keys2 = new ArrayList<>();
                for (String one:
                        keys) {
                    keys2.add(one);
                }

                for (String one:
                     keys) {
                    Log.d("findbug071506", "into createScrollViewQuanBu() foreach() 8");
                    //实例化一个LinearLayout
                    LinearLayout linearLayout = new LinearLayout(getContext());
                    linearLayout.setOrientation(LinearLayout.VERTICAL);
                    //设置LinearLayout属性(宽和高)
                    LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    //设置边距
                    layoutParams.gravity = Gravity.CENTER_VERTICAL;
                    //将以上的属性赋给LinearLayout
                    linearLayout.setLayoutParams(layoutParams);
                    Log.d("findbug071506", "into createScrollViewQuanBu() 6");


                    //实例化一个LinearLayout
                    LinearLayout column = new LinearLayout(getContext());
                    column.setOrientation(LinearLayout.HORIZONTAL);
                    //设置LinearLayout属性(宽和高)
                    LinearLayout.LayoutParams columnParams=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    //设置边距
                    columnParams.gravity = Gravity.CENTER;
                    //将以上的属性赋给LinearLayout
                    column.setLayoutParams(columnParams);
                    Log.d("findbug071506", "into createScrollViewQuanBu() foreach() 9");

                    ImageView iv = new ImageView(getContext());
                    //设置LinearLayout属性(宽和高)
                    LinearLayout.LayoutParams ivParams=new LinearLayout.LayoutParams(60, 60);
                    //将以上的属性赋给LinearLayout
                    column.setLayoutParams(ivParams);
                    iv.setBackgroundResource(R.drawable.myicon_correct);

                    Log.d("findbug071506", "into createScrollViewQuanBu() foreach() 10");
                    //实例化一个TextView
                    TextView tv_column = new TextView(getContext());
                    //设置宽高以及权重
                    LinearLayout.LayoutParams tv_columnParams = new LinearLayout.LayoutParams(60, 60);
                    //LinearLayout.LayoutParams tv_columnParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1);
                    tv_columnParams.setMargins(5, 2, 0, 0);
                    //设置textview垂直居中
                    tv_columnParams.gravity = Gravity.CENTER;
                    tv_column.setLayoutParams(tv_columnParams);
                    tv_column.setTextSize(25);
                    tv_column.setTextColor(getResources().getColor(R.color.blue));
                    Log.d("findbug071506", "into createScrollViewQuanBu() foreach() setText() one；" + one);
                    tv_column.setText(one.substring(one.indexOf("_") + 1));

                    Log.d("findbug071506", "into createScrollViewQuanBu() foreach() 11");
                    column.addView(iv);
                    column.addView(tv_column);

                    Log.d("findbug071506", "into createScrollViewQuanBu() foreach() 12");
                    linearLayout.addView(column);

                    MyGridView gv = new MyGridView(getContext());
                    gv.setNumColumns(3);
                    //设置LinearLayout属性(宽和高)
                    MyGridView.LayoutParams gvParams=new MyGridView.LayoutParams(MyGridView.LayoutParams.WRAP_CONTENT, GridView.LayoutParams.WRAP_CONTENT);
                    //将以上的属性赋给LinearLayout
                    gv.setLayoutParams(gvParams);
                    //TODO link datas
                    List<String> list_book = results.get(one);
                    SimpleAdapter adapter=new SimpleAdapter(getContext(),getData(list_book),R.layout.item_gv,new String[]{"text"},
                            new int[]{R.id.tv_item_gridview});
                    gv.setAdapter(adapter);

                    Log.d("findbug071506", "into createScrollViewQuanBu() foreach() 13");
                    linearLayout.addView(gv);

                    Log.d("findbug071506", "into createScrollViewQuanBu() foreach() 133");
                    linearLayout0.addView(linearLayout);
                    Log.d("findbug071506", "into createScrollViewQuanBu() foreach() 134");
                }

                Log.d("findbug071506", "into createScrollViewQuanBu()  14");


                scrollView.addView(linearLayout0);
                Log.d("findbug071506", "into createScrollViewQuanBu()  15");

                ll_fragmentx_root.addView(scrollView);
                Log.d("findbug071506", "into createScrollViewQuanBu()  16");
            }
        }
    }

    private List<Map<String,Object>> getData(List<String> list_book){

        data_list = new ArrayList<Map<String,Object>>();
        for(int i=0;i<list_book.size();i++){
            Map<String,Object>map=new HashMap<>();
            Log.d("findbug071506","list_book.get(i):" + list_book.get(i));
            map.put("text",((String)list_book.get(i)).substring(((String)list_book.get(i)).indexOf("_") + 1));
            data_list.add(map);
        }
        return data_list;
    }

    private void createScrollView(String name){
        if(ll_fragmentx_root != null){
            TextView tv = (TextView)ll_fragmentx_root.findViewWithTag(name);
            if(tv != null){
                initAllTextViewColor();
                tv.setTextColor(getResources().getColor(R.color.blue));

                results.keySet();

                //实例化一个LinearLayout
                LinearLayout linearLayout = new LinearLayout(getContext());
                linearLayout.setOrientation(LinearLayout.HORIZONTAL);
                //设置LinearLayout属性(宽和高)
                LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                //设置边距
                layoutParams.gravity = Gravity.CENTER_VERTICAL;
                //将以上的属性赋给LinearLayout
                linearLayout.setLayoutParams(layoutParams);
                //linearLayout.addView();

                ScrollView scrollView = new ScrollView(getContext());
                //scrollView.setHorizontalScrollBarEnabled(false);
                //设置LinearLayout属性(宽和高)
                ScrollView.LayoutParams scrolllayoutParams=new ScrollView.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                //设置边距
                scrolllayoutParams.gravity = Gravity.CENTER;
                //将以上的属性赋给LinearLayout
                scrollView.setLayoutParams(scrolllayoutParams);

                scrollView.addView(linearLayout);

                ll_fragmentx_root.addView(scrollView);
            }
        }
    }

    private void initAllTextViewColor(){
        for (TextView tv:
        list_tv) {
            tv.setTextColor(getResources().getColor(R.color.text_333));
        }
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


    public static HashMap<String, List<String>> results;
    public static  List<String> list_xinyue_str;
    private void querybooks(String dirName){
        MyCategoryDBHelper myCategoryDBHelper = new MyCategoryDBHelper(getContext());
        results = myCategoryDBHelper.getQueryBooks(FileUtil.replaceBy_(dirName), null);



        /*for (CategoryBean categoryBean:
                list_xinyue) {
            Main02FragmentBible.BookBeanOnShuKu bean = new Main02FragmentBible.BookBeanOnShuKu();
            bean.title = "";
            bean.title = categoryBean.categoryName.substring(categoryBean.categoryName.indexOf("_") + 1);
            bean.author = "作者:摩西";
            List<CategoryBean> books_xinyue = myCategoryDBHelper.getCategory(FileUtil.replaceBy_(categoryBean.categoryName), "jieshao");
            if(books_xinyue.size() > 0){
                bean.jieshao = books_xinyue.get(0).categoryJieshao;
                Log.d("Tab01Product", "bean.jieshao:" + bean.jieshao);
            }else{
                bean.jieshao = "无介绍";
            }
            list_xinyue_str.add(bean.toString());
            list_item.add(bean.toString());
            Log.d("GuideActivity", "categoryInsertOrder:" + categoryBean.categoryInsertOrder + ", categoryPath:" + categoryBean.categoryPath + ", categoryNam:" + categoryBean.categoryName);
        }*/

    }
}
