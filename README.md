# 📚 Hazelcast Notes (For Revision)

A quick and concise reference for understanding and revising **Hazelcast** concepts, tailored for Java/Spring Boot developers.

---

## 🔹 What is Hazelcast?

- **Hazelcast** is an open-source, in-memory **data grid** and distributed caching platform.
- Provides **high-speed access** to data by storing it in-memory (RAM).
- Supports **clustering**, **scalability**, and **fault-tolerance**.
- Integrates easily with **Spring Boot** for caching and distributed computing.

---

## 🔸 Key Concepts

| Concept         | Description                                                                 |
|-----------------|-----------------------------------------------------------------------------|
| Instance        | A Hazelcast node that participates in the cluster                          |
| Map             | Distributed key-value store (like a HashMap)                                |
| TTL             | Time to Live – how long an entry stays in cache                            |
| @Cacheable      | Spring annotation to cache method return values                            |
| @EnableCaching  | Enables Spring’s cache abstraction                                          |
| Config Class    | Used to define Hazelcast maps, TTL, backup, etc.                           |

---

## 🔹 Basic Setup (Spring Boot)

### ✅ Dependencies (Maven)

```xml
<dependency>
    <groupId>com.hazelcast</groupId>
    <artifactId>hazelcast</artifactId>
</dependency>

<dependency>
    <groupId>com.hazelcast</groupId>
    <artifactId>hazelcast-spring</artifactId>
</dependency>

<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-cache</artifactId>
</dependency>
```

---

### ✅ application.properties

```properties
spring.cache.type=hazelcast
```

---

### ✅ Java Config

```java
@Configuration
@EnableCaching
public class HazelcastConfig {

    @Bean
    public Config hazelcastConfig() {
        return new Config()
            .setInstanceName("hazelcast-instance")
            .addMapConfig(new MapConfig()
                .setName("user-cache")
                .setTimeToLiveSeconds(3600)); // 1 hour
    }
}
```

---

## 🔸 Caching in Service Layer

```java
@Cacheable("user-cache")
public User getUserById(Long id) {
    simulateSlowService();
    return userRepository.findById(id).orElseThrow();
}

private void simulateSlowService() {
    try {
        Thread.sleep(1300); // simulate DB delay
    } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
    }
}
```

---

## 🚀 Performance Experiment

| Call No. | Time Taken  | Description                  |
|----------|-------------|------------------------------|
| 1st      | ⏱️ 1.39 sec  | Initial DB call (cache miss) |
| 2nd      | ⚡ 14 ms     | Cache hit                    |
| 3rd      | ⚡ 10 ms     | Cache hit                    |
| 4th+     | ⚡ 8 ms      | Cache hit (very fast)        |

✅ **Result:** After the first hit, the method is ~170x faster thanks to Hazelcast.

---

## 🔸 Hazelcast vs Other Caches

| Feature        | Hazelcast        | Redis             | Ehcache         |
|----------------|------------------|-------------------|-----------------|
| Storage        | In-Memory Grid   | In-Memory/Remote  | In-Memory       |
| Clustering     | ✅ Built-in       | ✅ With setup      | ❌ Basic only    |
| Data Format    | Java Objects     | Strings/Bytes     | Java Objects    |
| Spring Support | ✅ Good           | ✅ Good            | ✅ Good          |
| Use Case       | Distributed Cache, Clustering | Caching, Pub/Sub | Local Cache     |

---

## 📌 Tips

- Use **Hazelcast Management Center** for monitoring and visualization.
- To **manually clear a cache**:

```java
@Autowired
private CacheManager cacheManager;

public void clearUserCache() {
    cacheManager.getCache("user-cache").clear();
}
```

- To **debug cache behavior**, enable logging:

```properties
logging.level.org.springframework.cache=DEBUG
```

---

## 📚 References

- [Hazelcast Docs](https://docs.hazelcast.com/)
- [Spring Boot Caching Docs](https://docs.spring.io/spring-boot/docs/current/reference/html/io.html#io.caching)
- [Hazelcast + Spring Integration Guide](https://docs.hazelcast.com/hazelcast/latest/integrations/spring/spring-overview)

---

> 🧠 *"The first hit teaches patience; the rest reward you with speed."*
