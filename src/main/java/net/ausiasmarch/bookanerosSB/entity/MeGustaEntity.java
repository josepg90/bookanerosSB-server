/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package net.ausiasmarch.bookanerosSB.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
/**
 *
 * @author Jose Primo Gil
 */
@Entity
@Table(name = "megusta")
@JsonIgnoreProperties({"hibernateLazyInitialize", "handler"})
public class MeGustaEntity  implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private boolean gusta;
    
    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private UsuarioEntity usuario;    
    
    @ManyToOne
    @JoinColumn(name = "id_post")
    private PostEntity post; 
    
    public MeGustaEntity () {
        
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isGusta() {
        return gusta;
    }

    public void setGusta(boolean gusta) {
        this.gusta = gusta;
    }

    public UsuarioEntity getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioEntity usuario) {
        this.usuario = usuario;
    }

    public PostEntity getPost() {
        return post;
    }

    public void setPost(PostEntity post) {
        this.post = post;
    }
    
}
