package com.kdf.etl.bean;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "kfd_pv_all")
public class PvAll extends Common {

	/**
	 * appid APPID
	 */
	private String appid;

	/**
	 * pv总数
	 */
	private Long pvCount;

	/**
	 * RequestTime 请求时间（yyyy-mm-dd HH:00:00）
	 */
	private Date requestTime;

}
