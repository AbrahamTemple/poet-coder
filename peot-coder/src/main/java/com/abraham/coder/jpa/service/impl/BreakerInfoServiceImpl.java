package com.abraham.coder.jpa.service.impl;

import com.abraham.coder.jpa.dto.NodeMatchDeduceDto;
import com.abraham.coder.jpa.entity.BreakerInfo;
import com.abraham.coder.jpa.service.BreakerInfoService;
import java.lang.Double;
import java.lang.String;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.benliu.jpa.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Repository;

@Repository
public class BreakerInfoServiceImpl extends BaseServiceImpl<BreakerInfo, String> implements BreakerInfoService {
  public List<NodeMatchDeduceDto> findBySubstationGisId(String subGisId) {
    List<NodeMatchDeduceDto> result = new ArrayList<>();
    return result.isEmpty() ? Collections.emptyList() : result;
  }

  public List<BreakerInfo> findMatchNode(Double latitude, Double longitude, Double radius) {
    List<BreakerInfo> result = new ArrayList<>();
    return result.isEmpty() ? Collections.emptyList() : result;
  }
}
