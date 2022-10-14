package com.lgl.gms.webapi.common.util;

import java.util.HashMap;

import com.lgl.gms.webapi.common.dto.response.PagingListResponse;

import lombok.Getter;

/**
 * 페이징처리 유틸
 *
 */
public class PagingUtil {

    @Getter
    int page;

    @Getter
    int listSize;

    /**
     *
     * @param page     요청페이지
     * @param listSize 페이지사이즈
     */
    public PagingUtil(String page, String listSize) {
        this.page = Integer.parseInt(page);
        this.listSize = Integer.parseInt(listSize);
    }

    /**
     * 페이지사이즈를 기준으로한 전체페이지수
     *
     * @param totalCount
     * @return
     */
    public int getTotalPage(int totalCount) {
        return totalCount == 0 ? 0 : (totalCount / this.listSize + (totalCount % this.listSize > 0 ? 1 : 0));
    }

    /**
     * 페이징 쿼리 수행 시, 전달할 파라마터 객체
     *
     * @return
     */
    public HashMap<String, Object> getParam() {
        HashMap<String, Object> param = new HashMap<String, Object>();
        int stIdx = (this.page - 1) * this.listSize;
        int edIdx = (this.page) * this.listSize;
        param.put("stIdx", stIdx);
        param.put("edIdx", edIdx);
        return param;
    }

    /**
     * JPA Paging 처리객체
	 * 2022.02.14 JPA 기능 제거
     */
//    public PageRequest getPageRequest() {
//        return PageRequest.of(this.page, this.listSize);
//    }

    /**
     * Paging 리스트응답 객체 Wrapper
     *
     * @param totalCount
     * @return
     */  
    public PagingListResponse getDefaultListResponse(int totalCount) {
        return new PagingListResponse(totalCount, this.getTotalPage(totalCount));
	}
    
}
