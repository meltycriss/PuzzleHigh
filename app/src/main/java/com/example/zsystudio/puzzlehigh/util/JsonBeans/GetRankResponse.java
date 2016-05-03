package com.example.zsystudio.puzzlehigh.util.JsonBeans;

import java.util.List;

/**
 * Created by liaoyt on 16-5-1.
 */
public class GetRankResponse {
    /**
     * success : 1
     * message : Score successfully update.
     * topthree : [{"topUsername":"liaoyt","topScore":"30"},{"topUsername":"li si","topScore":"0"},{"topUsername":"wang wu","topScore":"0"}]
     * myRank : 1
     */

    private int success;
    private String message;
    private String myRank;
    /**
     * topUsername : liaoyt
     * topScore : 30
     */

    private List<TopthreeBean> topthree;

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

    public String getMyRank() {
        return myRank;
    }

    public void setMyRank(String myRank) {
        this.myRank = myRank;
    }

    public List<TopthreeBean> getTopthree() {
        return topthree;
    }

    public void setTopthree(List<TopthreeBean> topthree) {
        this.topthree = topthree;
    }

    public static class TopthreeBean {
        private String topUsername;
        private String topScore;

        public String getTopUsername() {
            return topUsername;
        }

        public void setTopUsername(String topUsername) {
            this.topUsername = topUsername;
        }

        public String getTopScore() {
            return topScore;
        }

        public void setTopScore(String topScore) {
            this.topScore = topScore;
        }
    }
}
