package net.khcloud.study.dao.mapper;

import net.khcloud.study.dao.domain.Product;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * Created by GBC on 2017/10/20.
 */
@Mapper
public interface ProductMapper {
    Product select(@Param("id") long id);

    void update(Product product);
}