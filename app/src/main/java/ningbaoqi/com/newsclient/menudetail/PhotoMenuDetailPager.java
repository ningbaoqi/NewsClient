package ningbaoqi.com.newsclient.menudetail;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import java.util.ArrayList;

import ningbaoqi.com.newsclient.R;
import ningbaoqi.com.newsclient.base.BaseMenuDetailPager;
import ningbaoqi.com.newsclient.domiam.PhotoData;
import ningbaoqi.com.newsclient.global.GlobalContants;
import ningbaoqi.com.newsclient.utils.CacheUtils;

/**
 * =========================================
 * <p/>
 * 版    权: ningxiansheng
 * <p/>
 * 作    者: 宁宝琪
 * <p/>
 * 版    本:1.0
 * <p/>
 * 创建 时间:18-5-21
 * <p/>
 * 描    述:
 * <p/>
 * <p/>
 * 修订 历史:
 * <p/>
 * =========================================
 * 菜单详情页 组图
 */

public class PhotoMenuDetailPager extends BaseMenuDetailPager {

    private ListView lvPhoto;
    private GridView gvPhtot;
    private ArrayList<PhotoData.PhtotInfo> photoList;
    private PhotoAdapter adapter;
    private ImageButton btnPhoto;

    public PhotoMenuDetailPager(Activity mActivity, ImageButton btnPhoto) {
        super(mActivity);
        this.btnPhoto = btnPhoto;
        this.btnPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeDisplay();
            }
        });
    }

    private boolean isListDisplay = true;//是否是列表展示

    private void changeDisplay() {
        if (isListDisplay) {
            isListDisplay = false;
            lvPhoto.setVisibility(View.GONE);
            gvPhtot.setVisibility(View.VISIBLE);
            btnPhoto.setImageResource(R.drawable.icon_pic_list_type);
        } else {
            isListDisplay = true;
            lvPhoto.setVisibility(View.VISIBLE);
            gvPhtot.setVisibility(View.GONE);
            btnPhoto.setImageResource(R.drawable.icon_pic_grid_type);
        }
    }


    @Override
    public View initView() {
        View view = View.inflate(mActivity, R.layout.photo_layout, null);
        lvPhoto = view.findViewById(R.id.photo_listview);
        gvPhtot = view.findViewById(R.id.photo_gridview);
        return view;
    }

    @Override
    public void initData() {
        super.initData();
        String cache = CacheUtils.getCache(GlobalContants.PHOTOS_URL, mActivity);
        if (!TextUtils.isEmpty(cache)) {

        }
        getDataFromServer();
    }

    public void getDataFromServer() {
        HttpUtils utils = new HttpUtils();
        utils.send(HttpRequest.HttpMethod.GET, GlobalContants.PHOTOS_URL, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;
                parseData(result);
                CacheUtils.setCache(GlobalContants.PHOTOS_URL, result, mActivity);
            }

            @Override
            public void onFailure(HttpException e, String s) {
                Toast.makeText(mActivity, "获取失败", Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        });
    }

    private void parseData(String result) {
        Gson gson = new Gson();
        PhotoData photoData = gson.fromJson(result, PhotoData.class);
        photoList = photoData.data.news;
        if (photoList != null) {
            adapter = new PhotoAdapter();
            lvPhoto.setAdapter(adapter);
            gvPhtot.setAdapter(adapter);
        }
    }

    class PhotoAdapter extends BaseAdapter {

        private final BitmapUtils utils;

        public PhotoAdapter() {
            utils = new BitmapUtils(mActivity);
            utils.configDefaultLoadingImage(R.drawable.news_pic_default);
        }

        @Override

        public int getCount() {
            return photoList.size();
        }

        @Override
        public PhotoData.PhtotInfo getItem(int position) {
            return photoList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = View.inflate(mActivity, R.layout.photo_list_item, null);
                viewHolder = new ViewHolder();
                viewHolder.tvPic = convertView.findViewById(R.id.tv_pic);
                viewHolder.ivPic = convertView.findViewById(R.id.iv_pic);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            PhotoData.PhtotInfo item = getItem(position);
            viewHolder.tvPic.setText(item.title);
            utils.display(viewHolder.ivPic, item.listimage);
            return convertView;
        }
    }

    static class ViewHolder {
        public TextView tvPic;
        public ImageView ivPic;
    }
}
