/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package net.ausiasmarch.bookanerosSB.api;

import javax.servlet.http.HttpSession;
import net.ausiasmarch.bookanerosSB.entity.PostEntity;
import net.ausiasmarch.bookanerosSB.entity.UsuarioEntity;
import net.ausiasmarch.bookanerosSB.repository.LibroRepository;
import net.ausiasmarch.bookanerosSB.repository.PostRepository;
import net.ausiasmarch.bookanerosSB.repository.UsuarioRepository;
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
@RequestMapping("/opinion")
public class PostController {
    
    @Autowired
    HttpSession oHttpSession;

    @Autowired
    PostRepository oPostRepository;

    @Autowired
    LibroRepository oLibroRepository;

    @Autowired
    UsuarioRepository oUsuarioRepository;
    
    @GetMapping("/{id}")
    public ResponseEntity<PostEntity> view(@PathVariable(value = "id") Long id) {
        PostEntity oPostEntity = oPostRepository.getById(id);
        UsuarioEntity oUsuarioEntity = (UsuarioEntity) oHttpSession.getAttribute("usuario");
        if (oUsuarioEntity == null) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        } else if (oUsuarioEntity.getId() == 1) {
            if (oPostRepository.existsById(id)) {
                return new ResponseEntity<PostEntity>(oPostRepository.getById(id), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
        } else if (oPostRepository.existsById(id) && oUsuarioEntity.getId() == oPostEntity.getUsuario().getId()) {

            return new ResponseEntity<PostEntity>(oPostRepository.findByPostIdUsuarioView(oUsuarioEntity.getId(), oPostRepository.getById(id).getId()), HttpStatus.OK);

        } else if (oPostRepository.existsById(id) && oUsuarioEntity.getId() != oPostEntity.getUsuario().getId()) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);

        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/count")
    public ResponseEntity<Long> count() {
        PostEntity oPostEntity = null;
        UsuarioEntity oUsuarioEntity = (UsuarioEntity) oHttpSession.getAttribute("usuario");
        if (oUsuarioEntity == null) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        } else if (oUsuarioEntity.getId() == 1) {

            return new ResponseEntity<Long>(oPostRepository.count(), HttpStatus.OK);

        } else {

            return new ResponseEntity<Long>(oPostRepository.findByPostIdUsuarioCount(oUsuarioEntity.getId()), HttpStatus.OK);
        }

    }
    
    @GetMapping("")
    public ResponseEntity<?> getPage(@PageableDefault(page = 0, size = 10, direction = Sort.Direction.DESC) Pageable oPageable,
            @RequestParam(name = "filter", required = false) String filter, @RequestParam(name = "filtertype1", required = false) Long filtertype1,
            @RequestParam(name = "filtertype2", required = false) Long filtertype2) {
        Page<PostEntity> oPage = null;
        UsuarioEntity oUsuarioEntity = (UsuarioEntity) oHttpSession.getAttribute("usuario");
        //PostEntity oPostEntity = oPostRepository.getById(id);
        
        
        if (filtertype1 != null) {
            oPage = oPostRepository.findByLibroId(filtertype1, oPageable);
        } else {
            if (filtertype2 != null) {
            oPage = oPostRepository.findByUsuarioId(filtertype2, oPageable);
        } else {
                    oPage = oPostRepository.findAll(oPageable);
                }
            
        }
        /*
        if (oUsuarioEntity == null) {
            return new ResponseEntity<Long>(0L, HttpStatus.UNAUTHORIZED);
        } else {
            if (oUsuarioEntity.getId() == 1) {
                                        
            oPage = oPostRepository.findAll(oPageable);
        
            return new ResponseEntity<>(oPage, HttpStatus.OK);
            } else{
                //UN USUARIO NORMAL AHORA NO ESTA AUTORIZADO PARA VER POSTS
                //FALTA HACER QUE PUEDA VER SUS POSTS
                //return new ResponseEntity<Long>(0L, HttpStatus.UNAUTHORIZED);            
                //if(oPostRepository.existsById(id) && oUsuarioEntity.getId() == oPostEntity.getUsuario().getId()){
                    */
                    //return new ResponseEntity<Page<PostEntity>>((Page<PostEntity>) oPostRepository.findByPostIdUsuarioView(oUsuarioEntity.getId(), oPostRepository.getById(id).getId()), HttpStatus.OK);
                    //ESTE ES EL QUE ESTABA!!!!
                    //return new ResponseEntity<Page<PostEntity>>((Page<PostEntity>) oPostRepository.findAllUsuario(oUsuarioEntity.getId(), oPageable), HttpStatus.OK);
                    
                //} else {
                
                //return new ResponseEntity<Page<PostEntity>>(oPage, HttpStatus.NO_CONTENT);
                
                //}
            
                return new ResponseEntity<Page<PostEntity>>(oPage, HttpStatus.OK);
        }

    
    
        
        
