package com.kdf.etl.service;

import com.beust.jcommander.internal.Lists;
import com.beust.jcommander.internal.Maps;
import com.kdf.etl.bean.PvUrl;
import com.kdf.etl.repository.PvUrlRepository;
import com.kdf.etl.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.hadoop.hive.HiveClient;
import org.springframework.data.hadoop.hive.HiveClientCallback;
import org.springframework.data.hadoop.hive.HiveTemplate;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class PvUrlService {

    @Autowired
    private PvUrlRepository pvUrlRepository;

    @Autowired
    private HiveTemplate hiveTemplate;

    /**
     *
     */
    public void hiveToMysql(){
        List<Map<String, String>> list = getUrlPv("2019_10_11_14");
        list.forEach(temp ->{
            PvUrl pvUrl = new PvUrl();
            pvUrl.setAppId(temp.get("appId"));
            pvUrl.setIp(temp.get("ip"));
            pvUrl.setUrl(temp.get("url"));
            pvUrl.setCreateTime(new Date());
            pvUrl.setRequestTime(TimestampToDate(Integer.parseInt(temp.get("requestTime"))));
            pvUrlRepository.save(pvUrl);
        });
    }

    /**
     * 10位时间戳转Date
     * @param time
     * @return
     */
    public static Date TimestampToDate(Integer time){
        long temp = (long)time*1000;
        Timestamp ts = new Timestamp(temp);
        Date date = new Date();
        try {
            date = ts;
            //System.out.println(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }
    private List<Map<String, String>> getUrlPv(String yearMonthDayHour) {
        StringBuffer hiveSql = new StringBuffer();
        hiveSql.append("select appid, url, ip, url, request_time from pv_log_hive_");
        hiveSql.append(yearMonthDayHour);
        if(log.isInfoEnabled()) {
            log.info("getBrowserUv  hiveSql=[{}]", hiveSql.toString());
        }
        List<Map<String, String>> resultList = hiveTemplate.execute(new HiveClientCallback<List<Map<String, String>>>() {
            @Override
            public List<Map<String, String>> doInHive(HiveClient hiveClient) throws Exception {
                List<Map<String, String>> urlList = Lists.newArrayList();
                Connection conn = hiveClient.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(hiveSql.toString());
                while(rs.next()) {
                    String ip = rs.getString("ip");
                    String url = rs.getString("url");
                    String requestTime = rs.getString("request_time");
                    String appid = rs.getString("appid");
                    Map<String, String> map = Maps.newHashMap();
                    map.put("requestTime", requestTime);
                    map.put("url", url);
                    map.put("ip", ip);
                    map.put("appId", appid);
                    urlList.add(map);
                }
                return urlList;
            }
        });
        log.info("resultList========={}", resultList);
        return resultList;
    }
}
