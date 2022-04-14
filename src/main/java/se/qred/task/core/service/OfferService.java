package se.qred.task.core.service;

import se.qred.task.api.request.OfferCreateRequest;
import se.qred.task.api.request.OfferNegotiateManagerRequest;
import se.qred.task.api.request.OfferNegotiateUserRequest;
import se.qred.task.api.response.OfferCreateResponse;
import se.qred.task.api.response.OfferFullResponse;
import se.qred.task.core.mapper.request.OfferRequestMapper;
import se.qred.task.core.mapper.response.OfferResponseMapper;
import se.qred.task.core.model.OfferPair;
import se.qred.task.db.OfferRepository;
import se.qred.task.db.dto.Application;
import se.qred.task.db.dto.Offer;
import se.qred.task.core.model.enums.OfferStatus;

import javax.ws.rs.NotFoundException;
import java.util.List;
import java.util.Optional;

public class OfferService {

    private final OfferRepository offerRepository;
    private final OfferRequestMapper offerRequestMapper;
    private final OfferResponseMapper offerResponseMapper;

    public OfferService(OfferRepository offerRepository, OfferRequestMapper offerRequestMapper, OfferResponseMapper offerResponseMapper) {
        this.offerRepository = offerRepository;
        this.offerRequestMapper = offerRequestMapper;
        this.offerResponseMapper = offerResponseMapper;
    }

    public OfferPair create(OfferCreateRequest offerRequest, Application application) {
        final Offer offer = offerRequestMapper.map(offerRequest, application);
        final Offer savedOffer = offerRepository.save(offer);
        offerRepository.getSessionFactory().getCurrentSession().getTransaction().commit();
        return new OfferPair(savedOffer, offerResponseMapper.map(savedOffer));
    }

    public OfferFullResponse negotiate(long applicationId, OfferNegotiateUserRequest negotiateRequest) {
        Offer offer = offerRepository.findById(applicationId)
                .orElseThrow(NotFoundException::new);
        if (!OfferStatus.PENDING.equals(offer.getOfferStatus())) {
            throw new RuntimeException(); // TODO Custom exception conflict response
        }

        offer = offerRequestMapper.map(negotiateRequest, offer);
        offerRepository.save(offer);
        return offerResponseMapper.mapFull(offer);
    }

    public OfferFullResponse negotiate(long applicationId, OfferNegotiateManagerRequest negotiateRequest) {
        Offer offer = offerRepository.findById(applicationId)
                .orElseThrow(NotFoundException::new);
        if (!OfferStatus.NEGOTIATED.equals(offer.getOfferStatus())) {
            throw new RuntimeException(); // TODO Custom exception conflict response
        }

        offer = offerRequestMapper.map(negotiateRequest, offer);
        offerRepository.save(offer);
        return offerResponseMapper.mapFull(offer);
    }

    public OfferFullResponse sign(Long applicationId) {
        final Offer offer = offerRepository.findById(applicationId)
                .orElseThrow(NotFoundException::new);
        offer.setOfferStatus(OfferStatus.SIGNED);
        return offerResponseMapper.mapFull(offer);
    }

    public void cancel(Long applicationId) {
        final Optional<Offer> optionalOffer = offerRepository.findById(applicationId);
        if (!optionalOffer.isPresent()) {
            return;
        }

        final Offer offer = optionalOffer.get();
        offer.setOfferStatus(OfferStatus.CANCELLED);
        offerRepository.save(offer);
    }

    public Optional<OfferFullResponse> getOfferByApplicationId(Long applicationId) {
        return offerRepository.findById(applicationId)
                .map(offerResponseMapper::mapFull);
    }

    public List<Offer> getOffersByPending() {
        return offerRepository.findAllByStatusIsPending();
    }

    public void expireOffers(List<Offer> expiredOffers) {
        for (Offer expiredOffer : expiredOffers) {
            expiredOffer.setOfferStatus(OfferStatus.EXPIRED);
            offerRepository.save(expiredOffer);
        }
    }
}
