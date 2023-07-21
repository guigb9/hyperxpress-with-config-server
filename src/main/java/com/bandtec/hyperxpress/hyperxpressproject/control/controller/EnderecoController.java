package com.bandtec.hyperxpress.hyperxpressproject.control.controller;

import com.bandtec.hyperxpress.hyperxpressproject.control.request.AtualizacaoEnderecoRequest;
import com.bandtec.hyperxpress.hyperxpressproject.control.service.EnderecoService;
import com.bandtec.hyperxpress.hyperxpressproject.control.service.UsuarioService;
import com.bandtec.hyperxpress.hyperxpressproject.model.entity.Endereco;
import com.bandtec.hyperxpress.hyperxpressproject.view.dto.EnderecoDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/enderecos")
@RequiredArgsConstructor
public class EnderecoController {
	private final EnderecoService enderecoService;

	private final ModelMapper modelMapper;

	private final UsuarioService usuarioService;

	@GetMapping
	public Page<Endereco> getAllAdress(EnderecoDTO filter, Pageable pageable) {
		return enderecoService.findAllEnderecos(modelMapper.map(filter, Endereco.class), pageable);
	}

	@GetMapping(value = "/{idUsuario}", produces = "application/json")
	public Endereco getEndereco(@PathVariable Long idUsuario) {
		return enderecoService.procurarEnderecoPeloCodigoUsuario(idUsuario);
	}

	@GetMapping(value = "/cep-and-number/{cep}/{numero}", produces = "application/json")
	public Page<Endereco> getByCepAndNumber(@PathVariable String cep, @PathVariable String number, Pageable pageable) {
		return enderecoService.enderecoNumeroCep(cep, number, pageable);
	}

	@PutMapping("/{idUsuario}")
	public void updateAddress(@RequestBody @Valid AtualizacaoEnderecoRequest request, @PathVariable Long idUsuario){
		enderecoService.atualizarEnderecoPeloIdUsuarioComAtualizacaoEnderecoRequest(request, usuarioService.procurarUsuarioPeloId(idUsuario));
	}
}
