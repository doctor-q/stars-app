package cc.doctor.stars_app.http;

public class PageRequest<T> implements ToMap {
    private Integer pageNo;
    private Integer pageSize;
    private T data;

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public static <D> PageRequest<D> pageRequest(int pageNo, int pageSize) {
        PageRequest<D> request = new PageRequest<>();
        request.setPageNo(pageNo);
        request.setPageSize(pageSize);
        return request;
    }
}
