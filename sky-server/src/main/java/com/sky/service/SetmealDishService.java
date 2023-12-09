package com.sky.service;

import com.sky.entity.SetmealDish;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SetmealDishService {
    List<SetmealDish> getSetmealDishsBySetmealId(Long setmealId);

    void update(SetmealDish setmealDish);

}
