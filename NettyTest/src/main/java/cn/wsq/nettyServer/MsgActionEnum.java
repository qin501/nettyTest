package cn.wsq.nettyServer;

/*
* 消息接收类型
* */
public enum MsgActionEnum {
    CONNECT(1, "第一次(或重连)初始化连接"),
    CHAT(2, "聊天消息"),
    SIGNED(3, "消息签收"),
    KEEPALIVE(4, "客户端保持心跳"),
    PULL_FRIEND(5, "拉取好友"),
    ISLOGIN(6, "已经登录");
    public final Integer type;
    public final String content;
    MsgActionEnum(Integer type,String content){
        this.type=type;
        this.content=content;
    }

    public Integer getType() {
        return type;
    }
}
