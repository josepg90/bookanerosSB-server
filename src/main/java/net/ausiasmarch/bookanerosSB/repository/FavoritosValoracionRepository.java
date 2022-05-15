/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package net.ausiasmarch.bookanerosSB.repository;

import net.ausiasmarch.bookanerosSB.entity.FavoritosValoracionEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
/**
 *
 * @author Jose Primo Gil
 */
public interface FavoritosValoracionRepository extends JpaRepository<FavoritosValoracionEntity, Long>{
    
    @Query(
            value = "SELECT * FROM favoritos_valoracion where id_usuario IN (SELECT id FROM usuario WHERE id_usuario = ?)",
            nativeQuery = true)
    Page<FavoritosValoracionEntity> findAllUsuario(long id_usuario, Pageable oPageable);
    
    @Query(value = "SELECT COUNT(*) FROM favoritos_valoracion where id_usuario IN (SELECT id FROM usuario WHERE id_usuario = ?)", nativeQuery = true)
    Long findAllUsuarioCount(Long id_usuario);
    
    @Query(value = "SELECT id FROM favoritos_valoracion WHERE id_usuario = ? AND id_libro = ?", nativeQuery = true)
    Long getValoracionUsuario(Long id_usuario, Long id_libro);
    
    @Query(value = "SELECT AVG(CAST(valoracion AS DECIMAL(10,2))) FROM favoritos_valoracion WHERE id_usuario IN (SELECT id FROM usuario WHERE id_usuario = ?)", nativeQuery = true)
    Double getValoracion(Long id_usuario);
  
}
