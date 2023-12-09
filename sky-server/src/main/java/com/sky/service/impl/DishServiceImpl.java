package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.DishFlavorMapper;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealDishMapper;
import com.sky.result.PageResult;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class DishServiceImpl implements DishService {


    @Autowired
    private DishMapper dishMapper;

    @Autowired
    private DishFlavorMapper dishFlavorMapper;

    @Autowired
    private SetmealDishMapper setmealDishMapper;

    /**
     * 新增菜品和对应的口味数据
     *
     * @param dishDTO
     */
    @Transactional // 多张表开启事物
    public void saveWithFlavor(DishDTO dishDTO) {

        Dish dish = new Dish();

        BeanUtils.copyProperties(dishDTO, dish);// 属性明明一致

        // 向菜品表插入一条数据
        dishMapper.insert(dish);

        // 获取insert生成的主键值
        Long dishId = dish.getId();
        List<DishFlavor> flavors = dishDTO.getFlavors();
        if (flavors != null && !flavors.isEmpty()) {
            for (DishFlavor flavor : flavors) {
                flavor.setDishId(dishId);
            }
            // 向口味表Flavor插入n条数据
            dishFlavorMapper.insertBatch(flavors);
        }

    }

    public PageResult pageQuery(DishPageQueryDTO dishPageQueryDTO) {
        // 开始分页查询
        PageHelper.startPage(dishPageQueryDTO.getPage(), dishPageQueryDTO.getPageSize());
        Page<DishVO> page = dishMapper.pageQuery(dishPageQueryDTO);
        return new PageResult(page.getTotal(), page.getResult());
    }

    /**
     * 批量删除
     *
     * @param ids
     */
    @Transactional
    public void deleteBatch(List<Long> ids) {

        // 判断当前菜品是否能够删除 是否存在起售
        for (Long id : ids) {
            Dish dish = dishMapper.getById(id);
            if (dish.getStatus() == StatusConstant.ENABLE) {
                // 起售中
                throw new DeletionNotAllowedException(MessageConstant.DISH_ON_SALE);
            }
        }
        // 判断当前菜品是否能够删除 是否被套餐关联
        List<Long> setmealIds = setmealDishMapper.getSetmealIdsByDishIds(ids);
        if (setmealIds != null && !setmealIds.isEmpty()) {
            // 被关联
            throw new DeletionNotAllowedException(MessageConstant.DISH_BE_RELATED_BY_SETMEAL);
        }
        // 删除菜品表中的数据
        dishMapper.deleteByIds(ids);
        // 删除菜品关联的口味
        dishFlavorMapper.deleteByDishIds(ids);

    }


    /**
     * 根据id查询和对应的口味
     *
     * @param dishId
     * @return
     */
    public DishVO getByIdWithFlavor(Long dishId) {
        Dish dish = dishMapper.getById(dishId);
        List<DishFlavor> dishFlavors = dishFlavorMapper.getByDishId(dishId);

        DishVO dishVO = new DishVO();
        BeanUtils.copyProperties(dish, dishVO);
        dishVO.setFlavors(dishFlavors);
        return dishVO;
    }

    /**
     * 根据id修改对应信息
     *
     * @param dishDTO
     */
    @Transactional
    public void updateWithFlavor(DishDTO dishDTO) {
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO, dish);
        // 修改菜品
        dishMapper.update(dish);

        // 修改口味
        dishFlavorMapper.deleteByDishId(dish.getId());

        List<DishFlavor> flavors = dishDTO.getFlavors();
        if (flavors != null && !flavors.isEmpty()) {
            for (DishFlavor flavor : flavors) {
                flavor.setDishId(dishDTO.getId());
            }
            // 向口味表Flavor插入n条数据
            dishFlavorMapper.insertBatch(flavors);
        }
    }

    /**
     * 更新
     * @param dish
     */
    public void update(Dish dish) {
        dishMapper.update(dish);
    }

    @Override
    public List<Dish> list(String categoryId) {
        return (dishMapper.list(categoryId));
    }
}
