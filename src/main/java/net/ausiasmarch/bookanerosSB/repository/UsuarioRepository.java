/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package net.ausiasmarch.bookanerosSB.repository;


import net.ausiasmarch.bookanerosSB.entity.UsuarioEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author Jose Primo Gil
 */
public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Long>{
    
    Page<UsuarioEntity> findByLogin(String login, Pageable oPageable);
    
    UsuarioEntity findByLoginAndPassword(String login, String password);
    
    
	Page<UsuarioEntity> findByLoginIgnoreCaseContaining(String login, Pageable oPageable);
    
}
