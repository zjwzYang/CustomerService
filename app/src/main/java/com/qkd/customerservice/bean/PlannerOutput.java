package com.qkd.customerservice.bean;

import com.qkd.customerservice.net.BaseOutput;

import java.util.List;

/**
 * Created on 12/23/20 14:47
 * .
 *
 * @author yj
 * @org 趣看点
 */
public class PlannerOutput  extends BaseOutput {

    /**
     * errorMsg : null
     * data : {"total":9,"list":[{"id":1,"identifier":"test_yang","nick":"yangjie","faceUrl":"http://thirdwx.qlogo.cn/mmopen/0mzcpXkAgLicic3YtlFUaUVH5ws9tMn5qMicBc7D2KsCy17OibicGIPozSicmMbHAdGNZFMwlB0jORBdssQO8E1v2fXJ7CEZicRAPW2/132"},{"id":3,"identifier":"test_zhu","nick":"zzw","faceUrl":"http://thirdwx.qlogo.cn/mmopen/0mzcpXkAgLicic3YtlFUaUVH5ws9tMn5qMicBc7D2KsCy17OibicGIPozSicmMbHAdGNZFMwlB0jORBdssQO8E1v2fXJ7CEZicRAPW2/132"},{"id":4,"identifier":"test_li","nick":"lisi","faceUrl":"http://thirdwx.qlogo.cn/mmopen/0mzcpXkAgLicic3YtlFUaUVH5ws9tMn5qMicBc7D2KsCy17OibicGIPozSicmMbHAdGNZFMwlB0jORBdssQO8E1v2fXJ7CEZicRAPW2/132"},{"id":5,"identifier":"test_zzw","nick":"test_zzw","faceUrl":null},{"id":6,"identifier":"askjka","nick":"管管客服","faceUrl":"http://thirdwx.qlogo.cn/mmopen/0mzcpXkAgLicic3YtlFUaUVH5ws9tMn5qMicBc7D2KsCy17OibicGIPozSicmMbHAdGNZFMwlB0jORBdssQO8E1v2fXJ7CEZicRAPW2/132"},{"id":11,"identifier":"666","nick":"666","faceUrl":null},{"id":12,"identifier":"客服1","nick":"简易嘉兴网友","faceUrl":"http://qkbdev.qukandian573.com/files/pic/1608109484181418a2057-0dfc-4a20-a886-47e293f480d9.png"},{"id":13,"identifier":"askjka","nick":"管管客服","faceUrl":"http://sssssss"},{"id":14,"identifier":"askjka","nick":"管管客服","faceUrl":"http://sssssss"}],"pageNum":1,"pageSize":10,"size":9,"startRow":1,"endRow":9,"pages":1,"prePage":0,"nextPage":0,"isFirstPage":true,"isLastPage":true,"hasPreviousPage":false,"hasNextPage":false,"navigatePages":8,"navigatepageNums":[1],"navigateFirstPage":1,"navigateLastPage":1,"firstPage":1,"lastPage":1}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * total : 9
         * list : [{"id":1,"identifier":"test_yang","nick":"yangjie","faceUrl":"http://thirdwx.qlogo.cn/mmopen/0mzcpXkAgLicic3YtlFUaUVH5ws9tMn5qMicBc7D2KsCy17OibicGIPozSicmMbHAdGNZFMwlB0jORBdssQO8E1v2fXJ7CEZicRAPW2/132"},{"id":3,"identifier":"test_zhu","nick":"zzw","faceUrl":"http://thirdwx.qlogo.cn/mmopen/0mzcpXkAgLicic3YtlFUaUVH5ws9tMn5qMicBc7D2KsCy17OibicGIPozSicmMbHAdGNZFMwlB0jORBdssQO8E1v2fXJ7CEZicRAPW2/132"},{"id":4,"identifier":"test_li","nick":"lisi","faceUrl":"http://thirdwx.qlogo.cn/mmopen/0mzcpXkAgLicic3YtlFUaUVH5ws9tMn5qMicBc7D2KsCy17OibicGIPozSicmMbHAdGNZFMwlB0jORBdssQO8E1v2fXJ7CEZicRAPW2/132"},{"id":5,"identifier":"test_zzw","nick":"test_zzw","faceUrl":null},{"id":6,"identifier":"askjka","nick":"管管客服","faceUrl":"http://thirdwx.qlogo.cn/mmopen/0mzcpXkAgLicic3YtlFUaUVH5ws9tMn5qMicBc7D2KsCy17OibicGIPozSicmMbHAdGNZFMwlB0jORBdssQO8E1v2fXJ7CEZicRAPW2/132"},{"id":11,"identifier":"666","nick":"666","faceUrl":null},{"id":12,"identifier":"客服1","nick":"简易嘉兴网友","faceUrl":"http://qkbdev.qukandian573.com/files/pic/1608109484181418a2057-0dfc-4a20-a886-47e293f480d9.png"},{"id":13,"identifier":"askjka","nick":"管管客服","faceUrl":"http://sssssss"},{"id":14,"identifier":"askjka","nick":"管管客服","faceUrl":"http://sssssss"}]
         * pageNum : 1
         * pageSize : 10
         * size : 9
         * startRow : 1
         * endRow : 9
         * pages : 1
         * prePage : 0
         * nextPage : 0
         * isFirstPage : true
         * isLastPage : true
         * hasPreviousPage : false
         * hasNextPage : false
         * navigatePages : 8
         * navigatepageNums : [1]
         * navigateFirstPage : 1
         * navigateLastPage : 1
         * firstPage : 1
         * lastPage : 1
         */

