package com._4paradigm.prophet.LicenseMangement.entity;

import lombok.Data;

import java.util.Map;

@Data
public class AlgoFileUpdateRequest {
    String algo;
    Map<String,Map<String,String>> map;
    String ScenarioType;
}