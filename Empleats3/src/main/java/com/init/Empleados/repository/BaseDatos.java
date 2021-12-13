package com.init.Empleados.repository;

import java.util.ArrayList;

import com.init.Empleados.entity.Cargo;
import com.init.Empleados.entity.Empleado;



public class BaseDatos {
	
	ArrayList<Empleado> BdEmpleados= new ArrayList<Empleado>();
	
	public void iniciar() {
		
		BdEmpleados.add(new Empleado("Mª Jesus Serrano",Cargo.DIRECTOR));
		BdEmpleados.add(new Empleado("Eleuterio Dominguez",Cargo.GERENTE));
		BdEmpleados.add(new Empleado("Mª Luz Acin",Cargo.SECRETARIA));
		BdEmpleados.add(new Empleado("Roberto rivas",Cargo.OPERARIO));
	}
	
	public ArrayList<Empleado> getBaseDatos(){
		
		return BdEmpleados;
	}

}

