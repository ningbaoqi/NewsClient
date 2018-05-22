package ningbaoqi.com.newsclient.domiam;

import java.util.ArrayList;

/**
 * =========================================
 * <p/>
 * 版    权: ningxiansheng
 * <p/>
 * 作    者: 宁宝琪
 * <p/>
 * 版    本:1.0
 * <p/>
 * 创建 时间:18-5-22
 * <p/>
 * 描    述:
 * <p/>
 * <p/>
 * 修订 历史:
 * <p/>
 * =========================================
 */

public class TabData {
    public int retcode;
    public TabDetail data;

    public class TabDetail {
        public String title;
        public String more;
        public ArrayList<TabNewsData> news;
        public ArrayList<TopNewsData> topnews;

        @Override
        public String toString() {
            return "TabDetail{" +
                    "title='" + title + '\'' +
                    ", more='" + more + '\'' +
                    ", news=" + news +
                    ", topnews=" + topnews +
                    '}';
        }
    }

    public class TabNewsData {
        public String id;
        public String listimage;
        public String pubdate;
        public String title;
        public String type;
        public String url;

        @Override
        public String toString() {
            return "TabNewsData{" +
                    "id='" + id + '\'' +
                    ", listimage='" + listimage + '\'' +
                    ", pubdate='" + pubdate + '\'' +
                    ", title='" + title + '\'' +
                    ", type='" + type + '\'' +
                    ", url='" + url + '\'' +
                    '}';
        }
    }

    public class TopNewsData {
        public String id;
        public String topimage;
        public String pubdate;
        public String type;
        public String title;
        public String url;

        @Override
        public String toString() {
            return "TopNewsData{" +
                    "id='" + id + '\'' +
                    ", topimage='" + topimage + '\'' +
                    ", pubdate='" + pubdate + '\'' +
                    ", type='" + type + '\'' +
                    ", title='" + title + '\'' +
                    ", url='" + url + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "TabData{" +
                "retcode=" + retcode +
                ", data=" + data +
                '}';
    }
}
