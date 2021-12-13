package com.init.Empleados.entity;

public enum Cargo {
	
	 DIRECTOR("director",50000), GERENTE("gerente",30000), SECRETARIA("secretaria",15000), OPERARIO("operario",12000);
   
	private String puestoTrabajo; 
    private int sueldo;
    
    
	private Cargo() {
	}


	private Cargo(String puestoTrabajo, int sueldo) {
		this.puestoTrabajo = puestoTrabajo;
		this.sueldo = sueldo;
	}


	public String getPuestoTrabajo() {
		return puestoTrabajo;
	}


	public void setPuestoTrabajo(String puestoTrabajo) {
		this.puestoTrabajo = puestoTrabajo;
	}


	public int getSueldo() {
		return sueldo;
	}


	public void setSueldo(int sueldo) {
		this.sueldo = sueldo;
	}

	public static int sueldoModificado(String puestoTrabajo) {
		int sueldo=0;
		switch (puestoTrabajo) {
		case "director":
			sueldo=50000;
			break;
		case "gerente":
			sueldo=30000;
			break;
		case "secretaria":
			sueldo=15000;
			break;
		case "operario":
			sueldo=12000;
			break;
		default:
			sueldo=12000;
		}
		return sueldo;
	}
}
