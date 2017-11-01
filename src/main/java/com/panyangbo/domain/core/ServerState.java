package com.panyangbo.domain.core;

/**
 * 服务状态
 * @author 潘洋波[panybest1991@gmail.com]
 * @Date 2017/7/17 11:09
 * @Description ServerState
 * @version 4.0.0
 */
public enum ServerState {

    STATE_CONNECT_SUCCESS_LOGIN_FAIL(0),
    STATE_CONNECT_FAIL(-1),
    STATE_LOGIN_SUCCESS(1);

    private final int state;


    ServerState(int state) {
        this.state = state;
    }

    public int getState() {
        return state;
    }

}
