package se.qred.task.core.service;

import com.google.common.collect.Lists;
import se.qred.task.api.request.ApplicationApplyRequest;
import se.qred.task.api.response.ApplicationApplyResponse;
import se.qred.task.api.response.ApplicationFullResponse;
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

public class ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final ApplicationRequestMapper applicationRequestMapper;
    private final ApplicationResponseMapper applicationResponseMapper;

    public ApplicationService(ApplicationRepository applicationRepository, ApplicationRequestMapper applicationRequestMapper, ApplicationResponseMapper applicationResponseMapper) {
        this.applicationRepository = applicationRepository;
        this.applicationRequestMapper = applicationRequestMapper;
        this.applicationResponseMapper = applicationResponseMapper;
    }

    public ApplicationApplyResponse create(ApplicationApplyRequest applicationApplyRequest, Organization organization, String userId) {
        final Application application = applicationRequestMapper.map(applicationApplyRequest, organization, userId);
        final Application savedApplication = applicationRepository.save(application);
        return applicationResponseMapper.map(savedApplication);
    }

    public boolean isProcessedApplication(Long applicationId) {
        final Optional<Application> optionalApplication = applicationRepository.findById(applicationId);
        return optionalApplication.filter(application -> ApplicationStatus.PROCESSED.equals(application.getStatus()))
                .isPresent();
    }

    public void pendApplication(Application application, Offer offer) {
        application.setStatus(ApplicationStatus.PENDING);
        application.setOffer(offer);
        applicationRepository.save(application);
    }

    public void cancelApplications(List<Long> expiredApplicationIds) {
        if (expiredApplicationIds.isEmpty()) {
            return;
        }

        final List<Application> applications = applicationRepository.findAllInApplicationIds(expiredApplicationIds);
        for (Application application : applications) {
            application.setStatus(ApplicationStatus.CANCELLED);
            applicationRepository.save(application);
        }
    }

    public Optional<Application> cancelLatestApplication(String userId) {
        final Optional<Application> optionalApplication = applicationRepository.findByUserIdAndStatusIn(userId, Lists.newArrayList(ApplicationStatus.PENDING, ApplicationStatus.PROCESSED));
        if (!optionalApplication.isPresent()) {
            return Optional.empty();
        }

        final Application application = optionalApplication.get();
        application.setStatus(ApplicationStatus.CANCELLED);
        applicationRepository.save(application);
        return Optional.of(application);
    }

    public ApplicationPair signLatestApplication(String userId) {
        final Application application = applicationRepository.findByUserIdAndStatusIsPending(userId)
                .orElseThrow(NotFoundException::new);
        application.setStatus(ApplicationStatus.SIGNED);
        applicationRepository.save(application);
        return new ApplicationPair(application, applicationResponseMapper.mapFull(application));
    }

    public ApplicationFullResponse getLatestApplication(String userId) {
        return applicationRepository.findByUserIdAndStatusIn(userId, Lists.newArrayList(ApplicationStatus.PENDING, ApplicationStatus.PROCESSED))
                .map(applicationResponseMapper::mapFull)
                .orElseThrow(NotFoundException::new);
    }

    public Application getApplicationById(Long applicationId) {
        return applicationRepository.findById(applicationId)
                .orElseThrow(NotFoundException::new);
    }
}
