package cts.mfpe.authorization.entities;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class JwtRequest implements Serializable {


	private static final long serialVersionUID = 5926468583005150707L;

	//private String username;
	private String firstName;
	private String lastName;
	private String userEmail;
	private String userPassword;
	private String userAddress;
	private String userPhone;

}