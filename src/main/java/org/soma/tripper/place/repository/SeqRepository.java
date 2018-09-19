package org.soma.tripper.place.repository;

import org.soma.tripper.place.entity.Seq;
import org.soma.tripper.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SeqRepository extends JpaRepository<Seq,Integer> {
    Optional<Seq> findSeqBySeqnum(int seqnum);
    Optional<List<Seq>> findSeqsByUser(User user);
}
