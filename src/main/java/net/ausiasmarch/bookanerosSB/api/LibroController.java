/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package net.ausiasmarch.bookanerosSB.api;

import javax.servlet.http.HttpSession;
import net.ausiasmarch.bookanerosSB.entity.LibroEntity;
import net.ausiasmarch.bookanerosSB.entity.UsuarioEntity;
import net.ausiasmarch.bookanerosSB.repository.LibroRepository;
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
@RequestMapping("/libro")
public class LibroController {
    
    @Autowired
    LibroRepository oLibroRepository;

    @Autowired
    HttpSession oHttpSession;
    
    @GetMapping("/{id}")
    public ResponseEntity<LibroEntity> get(@PathVariable(value = "id") Long id) {

        if (oLibroRepository.existsById(id)) {
            return new ResponseEntity<LibroEntity>(oLibroRepository.getById(id), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
    
    // libro/count
    @GetMapping("/count")
    public ResponseEntity<Long> count() {
        return new ResponseEntity<Long>(oLibroRepository.count(), HttpStatus.OK);
    }

    // /libro?page=0&size=10&sort=precio,desc&filter=verde&tipoproducto=2
    //GET PAGE SIMPLE SIN FILTROOOOOOS!!!!
    @GetMapping("")
    public ResponseEntity<Page<LibroEntity>> getPage(@PageableDefault(page = 0, size = 10, direction = Sort.Direction.DESC) Pageable oPageable) {
        Page<LibroEntity> oPage = null;
        oPage = oLibroRepository.findAll(oPageable);
        return new ResponseEntity<Page<LibroEntity>>(oPage, HttpStatus.OK);
    }

    //CREAR
    // producto/
    @PostMapping("")
    public ResponseEntity<?> create(@RequestBody LibroEntity oProductoEntity) {
        UsuarioEntity oUsuarioEntity = (UsuarioEntity) oHttpSession.getAttribute("usuario");
        if (oUsuarioEntity.getId() == 1) {
            if (oUsuarioEntity == null) {
                return new ResponseEntity<Long>(0L, HttpStatus.UNAUTHORIZED);
            } else {
                oProductoEntity.setId(null);

                return new ResponseEntity<LibroEntity>(oLibroRepository.save(oProductoEntity), HttpStatus.OK);
            }
        } else {
            return new ResponseEntity<Long>(0L, HttpStatus.UNAUTHORIZED);
        }

    }

    //UPDATE
    //libro/
    //EL PATHVARIABLE ES PARA COMPROBAR EN EL POSTMAN,PARA EL CLIENTE SOBRA, Y /{id} DEL PutMapping!!
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable(value = "id") Long id, @RequestBody LibroEntity oLibroEntity) {
        UsuarioEntity oUsuarioEntity = (UsuarioEntity) oHttpSession.getAttribute("usuario");
        if (oUsuarioEntity.getId() == 1) {
            if (oUsuarioEntity == null) {
                return new ResponseEntity<Long>(0L, HttpStatus.UNAUTHORIZED);
            } else {
                //ESTO ES PA CLIENTE, LAS 3 SIGUIENTES LINEAS SIN COMENTAR ES PARA COMPROBAR EN POSTMAN (MIRAR WILDCART SI NO FUNCIONA)
                //if (oLibroRepository.existsById(oLibroEntity.getId())) {
                if (oLibroRepository.existsById(id)) {
					LibroEntity oLibroEntity3 = oLibroRepository.findById(id).get();
					oLibroEntity.setId(id);
                //HASTA AQUÍ COMPROBACIÓN EN POSTMAN
                    return new ResponseEntity<LibroEntity>(oLibroRepository.save(oLibroEntity), HttpStatus.OK);
                } else {
                    return new ResponseEntity<Long>(0L, HttpStatus.NOT_MODIFIED);
                }
            }
        } else {
            return new ResponseEntity<Long>(0L, HttpStatus.UNAUTHORIZED);

        }
    }

    // producto/id
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable(value = "id") Long id) {
        UsuarioEntity oUsuarioEntity = (UsuarioEntity) oHttpSession.getAttribute("usuario");
        if (oUsuarioEntity.getId() == 1) {

            if (oUsuarioEntity == null) {
                return new ResponseEntity<Long>(0L, HttpStatus.UNAUTHORIZED);
            } else {
                if (oLibroRepository.existsById(id)) {
                    oLibroRepository.deleteById(id);
                    if (oLibroRepository.existsById(id)) {
                        return new ResponseEntity<Long>(0L, HttpStatus.NOT_MODIFIED);
                    } else {
                        return new ResponseEntity<Long>(id, HttpStatus.OK);
                    }
                } else {
                    return new ResponseEntity<Long>(0L, HttpStatus.NOT_MODIFIED);
                }
            }
        } else {
            return new ResponseEntity<Long>(0L, HttpStatus.UNAUTHORIZED);

        }
    }
}