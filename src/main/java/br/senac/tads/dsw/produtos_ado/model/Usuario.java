package br.senac.tads.dsw.produtos_ado.model;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

//Ela é uma entidade meio diferente, já que ela será usada exclusivamente para o security
//Essa entidade implementará uma interface do próprio spring security

@Entity 
public class Usuario implements UserDetails{
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false) //Restrição que estou colocando no sql
    private String login;

    @Column(unique = true, nullable = false)
    private String senha;

    //Precisa colocar isso para reconhecer e ser enviado para o sql, e qualquer coisa que vier
    //seja convertido para string
    @Enumerated(EnumType.STRING) 
    private UserRole role;

    //Somos obrigados a colocar o construtor padrão porque Deus quis (sla, o spring obriga que precise colocar)
    public Usuario() {
    }

    //Construtor polimórfico 
    public Usuario(String login, String senha, UserRole role) {
        this.login = login;
        this.senha = senha;
        this.role = role;
    }//Sem o banco de dados porque o Banco de dados é responsável a gerar

    //Precisa passar uma lista por conta do Collection
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(this.role == UserRole.ADMIN){
            return List.of(
                new SimpleGrantedAuthority("ROLE_ADMIN"),
                new SimpleGrantedAuthority("ROLE_USER")
                //Precisa passar desse formato, "ROLE_" e na frente um dos enums que colocamos
            );
        }
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return login;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    
}
