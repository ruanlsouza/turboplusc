
package turboplus.turboplus.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import turboplus.turboplus.model.Veiculo;
import turboplus.turboplus.repository.VeiculoRepository;
import turboplus.turboplus.repository.ClienteRepository;

@Controller
@RequestMapping("/veiculos")
public class VeiculoController {

    private final VeiculoRepository veiculoRepository;
    private final ClienteRepository clienteRepository;

    public VeiculoController(
            VeiculoRepository veiculoRepository,
            ClienteRepository clienteRepository) {

        this.veiculoRepository = veiculoRepository;
        this.clienteRepository = clienteRepository;
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("veiculo", new Veiculo());
        model.addAttribute("clientes", clienteRepository.findAll());

        return "veiculos";
    }

    @PostMapping("/salvar")
    public String salvar(
            @ModelAttribute Veiculo veiculo,
            @RequestParam Long clienteId) {

        var cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Cliente não encontrado"));

        veiculo.setCliente(cliente);

        veiculoRepository.save(veiculo);

        return "redirect:/veiculos/listar";
    }

    @GetMapping("/listar")
    public String listar(Model model) {
        model.addAttribute("veiculos", veiculoRepository.findAll());
        return "lista-veiculos";
    }
}
