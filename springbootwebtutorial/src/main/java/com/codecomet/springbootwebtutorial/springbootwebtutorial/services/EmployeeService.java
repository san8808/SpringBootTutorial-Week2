package com.codecomet.springbootwebtutorial.springbootwebtutorial.services;

import com.codecomet.springbootwebtutorial.springbootwebtutorial.dto.EmployeeDTO;
import com.codecomet.springbootwebtutorial.springbootwebtutorial.entities.EmployeeEntity;
import com.codecomet.springbootwebtutorial.springbootwebtutorial.exceptions.ResourceNotFoundException;
import com.codecomet.springbootwebtutorial.springbootwebtutorial.repositories.EmployeeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.ReflectionUtils;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    ModelMapper modelMapper;

    public Optional<EmployeeDTO> getEmployeeById(Long id){
//        Optional<EmployeeEntity> employeeEntity = employeeRepository.findById(id);
//        return employeeEntity.map(employeeEntity1 -> modelMapper.map(employeeEntity1, EmployeeDTO.class));

        return employeeRepository.findById(id).map(employeeEntity -> modelMapper.map(employeeEntity, EmployeeDTO.class));
    }

    public List<EmployeeDTO> getAllEmployees(){
        List<EmployeeEntity> employeeEntityList =  employeeRepository.findAll();

        return employeeEntityList.stream().map(employeeEntity -> modelMapper.map(employeeEntity, EmployeeDTO.class)).collect(Collectors.toList());
    }

    public EmployeeDTO createNewEmployee(EmployeeDTO employeeDTO){
        EmployeeEntity employeeEntity = modelMapper.map(employeeDTO,EmployeeEntity.class);
        return modelMapper.map(employeeRepository.save(employeeEntity), EmployeeDTO.class);
    }

    public EmployeeDTO updateEmployeeById(Long employeeId, EmployeeDTO employeeDTO) {

        isExistsByEmployeeId(employeeId);

        EmployeeEntity employeeEntity = modelMapper.map(employeeDTO,EmployeeEntity.class);
        employeeEntity.setId(employeeId);
        EmployeeEntity savedEmployeeEntity = employeeRepository.save(employeeEntity);

        return modelMapper.map(savedEmployeeEntity, EmployeeDTO.class);
    }

    public boolean isExistsByEmployeeId(Long employeeId){

        boolean exists = employeeRepository.existsById(employeeId);

        if(!exists) throw new ResourceNotFoundException("Employee not present with id - "+employeeId);

        return true;
    }


    public boolean deleteEmployeeById(Long employeeId) {

        isExistsByEmployeeId(employeeId);

        try {
            employeeRepository.deleteById(employeeId);
            return true;
        }
        catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public EmployeeDTO updatePartialEmployeeById(Long employeeId, Map<String, Object> updates) {

       isExistsByEmployeeId(employeeId);

        EmployeeEntity employeeEntity = employeeRepository.findById(employeeId).get();

        updates.forEach((field,value) -> {
            Field fieldToBeUpdated = ReflectionUtils.findRequiredField(EmployeeEntity.class,field);
            fieldToBeUpdated.setAccessible(true);
            ReflectionUtils.setField(fieldToBeUpdated,employeeEntity,value);
        });

        return modelMapper.map(employeeRepository.save(employeeEntity),EmployeeDTO.class);

    }
}
