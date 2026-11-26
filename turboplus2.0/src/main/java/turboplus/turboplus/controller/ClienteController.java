
package turboplus.turboplus.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import turboplus.turboplus.model.Cliente;
import turboplus.turboplus.repository.ClienteRepository;

@Controller
@RequestMapping("/clientes")
public class ClienteController {

    private final ClienteRepository repository;

    public ClienteController(ClienteRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("cliente", new Cliente());
        return "cadastro";
    }

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute Cliente cliente) {
        repository.save(cliente);
        return "redirect:/clientes/listar";
    }

    @GetMapping("/listar")
    public String listar(Model model) {
        model.addAttribute("clientes", repository.findAll());
        return "clientes";
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id) {
        repository.deleteById(id);
        return "redirect:/clientes/listar";
    }
}

