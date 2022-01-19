/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package net.ausiasmarch.bookanerosSB.repository;

import net.ausiasmarch.bookanerosSB.entity.MeGustaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
/**
 *
 * @author Jose Primo Gil
 */
public interface MeGustaRepository extends JpaRepository<MeGustaEntity, Long>{
    
    @Query(value = "SELECT COUNT(*) FROM megusta where id_post = ?", nativeQuery = true)
    Long findAllMeGustaCount(Long id_post);
    
    @Query(
            value = "SELECT * FROM megusta where id_usuario IN (SELECT id FROM usuario WHERE id_usuario = ?)",
            nativeQuery = true)
    Page<MeGustaEntity> findAllByUsuario(long id_usuario, Pageable oPageable);
}
