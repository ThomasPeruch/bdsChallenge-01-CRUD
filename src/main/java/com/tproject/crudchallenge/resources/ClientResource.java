package com.tproject.crudchallenge.resources;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.tproject.crudchallenge.dto.ClientDTO;
import com.tproject.crudchallenge.services.ClientService;

@RestController
@RequestMapping(value = "/clients")
public class ClientResource {
	
	@Autowired
	private ClientService service;
		
	@GetMapping
	public ResponseEntity<Page<ClientDTO>> retrieveAll(
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "linesPerPage", defaultValue = "12") Integer linesPerPage,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction,
			@RequestParam(value = "orderBy", defaultValue = "name") String orderBy){
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		
		Page<ClientDTO> pageDto = service.retrieveAllPaged(pageRequest);
		return ResponseEntity.ok().body(pageDto);		
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<ClientDTO> retrieveById(@PathVariable Long id){
		ClientDTO dto = service.retrieveById(id); 
		return ResponseEntity.ok().body(dto);
	}
	
	@PostMapping
	public ResponseEntity<ClientDTO> create(@RequestBody ClientDTO dto){
		dto = service.create(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(dto.getId()).toUri();
		return ResponseEntity.created(uri).body(dto);
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<ClientDTO> update(@PathVariable Long id,@RequestBody ClientDTO dto){
		dto=service.update(id,dto);
		return ResponseEntity.ok().body(dto);
		
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<ClientDTO> delete(@PathVariable Long id){
		service.delete(id);
		return ResponseEntity.noContent().build();		
	}
	

}
