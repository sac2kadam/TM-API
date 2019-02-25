package com.iemr.mmu.service.tele_consultation;

public interface SMSGatewayService {
	public int smsSenderGateway(String smsType, Long benRegID, Integer specializationID, Long tMRequestID,
			Long tMRequestCancelID, String createdBy, String tcDate, String tcPreviousDate, String Authorization);

	public String createSMSRequest(String smsType, Long benRegID, Integer specializationID, Long tMRequestID,
			Long tMRequestCancelID, String createdBy, String tcDate, String tcPreviousDate);

	public String sendSMS(String request, String Authorization);
}
