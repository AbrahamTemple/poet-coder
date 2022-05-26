package com.abraham.coder.jpa.dto;

import com.abraham.coder.jpa.vo.LocationVo;
import com.benliu.jpa.annotation.JpaColumn;
import com.benliu.jpa.annotation.JpaDto;
import java.lang.Integer;
import java.lang.String;
import java.util.List;
import lombok.Data;

@JpaDto
@Data
public class NodeMatchDeduceDto {
  @JpaColumn("node_type")
  private Integer nodeType;

  @JpaColumn("node_code")
  private String nodeCode;

  @JpaColumn("node_name")
  private String nodeName;

  @JpaColumn("transformer_type")
  private Integer transformerType;

  @JpaColumn("location")
  private String location;

  @JpaColumn("location_list")
  private List<List<LocationVo>> locationList;
}
