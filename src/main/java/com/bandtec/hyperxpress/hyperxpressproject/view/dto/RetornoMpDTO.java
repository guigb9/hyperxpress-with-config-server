package com.bandtec.hyperxpress.hyperxpressproject.view.dto;

import com.mercadopago.resources.Preference;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RetornoMpDTO {
	private String urlMP;
	private String preferenceId;

	public RetornoMpDTO(Preference preference) {
		this.urlMP = preference.getInitPoint();
		this.preferenceId = preference.getId();
	}
}
