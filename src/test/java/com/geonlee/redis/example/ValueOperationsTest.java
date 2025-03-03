package com.geonlee.redis.example;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.BitFieldSubCommands;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@DisplayName("ValueOperations를 사용해서 테스트할 수 있는 기능들 Test")
public class ValueOperationsTest {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Test
    @DisplayName("일반 문자열 저장 및 출력 테스트")
    public void valueOperations() {
        String key = "first";
        String value = "hello";
        // GIVEN
        redisTemplate.opsForValue().set(key, value);
        // WHEN
        String redisValue = (String) redisTemplate.opsForValue().get(key);
        // THEN
        assertThat(value).isEqualTo(redisValue);
    }

    @Test
    @DisplayName("BitMap 자료형 저장 및 출력 테스트")
    public void bitmapOperationsTest() {
        String key = "bitmap";
        // GIVEN
        ValueOperations<String, Object> opr = redisTemplate.opsForValue();
        opr.setBit(key, 1, true);
        opr.setBit(key, 2, false);
        opr.setBit(key, 3, true);

        // THEN
        assertThat(opr.getBit(key, 1)).isTrue();
        assertThat(opr.getBit(key, 2)).isFalse();
        assertThat(opr.getBit(key, 3)).isTrue();
    }

    @Test
    @DisplayName("BitField 자료형 저장 및 출력 테스트 - redis 3.2 이상부터 가능")
    public void bitFieldOperationsTest() {
        // BitField의 크기가 [0, 1, 2, 3, 4, 5, 6, 7, 8, 9] 10개의 비트를 사용한다 가정
        String key = "bitfield";
        // 0 ~ 4까지의 연속된 5bit를 묶어 사용하여 10이라는 10진수를 2진수로 저장
        setBitField(key, 0, 10, 5);
        // 5 ~ 9까지 연속된 5bit를 묶어 사용하여 15라는 10진수를 2진수로 저장
        setBitField(key, 5, 15, 5);

        // 비트 필드 조회
        assertThat(getBitField(key, 0, 5)).isEqualTo(10);
        assertThat(getBitField(key, 5, 5)).isEqualTo(15);

        // 비트 필드 증가
        assertThat(incrBitField(key, 0, 5, 5)).isEqualTo(15);
    }

    private void setBitField(String key, int offset, int value, int bitSize) {
        redisTemplate.opsForValue().bitField(key,
            BitFieldSubCommands.create().set(BitFieldSubCommands.BitFieldType.signed(bitSize)).valueAt(offset).to(value)
        );
    }

    private Integer getBitField(String key, int offset, int bitSize) {
        List<Long> result = redisTemplate.opsForValue().bitField(key,
            BitFieldSubCommands.create().get(BitFieldSubCommands.BitFieldType.signed(bitSize)).valueAt(offset)
        );

        return !Objects.isNull(result) && !result.isEmpty() ? result.get(0).intValue() : null;
    }

    public Integer incrBitField(String key, int offset, int increment, int bitSize) {
        List<Long> result = redisTemplate.opsForValue().bitField(key,
                BitFieldSubCommands.create().incr(BitFieldSubCommands.BitFieldType.signed(bitSize)).valueAt(offset).by(increment)
        );
        return !Objects.isNull(result) && !result.isEmpty() ? result.get(0).intValue() : null;
    }
}
