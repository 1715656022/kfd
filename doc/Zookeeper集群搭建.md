# Zookeeper集群搭建

先安装jdk,需要1.8以上的版本

安装过程略过

在zookeeper官网下载安装包

zookeeper官网： [https://mirrors.tuna.tsinghua.edu.cn/apache/zookeeper/]

将安装包上传到服务器或虚拟机

```shell
[root@node1 data]# ls
apache-zookeeper-3.5.5-bin.tar.gz  java  jdk-8u162-linux-x64.tar.gz
[root@node1 data]#
```

解压zookeeper安装包

```shell
[root@node1 data]# ls
apache-zookeeper-3.5.5-bin.tar.gz  java  jdk-8u162-linux-x64.tar.gz  zookeeper
[root@node1 data]#
```

进入zookeeper目录，先创建data目录，用于存放数据，再创建dataLog目录，用于存储日志，然后在data目录下创建myid文件，myid文件用于指定zookeeper节点id，且是唯一id，

```shell
[root@node1 data]# vim myid
1
```

进入conf目录

```shell
[root@node1 conf]# ls
configuration.xsl  log4j.properties  zoo_sample.cfg
[root@node1 conf]#
```

将`zoo_sample.cfg`文件复制出一份，并将其命名为zoo.cfg

```shell
[root@node1 conf]# cp zoo_sample.cfg zoo.cfg
[root@node1 conf]# ls
configuration.xsl  log4j.properties  zoo.cfg  zoo_sample.cfg
[root@node1 conf]#
```

`zoo.cfg`源文件内容为

```shell
# The number of milliseconds of each tick
tickTime=2000
# The number of ticks that the initial 
# synchronization phase can take
initLimit=10
# The number of ticks that can pass between 
# sending a request and getting an acknowledgement
syncLimit=5
# the directory where the snapshot is stored.
# do not use /tmp for storage, /tmp here is just 
# example sakes.
dataDir=/tmp/zookeeper
# the port at which the clients will connect
clientPort=2181
# the maximum number of client connections.
# increase this if you need to handle more clients
#maxClientCnxns=60
#
# Be sure to read the maintenance section of the 
# administrator guide before turning on autopurge.
#
# http://zookeeper.apache.org/doc/current/zookeeperAdmin.html#sc_maintenance
#
# The number of snapshots to retain in dataDir
#autopurge.snapRetainCount=3
# Purge task interval in hours
# Set to "0" to disable auto purge feature
#autopurge.purgeInterval=1
```

将其修改为

```shell
# The number of milliseconds of each tick
tickTime=2000
# The number of ticks that the initial 
# synchronization phase can take
initLimit=10
# The number of ticks that can pass between 
# sending a request and getting an acknowledgement
syncLimit=5
# the directory where the snapshot is stored.
# do not use /tmp for storage, /tmp here is just 
# example sakes.
dataDir=/data/zookeeper/data
dataLogDir=/data/zookeeper/dataLog
# the port at which the clients will connect
clientPort=2181
# the maximum number of client connections.
# increase this if you need to handle more clients
#maxClientCnxns=60
#
# Be sure to read the maintenance section of the 
# administrator guide before turning on autopurge.
#
# http://zookeeper.apache.org/doc/current/zookeeperAdmin.html#sc_maintenance
#
# The number of snapshots to retain in dataDir
#autopurge.snapRetainCount=3
# Purge task interval in hours
# Set to "0" to disable auto purge feature
#autopurge.purgeInterval=1
server.1=192.168.40.131:2888:3888
server.2=192.168.40.132:2888:3888
server.3=192.168.40.133:2888:3888
```

将dataDir修改为zookeeper目录下的data目录

添加dataLogDir指向zookeeper目下的dataLog目录

`server.x=ip:port:port`其中的x需要和myid文件的id一致，用于启动时选举

第一个port用于集群交换信息的通讯端口

第二个port用于重新选举Leader的通讯端口

进入zookeeper目录下的bin目录使用`./zkServer.sh start`启动服务

```shell
[root@node1 bin]# ./zkServer.sh start
ZooKeeper JMX enabled by default
Using config: /data/zookeeper/bin/../conf/zoo.cfg
Starting zookeeper ... STARTED
[root@node1 bin]#
```

同样启动其他服务，之后使用`./zkServer.sh status`查看服务状态

启动前需要开放端口或者关闭防火墙

```shell
[root@node1 bin]# ./zkServer.sh status
ZooKeeper JMX enabled by default
Using config: /data/zookeeper/bin/../conf/zoo.cfg
Client port found: 2181. Client address: localhost.
Mode: follower
[root@node1 bin]#
```

```shell
[root@master1 bin]# ./zkServer.sh status
ZooKeeper JMX enabled by default
Using config: /data/zookeeper/bin/../conf/zoo.cfg
Client port found: 2181. Client address: localhost.
Mode: leader
[root@master1 bin]#
```

```shell
[root@node2 bin]# ./zkServer.sh status
ZooKeeper JMX enabled by default
Using config: /data/zookeeper/bin/../conf/zoo.cfg
Client port found: 2181. Client address: localhost.
Mode: follower
[root@node2 bin]#
```

启动成功。