package com.example.SpringLearnExpert.Services;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.util.ReflectionUtils;
import org.springframework.stereotype.Service;

import com.example.SpringLearnExpert.Dto.EmployeeDto;
import com.example.SpringLearnExpert.Entitys.EmployeeEntity;
import com.example.SpringLearnExpert.Repositories.EmployeeRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmployeesService {

    private final EmployeeRepository employeeRepository;

    private final ModelMapper modelmapper;

    private final String CACHE_NAME = "employees";

    public List<EmployeeDto> getAllEmployee() {

        List<EmployeeEntity> employeeEntities = employeeRepository.findAll();
        return employeeEntities
                .stream()
                .map(EmployeeEntity -> modelmapper.map(EmployeeEntity, EmployeeDto.class))
                .collect(Collectors.toList());

    }

    @Cacheable(value = CACHE_NAME, key = "#id")
    public EmployeeDto getEmployeeById(Long id) {
        EmployeeEntity EmployeeEntity = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee Not Found with id" + id));
        return modelmapper.map(EmployeeEntity, EmployeeDto.class);
    }

    @CachePut(cacheNames = CACHE_NAME, key = "#result.id")
    public EmployeeDto CreateEmployee(EmployeeDto EmployeeDto) {
        EmployeeEntity employeeEntity = modelmapper.map(EmployeeDto, EmployeeEntity.class);
        EmployeeEntity employeeSavedEntity = employeeRepository.save(employeeEntity);
        return modelmapper.map(employeeSavedEntity, EmployeeDto.getClass());
    }

    @CachePut(cacheNames = CACHE_NAME, key = "#id")
    public EmployeeDto updateEmployeeById(Long id, EmployeeDto EmployeeDto) {
        EmployeeEntity employeeEntity = modelmapper.map(EmployeeDto, EmployeeEntity.class);
        employeeEntity.setId(id);
        EmployeeEntity employeeSavedEntity = employeeRepository.save(employeeEntity);
        return modelmapper.map(employeeSavedEntity, EmployeeDto.getClass());
    }

    public boolean IsEmployeeExists(Long Id) {
        return employeeRepository.existsById(Id);
    }

    @CacheEvict(cacheNames = CACHE_NAME, key = "#id")
    public void deleteEmployeeById(Long id) {
        employeeRepository.deleteById(id);
    }

    @CachePut(cacheNames = CACHE_NAME, key = "#id")
    public EmployeeDto partialupdateemployeeById(Long id, Map<String, Object> patchMapUpdate) {
        if (!IsEmployeeExists(id))
            return null;
        EmployeeEntity EmployeeEntity = employeeRepository.findById(id).get();

        patchMapUpdate.forEach((Field, Value) -> {
            Field UpdatedFiled = ReflectionUtils.findRequiredField(EmployeeEntity.getClass(), Field);
            UpdatedFiled.setAccessible(true);
            ReflectionUtils.setField(UpdatedFiled, EmployeeEntity, Value);
        });
        return modelmapper.map(employeeRepository.save(EmployeeEntity), EmployeeDto.class);

    }

}
