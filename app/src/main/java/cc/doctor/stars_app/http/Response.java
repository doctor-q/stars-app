package cc.doctor.stars_app.http;

public class Response<T> {

    public static final int SUCCESS_CODE = 200;

    public static final int CALL_FAIL = -1;

    private int code;

    private String msg;

    private boolean success;

    private T data;

    public static <D> Response<D> fail(int code, String message) {
        Response<D> response = new Response<>();
        response.setCode(code);
        response.setMsg(message);
        return response;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
