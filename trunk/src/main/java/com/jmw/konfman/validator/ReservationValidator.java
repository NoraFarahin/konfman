/**
 * 
 */
package com.jmw.konfman.validator;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.jmw.konfman.model.Reservation;

/**
 * @author judahw
 *
 */
@Service(value = "reservationValidator")
public class ReservationValidator implements Validator {
    private final Log log = LogFactory.getLog(ReservationValidator.class);

	/* (non-Javadoc)
	 * @see org.springframework.validation.Validator#supports(java.lang.Class)
	 */
	public boolean supports(Class clazz) {
		return Reservation.class.equals(clazz);
	}

	/* (non-Javadoc)
	 * @see org.springframework.validation.Validator#validate(java.lang.Object, org.springframework.validation.Errors)
	 */
	public void validate(Object obj, Errors errors) {
		log.debug("Validating a reservaion");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "comment", "field.required");
		ValidationUtils.rejectIfEmpty(errors, "room", "noroom");
		ValidationUtils.rejectIfEmpty(errors, "user", "nouser");
		ValidationUtils.rejectIfEmpty(errors, "startDateTime", "nouser");
		ValidationUtils.rejectIfEmpty(errors, "endDateTime", "nouser");
		Reservation reservation = (Reservation) obj;
		Date now = new Date();
		if (reservation.getStartDateTime() != null && reservation.getStartDateTime().before(now)){
			errors.rejectValue("date", "past");
		}
		if (reservation.getStartDateTime() != null && reservation.getStartDateTime().after(reservation.getEndDateTime())){
			errors.rejectValue("startTime", "startsafterend");
		}

	}

}
