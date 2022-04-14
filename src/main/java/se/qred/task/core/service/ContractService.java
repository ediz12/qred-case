package se.qred.task.core.service;

import se.qred.task.api.response.ContractResponse;
import se.qred.task.api.response.OfferFullResponse;
import se.qred.task.core.mapper.request.ContractRequestMapper;
import se.qred.task.core.mapper.response.ContractResponseMapper;
import se.qred.task.db.ContractRepository;
import se.qred.task.db.dto.Contract;
import se.qred.task.db.dto.Organization;

import java.util.List;
import java.util.stream.Collectors;

public class ContractService {

    private final ContractRepository contractRepository;
    private final ContractRequestMapper contractRequestMapper;
    private final ContractResponseMapper contractResponseMapper;

    public ContractService(ContractRepository contractRepository, ContractRequestMapper contractRequestMapper, ContractResponseMapper contractResponseMapper) {
        this.contractRepository = contractRepository;
        this.contractRequestMapper = contractRequestMapper;
        this.contractResponseMapper = contractResponseMapper;
    }

    public ContractResponse create(OfferFullResponse signedOffer, String userId, Organization organization) {
        final Contract contract = contractRequestMapper.map(signedOffer, userId, organization);
        final Contract savedContract = contractRepository.save(contract);
        return contractResponseMapper.map(savedContract);
    }

    public List<ContractResponse> getContractsByUserId(String userId) {
        return contractRepository.findAllByUserId(userId).stream()
                .map(contractResponseMapper::map)
                .collect(Collectors.toList());
    }
}
