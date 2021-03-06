package cn.fxn.svm.ui.refresh;

/**
 * @author:Matthew
 * @date:2018/10/24
 * @email:guocheng0816@163.com
 * @func:
 */
public final class PagingBean {
    /**
     * 当前时第几页
     */
    private int mPageIndex = 0;
    /**
     * 总数据条数
     */
    private int total = 0;
    /**
     * 一页显示几条数据
     */
    private int mPageSize = 0;
    /**
     * 当前已经显示了几条数据
     */
    private int mCurrentCount = 0;
    /**
     * 加载延迟
     */
    private int mDelayed = 0;

    public int getPageIndex() {
        return mPageIndex;
    }

    public PagingBean setPageIndex(int pageIndex) {
        mPageIndex = pageIndex;
        return this;
    }

    public int getTotal() {
        return total;
    }

    public PagingBean setTotal(int total) {
        this.total = total;
        return this;
    }

    public int getPageSize() {
        return mPageSize;
    }

    public PagingBean setPageSize(int pageSize) {
        mPageSize = pageSize;
        return this;
    }

    public int getCurrentCount() {
        return mCurrentCount;
    }

    public PagingBean setCurrentCount(int currentCount) {
        mCurrentCount = currentCount;
        return this;
    }

    public int getDelayed() {
        return mDelayed;
    }

    public PagingBean setDelayed(int delayed) {
        mDelayed = delayed;
        return this;
    }

    PagingBean addIndex() {
        mPageIndex++;
        return this;
    }
}
