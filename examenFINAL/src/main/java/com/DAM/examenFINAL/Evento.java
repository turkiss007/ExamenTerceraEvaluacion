package com.DAM.examenFINAL;

import java.time.LocalDateTime;

public class Evento {

	private String nombre;
	private LocalDateTime fecha;
	private String ubicacion;
	private String descripcion;

	public Evento() {

	}

	public Evento(String nombre, LocalDateTime fecha, String ubicacion, String descripcion) {
		this.nombre = nombre;
		this.fecha = fecha;
		this.ubicacion = ubicacion;
		this.descripcion = descripcion;
	}

	public String getNombre() {
		return nombre;
	}

	public LocalDateTime getFecha() {
		return fecha;
	}

	public String getUbicacion() {
		return ubicacion;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setFecha(LocalDateTime fecha) {
		this.fecha = fecha;
	}

	public void setUbicacion(String ubicacion) {
		this.ubicacion = ubicacion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

}
