

public class Message {
    private String id;
    private String passwd;
    private String msg;
    private String type;
    private String googling;

    public Message(){

    }

    public Message(String id, String passwd, String msg, String type){
        this.id = id;
        this.passwd = passwd;
        this.msg = msg;
        this.type = type;
    }

    public Message(String id, String passwd, String msg, String type, String googling){
        this.id = id;
        this.passwd = passwd;
        this.msg = msg;
        this.type = type;
        this.googling = googling;
    }

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public String getPasswd() { return passwd; }

    public void setPasswd(String passwd) { this.passwd = passwd; }

    public String getMsg() { return msg; }

    public void setMsg(String msg) { this.msg = msg; }

    public String getType() { return type; }

    public void setType(String type) { this.type = type; }

    public String getGoogling() { return googling; }

    public void setGoogling(String googling) { this.googling = googling; }
}
