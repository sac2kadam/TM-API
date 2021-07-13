package com.iemr.mmu.service.fetosense;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.DocumentException;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.iemr.mmu.data.fetosense.Fetosense;
import com.iemr.mmu.data.fetosense.FetosenseData;
import com.iemr.mmu.data.fetosense.FetosenseDeviceID;
import com.iemr.mmu.repo.benFlowStatus.BeneficiaryFlowStatusRepo;
import com.iemr.mmu.repo.fetosense.FetosenseDeviceIDRepo;
import com.iemr.mmu.repo.fetosense.FetosenseRepo;
import com.iemr.mmu.repo.login.MasterVanRepo;
import com.iemr.mmu.utils.config.ConfigProperties;
import com.iemr.mmu.utils.exception.IEMRException;
import com.iemr.mmu.utils.http.HttpUtils;
//import com.itextpdf.kernel.pdf.PdfDocument;
//import com.itextpdf.kernel.pdf.PdfWriter;
//import com.itextpdf.layout.Document;

import ch.qos.logback.classic.Logger;

@Service
@PropertySource("classpath:application.properties")
public class FetosenseServiceImpl implements FetosenseService {

	@Value("${fotesenseFilePath}")
	private String fotesenseFilePath;

	static HttpURLConnection con;

	private static HttpUtils httpUtils = new HttpUtils();
	private Logger logger = (Logger) LoggerFactory.getLogger(this.getClass().getSimpleName());

	@Autowired
	private FetosenseRepo fetosenseRepo;

	@Autowired
	private MasterVanRepo masterVanRepo;

	@Autowired
	private BeneficiaryFlowStatusRepo beneficiaryFlowStatusRepo;

	@Autowired
	private FetosenseDeviceIDRepo fetosenseDeviceIDRepo;

	/***
	 * @author DU20091017 update the feto-sense data in the DB
	 * @throws IOException
	 * @throws DocumentException
	 */
	@Override
	public int updateFetosenseData(Fetosense fetosenseData) throws IEMRException {

		try {
			fetosenseData.setAccelerationsListDB(fetosenseData.getAccelerationsList().toString());
			fetosenseData.setDecelerationsListDB(fetosenseData.getAccelerationsList().toString());
			fetosenseData.setMovementEntriesDB(fetosenseData.getMovementEntries().toString());
			fetosenseData.setAutoFetalMovementDB(fetosenseData.getAutoFetalMovement().toString());
			fetosenseData.setFetosenseMotherID(fetosenseData.getMother().get("cmMotherId"));
			fetosenseData.setFetosensePartnerID(fetosenseData.getMother().get("partnerId"));
			fetosenseData.setPartnerName(fetosenseData.getMother().get("partnerName"));

			// fetching data from the db
			Fetosense fetosenseFetchData = fetosenseRepo.getFetosenseDetails(fetosenseData.getFetosenseID());

			if (fetosenseFetchData == null || fetosenseFetchData.getFetosenseID() == null)
//				fetosenseData.setFetosenseID(fetosenseData.getPartnerFetosenseID());
				throw new IEMRException("Invalid partnerFetosenseID");
//			else
//				throw new IEMRException("Invalid partnerFetosenseID");

			// setting the values from the DB response
			fetosenseData.setBeneficiaryRegID(fetosenseFetchData.getBeneficiaryRegID());
			if (fetosenseFetchData.getVisitCode() != null)
				fetosenseData.setVisitCode(fetosenseFetchData.getVisitCode());
			fetosenseData.setTestTime(fetosenseFetchData.getTestTime());
			fetosenseData.setMotherLMPDate(fetosenseFetchData.getMotherLMPDate());
			fetosenseData.setMotherName(fetosenseFetchData.getMotherName());
			fetosenseData.setFetosenseTestId(fetosenseFetchData.getFetosenseTestId());
			fetosenseData.setProviderServiceMapID(fetosenseFetchData.getProviderServiceMapID());
			fetosenseData.setBenFlowID(fetosenseFetchData.getBenFlowID());
			fetosenseData.setVanID(fetosenseFetchData.getVanID());
			fetosenseData.setTestName(fetosenseFetchData.getTestName());
			fetosenseData.setCreatedBy(fetosenseFetchData.getCreatedBy());

			fetosenseData.setResultState(true);

			// need to write the code for changing the report path data to base 64 and save
			// it in DB

			String filePath = generatePDF(fetosenseData.getReportPath());
			fetosenseData.setaMRITFilePath(filePath);

			// saving the feto sense response to DB
			Fetosense fetosenseDateUpdated = fetosenseRepo.save(fetosenseData);

			int flagUpdate = 0;

			// updating lab technician flag to 3 from 2 as we got the response from feto
			// sense
			if (fetosenseDateUpdated != null && fetosenseDateUpdated.getFetosenseID() > 0) {

				// need to check how many records are there with benflowID
				short lab_technician_flag = 3;
				ArrayList<Fetosense> fetosenseDataOnBenFlowID = fetosenseRepo
						.getFetosenseDetailsByFlowId(fetosenseFetchData.getBenFlowID());
				if (fetosenseDataOnBenFlowID.size() > 0) {
					// if any of the record is not updated then marking lab flag as 2 - not able to
					// open in doctor screen.
					for (Fetosense data : fetosenseDataOnBenFlowID) {
						if (data != null && !data.getResultState()) {
							lab_technician_flag = 2;
						}
					}
				}
				flagUpdate = beneficiaryFlowStatusRepo.updateLabTechnicianFlag(lab_technician_flag,
						fetosenseFetchData.getBenFlowID());
				if (flagUpdate > 0)
					return 1;
				else
					throw new IEMRException("Error in updating the lab technician flag");
			} else
				throw new IEMRException("Error in updating fetosense data");

		} catch (Exception e) {
			throw new IEMRException("Error in updating fetosense data" + e.getMessage());
		}

	}

