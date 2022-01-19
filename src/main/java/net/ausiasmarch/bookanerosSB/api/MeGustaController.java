/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package net.ausiasmarch.bookanerosSB.api;

import javax.servlet.http.HttpSession;
import net.ausiasmarch.bookanerosSB.entity.MeGustaEntity;
import net.ausiasmarch.bookanerosSB.entity.UsuarioEntity;
import net.ausiasmarch.bookanerosSB.repository.MeGustaRepository;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Jose Primo Gil
 */
@RestController
@RequestMapping("/like")
public class MeGustaController {
    
    @Autowired
    HttpSession oHttpSession;
    
    @Autowired
    MeGustaRepository oMeGustaRepository;
    
    @GetMapping("")
    public ResponseEntity<?> getPage(@PageableDefault(page = 0, size = 10, direction = Sort.Direction.DESC) Pageable oPageable,
            @RequestParam(name = "filter", required = false) String strFilter, @RequestParam(name = "id", required = false) Long id) {
        Page<MeGustaEntity> oPage = null;
        UsuarioEntity oUsuarioEntity = (UsuarioEntity) oHttpSession.getAttribute("usuario");
        //PostEntity oPostEntity = oPostRepository.getById(id);
        if (oUsuarioEntity == null) {
            return new ResponseEntity<Long>(0L, HttpStatus.UNAUTHORIZED);
        } else {
            
            return new ResponseEntity<Page<MeGustaEntity>>((Page<MeGustaEntity>) oMeGustaRepository.findAllByUsuario(oUsuarioEntity.getId(), oPageable), HttpStatus.OK);

            }
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Long> count(@PathVariable(value = "id") Long id) {
        //MeGustaEntity oMeGustaEntity = null;
        UsuarioEntity oUsuarioEntity = (UsuarioEntity) oHttpSession.getAttribute("usuario");
        if (oUsuarioEntity == null) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        } else {
            
            return new ResponseEntity<Long>(oMeGustaRepository.findAllMeGustaCount(id), HttpStatus.OK);
            //return new ResponseEntity<Long>(oMeGustaRepository.findAllMeGustaCount(oMeGustaEntity.getPost().getId()), HttpStatus.OK);

        }
           
        
    }
    
    @PostMapping("")
    public ResponseEntity<?> create(@RequestBody MeGustaEntity oMeGustaEntity) {
        UsuarioEntity oUsuarioEntity = (UsuarioEntity) oHttpSession.getAttribute("usuario");
        if (oUsuarioEntity == null) {
            return new ResponseEntity<Long>(0L, HttpStatus.UNAUTHORIZED);
        } else {
                oMeGustaEntity.setId(null);
                return new ResponseEntity<MeGustaEntity>(oMeGustaRepository.save(oMeGustaEntity), HttpStatus.OK);
            } 
        
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable(value = "id") Long id) {
        UsuarioEntity oUsuarioEntity = (UsuarioEntity) oHttpSession.getAttribute("usuario");
        if (oUsuarioEntity == null) {
            return new ResponseEntity<Long>(0L, HttpStatus.UNAUTHORIZED);
        } else {
                if (oMeGustaRepository.existsById(id)) {
                    oMeGustaRepository.deleteById(id);
                    if (oMeGustaRepository.existsById(id)) {
                        return new ResponseEntity<Long>(0L, HttpStatus.NOT_MODIFIED);
                    } else {
                        return new ResponseEntity<Long>(id, HttpStatus.OK);
                    }
                } else {
                    return new ResponseEntity<Long>(0L, HttpStatus.NOT_MODIFIED);
                }
            } 
    }
    
    
}
