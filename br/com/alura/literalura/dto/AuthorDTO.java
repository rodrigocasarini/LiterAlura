package br.com.alura.literalura.dto;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties (ignoreUnknown = true)
public record AuthorDTO(@JsonAlias("name")String name,
                        @JsonAlias("birth_year") Integer birth,
                        @JsonAlias("death_year") Integer death) {

}
