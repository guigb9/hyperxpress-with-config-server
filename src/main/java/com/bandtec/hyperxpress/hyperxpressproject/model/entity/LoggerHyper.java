package com.bandtec.hyperxpress.hyperxpressproject.model.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
public class LoggerHyper {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
//
//    @NotBlank
//    private String tipo;
//
//    @NotBlank
//    private String descricaoErro;
//    @NotBlank
//    private LocalDate dataInclusao;
//    private String metodo;
//
//    @ManyToOne
//    @JoinColumn(name = "usuario_id")
//    private Usuario codigoUsuario;
//
//
//    public LoggerHyper(@NotBlank String tipo, @NotBlank String descricaoErro, String metodo, Usuario codigoUsuario) {
//        this.tipo = tipo;
//        this.descricaoErro = descricaoErro;
//        this.dataInclusao = LocalDate.now();
//        this.metodo = metodo;
//        this.codigoUsuario = codigoUsuario;
//    }
//
//    public LoggerHyper(@NotBlank String tipo, @NotBlank String descricaoErro, String metodo) {
//        this.tipo = tipo;
//        this.descricaoErro = descricaoErro;
//        this.dataInclusao = LocalDate.now();
//        this.metodo = metodo;
//        this.codigoUsuario = null;
//    }
//
//    //    Getters & Setters
//
//    public Integer getId() {
//        return id;
//    }
//
//    public void setId(Integer id) {
//        this.id = id;
//    }
//
//    public String getDescricaoErro() {
//        return descricaoErro;
//    }
//
//    public void setDescricaoErro(String descricaoErro) {
//        this.descricaoErro = descricaoErro;
//    }
//
//    public LocalDate getDataInclusao() {
//        return dataInclusao;
//    }
//
//    public void setDataInclusao(LocalDate dataInclusao) {
//        this.dataInclusao = dataInclusao;
//    }
//
//    public Usuario getCodigoUsuario() {
//        return codigoUsuario;
//    }
//
//    public void setCodigoUsuario(Usuario codigoUsuario) {
//        this.codigoUsuario = codigoUsuario;
//    }
//
//    public String getMetodo() {
//        return metodo;
//    }
//
//    public void setMetodo(String metodo) {
//        this.metodo = metodo;
//    }
//
//    public String getTipo() {
//        return tipo;
//    }
//
//    public void setTipo(String tipo) {
//        this.tipo = tipo;
//    }
}
