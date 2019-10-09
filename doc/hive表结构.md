# hive/hbase表结构设计

```shell
create 'pv_log','log';
```



## pv_log_hive 数据收集表

| 字段名称     | 类型       | 备注     |
| ------------ | ---------- | -------- |
| key          | string     |          |
| user_agent   | string     |          |
| method       | string     |          |
| ip           | string     |          |
| port         | string     |          |
| url          | string     |          |
| request_time | timestamps | 创建时间 |

