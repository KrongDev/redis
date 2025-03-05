# Redis Commands

## 기본 명령어

| Command | 설명 |
| --- | --- |
| redis-cli | Redis 접속 |
| INFO | Redis 서버의 상태 정보 출력 |
| PING | Redis 서버가 정상적으로 작동하는지 확인 |
| SET {Key} {Value} | Key에 Value를 설정 |
| GET {Key} | Key의 Value를 출력 |
| DEL {Key} | Key 삭제 |
| EXISTS {Key} | Key가 존재하는지 확인 |
| KEYS {pattern} | pattern에 맞는 Keys 출력 |
| TTL {Key} | Key의 남은시간 조회 (초 단위) |
| EXPIRE {Key} {seconds} | Key 만료 시간을 설정 (초 단위) |
| PERSIST {Key} | Key의 만료 시간 제거 |
| RENAME {Key} {newKey} | Key 이름 변경 |
| TYPE {Key} | Key의 데이터 타입 확인 |
| DBSIZE | 현재 DB의 Key 개수 조회 |
| RANDOMKEY | 랜덤 Key 반환 |
| SCAN {cursor} | Key 목록을 점진적으로 조회 |

## String

| Command | 설명 |
| --- | --- |
| APPEND {Key} {Value} | Key의 기존 Value에 추가 |
| INCR {Key} | Key의 Value를 1 증가 |
| DECR {Key} | Key의 Value를 1 감소 |
| INCRBY {Key} {increment} | Key의 Value를 특정 값만큼 증가 |
| DECRBY {Key} {decrement} | Key의 Value를 특정 값만큼 감소 |
| GETSET {Key} {Value} | Key의 기존 Value를 반환 후 새 값 설정 |

## List

| Command | 설명 |
| --- | --- |
| LPUSH {list_name} {value} | List의 앞에 항목 추가 |
| RPUSH {list_name} {value} | List의 뒤에 항목 추가 |
| LPOP {list_name} | List의 앞에서 값 제거 및 반환 |
| RPOP {list_name} | List의 뒤에서 값 제거 및 반환 |
| LRANGE {list_name} {start} {stop} | 리스트의 특정 범위 항목 조회 |
| LINDEX {list_name} {index} | 특정 인덱스의 값 조회 |
| LLEN {list_name} | 리스트의 길이 조회 |
| LSET {list_name} {index} {value} | 특정 인덱스의 값을 변경 |
| LREM {list_name} {count} {value} | 특정 값 삭제 (count만큼) |

## Set

| Command | 설명 |
| --- | --- |
| SADD {set_name} {value} | Set에 항목 추가 |
| SMEMBERS {set_name} | Set의 모든 항목 조회 |
| SREM {set_name} {value} | Set에서 항목 삭제 |
| SISMEMBER {set_name} {value} | Set에 항목이 있는지 확인 |
| SCARD {set_name} | Set의 요소 개수 조회 |
| SRANDMEMBER {set_name} | Set에서 랜덤 값 반환 |
| SPOP {set_name} | Set에서 랜덤 값 제거 및 반환 |
| SUNION {set1} {set2} | 두 Set의 합집합 반환 |
| SINTER {set1} {set2} | 두 Set의 교집합 반환 |
| SDIFF {set1} {set2} | 두 Set의 차집합 반환 |

## Hash

| Command | 설명 |
| --- | --- |
| HSET {hash_name} {field} {value} | Hash에 필드 및 값을 설정 |
| HGET {hash_name} {field} | Hash에서 필드 값 가져오기 |
| HDEL {hash_name} {field} | Hash에서 필드 삭제 |
| HGETALL {hash_name} | Hash의 모든 필드 및 값 조회 |
| HLEN {hash_name} | Hash의 필드 개수 조회 |
| HKEYS {hash_name} | Hash의 모든 필드 조회 |
| HVALS {hash_name} | Hash의 모든 값 조회 |

## Sorted Set (ZSet)

| Command | 설명 |
| --- | --- |
| ZADD {zset_name} {score} {value} | Sorted Set에 항목 추가 |
| ZRANGE {zset_name} {start} {stop} | Sorted Set의 특정 범위 항목 조회 |
| ZREM {zset_name} {value} | Sorted Set에서 항목 삭제 |
| ZSCORE {zset_name} {value} | 특정 값의 점수 조회 |
| ZCARD {zset_name} | Sorted Set의 요소 개수 조회 |
| ZCOUNT {zset_name} {min} {max} | 특정 점수 범위의 요소 개수 조회 |
| ZINCRBY {zset_name} {increment} {value} | 특정 값의 점수 증가 |

## PUB/SUB

| Command | 설명 |
| --- | --- |
| PUBLISH {channel} {message} | 채널에 메시지 퍼블리시 |
| SUBSCRIBE {channel} | 채널 구독 |
| UNSUBSCRIBE {channel} | 채널 구독 취소 |
| PSUBSCRIBE {pattern} | 패턴을 이용한 채널 구독 |
| PUNSUBSCRIBE {pattern} | 패턴 기반 채널 구독 취소 |

## Transaction

| Command | 설명 |
| --- | --- |
| MULTI | 트랜잭션 시작 |
| EXEC | 트랜잭션 실행 |
| DISCARD | 트랜잭션 취소 |
| WATCH {Key} | Key를 감시하여 트랜잭션 충돌 방지 |
| UNWATCH | 감시 취소 |

## Pipeline

| Command | 설명 |
| --- | --- |
| --pipe | 여러 명령어를 한 번에 서버에 전송 |

## Server 관리

| Command | 설명 |
| --- | --- |
| FLUSHDB | 현재 데이터베이스의 모든 키 삭제 |
| FLUSHALL | 모든 데이터베이스의 모든 키 삭제 |
| SAVE | Redis 서버 데이터를 디스크에 저장 |
| BGSAVE | 백그라운드에서 Redis 서버 데이터를 디스크에 저장 |
| SHUTDOWN | Redis 서버 종료 |
| CONFIG GET {parameter} | 특정 설정 값 조회 |
| CONFIG SET {parameter} {value} | 특정 설정 변경 |

## 스냅샷 및 복구

| Command | 설명 |
| --- | --- |
| LASTSAVE | 마지막 저장 시간 조회 |
| BGREWRITEAOF | AOF 파일 재작성 |
| SLAVEOF {host} {port} | 특정 서버를 마스터로 설정 |
| ROLE | 서버 역할 조회 |
