# Redis ( Remote Dictionary Server )
- NoSQL 데이터베이스 중 하나이며 오픈소스 소프트웨어이다.
- '키-값()Key-Value'형태로 데이터를 저장한다.
- '다양한 종류의 값'을 지정 가능하다.( String, List, Set, Sorted Set, Hash등을 지원 )
- '인 메모리 데이터 저장소'에 저장하고 사용한다.
- 타 데이터베이스와 달리 디스크 I/O를 거치지 않아 I/O Overhead가 발생하지 않는다.
- '인 메모리 데이터 저장소'를 활용하여 휘발성의 성질을 띄지만 디스크에 저장하는 옵션을 제공하여 필요에 의해 설정할 수 있다.
- 영구 저장 옵션으로는 RDB( Redis DataBase )와 AOF( Append Only File ) 두 가지 방법을 사용할 수 있다.
- 데이터베이스, 캐시, 메시지 브로커 등 다양한 용도로 사용한다.
![cache](../../redis/reference/cache.gif)
---

## Spring Data Redis
- Lettuce와 Jedis에 대한 Redis 드라이버에 low-Level 추상화로서 연결한 패키지 제공
- Redis의 작업, 예외 변환, 직렬화등을 수행하기 위해 RedisTemplate 제공
- Pubsub 지원( MessageListenerContainer 등 )
- Redis Sentinel 및 Redis Cluster 지원
- Lettuce 드라이브를 사용한 반응형 API
- Redis 기반의 JDK 컬렉션 구현
- @EnableRedisRepositories Repository를 사용하여 사용자 정의 쿼리 방법에 대한 지원을 포함한 인터페이스의 자동구현 제공
- 저장소에 대한 CDI 지원

---
### Data Type
클래스 | 데이터 타입 | 설명
--- | --- | --- 
[ValueOperations]() | Strings | 단일 값에 대한 연산을 수행하는 클래스. 주로 문자열 타입의 데이터를 다룸.
[BitmapOperations]() | Bitmaps | 비트맵 타입의 데이터를 다루는 클래스. 비트맵에 비트를 설정하거나 검색하는 등의 연산을 수행.
[BitFieldOperations]() | Bit Field | 비트 필드 타입의 데이터를 다루는 클래스. 비트 필드에 비트를 설정하거나 검색하는 등의 연산을 수행.
[HashOperations]() | Hashes | 해시 타입의 데이터를 다루는 클래스. 해시에 필드와 값을 추가한거나 제거하는 등의 연산을 수행.
[ListOperations]() | Lists | 리스트 타입의 데이터를 다루는 클래스. 리스트의 요소를 추가하거나 제거하는 등의 연산을 수행.
[SetOperations]() | Sets | 셋 타입의 데이터를 다루는 클래스. 셋에 요소를 추가하거나 제거하는 등의 연산을 수행.
[ZSetOperations]() | Sorted Sets | 정렬된 셋 타입의 데이터를 다루는 클래스. 셋의 요소를 추가하거나 제거하면서, 각 요소에 점수를 부여하여 순서를 정렬.
[GeoOperations]() | Geospatial Indexes | 지리적 인덱스 타입의 데이터를 다루는 클래스. 지리적 인덱스에 위치를 추가하거나 검색하는 등의 연산을 수행.
[HyperLogLogOperations]() | Hyperloglogs | 하이퍼로그로그 타입의 데이터를 다루는 클래스. 하이퍼로그로그에 요소를 추가하거나 카운트하는 등의 연산을 수행.
[StreamOperations]() | Streams | 스트림 타입의 데이터를 다루는 클래스. 스트림에 메시지를 추가하거나 읽는 등의 연산을 수행.
Reactive 타입들도 정의가 되어있어 `org.springframework.data.redis.core`에 위치하는 ADT를 참고할것.

---

### Drivers
Spring Data Redis에서는 내부적으로 IOC 컨테이너를 통해 스토어에 연결하기 위해 Java Connector(or Binding)을 사용하여 연결합니다.  
즉 개발자 입장에서는 Spring Data Redis만 사용하면 모든 Connector에 대해 일관되게 사용할 수 있습니다.  
`org.springframework.data.redis.connection` 패키지는 Redis와의 활성 연결을 다루고 가져오는 데 사용되는 `RedisConnection` 및 `RedisConnectionFactory` 인터페이스를 제공합니다.

멀티스레드 환경에서 `RedisConnection`은 안전하지 않습니다.  
Lettuce의 `StatefulRedisConnection`과 같은 기본연결은 안전할 수 있지만, Spring Data Redis의 `LettuceConnection`클래스 자체는 안전하지 않다고 합니다.
- RedisConnection은 스레드 안전하지 않다 → 여러 스레드에서 공유하면 트랜잭션 충돌, 블로킹 이슈 등 예상치 못한 오류가 발생할 수 있음.
- 트랜잭션과 파이프라이닝은 상태를 유지하는 작업이므로 여러 스레드에서 동시에 사용하면 안 됨.
- BLPOP 같은 블로킹 명령어는 독립적인 연결을 사용해야 함.
- 각 스레드마다 새로운 RedisConnection을 생성하거나, Lettuce의 StatefulRedisConnection을 적절히 활용하는 것이 안전한 방법.

