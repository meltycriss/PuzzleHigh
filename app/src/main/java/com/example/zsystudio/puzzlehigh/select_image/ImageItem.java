package com.example.zsystudio.puzzlehigh.select_image;

import com.example.zsystudio.puzzlehigh.R;

/**
 * Created by liaoyt on 16-4-13.
 */
public class ImageItem {
    private String text;
    private String url;
    private int id;

    public ImageItem() {
        text = null;
        url = null;
        id = R.drawable.main_logo;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public String getUrl() {
        return url;
    }

    public int getId() {
        return id;
    }
}
