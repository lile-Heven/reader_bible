package com.sdattg.vip.search;

import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
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
import com.sdattg.vip.bean.NewCategoryBean;
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
public class SerachFragmentX extends BaseFragment implements View.OnClickListener {
    private LinearLayout ll_fragmentx_root;
    private List<TextView> list_tv = new ArrayList<TextView>();
    private List<Map<String, Object>> data_list;
    private ScrollView scrollView;

    @Override
    protected int getLayoutId() {
        return R.layout.serach_fragment_x;
    }

    @Override
    protected void addViewLayout(View view) {

    }


    @Override
    protected void initView(View view) {
        switch (SerachActivity.category_selected) {
            case 0:
                querybooks("01_圣经");
                break;
            case 1:
                querybooks("02_怀著");
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
        while (iterator.hasNext()) {
            String temp = (String) iterator.next();
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
        for (String one :
                keys) {
            keys2.add(one);
        }

        ll_fragmentx_root = view.findViewById(R.id.ll_fragmentx_root);

        //实例化一个LinearLayout
        LinearLayout linearLayout = new LinearLayout(getContext());
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        //设置LinearLayout属性(宽和高)
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        //设置边距
        layoutParams.gravity = Gravity.CENTER_VERTICAL;
        //将以上的属性赋给LinearLayout
        linearLayout.setLayoutParams(layoutParams);

        for (String one :
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
        HorizontalScrollView.LayoutParams scrolllayoutParams = new HorizontalScrollView.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        //设置边距
        scrolllayoutParams.gravity = Gravity.CENTER;
        //将以上的属性赋给LinearLayout
        hScrollView.setLayoutParams(scrolllayoutParams);

        hScrollView.addView(linearLayout);
        ll_fragmentx_root.addView(hScrollView);


        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //list_tv.get(0).performClick();
            }
        }.start();

        list_tv.get(0).performClick();

    }

    @Override
    public void onClick(View view) {
        if (view.getTag() != null) {

            SerachActivity.category_selected_str2 = ((String) view.getTag());
            SerachActivity.category_selected_str3 = "";
            ((SerachActivity) getContext()).updateSelected();

            if (((String) view.getTag()).equals("全部")) {
                if (scrollView != null) {
                    ll_fragmentx_root.removeView(scrollView);
                }
                createScrollViewQuanBu((String) view.getTag());
            } else {
                if (scrollView != null) {
                    ll_fragmentx_root.removeView(scrollView);
                }
                createScrollView((String) view.getTag());
            }
        }
    }

    private void createScrollViewQuanBu(String name) {
        Log.d("findbug071506", "into createScrollViewQuanBu()");
        if (ll_fragmentx_root != null) {
            Log.d("findbug071506", "into createScrollViewQuanBu() 1");
            TextView tv = (TextView) ll_fragmentx_root.findViewWithTag(name);
            Log.d("findbug071506", "into createScrollViewQuanBu() 2");
            if (tv != null) {
                Log.d("findbug071506", "into createScrollViewQuanBu() 3");
                initAllTextViewColor();
                tv.setTextColor(getResources().getColor(R.color.blue));


                scrollView = new ScrollView(getContext());
                //scrollView.setHorizontalScrollBarEnabled(false);
                //设置LinearLayout属性(宽和高)
                ScrollView.LayoutParams scrolllayoutParams = new ScrollView.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                //设置边距
                scrolllayoutParams.gravity = Gravity.CENTER;
                //将以上的属性赋给LinearLayout
                scrollView.setLayoutParams(scrolllayoutParams);
                Log.d("findbug071506", "into createScrollViewQuanBu() 4");

                //实例化一个LinearLayout
                LinearLayout linearLayout0 = new LinearLayout(getContext());
                linearLayout0.setOrientation(LinearLayout.VERTICAL);
                //设置LinearLayout属性(宽和高)
                LinearLayout.LayoutParams layoutParams0 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                //设置边距
                layoutParams0.gravity = Gravity.CENTER_VERTICAL;
                //将以上的属性赋给LinearLayout
                linearLayout0.setLayoutParams(layoutParams0);
                Log.d("findbug071506", "into createScrollViewQuanBu() 5");


                Set<String> keySet = results.keySet();
                Iterator iterator = keySet.iterator();
                String[] keys = new String[keySet.size()];
                int ii = 0;
                while (iterator.hasNext()) {
                    String temp = (String) iterator.next();
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
                for (String one :
                        keys) {
                    keys2.add(one);
                }

                for (String one :
                        keys) {
                    Log.d("findbug071506", "into createScrollViewQuanBu() foreach() 8");
                    //实例化一个LinearLayout
                    LinearLayout linearLayout = new LinearLayout(getContext());
                    linearLayout.setOrientation(LinearLayout.VERTICAL);
                    //设置LinearLayout属性(宽和高)
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    //设置边距
                    //将以上的属性赋给LinearLayout
                    linearLayout.setLayoutParams(layoutParams);
                    Log.d("findbug071506", "into createScrollViewQuanBu() 6");


                    //实例化一个LinearLayout
                    LinearLayout column = new LinearLayout(getContext());
                    column.setOrientation(LinearLayout.HORIZONTAL);
                    //column.setBackgroundColor(getResources().getColor(R.color.text_999));
                    //设置LinearLayout属性(宽和高)
                    LinearLayout.LayoutParams columnParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    //设置边距
                    //将以上的属性赋给LinearLayout
                    column.setLayoutParams(columnParams);
                    Log.d("findbug071506", "into createScrollViewQuanBu() foreach() 9");

                    View space = new View(getContext());
                    LinearLayout.LayoutParams spaceParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, MainActivity.dip2px(getContext(), 5));
                    //设置边距
                    //将以上的属性赋给LinearLayout
                    space.setLayoutParams(spaceParams);
                    space.setBackgroundColor(getResources().getColor(R.color.background_color));

                    linearLayout.addView(space);
                    linearLayout.addView(column);

                    View space2 = new View(getContext());
                    LinearLayout.LayoutParams space2Params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, MainActivity.dip2px(getContext(), 1));
                    //设置边距
                    //将以上的属性赋给LinearLayout
                    space2.setLayoutParams(space2Params);
                    space2.setBackgroundColor(getResources().getColor(R.color.background_color));

                    linearLayout.addView(space2);


                    ImageView iv = new ImageView(getContext());
                    //设置LinearLayout属性(宽和高)
                    //LinearLayout.LayoutParams ivParams=new LinearLayout.LayoutParams(60, 60);
                    //将以上的属性赋给LinearLayout
                    //column.setLayoutParams(ivParams);
                    iv.setImageResource(R.mipmap.myicon_correct2);
                    iv.setScaleType(ImageView.ScaleType.FIT_CENTER);



                    /*Log.d("findbug071506", "into createScrollViewQuanBu() foreach() 10");
                    //实例化一个TextView
                    TextView tv_column = new TextView(getContext());
                    //设置宽高以及权重
                    LinearLayout.LayoutParams tv_columnParams = new LinearLayout.LayoutParams(60, 60);
                    tv_columnParams.setMargins(200, 2, 0, 0);
                    //设置textview垂直居中
                    tv_columnParams.gravity = Gravity.CENTER;
                    tv_column.setLayoutParams(tv_columnParams);
                    tv_column.setTextSize(60);
                    tv_column.setTextColor(getResources().getColor(R.color.blue));
                    tv_column.setText("测试测试");*/
                    //实例化一个TextView
                    TextView tv2 = new TextView(getContext());
                    //设置宽高以及权重
                    LinearLayout.LayoutParams tvParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    tvParams.setMargins(5, 2, 0, 0);
                    //设置textview垂直居中
                    tvParams.gravity = Gravity.CENTER;
                    tv2.setTextSize(25);
                    tv2.setTextColor(getResources().getColor(R.color.text_333));
                    tv2.setText(one.substring(one.indexOf("_") + 1));
                    tv2.setLayoutParams(tvParams);
                    //tv2.setTag(one);
                    //tv2.setOnClickListener(this);
                    Log.d("findbug071506", "into createScrollViewQuanBu() foreach() 11");

                    column.addView(iv); //加上这个imageview就会有幺蛾子
                    column.addView(tv2);

                    Log.d("findbug071506", "into createScrollViewQuanBu() foreach() 12");


                    MyGridView gv = new MyGridView(getContext());
                    gv.setNumColumns(3);
                    gv.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);
                    gv.setHorizontalSpacing(2);
                    gv.setVerticalSpacing(2);
                    //设置LinearLayout属性(宽和高)
                    MyGridView.LayoutParams gvParams = new MyGridView.LayoutParams(MyGridView.LayoutParams.WRAP_CONTENT, GridView.LayoutParams.WRAP_CONTENT);
                    //将以上的属性赋给LinearLayout
                    gv.setLayoutParams(gvParams);
                    //TODO link datas
                    final List<String> list_book = results.get(one);
                    SimpleAdapter adapter = new SimpleAdapter(getContext(), getData(list_book), R.layout.item_gv, new String[]{"text"},
                            new int[]{R.id.tv_item_gridview});
                    gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            Log.d("findbug071517", "list_book.get(i)：" + list_book.get(i));
                            SerachActivity.category_selected_str3 = (list_book.get(i));
                            ((SerachActivity) getContext()).updateSelected();

                            ((SerachActivity) getContext()).showReadyToSearch();
                        }
                    });
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

