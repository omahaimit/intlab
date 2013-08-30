package com.zju.webapp.controller;

import org.apache.commons.lang.StringUtils;

import com.zju.service.InvalidSamplesManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.zju.model.InvalidSamples;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@Controller
@RequestMapping("/invalidSamplesform*")
public class InvalidSamplesFormController extends BaseFormController {
	
	private InvalidSamplesManager invalidSamplesManager;
	private final static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.s");

	@Autowired
	public void setInvalidSamplesManager(InvalidSamplesManager invalidSamplesManager) {
		this.invalidSamplesManager = invalidSamplesManager;

	}

	public InvalidSamplesFormController() {
		setCancelView("redirect:invalidSamples");
		setSuccessView("redirect:invalidSamplesform?barCode=");
	}

	@ModelAttribute
	@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
	protected InvalidSamples showForm(HttpServletRequest request) throws Exception {
		String barCode = request.getParameter("barCode");
		Locale locale = request.getLocale();

		if (!StringUtils.isBlank(barCode)) {
			InvalidSamples invalidSamples = invalidSamplesManager.getSample(barCode);
			if (invalidSamples.getPatient().getResult() >= 4) {
				saveMessage(request, getText("invalidSamples.valid", barCode, locale));
				return new InvalidSamples();
			}
			return invalidSamples;
		}

		return new InvalidSamples();
	}

	@RequestMapping(method = RequestMethod.POST)
	public String onSubmit(InvalidSamples invalidSamples, BindingResult errors, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String a = request.getParameter("buttonresult");

		if (a.equals("c")) {
			return getCancelView();
		}

		if (validator != null) { // validator is null during testing
			validator.validate(invalidSamples, errors);

			if (errors.hasErrors() && (!a.equals("d"))) { // don't validate when deleting
				return "invalidSamplesform";
			}
		}

		log.debug("entering 'onSubmit' method...");
		String barCode = request.getParameter("barCode");
		boolean isNew = (barCode == null);
		String success = getSuccessView() + barCode;
		Locale locale = request.getLocale();

		if (a.equals("d")) {
			invalidSamplesManager.remove(invalidSamples.getBarCode());
			saveMessage(request, getText("invalidSamples.deleted", locale));
		} else {
			if (invalidSamplesManager.barCodeExist(barCode)) {
				invalidSamples.setBarCode(barCode);
				invalidSamples.setRejectPerson(request.getRemoteUser());
				Date today = new Date();
				invalidSamples.setRejectTime(sdf.parse(sdf.format(today)));
				invalidSamplesManager.save(invalidSamples);
				String key = (isNew) ? "invalidSamples.added" : "invalidSamples.updated";
				saveMessage(request, getText(key, locale));
			} else {
				saveMessage(request, getText("invalidSamples.notExist", locale));
				barCode = null;
			}
			if (!isNew) {
				success = "redirect:invalidSamplesform?barCode=" + barCode;
			}
		}
		return success;
	}

}