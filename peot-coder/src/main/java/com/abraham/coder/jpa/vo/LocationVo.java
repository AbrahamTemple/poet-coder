/**
 * 
 */
package com.abraham.coder.jpa.vo;

import com.benliu.jpa.annotation.JpaDto;
import lombok.Data;

/**
 * @ClassName LocationVo
 * @author
 * @since 2022-4-25 2:47:55
 */
@JpaDto
@Data
public class LocationVo {
	
	//纬度
	private String latitude;
	
	//经度
	private String longitude;
}
