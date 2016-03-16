package com.sonnyzoom.viewpagerad.bean;

import java.util.List;

/**
 * Created by zoom on 2016/3/14.
 */
public class DataInfo {

    /**
     * state : 0
     * message :
     * request_id : 0ab6004614579545112068700e
     * data : {"imgs":[{"url":"collect:129942414","pic_url_yasha":"http://img.xiami.net/images/common/uploadpic/61/14579205615743.jpg"},{"url":"collect:9748655","pic_url_yasha":"http://img.xiami.net/images/common/uploadpic/34/14579205347401.jpg"},{"url":"collect:147658204","pic_url_yasha":"http://img.xiami.net/images/common/uploadpic/30/14576631301255.jpg"},{"url":"album:2100291056","pic_url_yasha":"http://img.xiami.net/images/common/uploadpic/41/14576879417584.jpg"},{"url":"collect:149076059","pic_url_yasha":"http://img.xiami.net/images/common/uploadpic/41/14575745416990.jpg"},{"url":"collect:149871871","pic_url_yasha":"http://img.xiami.net/images/common/uploadpic/62/14575270624949.jpg"}]}
     */

    private int state;
    private String message;
    private String request_id;
    private DataEntity data;

    public void setState(int state) {
        this.state = state;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setRequest_id(String request_id) {
        this.request_id = request_id;
    }

    public void setData(DataEntity data) {
        this.data = data;
    }

    public int getState() {
        return state;
    }

    public String getMessage() {
        return message;
    }

    public String getRequest_id() {
        return request_id;
    }

    public DataEntity getData() {
        return data;
    }

    public static class DataEntity {
        /**
         * url : collect:129942414
         * pic_url_yasha : http://img.xiami.net/images/common/uploadpic/61/14579205615743.jpg
         */

        private List<ImgsEntity> imgs;

        public void setImgs(List<ImgsEntity> imgs) {
            this.imgs = imgs;
        }

        public List<ImgsEntity> getImgs() {
            return imgs;
        }

        @Override
        public String toString() {
            return "DataEntity{" +
                    "imgs=" + imgs +
                    '}';
        }

        public static class ImgsEntity {

            private String url;
            private String pic_url_yasha;

            public void setUrl(String url) {
                this.url = url;
            }

            public void setPic_url_yasha(String pic_url_yasha) {
                this.pic_url_yasha = pic_url_yasha;
            }

            public String getUrl() {
                return url;
            }

            public String getPic_url_yasha() {
                return pic_url_yasha;
            }

            @Override
            public String toString() {
                return "ImgsEntity{" +
                        "url='" + url + '\'' +
                        ", pic_url_yasha='" + pic_url_yasha + '\'' +
                        '}';
            }
        }
    }
}
