/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package net.ausiasmarch.bookanerosSB.repository;

import net.ausiasmarch.bookanerosSB.entity.PeticionesEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
/**
 *
 * @author Jose Primo Gil
 */
public interface PeticionesRepository extends JpaRepository<PeticionesEntity, Long>{
    
@Query(value = "SELECT * FROM peticiones WHERE enproceso = false", nativeQuery = true)
    Page<PeticionesEntity> getPeticiones(Pageable oPageable);
    
@Query(value = "SELECT * FROM peticiones WHERE enproceso = true AND realizado = false", nativeQuery = true)
    Page<PeticionesEntity> getPeticionesEnProceso(Pageable oPageable);

@Query(value = "SELECT * FROM peticiones WHERE realizado = true", nativeQuery = true)
    Page<PeticionesEntity> getPeticionesRealizadas(Pageable oPageable);    
    
}
