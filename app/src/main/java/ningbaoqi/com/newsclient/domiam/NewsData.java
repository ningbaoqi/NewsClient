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
 * 创建 时间:18-5-21
 * <p/>
 * 描    述:
 * <p/>
 * <p/>
 * 修订 历史:
 * <p/>
 * =========================================
 * 网络分类信息的封装 :注意封装的字段名字要和服务器上的相对应，否则Gson解析的时候无法准确赋值，需要看JSON数据的格式，然后设计JavaBean
 */

public class NewsData {
    public int retcode;
    public ArrayList<NewsMenuData> data;

    public class NewsMenuData {//侧边栏数据对象
        public String id;
        public String title;
        public int type;
        public String url;
        public ArrayList<NewsTabData> children;

        @Override
        public String toString() {
            return "NewsMenuData{" +
                    "id='" + id + '\'' +
                    ", title='" + title + '\'' +
                    ", type=" + type +
                    ", url='" + url + '\'' +
                    ", children=" + children +
                    '}';
        }
    }

    public class NewsTabData {//新闻页面下11个子标签的数据对象
        public String id;
        public String title;
        public int type;
        public String url;

        @Override
        public String toString() {
            return "NewsTabData{" +
                    "id='" + id + '\'' +
                    ", title='" + title + '\'' +
                    ", type=" + type +
                    ", url='" + url + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "NewsData{" +
                "retcode=" + retcode +
                ", data=" + data +
                '}';
    }
}
