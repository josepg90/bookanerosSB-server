/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package net.ausiasmarch.bookanerosSB.repository;

import net.ausiasmarch.bookanerosSB.entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
/**
 *
 * @author Jose Primo Gil
 */
public interface PostRepository extends JpaRepository<PostEntity, Long>{
    
    //ESTAS QUERYS ESTAN MAL EN LOS WHERE!!!!
    @Query(value = "SELECT COUNT(*) FROM post where id_usuario IN (SELECT id FROM usuario WHERE id_usuario = :id_usuario)", nativeQuery = true)
    Long findByPostIdUsuarioCount(Long id_usuario);
    
    @Query(value = "SELECT * FROM post where id_usuario IN (SELECT id FROM usuario WHERE id_usuario = :id_usuario) AND id = :id_post", nativeQuery = true)
    PostEntity findByPostIdUsuarioView(Long id_usuario, Long id_post);
}
