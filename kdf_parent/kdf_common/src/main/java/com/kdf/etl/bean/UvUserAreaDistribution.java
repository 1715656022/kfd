package com.kdf.etl.bean;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;

/**
 * 用户地域分布实体
 * 
 * @ClassName: UvUserAreaDistribution
 * @author: PéiGǔangTíng QQ：1396968024
 * @date: 2019年10月12日 上午11:11:14
 */
@Data
@Entity
@Table(name = "kfd_uv_user_area_distribution")
public class UvUserAreaDistribution extends Common {

    /**
     * 启动次数
     */
    private Long uvStartCount;

    /**
     * 分布地区
     */
    private Long uvArea;

}