        private int total;
        private int pageNum;
        private int pageSize;
        private int size;
        private int startRow;
        private int endRow;
        private int pages;
        private int prePage;
        private int nextPage;
        private boolean isFirstPage;
        private boolean isLastPage;
        private boolean hasPreviousPage;
        private boolean hasNextPage;
        private int navigatePages;
        private int navigateFirstPage;
        private int navigateLastPage;
        private int firstPage;
        private int lastPage;
        private List<ListBean> list;
        private List<Integer> navigatepageNums;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public int getPageNum() {
            return pageNum;
        }

        public void setPageNum(int pageNum) {
            this.pageNum = pageNum;
        }

        public int getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public int getStartRow() {
            return startRow;
        }

        public void setStartRow(int startRow) {
            this.startRow = startRow;
        }

        public int getEndRow() {
            return endRow;
        }

        public void setEndRow(int endRow) {
            this.endRow = endRow;
        }

        public int getPages() {
            return pages;
        }

        public void setPages(int pages) {
            this.pages = pages;
        }

        public int getPrePage() {
            return prePage;
        }

        public void setPrePage(int prePage) {
            this.prePage = prePage;
        }

        public int getNextPage() {
            return nextPage;
        }

        public void setNextPage(int nextPage) {
            this.nextPage = nextPage;
        }

        public boolean isIsFirstPage() {
            return isFirstPage;
        }

        public void setIsFirstPage(boolean isFirstPage) {
            this.isFirstPage = isFirstPage;
        }

        public boolean isIsLastPage() {
            return isLastPage;
        }

        public void setIsLastPage(boolean isLastPage) {
            this.isLastPage = isLastPage;
        }

        public boolean isHasPreviousPage() {
            return hasPreviousPage;
        }

        public void setHasPreviousPage(boolean hasPreviousPage) {
            this.hasPreviousPage = hasPreviousPage;
        }

        public boolean isHasNextPage() {
            return hasNextPage;
        }

        public void setHasNextPage(boolean hasNextPage) {
            this.hasNextPage = hasNextPage;
        }

        public int getNavigatePages() {
            return navigatePages;
        }

        public void setNavigatePages(int navigatePages) {
            this.navigatePages = navigatePages;
        }

        public int getNavigateFirstPage() {
            return navigateFirstPage;
        }

        public void setNavigateFirstPage(int navigateFirstPage) {
            this.navigateFirstPage = navigateFirstPage;
        }

        public int getNavigateLastPage() {
            return navigateLastPage;
        }

        public void setNavigateLastPage(int navigateLastPage) {
            this.navigateLastPage = navigateLastPage;
        }

        public int getFirstPage() {
            return firstPage;
        }

        public void setFirstPage(int firstPage) {
            this.firstPage = firstPage;
        }

        public int getLastPage() {
            return lastPage;
        }

        public void setLastPage(int lastPage) {
            this.lastPage = lastPage;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public List<Integer> getNavigatepageNums() {
            return navigatepageNums;
        }

        public void setNavigatepageNums(List<Integer> navigatepageNums) {
            this.navigatepageNums = navigatepageNums;
        }

        public static class ListBean {
            /**
             * id : 1
             * identifier : test_yang
             * nick : yangjie
             * faceUrl : http://thirdwx.qlogo.cn/mmopen/0mzcpXkAgLicic3YtlFUaUVH5ws9tMn5qMicBc7D2KsCy17OibicGIPozSicmMbHAdGNZFMwlB0jORBdssQO8E1v2fXJ7CEZicRAPW2/132
             */

            private int id;
            private String identifier;
            private String nick;
            private String faceUrl;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getIdentifier() {
                return identifier;
            }

            public void setIdentifier(String identifier) {
                this.identifier = identifier;
            }

            public String getNick() {
                return nick;
            }

            public void setNick(String nick) {
                this.nick = nick;
            }

            public String getFaceUrl() {
                return faceUrl;
            }

            public void setFaceUrl(String faceUrl) {
                this.faceUrl = faceUrl;
            }
        }
    }
}