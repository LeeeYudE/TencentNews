package news.tencent.charco.android.view.activity;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.github.chrisbanes.photoview.PhotoView;

import news.tencent.charco.android.R;
import news.tencent.charco.android.base.BaseActivity;
import news.tencent.charco.android.widget.DragViewPager;

public class AlbumActivity extends BaseActivity implements View.OnClickListener {

    private DragViewPager mViewPager;
    private TextView mTvCount;
    private PhotoView[] mPhotoViews;
    private String[] mUrls = new String[]{"http://img0.imgtn.bdimg.com/it/u=556386140,2024865136&fm=27&gp=0.jpg",
            "http://img2.imgtn.bdimg.com/it/u=2069716012,863663220&fm=214&gp=0.jpg",
            "http://img5.imgtn.bdimg.com/it/u=2926643301,1163405546&fm=27&gp=0.jpg"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);
        initView();
    }

    private void initView() {
        setTransparentBar();
        mViewPager = findViewById(R.id.viewpager);
        mTvCount = findViewById(R.id.tv_count);
        if (mUrls !=null){
            findViewById(R.id.iv_back).setOnClickListener(this);
            mPhotoViews = new PhotoView[mUrls.length];
            for (int i = 0; i < mPhotoViews.length; i++) {
                PhotoView photoView = (PhotoView) View.inflate(this, R.layout.item_image, null);
                mPhotoViews[i] = photoView;
                Glide.with(this).load(mUrls[i])
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        .into(mPhotoViews[i]);//配合glide使用
            }

            mViewPager.setAdapter(new PagerAdapter() {
                @Override
                public int getCount() {
                    return mPhotoViews.length;
                }

                @Override
                public Object instantiateItem(ViewGroup container, int position) {
                    container.addView(mPhotoViews[position]);
                    return mPhotoViews[position];
                }

                @Override
                public void destroyItem(ViewGroup container, int position, Object object) {
                    container.removeView(mPhotoViews[position]);
                }

                @Override
                public boolean isViewFromObject(View view, Object object) {

                    return view == object;
                }
            });
            mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    mTvCount.setText(position+1+"/"+mUrls.length);
                    mViewPager.setCurrentShowView(mPhotoViews[position]);
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
            mViewPager.setOnDragListener(new DragViewPager.OnDragListener() {
                @Override
                public void onPictureClick() {

                }

                @Override
                public void onPictureRelease(View view) {
                    finish();
                }

                @Override
                public void onStartDrag() {
                    hideView(mTvCount);
                    hideView(R.id.iv_back);
                }

                @Override
                public void onReset() {
                    showView(mTvCount);
                    showView(R.id.iv_back);
                }
            });
            mViewPager.setCurrentItem(0);
            mTvCount.setText(1+"/"+mUrls.length);
            mViewPager.setCurrentShowView(mPhotoViews[0]);
        }
    }

    @Override
    public void onClick(View view) {

    }
}
