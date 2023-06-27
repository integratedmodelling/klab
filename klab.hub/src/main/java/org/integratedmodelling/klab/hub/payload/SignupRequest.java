package org.integratedmodelling.klab.hub.payload;

import javax.validation.constraints.NotBlank;

import org.integratedmodelling.klab.hub.enums.AgreementLevel;
import org.integratedmodelling.klab.hub.enums.AgreementType;

public class SignupRequest {
	
	public SignupRequest(){
	}
	
	public SignupRequest(@NotBlank String username, @NotBlank String email, @NotBlank String agreementType, @NotBlank String agreementLevel) {
		super();
		this.username = username;
		this.email = email;
		this.agreementType = AgreementType.valueOf(agreementLevel);
		this.agreementLevel = AgreementLevel.valueOf(agreementLevel);
	}

	@NotBlank
	private String username;

	@NotBlank
	private String email;
	
	@NotBlank
	private AgreementType agreementType;
	
	@NotBlank
	private AgreementLevel agreementLevel;
	
	public String getUsername() {
		return username;
	}

	public String getEmail() {
		return email;
	}

    public AgreementType getAgreementType() {
        return agreementType;
    }

    public AgreementLevel getAgreementLevel() {
        return agreementLevel;
    }
	
	

}
