package com.example.zsystudio.puzzlehigh.util.JsonBeans;

import java.util.List;

/**
 * Created by liaoyt on 16-5-1.
 */
public class GetPicListResponse {
    /**
     * success : 1
     * message : Get List successful.
     * list : [{"picID":"22","username":"liaoyt","postDate":"2016-05-01 22:40:42","url":"http://172.18.42.97/getPic.php?id=Image/2f8b/0c1c34f5c85b98e6bd6f16311f9a.bmp"},{"picID":"21","username":"zhangsan","postDate":"2016-04-13 18:20:53","url":"http://172.18.42.97/getPic.php?id=Image/52ad/9620f0624dca4235ce96da00243f.bmp"},{"picID":"20","username":"zhangsan","postDate":"2016-04-13 18:20:28","url":"http://172.18.42.97/getPic.php?id=Image/fd79/a8c4d41cdc27c2f9bccf3a62fadc.bmp"},{"picID":"19","username":"zhangsan","postDate":"2016-04-13 18:15:03","url":"http://172.18.42.97/getPic.php?id=Image/369a/58aadbfc7b7ce48d0f507c659683.bmp"},{"picID":"18","username":"zhangsan","postDate":"2016-04-13 13:40:43","url":"http://172.18.42.97/getPic.php?id=Image/c317/67e7c2f8c3a1852e40504d002c8e.bmp"},{"picID":"17","username":"zhangsan","postDate":"2016-04-13 13:38:12","url":"http://172.18.42.97/getPic.php?id=Image/8d38/1d7e826013378f001878d7932f22.bmp"},{"picID":"16","username":"zhangsan","postDate":"2016-04-13 13:31:02","url":"http://172.18.42.97/getPic.php?id=Image/28cf/f42a875bd97b8ce03b3d5b1ee0a0.bmp"},{"picID":"15","username":"zhangsan","postDate":"2016-04-13 13:28:07","url":"http://172.18.42.97/getPic.php?id=Image/3d18/bfd3394b5e5dbf76c5f95a809380.bmp"}]
     */

    private int success;
    private String message;
    /**
     * picID : 22
     * username : liaoyt
     * postDate : 2016-05-01 22:40:42
     * url : http://172.18.42.97/getPic.php?id=Image/2f8b/0c1c34f5c85b98e6bd6f16311f9a.bmp
     */

    private List<ListBean> list;

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        private String picID;
        private String username;
        private String postDate;
        private String url;

        public String getPicID() {
            return picID;
        }

        public void setPicID(String picID) {
            this.picID = picID;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPostDate() {
            return postDate;
        }

        public void setPostDate(String postDate) {
            this.postDate = postDate;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
