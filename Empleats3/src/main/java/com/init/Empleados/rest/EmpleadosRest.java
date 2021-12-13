package com.init.Empleados.rest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.Filter;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.init.Empleados.dao.EmpleadosDAO;
import com.init.Empleados.entity.Cargo;
import com.init.Empleados.entity.Empleado;
import com.init.Empleados.repository.BaseDatos;


import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/empleados")
public class EmpleadosRest implements Filter {

	@Autowired
	private EmpleadosDAO empleadosDao;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;
		httpServletResponse.setHeader("IT-Academy-Exercise", "Simple Service");
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
	}
	//Esta es otra manera de insertar datos mediante una clase
	@GetMapping("/InsertarDatos")
	public String crearDataBase(ArrayList<Empleado> BdEmpleados) {

		BaseDatos dB = new BaseDatos();

		dB.iniciar();
		ArrayList<Empleado> empleadosDb = dB.getBaseDatos();

		empleadosDao.saveAll(empleadosDb);

		return "Datos insertados";
	}

	//Listar empleados
	@GetMapping
	public ResponseEntity<List<Empleado>> getProduct() {
		List<Empleado> empleados = empleadosDao.findAll();
		return ResponseEntity.ok(empleados);
	}
	
	//filtrar empleados por cargo
	@GetMapping("/FiltrarXCargo/{cargo}")
	public ResponseEntity<List<Empleado>> getProductCargo(@PathVariable(name = "cargo") String cargo) {
		List<Empleado> empleados = (List<Empleado>) empleadosDao.findAll().stream()
				.filter(x -> x.getPuestoTrabajo().getPuestoTrabajo().equalsIgnoreCase(cargo))
				.collect(Collectors.toList());
		return ResponseEntity.ok(empleados);
	}

	//Buscar empleados por Id
	@RequestMapping(value = "{empleadoId}")
	public ResponseEntity<Empleado> getProductById(@PathVariable("empleadoId") int empleadoId) {
		Optional<Empleado> optionalEmpleados = empleadosDao.findById(empleadoId);

		if (optionalEmpleados.isPresent()) {
			return ResponseEntity.ok(optionalEmpleados.get());
		} else {

			return ResponseEntity.noContent().build();
		}

	}

	//guardar empleados en la base de datos
	@PostMapping("{name}/{cargo}")
	public ResponseEntity<Empleado> createEmpleado(@PathVariable(name = "name") String name,
			@PathVariable(name = "cargo") Cargo cargo) {

		Empleado nuevoEmpleado = new Empleado(name, cargo);

		empleadosDao.save(nuevoEmpleado);

		return ResponseEntity.ok(nuevoEmpleado);

	}

	//borrar empleados
	@DeleteMapping(value = "{empleadoId}")
	public ResponseEntity<Void> deleteEmpleado(@PathVariable("empleadoId") int empleadoId) throws Exception {
		try {
			empleadosDao.deleteById(empleadoId);
		} catch (Exception e) {
			throw new Exception("Empleado con id: " + empleadoId + " no encontrado");
		}

		return ResponseEntity.ok(null);

	}

	//Modifica un empleado
	@PutMapping("{id}/{name}/{cargo}")
	public ResponseEntity<Empleado> updateEmpleado(@PathVariable(name="id")int id,@PathVariable(name = "name") String name,
			@PathVariable(name = "cargo") Cargo cargo) {
		Optional<Empleado> optionalEmpleados = empleadosDao.findById(id);

		if (optionalEmpleados.isPresent()) {

			Empleado updateEmpleado = optionalEmpleados.get();
			updateEmpleado.setName(name);
			// updateEmpleado.setCargo(empleado.getCargo());
			updateEmpleado.setPuestoTrabajo(cargo);
			Cargo cargoActual = updateEmpleado.getPuestoTrabajo();
			int nuevoSueldo=Cargo.sueldoModificado(cargoActual.getPuestoTrabajo());
			updateEmpleado.setSueldo(nuevoSueldo);		
			empleadosDao.save(updateEmpleado);
			return ResponseEntity.ok(updateEmpleado);

		} else {

			return ResponseEntity.notFound().build();
		}

	}
	

	
	//para subir la foto
	 @PostMapping(value = "/uploadPhoto")
	    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) {

	        ResponseEntity<?> result = null;

	        try {
	        	   if (!file.isEmpty()) {

	                   if(!file.getContentType().equals("image/jpeg") && !file.getContentType().equals("image/png")) {
	                       throw new IOException("Error- Tipo de archivo aceptado: JPEG o PNG");
	                   }
	                   File upload_folder = new File("./uploads");

	                   String rutaArchivo = upload_folder.getAbsolutePath() + "//" + file.getOriginalFilename();
	                   FileOutputStream output = new FileOutputStream(rutaArchivo);
	                   output.write(file.getBytes());
	                   output.close();
	               }else {
	                   throw new IOException("Debe seleccionar una imagen");
	               }
	            result = ResponseEntity.status(HttpStatus.OK).body("Imagen subida correctamente");

	        } catch (Exception e) {
	            result = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
	        }

	        return result;

	    }
	
//	 //para descargar la foto
//	 public static Resource downloadPhoto(String filename) {
//	        String upload_folder = "./uploads/";
//	        Path path = Paths.get(upload_folder).toAbsolutePath().resolve(filename);
//
//	        Resource resource;
//	        try {
//	            resource = new UrlResource(path.toUri());
//
//	        } catch (MalformedURLException e) {
//	            throw new RuntimeException("Problema al leer el archivo", e);
//	        }
//	        if(resource.exists() || resource.isReadable()) {
//	            return resource;
//	        }else {
//	            throw new RuntimeException( "El archivo no existe o no es legible");
//	        }
//	    }
	 
	  //download photo
	    @GetMapping("/downloadPhoto/{filename}") // localhost:8080/employees/downloadPhoto/{filename} -> GET
	    public ResponseEntity<Resource> getEmployeePhoto(@PathVariable("filename") String filename, HttpServletResponse response) {

	    	  String upload_folder = "./uploads/";
		        Path path = Paths.get(upload_folder).toAbsolutePath().resolve(filename);

		        Resource resource;
		        try {
		            resource = new UrlResource(path.toUri());

		        } catch (MalformedURLException e) {
		            throw new RuntimeException("Problema al leer el archivo", e);
		        }

	        return ResponseEntity.ok()
	                .contentType(MediaType.IMAGE_JPEG)
	                .header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=" + resource.getFilename())
	                .body(resource);
	    }
	 
}
