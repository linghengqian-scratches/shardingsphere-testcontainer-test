package com.lingh.shardingspheretestcontainertest;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
@MapperScan("com.lingh.shardingspheretestcontainertest")
public class ShardingsphereTestcontainerTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShardingsphereTestcontainerTestApplication.class, args);
    }

}

record TOrderPO(String idString, LocalDateTime createTime) {
}

@SuppressWarnings({"SqlResolve", "SqlNoDataSourceInspection"})
@Mapper
interface TOrderMapper {
    @Select("""
            select ANY_VALUE(`id_string`) as id_string, ANY_VALUE(`create_time`) as create_time
            from t_order
            where `create_time` between '2022-11-24 00:00:00.000' and '2022-11-25 00:00:00.000'
            group by (`create_time` div 3600)
            order by `create_time`
            limit 30
            """)
    List<TOrderPO> findByLogicTable();
}