        /*if (oUsuarioEntity == null) {
            return new ResponseEntity<Long>(0L, HttpStatus.UNAUTHORIZED);
        } else {
            if (oUsuarioEntity.getId() == 1) {
                                        
            oPage = oPostRepository.findAll(oPageable);
        
            return new ResponseEntity<>(oPage, HttpStatus.OK);
            } else{
                //UN USUARIO NORMAL AHORA NO ESTA AUTORIZADO PARA VER POSTS
                //FALTA HACER QUE PUEDA VER SUS POSTS
                //return new ResponseEntity<Long>(0L, HttpStatus.UNAUTHORIZED);            
                //if(oPostRepository.existsById(id) && oUsuarioEntity.getId() == oPostEntity.getUsuario().getId()){
                    
                    //return new ResponseEntity<Page<PostEntity>>((Page<PostEntity>) oPostRepository.findByPostIdUsuarioView(oUsuarioEntity.getId(), oPostRepository.getById(id).getId()), HttpStatus.OK);
                    return new ResponseEntity<Page<PostEntity>>((Page<PostEntity>) oPostRepository.findAllUsuario(oUsuarioEntity.getId(), oPageable), HttpStatus.OK);

                //} else {
                
                //return new ResponseEntity<Page<PostEntity>>(oPage, HttpStatus.NO_CONTENT);
                
                //}
            }
                
        }

    }*/
    
    @PostMapping("")
    public ResponseEntity<?> create(@RequestBody PostEntity oPostEntity) {
        UsuarioEntity oUsuarioEntity = (UsuarioEntity) oHttpSession.getAttribute("usuario");
        if (oUsuarioEntity == null) {
            return new ResponseEntity<Long>(0L, HttpStatus.UNAUTHORIZED);
        } else {
            if (oUsuarioEntity.getId() == 1) {
                oPostEntity.setId(null);
                return new ResponseEntity<PostEntity>(oPostRepository.save(oPostEntity), HttpStatus.OK);
            } else {
                if (oUsuarioEntity.getId() != null) {
                oPostEntity.setId(null);
                return new ResponseEntity<PostEntity>(oPostRepository.save(oPostEntity), HttpStatus.OK);
            } else {
                //ESTO HABRÁ QUE CAMBIARLO QUE ES PARA QUE LOS USUARIOS NO PUEDAN HACER NINGUN POST
                return new ResponseEntity<Long>(0L, HttpStatus.UNAUTHORIZED);
            }
        }
    }
    }
    
    //EL PATHVARIABLE ES PARA COMPROBAR EN EL POSTMAN,PARA EL CLIENTE SOBRA, Y /{id} DEL PutMapping!!
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable(value = "id") Long id, @RequestBody PostEntity oPostEntity) {
        UsuarioEntity oUsuarioEntity = (UsuarioEntity) oHttpSession.getAttribute("usuario");
        if (oUsuarioEntity == null) {
            return new ResponseEntity<Long>(0L, HttpStatus.UNAUTHORIZED);
        } else {
            if (oUsuarioEntity.getId() == 1) {
                //ESTO ES PA CLIENTE, LAS 3 SIGUIENTES LINEAS SIN COMENTAR ES PARA COMPROBAR EN POSTMAN (MIRAR WILDCART SI NO FUNCIONA)
                //if (oPostRepository.existsById(oPostEntity.getId())) {
                if (oPostRepository.existsById(id)) {
					PostEntity oPostEntity3 = oPostRepository.findById(id).get();
					oPostEntity.setId(id);
                //HASTA AQUÍ COMPROBACIÓN EN POSTMAN
                    return new ResponseEntity<PostEntity>(oPostRepository.save(oPostEntity), HttpStatus.OK);
                } else {
                    return new ResponseEntity<Long>(0L, HttpStatus.NOT_MODIFIED);
                }
            } else {
              //ESTO HABRÁ QUE CAMBIARLO QUE ES PARA QUE LOS USUARIOS NO PUEDAN HACER NINGUN POST

                return new ResponseEntity<Long>(0L, HttpStatus.UNAUTHORIZED);
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

                if (oPostRepository.existsById(id)) {
                    oPostRepository.deleteById(id);
                    if (oPostRepository.existsById(id)) {
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

}
