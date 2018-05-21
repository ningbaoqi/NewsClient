package ningbaoqi.com.newsclient.fragment;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import java.util.ArrayList;

import ningbaoqi.com.newsclient.R;
import ningbaoqi.com.newsclient.activity.HomeActivity;
import ningbaoqi.com.newsclient.domiam.NewsData;
import ningbaoqi.com.newsclient.pager.NewsPager;

/**
 * =========================================
 * <p/>
 * 版    权: ningxiansheng
 * <p/>
 * 作    者: 宁宝琪
 * <p/>
 * 版    本:1.0
 * <p/>
 * 创建 时间:18-5-18
 * <p/>
 * 描    述:
 * <p/>
 * <p/>
 * 修订 历史:
 * <p/>
 * =========================================
 */

public class SlidingMenuFragment extends BaseFragment {
    private ArrayList<NewsData.NewsMenuData> newsMenuData;
    private ListView listView;
    private SlidingMenuAdapter adapter;
    private int currentPosition;//当前被点击的SlidingMenuItem

    @Override
    protected View initView() {
        View view = View.inflate(mActivity, R.layout.fragment_sliding_menu, null);
        listView = view.findViewById(R.id.slidingmenu_listview);
        return view;
    }

    @Override
    protected void initData() {
        super.initData();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                currentPosition = position;
                adapter.notifyDataSetChanged();
                setCurrentMenuDetailPager(position);
                toggleSlidingMenu();
            }
        });
    }

    /**
     * 切换SlidingMenu状态
     */
    private void toggleSlidingMenu() {
        SlidingMenu slidingMenu = ((HomeActivity) mActivity).getSlidingMenu();
        slidingMenu.toggle();//slidingmenu切换
    }

    /**
     * 设置当前菜单详情页
     *
     * @param position
     */
    private void setCurrentMenuDetailPager(int position) {
        HomeFragment homeFragment = ((HomeActivity) mActivity).getHomeFragment();
        NewsPager newsPager = homeFragment.getNewsPager();
        newsPager.setCurrentMenuDetailPager(position);
    }

    /**
     * 设置网络数据
     */
    public void setSlidingMenuData(NewsData newsData) {
        Log.d("nbq-----", newsData.toString());
        newsMenuData = newsData.data;
        adapter = new SlidingMenuAdapter();
        listView.setAdapter(adapter);
    }

    class SlidingMenuAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return newsMenuData.size();
        }

        @Override
        public NewsData.NewsMenuData getItem(int position) {
            return newsMenuData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = View.inflate(mActivity, R.layout.sliding_menu_list_item, null);
            TextView slidingItem = view.findViewById(R.id.sliding_item);
            slidingItem.setText(getItem(position).title);
            if (position == currentPosition) {
                slidingItem.setEnabled(true);
            } else {
                slidingItem.setEnabled(false);
            }
            return view;
        }
    }
}
