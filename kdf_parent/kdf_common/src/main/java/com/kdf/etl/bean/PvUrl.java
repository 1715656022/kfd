package com.kdf.etl.bean;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "kfd_pv_url")
@Data
public class PvUrl {
    /**
     * id 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     *  appid
     */
    private Long appId;

    /**
     * ip
     */
    private String ip;

    /**
     * url
     */
    private String url;

    /**
     * createTime 创建时间
     */
    private Date createTime ;
}
