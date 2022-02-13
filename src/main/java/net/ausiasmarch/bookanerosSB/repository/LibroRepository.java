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
    
    @Query(
            value = "SELECT * FROM libro WHERE id_tipolibro = ?1 AND (titulo LIKE  %?2% OR codigo LIKE %?3%)",
            nativeQuery = true)
    Page<LibroEntity> findByTipolibroIdAndTituloOrCodigo(long IdTipolibro, String titulo, String codigo, Pageable oPageable);
    
}
