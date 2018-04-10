package com.github.trang.druid.example.jpa.test.coveralls;

import com.github.trang.druid.example.jpa.model.City;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * MyBatis 测试
 *
 * @author trang
 */
public class JpaTests extends BaseTest {

    @Test
    public void testOne() {
        cityRepository.findById(1L)
                .map(gson::toJson)
                .ifPresent(city -> log.info("{}", city));
    }

    @Test
    public void testAll() {
        List<City> cities = cityRepository.findAll();
        cities.stream()
                .map(gson::toJson)
                .forEach(log::info);
        Assert.assertEquals(5, cities.size());
    }

}