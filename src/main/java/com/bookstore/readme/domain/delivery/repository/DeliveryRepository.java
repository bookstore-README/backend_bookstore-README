package com.bookstore.readme.domain.delivery.repository;

import com.bookstore.readme.domain.delivery.domain.Delivery;
import com.bookstore.readme.domain.delivery.domain.DeliveryStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface DeliveryRepository extends JpaRepository<Delivery, Long> {

    List<Delivery> findByMemberId(Long memberId);

    Optional<Delivery> findByIdAndMemberId(Long id, Long memberId);

    List<Delivery> findAllByMemberIdAndCreateDateBetween(Long memberId
            , LocalDateTime start
            , LocalDateTime end);

    // @Query("select d from Delivery d " +
    //         "left join Member m on m.member_id = d.member_id" +
    //         "where m.member_id = :member_id" +
    //         "and (:delivery_status is null or d.delivery_status = :delivery_status)" +
    //         "and d.create_date between :start and :end"
    // )
    List<Delivery> findCustomByMemberIdAndDeliveryStatusAndCreateDateBetween(
           Long memberId
            , DeliveryStatus deliveryStatus
            , LocalDateTime start
            , LocalDateTime end);



}
