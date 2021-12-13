package com.init.Empleados.entity;

import java.util.Optional;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.*;

@Converter(autoApply = true)
public class CargoConverter implements AttributeConverter<Cargo, String> {

	@Override
	public String convertToDatabaseColumn(Cargo jobs) {
		// TODO Auto-generated method stub
		return Optional.ofNullable(jobs).map(Cargo::getPuestoTrabajo).orElse(null);		
	}


	@Override
	public Cargo convertToEntityAttribute(String dbData) {
		
		return Arrays.stream(Cargo.values()).filter(targetEnum -> targetEnum.getPuestoTrabajo().equalsIgnoreCase(dbData)).findFirst().orElse(null);
	}
}
