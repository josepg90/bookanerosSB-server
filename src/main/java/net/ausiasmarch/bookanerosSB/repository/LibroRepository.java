/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package net.ausiasmarch.bookanerosSB.repository;

import net.ausiasmarch.bookanerosSB.entity.LibroEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author Jose Primo Gil
 */
public interface LibroRepository extends JpaRepository<LibroEntity, Long> {
    
    Page<LibroEntity> findByTituloIgnoreCaseContainingOrAutorIgnoreCaseContaining(String nombre, String autor, Pageable oPageable);
    
    Page<LibroEntity> findByTipolibroIdAndTituloIgnoreCaseContainingOrAutorIgnoreCaseContaining(long IdTipolibro, String nombre, String autor, Pageable oPageable);

    @Query(
            value = "SELECT * FROM libro WHERE id_tipolibro = ?1",
            nativeQuery = true)
    Page<LibroEntity> findByTipolibroId(long IdTipolibro, Pageable oPageable);
    
    //Query para sugerencias de libros cogiendo dos tipos de libros que se cogen aleatoriamente de los favoritos de un usuario
    @Query(
            value = "SELECT * FROM libro WHERE id_tipolibro IN (?1, ?2)",
            nativeQuery = true)
    Page<LibroEntity> findByTiposlibroId(long IdTipolibro1, long IdTipolibro2, Pageable oPageable);
    
    @Query(
            value = "SELECT * FROM libro WHERE id_tipolibro = ?1 AND (titulo LIKE  %?2% OR codigo LIKE %?3%)",
            nativeQuery = true)
    Page<LibroEntity> findByTipolibroIdAndTituloOrCodigo(long IdTipolibro, String titulo, String codigo, Pageable oPageable);
    
    @Query(value = "SELECT ROUND(AVG(CAST(valoracion AS DECIMAL(10,2))),1) FROM favoritos_valoracion WHERE id_libro IN (SELECT id FROM libro WHERE id_libro = ?)", nativeQuery = true)
    Double getValoracion(Long id_libro);
    
    @Query(value = "SELECT * FROM libro WHERE id IN (SELECT id_libro FROM favoritos_valoracion WHERE favorito = true AND id_usuario IN (SELECT id FROM usuario WHERE id_usuario = ?))", nativeQuery = true)
    Page<LibroEntity> getFavoritosUsuario(Long id_usuario, Pageable oPageable);
    
     @Query(value = "SELECT * FROM libro WHERE id IN (SELECT id_libro FROM favoritos_valoracion WHERE favorito = true AND id_usuario IN (SELECT id FROM usuario WHERE id_usuario = ?1)) AND id_tipolibro = ?2", nativeQuery = true)
    Page<LibroEntity> getFavoritosUsuarioAndIdTipolibro(Long id_usuario, long id_tipolibro, Pageable oPageable);
    
    @Query(value = "SELECT * FROM libro WHERE id IN (SELECT id_libro FROM favoritos_valoracion WHERE favorito = true AND id_usuario IN (SELECT id FROM usuario WHERE id_usuario = ?1)) AND (titulo LIKE  %?2% OR autor LIKE %?3%)", nativeQuery = true)
    Page<LibroEntity> getFavoritosUsuarioFiltro(Long id_usuario, String nombre, String autor, Pageable oPageable);
    
    @Query(value = "SELECT * FROM libro WHERE id IN (SELECT id_libro FROM favoritos_valoracion WHERE favorito = true AND id_usuario IN (SELECT id FROM usuario WHERE id_usuario = ?1)) AND ((titulo LIKE  %?2% OR autor LIKE %?3%) AND id_tipolibro = ?4)", nativeQuery = true)
    Page<LibroEntity> getFavoritosUsuarioFiltroAndIdTipoLibro(Long id_usuario, String nombre, String autor, long id_tipolibro, Pageable oPageable);
    
    @Query(value = "SELECT * FROM `libro` WHERE novedad=true", nativeQuery = true)
    Page<LibroEntity> getNovedad(Pageable oPageable);
    
    @Query(
            value = "SELECT * FROM libro WHERE novedad=true AND id_tipolibro = ?1",
            nativeQuery = true)
    Page<LibroEntity> findNovedadByTipolibroId(long IdTipolibro, Pageable oPageable);
    
    @Query(
            value = "SELECT * FROM libro WHERE novedad=true AND id_tipolibro = ?1 AND (titulo LIKE  %?2% OR autor LIKE %?3%)",
            nativeQuery = true)
    Page<LibroEntity> findNovedadByTipolibroIdAndTituloOrAutor(long IdTipolibro, String titulo, String autor, Pageable oPageable);
    
    @Query(
            value = "SELECT * FROM libro WHERE novedad=true AND (titulo LIKE  %?1% OR autor LIKE %?2%)",
            nativeQuery = true)
    Page<LibroEntity> findNovedadByTituloOrAutor(String titulo, String codigo, Pageable oPageable);
}
