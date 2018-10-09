    package org.soma.tripper.review.repository;

    import org.soma.tripper.review.entity.Review;
    import org.springframework.data.jpa.repository.JpaRepository;

    import java.util.List;
    import java.util.Optional;


    public interface ReviewRepository extends JpaRepository<Review,Integer> {

        Optional<List<Review>> findReviewByUsernum(int usernum);
        Optional<Review> findReviewByUsernumAndSeqnum(int usernum,int seqnum);
    }
