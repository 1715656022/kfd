package com.kdf.etl.bean;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

/**
 * 公用
 * 
 * @author mengpp
 * @date 2019年10月10日13:27:34
 *
 */
@Data
@Entity
public class Common {

	/**
	 * id 主键
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/**
	 * createTime 创建时间
	 */
	private Date createTime = new Date();

}
