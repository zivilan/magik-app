package com.kaltura.magikapp.magikapp.homepage;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.connect.backend.phoenix.data.KalturaMediaAsset;
import com.kaltura.magikapp.R;
import com.kaltura.magikapp.magikapp.asset_page.AssetPageFragment;
import com.kaltura.magikapp.magikapp.core.FragmentAid;
import com.kaltura.magikapp.magikapp.homepage.binders.DataBinder;
import com.kaltura.magikapp.magikapp.homepage.binders.FourImagesDataBinderTemplate2;
import com.kaltura.magikapp.magikapp.homepage.binders.ItemClick;
import com.kaltura.magikapp.magikapp.homepage.binders.OneImageTemplate2Binder;
import com.kaltura.magikapp.magikapp.homepage.binders.SimpleGridAdapterTemplate2;
import com.kaltura.magikapp.magikapp.homepage.recycler.RowSpaceItemDecoration;
import com.kaltura.magikapp.magikapp.homepage.recycler.Template1RecyclerAdapter;
import com.kaltura.magikapp.magikapp.toolbar.ToolbarMediator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vladir on 02/01/2017.
 */

public class Template2Fragment extends Fragment {

    private RecyclerView mRecyclerView;
    private Context mContext;
    private View mContainer;
    private ViewPager mViewPager;
    protected FragmentAid mFragmentAid;

    private String[] mViewPagerUrls = {"http://cdn.pinchofyum.com/wp-content/uploads/Simple-Mushroom-Penne-eaten-with-fork-600x900.jpg",
            "http://cdn.pinchofyum.com/wp-content/uploads/Dynamite-Plant-Power-Sushi-Bowl-2-2-600x900.jpg",
            "http://cdn.pinchofyum.com/wp-content/uploads/Sweet-Potato-Noodle-Salad-1-6-600x900.jpg" };
    private ArrayList<KalturaMediaAsset> assets;


    @Override
    public void onAttach(Activity context) {
        super.onAttach(context);
        mFragmentAid = (FragmentAid) context;
        mContext = context;
    }

    public static Fragment newInstance(ArrayList<KalturaMediaAsset> data) {
        Template2Fragment template2Fragment = new Template2Fragment();
        template2Fragment.setData(data);
        return template2Fragment;
    }

    private void setData(ArrayList<KalturaMediaAsset> data) {
        this.assets = data;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContainer = inflater.inflate(R.layout.content_scrolling_template1, container, false);
        return mContainer;

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mFragmentAid.setToolbarTitle("");
        mFragmentAid.changeToolbarLayoutColor(false);
        mFragmentAid.setToolbarHomeButton(ToolbarMediator.BUTTON_MENU);

        mRecyclerView = (RecyclerView) mContainer.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));

        OneImageTemplate2Binder oneImageBinder = new OneImageTemplate2Binder(mContext);
        oneImageBinder.setOnClickListener(mOnItemClicked);
        List<DataBinder> binders = new ArrayList<>();
        binders.add(oneImageBinder);

        int[] drawableRes = {R.drawable.cola1, R.drawable.cola2, R.drawable.cola4, R.drawable.cola3};
        SimpleGridAdapterTemplate2 gridAdapter = new SimpleGridAdapterTemplate2(mContext, drawableRes);
        gridAdapter.setOnClickListener(mOnItemClicked);
        FourImagesDataBinderTemplate2 fourImageDataBinder = new FourImagesDataBinderTemplate2(mContext, gridAdapter);
        binders.add(fourImageDataBinder);

        Template1RecyclerAdapter template1RecyclerAdapter = new Template1RecyclerAdapter(mContext, binders);
        mRecyclerView.setAdapter(template1RecyclerAdapter);
        mRecyclerView.addItemDecoration(new RowSpaceItemDecoration(2));

    }

    private ItemClick mOnItemClicked = new ItemClick() {
        @Override
        public void onClick(int position) {
            if (position <= assets.size() - 1) {
                getFragmentManager().beginTransaction().replace(R.id.activity_scrolling_content,
                        AssetPageFragment.newInstance(assets.get(position))).addToBackStack("item").commit();
            }
        }
    };

}
