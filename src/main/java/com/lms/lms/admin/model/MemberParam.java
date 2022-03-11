package com.lms.lms.admin.model;

import lombok.Data;

@Data
public class MemberParam {
    long pageIndex;
    long pageSize;
    long totalCount;
    String searchType;
    String searchValue;

    public long getpageStart(){
        init();
        return (pageIndex-1)*pageSize;
    }

    public long getpageEnd(){
        init();
        return pageSize;

    }


    public String getQueryString() {
        init();
        StringBuilder sb = new StringBuilder();

        if(searchType != null && searchType.length()>0){
            sb.append(String.format("searchType=%s",searchType));
        }


        if(searchValue != null && searchValue.length()>0){
            if(sb.length()>0){
                sb.append("&");
            }
            sb.append(String.format("searchValue=%s",searchValue));
        }

        return sb.toString();
    }
    public void init() {
        if (pageIndex < 1) {
            pageIndex = 1;
        }
        if (pageSize < 10) {
            pageSize = 10;
        }
    }
}
