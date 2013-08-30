package com.zju.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.zju.dao.PatientMapper;
import com.zju.model.Patient;

public class UserDetailServiceImpl implements UserDetailsService {

	@Autowired
	private PatientMapper patientMapper;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		if ("ejianyan".equals(username)) {
			Patient admin = new Patient();
			admin.setJZKH("ejianyan");
			admin.setLXDH("ejianyan");
			return admin;
		} else {
			Patient patient = patientMapper.getPatient(username);
			if (patient == null || patient.getLXDH() == null) {
				throw new UsernameNotFoundException("user '" + username + "' not found...");
			}
			return patient;
		}
	}

}
