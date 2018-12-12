package com._4paradigm.prophet.LicenseMangement.entity;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class PWSLicense extends ComponentLicense {

    public static final String PWS_PRODUCT = "pws";

    private int maxCpuCores;
    private int maxGpuUnits;
    private boolean allowAll;
    private List<String> operatorWhitelist;

    public PWSLicense() {
        setProduct(PWS_PRODUCT);
    }
}
