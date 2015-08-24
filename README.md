# Window-LFU
实现一个window-LFU缓存（即置换指定时间内，按照LFU规则排序淘汰数量）要求对外提供的接口：
- 可以指定cache的大小
- 可以指定时间窗口（windows）
- 提供get/put/remove数据访问方法
- 提供缓存命中率hitrate数据访问量
