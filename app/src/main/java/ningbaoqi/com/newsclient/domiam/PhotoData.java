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
 * 创建 时间:18-5-24
 * <p/>
 * 描    述:
 * <p/>
 * <p/>
 * 修订 历史:
 * <p/>
 * =========================================
 * 组图数据
 */

public class PhotoData {
    public int retcode;
    public PhtotsInfo data;

    public class PhtotsInfo {
        public String title;
        public ArrayList<PhtotInfo> news;
    }

    public class PhtotInfo {
        public String id;
        public String listimage;
        public String pubdate;
        public String title;
        public String url;
        public String type;
    }
}
