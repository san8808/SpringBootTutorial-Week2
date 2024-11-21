package com.codecomet.springbootwebtutorial.springbootwebtutorial.controllers;

import com.codecomet.springbootwebtutorial.springbootwebtutorial.dto.EmployeeDTO;
import com.codecomet.springbootwebtutorial.springbootwebtutorial.entities.EmployeeEntity;
import com.codecomet.springbootwebtutorial.springbootwebtutorial.repositories.EmployeeRepository;
import com.codecomet.springbootwebtutorial.springbootwebtutorial.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

//    @GetMapping(path = "/getSecretMessage")
//    public String getmySuperSecretMessage(){
//        return "Secret message: asdjh@jd$872e";
//    }

    @Autowired
    EmployeeService employeeService;

    @GetMapping("/{employeeId}")
    public ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable Long employeeId){
        //return new EmployeeDTO(employeeId,"Sankalp","san8808@gmail.com",24, LocalDate.of(2022,8,28),true);

        Optional<EmployeeDTO> employeeDTO = employeeService.getEmployeeById(employeeId);

        return employeeDTO.map(employeeDTO1 -> ResponseEntity.ok(employeeDTO1)).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<EmployeeDTO>> getAllEmployees(@RequestParam(required = false) Integer age,
                                                @RequestParam(required = false) String sortBy){
        //return "Hi age "+age+" "+sortBy;
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }

    @PostMapping
    public ResponseEntity<EmployeeDTO> createNewEmployee(@RequestBody EmployeeDTO employeeDTO){
       EmployeeDTO employeeDTO1= employeeService.createNewEmployee(employeeDTO);

       return new ResponseEntity<>(employeeDTO1, HttpStatus.CREATED);

    }

    @PutMapping("/{employeeId}")
    public ResponseEntity<EmployeeDTO> updateEmployeeById(@RequestBody EmployeeDTO employeeDTO, @PathVariable Long employeeId){
         return ResponseEntity.ok(employeeService.updateEmployeeById(employeeId,employeeDTO));
    }

    @DeleteMapping("/{employeeId}")
    public ResponseEntity<Boolean> deleteEmployeeById(@PathVariable Long employeeId){
        Boolean deleted =employeeService.deleteEmployeeById(employeeId);

        if(deleted) return ResponseEntity.ok(true);

        return ResponseEntity.notFound().build();
    }

    @PatchMapping("/{employeeId}")
    public ResponseEntity<EmployeeDTO> updatePartialEmployeeById(@RequestBody Map<String,Object> updates, @PathVariable Long employeeId){
        EmployeeDTO employeeDTO =employeeService.updatePartialEmployeeById(employeeId,updates);

        if(employeeDTO==null) return ResponseEntity.notFound().build();

        return ResponseEntity.ok(employeeDTO);
    }

}
