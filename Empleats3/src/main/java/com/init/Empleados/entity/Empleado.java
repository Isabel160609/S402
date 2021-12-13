package com.init.Empleados.entity;

import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="Empleados")
public class Empleado {
	
	@Column(name="id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private int id;
	
	@Column(name="name",nullable = false, length = 30)
	private String name;
	
//	@Transient
//	private Cargo cargo;
	
	@Column(name="puestotrabajo",nullable = false)
	private Cargo puestoTrabajo;
	
	@Column(name="sueldo",nullable = false)
	private int sueldo;
	
//	@Column(name="file",nullable = true)
//	private Blob foto;
//	
	public Empleado() {
	}
	
	public Empleado( String name, Cargo cargo) {
		
		this.name = name;
		//this.cargo = cargo;
		this.puestoTrabajo = cargo;
		this.sueldo = cargo.getSueldo();
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
//	public Cargo getCargo() {
//		return cargo;
//	}
//	public void setCargo(Cargo cargo) {
//		this.cargo = cargo;
//	}
//	public String getPuestoTrabajo() {
//		return puestoTrabajo;
//	}
//	public void setPuestoTrabajo(String puestoTrabajo) {
//		this.puestoTrabajo = puestoTrabajo;
//	}
	public Cargo getPuestoTrabajo() {
		return puestoTrabajo;
	}

	public void setPuestoTrabajo(Cargo puestoTrabajo) {
		this.puestoTrabajo = puestoTrabajo;
	}
	
	public int getSueldo() {
		return sueldo;
	}


	public void setSueldo(int sueldo) {
		this.sueldo = sueldo;
	}

//	public Blob getFoto() {
//		return foto;
//	}
//
//	public void setFoto(Blob foto) {
//		this.foto = foto;
//	}

	
	
	
	
	
}
