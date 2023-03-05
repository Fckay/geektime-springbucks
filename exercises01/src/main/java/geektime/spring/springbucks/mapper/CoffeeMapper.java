package geektime.spring.springbucks.mapper;

import geektime.spring.springbucks.model.Coffee;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

@Mapper
public interface CoffeeMapper {
    @Insert("insert into t_coffee (name, price, create_time, update_time)"
            + "values (#{name}, #{price}, now(), now())")
    @Options(useGeneratedKeys = true)
    int save(Coffee coffee);

    @Delete("delete from t_coffee where id = #{id}")
    int deleteById(Long id);

    @Update("update t_coffee set name = #{name}, price = #{price} where id = #{id}")
    int update(Coffee coffee);

    @Select("select * from t_coffee where id = #{id}")
    Coffee findById(@Param("id") Long id);

    Coffee findByName(@Param("name") String name);

    @Select("select * from t_coffee order by id")
    List<Coffee> findAll();

    @Select("select * from t_coffee order by id")
    List<Coffee> findAllWithParam(@Param("pageNum") int pageNum, @Param("pageSize") int pageSize);

    @Select("select * from t_coffee order by id")
    List<Coffee> findAllWithRowBounds(RowBounds rowBounds);

    List<Coffee> findAllByIds(List<Long> ids);

}
