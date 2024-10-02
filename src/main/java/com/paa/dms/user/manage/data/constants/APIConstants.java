package com.paa.dms.user.manage.data.constants;

public class APIConstants {

    //Externalized endpoint paths
    public static final String BASE_PATH = "${constants.api.uri.basePath}";
    public static final String CREATE_NEW_USER_ENDPOINT = "${constants.api.uri.userNewData.path}";
    public static final String READ_USER_DATA_ENDPOINT = "${constants.api.uri.userDataRead.path}";
    public static final String UPDATE_USER_DATA_ENDPOINT = "${constants.api.uri.userDataUpdate.path}";
    public static final String DELETE_USER_DATA_ENDPOINT = "${constants.api.uri.userDropData.path}";

    //Service call log messages
    public static final String SERVICE_START = "MS started : paams-d-userManageData";
    public static final String LOG_NEW_USER_ENDPOINT = "[Service endpoint Call - userNewData]";
    public static final String LOG_READ_USER_ENDPOINT = "[Service endpoint Call - userDataRead]";
    public static final String LOG_UPDATE_USER_ENDPOINT = "[Service endpoint Call - userDataUpdate]";
    public static final String LOG_DELETE_USER_ENDPOINT = "[Service endpoint Call - userDropData]";

    //Error resolver log messages
    public static final String RESPONSE_STRING_HTTP_EMPTY = "RESPONSE >>> HTTP STATUS ";
    public static final String EXCEPTION_MSG_UNEXPECTED = "An unexpected error occurred";
    public static final String EXCEPTION_MSG_NO_DATA_FOUND = "Resource not found in DB";
    public static final String EXCEPTION_MSG_FORBIDDEN = "Invalid Request due to data validation";


}
