package com.sdattg.vip;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.sdattg.vip.base.BaseFragment;
import com.sdattg.vip.fragment.Main02Fragment;
import com.sdattg.vip.R;

import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.bgabanner.BGABanner;

/**
 * Created by yinqm on 2018/6/27.
 */
public class MyFragmentShuKu extends BaseFragment {
    private List<String> imgUrl;
    private BGABanner banner_guide_content;
    private FrameLayout fl;
    @Override
    protected int getLayoutId() {
        return R.layout.main_01_fragment;
    }

    @Override
    protected void addViewLayout(View view) {

    }

    @Override
    protected void initView(View view) {
        banner_guide_content = (BGABanner) view.findViewById(R.id.banner_guide_content);

        imgUrl = new ArrayList<>();
    }

    @Override
    protected void initData() {
        imgUrl.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1530006397691&di=75eba0045a36f15423ee3d9554809825&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F01d56b5542d8bc0000019ae98da289.jpg");
        imgUrl.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1530006397691&di=411e3d0833ce0b66851f30a1a4b43628&imgtype=0&src=http%3A%2F%2Fwww.yongxintex.com%2FHtml%2Fimages%2Fb1.jpg");
        imgUrl.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1530092640192&di=411a16785d4da75d74430a4d42a09444&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F01ed875979943ba8012193a3d4860f.png");
        banner_guide_content.setAdapter(new BGABanner.Adapter<ImageView, String>() {
            @Override
            public void fillBannerItem(BGABanner banner, ImageView itemView, String model, int position) {
                Glide.with(getActivity())
                        .load(model)
                        .centerCrop()
                        .dontAnimate()
                        .into(itemView);
            }
        });
        banner_guide_content.setData(imgUrl, null);
    }

    @Override
    protected void iniLogic() {
        Main02Fragment tab01Fragment = new Main02Fragment();
        replaceFragment(tab01Fragment,R.id.fl);
    }

    @Override
    protected void httpRequest() {

    }

    @Override
    public void onResume() {
        super.onResume();
        banner_guide_content.stopAutoPlay();
    }

    @Override
    public void onStop() {
        super.onStop();
        banner_guide_content.startAutoPlay();
    }
}

