package io.github.marlonszm.libraryapi.repository.specs;

import io.github.marlonszm.libraryapi.model.GeneroLivro;
import io.github.marlonszm.libraryapi.model.Livro;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;

public class LivroSpecs {

    // root = dados que eu quero da query.
    // cb (criteria builder) = builder com criterios.
    // query = objeto query

    // .equal = faz a comparação 'campo do banco (com o nome da propriedade na classe Livro)'
    // com o valor passado no atributo presente no parâmetro do metodo
    public static Specification<Livro> isbnEqual(String isbn){
        return (root, query, cb) -> cb.equal(root.get("isbn"), isbn);
    }

    // .like = faz a comparação de semelhança do campo da classe com o
    // campo passado pelo parâmetro. o '%" no começo e final serve para utiliar diversas
    // partes de string para comparação. O UpperCase serve para nivelar a pesquisa.

    public static Specification<Livro> tituloLike(String titulo){
        return (root, query, cb)
                -> cb.like(cb.upper(root.get("titulo")),
                "%" +  titulo.toUpperCase() + "%");
    }

    public static Specification<Livro> generoEqual(GeneroLivro genero){
        return (root, query, cb) -> cb.equal(root.get("genero"), genero);
    }

    public static Specification<Livro> anoPublicacaoEqual(Integer anoPublicacao){
        // and to_char(data_publicacao, 'YYYY') = :anoPublicacao
        return (root, query, cb) -> cb.equal(cb.function("to_char", String.class,
                root.get("dataPublicacao"),
                cb.literal("YYYY")), anoPublicacao.toString());
    }

    public static Specification<Livro> nomeAutorLike(String nome){
        return (root, query, cb) -> {
//            Join<Object, Object> joinAutor = root.join("autor", JoinType.LEFT);
//            return cb.like(cb.upper(joinAutor.get("nome")), "%" + nome.toUpperCase() + "%");
            return cb.like( cb.upper(root.get("autor").get("name")), "%" +  nome.toUpperCase() + "%");
        };
    }





}
