package org.soma.tripper.place.Service;

import org.soma.tripper.place.entity.Seq;
import org.soma.tripper.place.repository.SeqRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SeqServiceImpl implements  SeqService{
    @Autowired
    SeqRepository seqRepository;

    @Override
    public Seq insertSeq(Seq seq) {
        return seqRepository.save(seq);
    }
}
