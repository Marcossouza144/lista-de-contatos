package com.algaworks;

import java.util.ArrayList;
import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.RequestParam;
			
@Controller

public class ContatosControle {
private static final ArrayList<Contatos> LISTA_CONTATOS = new ArrayList<>();
	
	static {
		
		LISTA_CONTATOS.add(new Contatos("1", "Maria", "+55 34 00000 0000"));
		LISTA_CONTATOS.add(new Contatos("2 ", "João", "+55 34 00000 0000"));
		LISTA_CONTATOS.add(new Contatos("3", "Normandes", "+55 34 00000 0000"));
		LISTA_CONTATOS.add(new Contatos("4", "Thiago", "+55 34 00000 0000"));
		LISTA_CONTATOS.add(new Contatos("5", "Alexandre", "+55 34 00000 0000"));
	}
	
	@GetMapping("/")
	public String index() {
		return "index";
	}
	
	@GetMapping("/contatos")
	public ModelAndView listar() {
		ModelAndView modelAndView = new ModelAndView("listar");
		
		modelAndView.addObject("contatos", LISTA_CONTATOS);
		
		return modelAndView;
	}
	
	@GetMapping("/contatos/novo")
	public ModelAndView novo () {
		ModelAndView modelAndView = new ModelAndView("formulario");
		
		modelAndView.addObject("contato", new Contatos());
		
		return modelAndView;
	}	
	
	@PostMapping("/contatos")
	public String cadastrar(Contatos contato) {
		String id = UUID.randomUUID().toString();
		
		contato.setId(id);
		
		LISTA_CONTATOS.add(contato);
		
		return "redirect:/contatos";
	}
	
	@GetMapping("/contatos/{id}/editar")
	public ModelAndView editar(@PathVariable String id) {
		ModelAndView modelAndView = new ModelAndView("formulario");
		
		Contatos contato = procurarContato(id);
		
		modelAndView.addObject("contato", contato);
		
		return modelAndView;
	}
	
	@PutMapping("/contatos/{id}")
	public String atualizar(Contatos contato) {
		Integer indice = procurarIndiceContato(contato.getId());
		
		Contatos contatoVelho = LISTA_CONTATOS.get(indice);
		
		LISTA_CONTATOS.remove(contatoVelho);
		
		LISTA_CONTATOS.add(indice, contato);
		
		return "redirect:/contatos";
	}
	
	// -------------------------------------- Métodos auxiliares
	@DeleteMapping ("/contatos/{id}")
	public String remover (@PathVariable String id) {
		Contatos contato = procurarContato(id);
		LISTA_CONTATOS.remove(contato);
		return "redirect:/contatos";
	}
	
	private Contatos procurarContato(String id) {
		Integer indice = procurarIndiceContato(id);
		
		if (indice != null) {
			Contatos contato = LISTA_CONTATOS.get(indice);
			return contato;
		}
		
		return null;
	}
	
	private Integer procurarIndiceContato(String id) {
		for (int i = 0; i < LISTA_CONTATOS.size(); i++) {
			Contatos contato = LISTA_CONTATOS.get(i);
			
			if (contato.getId().equals(id)) {
				return i;
			}
		}
		
		return null;
	}
}