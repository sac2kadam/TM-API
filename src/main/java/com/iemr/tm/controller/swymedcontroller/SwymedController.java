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
package com.iemr.tm.controller.swymedcontroller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.iemr.tm.service.swymed.SwymedService;
import com.iemr.tm.utils.response.OutputResponse;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/swymed", headers = "Authorization")
public class SwymedController {

	private Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

	@Autowired
	private SwymedService swymedService;

	@CrossOrigin()
	@ApiOperation(value = "Login to swymed service", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = "/login/{userID}", headers = "Authorization", method = { RequestMethod.GET }, produces = {
			"application/json" })
	public String login(@PathVariable("userID") Long userID) {

		OutputResponse response = new OutputResponse();

		try {

			String createdData = swymedService.login(userID);

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
	@ApiOperation(value = "Call user for swymed service", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = "/call/{fromuserID}/{touserID}", headers = "Authorization", method = {
			RequestMethod.GET }, produces = { "application/json" })
	public String call(@PathVariable("fromuserID") Long fromuserID, @PathVariable("touserID") Long touserID) {

		OutputResponse response = new OutputResponse();

		try {

			String createdData = swymedService.callUser(fromuserID, touserID);

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
	@ApiOperation(value = "Swymed service for users by passing type", consumes = "application/json", produces = "application/json")	
	@RequestMapping(value = "/call/{fromuserID}/{touserID}/{type}", headers = "Authorization", method = {
			RequestMethod.GET }, produces = { "application/json" })
	public String CallSwymedAndJitsi(@PathVariable("fromuserID") Long fromuserID,
			@PathVariable("touserID") Long touserID, @PathVariable("type") String Type) {

		OutputResponse response = new OutputResponse();

		try {
			String createdData = null;
			if (Type.equalsIgnoreCase("Swymed")) {
				createdData = swymedService.callUser(fromuserID, touserID);
			} else {
				createdData = swymedService.callUserjitsi(fromuserID, touserID);
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
	@ApiOperation(value = "Call van through Swymed", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = "/callvan/{fromuserID}/{vanID}", headers = "Authorization", method = {
			RequestMethod.GET }, produces = { "application/json" })
	public String callvan(@PathVariable("fromuserID") Long fromuserID, @PathVariable("vanID") Integer vanID) {

		OutputResponse response = new OutputResponse();

		try {

			String createdData = swymedService.callVan(fromuserID, vanID);

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
	@ApiOperation(value = "Call van through Swymed by passing type", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = "/callvan/{fromuserID}/{vanID}/{type}", headers = "Authorization", method = {
			RequestMethod.GET }, produces = { "application/json" })
	public String CallVanSwymedAndJitsi(@PathVariable("fromuserID") Long fromuserID,
			@PathVariable("vanID") Integer vanID, @PathVariable("type") String Type) {

		OutputResponse response = new OutputResponse();

		try {

			String createdData = null;
			if (Type.equalsIgnoreCase("Swymed")) {
				createdData = swymedService.callVan(fromuserID, vanID);
			} else {
				createdData = swymedService.callVanJitsi(fromuserID, vanID);
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
	@ApiOperation(value = "Logout of swymed service", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = "/logout", headers = "Authorization", method = { RequestMethod.GET }, produces = {
			"application/json" })
	public String logout() {

		OutputResponse response = new OutputResponse();

		try {

			String createdData = swymedService.logout();

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
