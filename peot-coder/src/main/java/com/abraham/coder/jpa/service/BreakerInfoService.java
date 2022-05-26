package com.abraham.coder.jpa.service;

import com.abraham.coder.jpa.dto.NodeMatchDeduceDto;
import com.abraham.coder.jpa.entity.BreakerInfo;
import com.benliu.jpa.service.BaseService;
import java.lang.Double;
import java.lang.String;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface BreakerInfoService extends BaseService<BreakerInfo, String> {
  List<NodeMatchDeduceDto> findBySubstationGisId(String subGisId);

  List<BreakerInfo> findMatchNode(Double latitude, Double longitude, Double radius);
}
