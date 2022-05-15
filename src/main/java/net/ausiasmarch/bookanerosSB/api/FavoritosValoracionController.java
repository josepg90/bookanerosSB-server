/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package net.ausiasmarch.bookanerosSB.api;

import javax.servlet.http.HttpSession;
import net.ausiasmarch.bookanerosSB.entity.FavoritosValoracionEntity;
import net.ausiasmarch.bookanerosSB.entity.UsuarioEntity;
import net.ausiasmarch.bookanerosSB.repository.FavoritosValoracionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Jose Primo Gil
 */
@RestController
@RequestMapping("/fav")
public class FavoritosValoracionController {
    
    @Autowired
    HttpSession oHttpSession;
    
    @Autowired
    FavoritosValoracionRepository oFavoritosValoracionRepository;
    
    @GetMapping("")
    public ResponseEntity<?> getPage(@PageableDefault(page = 0, size = 10, direction = Sort.Direction.DESC) Pageable oPageable,
            @RequestParam(name = "filter", required = false) String strFilter, @RequestParam(name = "id", required = false) Long id) {
        Page<FavoritosValoracionEntity> oPage = null;
        UsuarioEntity oUsuarioEntity = (UsuarioEntity) oHttpSession.getAttribute("usuario");
        if (oUsuarioEntity == null) {
            return new ResponseEntity<Long>(0L, HttpStatus.UNAUTHORIZED);
        } else {
            
            return new ResponseEntity<Page<FavoritosValoracionEntity>>((Page<FavoritosValoracionEntity>) oFavoritosValoracionRepository.findAllUsuario(oUsuarioEntity.getId(), oPageable), HttpStatus.OK);

            }
    }
        
    
    @GetMapping("/count")
    public ResponseEntity<Long> count() {
        FavoritosValoracionEntity oFavoritosValoracionEntity = null;
        UsuarioEntity oUsuarioEntity = (UsuarioEntity) oHttpSession.getAttribute("usuario");
        if (oUsuarioEntity == null) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        } else {

            return new ResponseEntity<Long>(oFavoritosValoracionRepository.findAllUsuarioCount(oUsuarioEntity.getId()), HttpStatus.OK);
        
        }

    }
    
    @PostMapping("")
    public ResponseEntity<?> create(@RequestBody FavoritosValoracionEntity oFavoritosValoracionEntity) {
        UsuarioEntity oUsuarioEntity = (UsuarioEntity) oHttpSession.getAttribute("usuario");
        if (oUsuarioEntity == null) {
            return new ResponseEntity<Long>(0L, HttpStatus.UNAUTHORIZED);
        } else {
            
                oFavoritosValoracionEntity.setId(null);
                return new ResponseEntity<FavoritosValoracionEntity>(oFavoritosValoracionRepository.save(oFavoritosValoracionEntity), HttpStatus.OK);
            
            
        }
    }
    
    //EL PATHVARIABLE ES PARA COMPROBAR EN EL POSTMAN,PARA EL CLIENTE SOBRA, Y /{id} DEL PutMapping!!
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable(value = "id") Long id, @RequestBody FavoritosValoracionEntity oFavoritosValoracionEntity) {
        UsuarioEntity oUsuarioEntity = (UsuarioEntity) oHttpSession.getAttribute("usuario");
        if (oUsuarioEntity == null) {
            return new ResponseEntity<Long>(0L, HttpStatus.UNAUTHORIZED);
        } else {
                //ESTO ES PA CLIENTE, LAS 3 SIGUIENTES LINEAS SIN COMENTAR ES PARA COMPROBAR EN POSTMAN (MIRAR WILDCART SI NO FUNCIONA)
                if (oFavoritosValoracionRepository.existsById(oFavoritosValoracionEntity.getId())) {
                //if (oFavoritosValoracionRepository.existsById(id)) {
		//			FavoritosValoracionEntity oFavoritosValoracionEntity3 = oFavoritosValoracionRepository.findById(id).get();
		//			oFavoritosValoracionEntity.setId(id);
                //HASTA AQUÍ COMPROBACIÓN EN POSTMAN
                    return new ResponseEntity<FavoritosValoracionEntity>(oFavoritosValoracionRepository.save(oFavoritosValoracionEntity), HttpStatus.OK);
                } else {
                    return new ResponseEntity<Long>(0L, HttpStatus.NOT_MODIFIED);
                }
            } 
        
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable(value = "id") Long id) {
        UsuarioEntity oUsuarioEntity = (UsuarioEntity) oHttpSession.getAttribute("usuario");
        if (oUsuarioEntity == null) {
            return new ResponseEntity<Long>(0L, HttpStatus.UNAUTHORIZED);
        } else {
            if (oUsuarioEntity.getId() == 1) {

                if (oFavoritosValoracionRepository.existsById(id)) {
                    oFavoritosValoracionRepository.deleteById(id);
                    if (oFavoritosValoracionRepository.existsById(id)) {
                        return new ResponseEntity<Long>(0L, HttpStatus.NOT_MODIFIED);
                    } else {
                        return new ResponseEntity<Long>(id, HttpStatus.OK);
                    }
                } else {
                    return new ResponseEntity<Long>(0L, HttpStatus.NOT_MODIFIED);
                }
            } else {
                return new ResponseEntity<Long>(0L, HttpStatus.UNAUTHORIZED);
            }
        }
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<FavoritosValoracionEntity> getOne(@PathVariable(value = "id") Long id) {

        if (oFavoritosValoracionRepository.existsById(id)) {
            return new ResponseEntity<FavoritosValoracionEntity>(oFavoritosValoracionRepository.findById(id).get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
    
    @GetMapping("/countValoracion")
    public ResponseEntity<Double> countValoracion() {
        FavoritosValoracionEntity oFavoritosValoracionEntity = null;
        UsuarioEntity oUsuarioEntity = (UsuarioEntity) oHttpSession.getAttribute("usuario");
        if (oUsuarioEntity == null) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        } else {

            return new ResponseEntity<Double>(oFavoritosValoracionRepository.getValoracion(oUsuarioEntity.getId()) , HttpStatus.OK);
        
        }
    }
    
    @GetMapping("/valoracionUsuario")
    public ResponseEntity<Long> getValoracionUsuario(@RequestParam(name = "filterUsuario", required = false) Long filterUsuario,
            @RequestParam(name = "filterLibro", required = false) Long filterLibro) {
        FavoritosValoracionEntity oFavoritosValoracionEntity = null;
        Long oValoracionUsuario = null;
        UsuarioEntity oUsuarioEntity = (UsuarioEntity) oHttpSession.getAttribute("usuario");
        if (oUsuarioEntity == null) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        } else if (filterUsuario != null && filterLibro!= null) {
            oValoracionUsuario = oFavoritosValoracionRepository.getValoracionUsuario(filterUsuario, filterLibro);
            return new ResponseEntity<Long>(oValoracionUsuario , HttpStatus.OK);
        
        } else {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

    }
}
