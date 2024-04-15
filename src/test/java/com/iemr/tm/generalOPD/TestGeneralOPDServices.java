/*
 * AMRIT – Accessible Medical Records via Integrated Technology Integrated EHR
 * (Electronic Health Records) Solution
 *
 * Copyright (C) "Piramal Swasthya Management and Research Institute"
 *
 * This file is part of AMRIT.
 *
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see https://www.gnu.org/licenses/.
 * 
 * package com.iemr.tm.generalOPD;
 * 
 * import static org.assertj.core.api.Assertions.assertThat; import static
 * org.mockito.ArgumentMatchers.isA; import static org.mockito.Mockito.mock;
 * import static org.mockito.Mockito.spy; import static
 * org.mockito.Mockito.when;
 * 
 * import java.util.ArrayList;
 * 
 * import org.hamcrest.Matchers; import org.junit.jupiter.api.BeforeAll; import
 * org.junit.jupiter.api.Test; import org.mockito.InjectMocks; import
 * org.mockito.Mock;
 * 
 * import com.iemr.tm.common.TestCommonServices; import
 * com.iemr.tm.data.anc.BenChildDevelopmentHistory; import
 * com.iemr.tm.data.anc.ChildFeedingDetails; import
 * com.iemr.tm.data.anc.PerinatalHistory; import
 * com.iemr.tm.data.anc.SysGastrointestinalExamination; import
 * com.iemr.tm.repo.nurse.anc.BenChildDevelopmentHistoryRepo; import
 * com.iemr.tm.repo.nurse.anc.ChildFeedingDetailsRepo; import
 * com.iemr.tm.repo.nurse.anc.PerinatalHistoryRepo; import
 * com.iemr.tm.repo.nurse.anc.SysGastrointestinalExaminationRepo; import
 * com.iemr.tm.service.generalOPD.GeneralOPDNurseServiceImpl; import
 * com.iemr.tm.service.generalOPD.GeneralOPDServiceImpl;
 * 
 * public class TestGeneralOPDServices {
 * 
 * @Mock TestCommonServices testCommonServices;
 * 
 * @InjectMocks private static GeneralOPDServiceImpl generalOPDServiceImpl =
 * spy(GeneralOPDServiceImpl.class); private static GeneralOPDNurseServiceImpl
 * generalOPDNurseServiceImpl = spy(GeneralOPDNurseServiceImpl.class);
 * 
 * private static BenChildDevelopmentHistoryRepo
 * benChildDevelopmentHistoryRepoMock = mock(
 * BenChildDevelopmentHistoryRepo.class); private static ChildFeedingDetailsRepo
 * childFeedingDetailsRepoMock = mock(ChildFeedingDetailsRepo.class); private
 * static PerinatalHistoryRepo perinatalHistoryRepoMock =
 * mock(PerinatalHistoryRepo.class); private static
 * SysGastrointestinalExaminationRepo sysGastrointestinalExaminationRepoMock =
 * mock( SysGastrointestinalExaminationRepo.class);
 * 
 * public static String feedingHistoryDataPveRes = ""; public static String
 * perinatalHistoryDataPveRes = ""; public static String
 * developmentHistoryDataPveRes = "";
 * 
 * @BeforeAll public static void initializeParams() {
 * 
 * TestCommonServices.initializeParams();
 * 
 * 
 * feedingHistoryDataPveRes =
 * "{\"data\":[],\"columns\":[{\"keyName\":\"captureDate\",\"columnName\":\"Date of Capture\"},{\"keyName\":\"childID\",\"columnName\":\"Child ID\"},{\"keyName\":\"benMotherID\",\"columnName\":\"Beneficiary Mother ID\"},{\"keyName\":\"typeOfFeed\",\"columnName\":\"Type Of Feed\"},{\"keyName\":\"compFeedStartAge\",\"columnName\":\"Comp Feed Start Age\"},{\"keyName\":\"noOfCompFeedPerDay\",\"columnName\":\"NoOf Comp Feed Per Day\"},{\"keyName\":\"foodIntoleranceStatus\",\"columnName\":\"Food Intolerance Status\"},{\"keyName\":\"typeofFoodIntolerance\",\"columnName\":\"Type of Food Intolerance\"}]}"
 * ; perinatalHistoryDataPveRes =
 * "{\"data\":[],\"columns\":[{\"keyName\":\"captureDate\",\"columnName\":\"Date of Capture\"},{\"keyName\":\"placeOfDelivery\",\"columnName\":\"Place Of Delivery\"},{\"keyName\":\"otherPlaceOfDelivery\",\"columnName\":\"Other Place Of Delivery\"},{\"keyName\":\"typeOfDelivery\",\"columnName\":\"Type Of Delivery\"},{\"keyName\":\"complicationAtBirth\",\"columnName\":\"Complication At Birth\"},{\"keyName\":\"otherComplicationAtBirth\",\"columnName\":\"Other Complication At Birth\"},{\"keyName\":\"gestation\",\"columnName\":\"Gestation\"},{\"keyName\":\"birthWeight_kg\",\"columnName\":\"Birth Weight(kg)\"}]}"
 * ; developmentHistoryDataPveRes =
 * "{\"data\":[],\"columns\":[{\"keyName\":\"captureDate\",\"columnName\":\"Date of Capture\"},{\"keyName\":\"grossMotorMilestone\",\"columnName\":\"Gross Motor Milestone\"},{\"keyName\":\"isGMMAttained\",\"columnName\":\"Is GMM Attained\"},{\"keyName\":\"fineMotorMilestone\",\"columnName\":\"Fine Motor Milestone\"},{\"keyName\":\"isFMMAttained\",\"columnName\":\"Is FMM Attained\"},{\"keyName\":\"socialMilestone\",\"columnName\":\"Social Milestone\"},{\"keyName\":\"isSMAttained\",\"columnName\":\"Is SM Attained\"},{\"keyName\":\"languageMilestone\",\"columnName\":\"Language Milestone\"},{\"keyName\":\"isLMAttained\",\"columnName\":\"Is LM Attained\"},{\"keyName\":\"developmentProblem\",\"columnName\":\"Development Problem\"}]}"
 * ;
 * 
 * try {
 * 
 * // Mocking Save Repo's BenChildDevelopmentHistory dvmtHsry =
 * spy(BenChildDevelopmentHistory.class); dvmtHsry.setID(1L);
 * when(benChildDevelopmentHistoryRepoMock.save(isA(BenChildDevelopmentHistory.
 * class))).thenReturn(dvmtHsry);
 * 
 * ChildFeedingDetails feedingHistry = spy(ChildFeedingDetails.class);
 * feedingHistry.setID(1L);
 * when(childFeedingDetailsRepoMock.save(isA(ChildFeedingDetails.class))).
 * thenReturn(feedingHistry);
 * 
 * PerinatalHistory perinatalHistry = spy(PerinatalHistory.class);
 * perinatalHistry.setID(1L);
 * when(perinatalHistoryRepoMock.save(isA(PerinatalHistory.class))).thenReturn(
 * perinatalHistry);
 * 
 * SysGastrointestinalExamination sysGastrointestinalExamination =
 * spy(SysGastrointestinalExamination.class);
 * sysGastrointestinalExamination.setID(1L);
 * when(sysGastrointestinalExaminationRepoMock.save(isA(
 * SysGastrointestinalExamination.class)))
 * .thenReturn(sysGastrointestinalExamination);
 * 
 * Mocking get History Repo's ArrayList<Object[]> getGOPDRes = new
 * ArrayList<Object[]>();
 * when(perinatalHistoryRepoMock.getBenPerinatalDetail(TestCommonServices.
 * beneficiaryRegID)) .thenReturn(getGOPDRes);
 * when(childFeedingDetailsRepoMock.getBenFeedingHistoryDetail(
 * TestCommonServices.beneficiaryRegID)) .thenReturn(getGOPDRes);
 * when(benChildDevelopmentHistoryRepoMock.getBenDevelopmentHistoryDetail(
 * TestCommonServices.beneficiaryRegID)) .thenReturn(getGOPDRes);
 * 
 * Mocking get Current Visit Repo's
 * 
 * when(benChildDevelopmentHistoryRepoMock.getBenDevelopmentDetails(
 * TestCommonServices.beneficiaryRegID,
 * TestCommonServices.benVisitID)).thenReturn(getGOPDRes);
 * 
 * when(perinatalHistoryRepoMock.getBenPerinatalDetails(TestCommonServices.
 * beneficiaryRegID, TestCommonServices.benVisitID)).thenReturn(getGOPDRes);
 * 
 * when(childFeedingDetailsRepoMock.getBenFeedingDetails(TestCommonServices.
 * beneficiaryRegID, TestCommonServices.benVisitID)).thenReturn(getGOPDRes);
 * 
 * when(sysGastrointestinalExaminationRepoMock.
 * getSSysGastrointestinalExamination( TestCommonServices.beneficiaryRegID,
 * TestCommonServices.benVisitID)) .thenReturn(new
 * SysGastrointestinalExamination());
 * 
 * 
 * } catch (Exception e) { // TODO Auto-generated catch block
 * e.printStackTrace(); } }
 * 
 * @Test public void saveGOPDNurseDataPveTest() {
 * 
 * Long response = null; try { // response =
 * generalOPDServiceImpl.saveNurseData(TestCommonServices.jsnOBJPve); response =
 * null;
 * 
 * } catch (Exception e) { // TODO Auto-generated catch block
 * e.printStackTrace(); }
 * 
 * assertThat(response).isEqualTo(1); }
 * 
 * @Test public void saveGOPDNurseDataNveTest() {
 * 
 * Long response = null; try { // response =
 * generalOPDServiceImpl.saveNurseData(TestCommonServices.jsnOBJNve);
 * 
 * } catch (Exception e) { // TODO Auto-generated catch block
 * e.printStackTrace(); }
 * 
 * assertThat(response).isEqualTo(null);
 * 
 * // assertEquals(1, response); }
 * 
 * @Test public void saveGOPDDoctorDataPveTest() {
 * 
 * Long response = null; try { response =
 * generalOPDServiceImpl.saveDoctorData(TestCommonServices.doctorSaveJsnPve,
 * "");
 * 
 * System.out.println("response " + response); } catch (Exception e) { // TODO
 * Auto-generated catch block e.printStackTrace(); }
 * 
 * assertThat(response).isEqualTo(1); }
 * 
 * 
 * @Test public void getBenVisitDetailsPveTest() {
 * 
 * String response = null; try { response =
 * generalOPDServiceImpl.getBenVisitDetailsFrmNurseGOPD(TestCommonServices.
 * beneficiaryRegID, TestCommonServices.benVisitID); } catch (Exception e) { //
 * TODO Auto-generated catch block e.printStackTrace(); }
 * 
 * assertThat(response).isEqualTo(TestCommonServices.visitDetailsPveRes); }
 * 
 * @Test public void getBenHistoryDetailsPveTest() {
 * 
 * String response = null; try { response =
 * generalOPDServiceImpl.getBenHistoryDetails(TestCommonServices.
 * beneficiaryRegID, TestCommonServices.benVisitID); } catch (Exception e) { //
 * TODO Auto-generated catch block e.printStackTrace(); }
 * 
 * assertThat(response).isEqualTo(TestCommonServices.historyDetailsPveRes); }
 * 
 * @Test public void getBeneficiaryVitalDetailsPveTest() {
 * 
 * String response = null; try { response =
 * generalOPDServiceImpl.getBeneficiaryVitalDetails(TestCommonServices.
 * beneficiaryRegID, TestCommonServices.benVisitID); } catch (Exception e) { //
 * TODO Auto-generated catch block e.printStackTrace(); }
 * 
 * assertThat(response).isEqualTo(testCommonServices.vitalDetailsPveRes); }
 * 
 * @Test public void getExaminationDetailsPveTest() {
 * 
 * String response = null; try { response =
 * generalOPDServiceImpl.getExaminationDetailsData(TestCommonServices.
 * beneficiaryRegID, TestCommonServices.benVisitID); } catch (Exception e) { //
 * TODO Auto-generated catch block e.printStackTrace(); }
 * 
 * assertThat(response).isEqualTo(TestCommonServices.examinationDetailsPveRes);
 * }
 * 
 * 
 * 
 * @Test public void updateBenHistoryDetailsPveTest() {
 * 
 * int response = 0; try { response =
 * generalOPDServiceImpl.updateBenHistoryDetails(TestCommonServices.
 * updateHstryJsnPve);
 * 
 * System.out.println("updateBenHistoryDetailsPveTest ---" + response); } catch
 * (Exception e) { // TODO Auto-generated catch block e.printStackTrace(); }
 * 
 * assertThat(response).isEqualTo(1); }
 * 
 * @Test public void updateBenVitalDetailsPveTest() {
 * 
 * int response = 0; try { response =
 * generalOPDServiceImpl.updateBenVitalDetails(TestCommonServices.
 * updateVitalJsnPve); } catch (Exception e) { // TODO Auto-generated catch
 * block e.printStackTrace(); }
 * 
 * assertThat(response).isEqualTo(1); }
 * 
 * @Test public void updateBenExaminationDetailsPveTest() {
 * 
 * int response = 0; try { response =
 * generalOPDServiceImpl.updateBenExaminationDetails(TestCommonServices.
 * updateExaminationJsnPve); } catch (Exception e) { // TODO Auto-generated
 * catch block e.printStackTrace(); }
 * 
 * assertThat(response).isEqualTo(1); } }
 */