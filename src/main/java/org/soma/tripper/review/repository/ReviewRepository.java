    package org.soma.tripper.review.repository;

    import org.soma.tripper.review.entity.Review;
    import org.springframework.data.domain.Page;
    import org.springframework.data.domain.Pageable;
    import org.springframework.data.jpa.repository.JpaRepository;
    import org.springframework.data.jpa.repository.Query;
    import org.springframework.data.repository.query.Param;

    import java.util.List;
    import java.util.Optional;


    public interface ReviewRepository extends JpaRepository<Review,Integer> {

        Optional<List<Review>> findReviewByUsernum(int usernum);
        Optional<Review> findReviewByUsernumAndSeqnum(int usernum,int seqnum);

        @Query("SELECT r from  Review r, User u, Seq s " +
                "WHERE (u.name=:str AND u.user_num=r.usernum AND s.seqnum=r.seqnum) " +
                "OR (s.title LIKE CONCAT('%',:str,'%')) and  s.seqnum=r.seqnum and u.user_num= r.usernum")
        Page<Review> findReviewBystr(@Param("str") String str, Pageable pageRequest);
    }
