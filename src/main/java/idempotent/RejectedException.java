package idempotent;

/**
 * @author zhenghao
 */
public class RejectedException extends RuntimeException {
    private String id;
    private String resultJson;

    public RejectedException(String id, String resultJson) {
        super(null, null, true, false);
        this.id = id;
        this.resultJson = resultJson;
    }

    public String getId() {
        return id;
    }

    public String getResultJson() {
        return resultJson;
    }
}
