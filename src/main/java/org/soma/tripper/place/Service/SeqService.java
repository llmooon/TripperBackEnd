package org.soma.tripper.place.Service;

import org.soma.tripper.place.entity.Seq;
import org.soma.tripper.user.domain.User;

import java.util.Optional;

public interface SeqService {
    Seq insertSeq(Seq seq);
    Seq modifySeq(Seq seq);
    Optional<Seq> loadSeq(int seqnum);
}
