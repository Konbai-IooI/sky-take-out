package com.sky.mapper;

import com.sky.entity.SetmealDish;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SetmealDishMapper {


    //根据菜品id查询套餐id
    List<Long> getSetmealIdsByDishIds(List<Long> dishIds);

    //保存套餐信息
    void insertBatch(List<SetmealDish> setmealDishes);

    @Delete("DELETE FROM setmeal_dish where setmeal_id = #{setmealId}")
    void deleteBySetmealId(Long setmealId);

    @Select("SELECT * FROM setmeal_dish where setmeal_id = #{setmealId}")
    List<SetmealDish> getSetmealDishsBySetmealId(Long setmealId);

    // void update(SetmealDish setmealDish);
}
