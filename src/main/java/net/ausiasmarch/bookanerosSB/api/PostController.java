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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Jose Primo Gil
 */
@RestController
@RequestMapping("/post")
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

}
