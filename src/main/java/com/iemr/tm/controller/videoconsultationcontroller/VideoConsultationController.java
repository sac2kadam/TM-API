/*
* AMRIT – Accessible Medical Records via Integrated Technology
* Integrated EHR (Electronic Health Records) Solution
*
* Copyright (C) "Piramal Swasthya Management and Research Institute"
*
* This file is part of AMRIT.
*
* This program is free software: you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation, either version 3 of the License, or
* (at your option) any later version.
*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with this program.  If not, see https://www.gnu.org/licenses/.
*/
package com.iemr.tm.controller.videoconsultationcontroller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.iemr.tm.service.videoconsultation.VideoConsultationService;
import com.iemr.tm.utils.response.OutputResponse;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/videoConsultation", headers = "Authorization")
public class VideoConsultationController {

	private Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

	@Autowired
	private VideoConsultationService videoConsultationService;

	@CrossOrigin()
	@ApiOperation(value = "Login to video consultation service", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = "/login/{userID}", headers = "Authorization", method = { RequestMethod.GET }, produces = {
			"application/json" })
	public String login(@PathVariable("userID") Long userID) {

		OutputResponse response = new OutputResponse();

		try {

			String createdData = videoConsultationService.login(userID);

			response.setResponse(createdData.toString());

		} catch (Exception e) {
			logger.error(e.getMessage());
			response.setError(e);

		}
		/**
		 * sending the response...
		 */
		return response.toString();

	}

	@CrossOrigin()
	@ApiOperation(value = "Call user for video consultation service", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = "/call/{fromuserID}/{touserID}", headers = "Authorization", method = {
			RequestMethod.GET }, produces = { "application/json" })
	public String call(@PathVariable("fromuserID") Long fromuserID, @PathVariable("touserID") Long touserID) {

		OutputResponse response = new OutputResponse();

		try {

			String createdData = videoConsultationService.callUser(fromuserID, touserID);

			response.setResponse(createdData.toString());

		} catch (Exception e) {
			logger.error(e.getMessage());
			response.setError(e);

		}
		/**
		 * sending the response...
		 */
		return response.toString();

	}

	@CrossOrigin()
	@ApiOperation(value = "Video consultation service for users by passing type", consumes = "application/json", produces = "application/json")	
	@RequestMapping(value = "/call/{fromuserID}/{touserID}/{type}", headers = "Authorization", method = {
			RequestMethod.GET }, produces = { "application/json" })
	public String callVideoConsultationAndJitsi(@PathVariable("fromuserID") Long fromuserID,
			@PathVariable("touserID") Long touserID, @PathVariable("type") String Type) {

		OutputResponse response = new OutputResponse();

		try {
			String createdData = null;
			if (Type.equalsIgnoreCase("VideoConsultation")) {
				createdData = videoConsultationService.callUser(fromuserID, touserID);
			} else {
				createdData = videoConsultationService.callUserjitsi(fromuserID, touserID);
			}

			response.setResponse(createdData.toString());

		} catch (Exception e) {
			logger.error(e.getMessage());
			response.setError(e);

		}
		/**
		 * sending the response...
		 */
		return response.toString();

	}

	@CrossOrigin()
	@ApiOperation(value = "Call van through video consultation", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = "/callvan/{fromuserID}/{vanID}", headers = "Authorization", method = {
			RequestMethod.GET }, produces = { "application/json" })
	public String callvan(@PathVariable("fromuserID") Long fromuserID, @PathVariable("vanID") Integer vanID) {

		OutputResponse response = new OutputResponse();

		try {

			String createdData = videoConsultationService.callVan(fromuserID, vanID);

			response.setResponse(createdData.toString());

		} catch (Exception e) {
			logger.error(e.getMessage());
			response.setError(e);

		}
		/**
		 * sending the response...
		 */
		return response.toString();

	}

	@CrossOrigin()
	@ApiOperation(value = "Call van through video consultation by passing type", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = "/callvan/{fromuserID}/{vanID}/{type}", headers = "Authorization", method = {
			RequestMethod.GET }, produces = { "application/json" })
	public String callVanVideoConsultationAndJitsi(@PathVariable("fromuserID") Long fromuserID,
			@PathVariable("vanID") Integer vanID, @PathVariable("type") String Type) {

		OutputResponse response = new OutputResponse();

		try {

			String createdData = null;
			if (Type.equalsIgnoreCase("VideoConsultation")) {
				createdData = videoConsultationService.callVan(fromuserID, vanID);
			} else {
				createdData = videoConsultationService.callVanJitsi(fromuserID, vanID);
			}

			response.setResponse(createdData.toString());

		} catch (Exception e) {
			logger.error(e.getMessage());
			response.setError(e);

		}
		/**
		 * sending the response...
		 */
		return response.toString();

	}

	@CrossOrigin()
	@ApiOperation(value = "Logout of video consultation service", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = "/logout", headers = "Authorization", method = { RequestMethod.GET }, produces = {
			"application/json" })
	public String logout() {

		OutputResponse response = new OutputResponse();

		try {

			String createdData = videoConsultationService.logout();

			response.setResponse(createdData.toString());

		} catch (Exception e) {
			logger.error(e.getMessage());
			response.setError(e);

		}
		/**
		 * sending the response...
		 */
		return response.toString();

	}

}
