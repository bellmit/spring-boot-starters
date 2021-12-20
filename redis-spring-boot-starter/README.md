# Spring Boot Starter Redis

Redis 自动配置

## 配置

```yaml
spring:
  redis:
    database: 0
    password:
    port: 6379
    host: redis
    lettuce:
      pool:
        # 连接池最大连接数（使用负值表示没有限制）
        max-active: 8
        # 连接池最大阻塞等待时间（使用负值表示没有限制）
        max-wait: 1000
        # 连接池中的最大空闲连接
        max-idle: 8
        # 连接池中的最小空闲连接
        min-idle: 0
        # 关闭超时时间
      shutdown-timeout: 100
```

## 使用 CacheService

```java
    @Autowired
    CacheService cache;

    public void func(){
        cache.set("key", "val", 300);
        cache.get("key");
    }
```

## 使用 Cache 注解

将缓存中形如 name@\d+ 格式的注解，自动设置过期时间, 如

```java

    /**
     * 获取房间状态
     *
     * @param roomId
     * @param state
     * @return
     */
    @Override
    @Cacheable(value = "roomStateCache@600", key = "':' + #root.targetClass.getName() + ':' + #roomId")
    public Integer getState(String roomId) {
        Room room = roomService.findById(roomId);
        if (room == null) {
            return RoomState.UNAVAILABLE.value();
        }
        return room.getState();
    }

    /**
     * 设置房间状态
     *
     * @param roomId
     * @return
     */
    @Override
    @CachePut(value = "roomStateCache@600", key = "':' + #root.targetClass.getName() + ':' + #roomId")
    public Integer setState(String roomId, Integer state) {
        Room model = new Room();
        model.setState(state);
        roomService.update(roomId, model);
        return state;
    }
```

`roomStateCache@600` 将会设置为 600 秒自动过期

如果不添加`@\d+`，缓存默认1天有效，过期自动清除

```java
 @Cacheable(value = "roomStateCache", key = "':' + #root.targetClass.getName() + ':' + #roomId")
```


## 说明

值为null时，不更新缓存

```java
@Cacheable(value = "roomStateCache@600", key = "':' + #root.targetClass.getName() + ':' + #roomId", unless="#result == null")
```