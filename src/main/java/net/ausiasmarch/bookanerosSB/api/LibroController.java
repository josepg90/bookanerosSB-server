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
    public ResponseEntity<Page<LibroEntity>> getPage(@PageableDefault(page = 0, size = 10, direction = Sort.Direction.DESC) Pageable oPageable,
            @RequestParam(name = "filter", required = false) String filter, @RequestParam(name = "filtertype", required = false) Long filtertype/**/) {
        Page<LibroEntity> oPage = null;

        /**/if (filtertype != null && filter != null) {
            oPage = oLibroRepository.findByTipolibroIdAndTituloIgnoreCaseContainingOrAutorIgnoreCaseContaining(filtertype, filter, filter, oPageable);
        } else {
            if (filter != null && filtertype == null) {
                oPage = oLibroRepository.findByTituloIgnoreCaseContainingOrAutorIgnoreCaseContaining(filter == null ? "" : filter, filter == null ? "" : filter, oPageable);

            } else {
                if (filtertype != null && filter == null) {
                    oPage = oLibroRepository.findByTipolibroId(filtertype, oPageable);
                } else {
                    oPage = oLibroRepository.findAll(oPageable);
                }
            }
        /*    if (filtertype != null) {
            oPage = oLibroRepository.findByTipolibroId(filtertype, oPageable);
        } else {
            if (filter != null) {
                oPage = oLibroRepository.findByTituloIgnoreCaseContainingOrAutorIgnoreCaseContaining(filter == null ? "" : filter, filter == null ? "" : filter, oPageable);

            } else {
                oPage = oLibroRepository.findAll(oPageable);
            }
        }*/
        }
        //oPage = oLibroRepository.findAll(oPageable);
        return new ResponseEntity<Page<LibroEntity>>(oPage, HttpStatus.OK);
    }

    //CREAR
    // producto/
    @PostMapping("")
    public ResponseEntity<?> create(@RequestBody LibroEntity oLibroEntity) {
        UsuarioEntity oUsuarioEntity = (UsuarioEntity) oHttpSession.getAttribute("usuario");
        if (oUsuarioEntity.getId() == 1) {
            if (oUsuarioEntity == null) {
                return new ResponseEntity<Long>(0L, HttpStatus.UNAUTHORIZED);
            } else {
                oLibroEntity.setId(null);

                return new ResponseEntity<LibroEntity>(oLibroRepository.save(oLibroEntity), HttpStatus.OK);
            }
        } else {
            return new ResponseEntity<Long>(0L, HttpStatus.UNAUTHORIZED);
        }

    }

    //UPDATE
    //libro/
    //EL PATHVARIABLE ES PARA COMPROBAR EN EL POSTMAN,PARA EL CLIENTE SOBRA, Y /{id} DEL PutMapping!!
    @PutMapping("/")
    public ResponseEntity<?> update(@RequestBody LibroEntity oLibroEntity) {
        UsuarioEntity oUsuarioEntity = (UsuarioEntity) oHttpSession.getAttribute("usuario");
        if (oUsuarioEntity.getId() == 1) {
            if (oUsuarioEntity == null) {
                return new ResponseEntity<Long>(0L, HttpStatus.UNAUTHORIZED);
            } else {
                //ESTO ES PA CLIENTE, LAS 3 SIGUIENTES LINEAS SIN COMENTAR ES PARA COMPROBAR EN POSTMAN (MIRAR WILDCART SI NO FUNCIONA)
                if (oLibroRepository.existsById(oLibroEntity.getId())) {
                //if (oLibroRepository.existsById(id)) {
                    //LibroEntity oLibroEntity3 = oLibroRepository.findById(id).get();
                    //oLibroEntity.setId(id);
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
    
    @GetMapping("/countValoracion/{id}")
    public ResponseEntity<Double> countValoracion(@PathVariable(value = "id") Long id) {
        //LibroEntity olibroEntity = oLibroRepository.getById(id);
        UsuarioEntity oUsuarioEntity = (UsuarioEntity) oHttpSession.getAttribute("usuario");
        Double oValoracion = null;
        if (oUsuarioEntity == null) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        } else {
            oValoracion = oLibroRepository.getValoracion(id);
            return new ResponseEntity<Double>(oValoracion , HttpStatus.OK);        
        }

    }
    
    @GetMapping("/favoritos")
    public ResponseEntity<Page<LibroEntity>> getFavoritos(@PageableDefault(page = 0, size = 10, direction = Sort.Direction.DESC) Pageable oPageable) {
        Page<LibroEntity> oFavoritos = null;
        UsuarioEntity oUsuarioEntity = (UsuarioEntity) oHttpSession.getAttribute("usuario");

        if (oUsuarioEntity == null) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        } else {
            oFavoritos = oLibroRepository.getFavoritosUsuario(oUsuarioEntity.getId(), oPageable);                   
                
            return new ResponseEntity<Page<LibroEntity>>(oFavoritos, HttpStatus.OK);
            //return new ResponseEntity<Page<LibroEntity>>((Page<LibroEntity>) oLibroRepository.getFavoritosUsuario(oUsuarioEntity.getId()), HttpStatus.OK);

        }
    }
    
    @GetMapping("/novedadNoFiltro")
    public ResponseEntity<Page<LibroEntity>> getNovedad(@PageableDefault(page = 0, size = 10, direction = Sort.Direction.DESC) Pageable oPageable) {
        Page<LibroEntity> oNovedad = null;
        UsuarioEntity oUsuarioEntity = (UsuarioEntity) oHttpSession.getAttribute("usuario");

        if (oUsuarioEntity == null) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        } else {
            oNovedad = oLibroRepository.getNovedad( oPageable);                   
                
            return new ResponseEntity<Page<LibroEntity>>(oNovedad, HttpStatus.OK);
            //return new ResponseEntity<Page<LibroEntity>>((Page<LibroEntity>) oLibroRepository.getFavoritosUsuario(oUsuarioEntity.getId()), HttpStatus.OK);

        }
    }
    @GetMapping("/novedad")
    public ResponseEntity<Page<LibroEntity>> getNovedadFiltro(@PageableDefault(page = 0, size = 10, direction = Sort.Direction.DESC) Pageable oPageable,
            @RequestParam(name = "filter", required = false) String filter, @RequestParam(name = "filtertype", required = false) Long filtertype/**/) {
        Page<LibroEntity> oPage = null;

        /**/if (filtertype != null && filter != null) {
            oPage = oLibroRepository.findNovedadByTipolibroIdAndTituloOrAutor(filtertype, filter, filter, oPageable);
        } else {
            if (filter != null && filtertype == null) {
                oPage = oLibroRepository.findNovedadByTituloOrAutor(filter == null ? "" : filter, filter == null ? "" : filter, oPageable);

            } else {
                if (filtertype != null && filter == null) {
                    oPage = oLibroRepository.findNovedadByTipolibroId(filtertype, oPageable);
                } else {
                    oPage = oLibroRepository.getNovedad(oPageable);
                }
            }
        /*    if (filtertype != null) {
            oPage = oLibroRepository.findByTipolibroId(filtertype, oPageable);
        } else {
            if (filter != null) {
                oPage = oLibroRepository.findByTituloIgnoreCaseContainingOrAutorIgnoreCaseContaining(filter == null ? "" : filter, filter == null ? "" : filter, oPageable);

            } else {
                oPage = oLibroRepository.findAll(oPageable);
            }
        }*/
        }
        //oPage = oLibroRepository.findAll(oPageable);
        return new ResponseEntity<Page<LibroEntity>>(oPage, HttpStatus.OK);
    }
}
