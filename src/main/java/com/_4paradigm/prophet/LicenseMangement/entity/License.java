package com._4paradigm.prophet.LicenseMangement.entity;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;

import java.util.List;

import static com._4paradigm.prophet.LicenseMangement.entity.PASLicense.PAS_PRODUCT;
import static com._4paradigm.prophet.LicenseMangement.entity.PWSLicense.PWS_PRODUCT;
import static com._4paradigm.prophet.LicenseMangement.entity.ProphetLicense.PROPHET_PRODUCT;


@Data
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "product")
@JsonSubTypes({
        @Type(value = ProphetLicense.class, name = PROPHET_PRODUCT),
        @Type(value = PASLicense.class, name = PAS_PRODUCT),
        @Type(value = PWSLicense.class, name = PWS_PRODUCT)
})
public  class License {

    @JsonProperty("isTrial")
    public boolean trial;
    private int trialDays;
    private String product;
    private List<String> versionsSupported;
    private String issuedDate;
    private String  expiredDate;
    private String machineCode;
    private boolean available;
}