    private List<Map<String, Object>> getData(List<String> list_book) {

        data_list = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < list_book.size(); i++) {
            Map<String, Object> map = new HashMap<>();
            Log.d("findbug071506", "list_book.get(i):" + list_book.get(i));
            map.put("text", ((String) list_book.get(i)).substring(((String) list_book.get(i)).indexOf("_") + 1));
            data_list.add(map);
        }
        return data_list;
    }

    private void createScrollView(String name) {
        if (ll_fragmentx_root != null) {
            TextView tv = (TextView) ll_fragmentx_root.findViewWithTag(name);
            if (tv != null) {
                Log.d("findbug071506", "into createScrollViewQuanBu() 3");
                initAllTextViewColor();
                tv.setTextColor(getResources().getColor(R.color.blue));

                scrollView = new ScrollView(getContext());
                //scrollView.setHorizontalScrollBarEnabled(false);
                //设置LinearLayout属性(宽和高)
                ScrollView.LayoutParams scrolllayoutParams = new ScrollView.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                //设置边距
                scrolllayoutParams.gravity = Gravity.CENTER;
                //将以上的属性赋给LinearLayout
                scrollView.setLayoutParams(scrolllayoutParams);
                Log.d("findbug071506", "into createScrollViewQuanBu() 4");

                //实例化一个LinearLayout
                LinearLayout linearLayout0 = new LinearLayout(getContext());
                linearLayout0.setOrientation(LinearLayout.VERTICAL);
                //设置LinearLayout属性(宽和高)
                LinearLayout.LayoutParams layoutParams0 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                //设置边距
                layoutParams0.gravity = Gravity.CENTER_VERTICAL;
                //将以上的属性赋给LinearLayout
                linearLayout0.setLayoutParams(layoutParams0);
                Log.d("findbug071506", "into createScrollViewQuanBu() 5");


                Log.d("findbug071506", "into createScrollViewQuanBu() foreach() 8");
                //实例化一个LinearLayout
                LinearLayout linearLayout = new LinearLayout(getContext());
                linearLayout.setOrientation(LinearLayout.VERTICAL);
                //设置LinearLayout属性(宽和高)
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                //设置边距
                //将以上的属性赋给LinearLayout
                linearLayout.setLayoutParams(layoutParams);
                Log.d("findbug071506", "into createScrollViewQuanBu() 6");


                //实例化一个LinearLayout
                LinearLayout column = new LinearLayout(getContext());
                column.setOrientation(LinearLayout.HORIZONTAL);
                //column.setBackgroundColor(getResources().getColor(R.color.text_999));
                //设置LinearLayout属性(宽和高)
                LinearLayout.LayoutParams columnParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                //设置边距
                //将以上的属性赋给LinearLayout
                column.setLayoutParams(columnParams);
                Log.d("findbug071506", "into createScrollViewQuanBu() foreach() 9");

                View space = new View(getContext());
                LinearLayout.LayoutParams spaceParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, MainActivity.dip2px(getContext(), 5));
                //设置边距
                //将以上的属性赋给LinearLayout
                space.setLayoutParams(spaceParams);
                space.setBackgroundColor(getResources().getColor(R.color.background_color));

                linearLayout.addView(space);
                linearLayout.addView(column);

                View space2 = new View(getContext());
                LinearLayout.LayoutParams space2Params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, MainActivity.dip2px(getContext(), 1));
                //设置边距
                //将以上的属性赋给LinearLayout
                space2.setLayoutParams(space2Params);
                space2.setBackgroundColor(getResources().getColor(R.color.background_color));

                linearLayout.addView(space2);


                ImageView iv = new ImageView(getContext());
                //设置LinearLayout属性(宽和高)
                //LinearLayout.LayoutParams ivParams=new LinearLayout.LayoutParams(60, 60);
                //将以上的属性赋给LinearLayout
                //column.setLayoutParams(ivParams);
                iv.setImageResource(R.mipmap.myicon_correct2);
                iv.setScaleType(ImageView.ScaleType.FIT_CENTER);


                //实例化一个TextView
                TextView tv2 = new TextView(getContext());
                //设置宽高以及权重
                LinearLayout.LayoutParams tvParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                tvParams.setMargins(5, 2, 0, 0);
                //设置textview垂直居中
                tvParams.gravity = Gravity.CENTER;
                tv2.setTextSize(25);
                tv2.setTextColor(getResources().getColor(R.color.text_333));
                tv2.setText(name.substring(name.indexOf("_") + 1));
                tv2.setLayoutParams(tvParams);
                //tv2.setTag(one);
                //tv2.setOnClickListener(this);
                Log.d("findbug071506", "into createScrollViewQuanBu() foreach() 11");

                column.addView(iv); //加上这个imageview就会有幺蛾子
                column.addView(tv2);

                Log.d("findbug071506", "into createScrollViewQuanBu() foreach() 12");


                MyGridView gv = new MyGridView(getContext());
                gv.setNumColumns(3);
                gv.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);
                gv.setHorizontalSpacing(2);
                gv.setVerticalSpacing(2);
                //设置LinearLayout属性(宽和高)
                MyGridView.LayoutParams gvParams = new MyGridView.LayoutParams(MyGridView.LayoutParams.WRAP_CONTENT, GridView.LayoutParams.WRAP_CONTENT);
                //将以上的属性赋给LinearLayout
                gv.setLayoutParams(gvParams);
                //TODO link datas
                final List<String> list_book = results.get(name);
                SimpleAdapter adapter = new SimpleAdapter(getContext(), getData(list_book), R.layout.item_gv, new String[]{"text"},
                        new int[]{R.id.tv_item_gridview});
                gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Log.d("findbug071517", "list_book.get(i)：" + list_book.get(i));
                        SerachActivity.category_selected_str3 = (list_book.get(i));
                        ((SerachActivity) getContext()).updateSelected();

                        ((SerachActivity) getContext()).showReadyToSearch();
                    }
                });
                gv.setAdapter(adapter);

                Log.d("findbug071506", "into createScrollViewQuanBu() foreach() 13");
                linearLayout.addView(gv);

                Log.d("findbug071506", "into createScrollViewQuanBu() foreach() 133");
                linearLayout0.addView(linearLayout);
                Log.d("findbug071506", "into createScrollViewQuanBu() foreach() 134");


                Log.d("findbug071506", "into createScrollViewQuanBu()  14");


                scrollView.addView(linearLayout0);
                Log.d("findbug071506", "into createScrollViewQuanBu()  15");

                ll_fragmentx_root.addView(scrollView);
                Log.d("findbug071506", "into createScrollViewQuanBu()  16");
            }
        }
    }

    private void initAllTextViewColor() {
        for (TextView tv :
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
    public static List<String> list_xinyue_str;

    private void querybooks(String dirName) {
        NewCategoryDBHelper myCategoryDBHelper = new NewCategoryDBHelper(getContext());
        results = new HashMap<String, List<String>>();
        List<NewCategoryBean> list_category = myCategoryDBHelper.queryCategory(dirName);
        for (NewCategoryBean categoryBean:
             list_category) {
            List<NewCategoryBean> booksCategory_list = myCategoryDBHelper.queryCategory(categoryBean.name);
            List<String> books_list = new ArrayList<String>();
            for (NewCategoryBean one:
                    booksCategory_list) {
                books_list.add(one.name);
            }
            results.put(categoryBean.name, books_list);
        }



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
