# sns-im

## 框架
服务端使用的是netty+protobuf
负载均衡zookeeper+dubbo
基本框架使用了springboot

## 项目介绍
sns-im 是一个im服务端的例子,netty和protobuf作为通信的框架协议
netty社区活跃,protobuf是谷歌开发一个的一个协议框架,性能好,所以选用
本文只作为基础服务使用


## 后期添加的项目
添加zookeeper和dubbo作为负载均衡使用
开发rest服务，作为通信的调用api
性能的调优,测试
心跳探测模块的优化,测试


