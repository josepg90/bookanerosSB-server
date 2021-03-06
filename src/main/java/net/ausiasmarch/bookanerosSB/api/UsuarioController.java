/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package net.ausiasmarch.bookanerosSB.api;

import java.util.List;
import javax.servlet.http.HttpSession;
import net.ausiasmarch.bookanerosSB.entity.UsuarioEntity;
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
@RequestMapping("/usuario")
public class UsuarioController {
    
    @Autowired
	UsuarioRepository oUsuarioRepository;

	@Autowired
	HttpSession oHttpSession;

	@GetMapping("/{id}")
	public ResponseEntity<?> get(@PathVariable(value = "id") Long id) {

		UsuarioEntity oUsuarioEntity = (UsuarioEntity) oHttpSession.getAttribute("usuario");

		if (oUsuarioEntity == null) {
			return new ResponseEntity<Long>(0L, HttpStatus.UNAUTHORIZED);
		} else {
			if (oUsuarioEntity.getId() == 1) {
				if (oUsuarioRepository.existsById(id)) {
					try {
						oUsuarioEntity = oUsuarioRepository.findById(oUsuarioEntity.getId()).get();
						return new ResponseEntity<UsuarioEntity>(oUsuarioRepository.findById(id).get(), HttpStatus.OK);
					} catch (Exception ex) {
						oUsuarioEntity = null;
					}
					return new ResponseEntity<UsuarioEntity>(oUsuarioRepository.findById(id).get(), HttpStatus.OK);

				} else {
					return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
				}
			} else {
				if (id.equals(oUsuarioEntity.getId())) { // los datos pedidos por el cliente son sus propios datos?
					return new ResponseEntity<UsuarioEntity>(oUsuarioRepository.findById(id).get(), HttpStatus.OK);
				} else {
					return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
				}
			}
		}
	}

	@GetMapping("/all")
	public ResponseEntity<?> get() {
		UsuarioEntity oUsuarioEntity = (UsuarioEntity) oHttpSession.getAttribute("usuario");

		if (oUsuarioEntity == null) {
			return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
		} else {
			if (oUsuarioEntity.getId() == 1) {
				return new ResponseEntity<List<UsuarioEntity>>(oUsuarioRepository.findAll(), HttpStatus.OK);

			} else {
				return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
			}
		}

	}

