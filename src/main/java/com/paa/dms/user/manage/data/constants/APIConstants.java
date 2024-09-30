package com.paa.dms.user.manage.data.constants;

public class APIConstants {

    //Externalized endpoint paths
    public static final String BASE_PATH = "${constants.api.uri.basePath}";
    public static final String CREATE_NEW_USER_ENDPOINT = "${constants.api.uri.userNewData.path}";
    public static final String READ_USER_DATA_ENDPOINT = "${constants.api.uri.userDataRead.path}";
    public static final String UPDATE_USER_DATA_ENDPOINT = "${constants.api.uri.userDataUpdate.path}";
    public static final String DELETE_USER_DATA_ENDPOINT = "${constants.api.uri.userDropData.path}";

    //Service call log messages
    public static final String LOG_NEW_USER_ENDPOINT = "[Service endpoint Call - userNewData]";
    public static final String LOG_READ_USER_ENDPOINT = "[Service endpoint Call - userDataRead]";
    public static final String LOG_UPDATE_USER_ENDPOINT = "[Service endpoint Call - userDataUpdate]";
    public static final String LOG_DELETE_USER_ENDPOINT = "[Service endpoint Call - userDropData]";

    //Service process log messages
    public static final String RESPONSE_STRING_HTTP_EMPTY = "RESPONSE >>> HTTP STATUS ";
    public static final String RESPONSE_STRING_HTTP403 = "RESPONSE >>> HTTP STATUS 403 FORBIDDEN";
    public static final String RESPONSE_STRING_HTTP404 = "RESPONSE >>> HTTP STATUS 404 NOT FOUND";


}
