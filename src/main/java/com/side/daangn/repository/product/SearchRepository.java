package com.side.daangn.repository.product;

import com.side.daangn.entitiy.product.Search;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface SearchRepository extends JpaRepository<Search, Long> {

    Optional<Search> findBySearch (String search);

    @Transactional
    @Modifying
    @Query("UPDATE Search SET count = count + :count WHERE search = :search")
    void searchPlus(String search, long count);

    List<Search> findTop5ByTypeOrderByCountDesc(int type);

}
