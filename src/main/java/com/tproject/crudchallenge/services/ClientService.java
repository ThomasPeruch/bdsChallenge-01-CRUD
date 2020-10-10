package com.tproject.crudchallenge.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tproject.crudchallenge.dto.ClientDTO;
import com.tproject.crudchallenge.entities.Client;
import com.tproject.crudchallenge.repositories.ClientRepository;

@Service
public class ClientService {
	
	@Autowired
	private ClientRepository repository;

	@Transactional(readOnly=true)
	public List<ClientDTO> findAll(){
		List<Client> list= repository.findAll();
		return list.stream().map(cli -> new ClientDTO(cli)).collect(Collectors.toList());			
	}
	
	@Transactional(readOnly=true)
	public ClientDTO findById(Long id) {
		Optional<Client> opt = repository.findById(id);
		Client entity = opt.get();	
		return new ClientDTO(entity);
		
	}

	public Client create(Client client) {
		client = repository.save(client); 
		return client;
	}
	
	
}
