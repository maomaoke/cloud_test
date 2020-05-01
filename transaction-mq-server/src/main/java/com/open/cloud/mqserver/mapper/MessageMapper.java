package com.open.cloud.mqserver.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.open.cloud.mqserver.pojo.entity.Message;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author chen-ke-chao
 * @since 2019-08-28
 */
@Repository
public interface MessageMapper extends BaseMapper<Message> {

    int batchInsert(@Param("list") List<Message> message);

}
