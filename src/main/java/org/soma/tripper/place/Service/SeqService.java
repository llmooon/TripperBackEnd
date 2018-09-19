package org.soma.tripper.place.Service;

import com.amazonaws.services.apigateway.model.Op;
import org.soma.tripper.place.entity.Seq;
import org.soma.tripper.user.domain.User;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface SeqService {
    Seq insertSeq(Seq seq);
    Seq modifySeq(Seq seq);
    Optional<Seq> loadSeq(int seqnum);
    void deleteSeq(int seqnum);
    Optional<List<Seq>> loadSeqByUser(User user);
}
