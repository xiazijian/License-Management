package com._4paradigm.prophet.LicenseMangement.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class PASLicense extends ComponentLicense {

    public static final String PAS_PRODUCT = "pas";

    private int maxCpuCores;
    private int maxGpuUnits;

    public PASLicense() {
        setProduct(PAS_PRODUCT);
    }
}
