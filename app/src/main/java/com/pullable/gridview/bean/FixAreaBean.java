package com.pullable.gridview.bean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/9/27.
 */

public class FixAreaBean {
    public boolean isOpen = false;
    public String fixTitle;
    public int selectedNum = 0;
    public ArrayList<FixContentBean> fixAreaNames = new ArrayList<>();

    public static class FixContentBean {
        public int spaceId;//空间ID
        public String spaceName;//空间名称
        public boolean isChecked = false;// 空间是否被选中
    }
}
