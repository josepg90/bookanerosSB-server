/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package net.ausiasmarch.bookanerosSB.repository;

import net.ausiasmarch.bookanerosSB.entity.TipoLibroEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
/**
 *
 * @author Jose Primo Gil
 */
public interface TipoLibroRepository extends JpaRepository<TipoLibroEntity, Long>{
    
    public Page<TipoLibroEntity> findByGeneroIgnoreCaseContaining(String strFilter, Pageable oPageable);

    public Page<TipoLibroEntity> findById(Long longTipoProducto, Pageable oPageable);
    
}
