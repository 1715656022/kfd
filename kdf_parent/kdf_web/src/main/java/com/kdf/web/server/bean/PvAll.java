package com.kdf.web.server.bean;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "kfd_pv_all")
@Data
public class PvAll {

	/**
     * id 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     *  appid
     */
    private String appid;

    /**
     * 请求次数
     */
    private Integer pvCount;

    /**
     * createTime 创建时间
     */
    private Date createTime;

    /**
     * requestTime 请求时间（yyyy-mm-dd HH:00:00）
     */
    private Date requestTime;
}
