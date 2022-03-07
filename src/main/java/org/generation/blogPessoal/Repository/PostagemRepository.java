package org.generation.blogPessoal.Repository;

import java.util.List;

import org.generation.blogPessoal.Model.Postagem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



//Comunica pro Spring que essa interface é um repositório
@Repository
public interface PostagemRepository extends JpaRepository<Postagem, Long>{ //Entidade e tipagem do Id (no tipo primitivo)
	public List<Postagem> findAllByTituloContainingIgnoreCase(String titulo);
}
