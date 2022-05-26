package com.abraham.coder.jpa.repository;

import com.abraham.coder.jpa.dto.NodeMatchDeduceDto;
import com.abraham.coder.jpa.entity.BreakerInfo;
import com.benliu.jpa.repository.BaseRepository;
import java.lang.Double;
import java.lang.String;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BreakerInfoRepository extends BaseRepository<BreakerInfo, String> {
  @Query(
      value = "",
      nativeQuery = true
  )
  List<NodeMatchDeduceDto> findBySubstationGisId(String subGisId);

  @Query(
      value = "",
      nativeQuery = true
  )
  List<BreakerInfo> findMatchNode(Double latitude, Double longitude, Double radius);
}