	// generate report file in file storage
	private String generatePDF(String filePath) throws IEMRException {
		String filePathLocal = "";
		Long timeStamp = System.currentTimeMillis();
		try {
			URL url = new URL(filePath);
			con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			con.setDoInput(true);
			filePathLocal = fotesenseFilePath + timeStamp.toString() + ".pdf";
			Path path = Paths.get(filePathLocal);
			Files.copy(con.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

			// base64 = readPDFANDGetBase64(filePathLocal);

		} catch (IOException e) {
			throw new IEMRException(e.getMessage());
		} finally {
			con.disconnect();
		}

		return filePathLocal;
	}

	// generate report file in file storage
	@Override
	public String readPDFANDGetBase64(String filePath) throws IEMRException, IOException {
//		FileInputStream file = new FileInputStream(filePath);
		byte[] byteArray = Files.readAllBytes(Paths.get(filePath));
		return Base64.getEncoder().encodeToString(byteArray);
	}

	/***
	 * sends the details to fetosense.
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public String sendFetosenseTestDetails(Fetosense request, String auth) throws IEMRException {

		Fetosense response = null;

		try {
			// Saving Fetosense Data in Amrit DB
			response = fetosenseRepo.save(request);

			if (response != null && response.getFetosenseID() > 0) {

				FetosenseData fetosenseTestDetails = new FetosenseData();
				fetosenseTestDetails.setPartnerFetosenseID(response.getFetosenseID());
				fetosenseTestDetails.setBeneficiaryRegID(request.getBeneficiaryRegID());

				String ISO_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
				SimpleDateFormat sdf = new SimpleDateFormat(ISO_FORMAT);

				fetosenseTestDetails.setMotherLMPDate(sdf.format(request.getMotherLMPDate()));
				fetosenseTestDetails.setMotherName(request.getMotherName());
				fetosenseTestDetails.setTestName(request.getTestName());

				// checking whether device ID is mapped or not
				FetosenseDeviceID deviceIDForVanID = fetosenseDeviceIDRepo.getDeviceIDForVanID(request.getVanID());

				if (deviceIDForVanID != null && deviceIDForVanID.getDeviceID() != null) {
					fetosenseTestDetails.setDeviceID(deviceIDForVanID.getDeviceID());
				} else
					throw new IEMRException("Van is not mapped with the deviceID");

				JsonParser parser = new JsonParser();
				ResponseEntity<String> result = null;

				HashMap<String, Object> header = new HashMap<>();
				if (auth != null) {
					header.put("Authorization", auth);
				}

				String requestObj = new Gson().toJson(fetosenseTestDetails).toString();

				logger.debug("request obj of fetosense API - " + requestObj);

				// Invoking Fetosense API - Sending mother data and test details to fetosense
				result = httpUtils.postWithResponseEntity(
						ConfigProperties.getPropertyByName("fetosense-api-url-ANCTestDetails"), requestObj, header);

				if (Integer.parseInt(result.getStatusCode().toString()) == 200) {
					JsonObject responseObj = (JsonObject) parser.parse(result.getBody());
					JsonObject data1 = (JsonObject) responseObj.get("data");
					String responseData = data1.get("response").getAsString();
					if (responseData != null) {
						return "Patient details sent to fetosense device successfully. Please select patient name on device and start the test";
					} else
						throw new IEMRException("Error in receving data from fetosense");
				} else
					throw new IEMRException("Error in receving data from fetosense");

			} else
				throw new IEMRException("Unable to save data");

		} catch (Exception e) {
			throw new IEMRException("Unable to save data " + e.getMessage());
		}

	}

	@Override
	public String getFetosenseDetails(Long benFlowID) throws IEMRException {

		try {
			Map<String, Object> resMap = new HashMap<>();
			ArrayList<Fetosense> fetosenseData = fetosenseRepo.getFetosenseDetailsByFlowId(benFlowID);

			List<Fetosense> fetosenseList = new ArrayList<Fetosense>();
			for (Fetosense data : fetosenseData) {
				Fetosense listData = new Fetosense(data.getFetosenseID(), data.getBeneficiaryRegID(),
						data.getBenFlowID(), data.getVisitCode(), data.getFetosenseTestId(), data.getResultState());
				fetosenseList.add(listData);
			}
			resMap.put("benFetosenseData", fetosenseList);
			return new Gson().toJson(resMap);
		} catch (Exception e) {
			throw new IEMRException("Error in fetching fetosense details " + e.getMessage());
		}

	}

}
