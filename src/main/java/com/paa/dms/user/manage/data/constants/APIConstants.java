package com.paa.dms.user.manage.data.constants;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class APIConstants {

    //Endpoint paths
    public static final String BASE_PATH = "${constants.api.uri.basePath}";
    public static final String CREATE_NEW_USER_ENDPOINT = "${constants.api.uri.userNewData.path}";
    public static final String READ_USER_DATA_ENDPOINT = "${constants.api.uri.userDataRead.path}";
    public static final String UPDATE_USER_DATA_ENDPOINT = "${constants.api.uri.userDataUpdate.path}";
    public static final String DELETE_USER_DATA_ENDPOINT = "${constants.api.uri.userDropData.path}";

    //Service call log messages
    @Value("${service.api.name}")
    private String SERVICE_NAME;
    @Value("${constants.api.uri.userNewData.call}")
    private String LOG_NEW_USER_ENDPOINT;
    @Value("${constants.api.uri.userDataRead.call}")
    private String LOG_READ_USER_ENDPOINT;
    @Value("${constants.api.uri.userDataUpdate.call}")
    private String LOG_UPDATE_USER_ENDPOINT;
    @Value("${constants.api.uri.userDropData.call}")
    private String LOG_DELETE_USER_ENDPOINT;

    //Error resolver log messages
    @Value("${constants.api.uri.errors.msg.unexpected}")
    private String EXCEPTION_MSG_UNEXPECTED;
    @Value("${constants.api.uri.errors.msg.noDataFound}")
    private String EXCEPTION_MSG_NO_DATA_FOUND;
    @Value("${constants.api.uri.errors.msg.forbidden}")
    private String EXCEPTION_MSG_FORBIDDEN;
    @Value("${constants.api.uri.errors.msg.badRequest}")
    private String exceptionBadRequest;

    //Swagger Constants
    @Value("${service.api.version}")
    private String SERVICE_VERSION;
    @Value("${service.api.details}")
    private String SERVICE_DESCRIPTION;
    @Value("${service.api.dev.name}")
    private String DEV_NAME;
    @Value("${service.api.dev.web}")
    private String DEV_WEB;
    @Value("${service.api.dev.email}")
    private String DEV_EMAIL;

}
