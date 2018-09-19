package org.soma.tripper.place.Service;

import org.soma.tripper.common.exception.NoSuchDataException;
import org.soma.tripper.place.entity.Seq;
import org.soma.tripper.place.repository.SeqRepository;
import org.soma.tripper.user.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SeqServiceImpl implements  SeqService{
    @Autowired
    SeqRepository seqRepository;

    @Override
    public Seq insertSeq(Seq seq) {
        return seqRepository.save(seq);
    }

    @Override
    public Seq modifySeq(Seq seq) {
        Seq before = seqRepository.findSeqBySeqnum(seq.getSeqnum()).orElseThrow(()->new NoSuchDataException());
       // before.setSchedulelist(seq.getSchedulelist());
        seqRepository.save(before);
        return before;
    }

    @Override
    public Optional<Seq> loadSeq(int seqnum) {
        return seqRepository.findSeqBySeqnum(seqnum);
    }

    @Override
    public void deleteSeq(int seqnum) {
        seqRepository.deleteById(seqnum);
    }

    @Override
    public Optional<List<Seq>> loadSeqByUser(User user) {
        return seqRepository.findSeqsByUser(user);
    }
}
