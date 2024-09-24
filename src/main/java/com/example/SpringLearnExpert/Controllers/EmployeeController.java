package com.example.SpringLearnExpert.Controllers;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.SpringLearnExpert.Dto.EmployeeDto;
import com.example.SpringLearnExpert.Services.EmployeesService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeesService employeesService;

    @GetMapping("/getallemployee")
    public ResponseEntity<List<EmployeeDto>> getEmployees() {
        return ResponseEntity.ok(employeesService.getAllEmployee());
    }

    @GetMapping("/getemployee/{Id}")
    public ResponseEntity<EmployeeDto> getEmployees(@PathVariable Long Id) {
        Optional<EmployeeDto> employeeDto = employeesService.getEmployeeById(Id);
        return employeeDto
                .map(employeeDto1 -> ResponseEntity.ok(employeeDto1)).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/addemployee")
    public ResponseEntity<EmployeeDto> CreateEmployee(@RequestBody EmployeeDto employeeDto) {
        EmployeeDto employeeSavedDto = employeesService.CreateEmployee(employeeDto);
        return new ResponseEntity<>(employeeSavedDto, HttpStatus.CREATED);
    }

    @PutMapping("/updateemployee/{Id}")
    public ResponseEntity<EmployeeDto> UpdateEmployee(@PathVariable Long Id, @RequestBody EmployeeDto EmployeeDto) {
        return ResponseEntity.ok(employeesService.updateEmployeeById(Id, EmployeeDto));

    }

    @DeleteMapping("/deleteemployee/{Id}")
    public ResponseEntity<Boolean> deleteEmployeeByid(@PathVariable Long Id) {
        Boolean gotDeleted = employeesService.deleteEmployeeById(Id);
        if (gotDeleted)
            return ResponseEntity.ok(true);
        return ResponseEntity.notFound().build();
    }

    @PatchMapping("/partialupdateemployee/{Id}")
    public ResponseEntity<EmployeeDto> partialUpdateEmployeeById(@PathVariable Long Id,
            @RequestBody Map<String, Object> PatchMapUpdate) {
        EmployeeDto employeeDto = employeesService.partialupdateemployeeById(Id, PatchMapUpdate);
        if (employeeDto == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(employeeDto);

    }

}
