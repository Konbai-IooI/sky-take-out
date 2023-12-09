package com.sky.service.impl;

import com.sky.entity.SetmealDish;
import com.sky.mapper.SetmealDishMapper;
import com.sky.service.SetmealDishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SetmealDishServiceImpl implements SetmealDishService {


    @Autowired
    private SetmealDishMapper setmealDishMapper;
    public List<SetmealDish> getSetmealDishsBySetmealId(Long setmealId) {
        return setmealDishMapper.getSetmealDishsBySetmealId(setmealId);
    }

    @Override
    public void update(SetmealDish setmealDish) {
        // setmealDishMapper.update(setmealDish);
    }

}
