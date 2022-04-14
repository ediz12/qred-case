package se.qred.task.core.service;

import se.qred.task.api.response.OrganizationResponse;
import se.qred.task.client.model.AllabolagOrganizationDetailResponse;
import se.qred.task.core.mapper.request.OrganizationRequestMapper;
import se.qred.task.core.mapper.response.OrganizationResponseMapper;
import se.qred.task.core.model.OrganizationPair;
import se.qred.task.db.OrganizationRepository;
import se.qred.task.db.dto.Organization;

import javax.ws.rs.NotFoundException;
import java.util.Optional;

public class OrganizationService {

    private final OrganizationRepository organizationRepository;
    private final OrganizationRequestMapper organizationRequestMapper;
    private final OrganizationResponseMapper organizationResponseMapper;

    public OrganizationService(OrganizationRepository organizationRepository, OrganizationRequestMapper organizationRequestMapper,
                               OrganizationResponseMapper organizationResponseMapper) {
        this.organizationRepository = organizationRepository;
        this.organizationRequestMapper = organizationRequestMapper;
        this.organizationResponseMapper = organizationResponseMapper;
    }

    public OrganizationPair create(AllabolagOrganizationDetailResponse organizationDetailResponse) {
        final Organization organization = organizationRequestMapper.map(organizationDetailResponse);
        final Organization addedOrganization = organizationRepository.save(organization);
        return new OrganizationPair(addedOrganization, organizationResponseMapper.map(addedOrganization));
    }

    public Optional<OrganizationPair> getByOrganizationNumber(String organizationNumber) {
        final Optional<Organization> optionalOrganization = organizationRepository.findByOrganizationNumber(organizationNumber);
        if (!optionalOrganization.isPresent()) {
            return Optional.empty();
        }
        final Organization organization = optionalOrganization.get();
        return Optional.of(new OrganizationPair(organization, organizationResponseMapper.map(organization)));
    }

    public OrganizationResponse getByOrganizationId(Long organizationId) {
        return organizationRepository.findById(organizationId)
                .map(organizationResponseMapper::map)
                .orElseThrow(NotFoundException::new);
    }
}
