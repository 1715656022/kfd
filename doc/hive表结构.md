# hive/hbase表结构设计

```sql
create database my_hive;
```

```shell
create 'pv_log','log';
```



## pv_log_hive 数据收集表

| 字段名称     | 类型   | 备注     |
| ------------ | ------ | -------- |
| key          | string |          |
| user_agent   | string |          |
| method       | string |          |
| ip           | string |          |
| port         | string |          |
| url          | string |          |
| request_time | string | 创建时间 |

```sql
CREATE EXTERNAL TABLE pv_log_hive 
(key string, user_agent string, method string,ip string,port string,url string,request_time string)  
STORED BY 'org.apache.hadoop.hive.hbase.HBaseStorageHandler'  
WITH SERDEPROPERTIES ("hbase.columns.mapping" = ":key,log:user_agent,log:method,log:ip,log:port,log:url,log:request_time")  
TBLPROPERTIES ("hbase.table.name" = "pv_log");
```

