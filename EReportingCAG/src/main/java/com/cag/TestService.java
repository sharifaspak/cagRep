/**
 * 
 */
package com.cag;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author aspak.avesh.sharif
 *
 */
@RestController
public class TestService {

	@Value("${mymessage}")
	private String mymessage;

@Value("${custom-app.enable-mocks}")
private boolean enableMocks;

//	@RequestMapping(method = RequestMethod.GET, value = "/checkEnv")
	@GetMapping(value = "/checkEnv")
	public String checkEnv() {
		System.out.println(mymessage);
		return mymessage;
	}

}
