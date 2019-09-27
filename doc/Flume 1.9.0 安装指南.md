# Flume 1.9.0 安装指南

[TOC]

## 简介

- Apache Flume是一个分布式，可靠且可用的系统，用于有效地收集，聚合大量日志数据并将其从许多不同的源移动到集中式数据存储中。
- Apache Flume的使用不仅限于日志数据聚合。由于数据源是可定制的，因此Flume可用于传输大量事件数据，包括但不限于网络流量数据，社交媒体生成的数据，电子邮件消息以及几乎所有可能的数据源。
- Apache Flume是Apache Software Foundation的顶级项目。

## 相关链接

- 官网：<http://flume.apache.org/>
- 下载：<http://flume.apache.org/download.html>
- 用户指南：<http://flume.apache.org/releases/content/1.9.0/FlumeUserGuide.html>

## centos7 安装

- ### 安装jdk

  ```shell
  rpm -ivh jdk
  ```

- ### 安装Flume

  ```shell
  # 安装Flume
  wget http://mirrors.tuna.tsinghua.edu.cn/apache/flume/1.9.0/apache-flume-1.9.0-bin.tar.gz
  tar -zxvf apache-flume-1.9.0-bin.tar.gz
  # 进入conf目录下
  cd ${flumePath}/conf
  cp flume-env.sh.template flume-env.sh
  # 修改JAVA_HOME路径（jdk安装路径），见附录1
  vim flume-env.sh
  mkdir agentconf
  # 配置见附录2
  vim ${fileName}.properties
  ```

- ### 启动命令

  ```shell
  bin/flume-ng agent -c ../conf/ -f spooling-logger.properties -n a1 -Dflume.root.logger=INFO,console
  ```

## 附录1

- 编辑配置

  ```sh
  vim flume-env.sh
  ```

- 修改文件内容

  ```sh
  # Licensed to the Apache Software Foundation (ASF) under one
  # or more contributor license agreements.  See the NOTICE file
  # distributed with this work for additional information
  # regarding copyright ownership.  The ASF licenses this file
  # to you under the Apache License, Version 2.0 (the
  # "License"); you may not use this file except in compliance
  # with the License.  You may obtain a copy of the License at
  #
  #     http://www.apache.org/licenses/LICENSE-2.0
  #
  # Unless required by applicable law or agreed to in writing, software
  # distributed under the License is distributed on an "AS IS" BASIS,
  # WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  # See the License for the specific language governing permissions and
  # limitations under the License.
  
  # If this file is placed at FLUME_CONF_DIR/flume-env.sh, it will be sourced
  # during Flume startup.
  
  # Enviroment variables can be set here.
  # 修改此部分
   export JAVA_HOME=/usr/java/jdk1.8.0_181-amd64
  
  # Give Flume more memory and pre-allocate, enable remote monitoring via JMX
  # export JAVA_OPTS="-Xms100m -Xmx2000m -Dcom.sun.management.jmxremote"
  
  # Let Flume write raw event data and configuration information to its log files for debugging
  # purposes. Enabling these flags is not recommended in production,
  # as it may result in logging sensitive user information or encryption secrets.
  # export JAVA_OPTS="$JAVA_OPTS -Dorg.apache.flume.log.rawdata=true -Dorg.apache.flume.log.printconfig=true "
  
  # Note that the Flume conf directory is always included in the classpath.
  #FLUME_CLASSPATH=""
  ```

## 附录2

- 编辑配置

  ```shell
  vim spooling-logger.properties
  ```

- 修改文件内容

  ```properties
  # 定义这个 agent 中各个组件的名字
  a1.sources = r1
  a1.sinks = k1
  a1.channels = c1
  # 描述和配置 source 组件：r1
  a1.sources.r1.type = spooldir
  a1.sources.r1.spoolDir = /data
  a1.sources.r1.fileHeader = true
  a1.sources.r1.deserializer.maxLineLength = 2048000000
  # 描述和配置 sink 组件：k1，使用hdfs需要自己导入安装包，见附录3
  a1.sinks.k1.type = hdfs
  a1.sinks.k1.hdfs.path = hdfs://127.0.0.1:9000/flume/webdata
  a1.sinks.k1.hdfs.filePrefix = events-
  a1.sinks.k1.hdfs.round = true
  a1.sinks.k1.hdfs.roundValue = 10
  a1.sinks.k1.hdfs.roundUnit = minute
  a1.sinks.k1.hdfs.fileSuffix = .log
  a1.sinks.k1.hdfs.fileType = DataStream
  a1.sinks.k1.hdfs.rollSize = 0
  a1.sinks.k1.hdfs.rollCount = 0
  a1.sinks.k1.hdfs.rollInterval = 0
  a1.sinks.k1.hdfs.useLocalTimeStamp = true
  # 描述和配置 channel 组件，此处使用是内存缓存的方式（与内存缓存二选一）
  a1.channels.c1.type = memory
  a1.channels.c1.capacity = 1000
  a1.channels.c1.transactionCapacity = 100
  # 描述和配置 channel 组件，此处使用是kafka缓存的方式（与内存缓存二选一）
  a1.channels.c1.type = org.apache.flume.channel.kafka.KafkaChannel
  a1.channels.c1.kafka.bootstrap.servers = 127.0.0.1:9092
  a1.channels.c1.kafka.topic = mengpp-cache
  a1.channels.c1.kafka.consumer.group.id = mengpp-group
  # 描述和配置 source channel sink 之间的连接关系
  a1.sources.r1.channels = c1
  a1.sinks.k1.channel = c1
  ```

## 附录3

- jar包下载路径：https://dev.tencent.com/s/50c8eb3b-a8fa-4aa7-8382-e1746b0ecc21

- 导入文件路径：`${flumePath}/lib`