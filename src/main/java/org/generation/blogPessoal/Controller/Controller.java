package org.generation.blogPessoal.Controller;

import java.util.List;

import org.generation.blogPessoal.Model.Postagem;
import org.generation.blogPessoal.Repository.PostagemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//Indica para o Spring que essa classe é um controlador
@RestController

//Indica que a partir de um requisição no /postagens, ela consumi essa classe
@RequestMapping("/postagens")

//Para diferentes aplicações (angular, React...) a nossa API aceita
@CrossOrigin("*") //* => qualquer origem aceita requisições
public class Controller {
	
	//Spring instância essa interface, injeta dependênciaPara usar serviços da interface
	@Autowired
	private PostagemRepository repository;
	
	@GetMapping //Requisição externa (consuma API através da uri /postagens, ele dispara o método public ... findAll());
	public ResponseEntity<List<Postagem>> GetAll() {
		return ResponseEntity.ok(repository.findAll());
	}
	
	@GetMapping("/{id}") //Requisição do tipo Get em /postagens/"id" -> acessa o método
	public ResponseEntity<Postagem> PegaPeloId(@PathVariable long id){ //Captura qaul variavel dentro do Path
		return repository.findById(id) //retorna a interface que injetamos com @AutoWired
				.map(resp -> ResponseEntity.ok(resp)) //retorna o objeto do tipo postagem c/ ok
				.orElse(ResponseEntity.notFound().build()); //devolve not Found em erro ou vazio
	}
	
	@GetMapping("/titulo/{titulo}") //subcaminho ao titulo e o atributo titulo
									//Depois da barra o último dado ñ entende o nome, entende como um atributo +> daria duplicidade de endpoint
	public ResponseEntity<List<Postagem>> PegaPeloTitulo(@PathVariable String titulo){
		return ResponseEntity.ok(repository.findAllByTituloContainingIgnoreCase(titulo));
	}
	
	@PostMapping
	public ResponseEntity<Postagem> post(@RequestBody Postagem postagem){ //No corpo da requisição o objeto postagem é do tipo Postagem
		return ResponseEntity.status(HttpStatus.CREATED) //Devolve um status
				.body(repository.save(postagem)); //Salva na base de dados e devolve no body
	}
	
	@PutMapping
	public ResponseEntity<Postagem> put(@RequestBody Postagem postagem){ //Recebe um objeto pela body do tipo Postagem
		return ResponseEntity.status(HttpStatus.OK) //Devolve um ok
				.body(repository.save(postagem)); //Salva na base de dados e devolve no body
	}
	
	@DeleteMapping("/{id}") //Acessa o paramêtro id 
	public void delete(@PathVariable long id){ //ñ retorna nada (só o status)
												//, nome do método delete e o parametro vem na URI então é necessário um @PathVariable e o tipo de parametro
		repository.deleteById(id);
	}

	//Getters & Setters
	public PostagemRepository getRepository() {
		return repository;
	}

	public void setRepository(PostagemRepository repository) {
		this.repository = repository;
	}

	
}
