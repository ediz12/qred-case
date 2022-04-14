package se.qred.task.core.service;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import se.qred.task.api.request.ApplicationApplyRequest;
import se.qred.task.api.response.ApplicationApplyResponse;
import se.qred.task.core.mapper.request.ApplicationRequestMapper;
import se.qred.task.core.mapper.response.ApplicationResponseMapper;
import se.qred.task.db.ApplicationRepository;
import se.qred.task.db.dto.Application;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ApplicationServiceTest {

    @Mock
    private ApplicationRepository applicationRepository;

    @Mock
    private ApplicationRequestMapper applicationRequestMapper;

    @Mock
    private ApplicationResponseMapper applicationResponseMapper;

    @Captor
    private ArgumentCaptor<Application> applicationDTOArgumentCaptor;

    private ApplicationService applicationService;

    @Before
    public void setUp() throws Exception {
        applicationService = new ApplicationService(applicationRepository, applicationRequestMapper, applicationResponseMapper);

    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void test() {
        // given
        final String userId = "1";
        final ApplicationApplyRequest applyRequest = new ApplicationApplyRequest.Builder()
                .amount(10000)
                .email("test@test.com")
                .phoneNumber("+1234567890")
                .organizationNumber("1234567890")
                .build();
        final Application application = new Application.Builder()
                .amount(10000)
                .email("test@test.com")
                .phoneNumber("+1234567890")
        //        .organizationId(1234567890)
                .build();
        final ApplicationApplyResponse expectedApplyResponse = new ApplicationApplyResponse.Builder()
         //       .id(1)
                .amount(10000)
                .email("test@test.com")
                .phoneNumber("+1234567890")
                .build();

        // when
        //when(applicationRequestMapper.map(applyRequest, userId)).thenReturn(application);
        //when(applicationRepository).add(applicationDTO).thenReturn(newApplicationDTO);
        //when(applicationResponseMapper.map(newApplicationDTO)).thenReturn(expectedApplyResponse);

        // then
        //final ApplicationApplyResponse applicationApplyResponse = applicationService.create(applyRequest, userId);
        //verify(applicationRequestMapper).map(applyRequest, userId);

        // TODO repository
        // verify(appRepository).ad(applicationDTOArgumentCaptor.capture());
        // final ApplicationDTO value = applicationDTOArgumentCaptor.getValue();
        // assertTrue(value.getId() == 12531762L);

        verify(applicationResponseMapper).map(application);
        verifyNoMoreInteractions(applicationRequestMapper, applicationResponseMapper);

        //assertEquals("ID is equal", expectedApplyResponse.getId(), applicationApplyResponse.getId());
        //assertEquals("Amount is equal", expectedApplyResponse.getAmount(), applicationApplyResponse.getAmount());
        //assertEquals("Email is equal", expectedApplyResponse.getEmail(), applicationApplyResponse.getEmail());
        //assertEquals("Phone number is equal", expectedApplyResponse.getPhoneNumber(), applicationApplyResponse.getPhoneNumber());
        //assertNull("Organization is null", applicationApplyResponse.getOrganization());
    }
}