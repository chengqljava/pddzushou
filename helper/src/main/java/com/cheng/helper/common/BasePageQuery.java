package com.cheng.helper.common;

public class BasePageQuery extends BaseQuery {

    /**  */
    private static final long serialVersionUID = 1L;
    private int               page             = 1;
    private int               size             = 40;

    protected BasePageQuery() {
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = (page <= 0 ? 1 : page);
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = (size <= 0 ? 40 : size);
    }

}
