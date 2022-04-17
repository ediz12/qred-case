package se.qred.task.core.service;

import com.google.common.collect.Lists;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import se.qred.task.api.request.ApplicationApplyRequest;
import se.qred.task.api.response.ApplicationApplyResponse;
import se.qred.task.api.response.ApplicationFullResponse;
import se.qred.task.base.BaseMockitoTest;
import se.qred.task.base.model.db.MockApplication;
import se.qred.task.base.model.db.MockOffer;
import se.qred.task.base.model.db.MockOrganization;
import se.qred.task.base.model.request.MockApplicationApplyRequest;
import se.qred.task.base.model.response.MockApplicationApplyResponse;
import se.qred.task.base.model.response.MockApplicationFullResponse;
import se.qred.task.core.mapper.request.ApplicationRequestMapper;
import se.qred.task.core.mapper.response.ApplicationResponseMapper;
import se.qred.task.core.model.ApplicationPair;
import se.qred.task.core.model.enums.ApplicationStatus;
import se.qred.task.db.ApplicationRepository;
import se.qred.task.db.dto.Application;
import se.qred.task.db.dto.Offer;
import se.qred.task.db.dto.Organization;

import javax.ws.rs.NotFoundException;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class ApplicationServiceTest extends BaseMockitoTest {

    @Mock
    private ApplicationRepository applicationRepository;

    @Mock
    private ApplicationRequestMapper applicationRequestMapper;

    @Mock
    private ApplicationResponseMapper applicationResponseMapper;

    private ApplicationService applicationService;

    @Before
    public void setUp() throws Exception {
        applicationService = new ApplicationService(applicationRepository, applicationRequestMapper, applicationResponseMapper);

    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void create() {
        // Given
        final String userId = "1";
        final ApplicationApplyRequest applyRequest = MockApplicationApplyRequest.simpleApplyRequest();
        final Organization organization = MockOrganization.getSimpleModifiedOrganization();
        final Application application = MockApplication.simpleCreatedApplication();
        final Application createdApplication = MockApplication.simpleProcessedApplication();
        final ApplicationApplyResponse expectedApplyResponse = MockApplicationApplyResponse.simpleApplyResponse();

        // When
        when(applicationRequestMapper.map(applyRequest, organization, userId)).thenReturn(application);
        when(applicationRepository.save(application)).thenReturn(createdApplication);
        when(applicationResponseMapper.map(createdApplication)).thenReturn(expectedApplyResponse);

        // Then
        final ApplicationApplyResponse applicationApplyResponse = applicationService.create(applyRequest, organization, userId);
        verify(applicationRequestMapper).map(applyRequest, organization, userId);
        verify(applicationRepository).save(application);
        verify(applicationResponseMapper).map(createdApplication);
        verifyNoMoreInteractions(applicationRequestMapper, applicationResponseMapper);

        assertEquals("ID is equal", expectedApplyResponse.getId(), applicationApplyResponse.getId());
        assertEquals("Amount is equal", expectedApplyResponse.getAmount(), applicationApplyResponse.getAmount());
        assertEquals("Email is equal", expectedApplyResponse.getEmail(), applicationApplyResponse.getEmail());
        assertEquals("Phone number is equal", expectedApplyResponse.getPhoneNumber(), applicationApplyResponse.getPhoneNumber());
        assertEquals("Organization ID is equal", expectedApplyResponse.getOrganization().getId(), applicationApplyResponse.getOrganization().getId());
        // TODO: Fill other asserts
    }

    @Test
    public void whenApplicationIsNotProcessed_ThenReturnFalse() {
        // Given
        final Long applicationId = 1L;
        final Optional<Application> optionalApplication = Optional.of(MockApplication.simplePendingApplicationWithOffer());

        // When
        when(applicationRepository.findById(applicationId)).thenReturn(optionalApplication);

        // Then
        final boolean processedApplication = applicationService.isProcessedApplication(applicationId);
        verify(applicationRepository).findById(applicationId);
        verifyNoMoreInteractions(applicationRepository);
        verifyNoInteractions(applicationRequestMapper, applicationResponseMapper);

        assertFalse(processedApplication);
    }

    @Test
    public void whenApplicationIsProcessed_ThenReturnTrue() {
        // Given
        final Long applicationId = 1L;
        final Optional<Application> optionalApplication = Optional.of(MockApplication.simpleProcessedApplication());

        // When
        when(applicationRepository.findById(applicationId)).thenReturn(optionalApplication);

        // Then
        final boolean processedApplication = applicationService.isProcessedApplication(applicationId);
        verify(applicationRepository).findById(applicationId);
        verifyNoMoreInteractions(applicationRepository);
        verifyNoInteractions(applicationRequestMapper, applicationResponseMapper);

        assertTrue(processedApplication);
    }

    @Test
    public void pendApplication() {
        // Given
        final Application application = MockApplication.simpleProcessedApplication();
        final Offer offer = MockOffer.getSimpleOffer();

        final Application expectedApplication = MockApplication.simplePendingApplicationWithOffer();
        // When
        when(applicationRepository.save(application)).thenReturn(expectedApplication);

        // Then
        applicationService.pendApplication(application, offer);

        assertEquals(ApplicationStatus.PENDING, expectedApplication.getStatus());
        assertNotNull(expectedApplication.getOffer());
    }

    @Test
    public void whenApplicationIdsAreEmpty_thenDoNothingForCancelApplications() {
        // Given
        final List<Long> expiredApplicationIds = Lists.newArrayList();

        // When

        // Then
        applicationService.cancelApplications(expiredApplicationIds);
        verifyNoMoreInteractions(applicationRequestMapper, applicationRepository, applicationResponseMapper);
    }

    @Test
    public void whenApplicationIdsExist_thenCancelApplications() {
        // Given
        final List<Long> expiredApplicationIds = Lists.newArrayList(1L);
        final List<Application> applications = MockApplication.pendingApplications();

        // When
        when(applicationRepository.findAllInApplicationIds(expiredApplicationIds)).thenReturn(applications);
        for (Application application : applications) {
            when(applicationRepository.save(application)).thenReturn(application);
        }

        // Then
        applicationService.cancelApplications(expiredApplicationIds);
        verify(applicationRepository).findAllInApplicationIds(expiredApplicationIds);
        verify(applicationRepository, times(applications.size())).save(any(Application.class));
        verifyNoMoreInteractions(applicationRepository);
        verifyNoInteractions(applicationRequestMapper, applicationResponseMapper);

        for (Application application : applications) {
            assertEquals(ApplicationStatus.CANCELLED, application.getStatus());
        }
    }

    @Test
    public void whenLatestApplicationDoesNotExist_thenCancelLatestApplicationReturnEmptyOptional() {
        // Given
        final String userId = "1";
        final Optional<Application> expectedOptional = Optional.empty();

        // When
        when(applicationRepository.findByUserIdAndStatusIn(userId, Lists.newArrayList(ApplicationStatus.PENDING, ApplicationStatus.PROCESSED))).thenReturn(expectedOptional);

        // Then
        final Optional<Application> optional = applicationService.cancelLatestApplication(userId);
        verify(applicationRepository).findByUserIdAndStatusIn(userId, Lists.newArrayList(ApplicationStatus.PENDING, ApplicationStatus.PROCESSED));
        verifyNoMoreInteractions(applicationRepository);
        verifyNoInteractions(applicationRequestMapper, applicationResponseMapper);

        assertFalse(optional.isPresent());
    }

    @Test
    public void whenLatestApplicationExists_thenCancelLatestApplicationReturnCancelledOptional() {
        // Given
        final String userId = "1";
        final Optional<Application> optionalApplication = Optional.of(MockApplication.simpleProcessedApplication());
        final Application expectedApplication = optionalApplication.get();

        // When
        when(applicationRepository.findByUserIdAndStatusIn(userId, Lists.newArrayList(ApplicationStatus.PENDING, ApplicationStatus.PROCESSED))).thenReturn(optionalApplication);
        when(applicationRepository.save(expectedApplication)).thenReturn(expectedApplication);

        // Then
        final Optional<Application> optional = applicationService.cancelLatestApplication(userId);
        verify(applicationRepository).findByUserIdAndStatusIn(userId, Lists.newArrayList(ApplicationStatus.PENDING, ApplicationStatus.PROCESSED));
        verify(applicationRepository).save(expectedApplication);
        verifyNoMoreInteractions(applicationRepository);
        verifyNoInteractions(applicationRequestMapper, applicationResponseMapper);

        assertTrue(optional.isPresent());
        final Application application = optional.get();
        assertEquals(ApplicationStatus.CANCELLED, application.getStatus());
        // TODO other asserts
    }

    @Test
    public void whenPendingApplicationExists_thenSignApplication() {
        // Given
        final String userId = "1";
        final Application initialApplication = MockApplication.simplePendingApplicationWithOffer();
        final Optional<Application> initialOptional = Optional.of(initialApplication);
        final Application expectedApplication = MockApplication.simpleSignedApplication();
        final ApplicationFullResponse expectedResponse = MockApplicationFullResponse.simpleFullResponseWithOffer();
        final ApplicationPair expectedPair = new ApplicationPair(expectedApplication, expectedResponse);

        // When
        when(applicationRepository.findByUserIdAndStatusIsPending(userId)).thenReturn(initialOptional);
        when(applicationRepository.save(initialApplication)).thenReturn(expectedApplication);
        when(applicationResponseMapper.mapFull(initialApplication)).thenReturn(expectedResponse);

        // Then
        final ApplicationPair pair = applicationService.signLatestApplication(userId);
        verify(applicationRepository).findByUserIdAndStatusIsPending(userId);
        verify(applicationRepository).save(initialApplication);
        verify(applicationResponseMapper).mapFull(initialApplication);
        verifyNoMoreInteractions(applicationRepository, applicationResponseMapper);
        verifyNoInteractions(applicationRequestMapper);

        assertEquals(ApplicationStatus.SIGNED, pair.getApplication().getStatus());
        assertEquals(expectedPair.getApplication().getId(), pair.getApplication().getId());
        assertEquals(expectedPair.getApplicationResponse().getId(), pair.getApplicationResponse().getId());
        // TODO do proper asserts without object match
    }

    @Test(expected = NotFoundException.class)
    public void whenPendingApplicationDoesNotExist_thenSignApplicationThrowError() {
        // Given
        final String userId = "1";

        // When
        doThrow(NotFoundException.class).when(applicationRepository).findByUserIdAndStatusIsPending(userId);

        // Then
        final ApplicationPair applicationPair = applicationService.signLatestApplication(userId);
        verify(applicationRepository).findByUserIdAndStatusIsPending(userId);
        verifyNoMoreInteractions(applicationRepository);
        verifyNoInteractions(applicationRequestMapper, applicationResponseMapper);
    }

    @Test
    public void whenGetLatestApplicationExists_thenReturnLatestApplicationFullResponse() {
        // Given
        final String userId = "1";
        final Application expectedApplication = MockApplication.simpleProcessedApplication();
        final Optional<Application> expectedOptional = Optional.of(expectedApplication);
        final ApplicationFullResponse expectedResponse = MockApplicationFullResponse.simpleFullResponse();

        // When
        when(applicationRepository.findByUserIdAndStatusIn(userId, Lists.newArrayList(ApplicationStatus.PENDING, ApplicationStatus.PROCESSED))).thenReturn(expectedOptional);
        when(applicationResponseMapper.mapFull(expectedApplication)).thenReturn(expectedResponse);

        // Then
        final ApplicationFullResponse response = applicationService.getLatestApplication(userId);
        verify(applicationRepository).findByUserIdAndStatusIn(userId, Lists.newArrayList(ApplicationStatus.PENDING, ApplicationStatus.PROCESSED));
        verify(applicationResponseMapper).mapFull(expectedApplication);
        verifyNoMoreInteractions(applicationRepository, applicationResponseMapper);
        verifyNoInteractions(applicationRequestMapper);

        assertEquals(expectedResponse.getId(), response.getId());
        assertEquals(expectedResponse.getAmount(), response.getAmount());
        // TODO fill other asserts


    }

    @Test(expected = NotFoundException.class)
    public void whenGetLatestApplicationNotExists_thenLatestApplicationThrowError() {
        // Given
        final String userId = "1";

        // When
        doThrow(NotFoundException.class).when(applicationRepository).findByUserIdAndStatusIn(userId, Lists.newArrayList(ApplicationStatus.PENDING, ApplicationStatus.PROCESSED));

        // Then
        final ApplicationFullResponse latestApplication = applicationService.getLatestApplication(userId);
        verify(applicationRepository).findByUserIdAndStatusIn(userId, Lists.newArrayList(ApplicationStatus.PENDING, ApplicationStatus.PROCESSED));
        verifyNoMoreInteractions(applicationRepository);
        verifyNoInteractions(applicationRequestMapper, applicationResponseMapper);
    }

    @Test
    public void whenApplicationExists_thenGetApplicationById() {
        // Given
        final Long applicationId = 1L;
        final Application expectedApplication = MockApplication.simpleProcessedApplication();
        final Optional<Application> expectedOptional = Optional.of(expectedApplication);

        // When
        when(applicationRepository.findById(applicationId)).thenReturn(expectedOptional);

        // Then
        final Application application = applicationService.getApplicationById(applicationId);
        verify(applicationRepository).findById(applicationId);
        verifyNoMoreInteractions(applicationRepository);
        verifyNoInteractions(applicationRequestMapper, applicationResponseMapper);

        assertEquals(expectedApplication.getId(), application.getId());
        assertEquals(expectedApplication.getAmount(), application.getAmount());
        // TODO fill other asserts
    }

    @Test(expected = NotFoundException.class)
    public void whenApplicationNotExists_thenGetApplicationByIdThrowError() {
        // Given
        final Long applicationId = 2L;

        // When
        doThrow(NotFoundException.class).when(applicationRepository).findById(applicationId);

        // Then
        final Application application = applicationService.getApplicationById(applicationId);
        verify(applicationRepository).findById(applicationId);
        verifyNoMoreInteractions(applicationRepository);
        verifyNoInteractions(applicationRequestMapper, applicationResponseMapper);
    }
}