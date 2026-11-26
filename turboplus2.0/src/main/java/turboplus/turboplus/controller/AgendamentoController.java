
package turboplus.turboplus.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import turboplus.turboplus.model.Agendamento;
import turboplus.turboplus.repository.AgendamentoRepository;
import turboplus.turboplus.repository.ClienteRepository;
import turboplus.turboplus.repository.VeiculoRepository;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/agendamentos")
public class AgendamentoController {

    private final AgendamentoRepository agendamentoRepository;
    private final ClienteRepository clienteRepository;
    private final VeiculoRepository veiculoRepository;

    public AgendamentoController(
            AgendamentoRepository agendamentoRepository,
            ClienteRepository clienteRepository,
            VeiculoRepository veiculoRepository) {

        this.agendamentoRepository = agendamentoRepository;
        this.clienteRepository = clienteRepository;
        this.veiculoRepository = veiculoRepository;
    }

    @GetMapping("/novo")
    public String novo(Model model) {

        model.addAttribute("agendamento", new Agendamento());
        model.addAttribute("clientes", clienteRepository.findAll());
        model.addAttribute("veiculos", veiculoRepository.findAll());

        return "agendamento";
    }

    @PostMapping("/salvar")
    public String salvar(
            @RequestParam Long clienteId,
            @RequestParam Long veiculoId,
            @RequestParam String servico,
            @RequestParam String dataHora) {

        var cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Cliente não encontrado"));

        var veiculo = veiculoRepository.findById(veiculoId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Veículo não encontrado"));

        if (!veiculo.getCliente().getId().equals(clienteId)) {
            throw new IllegalArgumentException(
                    "O veículo não pertence ao cliente selecionado");
        }

        Agendamento agendamento = new Agendamento();

        agendamento.setCliente(cliente);
        agendamento.setVeiculo(veiculo);
        agendamento.setServico(servico);
        agendamento.setDataHora(LocalDateTime.parse(dataHora));
        agendamento.setStatus("PENDENTE");

        agendamentoRepository.save(agendamento);

        return "redirect:/agendamentos/listar";
    }

    @GetMapping("/listar")
    public String listar(Model model) {

        model.addAttribute(
                "agendamentos",
                agendamentoRepository.findAll()
        );

        return "agendamentos";
    }

    @PostMapping("/status/{id}")
    public String atualizarStatus(
            @PathVariable Long id,
            @RequestParam String status) {

        var agendamento = agendamentoRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("Agendamento não encontrado"));

        agendamento.setStatus(status);

        agendamentoRepository.save(agendamento);

        return "redirect:/agendamentos/listar";
    }
}
