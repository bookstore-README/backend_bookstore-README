package com.bookstore.readme.domain.delivery.service;

import com.bookstore.readme.domain.basket.repository.BasketRepository;
import com.bookstore.readme.domain.book.domain.Book;
import com.bookstore.readme.domain.book.exception.NotFoundBookByIdException;
import com.bookstore.readme.domain.book.repository.BookRepository;
import com.bookstore.readme.domain.delivery.domain.BasicAddress;
import com.bookstore.readme.domain.delivery.domain.Delivery;
import com.bookstore.readme.domain.delivery.domain.DeliveryStatus;
import com.bookstore.readme.domain.delivery.dto.DeliveryDto;
import com.bookstore.readme.domain.delivery.dto.DeliverySaveDto;
import com.bookstore.readme.domain.delivery.dto.DeliveryStatusDto;
import com.bookstore.readme.domain.delivery.exception.DeliverySaveException;
import com.bookstore.readme.domain.delivery.exception.NotFoundDeliveryByIdException;
import com.bookstore.readme.domain.delivery.exception.NotFoundDeliveryByMemberIdException;
import com.bookstore.readme.domain.delivery.repository.BasicAddressRepository;
import com.bookstore.readme.domain.delivery.repository.DeliveryRepository;
import com.bookstore.readme.domain.member.exception.NotFoundMemberByIdException;
import com.bookstore.readme.domain.member.model.Member;
import com.bookstore.readme.domain.member.repository.MemberRepository;
import com.bookstore.readme.domain.order.domain.Order;
import com.bookstore.readme.domain.order.domain.OrderBook;
import com.bookstore.readme.domain.order.dto.OrderBookSaveDto;
import com.bookstore.readme.domain.order.repository.OrderBookRepository;
import com.bookstore.readme.domain.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class DeliveryService {

    private final MemberRepository memberRepository;
    private final BookRepository bookRepository;
    private final OrderBookRepository orderBookRepository;
    private final OrderRepository orderRepository;
    private final DeliveryRepository deliveryRepository;
    private final BasketRepository basketRepository;
    private final BasicAddressRepository basicAddressRepository;

    @Transactional(rollbackFor = Exception.class)
    public Long save(Long memberId, DeliverySaveDto deliverySaveDto) {

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NotFoundMemberByIdException(memberId));

        try {
            Order order = orderRepository.save(Order.builder()
                    .orderBooks(new ArrayList<>())
                    .build());

            Delivery delivery = deliveryRepository.save(deliverySaveDto.toEntity(order));

            delivery.changeMember(member);

            for (OrderBookSaveDto orderSaveDto : deliverySaveDto.getOrderBooks()) {
                Book book = bookRepository.findById(orderSaveDto.getBookId().longValue())
                        .orElseThrow(() -> new NotFoundBookByIdException(orderSaveDto.getBookId().longValue()));

                OrderBook orderBook = OrderBook.builder()
                        .book(book)
                        .count(orderSaveDto.getCount())
                        .order(order)
                        .build();

                orderBook.changeOrder(order);

                orderBookRepository.save(orderBook);
            }

            // 기본 배송지 저장 시, 회원 BasicAddress 업데이트
            if(deliverySaveDto.getBasicAddress()) {

                if(null != member.getBasicAddress()) {
                    basicAddressRepository.deleteById(member.getBasicAddress().getId());
                }

                BasicAddress basicAddress = BasicAddress.builder()
                        .name(deliverySaveDto.getName())
                        .phone(deliverySaveDto.getPhone())
                        .address(deliverySaveDto.getAddress())
                        .member(member)
                        .build();

                basicAddressRepository.save(basicAddress);

                member.updateBasicAddress(basicAddress);
            }

            // 장바구니 -> 주문하기로 장바구니 삭제 분기 처리
            if(null != deliverySaveDto.getBasketIds() && !deliverySaveDto.getBasketIds().isEmpty()) {
                List<Long> ids = deliverySaveDto.getBasketIds().stream()
                        .map(Integer::longValue)
                        .toList();

                basketRepository.deleteAllById(ids);
            }

            // OrderDto orderDto = OrderDto.of(order);

            return delivery.getId();
        } catch(Exception e) {
            throw new DeliverySaveException();
        }
    }

    @Transactional
    public DeliveryDto searchDelivery(Long deliveryId) {
        Delivery delivery = deliveryRepository.findById(deliveryId)
                .orElseThrow(() -> new NotFoundDeliveryByIdException(deliveryId));

        return DeliveryDto.of(delivery);
    }

    @Transactional
    public List<DeliveryDto> searchByMemberId(
            Long memberId
            , DeliveryStatus deliveryStatus
            , String startDate
            , String endDate
    ) {
        LocalDateTime start = LocalDate.parse(startDate).atStartOfDay();
        LocalDateTime end = LocalDate.parse(endDate).atTime(LocalTime.MAX);

        // List<Delivery> deliveries = deliveryRepository.findByMemberId(memberId);

        // if(deliveryStatus.equals(DeliveryStatus.ALL))
        //     deliveryStatus = null;

        List<Delivery> deliveries = null;

        // !!!!!!!나중에 리팩토링!!!!!!!
        //  1. N+1 발생
        //  2. deliveryStatus 전체 조회 시 조건 null 처리
        // 전체 조회
        if(deliveryStatus.equals(DeliveryStatus.ALL)) {
            deliveries = deliveryRepository.findAllByMemberIdAndCreateDateBetween(
                    memberId
                    , start
                    , end
            );
        }
        // 상태값 조건 추가 조회
        else {
            deliveries = deliveryRepository.findCustomByMemberIdAndDeliveryStatusAndCreateDateBetween(memberId
                    , deliveryStatus
                    , start
                    , end
            );
        }

        return DeliveryDto.ofs(deliveries);
    }

    @Transactional
    public Long cancleDelivery(Long memberId, Long deliveryId) {
        Delivery delivery = deliveryRepository.findByIdAndMemberId(deliveryId, memberId)
                .orElseThrow(() -> new NotFoundDeliveryByMemberIdException(memberId));

        delivery.updateDeliveryStatus(DeliveryStatus.CANCEL);

        return delivery.getId();
    }

    @Transactional
    public Long changeDelivery(Long memberId, DeliveryStatusDto deliveryStatusDto) {
        Long deliveryId = deliveryStatusDto.getDeliveryId().longValue();

        Delivery delivery = deliveryRepository.findByIdAndMemberId(deliveryId, memberId)
                .orElseThrow(() -> new NotFoundDeliveryByMemberIdException(memberId));

        DeliveryStatus deliveryStatus = DeliveryStatus.of(deliveryStatusDto.getDeliveryStatus());

        delivery.updateDeliveryStatus(deliveryStatus);

        return delivery.getId();
    }

    @Transactional
    public Long deleteDelivery(Long deliveryId) {

        deliveryRepository.deleteById(deliveryId);

        return deliveryId;
    }

}
