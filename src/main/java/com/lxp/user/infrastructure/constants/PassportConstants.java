package com.lxp.user.infrastructure.constants;

/**
 * Passport 이름 및 관련 상수 정의
 */
public class PassportConstants {

    private PassportConstants() {
    } // 인스턴스화 방지

    public static final String PASSPORT_HEADER_NAME = "X-Passport";

    public static final String PASSPORT_USER_ID = "uid";
    public static final String PASSPORT_ROLE = "rol";
    public static final String PASSPORT_TRACE_ID = "tid";
}
