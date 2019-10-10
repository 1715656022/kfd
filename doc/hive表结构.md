

# hive/hbase表结构设计

##  数据收集表

### hbase表结构

表名称 pv_log_yyyy_mm_dd_HH

```sql
create database my_hive;
```

```shell
create 'pv_log_yyyy_mm_dd_HH','log';
```

### hive表结构 

表名称：pv_log_hive_yyyy_mm_dd_HH

| 字段名称     | 类型   | 备注                |
| ------------ | ------ | ------------------- |
| key          | string |                     |
| appid        | string | 项目appid           |
| user_agent   | string |                     |
| method       | string |                     |
| ip           | string |                     |
| port         | string |                     |
| url          | string |                     |
| request_time | string | 请求时间 10位时间戳 |

```sql
CREATE EXTERNAL TABLE pv_log_hive 
(key string, user_agent string, method string,ip string,port string,url string,request_time string)  
STORED BY 'org.apache.hadoop.hive.hbase.HBaseStorageHandler'  
WITH SERDEPROPERTIES ("hbase.columns.mapping" = ":key,log:user_agent,log:method,log:ip,log:port,log:url,log:request_time")  
TBLPROPERTIES ("hbase.table.name" = "pv_log");
```

### mysql 表设计

#### 项目表

表名称：kfd_project

| 列名称       | 类型        | 备注        |
| ------------ | ----------- | ----------- |
| project_id   | bigint      | 主键自增    |
| project_name | varchar(50) | 项目名称    |
| appid        | varchar(50) |             |
| project_url  | varchar(50) | 项目url路径 |
| create_time  | datetime    | 创建时间    |

#### 用户项目关联表

表名称：kfd_user_project_relation

| 列名称      | 类型     | 备注     |
| ----------- | -------- | -------- |
| id          | bigint   | 主键自增 |
| user_id     | bigint   | 用户id   |
| project_id  | bigint   | 项目id   |
| create_time | datetime | 创建时间 |

#### PV统计类表

##### 项目pv总统计表

| 列名称       | 类型        | 备注                |
| ------------ | ----------- | ------------------- |
| id           | bigint      | 主键自增            |
| appid        | varchar(50) |                     |
| pv_count     | bigint      | pv总数              |
| create_time  | datetime    | 创建时间            |
| request_time | datetime    | yyyy-mm-dd HH:00:00 |

