package com.kdf.etl.bean;

import java.util.Date;

import lombok.Data;

/**
 * 公用
 * 
 * @author mengpp
 * @date 2019年10月10日13:27:34
 *
 */
@Data
public class Common {

	/**
	 * id 主键
	 */
	private Long id;

	/**
	 * createTime 创建时间
	 */
	private Date createTime = new Date();

}
