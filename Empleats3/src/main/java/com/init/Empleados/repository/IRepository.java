package com.init.Empleados.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.init.Empleados.entity.Empleado;



@Repository
@Transactional
public interface IRepository  extends JpaRepository<Empleado, Integer>{
	
	List<Empleado> findEmpleadoById(int id);

}
