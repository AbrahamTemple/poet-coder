/**
 * 
 */
package com.abraham.coder.jpa.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @ClassName BreakerInfo
 * @author
 * @since 2022-4-19 10:01:50
 */
@Data
@Entity
@Table(name="BREAKER_INFO")
public class BreakerInfo {
	
	@Id
	@Column(name = "GISID",columnDefinition = "VARCHAR(64)")
	private String gisId;
	
	/**
	 * 首端开关主网侧编码
	 */
	@Column(name = "BREAKER_CODE",columnDefinition = "VARCHAR2(64)")
	private String breakerCode;
	
	/**
	 * 首端开关名称
	 */
	@Column(name = "BREAKER_NAME",columnDefinition = "VARCHAR2(128)")
	private String breakerName;
	
	/**
	 * 变电站GISID
	 */
	@Column(name = "SUBSTATION_GISID",columnDefinition = "VARCHAR2(64)")
	private String substationGisId;
	
	@Column(name = "BREAKER_MAIN_ID",columnDefinition = "VARCHAR2(64)")
	private String breakerMainId;
	
	@Column(name = "LOCATION",columnDefinition = "CLOB")
	private String location;
	
	@JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@CreatedDate
	@Column(name = "created_time", columnDefinition = "DATE")
	private Date createdTime;
	/**
	 * 最后更新时间
	 */
	@JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@LastModifiedDate
	@Column(name = "last_modify", columnDefinition = "DATE")
	private Date lastModify;
}
