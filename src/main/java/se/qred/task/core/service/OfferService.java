package se.qred.task.core.service;

import se.qred.task.api.request.OfferCreateRequest;
import se.qred.task.api.request.OfferNegotiateManagerRequest;
import se.qred.task.api.request.OfferNegotiateUserRequest;
import se.qred.task.api.response.OfferFullResponse;
import se.qred.task.core.mapper.request.OfferRequestMapper;
import se.qred.task.core.mapper.response.OfferResponseMapper;
import se.qred.task.core.model.OfferPair;
import se.qred.task.db.OfferRepository;
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

    public OfferPair create(OfferCreateRequest offerRequest) {
        final Offer offer = offerRequestMapper.map(offerRequest);
        final Offer savedOffer = offerRepository.create(offer);
        return new OfferPair(savedOffer, offerResponseMapper.map(savedOffer));
    }

    public OfferFullResponse negotiate(Long id, OfferNegotiateUserRequest negotiateRequest) {
        Offer offer = offerRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        if (!OfferStatus.PENDING.equals(offer.getOfferStatus())) {
            throw new RuntimeException(); // TODO Custom exception conflict response
        }

        offer = offerRequestMapper.map(negotiateRequest, offer);
        offerRepository.save(offer);
        return offerResponseMapper.mapFull(offer);
    }

    public OfferFullResponse negotiate(Long id, OfferNegotiateManagerRequest negotiateRequest) {
        Offer offer = offerRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        if (!OfferStatus.NEGOTIATED.equals(offer.getOfferStatus())) {
            throw new RuntimeException(); // TODO Custom exception conflict response
        }

        offer = offerRequestMapper.map(negotiateRequest, offer);
        offerRepository.save(offer);
        return offerResponseMapper.mapFull(offer);
    }

    public OfferFullResponse sign(Long id) {
        final Offer offer = offerRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        offer.setOfferStatus(OfferStatus.SIGNED);
        return offerResponseMapper.mapFull(offer);
    }

    public void cancel(Offer offer) {
        offer.setOfferStatus(OfferStatus.CANCELLED);
        offerRepository.save(offer);
    }

    public Optional<OfferFullResponse> getOfferById(Long id) {
        return offerRepository.findById(id)
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
