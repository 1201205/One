package com.hyc.zhihu.beans;

import java.util.List;

public class IDList extends BaseBean<List<String>> implements java.io.Serializable {
    private static final long serialVersionUID = -1122228010512198195L;

    @Override
    public String toString() {
        if (data!=null &&data.size()>0) {
            StringBuilder builder=new StringBuilder();
            for (String s:data) {
                builder.append(s).append("-");
            }
            builder.substring(0,builder.length()-1);
            return builder.toString();
        }
        return "";
    }
}
