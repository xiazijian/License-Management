package com._4paradigm.prophet.LicenseMangement.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BaseResponse {

    public static final int STATUS_SUCCESS = 0;
    public static final int STATUS_INTERNAL_ERROR = 500;
    public static final int STATUS_PERMISSION_DENIED = 401;
    public static final int STATUS_NOT_FOUND = 404;
    public static final int STATUS_BAD_REQUEST = 400;
    public static final int STATUS_TOO_MANY_REQUEST = 429;

    public static final int STATUS_EXPERIMENT_NOT_READY = 101;
    public static final int STATUS_EXPERIMENT_DATA_SET_NOT_READY = 102;
    public static final int STATUS_EXPERIMENT_NOT_FOUND = 103;
    public static final int STATUS_EVALUATION_NOT_READY = 104;
    public static final int STATUS_APP_NOT_READY = 111;
    public static final int STATUS_KS_UNAVAILABLE = 131;
    public static final int STATUS_PWS_UNAVAILABLE = 151;
    public static final int STATUS_PAS_UNAVAILABLE = 171;
    public static final int STATUS_PAS_LICENSE_EXCEEDED = 172;
    public static final int STATUS_PDMS_UNAVAILABLE = 191;
    public static final int STATUS_AGLO_DEPLOY_FAILED = 211;
    public static final int STATUS_AGLO_DEPLOY_BAD_PROTOCOL = 212;
    public static final int STATUS_AGLO_DEPLOY_ILLEGAL_ARGS = 213;
    public static final int STATUS_AGLO_DEPLOY_HDFS_TRANSFER_ERROR = 214;
    public static final int STATUS_AGLO_DEPLOY_HDFS_PATH_ERROR = 215;
    public static final int STATUS_AGLO_DEPLOY_STREAM_UPLOAD_ERROR = 216;
    public static final int STATUS_PROJECT_OR_ALGO_NOT_FOUND = 231;
    public static final int STATUS_FILE_STORAGE_ERROR = 251;
    public static final int STATUS_APP_EXPERIMENT_NOT_FOUND = 271;
    public static final int STATUS_APP_LAST_KEY_DELETION_DENIED = 272;
    public static final int STATUS_PACKAGE_NOT_FOUND = 291;
    public static final int STATUS_POSTER_SIZE_TOO_LARGE = 292;
    public static final int STATUS_PACKAGE_NOT_READY = 293;
    public static final int STATUS_TEMPLATE_IMAGE_IN_USE = 311;
    public static final int STATUS_EXPERIMENT_TYPE_ERROR = 105;

    private int status;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Object data;

    public BaseResponse() {

    }

    public BaseResponse(int status) {
        this.status = status;
    }

    public static BaseResponse success() {
        return new BaseResponse(STATUS_SUCCESS);
    }

    public static BaseResponse success(Object data) {
        return new BaseResponse(STATUS_SUCCESS, data);
    }

    public static BaseResponse permissionDenied() {
        return new BaseResponse(STATUS_PERMISSION_DENIED);
    }

    public static BaseResponse internalServerError() {
        return new BaseResponse(STATUS_INTERNAL_ERROR);
    }

    public static BaseResponse badRequest() {
        return new BaseResponse(STATUS_BAD_REQUEST);
    }

    public static BaseResponse notFound() {
        return new BaseResponse(STATUS_NOT_FOUND);
    }

    public static BaseResponse tooManyRequest() {
        return new BaseResponse(STATUS_TOO_MANY_REQUEST);
    }

    public static BaseResponse experimentNotReady() {
        return new BaseResponse(STATUS_EXPERIMENT_NOT_READY);
    }

    public static BaseResponse evaluationNotReady() {
        return new BaseResponse(STATUS_EVALUATION_NOT_READY);
    }

    public static BaseResponse templateImageInUse() {
        return new BaseResponse(STATUS_TEMPLATE_IMAGE_IN_USE);
    }

    public static BaseResponse appNotReady() {
        return new BaseResponse(STATUS_APP_NOT_READY);
    }

    public static BaseResponse ksUnavailable() {
        return new BaseResponse(STATUS_KS_UNAVAILABLE);
    }

    public static BaseResponse pwsUnavailable() {
        return new BaseResponse(STATUS_PWS_UNAVAILABLE);
    }

    public static BaseResponse pasUnavailable() {
        return new BaseResponse(STATUS_PAS_UNAVAILABLE);
    }

    public static BaseResponse pasLicenseExceeded() {
        return new BaseResponse(STATUS_PAS_LICENSE_EXCEEDED);
    }

    public static BaseResponse pdmsUnavailable() {
        return new BaseResponse(STATUS_PDMS_UNAVAILABLE);
    }

}