따라서 `RedisConnection` 인스턴스는 여러 스레드에서 공유해서는 안된다고 합니다.

Redis Connection resource를 공유해야하는 경우에는 connection pool를 사용하는 것을 권장합니다.

---

### Connections

#### Redis Sentinel
Redis의 고가용성을 보장하기 위한 기능입니다.

##### Monitoring
- 마스터와 슬레이브의 상태를 감지
- Redis서버가 정상적으로 작동하는지 지속적인 PING을 보내 확인
- PING이 오지 않다면 장애로 간주
##### Automatic Failover
- 장애 발생 시 슬레이브 중 하나를 새로운 마스터로 승격

#### Redis Cluster
Sharding과 Failover를 제공하는 분산 아키텍처  
데이터를 여러 노드에 Sharding하여 Scalability와 Availability를 보장

##### Sharding
- Redis Cluster는 데이터를 여러 노드에 나눠 저장합니다.
- 특정 키를 어느 노드에 저장할지 결정하기 위해 Slot 개념을 사용
  - 16384개의 Hash Slot으로 데이터 분할
##### Automatic Failover
- 마스터 노드가 다운되면 슬레이브 노드 중 하나가 자동으로 마스터로 승격
##### 클라이언트가 여러 노드에 직접 연결
- 클라이언트가 마스터 노드의 IP로 직접 연결

최소 3개 이상의 마스터 노드로 구성되며, 하나이상의 슬레이브 노드를 가질 수 있습니다.

---

### Lettuce Client
- spring-boot-starter-data-redis 라이브러리 내에 내장되어 있으며 Java 환경에서 Redis를 이용하기 위한 Redis 클라이언트
- 이를 사용하면 여러 Redis 명령을 비동기적으로 실행할 수 있습니다. 또한, Netty 네트워크 프레임워크를 사용하여 구현되어서 고가용성 및 확장성을 제공하며 다중 스레드 환경을 지원
- Sentinel, Cluster, Pipelining, Auto-Reconnect 기능을 지원
- Spring Boot 2.x 이상의 버전에서는 기본적으로 Lettuce를 사용

특징 | 설명
--- |--- 
비동기 지원| Lettuce는 비동기적으로 Redis 명령을 실행할 수 있다.
고가용성 및 확장성 | Lettuce는 Netty 네트워크 프레임워크를 사용하여 구현되어 있어 고가용성과 확장성을 제공
다중 스레드 환경 지원 | Lettuce는 다중 스레드 환경을 지원

#### Redis Application.properties 속성
속성 | 설명
--- | ---
spring.redis.host | Redis 서버의 호스트 이름
spring.redis.port | Redis 서버의 포트 번호
spring.redis.password | Redis 서버에 접근하기 위한 비밀번호
spring.redis.database | 사용할 Redis 데이터베이스의 인덱스
spring.redis.ssl | SSL 연결 사용 여부
spring.redis.timeout | Redis 서버와의 연결 시간 제한
spring.redis.lettuce.pool.max-active | 동시에 유지할 수 있는 최대 연결 수
spring.redis.lettuce.pool.max-idle | 유휴 상태에서 유지할 수 있는 최대 연결 수
spring.redis.lettuce.pool.min-idle | 유휴 상태에서 유지할 수 있는 최소 연결 수

#### Redis Config
- 주요 Redis를 구성하기 위한 환경설정을 위한 Configuration 클래스.
- application.properties로부터 받아온 host,port정보를 기반으로 Redis Client를 구성
1. RedisConnectionFactory
   - Redis와의 연결을 위한 `Connection`을 생성하고 관리하는 메소드
   - LettuceConnectionFactory를 사용하여 host와 port정보를 기반으로 연결을 생성
2. RedisTemplate<String, Object>
   - Redis 데이터 처리를 위한 템플릿을 구성하는 메소드.
   - Redis와의 통신을 처리하기 위한 직렬화를 수행

---

# Reference
- [Spring Boot Data Redis 환경 구성 및 활용하기 -1 : 환경 구성 및 데이터 조작 방법](`https://adjh54.tistory.com/459#1)%20Redis(Remote%20Dictionary%20Server)%20%EB%B0%8F%20%EA%B5%AC%EC%A1%B0-1`)
- [Spring.io(redis)](https://docs.spring.io/spring-data/redis/reference/redis/template.html)
- [Spring Data Redis’s Property-Based Configuration](https://www.baeldung.com/spring-data-redis-properties)

