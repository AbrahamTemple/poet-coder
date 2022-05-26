package com.abraham.coder.jpa.service;

import com.abraham.coder.jpa.dto.NodeMatchDeduceDto;
import com.abraham.coder.jpa.entity.BreakerInfo;
import com.benliu.jpa.service.BaseService;
import java.lang.Double;
import java.lang.String;
import java.util.List;

public interface BreakerInfoService extends BaseService<BreakerInfo, String> {
  List<NodeMatchDeduceDto> findBySubstationGisId(String subGisId);

  List<BreakerInfo> findMatchNode(Double latitude, Double longitude, Double radius);
}