	@GetMapping("/count")
	public ResponseEntity<?> count() {
		UsuarioEntity oUsuarioEntity = (UsuarioEntity) oHttpSession.getAttribute("usuario");
		if (oUsuarioEntity == null) {
			return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
		} else {
			if (oUsuarioEntity.getId() == 1) {
				return new ResponseEntity<Long>(oUsuarioRepository.count(), HttpStatus.OK);
			} else {
				return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
			}
		}
	}
        //POSTMAN: DA ERROR --> solucionado
	@GetMapping("/page")
	public ResponseEntity<?> getPage(@PageableDefault(page = 0, size = 5, direction = Sort.Direction.ASC) Pageable oPageable,
                @RequestParam(name = "filter", required = false) String filter
			/*@RequestParam(required = false) Long filtertype, @RequestParam(required = false)*/ ) {
                Page<UsuarioEntity> oPage;
		UsuarioEntity oUsuarioEntity = (UsuarioEntity) oHttpSession.getAttribute("usuario");
                
		if (oUsuarioEntity == null) {
			return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
		} else {

			if (oUsuarioEntity.getId() == 1) {
                            if (filter != null) {
                                
                                oPage = oUsuarioRepository.findByLoginIgnoreCaseContaining(filter, oPageable);
                            } else {
				
                                //ESTE BUSCA FILTRANDO POR LOGIN
				//oPage = oUsuarioRepository.findByLoginIgnoreCaseContaining(oUsuarioEntity.getLogin(), oPageable);				
                                oPage = oUsuarioRepository.findAll(oPageable);
                            }

				return new ResponseEntity<Page<UsuarioEntity>>(oPage, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
			}
		}
	}

	@PostMapping("")
	public ResponseEntity<?> create(@RequestBody UsuarioEntity oNewUsuarioEntity) {

		/*UsuarioEntity oUsuarioEntity = (UsuarioEntity) oHttpSession.getAttribute("usuario");
		if (oUsuarioEntity == null) {
			return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
		} else {
			if (oUsuarioEntity.getId() == 1) {
				if (oNewUsuarioEntity.getId() == null) {
                                        //AQUI ESTAS A??ADIENDO ESTE PASSWORD AUNQUE PONGAS OTRO DIFERENTE, CUIDADO!
					//oNewUsuarioEntity.setPassword("4298f843f830fb3cc13ecdfe1b2cf10f51f929df056d644d1bca73228c5e8f64");
					return new ResponseEntity<>(oUsuarioRepository.save(oNewUsuarioEntity), HttpStatus.OK);
				} else {
					return new ResponseEntity<Long>(0L, HttpStatus.NOT_MODIFIED);
				}
			} else {
				return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
			}
		}*/
                if (oNewUsuarioEntity.getId() == null) {
					return new ResponseEntity<>(oUsuarioRepository.save(oNewUsuarioEntity), HttpStatus.OK);
				} else {
					return new ResponseEntity<Long>(0L, HttpStatus.NOT_MODIFIED);
				}
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> update(@PathVariable(value = "id") Long id, @RequestBody UsuarioEntity oUsuarioEntity) {

		UsuarioEntity oUsuarioEntity2 = (UsuarioEntity) oHttpSession.getAttribute("usuario");
		String password = oUsuarioEntity2.getPassword();
		if (oUsuarioEntity2 == null) {
			return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
		} else {
			if (oUsuarioEntity2.getId() == 1) {
				if (oUsuarioRepository.existsById(id)) {
					UsuarioEntity oUsuarioEntity3 = oUsuarioRepository.findById(id).get();
					oUsuarioEntity.setId(id);
                                        //AQUI ESTAS A??ADIENDO ESTE PASSWORD AUNQUE PONGAS OTRO DIFERENTE, CUIDADO!
					//oUsuarioEntity.setPassword("4298f843f830fb3cc13ecdfe1b2cf10f51f929df056d644d1bca73228c5e8f64");
					return new ResponseEntity<UsuarioEntity>(oUsuarioRepository.save(oUsuarioEntity), HttpStatus.OK);
				} else {
					return new ResponseEntity<UsuarioEntity>(oUsuarioRepository.findById(id).get(),
							HttpStatus.NOT_FOUND);
				}
			} else {
				if (oUsuarioEntity2.getId() == id) {
					UsuarioEntity oUsuarioEntity3 = oUsuarioRepository.findById(id).get();
					oUsuarioEntity.setId(oUsuarioEntity2.getId());
					
					//oUsuarioEntity.setPassword(password);
					return new ResponseEntity<UsuarioEntity>(oUsuarioRepository.save(oUsuarioEntity), HttpStatus.OK);
				} else {
					return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
				}
			}
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable(value = "id") Long id) {

		UsuarioEntity oUsuarioEntity = (UsuarioEntity) oHttpSession.getAttribute("usuario");

		if (oUsuarioEntity == null) {
			return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
		} else {
			if (oUsuarioEntity.getId() == 1) {
				oUsuarioRepository.deleteById(id);
				if (oUsuarioRepository.existsById(id)) {
					return new ResponseEntity<Long>(id, HttpStatus.NOT_MODIFIED);
				} else {
					return new ResponseEntity<Long>(0L, HttpStatus.OK);
				}
			} else {
                            if (oUsuarioEntity.getId() == id) {
				oUsuarioRepository.deleteById(id);
				if (oUsuarioRepository.existsById(id)) {
					return new ResponseEntity<Long>(id, HttpStatus.NOT_MODIFIED);
				} else {
					return new ResponseEntity<Long>(0L, HttpStatus.OK);
				}
			} else {
				return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
			}
				
			}
		}
	}
    
}
