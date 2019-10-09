#      kafka安装部署教程

## kafka简介

Kafka是一个分布式消息队列。★Kafka对消息保存时根据Topic进行归类，发送消息者称为Producer，消息接受者称为Consumer，此外kafka集群有多个kafka实例组成，每个实例(server)称为broker。无论是kafka集群，还是consumer都依赖于zookeeper集群保存一些meta信息，来保证系统可用性。

## kafka安装前置条件

需要带有jdk环境的服务器

## kafka安装教程

1.首先要下载安装包[**http://mirrors.tuna.tsinghua.edu.cn/apache/kafka/2.3.0/kafka-2.3.0-src.tgz**](http://mirrors.tuna.tsinghua.edu.cn/apache/kafka/2.3.0/kafka-2.3.0-src.tgz)

2.然后传到要安装的服务器上解压

(https://pcc58.com/aaa.png)