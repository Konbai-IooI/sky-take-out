package com.sky.service;

import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.result.PageResult;

public interface EmployeeService {

    /**
     * 员工登录
     * @param employeeLoginDTO
     * @return
     */
    Employee login(EmployeeLoginDTO employeeLoginDTO);

    /**
     * 新增
     * @param employeeDTO
     */
    void add(EmployeeDTO employeeDTO);

    /**
     * 分页查询
     * @param employeePageQueryDTO
     * @return
     */
    PageResult pageQuery(EmployeePageQueryDTO employeePageQueryDTO);

    /**
     * 修改状态
     * @param status
     * @param id
     */
    void status(Integer status, Long id);

    /**
     * 根据id查询
     * @param id
     * @return
     */
    Employee getByUserid(Long id);

    /**
     * 更新信息
     * @param employeeDTO
     */
    void update(EmployeeDTO employeeDTO);

}
