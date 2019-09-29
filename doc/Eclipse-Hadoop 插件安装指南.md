# Eclipse-Hadoop 插件安装指南

[TOC]

## Eclipse插件安装

- 插件下载地址：https://dev.tencent.com/s/fc54f422-7c60-404b-a692-26171aef6287
- 将下载文件复制到\eclipse\plugins下

## 配置Windows工具

- 下载地址：https://dev.tencent.com/s/baa7f105-9c38-465e-9b20-49f61731b30d
- 配置环境变量->我的电脑->属性->高级->环境变量->系统变量
- 新建->变量名：HADOOP_HOME，变量值：${FilePath}/winutils-master\hadoop-3.0.0
- 修改->变量名：PATH，变量值：%HADOOP_HOME%\bin;

## 相关链接

- Eclipse插件官方链接：<https://github.com/winghc/hadoop2x-eclipse-plugin>
- Hadoop Windows工具官方链接：<https://github.com/4ttty/winutils>