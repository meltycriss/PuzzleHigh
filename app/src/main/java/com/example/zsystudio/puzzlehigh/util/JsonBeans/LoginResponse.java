package com.example.zsystudio.puzzlehigh.util.JsonBeans;

/**
 * Created by liaoyt on 16-5-1.
 */
public class LoginResponse {
    /**
     * success : 1
     * message : User successfully login.
     * score : 519
     */

    private int success;
    private String message;

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    private int score;

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
}
