/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package net.ausiasmarch.bookanerosSB.api;

import javax.servlet.http.HttpSession;
import net.ausiasmarch.bookanerosSB.entity.PeticionesEntity;
import net.ausiasmarch.bookanerosSB.entity.UsuarioEntity;
import net.ausiasmarch.bookanerosSB.repository.PeticionesRepository;
import net.ausiasmarch.bookanerosSB.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Jose Primo Gil
 */

@RestController
@RequestMapping("/peticiones")
public class PeticionesController {
    
    @Autowired
    HttpSession oHttpSession;
    
    @Autowired
    PeticionesRepository oPeticionesRepository;
    
    @Autowired
    UsuarioRepository oUsuarioRepository;
    
    @GetMapping("/{id}")
    public ResponseEntity<PeticionesEntity> view(@PathVariable(value = "id") Long id) {
       
        
            if (oPeticionesRepository.existsById(id)) {
                return new ResponseEntity<PeticionesEntity>(oPeticionesRepository.getById(id), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
        
    }
        
    @GetMapping("/count")
    public ResponseEntity<Long> count() {
        PeticionesEntity oPeticionesEntity = null;
        UsuarioEntity oUsuarioEntity = (UsuarioEntity) oHttpSession.getAttribute("usuario");
        if (oUsuarioEntity == null) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        } else {

            return new ResponseEntity<Long>(oPeticionesRepository.count(), HttpStatus.OK);

        } 

    }
    
    @PostMapping("")
    public ResponseEntity<?> create(@RequestBody PeticionesEntity oPeticionesEntity) {
        UsuarioEntity oUsuarioEntity = (UsuarioEntity) oHttpSession.getAttribute("usuario");
        if (oUsuarioEntity.getId() == 1) {
            if (oUsuarioEntity == null) {
                return new ResponseEntity<Long>(0L, HttpStatus.UNAUTHORIZED);
            } else {
                oPeticionesEntity.setId(null);

                return new ResponseEntity<PeticionesEntity>(oPeticionesRepository.save(oPeticionesEntity), HttpStatus.OK);
            }
        } else {
            return new ResponseEntity<Long>(0L, HttpStatus.UNAUTHORIZED);
        }
    
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable(value = "id") Long id, @RequestBody PeticionesEntity oPeticionesEntity) {
        UsuarioEntity oUsuarioEntity = (UsuarioEntity) oHttpSession.getAttribute("usuario");
        if (oUsuarioEntity == null) {
            return new ResponseEntity<Long>(0L, HttpStatus.UNAUTHORIZED);
        } else {
                //ESTO ES PA CLIENTE, LAS 3 SIGUIENTES LINEAS SIN COMENTAR ES PARA COMPROBAR EN POSTMAN (MIRAR WILDCART SI NO FUNCIONA)
                if (oPeticionesRepository.existsById(oPeticionesEntity.getId())) {
                //if (oPeticionesRepository.existsById(id)) {
		//			PeticionesEntity oPeticionesEntity3 = oPeticionesRepository.findById(id).get();
		//			oPeticionesEntity.setId(id);
                //HASTA AQUÍ COMPROBACIÓN EN POSTMAN
                    return new ResponseEntity<PeticionesEntity>(oPeticionesRepository.save(oPeticionesEntity), HttpStatus.OK);
                } else {
                    return new ResponseEntity<Long>(0L, HttpStatus.NOT_MODIFIED);
                }
            } 
        
    }
    
    @GetMapping("")
    public ResponseEntity<Page<PeticionesEntity>> getPeticiones(@PageableDefault(page = 0, size = 10, direction = Sort.Direction.DESC) Pageable oPageable) {
        Page<PeticionesEntity> oTodas = null;
        UsuarioEntity oUsuarioEntity = (UsuarioEntity) oHttpSession.getAttribute("usuario");

        if (oUsuarioEntity == null) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        } else {
            oTodas = oPeticionesRepository.getPeticiones( oPageable);                   
                
            return new ResponseEntity<Page<PeticionesEntity>>(oTodas, HttpStatus.OK);
            //return new ResponseEntity<Page<LibroEntity>>((Page<LibroEntity>) oLibroRepository.getFavoritosUsuario(oUsuarioEntity.getId()), HttpStatus.OK);

        }
    }
    
    @GetMapping("/enproceso")
    public ResponseEntity<Page<PeticionesEntity>> getPeticionesEnProceso(@PageableDefault(page = 0, size = 10, direction = Sort.Direction.DESC) Pageable oPageable) {
        Page<PeticionesEntity> oTodas = null;
        UsuarioEntity oUsuarioEntity = (UsuarioEntity) oHttpSession.getAttribute("usuario");

        if (oUsuarioEntity == null) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        } else {
            oTodas = oPeticionesRepository.getPeticionesEnProceso( oPageable);                   
                
            return new ResponseEntity<Page<PeticionesEntity>>(oTodas, HttpStatus.OK);
            //return new ResponseEntity<Page<LibroEntity>>((Page<LibroEntity>) oLibroRepository.getFavoritosUsuario(oUsuarioEntity.getId()), HttpStatus.OK);

        }
    }
    
    @GetMapping("/realizado")
    public ResponseEntity<Page<PeticionesEntity>> getPeticionesRealizadas(@PageableDefault(page = 0, size = 10, direction = Sort.Direction.DESC) Pageable oPageable) {
        Page<PeticionesEntity> oTodas = null;
        UsuarioEntity oUsuarioEntity = (UsuarioEntity) oHttpSession.getAttribute("usuario");

        if (oUsuarioEntity == null) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        } else {
            oTodas = oPeticionesRepository.getPeticionesRealizadas( oPageable);                   
                
            return new ResponseEntity<Page<PeticionesEntity>>(oTodas, HttpStatus.OK);
            //return new ResponseEntity<Page<LibroEntity>>((Page<LibroEntity>) oLibroRepository.getFavoritosUsuario(oUsuarioEntity.getId()), HttpStatus.OK);

        }
    }
}
