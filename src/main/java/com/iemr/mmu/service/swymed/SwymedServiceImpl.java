package com.iemr.mmu.service.swymed;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iemr.mmu.data.swymed.UserSwymed;
import com.iemr.mmu.repo.login.MasterVanRepo;
import com.iemr.mmu.repo.swymed.UserRepo;
import com.iemr.mmu.repo.swymed.UserSwymedRepo;
import com.iemr.mmu.utils.config.ConfigProperties;
import com.iemr.mmu.utils.exception.SwymedException;

@Service
public class SwymedServiceImpl implements SwymedService {

	// swymed://dnsname?l=mylogin&p=mypassword&d=mydomain&c=callnumber&m=1
	private String swymed_dnsname = ConfigProperties.getPropertyByName("swymed_dnsname");

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private UserSwymedRepo userSwymedRepo;

	@Autowired
	private MasterVanRepo masterVanRepo;

	@Override
	public String login(Long userid) throws SwymedException {
		// TODO Auto-generated method stub
		UserSwymed user = userSwymedRepo.findOneMap(userid);

		if (user == null) {
			throw new SwymedException("User doesnt have access to Swymed");
		}
		//
		StringBuilder data = new StringBuilder();

		data.append(swymed_dnsname);
		data.append("?l=");
		data.append(user.getSwymedEmailID());
		data.append("&p=");
		data.append(user.getSwymedPassword());
		data.append("&d=");
		data.append(user.getSwymedDomain());

		return data.toString();
	}

	@Override
	public String callUser(Long fromuserid, Long touserid) throws SwymedException {
		// TODO Auto-generated method stub
		UserSwymed user = userSwymedRepo.findOneMap(fromuserid);
		UserSwymed touser = userSwymedRepo.findOneMap(touserid);

		if (user == null) {
			throw new SwymedException("User doesnt have access to Swymed");
		}
		if (touser == null) {
			throw new SwymedException("Callee  couldnt be found. Please call manually");
		}

		StringBuilder data = new StringBuilder();

		data.append(swymed_dnsname);
		data.append("?l=");
		data.append(user.getSwymedEmailID());
		data.append("&p=");
		data.append(user.getSwymedPassword());
		data.append("&d=");
		data.append(user.getSwymedDomain());
		data.append("&e=");
		data.append(touser.getSwymedEmailID());

		return data.toString();
	}

	@Override
	public String callVan(Long fromuserid, Integer vanid) throws SwymedException {
		// TODO Auto-generated method stubUserSwymed user =
		// userSwymedRepo.findOneMap(fromuserid);
		UserSwymed user = userSwymedRepo.findOneMap(fromuserid);
		String vanSwymesEmail = masterVanRepo.getSpokeEmail(vanid);
		// MasterVan van = masterVanRepo.findOne(vanid);

		if (user == null) {
			throw new SwymedException("User doesnt have access to Swymed");
		}
		if (vanSwymesEmail == null) {
			throw new SwymedException("Callee  couldnt be found. Please call manually");
		}

		StringBuilder data = new StringBuilder();

		data.append(swymed_dnsname);
		data.append("?l=");
		data.append(user.getUsername());
		data.append("&p=");
		data.append(user.getSwymedPassword());
		data.append("&d=");
		data.append(user.getSwymedDomain());
		data.append("&e=");
		data.append(vanSwymesEmail);

		return data.toString();
	}

}
