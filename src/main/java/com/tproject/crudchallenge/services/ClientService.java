package com.tproject.crudchallenge.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tproject.crudchallenge.dto.ClientDTO;
import com.tproject.crudchallenge.entities.Client;
import com.tproject.crudchallenge.repositories.ClientRepository;
import com.tproject.crudchallenge.services.exceptions.ResourceNotFoundException;

@Service
public class ClientService {
	
	@Autowired
	private ClientRepository repository;

	@Transactional(readOnly=true)
	public List<ClientDTO> retrieveAll(){
		List<Client> list= repository.findAll();
		return list.stream().map(cli -> new ClientDTO(cli)).collect(Collectors.toList());			
	}
	
	@Transactional(readOnly=true)
	public ClientDTO retrieveById(Long id) {
		Optional<Client> opt = repository.findById(id);
		Client entity = opt.orElseThrow(() -> new ResourceNotFoundException("id " + id + " not found"));	
		return new ClientDTO(entity);
		
	}

	@Transactional
	public ClientDTO create(ClientDTO dto) {
		Client entity = new Client();
		copyDtoToEntity(dto, entity);
		entity = repository.save(entity); 
		return new ClientDTO(entity);
	}

	@Transactional
	public ClientDTO update(Long id, ClientDTO dto) {
		try {
			Client entity = repository.getOne(id);
			copyDtoToEntity(dto, entity);
			entity = repository.save(entity);
			return new ClientDTO(entity);
		}
		catch(EntityNotFoundException e) {
			throw new ResourceNotFoundException("id " + id + " not found");
		}
	}
	
	@Transactional
	public void delete(Long id) {
		try {
			repository.deleteById(id);
		}
		catch(EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("id " + id + " not found");
		}
	
	}
	
	private void copyDtoToEntity(ClientDTO dto,Client entity) {
		entity.setName(dto.getName());
		entity.setCpf(dto.getCpf());
		entity.setIncome(dto.getIncome());
		entity.setBirthDate(dto.getBirthDate());
		entity.setChildren(dto.getChildren());
	}

	
	
}
