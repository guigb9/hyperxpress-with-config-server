package com.bandtec.hyperxpress.hyperxpressproject.control.request;

public class ProdutoDestaque {
	private Long idProduto;
	private Integer nivelDestaque;

	public Integer getNivelDestaque() {
		return nivelDestaque;
	}

	public void setNivelDestaque(Integer nivelDestaque) {
		this.nivelDestaque = nivelDestaque;
	}

	public Long getIdProduto() {
		return idProduto;
	}

	public void setIdProduto(Long idProduto) {
		this.idProduto = idProduto;
	}
}
