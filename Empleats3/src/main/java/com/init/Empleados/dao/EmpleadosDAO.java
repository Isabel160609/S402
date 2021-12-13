package com.init.Empleados.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.init.Empleados.entity.Empleado;

public interface EmpleadosDAO extends JpaRepository<Empleado, Integer>{

	
	
}
